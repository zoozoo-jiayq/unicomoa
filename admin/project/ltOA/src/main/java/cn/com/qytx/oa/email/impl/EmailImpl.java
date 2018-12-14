/**
 *
 */
package cn.com.qytx.oa.email.impl;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairSetting.service.IAffairSetting;
import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.oa.email.dao.EmailBodyDao;
import cn.com.qytx.oa.email.dao.EmailToDao;
import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.domain.LogType;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.email.service.IEmail;
import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.oa.email.vo.EmailSearchVo;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.DateUtils;

/**
 * 功能:电子邮件服务接口实现类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Service
@Transactional
public class EmailImpl extends BaseServiceImpl<EmailBody> implements IEmail,Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 5221805580041180735L;

	private static final Logger LOGGER = Logger.getLogger(EmailImpl.class);
	@Autowired
    private EmailBodyDao emailBodyDao;
	@Autowired
    private EmailToDao emailToDao;
	@Autowired
    private IEmailBox emailBoxService;
	@Autowired
    private IUser userService;
	@Autowired
    IAffairsBody affairsBodyService;
	@Autowired
    private ILog logService; //日志服务类
	
	@Autowired
	private IAffairSetting affairSettingService;


    /**
     * 删除一封草稿或者已发邮件(同步取消发送收信人中为查看的)
     *
     * @param emailBodyId 邮件体ID
     */
    @Override
    public void deleteEmailBody(int emailBodyId) {

        // 标记邮件体为删除
        EmailBody emailBody = this.emailBodyDao.findOne(emailBodyId);
        emailBody.setIsDelete(CbbConst.DELETED);
        // 最后更新时间，最后更新人
        this.emailBodyDao.saveOrUpdate(emailBody);
        this.saveLog("删除草稿或已发送邮件,ID:" + emailBody.getId());
        // 如果已经发送过了，则取消发送
        if (emailBody.getSendStatus().equals(EmailConst.EMAIL_SENDED)) {
            for (EmailTo emailTo : findAllEmailToByEmailBodyId(emailBody
                    .getId())) {
                if (emailTo.getReadStatus().equals(EmailConst.EMAIL_NOT_READ)) {
                    emailTo.setEmailToStatus(EmailConst.EMAIL_CANCEL_SEND);
                    // 最后更新时间，最后更新人
                    this.emailToDao.saveOrUpdate(emailTo);
                }
            }
        }
    }

    /**
     * 删除一封收件箱邮件，可恢复---实质为移动到废纸篓
     *
     * @param emailToId 收件箱邮件ID
     */
    @Override
    public void deleteEmailTo(int emailToId) {
        EmailTo emailTo = this.emailToDao.findOne(emailToId);
        EmailBox emailBox = this.emailBoxService
                .findDefaultBoxByUserIdAndBoxType(emailTo.getToId(),
                        EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
        emailTo.setOldEmailBox(emailTo.getEmailBox());
        emailTo.setEmailBox(emailBox);
        emailTo.setEmailBoxId(emailBox.getId());
        emailTo.setReadStatus(EmailConst.EMAIL_READED);
        emailTo.setReadTime(new Timestamp(System.currentTimeMillis()));
        this.emailToDao.saveOrUpdate(emailTo);
        saveLog("删除一封收件箱邮件(ID:" + emailToId + ")到垃圾箱");
    }

    /**
     * 删除某个邮件箱所有已经读取的邮件
     *
     * @param userId 用户ID
     * @param boxId  邮件箱ID
     */
    public int deleteEmailToReaded(int userId, int boxId) {
        int count = emailToDao.deleteEmailToReaded(userId, boxId);
        saveLog("删除指定邮件箱(邮件箱ID:" + boxId + ")中的所有已读取邮件");
        return count;
    }

    /**
     * 销毁一封收件箱邮件
     *
     * @param emailToId 收件箱邮件ID
     */
    @Override
    public void destroyEmailTo(int emailToId) {
        EmailTo emailTo = this.emailToDao.findOne(emailToId);
        emailTo.setIsDelete(CbbConst.DELETED);
        this.emailToDao.saveOrUpdate(emailTo);
        saveLog("销毁一封收取的邮件,ID:" + emailToId);
    }

    /**
     * 销毁某个用户某个邮件箱中的所有邮件
     *
     * @param userId     用户Id
     * @param emailBoxId 邮件箱ID
     */
    public void destroyAllEmailTo(int userId, int emailBoxId) {
        this.emailToDao.destroyAllEmailTo(userId, emailBoxId);
        saveLog("销毁指定邮件箱(邮件箱ID:" + emailBoxId + ")中的所有邮件");
    }

    /**
     * 恢复一封废纸篓邮件到默认收件箱
     *
     * @param emailToId 邮件id
     */
    @Override
    public void recoveryEmailTo(int emailToId) {

        EmailTo emailTo = this.emailToDao.findOne(emailToId);
        EmailBox emailBox = emailTo.getOldEmailBox();
        if(emailBox==null){
        	emailBox = this.emailBoxService
        			.findDefaultBoxByUserIdAndBoxType(emailTo.getToId(),
        					EmailConst.EMAIL_BOX_TYPE_INBOX);
        }
        emailTo.setEmailBox(emailBox);
        emailTo.setEmailBoxId(emailBox.getId());
        this.emailToDao.saveOrUpdate(emailTo);

    }

    /**
     * 移动一封邮件到其他邮件箱
     *
     * @param emailToId   邮件
     * @param targetBoxId 目标邮件箱id
     */
    public void moveEmailTo(int emailToId, int targetBoxId) {
        if (emailToId == 0 || targetBoxId == 0) {
            return;
        }
        EmailTo emailTo = this.emailToDao.findOne(emailToId);
        EmailBox emailBox = this.emailBoxService.findById(targetBoxId);
        emailTo.setEmailBox(emailBox);
        emailTo.setEmailBoxId(emailBox.getId());
        this.emailToDao.saveOrUpdate(emailTo);
    }

    /**
     * 批量移动邮件，从某个邮件箱移动到某个邮件箱
     *
     * @param userId      用户ID
     * @param fromBoxId   从哪个邮件箱id
     * @param targetBoxId 移动到哪个邮件箱id
     */
    public void moveAllEmailTo(int userId, int fromBoxId, int targetBoxId) {
        this.emailToDao.moveAllEmailTo(userId, fromBoxId, targetBoxId);
    }

    /**
     * 标记一封邮件为已读取
     *
     * @param emailToId 邮件
     */
    public void readedEmailTo(int emailToId) {
        if (emailToId == 0) {
            return;
        }
        EmailTo emailTo = this.emailToDao.findOne(emailToId);
        emailTo.setReadStatus(EmailConst.EMAIL_READED);
        emailTo.setReadTime(new Timestamp(System.currentTimeMillis()));
        this.emailToDao.saveOrUpdate(emailTo);

        //回执给收件人收条
        checkAndSendAffairsForRead(emailTo);
    }

    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    public Page<EmailBody> findAllDraftEmailBodyByUserId(Pageable page,
                                                         int userId,String searchWord) {
        return this.emailBodyDao.findAllDraftEmailBodyByUserId(page, userId, searchWord);
    }

    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllDraftEmailBodyByUserId(int userId) {
        return this.emailBodyDao.findAllDraftEmailBodyByUserId(userId);
    }

    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param page          翻页信息
     * @param userId        用户Id
     * @param emailSearchVo 高级搜索条件
     * @return 查询结果
     */
    public Page<EmailBody> findAllDraftEmailBodyBySearchVo(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception {
        return this.emailBodyDao.findAllDraftEmailBodyBySearchVo(page, userId, emailSearchVo);
    }

    /**
     * 查询某个用户已发送邮件
     *
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    @Override
    public Page<EmailBody> findAllSendedEmailBodyByUserId(Pageable page, int userId,String searchWord) {
        return this.emailBodyDao.findAllSendedEmailBodyByUserId(page, userId, searchWord);

    }

    /**
     * 查询某个用户已发送邮件
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllSendedEmailBodyByUserId(int userId) {
        return this.emailBodyDao.findAllSendedEmailBodyByUserId(userId);
    }

    /**
     * 根据高级搜索条件查询某个用户的已发送邮件
     *
     * @param page          翻页信息
     * @param userId        用户ID
     * @param emailSearchVo 高级搜索信息
     * @return 结果集-翻页
     * @throws Exception 日期转换异常等等
     */
    public Page<EmailBody> findAllSendedEmailBodyBySearchVo(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception {
        return this.emailBodyDao.findAllSendedEmailBodyBySearchVo(page, userId, emailSearchVo);
    }

    /**
     * 获取某个用户的某个邮件箱的所有邮件
     *
     * @param page       翻页信息
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 查询结果
     */
    @Override
    public Page<EmailTo> findAllReceiveEmailByUserId(Pageable page, int userId, int emailBoxId) {
        return this.emailToDao.findAllReceiveEmailByUserId(page, userId, emailBoxId);
    }

    /**
     * 搜索某个用户的某个邮件箱邮件
     *
     * @param page       翻页信息
     * @param userId     用户Id
     * @param emailBoxId 邮件箱Id
     * @param searchWord 搜索关键字
     * @return 搜索结果
     */
    public Page<EmailTo> findAllReceiveEmailBySearchWord(Pageable page, int userId, int emailBoxId, String searchWord) {
        return this.emailToDao.findAllReceiveEmailBySearchWord(page, userId, emailBoxId, searchWord);
    }

    /**
     * 查找某个邮件箱下的所有邮件
     *
     * @param emailBoxId 邮件箱ID
     * @return 所有邮件
     */
    public List<EmailTo> findAllReceiveEmailByBoxId(int emailBoxId) {
        return this.emailToDao.findAllReceiveEmailByBoxId(emailBoxId);
    }

    /**
     * 查询某个用户未读取的邮件
     *
     * @param page   翻页信心
     * @param userId 用户ID
     * @return 查询结果
     */
    public Page<EmailTo> findAllNotReadEmail(Pageable page, int userId) {
        return this.emailToDao.findAllNotReadEmail(page, userId);
    }

    /**
     * 获取未读取邮件的个数
     *
     * @param userId 用户Id
     * @return 未读邮件数
     */
    public int countOfNotReadEmail(int userId) {
        return this.emailToDao.countOfNotReadEmail(userId);
    }
    /**
     * 获取未读取邮件的个数 收件箱
     * 2014.8.4
     * @param userId 用户Id
     * @return 未读邮件数
     */
    public int countOfNotReadInBox(int userId,int emailBoxId) {
    	return this.emailToDao.countOfNotReadInBox(userId, emailBoxId);
    }

    /**
     * 查询某个用户未读取的邮件，可以搜索
     *
     * @param page       翻页信心
     * @param userId     用户ID
     * @param searchWord 搜索关键字
     * @return 查询结果
     */
    public Page<EmailTo> findAllNotReadEmailBySearchWord(Pageable page, int userId, String searchWord) {
        return this.emailToDao.findAllNotReadEmailBySearchWord(page, userId, searchWord);
    }


    /**
     * 查询一个邮件体-未删除
     *
     * @param emailBodyId 邮件体Id
     * @return 邮件体
     */
    public EmailBody findEmailBodyByIdNotDelete(int emailBodyId) {
        return this.emailBodyDao.findById(emailBodyId);
    }

    /**
     * 查询一封收取的邮件
     *
     * @param emailToId 邮件id
     * @return 收件箱邮件
     */
    @Override
    public EmailTo findEmailToById(int emailToId) {
        return this.emailToDao.findById(emailToId);
    }

    /**
     * 根据邮件体查询所有发送的邮件信息
     *
     * @param emailBodyId 邮件体ID
     * @return 未删除的发送的邮件信息
     */
    public List<EmailTo> findAllEmailToByEmailBodyId(int emailBodyId) {
        return (List<EmailTo>) this.emailToDao.findAllEmailToByEmailBodyId(emailBodyId);
    }

    /**
     * 根据邮件体查询所有发送的邮件信息-不排除已取消发送的
     *
     * @param emailBodyId 邮件体ID
     * @return 未删除的发送的邮件信息
     */
    public List<EmailTo> findAllEmailToHasCancelSendByEmailBodyId(int emailBodyId) {
        return (List<EmailTo>) this.emailToDao.findAllEmailToHasCancelSendByEmailBodyId(emailBodyId);
    }

    /**
     * 保存一封邮件体--草稿
     *
     * @param emailBody 邮件箱实体
     */
    @Override
    public EmailBody saveOrUpdate(EmailBody emailBody) {
        checkSubject(emailBody);
        checkReceiveIds(emailBody);
        return this.emailBodyDao.saveOrUpdate(emailBody);
    }

    private void checkSubject(EmailBody emailBody) {
        if (StringUtils.isEmpty(emailBody.getSubject())) {
            emailBody.setSubject("[无主题]");
        }
    }

    /**
     * 检查并修改接收者ID
     */
    private void checkReceiveIds(EmailBody emailBody) {
        emailBody.setToId(checkIds(emailBody.getToId()));
        emailBody.setToName(checkIds(emailBody.getToName()));
        emailBody.setCopyToId(checkIds(emailBody.getCopyToId()));
        emailBody.setCopyToName(checkIds(emailBody.getCopyToName()));
        emailBody.setSecretToId(checkIds(emailBody.getSecretToId()));
        emailBody.setSecretToName(checkIds(emailBody.getSecretToName()));
    }

    private String checkIds(String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            if (!StringUtils.startsWith(ids, EmailConst.EMAIL_TO_SEPARATOR)) {
                ids = "," + ids;
            }
            if (!StringUtils.endsWith(ids, EmailConst.EMAIL_TO_SEPARATOR)) {
                ids = ids + ",";
            }
            return ids;
        }
        return ids;
    }

    /**
     * 保存并发送邮件
     *
     * @param emailBody 邮件体
     */
    @Override
    public void saveAndSend(EmailBody emailBody) {

        //保存
        this.saveOrUpdate(emailBody);
        List<String> arrIds = new ArrayList<String>();
        // 发送
        arrIds = sendEmailByReceiveIds(emailBody.getToId(), EmailConst.EMAIL_TO_TYPE_TO,
                emailBody, emailBody.getCreateUserInfo(),arrIds);
        arrIds = sendEmailByReceiveIds(emailBody.getCopyToId(),
                EmailConst.EMAIL_TO_TYPE_COPY, emailBody, emailBody.getCreateUserInfo(),arrIds);
        arrIds = sendEmailByReceiveIds(emailBody.getSecretToId(),
                EmailConst.EMAIL_TO_TYPE_SECRET, emailBody, emailBody.getCreateUserInfo(),arrIds);

        // 发送完成，修改发送状态
        Timestamp now = new Timestamp(System.currentTimeMillis());
        emailBody.setSendStatus(EmailConst.EMAIL_SENDED);
        emailBody.setSendTime(now);
        emailBody.setLastUpdateTime(now);
        this.emailBodyDao.saveOrUpdate(emailBody);
    }


    
    /**
     * 获取前后没有逗号的收件人ID或者Name集合
     *
     * @param str
     * @return str
     */
    private String getNoSepStr(String str) {
        String result = null;
        if (StringUtils.isNotEmpty(str)) {
            if (str.startsWith(EmailConst.EMAIL_TO_SEPARATOR)) {
                result = str.substring(1);
            }
            if (str.endsWith(EmailConst.EMAIL_TO_SEPARATOR)) {
                result = str.substring(0, str.length() - 1);
            }
        } else {
            result = str;
        }
        return result;
    }

    /**
     * 发送邮件给批量人
     *
     * @param receiveIds 批量人id，逗号分割
     * @param toType     收件人类型
     * @param emailBody  邮件体
     * @param fromUser   发件人
     */
    private List<String> sendEmailByReceiveIds(String receiveIds, int toType,
                                       EmailBody emailBody, UserInfo fromUser,List<String> arrIds) {
        String noSepReceiveIds = getNoSepStr(receiveIds);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (!StringUtil.isEmpty(noSepReceiveIds)) {
            String[] ids = noSepReceiveIds.split(EmailConst.EMAIL_TO_SEPARATOR);
            for (String id : ids) {
                if (id.trim().length() == 0) {
                    continue;
                }
                if(arrIds.contains(id)){
                	continue;
                }else{
                	arrIds.add(id);
                }
                
                UserInfo toUser = this.userService.findOne(Integer.valueOf(id));
                if (toUser == null) {
                    LOGGER.error("收件人不存在，ID:" + id);
                    continue;
                }
                EmailBox defaultInBox = this.emailBoxService
                        .findDefaultBoxByUserIdAndBoxType(toUser.getUserId(),
                                EmailConst.EMAIL_BOX_TYPE_INBOX);
                if (defaultInBox == null) {
                    // 检查默认邮件箱是否存在:后期放在新建用户方法中，此处后期省略
                    this.emailBoxService.saveDefaultAfterCheckExist(toUser
                            .getUserId(),fromUser);
                    defaultInBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(toUser.getUserId(), EmailConst.EMAIL_BOX_TYPE_INBOX);
                }

                EmailTo emailTo = new EmailTo();
                emailTo.setToId(toUser.getUserId());
                emailTo.setCompanyId(fromUser.getCompanyId());
                emailTo.setCreateTime(now);

                emailTo.setCreateUserInfo(fromUser);
                emailTo.setCreateUserId(fromUser.getUserId());

                emailTo.setEmailBody(emailBody);
                emailTo.setEmailBodyId(emailBody.getId());

                emailTo.setEmailBox(defaultInBox);
                emailTo.setEmailBoxId(defaultInBox.getId());

                emailTo.setEmailToStatus(EmailConst.EMAIL_SENDED);
                emailTo.setLastUpdateTime(now);

                emailTo.setLastUpdateUserInfo(fromUser);
                emailTo.setLastUpdateUserId(fromUser.getUserId());

                emailTo.setToType(toType);

                emailTo.setEmailToStatus(EmailConst.EMAIL_RECEIVED);
                this.emailToDao.saveOrUpdate(emailTo);

                //检查并发送事物提醒
                sendRemindToUser(emailTo);
                
            }
            
            
        }
        
        return arrIds;
    }
    
    
    
    
    /**
     * 发送指定id的邮件
     *
     * @param emailBodyId 邮件体ID
     */
    @Override
    public void saveAndSend(int emailBodyId) {
        saveAndSend(this.emailBodyDao.findById(emailBodyId));
    }

    /**
     * 更新邮件体（草稿和已发送的未读邮件）
     *
     * @param emailBody 邮件体对象
     */
    @Override
    public void update(EmailBody emailBody) {
        checkReceiveIds(emailBody);
        checkSubject(emailBody);
        this.emailBodyDao.saveOrUpdate(emailBody);
    }

    /**
     * 更新收件箱邮件的星级
     *
     * @param emailToId 收件箱邮件ID
     * @param markLevel 要设置的级别标识
     */
    public void updateEmailToMarkLevel(int emailToId, int markLevel) {

        EmailTo emailTo = this.emailToDao.findById(emailToId);
        if (emailTo != null && emailTo.getMarkLevel() != markLevel) {
            emailTo.setMarkLevel(markLevel);
            this.updateEmailTo(emailTo);
        }
    }

    /**
     * 更新并发送邮件
     *
     * @param emailBody 邮件体
     */
    @Override
    public void updateAndSend(EmailBody emailBody) {
        checkReceiveIds(emailBody);
        checkSubject(emailBody);
        UserInfo user = TransportUser.get();
        List<String> arrIds = new ArrayList<String>();
        // 发送
        arrIds = sendEmailByReceiveIds(emailBody.getToId(), EmailConst.EMAIL_TO_TYPE_TO,
                emailBody, user,arrIds);
        arrIds = sendEmailByReceiveIds(emailBody.getCopyToId(),
                EmailConst.EMAIL_TO_TYPE_COPY, emailBody, user,arrIds);
        arrIds = sendEmailByReceiveIds(emailBody.getSecretToId(),
                EmailConst.EMAIL_TO_TYPE_SECRET, emailBody, user,arrIds);

        // 发送完成，修改发送状态
        Timestamp now = new Timestamp(System.currentTimeMillis());
        emailBody.setSendStatus(EmailConst.EMAIL_SENDED);
        emailBody.setSendTime(now);
        this.emailBodyDao.saveOrUpdate(emailBody);
    }

    /**
     * 发送草稿箱邮件
     *
     * @param emailBodyId 邮件体id
     */
    public boolean updateAndSendDraft(int emailBodyId) {

        EmailBody emailBody = this.emailBodyDao.findById(emailBodyId);
        if (StringUtils.isEmpty(emailBody.getToId()) && StringUtils.isEmpty(emailBody.getCopyToId()) && StringUtils.isEmpty(emailBody.getSecretToId())) {
            LOGGER.info("邮件：" + emailBody.getSubject() + " 没有指定任何收件人，发送失败");
            return false;
        }
        updateAndSend(emailBody);
        return true;
    }

    /**
     * 更新邮件接受信息（收件箱邮件） 星级、查看状态、移动邮件箱、恢复删除
     *
     * @param emailTo 接收的邮件
     */
    @Override
    public void updateEmailTo(EmailTo emailTo) {
        this.emailToDao.saveOrUpdate(emailTo);
    }

    /**
     * 将某个用户某个邮件箱中的未读邮件全部标记为已读取
     *
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 更新的条数
     */
    public int updateEmailToAllReaded(int userId, int emailBoxId) {
        return this.emailToDao.updateEmailToAllReaded(userId, emailBoxId);
    }

    /**
     * 根据用户ID和邮件箱ID查询某用户某个邮件箱中的邮件份数
     *
     * @param userId 用户ID
     * @param boxId  邮件箱ID
     * @return 邮件数量
     */
    public int countOfEmailToByBoxId(int userId, int boxId) {
        return this.emailToDao.countOfEmailToByBoxId(userId, boxId);
    }

    /**
     * 根据用户ID和发送状态查询某用户某个发送状态的发送邮件份数
     * 0:查询草稿箱邮件份数，1：查询已发送邮件箱份数
     *
     * @param userId     用户ID
     * @param sendStatus 发送状态
     * @return 邮件数量
     */
    public int countOfEmailBodyByBoxId(int userId, int sendStatus) {
        return this.emailBodyDao.countOfEmailBodyByBoxId(userId, sendStatus);
    }

    /**
     * 根据邮件体ID和用户ID查询用户读取状态
     *
     * @param emailBodyId 邮件体ID
     * @param userId      用户ID
     * @return 是否已读取，已读取返回1，未读取0
     */
    public int getReadStatusByEmailBodyIdAndUserId(int emailBodyId, int userId) {
        return emailToDao.getReadStatusByEmailBodyIdAndUserId(emailBodyId, userId);
    }

    /**
     * 删除所有接收人已经删除的邮件体
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailBodyIfToUserDeleted(int userId) {
        int count = 0;
        List<EmailBody> emailBodyList = this.findAllSendedEmailBodyByUserId(userId);
        for (EmailBody emailBody : emailBodyList) {
            boolean allowDelete = true;
            List<EmailTo> emailToList = findAllEmailToByEmailBodyId(emailBody.getId());
//            for (EmailTo emailTo : emailToList) {
//                if (!emailTo.getIsDelete().equals(CbbConst.DELETED)) {
//                    allowDelete = false;
//                    break;
//                }
//            }
            //销毁不算删除

            //为空则说明所有收件人都已经删除
            allowDelete = emailToList == null || emailToList.size() == 0;
            if (allowDelete) {
                deleteEmailBody(emailBody.getId());
                count++;
            }
        }
        saveLog("删除所有接收人已经删除的邮件体");
        return count;
    }

    /**
     * 删除所有接收人已经读取的邮件体
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailBodyIfToUserReaded(int userId) {

        int count = 0;
        List<EmailBody> emailBodyList = findAllSendedEmailBodyByUserId(userId);
        for (EmailBody emailBody : emailBodyList) {
            boolean allowDelete = true;
            List<EmailTo> emailToList = findAllEmailToByEmailBodyId(emailBody.getId());
            for (EmailTo emailTo : emailToList) {
                if (emailTo.getReadStatus().equals(EmailConst.EMAIL_NOT_READ) &&
                        emailTo.getEmailToStatus().equals(EmailConst.EMAIL_RECEIVED)) {
                    allowDelete = false;
                    break;
                }
            }
            if (allowDelete) {
                deleteEmailBody(emailBody.getId());
                count++;
            }
        }
        saveLog("删除所有接收人已经读取的邮件体");
        return count;
    }

    /**
     * 删除所有接收人未读取的邮件体
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailBodyIfToUserNotRead(int userId) {

        int count = 0;
        List<EmailBody> emailBodyList = findAllSendedEmailBodyByUserId(userId);
        for (EmailBody emailBody : emailBodyList) {
            boolean allowDelete = true;
            List<EmailTo> emailToList = findAllEmailToByEmailBodyId(emailBody.getId());
            for (EmailTo emailTo : emailToList) {
                if (emailTo.getReadStatus().equals(EmailConst.EMAIL_READED)) {
                    allowDelete = false;
                } else {
                    this.updateEmailToForTakeBack(emailTo.getId());
                }
            }
            if (allowDelete) {
                deleteEmailBody(emailBody.getId());
                count++;
            }
        }
        saveLog("删除所有接收人未读取的邮件体");
        return count;
    }

    /**
     * 删除所有废纸篓中的邮件
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailToWastebasket(int userId) {
        saveLog("删除所有废纸篓中的邮件");
        return emailToDao.deleteEmailToWastebasket(userId);
    }

    /**
     * 获取某个用户的所有邮件体，不带翻页
     *
     * @param userId 用户ID
     * @return 用户的所有邮件
     */
    public List<EmailBody> findAllEmailBodyByUserId(int userId) {
        return this.emailBodyDao.findAllEmailBodyByUserId(userId);
    }

    /**
     * 获取某个用户的最近邮件联系人
     *
     * @param userId 用户ID
     * @param count  获取的最近联系人最多人数
     * @return 最近联系人列表
     */
    @Transactional(readOnly=true)
    public List<UserInfo> findRecentContacts(int userId, int count) {
        List<UserInfo> list = new LinkedList<UserInfo>();
        List<Integer> toIdsList = this.emailToDao.findRecentContactIds(userId, count);
        StringBuffer sb = new StringBuffer();
        for (Integer toId : toIdsList) {
        	sb.append(toId+",");
        }
        if(sb.toString().length()>0){
        	list = userService.findUsersByIds(sb.toString().substring(0, sb.toString().length()-1));
        }
        return list;
    }

    /**
     * 根据高级搜索信息搜索收件信息
     *
     * @param page          翻页信息
     * @param emailSearchVo 搜索VO
     * @return 数据信息带翻页
     */
    public Page<EmailTo> findAllReceiveEmailBySearchVO(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception {
        return this.emailToDao.findAllReceiveEmailBySearchVO(page, userId, emailSearchVo);
    }

    /**
     * 收回一封邮件
     *
     * @param emailToId 邮件ID
     * @return 是否收回成功，失败原因：收件人已经读取
     */
    public boolean updateEmailToForTakeBack(int emailToId) {
        EmailTo emailTo = emailToDao.findById(emailToId);
        if (emailTo.getReadStatus().equals(EmailConst.EMAIL_NOT_READ)) {
            emailTo.setEmailToStatus(EmailConst.EMAIL_CANCEL_SEND);
            updateEmailTo(emailTo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 发送事物提醒
     *
     * @param emailTo 邮件接收信息
     */
    public void checkAndSendAffairsForSend(EmailTo emailTo) {

        EmailBody emailBody = emailTo.getEmailBody();
        String remindUserIds = getRemindsUserIds(emailBody);//获得要提醒的人员的ids
        UserInfo fromUser = emailTo.getCreateUserInfo();

        
//        String sendType = affairSettingService.findDefaultByCode("nbyj");
        ReminderInfo reminderInfo = new ReminderInfo();
        reminderInfo.setSendType(emailBody.getSendType());
        // 提醒内容
        StringBuilder contentSB = new StringBuilder();
        contentSB.append("请查看");
        contentSB.append(fromUser.getUserName());
        contentSB.append("给您发的邮件，主题为:");
        contentSB.append(emailBody.getSubject());
        reminderInfo.setAffairContent(contentSB.toString());
        // 设置提醒对应URL
        String emailDetailURL = "/logined/email/emailAffairsDetail.action?emailToId=" + emailTo.getId();
        String mainPageURL = "/logined/email/emailMainPage.action?redirectURL=" + emailDetailURL;
        reminderInfo.setAffairUrl(mainPageURL);
        reminderInfo.setToids(remindUserIds);
        reminderInfo.setFromUserInfo(fromUser);
        reminderInfo.setModuleName(CbbConst.AFFAIRS_TYPE_EMAIL);
        reminderInfo.setSmsContent(contentSB.toString());
        reminderInfo.setRecordId(emailTo.getId()+"");
        reminderInfo.setPushSubjcet(contentSB.toString());
        reminderInfo.setPushContent(contentSB.toString());
        reminderInfo.setSmsContent(contentSB.toString());
        
		try {
			affairsBodyService.sendReminder(reminderInfo);
		} catch (HttpException e) {					
			e.printStackTrace();
		} catch (IOException e) {					
			e.printStackTrace();
		}
        
    }
    
    /**
     * 功能： 发送事务提醒给指定人
     * @param emailTo
     */
    private void sendRemindToUser(EmailTo emailTo){

        EmailBody emailBody = emailTo.getEmailBody();
        UserInfo fromUser = emailTo.getCreateUserInfo();

        
//        String sendType = affairSettingService.findDefaultByCode("nbyj");
        ReminderInfo reminderInfo = new ReminderInfo();
        reminderInfo.setSendType(emailBody.getSendType());
        // 提醒内容
        StringBuilder contentSB = new StringBuilder();
        contentSB.append("请查看");
        contentSB.append(fromUser.getUserName());
        contentSB.append("给您发的邮件，主题为:");
        contentSB.append(emailBody.getSubject());
        reminderInfo.setAffairContent(contentSB.toString());
        // 设置提醒对应URL
        String emailDetailURL = "/logined/email/emailAffairsDetail.action?emailToId=" + emailTo.getId();
        String mainPageURL = "/logined/email/emailMainPage.action?redirectURL=" + emailDetailURL;
        reminderInfo.setAffairUrl(mainPageURL);
        reminderInfo.setToids(emailTo.getToId()+"");
        reminderInfo.setFromUserInfo(fromUser);
        reminderInfo.setModuleName(CbbConst.AFFAIRS_TYPE_EMAIL);
        reminderInfo.setSmsContent(contentSB.toString());
        reminderInfo.setRecordId(emailTo.getId()+"");
        reminderInfo.setPushSubjcet(contentSB.toString());
        reminderInfo.setPushContent(contentSB.toString());
        reminderInfo.setSmsContent(contentSB.toString());
        
		try {
			affairsBodyService.sendReminder(reminderInfo);
		} catch (HttpException e) {					
			e.printStackTrace();
		} catch (IOException e) {					
			e.printStackTrace();
		}
        
    }
    
    /**
     * 获得要提醒的人员的ids
     */
    private String getRemindsUserIds(EmailBody emailBody){
    	String toIds = emailBody.getToId();
    	String copyToIds = emailBody.getCopyToId();
    	String secretToIdds = emailBody.getSecretToId();
    	List<String> list = new ArrayList<String>();
    	if(toIds!=null&&toIds.length()>0){
    		String[] toIdArr = toIds.split(",");
    		if(toIdArr.length>0){
    			for(int i=0;i<toIdArr.length;i++){
    				if(!list.contains(toIdArr[i])){
    					list.add(toIdArr[i]);
    				}
    			}
    		}
    	}
    	
    	if(copyToIds!=null&&copyToIds.length()>0){
    		String[] copyToIdArr = copyToIds.split(",");
    		if(copyToIdArr.length>0){
    			for(int i=0;i<copyToIdArr.length;i++){
    				if(!list.contains(copyToIdArr[i])){
    					list.add(copyToIdArr[i]);
    				}
    			}
    		}
    	}
    	
    	if(secretToIdds!=null&&secretToIdds.length()>0){
    		String[] secretToIdArr = secretToIdds.split(",");
    		if(secretToIdArr.length>0){
    			for(int i=0;i<secretToIdArr.length;i++){
    				if(!list.contains(secretToIdArr[i])){
    					list.add(secretToIdArr[i]);
    				}
    			}
    		}
    	}
    	StringBuffer sb = new StringBuffer();
    	if(list.size()>0){
    		for(String id:list){
    			sb.append(id+",");
    		}
    	}
    	return sb.toString();
    }

    /**
     * 检查并发送邮件阅读回执
     *
     * @param emailTo 邮件接收信息
     */
    public void checkAndSendAffairsForRead(EmailTo emailTo) {
        EmailBody emailBody = emailTo.getEmailBody();
        if (!emailBody.getNeedReceipt().equals(EmailConst.EMAIL_NEED_RECEIPT)) {
            return;
        }
        UserInfo fromUser = emailTo.getLastUpdateUserInfo();
//        AffairsBody affairsBody = new AffairsBody();
//        affairsBody.setCompyId(fromUser.getCompanyId());
//        Timestamp createTime = new Timestamp(System.currentTimeMillis());
//        affairsBody.setCreateTime(createTime);
//        affairsBody.setCreateUserInfo(fromUser);
//        affairsBody.setIsDelete(CbbConst.NOT_DELETE);
//        affairsBody.setFromUserInfo(fromUser);
//
//        // 设置提醒人员 以,分割
//        affairsBody.setToIds(String.valueOf(emailBody.getCreateUserInfo().getUserId()));
//        // 模块名
//        affairsBody.setSmsType(CbbConst.AFFAIRS_TYPE_EMAIL);

        // 提醒内容
//        StringBuilder contentSB = new StringBuilder();
//        contentSB.append(fromUser.getUserName());
//        contentSB.append("于");
//        contentSB.append(DateUtils.date2LongStr(emailTo.getReadTime()));
//        contentSB.append("阅读了您发的邮件，主题为:");
//        contentSB.append(emailBody.getSubject());
//        affairsBody.setContentInfo(contentSB.toString());
//        affairsBody.setSendTime(createTime);
        // 设置查看的URL
//        String emailDetailURL = "/logined/email/emailDetail.action?emailBodyId=" + emailBody.getId();
//        String mainPageURL = "/logined/email/emailMainPage.action?redirectURL=" + emailDetailURL;
//        affairsBody.setRemindUrl(mainPageURL);
//        this.affairsBodyService.saveOrUpdate(affairsBody);
        
        
        String sendType = affairSettingService.findDefaultByCode("nbyj");
        ReminderInfo reminderInfo = new ReminderInfo();
        reminderInfo.setSendType(sendType);
        // 提醒内容
        StringBuilder contentSB = new StringBuilder();
        contentSB.append(fromUser.getUserName());
        contentSB.append("于");
        contentSB.append(DateUtils.date2LongStr(emailTo.getReadTime()));
        contentSB.append("阅读了您发的邮件，主题为:");
        contentSB.append(emailBody.getSubject());
        reminderInfo.setAffairContent(contentSB.toString());
        // 设置提醒对应URL
        String emailDetailURL = "/logined/email/emailAffairsDetail.action?emailBodyId=" + emailBody.getId();
      String mainPageURL = "/logined/email/emailMainPage.action?redirectURL=" + emailDetailURL;
        reminderInfo.setAffairUrl(mainPageURL);
        reminderInfo.setToids(String.valueOf(emailBody.getCreateUserInfo().getUserId()));
        reminderInfo.setFromUserInfo(fromUser);
        reminderInfo.setModuleName(CbbConst.AFFAIRS_TYPE_EMAIL);
        reminderInfo.setSmsContent(contentSB.toString());
        reminderInfo.setRecordId(emailTo.getId()+"");
        reminderInfo.setPushSubjcet(contentSB.toString());
		try {
			affairsBodyService.sendReminder(reminderInfo);
		} catch (HttpException e) {					
			e.printStackTrace();
		} catch (IOException e) {					
			e.printStackTrace();
		}
        
    }

    /**
     * 保存删除操作的日志
     *
     * @param remark 详情
     */
    private void saveLog(String remark) {
        Log log = new Log();
        UserInfo loginUser = TransportUser.get();
        log.setInsertTime(new Date());
        log.setIp(TransportUser.get().getIp());
        log.setIsDelete(0);
        log.setLogType(LogType.LOG_EMAIL_DELETE);
        log.setRemark(remark);
        log.setModuleName("电子邮件");
        log.setUserId(loginUser.getUserId());
        log.setUserName(loginUser.getUserName());
        log.setCompanyId(loginUser.getCompanyId());
        log.setType(0);//手动添加系统日志
        this.logService.saveOrUpdate(log);
    }


    /**
     * 获取所有收取的邮件，不区分邮件箱，为移动设备使用
     *
     * @param page   分页信息
     * @param userId 用户ID
     * @return 分页信息
     */
    public Page<EmailTo> findAllInBoxEmailsForMobile(Pageable page, int userId) {
        return this.emailToDao.findAllInBoxEmailsForMobile(page, userId);
    }

}
