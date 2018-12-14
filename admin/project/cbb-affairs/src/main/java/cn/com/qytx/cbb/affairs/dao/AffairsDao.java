package cn.com.qytx.cbb.affairs.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.service.MessageConst;
import cn.com.qytx.cbb.affairs.vo.AffairsVo;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.dao.ClearParamAfterMethod;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

@Component
public class AffairsDao extends BaseDao<Affairs,Integer> implements Serializable{
	
	 public Page<Affairs> findPageByUserId(Pageable pageable, Integer id, Integer remindFlag)
	    {
	        if (null != remindFlag && MessageConst.RECEIVED == remindFlag){
	            String hql = " toUserInfo.userId = ?1 and remindFlag = ?2 and isDelete = ?3 and remindTime <= ?4";
	            return super.findAll(hql, pageable, id, MessageConst.READED, CbbConst.NOT_DELETE,
	                    new Timestamp(System.currentTimeMillis()));
	        }else{
	            String hql = " toUserInfo.userId = ?1 and (remindFlag = ?2 or remindFlag = ?3) and isDelete = ?4 and remindTime <= ?5";
	            return super.findAll(hql, pageable, id, remindFlag, MessageConst.RECEIVED, CbbConst.NOT_DELETE,
	                    new Timestamp(System.currentTimeMillis()));
	        }
	        
	    }

	    /**
	     * 分页获取发送的事务提醒列表
	     * @param page 分页信息
	     * @param id 用户Id
	     * @return Page<Affairs>
	     */
	    public Page<Affairs> findSendPageByUserId(Pageable pageable, Integer id)
	    {
	        String hql = " affairsBody.fromUserInfo.userId = ?1 and isDelete = ?2 ";
	        return super.findAll(hql, pageable, id, CbbConst.NOT_DELETE);
	    }

	    /**
	     * 根据Id删除事务提醒信息
	     * @param addressId 事务信息Id
	     */
	    @ClearParamAfterMethod
	    public void deleteById(Integer affairsId)
	    {
	        super.executeQuery("update Affairs set isDelete = ?1  where isDelete = ?2  and id = ?3",
	                CbbConst.DELETED, CbbConst.NOT_DELETE, affairsId);
	    }

	    /**
	     * 根据Id设置事务提醒信息状态
	     * @param affairId 事务Id
	     * @param remindFlag 状态
	     */
	    @ClearParamAfterMethod
	    public void updateReadedAffairs(Integer affairId, Integer remindFlag)
	    {
	        super.executeQuery("update Affairs set remindFlag = ?1  where isDelete = ?2  and id = ?3",
	                remindFlag, CbbConst.NOT_DELETE, affairId);
	    }
	    

	    /**
	     * 功能：批量事务提醒为已接受
	     * @param userId 用户
	     * @param remainFilg 更新状态
	     */
	    @ClearParamAfterMethod
	    public void updateReceivedAffairs(Integer userId, Integer remindFlag)
	    {
	        super.executeQuery("update Affairs set remindFlag = ?1  where isDelete = ?2  and toUserInfo.userId = ?3 and remindFlag = ?4 ",
	                remindFlag, CbbConst.NOT_DELETE, userId, MessageConst.SENDED);
	    }

	    /**
	     * 删除所有已读信息
	     * @param userId userId
	     */
	    @ClearParamAfterMethod
	    public void deleteAllReaded(Integer userId)
	    {
	        super.executeQuery(
	                "update Affairs set remindFlag = ?1  where isDelete = ?2 and toUserInfo.userId = ?3 and remindFlag = ?4",
	                MessageConst.DELETED, CbbConst.NOT_DELETE, userId, MessageConst.READED);
	    }

	    /**
	     * 删除所有未读信息
	     * @param userId userId
	     */
	    @ClearParamAfterMethod
	    public void deleteAllUnReaded(Integer userId)
	    {
	        super.executeQuery(
	                "update Affairs set isDelete = ?1  where isDelete = ?2 and (remindFlag = ?3 or remindFlag = ?4) and toUserInfo.userId = ?5",
	                MessageConst.DELETED, CbbConst.NOT_DELETE, MessageConst.RECEIVED, MessageConst.SENDED, userId);
	    }
	    
	    /**
	     * 删除所有信息
	     * @param userId userId
	     */
	    @ClearParamAfterMethod
	    public void deleteAllAffairs(Integer userId)
	    {
	        super.executeQuery(
	                "update Affairs set remindFlag = ?1  where isDelete = ?2 and toUserInfo.userId = ?3",
	                MessageConst.DELETED, CbbConst.NOT_DELETE, userId);
	    }

	    /**
	     * 设置所有已接受消息为已读
	     * @param userId userId
	     */
	    @ClearParamAfterMethod
	    public void updateAllReaded(Integer userId)
	    {
	        super.executeQuery(
	                "update Affairs set remindFlag = ?1  where isDelete = ?2 and toUserInfo.userId = ?3 and (remindFlag = ?4 or remindFlag = ?5)",
	                MessageConst.READED, CbbConst.NOT_DELETE, userId, MessageConst.RECEIVED, MessageConst.SENDED);
	    }

	    /**
	     * 删除已提醒收信人提醒
	     * @param userId 发送人Id
	     */
	    @SuppressWarnings("unchecked")
	    @ClearParamAfterMethod
	    public void deleteAllSendReaded(Integer userId)
	    {
	        String hql = " isDelete = ?1 and affairsBody.fromUserInfo.userId = ?2 and remindFlag = ?3";
	        List<Affairs> list = super.findAll(hql, CbbConst.NOT_DELETE, userId, MessageConst.READED);
	        if (null != list)
	        {
	            for (Affairs affairs : list)
	            {
	                affairs.setIsDelete(CbbConst.DELETED);
	                this.saveOrUpdate(affairs);
	            }
	        }
	    }

	    /**
	     * 删除所有已发送的事务提醒
	     * @param userId 发送人Id
	     */
	    @SuppressWarnings("unchecked")
	    public void deleteAllSendAffairs(Integer userId)
	    {
	        String hql = "  isDelete = ?1 and affairsBody.fromUserInfo.userId = ?2";
	        List<Affairs> list = super.findAll(hql, CbbConst.NOT_DELETE, userId);
	        if (null != list)
	        {
	            deleteAffairsList(list);
	        }
	    }

	    /**
	     * 获取Id集合
	     * @return List<Integer>
	     */
	    private String getIdList(String ids)
	    {
	        StringBuffer sb = new StringBuffer();
	        if (StringUtils.isEmpty(ids))
	        {
	            return null;
	        }
	        String[] idArr = ids.split(CbbConst.USERID_SEGMENTATION);
	        for (String id : idArr)
	        {
	            if (!StringUtils.isEmpty(id))
	            {
	                sb.append(id + ",");
	            }
	        }
	        return sb.toString();
	    }

	    /**
	     * 根据VO查询所有消息
	     * @param affairsVo affairsVo
	     * @return List<Affairs>
	     */
	    @SuppressWarnings("unchecked")
	    public List<Affairs> getAllAffairsByVo(AffairsVo vo)
	    {
	        StringBuilder hql = new StringBuilder();
	        hql.append(" x.isDelete = " + CbbConst.NOT_DELETE );
	        if (null != vo)
	        {
	            String limitHql = getLimitHql(vo);
	            if (!StringUtils.isEmpty(limitHql))
	            {
	                hql.append(limitHql);
	                return super.findAll(hql.toString());
	            }
	        }
	        return super.findAll(hql.toString());
	    }

	    /**
	     * 获取所有事务提醒列表
	     * @param userId 用户Id
	     * @param remindFlag 提醒状态
	     * @return List<Affairs>
	     */
	    @SuppressWarnings("unchecked")
	    public List<Affairs> findAllByUserId(Integer userId, Integer remindFlag)
	    {
	        String hql = " toUserInfo.userId = ?1 and remindFlag = ?2 and isDelete = ?3 and remindTime <= ?4 ";
	        return super.findAll(hql, userId, remindFlag, CbbConst.NOT_DELETE,
	                new Timestamp(System.currentTimeMillis()));
	    }
	    
	    /**
	     * 获取所有未阅的提醒列表
	     * @param userId 用户Id
	     * @return List<Affairs>
	     */
	    @SuppressWarnings("unchecked")
	    public List<Affairs> findUnReadByUserId(Integer userId)
	    {
	        String hql = " toUserInfo.userId = ?1 and (remindFlag = ?2 or remindFlag = ?3) and isDelete = ?4 and remindTime <= ?5 ";
	        return super.findAll(hql, userId, MessageConst.SENDED, MessageConst.RECEIVED, CbbConst.NOT_DELETE,
	                new Timestamp(System.currentTimeMillis()));
	    }
	    
	    private String getLimitHql(AffairsVo vo)
	    {
	        StringBuilder hql = new StringBuilder();
	        
	        // 用户类型
	        Integer userType = vo.getUserType();
	        if (null != userType)
	        {
	            if (MessageConst.USERTYPE_SEND == userType)
	            {
	                // 本人作为接收人 发信人为vo.userIds的
	                hql.append(" and remindFlag != " + MessageConst.DELETED);
	                hql.append(" and a.toUserInfo.userId = "+vo.getUserinfo().getUserId()+"");
	                String idList = getIdList(vo.getUserIds());
	                if (!StringUtils.isEmpty(idList))
	                {
	                    hql.append(" and a.affairsBody.fromUserInfo.userId in ("
	                            + idList.substring(0, idList.length() - 1) + ")");
	                }
	            }
	            else if (MessageConst.USERTYPE_RECEIVE == userType)
	            {
	                // 查询本人发送的,接收人为vo.userIds的
	                hql.append(" and a.affairsBody.fromUserInfo.userId = "+vo.getUserinfo().getUserId()+"");
	                String idList = getIdList(vo.getUserIds());
	                if (!StringUtils.isEmpty(idList))
	                {
	                    hql.append(" and a.toUserInfo.userId in ("
	                            + idList.substring(0, idList.length() - 1) + ")");
	                }
	            }
	        }

	        // 提醒类型
	        String smsType = vo.getSmsType();
	        if (!StringUtils.isEmpty(smsType) && !smsType.equals(CbbConst.SEARCH_ALL + ""))
	        {
	            hql.append(" and a.affairsBody.smsType = '"+smsType+"'");
	        }

	        // 查询开始时间
	        String startTime = vo.getStartTime();
	        if (!StringUtils.isEmpty(startTime))
	        {
	            hql.append(" and a.remindTime >= '"+DateTimeUtil.stringToTimestamp(startTime, CbbConst.TIME_FORMAT_STR)+"'");
	        }

	        // 查询结束时间
	        String endTime = vo.getEndTime();
	        if (!StringUtils.isEmpty(endTime))
	        {
	            hql.append(" and a.remindTime <= '"+DateTimeUtil.stringToTimestamp(endTime, CbbConst.TIME_FORMAT_STR)+"'");
	        }
	        hql.append(" and a.remindTime <= '"+new Timestamp(System.currentTimeMillis())+"'");

	        // 内容
	        String contentInfo = vo.getContentInfo();
	        if (!StringUtils.isEmpty(contentInfo))
	        {
	            hql.append(" and a.affairsBody.contentInfo like '%"+contentInfo+"%'");
	        }

	        // 排序字段和排序方式
	        String sortFiled = vo.getSortFiled();
	        String sortType = vo.getSortType();
	        if (!StringUtils.isEmpty(sortFiled) && !StringUtils.isEmpty(sortType))
	        {
	            if ("1".equals(sortFiled))
	            {
	                // 类型
	                hql.append(" order by a.affairsBody.smsType " + sortType);
	            }
	            else if ("2".equals(sortFiled))
	            {
	                // 内容
	                hql.append(" order by a.affairsBody.contentInfo " + sortType);
	            }
	            else if ("3".equals(sortFiled))
	            {
	                // 发信人/收信人
	                if (MessageConst.USERTYPE_SEND == userType)
	                {
	                    // 发送人
	                    hql.append(" order by a.affairsBody.fromUserInfo.userName " + sortType);
	                }
	                else if (MessageConst.USERTYPE_RECEIVE == userType)
	                {
	                    // 接收人姓名
	                    hql.append(" order by a.toUserInfo.userName " + sortType);
	                }
	            }
	            else if ("4".equals(sortFiled))
	            {
	                // 发送时间
	                hql.append(" order by a.remindTime " + sortType);
	            }
	        }
	        return hql.toString();
	    }
	    
	    /**
	     * 功能：删除事务提醒List
	     * @param affairList
	     */
	    @ClearParamAfterMethod
	    public void deleteAffairsList(List<Affairs> affairList)
	    {
	        if (null != affairList && !affairList.isEmpty())
	        {
	            StringBuffer sb = new StringBuffer();
	            for(Affairs affairs : affairList)
	            {
	                sb.append(affairs.getId() + ",");
	            }
	            
	            super.executeQuery("update Affairs set isDelete = ?1  where isDelete = ?2  and id in ("
	                    + sb.substring(0, sb.length()-1) + ")",
	                    CbbConst.DELETED, CbbConst.NOT_DELETE);
	        }
	    }
	    
	    /**
	     * 删除收信人已删除提醒
	     * @param userId 发送人Id
	     */
	    @SuppressWarnings("unchecked")
	    public void deleteToUserDeleted(Integer userId)
	    {
	        String hql = " isDelete = ?1 and affairsBody.fromUserInfo.userId = ?2 and remindFlag = ?3";
	        List<Affairs> list = super.findAll(hql, CbbConst.NOT_DELETE, userId, MessageConst.DELETED);
	        if (null != list)
	        {
	            deleteAffairsList(list);
	        }
	    }
	    
	    /**
	     * 功能：根据模块名称更新此模块提醒为已读
	     * @param userId 用户ID
	     * @param moduleName 模块名称
	     */
	    @SuppressWarnings("unchecked")
	    public void updateModuleReaded(Integer userId, String moduleName)
	    {
	        // 获取所有此模块未阅读提醒
	        String hql = " isDelete = ?1 and affairsBody.smsType = ?2 and toUserInfo.userId = ?3 and (remindFlag = ?4 or remindFlag = ?5 )";
	        List<Affairs> list = super.findAll(hql, CbbConst.NOT_DELETE, moduleName, userId, MessageConst.SENDED, MessageConst.RECEIVED);
	        
	        // 更新提醒状态为已阅
	        if (null != list)
	        {
	            for (Affairs affairs : list)
	            {
	                affairs.setRemindFlag(MessageConst.READED);
	                super.saveOrUpdate(affairs);
	            }
	        }
	    }
	    
	    /**
	     * 功能：获取未读事务提醒的数量
	     * @param userId
	     * @return 未读微讯数量
	     */
	    public int getNewAffairsNum(Integer userId)
	    {
	        String hql = "isDelete = ?1 and toUserInfo.userId = ?2 and remindFlag = ?3 and remindTime <= ?4 ";
	        return super.count(hql, CbbConst.NOT_DELETE, userId,MessageConst.SENDED,  new Timestamp(System.currentTimeMillis()));
	    }
	    
	    /**
	     * 功能：获取有未接受的事务提醒的员工Id集合
	     * @return List<Integer>
	     */
	    public List<Integer> findUnReadAffairsUser()
	    {
	        String hql = "select toUserInfo.userId from Affairs where isDelete = ? and remindFlag = ? group by toUserInfo.userId";
	        List list = super.find(hql, CbbConst.NOT_DELETE, MessageConst.SENDED);
	        return list;
	    }
	    /**
	     * 删除消息体下面的消息
	     * @Title: deleteAffairsByBodyId    
	     * @param bodyId
	     */
	    public void deleteAffairsByBodyId(String bodyId){
	    	String hql="delete Affairs where affairsBody.id=? ";
	    	super.bulkDelete(hql, Integer.parseInt(bodyId));
	    }
	    
	    /**
	     * 
	     * @Title: findAffairsListByFlag   
	     * @Description: 通过发送状态得到提醒列表
	     * @param remindFlag
	     * @return
	     */
		public List<Affairs> findAffairsListByFlag(int remindFlag) {
			String hql = " isDelete = ?1 and remindFlag = ?2 and remindTime <= ?3 and (affairsBody.smsType='rwgl' or affairsBody.smsType='srgl') ";
			List<Affairs> list = super.findAll(hql, CbbConst.NOT_DELETE, remindFlag,new Timestamp(System.currentTimeMillis()));
			for (Affairs affairs : list) {
				UserInfo sendUserInfo=affairs.getToUserInfo();
				if(sendUserInfo!=null){
					int toId=sendUserInfo.getUserId();
					affairs.setToId(toId);
				}
			}
			return list;
		}

	    /**
	     * 
	     * @Title: updateAffairsFlag   
	     * @Description: 修改用户状态
	     * @param remindFlag
	     * @return
	     */
		public int updateAffairsFlag (String affairsIds,int remindFlag) {
			String hql = " update Affairs set remindFlag = ? where id in ("+affairsIds+")  ";
			return super.executeQuery(hql, remindFlag);
		}
	    /**
	     * 分页查询我的提醒（手机端） 
	     * @Title: findPageByUserId   
	     * @param pageable
	     * @param userId
	     * @return
	     */
		public Page<Affairs> findPageByUserId(Pageable pageable, Integer userId) {
			 String hql = " toUserInfo.userId = ? and isDelete = ? and remindTime <= ?  ";
	         return super.findAll(hql, pageable, userId, 0,new Timestamp(System.currentTimeMillis()));
		}
		/**
		 * 得到未读的提醒
		 * @Title: getUnReadAffairs   
		 * @param userId
		 * @return
		 */
		public int getUnReadAffairs(int userId) {
			String hql="toUserInfo.userId = ? and isDelete = ? and remindFlag=1";
			List<Affairs> list=super.findAll(hql, userId,0);
			if(list!=null){
				return list.size();
			}
			return 0;
		}
		/**
		 * 
		 * @Title: updateRedAffairsFlag   
		 * @Description: 修改红点状态
		 * @param remindFlag
		 * @return
		 */
		public int updateRedAffairsFlag(int userId, int remindFlag) {
			String hql = " update Affairs set remindFlag = ? where toUserInfo.userId = ? and remindFlag = ? and remindTime <= ?  ";
			return super.executeQuery(hql, remindFlag, userId,1,new Timestamp(System.currentTimeMillis()));
		}
		
	    
	    public void deleteAffairsByRecordIds(String moduleName,String recordIds){
			String sql="delete from tb_om_affairs where body_id in (select id from tb_om_affairs_body where record_id in ("+recordIds+") and sms_type='"+moduleName+"')";
			this.entityManager.createNativeQuery(sql).executeUpdate();
		}
}
