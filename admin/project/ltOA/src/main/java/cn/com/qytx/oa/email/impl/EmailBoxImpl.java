/**
 *
 */
package cn.com.qytx.oa.email.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.email.dao.EmailBoxDao;
import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.email.service.IEmail;
import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:邮件箱服务接口实现类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Service
@Transactional
public class EmailBoxImpl extends BaseServiceImpl<EmailBox> implements IEmailBox,Serializable {

    private static final Logger LOGGER = Logger.getLogger(EmailBoxImpl.class);

    @Resource
    private EmailBoxDao emailBoxDao;
    @Resource
    private IEmail emailService;

    /**
     * 获取某个用户所有的邮件箱
     *
     * @param userId 用户Id
     * @return 该用户的所有邮件箱
     */
    public List<EmailBox> findAllByUserId(int userId) {
        return this.emailBoxDao.findAllByUserId(userId);
    }

    /**
     * 为某个用户创建默认的几个邮件箱
     *
     * @param userId 创建的邮件箱用户Id
     */
    @Override
    public void saveDefault(int userId,UserInfo currentUser) {

        LOGGER.info("开始创建默认的几个邮件箱，用户ID:" + userId);
        // 邮件箱类型 1：默认-收件箱 2：默认-发件箱 3：默认-草稿箱 4：默认-废纸篓
        HashMap<Integer, String> defaultBox = new HashMap<Integer, String>();
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_INBOX, "收件箱");
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_OUTBOX, "已发送");
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_DRAFT, "草稿箱");
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_WASTEBASKET, "垃圾箱");
        int compyId = currentUser.getCompanyId();

        Set<Map.Entry<Integer, String>> set = defaultBox.entrySet();
        Iterator<Map.Entry<Integer, String>> ite = set.iterator();
        while (ite.hasNext())
        {
            Map.Entry<Integer, String> entry = ite.next();            
            EmailBox emailBox = new EmailBox();
            emailBox.setBoxName(entry.getValue());
            emailBox.setBoxType(entry.getKey());
            emailBox.setBoxSize(0L);
            emailBox.setCompanyId(compyId);
            emailBox.setUserId(userId);
            emailBox.setCreateTime(new Timestamp(System.currentTimeMillis()));
            emailBox.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
            emailBox.setCreateUserId(currentUser.getUserId());
            emailBox.setCreateUserInfo(currentUser);
            emailBox.setLastUpdateInfo(currentUser);
            emailBox.setLastUpdateUserId(currentUser.getUserId());
            this.emailBoxDao.saveOrUpdate(emailBox);
        }
        
        LOGGER.info("完成创建默认的几个邮件箱，用户ID:" + userId);
    }

    /**
     * 检查某人默认的系统邮件箱是否存在，不存在则创建
     *
     * @param userId 创建的邮件箱用户Id
     */
    @Override
    public void saveDefaultAfterCheckExist(int userId,UserInfo currentUser) {
        LOGGER.info("开始检查并创建默认的几个邮件箱，用户ID:" + userId);
        // 邮件箱类型 1：默认-收件箱 2：默认-发件箱 3：默认-草稿箱 4：默认-废纸篓
        HashMap<Integer, String> defaultBox = new HashMap<Integer, String>();
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_INBOX, "收件箱");
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_OUTBOX, "已发送");
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_DRAFT, "草稿箱");
        defaultBox.put(EmailConst.EMAIL_BOX_TYPE_WASTEBASKET, "垃圾箱");

        Set<Map.Entry<Integer, String>> set = defaultBox.entrySet();
        Iterator<Map.Entry<Integer, String>> ite = set.iterator();
        while (ite.hasNext())
        {
            Map.Entry<Integer, String> entry = ite.next();
            EmailBox emailBox = findDefaultBoxByUserIdAndBoxType(userId, entry.getKey());
            if (emailBox == null) {
                emailBox = new EmailBox();
                emailBox.setBoxName(entry.getValue());
                emailBox.setBoxType(entry.getKey());
                emailBox.setBoxSize(0L);
                emailBox.setCreateTime(new Timestamp(System.currentTimeMillis()));
                emailBox.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                if(currentUser!=null){
	                emailBox.setCreateUserId(currentUser.getUserId());
	                emailBox.setCreateUserInfo(currentUser);
	                emailBox.setLastUpdateInfo(currentUser);
	                emailBox.setLastUpdateUserId(currentUser.getUserId());
	                emailBox.setCompanyId(currentUser.getCompanyId());
                }
                emailBox.setPageMax(EmailConst.EMAIL_PAGE_MAX);
                emailBox.setUserId(userId);
                try{
                this.emailBoxDao.saveOrUpdate(emailBox);
                }catch(Exception e){
                	e.printStackTrace();
                }
            }
        }
        
//        
//        for (Integer boxType : defaultBox.keySet()) {
//            EmailBox emailBox = findDefaultBoxByUserIdAndBoxType(userId, boxType);
//            if (emailBox == null) {
//                emailBox = new EmailBox();
//                emailBox.setBoxName(defaultBox.get(boxType));
//                emailBox.setBoxType(boxType);
//                emailBox.setBoxSize(0L);
//                emailBox.setCompyId(compyId);
//                emailBox.setPageMax(EmailConst.EMAIL_PAGE_MAX);
//                emailBox.setUserId(userId);
//                this.commonService.setCreateUpdateInfo(emailBox);
//                this.emailBoxDao.save(emailBox);
//            }
//        }
        LOGGER.info("完成检查并创建默认的几个邮件箱，用户ID:" + userId);
    }

    /**
     * 根据用户ID和邮件箱类型查询一个系统默认邮件箱
     *
     * @param userId  用户ID
     * @param boxType 邮件箱类型
     * @return 查询出来的邮件箱对象
     */
    @Override
    public EmailBox findDefaultBoxByUserIdAndBoxType(int userId, int boxType) {
        return this.emailBoxDao.findDefaultBoxByUserIdAndBoxType(userId, boxType);
    }

    /**
     * 删除一个邮件箱
     *
     * @param id 邮件箱ID
     */
    public void delete(int id) {
        EmailBox emailBox = findById(id);
        //将删除该邮件箱中的所有邮件
        this.emailService.destroyAllEmailTo(emailBox.getUserId(), id);
        emailBox.setIsDelete(CbbConst.DELETED);
        this.emailBoxDao.saveOrUpdate(emailBox);
    }

    /**
     * 新建保存自定义的邮件箱
     *
     * @param emailBox 邮件箱对象
     */
    @Override
    public EmailBox saveOrUpdate(EmailBox emailBox) {
    	List list = new ArrayList();
    	list.add(EmailConst.EMAIL_BOX_TYPE_INBOX);
    	list.add(EmailConst.EMAIL_BOX_TYPE_OUTBOX);
    	list.add(EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
    	list.add(EmailConst.EMAIL_BOX_TYPE_DRAFT);
    	if(emailBox.getBoxType() == null || (!list.contains(emailBox.getBoxType()))){
    		emailBox.setBoxType(EmailConst.EMAIL_BOX_TYPE_DIY);
    	}
        emailBox.setBoxSize(0L);
        return this.emailBoxDao.saveOrUpdate(emailBox);
    }



    /**
     * 获取某个邮件箱的总容量
     *
     * @param emailBox 邮件箱
     * @return 容量byte
     */
    public long getBoxSize(EmailBox emailBox) {
        long size = 0;
        if (emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_OUTBOX)) {
            List<EmailBody> list = emailService.findAllSendedEmailBodyByUserId(emailBox.getUserId());
            for (EmailBody emailBody : list) {
                if (emailBody.getAttachmentSize() != null) {
                    size += emailBody.getAttachmentSize();
                }
                if (StringUtils.isNotEmpty(emailBody.getContentInfo())) {
                    size += emailBody.getContentInfo().getBytes().length;
                }
            }

        } else if (emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_DRAFT)) {
            List<EmailBody> list = emailService.findAllDraftEmailBodyByUserId(emailBox.getUserId());
            for (EmailBody emailBody : list) {
                if (emailBody.getAttachmentSize() != null) {
                    size += emailBody.getAttachmentSize();
                }
                if (StringUtils.isNotEmpty(emailBody.getContentInfo())) {
                    size += emailBody.getContentInfo().getBytes().length;
                }
            }
        } else {
            List<EmailTo> list = emailService.findAllReceiveEmailByBoxId(emailBox.getId());
            for (EmailTo emailTo : list) {
                EmailBody emailBody = emailTo.getEmailBody();
                if (emailBody.getAttachmentSize() != null) {
                    size += emailBody.getAttachmentSize();
                }
                if (StringUtils.isNotEmpty(emailBody.getContentInfo())) {
                    size += emailBody.getContentInfo().getBytes().length;
                }
            }
        }
        return size;
    }

    /**
     * 根据Vid查询单个邮件箱
     *
     * @param emailBoxId 邮件箱Id
     * @return 查询出来的邮件箱
     */
    @Override
    public EmailBox findById(int emailBoxId) {
        return this.emailBoxDao.findById(emailBoxId);
    }


    @Override
    public List<EmailBox> findAllDiyBoxByUserId(int userId) {
        return this.emailBoxDao.findAllDiyBoxByUserId(userId);
    }


    public void deleteEmailBoxByUserIds(String userIds){
    	this.emailBoxDao.deleteEmailBoxByUserIds(userIds);
    }
}
