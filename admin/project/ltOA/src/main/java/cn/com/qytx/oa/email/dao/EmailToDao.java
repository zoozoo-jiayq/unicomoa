/**
 *
 */
package cn.com.qytx.oa.email.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.email.vo.EmailSearchVo;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.DateUtils;

/**
 * 功能:收件箱信息数据层Dao类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Component
public class EmailToDao extends BaseDao<EmailTo, Integer> implements Serializable{

	@Autowired
	EmailBoxDao emailBoxDao;
	
    /**
     * 查询单封邮件接收信息
     *
     * @param id 邮件ID
     * @return 符合该ID的未删除邮件
     */
    public EmailTo findById(int id) {

        return (EmailTo) findOne(" id=?1 and isDelete=?2", id, CbbConst.NOT_DELETE);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> findRecentContactIds(int userId, int count) {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "select x.to_id from tb_op_email_to x where x.create_user_id="+userId+" order by x.create_time desc";
		List<Object> toIdList = entityManager.createNativeQuery(sql).getResultList();
        for (Object toId : toIdList) {
        	Integer tid = Integer.parseInt(toId.toString());
            if (list.size() == count) {
                break;
            }
            if (!list.contains(tid)) {
                list.add(tid);
            }
        }
        return list;
    }

    void test() {

    }
    
    /**
     * 删除某个邮件箱所有已经读取的邮件
     *
     * @param userId 用户ID
     * @param boxId  邮件箱ID
     */
    public int deleteEmailToReaded(int userId, int boxId) {
        EmailBox wastebasketBox = emailBoxDao.findDefaultBoxByUserIdAndBoxType(userId, EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
        String hql = "update EmailTo set oldEmailBox.id = emailBox.id, emailBox.id=?,lastUpdateTime=? where isDelete=? and emailBoxId=? and readStatus=?";
        LinkedList<Object> valueList = new LinkedList<Object>();
        valueList.add(wastebasketBox.getId());
        valueList.add(new Timestamp(System.currentTimeMillis()));
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(boxId);
        valueList.add(EmailConst.EMAIL_READED);
        return this.bulkUpdate(hql, valueList.toArray());
    }
    
    /**
     * 销毁某个用户某个邮件箱中的所有邮件
     *
     * @param userId     用户Id
     * @param emailBoxId 邮件箱ID
     */
    public void destroyAllEmailTo(int userId, int emailBoxId) {
        String hql = "update EmailTo set isDelete=?,lastUpdateTime=? where isDelete=? and toId=? and emailBox.id=?";
        LinkedList<Object> valueList = new LinkedList<Object>();
        valueList.add(CbbConst.DELETED);
        valueList.add(new Timestamp(System.currentTimeMillis()));
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(userId);
        valueList.add(emailBoxId);
        this.bulkUpdate(hql, valueList.toArray());
    }
    
    /**
     * 批量移动邮件，从某个邮件箱移动到某个邮件箱
     *
     * @param userId      用户ID
     * @param fromBoxId   从哪个邮件箱id
     * @param targetBoxId 移动到哪个邮件箱id
     */
    public void moveAllEmailTo(int userId, int fromBoxId, int targetBoxId) {
        String hql = "update EmailTo set emailBox.id=? and lastUpdateTime=? where isDelete=? and toId=? and emailBoxId=?";
        LinkedList<Object> valueList = new LinkedList<Object>();
        valueList.add(targetBoxId);
        valueList.add(new Timestamp(System.currentTimeMillis()));
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(userId);
        valueList.add(fromBoxId);
        this.bulkUpdate(hql, valueList.toArray());
    }

    
	/**
	 * 获得所有未读列表
	 */
	public List<EmailTo> getAllNotReadEmail(int userId, String searchWord){
        if (StringUtils.isEmpty(searchWord)) {
            return findAllNotReadEmail(userId);
        }
        String condition = " toId=? and isDelete=? and readStatus=? and emailToStatus=? and (emailBody.subject like ? or createUserInfo.userName like ?) ";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_NOT_READ, EmailConst.EMAIL_RECEIVED, "%" + searchWord + "%", "%" + searchWord + "%"};
        return this.findAll(condition,new Sort(Direction.DESC, "createTime"), values);
	}
	
	 /**
     * 查询某个用户未读取的邮件
     * @param userId 用户ID
     * @return 查询结果
     */
    public List<EmailTo> findAllNotReadEmail(int userId) {
        String condition = " toId=? and isDelete=? and readStatus=? and emailToStatus=? ";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_NOT_READ, EmailConst.EMAIL_RECEIVED};
        return this.findAll(condition,new Sort(Direction.DESC, "createTime"), values);
    }
    

    /**
     * 搜索某个用户的某个邮件箱邮件
     *
     * @param userId     用户Id
     * @param emailBoxId 邮件箱Id
     * @param searchWord 搜索关键字
     * @return 搜索结果
     */
    public List<EmailTo> findAllReceiveEmailBySearchWord(int userId, int emailBoxId, String searchWord) {
        if (StringUtils.isEmpty(searchWord)) {
            return findAllReceiveEmailByUserId(userId, emailBoxId);
        }else{
        	searchWord = searchWord.replaceAll("%", "/%");
        	searchWord = searchWord.replaceAll("_", "/_");
        }
        String condition = " toId=? and isDelete=? and emailBox.id=? and emailToStatus=? and (emailBody.subject like ? escape '/'  or createUserInfo.userName like ?  escape '/')";
        Object[] values = {userId, CbbConst.NOT_DELETE, emailBoxId, EmailConst.EMAIL_RECEIVED, "%" + searchWord + "%", "%" + searchWord + "%"};
        return this.findAll(condition,new Sort(Direction.DESC, "createTime"),values);
    }
    
    /**
     * 获取某个用户的某个邮件箱的所有邮件
     *
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 查询结果
     */
    public List<EmailTo> findAllReceiveEmailByUserId(int userId, int emailBoxId) {
        String condition = " toId=?1 and isDelete=?2 and emailBox.id=?3 and emailToStatus=?4 ";
        Object[] values = {userId, CbbConst.NOT_DELETE, emailBoxId, EmailConst.EMAIL_RECEIVED};
        return this.findAll(condition,new Sort(Direction.DESC, "createTime"), values);
    }
    
    /**
     * 获取某个用户的某个邮件箱的所有邮件
     *
     * @param page       翻页信息
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 查询结果
     */
    public Page<EmailTo> findAllReceiveEmailByUserId(Pageable page,
                                                     int userId, int emailBoxId) {
        String condition = " toId=?1 and isDelete=?2 and emailBox.id=?3 and emailToStatus=?4 ";
        Object[] values = {userId, CbbConst.NOT_DELETE, emailBoxId, EmailConst.EMAIL_RECEIVED};
        return this.findAll(condition, page, values);
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
    public Page<EmailTo> findAllReceiveEmailBySearchWord(Pageable page,
                                                         int userId, int emailBoxId, String searchWord) {
        if (StringUtils.isEmpty(searchWord)) {
            return findAllReceiveEmailByUserId(page, userId, emailBoxId);
        }else{
        	searchWord = searchWord.replaceAll("%", "/%");
        	searchWord = searchWord.replaceAll("_", "/_");
        }
        String condition = " toId=? and isDelete=? and emailBox.id=? and emailToStatus=? and (emailBody.subject like ? escape '/'  or createUserInfo.userName like ?  escape '/') ";
        Object[] values = {userId, CbbConst.NOT_DELETE, emailBoxId, EmailConst.EMAIL_RECEIVED, "%" + searchWord + "%", "%" + searchWord + "%"};
        return this.findAll(condition, page, values);
    }
    
    /**
     * 查找某个邮件箱下的所有邮件
     *
     * @param emailBoxId 邮件箱ID
     * @return 所有邮件
     */
    public List<EmailTo> findAllReceiveEmailByBoxId(int emailBoxId) {
        String hql = " isDelete=? and emailBox.id=? and emailToStatus=?";
        return this.findAll(hql, CbbConst.NOT_DELETE, emailBoxId, EmailConst.EMAIL_RECEIVED);
    }
    
    /**
     * 查询某个用户未读取的邮件
     *
     * @param page   翻页信心
     * @param userId 用户ID
     * @return 查询结果
     */
    public Page<EmailTo> findAllNotReadEmail(Pageable page, int userId) {
        String condition = " toId=? and isDelete=? and readStatus=? and emailToStatus=? ";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_NOT_READ, EmailConst.EMAIL_RECEIVED};
        return this.findAll(condition, page, values);
    }
    
    /**
     * 获取未读取邮件的个数
     *
     * @param userId 用户Id
     * @return 未读邮件数
     */
    public int countOfNotReadEmail(int userId) {
        String condition = " toId=? and isDelete=? and readStatus=? and emailToStatus=? ";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_NOT_READ, EmailConst.EMAIL_RECEIVED};
        return this.count(condition, values);
    }
    
    /**
     * 获取未读取邮件的个数 收件箱
     * 2014.8.4
     * @param userId 用户Id
     * @return 未读邮件数
     */
    public int countOfNotReadInBox(int userId,int emailBoxId) {
    	String condition = " toId=? and isDelete=? and readStatus=? and emailToStatus=? and emailBox.id=? ";
    	Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_NOT_READ, EmailConst.EMAIL_RECEIVED ,emailBoxId};
    	return this.count(condition, values);
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
        if (StringUtils.isEmpty(searchWord)) {
            return findAllNotReadEmail(page, userId);
        }
        String condition = " toId=? and isDelete=? and readStatus=? and emailToStatus=? and (emailBody.subject like ? or createUserInfo.userName like ?)  ";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_NOT_READ, EmailConst.EMAIL_RECEIVED, "%" + searchWord + "%", "%" + searchWord + "%"};
        return this.findAll(condition, page, values);
    }
    
    /**
     * 根据邮件体查询所有发送的邮件信息
     *
     * @param emailBodyId 邮件体ID
     * @return 未删除的发送的邮件信息
     */
    public List<EmailTo> findAllEmailToByEmailBodyId(int emailBodyId) {
        String condition = " emailBody.id=? and isDelete=? and emailToStatus=? ";
        Object[] values = {emailBodyId, CbbConst.NOT_DELETE, EmailConst.EMAIL_RECEIVED};
        return (List<EmailTo>) this.findAll(condition, values);
    }
    
    /**
     * 根据邮件体查询所有发送的邮件信息-不排除已取消发送的
     *
     * @param emailBodyId 邮件体ID
     * @return 未删除的发送的邮件信息
     */
    public List<EmailTo> findAllEmailToHasCancelSendByEmailBodyId(int emailBodyId) {
        String condition = " emailBody.id=? and isDelete=?";
        Object[] values = {emailBodyId, CbbConst.NOT_DELETE};
        return (List<EmailTo>) this.findAll(condition, values);
    }

    
    /**
     * 将某个用户某个邮件箱中的未读邮件全部标记为已读取
     *
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 更新的条数
     */
    public int updateEmailToAllReaded(int userId, int emailBoxId) {
        LinkedList<Object> valueList = new LinkedList<Object>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder hql = new StringBuilder("update EmailTo set readStatus=?,readTime=?,lastUpdateTime=? where isDelete=? and toId=?");
        valueList.add(EmailConst.EMAIL_READED);
        valueList.add(now);
        valueList.add(now);
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(userId);
        if (emailBoxId != 0 && emailBoxId != -1) {
            hql.append(" and emailBox.id=?");
            valueList.add(emailBoxId);
        }
        hql.append(" and readStatus=? and emailToStatus=?");
        valueList.add(EmailConst.EMAIL_NOT_READ);
        valueList.add(EmailConst.EMAIL_RECEIVED);
        return this.bulkUpdate(hql.toString(), valueList.toArray());
    }
    
    /**
     * 根据用户ID和邮件箱ID查询某用户某个邮件箱中的邮件份数
     *
     * @param userId 用户ID
     * @param boxId  邮件箱ID
     * @return 邮件数量
     */
    public int countOfEmailToByBoxId(int userId, int boxId) {
        String hql = " toId=? and emailBox.id=? and isDelete=? and emailToStatus=? ";
        return this.count(hql, userId, boxId, CbbConst.NOT_DELETE, EmailConst.EMAIL_RECEIVED);
    }
    
    /**
     * 根据邮件体ID和用户ID查询用户读取状态
     *
     * @param emailBodyId 邮件体ID
     * @param userId      用户ID
     * @return 是否已读取，已读取返回1，未读取0
     */
    public int getReadStatusByEmailBodyIdAndUserId(int emailBodyId, int userId) {
        EmailTo emailTo = (EmailTo) this.findOne(" toId=? and emailBody.id=?", userId, emailBodyId);
        if (emailTo == null) {
            return EmailConst.EMAIL_NOT_READ;
        }
        return emailTo.getReadStatus();
    }
    
    /**
     * 删除所有废纸篓中的邮件
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailToWastebasket(int userId) {
        int count = 0;
        EmailBox wastebasketBox = this.emailBoxDao.findDefaultBoxByUserIdAndBoxType(userId, EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
        String hql = "update EmailTo set isDelete=? where toId=? and emailBox.id=? and isDelete!=? and emailToStatus=?";
        count = this.bulkUpdate(hql, CbbConst.DELETED, userId, wastebasketBox.getId(), CbbConst.DELETED, EmailConst.EMAIL_RECEIVED);
        return count;
    }
    
    /**
     * 根据高级搜索信息搜索收件信息
     *
     * @param page          翻页信息
     * @param emailSearchVo 搜索VO
     * @return 数据信息带翻页
     */
    public Page<EmailTo> findAllReceiveEmailBySearchVO(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception {

        LinkedList<Object> valueList = new LinkedList<Object>();
        StringBuilder hqlSb = new StringBuilder(" isDelete=? and emailToStatus=? and toId=? and emailBox.id=?");
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(EmailConst.EMAIL_RECEIVED);
        valueList.add(userId);
        valueList.add(emailSearchVo.getEmailBoxId());
        if (emailSearchVo.getReadStatus() != null) {
            hqlSb.append(" and readStatus=?");
            valueList.add(emailSearchVo.getReadStatus());
        }
        if (emailSearchVo.getMarkLevel() != null) {
            hqlSb.append(" and markLevel=?");
            valueList.add(emailSearchVo.getMarkLevel());
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeStart())) {
            hqlSb.append(" and createTime>=?");
            Timestamp dateStart = DateUtils.dateShortStr2DateStart(emailSearchVo.getTimeStart());
            valueList.add(dateStart);
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeEnd())) {
            hqlSb.append(" and createTime<=?");
            Timestamp dateEnd = DateUtils.dateShortStr2DateEnd(emailSearchVo.getTimeEnd());
            valueList.add(dateEnd);
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getUserIds())) {
            String[] ids = emailSearchVo.getUserIds().split(EmailConst.EMAIL_TO_SEPARATOR);
            hqlSb.append(" and (");
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) {
                    hqlSb.append(" or");
                }
                hqlSb.append(" createUserInfo.id=?");
                valueList.add(Integer.parseInt(ids[i]));
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getSubject())) {
            hqlSb.append(" and emailBody.subject like ?");
            valueList.add("%" + emailSearchVo.getSubject() + "%");
        }
        List<String> contentInfoArray = emailSearchVo.getContentInfoList();
        if (contentInfoArray != null && contentInfoArray.size() > 0) {
            hqlSb.append(" and (");
            for (int i = 0; i < contentInfoArray.size(); i++) {
                if (i > 0) {
                    hqlSb.append(" or");
                }
                hqlSb.append(" emailBody.contentInfo like ?");
                valueList.add("%" + contentInfoArray.get(i) + "%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getAttachmentName())) {
            hqlSb.append(" and emailBody.attachment like ?");
            valueList.add("%" + emailSearchVo.getAttachmentName() + "%");
        }
        return this.findAll(hqlSb.toString(),page, valueList.toArray());
    }
    
    /**
     * 获取所有收取的邮件，不区分邮件箱，为移动设备使用
     *
     * @param page   分页信息
     * @param userId 用户ID
     * @return 分页信息
     */
    public Page<EmailTo> findAllInBoxEmailsForMobile(Pageable page, int userId) {
        String condition = " toId=? and isDelete=? and emailToStatus=? ";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_RECEIVED};
        return this.findAll(condition, page, values);
    }
}
