/**
 *
 */
package cn.com.qytx.oa.record.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.impl.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:人事档案信息Dao类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Component
public class UserRecordDao extends BaseDao<UserRecord, Integer> implements Serializable{


    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 根据Id查询一份没有标记删除的人事档案信息
     *
     * @param id 人事档案Id
     * @return 指定ID的人事档案
     */
    public UserRecord findById(int id) {
        return (UserRecord) super.findOne(" id=? and isDelete=?", id, CbbConst.NOT_DELETE);
    }
    
    /**
     * 功能：根据用户ID删除档案信息
     * @param userId
     */
    public void deleteByUserIds(String userIds){
        String hql=" update UserRecord ur set ur.isDelete = 1 where userInfo.userId in ("+userIds+")";
        this.bulkDelete(hql);
    }
    
    public List<UserInfo> findUserListByType(Integer companyId,Integer treeType){
    	String sql = "select u.user_id as userId,u.user_name as userName,u.sex,u.group_id as groupId,u.Phone as phone  "
    			+ " from tb_ohr_user_record ur inner join tb_user_info u on ur.user_id=u.user_id ";
    	if(treeType==2){//离职 去离职记录表中
    		sql += " inner join tb_ohr_leave ol on ur.user_id=ol.user_id ";
    	}
    	sql += " where ur.is_delete=0 and ur.company_id="+companyId;
    	if(treeType==1){
    		sql += " and ur.user_id not in (select ol.user_id from tb_ohr_leave ol)";
    	}
    	Query query = super.entityManager.createNativeQuery(sql);
	    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(UserInfo.class));
    	return  query.getResultList();
    }
    
    /**
	 * 执行有返回值的SQLServer存储过程
	 * 
	 * @param procedureName SQLServer存储过程名
	 * @param params SQLServer存储过程参数
	 * @return String 返回值 。执行成功返回id. 执行时有问题返回 如：[{"errorCol": "0,1",
	 *         "errorCode":0, "errorMessage":"数据重复"}, {"errorCol": "6",
	 *         "errorCode":1, "errorMessage":"省份不存在"}]
	 */
	public String myExecuteProcedure(String procedureName, List<String> params){
		if (StringUtils.isBlank(procedureName)) {
			return null;
		}
		if (params == null || params.isEmpty()) {
			return null;
		}
		String result = null;
		int paramslength = params.size();
		Connection conn = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) entityManager.getDelegate();
			conn = sessionImpl.connection();
			StringBuffer sql = new StringBuffer();
			sql.append("{call ");
			sql.append(procedureName);
			sql.append("( ");
			for (int i = 0; i < paramslength; i++) {
				sql.append("?");
				sql.append(",");
			}
			sql.append("?");
			sql.append(" )}");

			CallableStatement cstmt = conn.prepareCall(sql.toString());
			// 这里需要再传一个参数类型的数组，然后赋值。
			for (int i = 0; i < params.size(); i++) {
				cstmt.setString(i + 1, params.get(i));
			}
			cstmt.registerOutParameter(paramslength + 1, java.sql.Types.VARCHAR);
			cstmt.execute();
			/*
			 * 记录集获取到后,把rs记录集循环取出后或者调用cstmt.getMoreResults()方法后,
			 * sqlserver才会处理output返回值
			 */
			if (!cstmt.getMoreResults()) {// 此行判断是否还有更多的结果集,如果没有,接下来会处理output返回参数了
				result = cstmt.getString(paramslength + 1);// 此行不再报错
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 手机端获取档案详情list方法
	 * @param page
	 * @param treeType
	 * @param userName
	 * @return
	 */
	  public Page<UserRecord> findAllUserRecordList(Pageable page,Integer treeType,String userName) {
//	    	String hql = " x.userInfo.userId in (select gu.userId from GroupUser as gu where groupId=?) and x.isDelete=?";
	    	
	    	String hql=" x.isDelete=0";
	    	if(StringUtils.isNoneBlank(userName)){
	    		hql+=" and x.userInfo.userName like '%"+userName+"%'";
	    	}
	    	if(treeType!=null&&treeType.intValue()==2){
	    		hql += " and x.userInfo.userId in (select rl.userInfo.userId from RecordLeave rl)";
	    	}else{
	    		hql += " and x.userInfo.userId not in (select rl.userInfo.userId from RecordLeave rl)";
	    	}
	        return super.findAll( hql, page);
	    }
	  
	
	  
}
