package cn.com.qytx.oa.message.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.affairs.service.MessageConst;
import cn.com.qytx.oa.message.domain.Message;
import cn.com.qytx.oa.message.domain.MessageView;
import cn.com.qytx.oa.message.vo.MessageVo;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:微讯Dao
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Component
public class MessageDao extends BaseDao<Message, Integer> implements Serializable
{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 分页获取微讯列表
     * @param userId 接收人Id
     * @param remindFlag 消息提醒状态
     * @return Page<Affairs>
     */
    public Page<Message> findPageByUserId(Pageable page, Integer userId, Integer remindFlag)
    {
        String condition = "isDelete = ?1 and toUserInfo.userId = ?2 and remindFlag = ?3 and sendTime <= ?4";
        return super.findAll(condition, page,CbbConst.NOT_DELETE, userId, remindFlag, new Timestamp(
                System.currentTimeMillis()));
    }

    /**
     * 设置微讯状态
     * @param messageIds messageIds
     * @param remainFilg 状态
     */
    public void updateReadedMessage(Integer messageId, Integer remainFilg)
    {
        if (null != messageId && null != remainFilg){
            super.executeQuery("update Message set remindFlag = ?1  where isDelete = ?2  and id = ?3",
                    remainFilg, CbbConst.NOT_DELETE, messageId);
        }        
    }

    /**
     * 删除微讯
     * @param messageIds messageIds
     * @param remainFilg 状态
     */
    public void deleteMessageById(Integer messageId)
    {
        super.executeQuery("update Message set isDelete = ?1  where isDelete = ?2  and id = ?3",
                CbbConst.DELETED, CbbConst.NOT_DELETE, messageId);
    }

    /**
     * 分页过滤查询所有微讯信息
     * @param pageInfo pageInfo
     * @param messageVo messageVo
     * @return Page<Message>
     */
    public Page<Message> findAllMessagePageByVo(Pageable pageInfo, MessageVo vo)
    {
        StringBuilder hql = new StringBuilder();
        hql.append("x.isDelete = " + CbbConst.NOT_DELETE);
        List<Object> value = new ArrayList<Object>();
        if (null != vo)
        {
            // 接收人
            hql.append(" and (x.toUserInfo.userId = "+vo.getUserinfo().getUserId()+" or x.createUserInfo.userId = "+vo.getUserinfo().getUserId()+")");

            // 消息类型
            Integer msgType = vo.getMsgType();
            if (null != msgType && CbbConst.SEARCH_ALL != msgType)
            {
                hql.append(" and x.msgType = "+msgType+"");
            }

            // 查询开始时间
            String startTime = vo.getStartTime();
            if (!StringUtil.isEmpty(startTime))
            {
                hql.append(" and x.sendTime >= ? ");
                value.add(DateTimeUtil.stringToTimestamp(startTime, CbbConst.TIME_FORMAT_STR));
            }

            // 查询结束时间
            String endTime = vo.getEndTime();
            if (!StringUtil.isEmpty(endTime))
            {
                hql.append(" and x.sendTime <= ? ");
                value.add(DateTimeUtil.stringToTimestamp(endTime, CbbConst.TIME_FORMAT_STR));
            }
            // 不能大于系统当前时间
            hql.append(" and x.sendTime <= ? ");
            value.add(new Timestamp(System.currentTimeMillis()));

            // 内容
            String contentInfo = vo.getContentInfo();
            if (!StringUtil.isEmpty(contentInfo))
            {
                hql.append(" and x.contentInfo like '%"+contentInfo+"%'");
            }
            String ids = vo.getIds();
            if(ids!=null && !ids.equals("")){
            	hql.append(" and x.id in ("+ids+")");
            }
            
        }
        if(value.size() <= 0){
        	return super.findAll(hql.toString(),pageInfo);
        }else{
        	return super.findAll(hql.toString(),pageInfo,value.toArray());
        }
    }

    /**
     * 根据VO条件删除微讯信息
     * @param messageVo 查询条件Vo
     */
    public void deleteMessageByVo(MessageVo messageVo)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("update Message x set x.isDelete = ? where x.isDelete = " + CbbConst.NOT_DELETE);
        String limitHql = getLimitHql(messageVo);
        if (!StringUtil.isEmpty(limitHql))
        {
            hql.append(limitHql);
        }
        super.executeQuery(hql.toString(), CbbConst.DELETED);
    }

    /**
     * 根据VO条件查询微讯信息
     * @param messageVo 查询条件Vo
     */
    @SuppressWarnings("unchecked")
    public List<Message> findAllByVo(MessageVo messageVo)
    {
        StringBuffer hql = new StringBuffer();
        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE);
        String limitHql = getLimitHql(messageVo);
        if (!StringUtil.isEmpty(limitHql))
        {
            hql.append(limitHql);
            // 排序字段和排序方式
            String sortFiled = messageVo.getSortFiled();
            String sortType = messageVo.getSortType();
            if (!StringUtil.isEmpty(sortFiled) && !StringUtil.isEmpty(sortType))
            {
                if ("1".equals(sortFiled))
                {
                    // 类型
                    if (sortType.toLowerCase().equals("desc")) {
                    	return super.findAll(hql.toString(),new Sort(Direction.DESC, "cast(x.contentInfo as varchar(4000))"));
					}else {
						return super.findAll(hql.toString(),new Sort(Direction.ASC, "cast(x.contentInfo as varchar(4000))"));
					}
                    
                }
                else if ("2".equals(sortFiled))
                {
                    // 内容
                    if (sortType.toLowerCase().equals("desc")) {
                    	return super.findAll(hql.toString(),new Sort(Direction.DESC, "x.sendTime"));
					}else {
						return super.findAll(hql.toString(),new Sort(Direction.ASC, "x.sendTime"));
					}
                }

            }
            return super.findAll(hql.toString());
        }
        return super.findAll(hql.toString());
    }

    /**
     * 获取过滤sql
     * @param vo MessageVo
     * @param params 附加参数
     * @return sql语句
     */
    private String getLimitHql(MessageVo vo)
    {
        StringBuilder hql = new StringBuilder();

        // 接收人
        hql.append(" and (x.toUserInfo.userId = "+vo.getUserinfo().getUserId()+" or x.createUserInfo.userId = "+vo.getUserinfo().getUserId()+")");

        // 消息类型
        Integer msgType = vo.getMsgType();
        if (null != msgType && CbbConst.SEARCH_ALL != msgType)
        {
            hql.append(" and x.msgType = "+msgType+"");
        }

        // 查询开始时间
        String startTime = vo.getStartTime();
        if (!StringUtil.isEmpty(startTime))
        {
            hql.append(" and x.sendTime >= '"+DateTimeUtil.stringToTimestamp(startTime, CbbConst.TIME_FORMAT_STR)+"'");
        }

        // 查询结束时间
        String endTime = vo.getEndTime();
        if (!StringUtil.isEmpty(endTime))
        {
            hql.append(" and x.sendTime <= '"+DateTimeUtil.stringToTimestamp(endTime, CbbConst.TIME_FORMAT_STR)+"'");
        }
        // 不能大于系统当前时间
        hql.append(" and x.sendTime <= '"+new Timestamp(System.currentTimeMillis())+"'");

        // 内容
        String contentInfo = vo.getContentInfo();
        if (!StringUtil.isEmpty(contentInfo))
        {
            hql.append(" and x.contentInfo like '%"+contentInfo+"%'");
        }
        String ids = vo.getIds();
        if(ids!=null && !ids.equals("")){
        	hql.append(" and x.id in ("+ids+")");
        }
        
        // 排序字段和排序方式
        /*String sortFiled = vo.getSortFiled();
        String sortType = vo.getSortType();
        if (!StringUtil.isEmpty(sortFiled) && !StringUtil.isEmpty(sortType))
        {
            if ("1".equals(sortFiled))
            {
                // 类型
                hql.append(" order by cast(x.contentInfo as varchar(4000)) " + sortType);
            }
            else if ("2".equals(sortFiled))
            {
                // 内容
                hql.append(" order by x.sendTime " + sortType);
            }

        }*/
        return hql.toString();
    }

    /**
     * 获取用户1和用户2的微讯聊天纪律
     * @param page 分页信息
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @return 分页查询结果Page<Message>
     */
    public Page<Message> findPageWithUId(Pageable page, Integer userId1, Integer userId2, String order)
    {
        String hql = " ((toUserInfo.userId = ?1 and createUserInfo.userId = ?2) or"
                + "(toUserInfo.userId = ?3 and createUserInfo.userId = ?4)) and sendTime <= ?5 and isDelete = ?6";
        return super.findAll(hql, page, userId1, userId2, userId2, userId1,
                new Timestamp(System.currentTimeMillis()), CbbConst.NOT_DELETE);
    }

    /**
     * 功能：根据当前用户Id获取当前微讯聊天人信息
     * @param userId 用户Id
     * @return List<UserInfo>用户列表
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getCurrentUserId(Integer userId)
    {
        String jpql = "select distinct m1.createUserInfo.userId from Message m1 where"
                + " m1.toUserInfo.userId = ? and m1.sendTime <= ? and m1.isDelete = ? and m1.remindFlag = 0";

        // 同时设置不能大于当前时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return super.find(jpql, userId, now, CbbConst.NOT_DELETE);
    }

    /**
     * 功能：查询与当前用户的聊天记录
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @param beginTime 查询开始时间
     * @return List<Message>
     */
    public List<Message> findCurrentUserMessage(Integer userId1, Integer userId2, String beginTime)
    {
        // 按时间获取第一条未读的微讯
        String hqlFirst = " toUserInfo.userId = "+ userId2 
                +" and createUserInfo.userId = "+ userId1 
                +" and (remindFlag = "+MessageConst.SENDED
                +"  or remindFlag = "+ MessageConst.RECEIVED + " )"
                +"  and isDelete = "+ CbbConst.NOT_DELETE;
        List<Message> list = new ArrayList<Message>();
        list=findAll(hqlFirst, new Sort(Direction.ASC, "sendTime"));
        Message temp =new Message();
        if (list!=null&&list.size()>0) {
        	temp=list.get(0);
		}
        if (null == temp)
        {
            return null;
        }
        
        
        List<Object> params = new ArrayList<Object>();
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append(" ((toUserInfo.userId = ?1 and createUserInfo.userId = ?2)");
        params.add(userId1);
        params.add(userId2);
        hqlSb.append(" or (toUserInfo.userId = ?3 and createUserInfo.userId = ?4))");
        params.add(userId2);
        params.add(userId1);

        // 限制系统当前时间,且未被删除
        hqlSb.append(" and sendTime <= ?5 and isDelete = ?6 ");
        params.add(new Timestamp(System.currentTimeMillis()));
        params.add(CbbConst.NOT_DELETE);
        hqlSb.append(" and sendTime >= ?7");
        params.add(temp.getSendTime());
        if (!StringUtil.isEmpty(beginTime))
        {
            hqlSb.append(" and sendTime >= ?8");
            params.add(DateTimeUtil.stringToTimestamp(beginTime, CbbConst.TIME_FORMAT_STR));
        }
        return super.findAll(hqlSb.toString(),new Sort(Direction.ASC, "sendTime"), params.toArray());
    }
    
    /**
     * 功能：根据聊天人Id,批量更新与此人微讯为已读
     * @param userId 登录人Id
     * @param withUserId 通讯人Id
     */
    public void updateReadedByUserId(Integer userId, Integer withUserId)
    {
        StringBuffer hql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        hql.append("update Message m set m.remindFlag = ?1 where m.isDelete = ?2 and (m.remindFlag = ?3  or m.remindFlag = ?4 ) and m.toUserInfo.userId = ?5");
        
        params.add(MessageConst.READED);
        params.add(CbbConst.NOT_DELETE);
        params.add(MessageConst.SENDED);
        params.add(MessageConst.RECEIVED);
        params.add(userId);
        if (null != withUserId)
        {
            hql.append(" and m.createUserInfo.userId = ?6");
            params.add(withUserId);
        }
        super.executeQuery(hql.toString(), params.toArray());
    }
    
    /**
     * 功能：根据聊天人Id,批量删除与此人微讯
     * @param userId 登录人Id
     * @param withUserId 通讯人Id
     */
    public void deleteByUserId(Integer userId, Integer withUserId)
    {
        StringBuffer hql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        hql.append("update Message m set m.isDelete = ?1 where m.isDelete = ?2  and m.toUserInfo.userId = ?3 and m.createUserInfo.userId = ?4  and (m.remindFlag = ?5  or m.remindFlag = ?6 )");
        params.add(CbbConst.DELETED);
        params.add(CbbConst.NOT_DELETE);        
        params.add(userId);
        params.add(withUserId);
        params.add(MessageConst.SENDED);
        params.add(MessageConst.RECEIVED);
        super.executeQuery(hql.toString(), params.toArray());
    }
    
    
    /**
     * 功能：查询与当前用户的聊天记录
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @param beginTime 查询开始时间
     * @return List<Message>
     */
    public List<Message> findUserMessageByTime(Integer userId1, Integer userId2, String beginTime)
    {
        List<Object> params = new ArrayList<Object>();
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append(" ((toUserInfo.userId = ? and createUserInfo.userId = ?)");
        params.add(userId1);
        params.add(userId2);
        hqlSb.append(" or (toUserInfo.userId = ? and createUserInfo.userId = ?))");
        params.add(userId2);
        params.add(userId1);

        // 限制系统当前时间,且未被删除
        hqlSb.append(" and sendTime <= ? and isDelete = ? ");
        params.add(new Timestamp(System.currentTimeMillis()));
        params.add(CbbConst.NOT_DELETE);

        if (!"-1".equals(beginTime)){
            Timestamp beginTimeStamp =  DateTimeUtil.stringToTimestamp(beginTime, CbbConst.TIME_FORMAT_STR);
            beginTimeStamp.setTime(beginTimeStamp.getTime()+1000);
            hqlSb.append(" and sendTime >= ?");
            params.add(beginTimeStamp);
        }
        return super.findAll(hqlSb.toString(),new Sort(Direction.DESC, "sendTime"), params.toArray());
    }
    
    
    /**
     * 功能：获取有未读微讯的员工Id集合
     * @return List<Integer>
     */
    public List<Integer> findUnReadMessageUser()
    {
        String hql = "select toUserInfo.userId from Message where sendTime <= ? and isDelete = ? and (remindFlag = ? or remindFlag = ?) group by toUserInfo.userId";
        List list = super.find(hql, new Timestamp(System.currentTimeMillis()), CbbConst.NOT_DELETE, MessageConst.SENDED, MessageConst.RECEIVED);
        return list;
    }
    
    /**
     * 功能：分组查询所有微讯信息
     * @param page 分页数据
     * @param userId 当前用户ID
     * @return Page<Message>
     */
    @SuppressWarnings({ "unused", "unchecked" })
    public Page<MessageView> findAllUserMessageByPage(Pageable page, Integer userId)
    {
    	
    	
    	Query query= entityManager.createNativeQuery("select * from View_message where to_uid ="+userId+" order by max_mess desc", MessageView.class);
    	query.setFirstResult(page.getOffset());
    	query.setMaxResults(page.getPageSize());
    	int count = (Integer) entityManager.createNativeQuery("select count(*) from View_message where to_uid ="+userId+" order by max_mess desc", MessageView.class).getSingleResult();
    	
    	PageImpl pageInfo = new PageImpl(query.getResultList(), page, count);
        return pageInfo;
    }
}
