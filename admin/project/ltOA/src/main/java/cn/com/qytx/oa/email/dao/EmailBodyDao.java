/**
 *
 */
package cn.com.qytx.oa.email.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.email.domain.EmailBody;
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
 * 功能:邮件体数据层Dao类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Component
public class EmailBodyDao extends BaseDao<EmailBody, Integer> implements Serializable{


    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 查询单个未标记删除的邮件体
     *
     * @param id 邮件体ID
     * @return 符合该ID的未删除邮件体
     */
    public EmailBody findById(int id) {
        return (EmailBody) findOne(" id=?1 and isDelete=?2", id, CbbConst.NOT_DELETE);
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
        String condition = " createUserInfo.userId=?1 and isDelete=?2 and sendStatus=?3 and subject like ?4 ";
        if(searchWord==null){
        	searchWord = "";
        }
        Object[] values = {userId, CbbConst.NOT_DELETE,
                EmailConst.EMAIL_NOT_SEND,"%"+searchWord+"%"};
        return this.findAll(condition, page, values);
    }
    
    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllDraftEmailBodyByUserId(int userId) {
        String hql = "createUserInfo.userId=? and isDelete=? and sendStatus=?";
        Object[] values = {userId, CbbConst.NOT_DELETE,
                EmailConst.EMAIL_NOT_SEND};
        return this.findAll(hql, values);
    }
    
    /**
     * 根据高级搜索条件查询某个用户的已发送邮件
     *
     * @param userId        用户ID
     * @param emailSearchVo 高级搜索信息
     * @return 结果集-翻页
     * @throws Exception 日期转换异常等等
     */
    public List<EmailBody> findAllSendedEmailBodyBySearchVo(int userId, EmailSearchVo emailSearchVo) throws Exception {
        LinkedList<Object> valueList = new LinkedList<Object>();
        StringBuilder hqlSb = new StringBuilder(" isDelete=? and sendStatus=? and createUserInfo.userId=? ");
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(EmailConst.EMAIL_SENDED);
        valueList.add(userId);
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeStart())) {
            hqlSb.append(" and lastUpdateTime>=?");
            Timestamp dateStart = DateUtils.dateShortStr2DateStart(emailSearchVo.getTimeStart());
            valueList.add(dateStart);
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeEnd())) {
            hqlSb.append(" and lastUpdateTime<=?");
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
                hqlSb.append(" toId like ?");
                valueList.add("%," + ids[i] + ",%");
                hqlSb.append(" or copyToId like ?");
                valueList.add("%," + ids[i] + ",%");
                hqlSb.append(" or secretToId like ?");
                valueList.add("%," + ids[i] + ",%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getSubject())) {
            hqlSb.append(" and subject like ?");
            valueList.add("%" + emailSearchVo.getSubject() + "%");
        }
        List<String> contentInfoArray = emailSearchVo.getContentInfoList();
        if (contentInfoArray != null && contentInfoArray.size() > 0) {
            hqlSb.append(" and (");
            for (int i = 0; i < contentInfoArray.size(); i++) {
                if (i > 0) {
                    hqlSb.append(" or");
                }
                hqlSb.append(" contentInfo like ?");
                valueList.add("%" + contentInfoArray.get(i) + "%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getAttachmentName())) {
            hqlSb.append(" and attachment like ?");
            valueList.add("%" + emailSearchVo.getAttachmentName() + "%");
        }
        return this.findAll(hqlSb.toString(),new Sort(Direction.DESC, "lastUpdateTime","sendTime"),valueList.toArray());
    }
    
    
    /**
     * 查询某个用户已发送邮件
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllSendedEmailBodyByUserId(int userId,String searchWord) {
        String condition = " createUserInfo.userId=? and isDelete=? and sendStatus=? and subject like ? ";
        if(searchWord == null){
        	searchWord = "";
        }
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_SENDED,"%"+searchWord+"%"};
        return this.findAll(condition, new Sort(Direction.DESC, "sendTime"),values);
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
        LinkedList<Object> valueList = new LinkedList<Object>();
        StringBuilder hqlSb = new StringBuilder(" isDelete=? and sendStatus=? and createUserInfo.userId=? ");
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(EmailConst.EMAIL_NOT_SEND);
        valueList.add(userId);
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeStart())) {
            hqlSb.append(" and lastUpdateTime>=?");
            Timestamp dateStart = DateUtils.dateShortStr2DateStart(emailSearchVo.getTimeStart());
            valueList.add(dateStart);
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeEnd())) {
            hqlSb.append(" and lastUpdateTime<=?");
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
                hqlSb.append(" toId like ?");
                valueList.add("%," + ids[i] + ",%");
                hqlSb.append(" or copyToId like ?");
                valueList.add("%," + ids[i] + ",%");
                hqlSb.append(" or secretToId like ?");
                valueList.add("%," + ids[i] + ",%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getSubject())) {
            hqlSb.append(" and subject like ?");
            valueList.add("%" + emailSearchVo.getSubject() + "%");
        }
        List<String> contentInfoArray = emailSearchVo.getContentInfoList();
        if (contentInfoArray != null && contentInfoArray.size() > 0) {
            hqlSb.append(" and (");
            for (int i = 0; i < contentInfoArray.size(); i++) {
                if (i > 0) {
                    hqlSb.append(" or");
                }
                hqlSb.append(" contentInfo like ?");
                valueList.add("%" + contentInfoArray.get(i) + "%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getAttachmentName())) {
            hqlSb.append(" and attachment like ?");
            valueList.add("%" + emailSearchVo.getAttachmentName() + "%");
        }
        hqlSb.append(" ");
        return this.findAll(hqlSb.toString(), page, valueList.toArray());
    }
    
    /**
     * 查询某个用户已发送邮件
     *
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    public Page<EmailBody> findAllSendedEmailBodyByUserId(Pageable page,
                                                          int userId,String searchWord) {
        String condition = " createUserInfo.userId=? and isDelete=? and sendStatus=? and subject like ? ";
        if(searchWord == null){
        	searchWord = "";
        }
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_SENDED,"%"+searchWord+"%"};
        return this.findAll(condition, page, values);

    }


    /**
     * 查询某个用户已发送邮件
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllSendedEmailBodyByUserId(int userId) {
        String hql = " createUserInfo.userId=? and isDelete=? and sendStatus=?";
        Object[] values = {userId, CbbConst.NOT_DELETE, EmailConst.EMAIL_SENDED};
        return this.findAll(hql, values);
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
        LinkedList<Object> valueList = new LinkedList<Object>();
        StringBuilder hqlSb = new StringBuilder(" isDelete=? and sendStatus=? and createUserInfo.userId=? ");
        valueList.add(CbbConst.NOT_DELETE);
        valueList.add(EmailConst.EMAIL_SENDED);
        valueList.add(userId);
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeStart())) {
            hqlSb.append(" and lastUpdateTime>=?");
            Timestamp dateStart = DateUtils.dateShortStr2DateStart(emailSearchVo.getTimeStart());
            valueList.add(dateStart);
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getTimeEnd())) {
            hqlSb.append(" and lastUpdateTime<=?");
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
                hqlSb.append(" toId like ?");
                valueList.add("%," + ids[i] + ",%");
                hqlSb.append(" or copyToId like ?");
                valueList.add("%," + ids[i] + ",%");
                hqlSb.append(" or secretToId like ?");
                valueList.add("%," + ids[i] + ",%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getSubject())) {
            hqlSb.append(" and subject like ?");
            valueList.add("%" + emailSearchVo.getSubject() + "%");
        }
        List<String> contentInfoArray = emailSearchVo.getContentInfoList();
        if (contentInfoArray != null && contentInfoArray.size() > 0) {
            hqlSb.append(" and (");
            for (int i = 0; i < contentInfoArray.size(); i++) {
                if (i > 0) {
                    hqlSb.append(" or");
                }
                hqlSb.append(" contentInfo like ?");
                valueList.add("%" + contentInfoArray.get(i) + "%");
            }
            hqlSb.append(")");
        }
        if (StringUtils.isNotEmpty(emailSearchVo.getAttachmentName())) {
            hqlSb.append(" and attachment like ?");
            valueList.add("%" + emailSearchVo.getAttachmentName() + "%");
        }
        return this.findAll(hqlSb.toString(), page, valueList.toArray());
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
        String hql = " createUserInfo.userId=? and sendStatus=? and isDelete=?";
        return this.count(hql, userId, sendStatus, CbbConst.NOT_DELETE);
    }
    
    /**
     * 获取某个用户的所有邮件体，不带翻页
     *
     * @param userId 用户ID
     * @return 用户的所有邮件
     */
    public List<EmailBody> findAllEmailBodyByUserId(int userId) {
        String hql = " createUserInfo.userId=? and isDelete=?";
        return this.findAll(hql, userId, CbbConst.NOT_DELETE);
    }

}
