package cn.com.qytx.cbb.jpush.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jpush.dao.PushInfoDao;
import cn.com.qytx.cbb.jpush.domain.PushInfo;
import cn.com.qytx.cbb.jpush.service.IPushInfo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;


@Service("pushInfoService")
@Transactional
public class PushInfoImpl extends BaseServiceImpl<PushInfo> implements IPushInfo{
	/*@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;*/
	@Resource(name="pushInfoDao")
	PushInfoDao pushInfoDao;
	/**
	 * 
	 * 功能：添加发布
	 * @param pushInfo
	 */
/*	public int addPushInfo(final PushInfo pushInfo){
		
		
		final String sql="insert into PushInfo(Subject,UserId,UserName,ShowContent,PushContent,PushTime,isDelete) values(?,?,?,?,?,?,?)";
		
		KeyHolder holder=new GeneratedKeyHolder(); 
		
		jdbcTemplate.update(new PreparedStatementCreator() { 
		public PreparedStatement createPreparedStatement(Connection con)  throws SQLException { 
			PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); 
			ps.setString(1, pushInfo.getSubject()); 
			ps.setInt(2, pushInfo.getUserId()); 
			ps.setString(3,pushInfo.getUserName()); 
			ps.setString(4, pushInfo.getShowContent()); 
			ps.setString(5, pushInfo.getPushContent()); 
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis())); 
			ps.setInt(7, 0); 
			return ps; 
		}
		}, holder);
		int generatedId = holder.getKey().intValue();
		return generatedId;
	}*/
	/**
	 * 
	 * 功能：编辑发布
	 * @param pushInfo
	 */
	/*public int editPushInfo(final PushInfo pushInfo){
		
		final String sql="update PushInfo set Subject=?,UserId=?,UserName=?,ShowContent=?,PushContent=?,PushTime=? where pushId=?";

		Object [] args={ pushInfo.getSubject(),pushInfo.getUserId(),pushInfo.getUserName(), pushInfo.getShowContent(),pushInfo.getPushContent(),new Timestamp(System.currentTimeMillis()),pushInfo.getPushId()};
		int ret=jdbcTemplate.update(sql, args);
	
		return ret;
	}*/
	/**
	 * 
	 * 功能：根据条件得到分页信息
	 * @param page
	 * @param userId
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<PushInfo> findPage(Pageable pageable,Integer userId,String subject,String startTime,String endTime){
		/*try {
			StringBuilder sql=new StringBuilder(" from  PushInfo where isDelete=0 ");
			if(userId!=null&&userId!=0){
				sql.append(" and UserId = "+userId );
			}
			if(subject!=null&&!subject.equals("")){
				sql.append(" and Subject like '%"+subject+"%'");
			}
			if(startTime!=null&&!"".equals(startTime)){
				sql.append(" and PushTime >='"+startTime+"'");
			}
			if(endTime!=null&&!"".equals(endTime)){
				sql.append(" and PushTime <='"+endTime+"'");
			}
            int count=getCountBySql("select count(*) "+sql.toString());
			page = findByPage(page,sql.toString(),new  PushInfoMapper(),"*","PushId","desc",count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;*/
		return pushInfoDao.findPage(pageable, userId, subject, startTime, endTime);
	}
	
	/**
	 * 正在使用
	 */
		/*private Page findByPage(Page page, String hql, RowMapper rowmapper, String fileds,String orderby,String orderType, int totalCount) {
	        try {
	            int first=0; //起始页
	            int pagesize=0; //显示条数
	            int pageselectsize=0;//已经选择的长度
	            if (page.isFirstSetted()) {
	                first=page.getFirst();
	            }
	            if (page.isPageSizeSetted()) {
	                pagesize=page.getPageSize();
	            }

	            pageselectsize = pagesize+first;

	            if(pageselectsize>=totalCount){
	                pageselectsize=totalCount;
	            }
	            String orderStr=getOrderAsc(orderby,orderType);
	            //新修改2013-05-06 黄普友
	            String sql="SELECT * FROM (";
	            sql+="select "+fileds+" ,ROW_NUMBER() OVER (ORDER BY "+orderStr+") AS RowNo ";
	            sql+=" "+hql;
	            sql+=" ) AS newTable WHERE RowNo>"+(page.getPageNo()-1)*pagesize+" and RowNo<="+pageselectsize;

	            page.setTotalCount(totalCount);
	            List<Map<String, Object>> list=null;
	            if(rowmapper==null){
	            	list=jdbcTemplate.queryForList(sql);
	            }else{
	            	list=jdbcTemplate.query(sql,rowmapper);
	            }
	            page.setResult(list);
	         
	            return page;

	        } catch (RuntimeException e) {
	            throw e;
	        }
	    }*/
	    
	  //排序处理
		@SuppressWarnings("unused")
		private String getOrderAsc(String orderby,String orderType) {
			StringBuilder res=new StringBuilder("");
			String resStr="";
			 String[] arr = orderby.split(",");
			 for(String s:arr){
				 res.append(s+" "+orderType+",");
			 }
			 resStr=res.toString();
			 if(resStr.endsWith(",")){ 
				 resStr = resStr.substring(0,resStr.length()-1);
			 }
			return resStr;
		}
	
    /**
     * 获取sql结果集个数
     * @param sql
     * @return
     */
   /* private int getCountBySql(String sql) {
        int count = 0;
        try {
        	List list=this.jdbcTemplate.queryForList(sql);
        	if(list!=null&&list.size()>0){
        		count=this.jdbcTemplate.queryForInt(sql);
        	}
        } catch (RuntimeException e) {
            throw e;
        }
        return count;
    }*/
	
	/**
	 * 功能：获取给登陆人发送的所有活动推荐
	 * @param userId 客户经理id
	 * @param companyId 公司id
	 * @return
	 */
	public List<PushInfo> findPushsByUserId(Integer userId){
		/*try {
			String sql="select * from  PushInfo p where p.isDelete=0 and p.pushId in (select pushId from PushUser where userId="+userId+"  ) order by pushTime desc";
			List<PushInfo> pushList = jdbcTemplate.query(sql,new PushInfoMapper());
			return pushList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}*/
		return pushInfoDao.findPushsByUserId(userId);
	}
    /**
     * 功能：根据用户id获取推送信息
     * @param userId
     * @return
     */
    public List<PushInfo> findPushsByUserId(Integer userId,Integer pushType){
    	return pushInfoDao.findPushsByUserId(userId, pushType);
    }
	/**
	 * 通过活动id获得活动信息
	 * @param pushId
	 * @return
	 */
/*	public PushInfo findById(Integer pushId){
			String sql = "select * from PushInfo where pushId="+pushId;
			Object obj = jdbcTemplate.queryForObject(sql,new PushInfoMapper());
			if (obj != null) {
				return (PushInfo)obj;
			}else {
				return null;
			}
	}*/
	/**
	 * 通过活动id删除活动信息
	 * @param pushId
	 * @return
	 */
	public int deletePushs(String ids){
	/*	if (ids.startsWith(",")) {
			ids = ids.substring(1);
		}	
		if (ids.endsWith(",")) {
			ids = ids.substring(0,ids.length()-1);
		}
		if (ids.length()>0) {
			String sql = "update PushInfo set isDelete=1 where pushId in ("+ids+")";
			//int res = jdbcTemplate.update(sql);
			return res;
		}else {
			return 0;
		}*/
		return pushInfoDao.deletePushs(ids);
	}
    /**
     * 功能：得到下一次推送
     * @param pushId
     * @return
     */
    public PushInfo loadNextPush(Integer pushId){
    	return pushInfoDao.loadNextPush(pushId);
    }

}
