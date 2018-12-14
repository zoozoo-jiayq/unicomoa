package cn.com.qytx.cbb.jpush.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.jpush.domain.PushInfo;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
/**
 * 功能:pushInfoDao
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-24
 * 修改日期: 2014-6-24
 * 修改列表: 
 */
@Repository("pushInfoDao")
public class PushInfoDao extends BaseDao<PushInfo,Integer> {
	
	/**
	 * 功能：根据ids删除推送信息
	 * @param ids
	 * @return
	 */
	public int deletePushs(String data){
		String ids=data;
		if (ids.startsWith(",")) {
			ids = ids.substring(1,ids.length());
		}	
		if (ids.endsWith(",")) {
			ids = ids.substring(0,ids.length()-1);
		}
		if (ids.length()>0) {
			String  hql = "update PushInfo set isDelete=1 where pushId in ("+ids+")";
			return entityManager.createQuery(hql).executeUpdate();
		}else {
			return 0;
		}
	}
	
    /**
     * 功能：根据用户id获取推送信息
     * @param userId
     * @return
     */
    public List<PushInfo> findPushsByUserId(Integer userId){
    	return findAll(" isDelete=0 and pushId in (select pushId from PushUser where userId=? )", new Sort(new Sort.Order(Direction.DESC, "pushTime")),userId);
    }
    /**
     * 功能：根据用户id获取推送信息
     * @param userId
     * @return
     */
    public List<PushInfo> findPushsByUserId(Integer userId,Integer pushType){
    	return findAll(" isDelete=0 and pushType=? and pushId in (select pushId from PushUser where userId=? )", new Sort(new Sort.Order(Direction.DESC, "pushTime")),pushType,userId);
    }
    
    /**
     * 功能：获取分页信息
     * @return
     */
    public Page<PushInfo> findPage(Pageable pageable,Integer userId,String subject,String startTime,String endTime){
    	StringBuilder hql=new StringBuilder("select x from PushInfo x where isDelete=0 ");
		if(userId!=null&&userId!=0){
			hql.append(" and userId = "+userId );
		}
		if(subject!=null&&!subject.equals("")){
			hql.append(" and subject like '%"+subject+"%'");
		}
		if(startTime!=null&&!"".equals(startTime)){
			hql.append(" and pushTime >='"+startTime+"'");
		}
		if(endTime!=null&&!"".equals(endTime)){
			hql.append(" and pushTime <='"+endTime+"'");
		}
		hql.append(" order by pushId desc");
    	return findByPage(pageable,hql.toString());
    }
    /**
     * 功能：得到下一次推送
     * @param pushId
     * @return
     */
    public PushInfo loadNextPush(Integer pushId){
    	//判断是否有下一次推荐
		String jpqlNext=" pushId>? and pushType=1 order by pushId asc";
		PushInfo pushInfo=super.findOne(jpqlNext, pushId);
		return pushInfo;
    }
}