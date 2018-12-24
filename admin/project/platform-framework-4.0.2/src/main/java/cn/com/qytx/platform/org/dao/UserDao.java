package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.dao.ClearParamAfterMethod;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 用户操作实体类 User: 黄普友 Date: 13-2-17 Time: 下午2:58
 */
@Repository("userDao")
public class UserDao<T extends UserInfo>  extends BaseDao<UserInfo, Integer> implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 根据人员ID获取人员列表
	 *
	 * @param userIds 人员Id，之间用，隔开
	 * @return 人员列表
	 */
	public List<UserInfo> findUsersByIds( String str) {
		String userIds = str;
		String hql = "";
		hql = "isDelete=0 ";
		if (userIds != null && !userIds.equals("")) {
			if (userIds.endsWith(",")) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			if (userIds.startsWith(",")) {
				userIds = userIds.substring(1, userIds.length());
			}
			Sort sort = new Sort(new Sort.Order(Direction.ASC, "orderIndex"));
			hql += " and userId in (" + userIds + ") ";
			return super.partition().findAll(hql,sort);
		}

		return null;
	}

	/**
	 * 查找人员
	 * @param companyId 企业ID
	 * @param searchName 查找人员条件，姓名，电话，手机
	 * @return 人员列表
	 */
	public List<UserInfo> findAllUsers(int companyId, String searchName) {
		super.isDelete.set(null);
		String hql = "";
		hql = " isDelete=0 and companyId=?";
		if (searchName != null && !searchName.equals("")) {
			hql += " and (userName like '%" + searchName
					+ "%' or phone like '%" + searchName + "%' or py like '%"
					+ searchName + "%')";
		}
		return super.partition(companyId%10).findAll(hql, companyId);
	}
	/**
	 * 功能：查找人员 过滤掉隐藏的人员
	 * @param companyId 单位ID
	 * @param searchName 查询关键字
	 * @return 返回人员列表
	 */
	public List<UserInfo> findAllUsersAndFilter(int companyId, String searchName) {
		super.isDelete.set(null);
		String hql = "";
		hql = " isDelete=0 and mobileShowState=0 and companyId=?";
		if (searchName != null && !searchName.equals("")) {
			hql += " and (userName like '%" + searchName
					+ "%' or phone like '%" + searchName + "%' or py like '%"
					+ searchName + "%')";
		}
		return super.partition(companyId%10).findAll(hql, companyId);
	}
	/**
	 *  得到未登录人员
	 * @param companyId 单位ID
	 * @param searchName 查询关键字
	 * @return 返回人员列表
	 */
	public List<UserInfo> findAllNoLoginUsers(int companyId, String searchName) {
		super.isDelete.set(null);
		String hql = "";
		hql = " isDelete=0 and companyId=?";
		if (searchName != null && !searchName.equals("")) {
			hql += " and (userName like '%" + searchName
					+ "%' or phone like '%" + searchName + "%' or py like '%"
					+ searchName + "%')";
		}
		hql +=" and userId not in (select userId from UserLoginST where companyId=?)";
		return super.partition(companyId%10).findAll(hql, companyId,companyId);
	}
	/**
	 * 根据角色 id获取人员列表
	 * @param ids 角色 id,多个id之间，隔开
	 * @return 人员列表
	 */
	public List<UserInfo> findUsersByRoleId(String str) {
		String ids = str;
			if (ids == null || ids.equals(""))
				return null;
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length());
			}
			Sort sort = new Sort(new Sort.Order(Direction.ASC, "orderIndex"));
			String  hql = "isDelete = 0 and userId in (select userId from RoleUser where roleId in ("+ids+")) ";
			return super.partition().findAll(hql,sort);
			
	}

	/**
	 * 根据部门/群组 id获取人员列表
	 * @param ids 部门/群组 id,多个id之间，隔开
	 * @return null 表示没有数据 ,非空返回用户列表，用户信息只包括用户id，用户姓名，用户手机号，部门id
	 */
	public List<UserInfo> findUsersByGroupId(String str) {
		String ids  = str;
		if (ids == null || ids.equals(""))
			return null;
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length());
		}
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "orderIndex"));
		String hql = "isDelete =0 and groupId in  ("+ids+") ";
		return super.partition().findAll(hql,sort);
	}

	/**
	 * 根据部门/群组 id获取人员列表过滤掉隐藏人员
	 * @param ids 部门/群组 id,多个id之间，隔开
	 * @return null 表示没有数据 ,非空返回用户列表，用户信息只包括用户id，用户姓名，用户手机号，部门id
	 */
	public List<UserInfo> findUsersByGroupIdAndFilter(String str) {
		String ids  = str;
		if (ids == null || ids.equals(""))
			return null;
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length());
		}
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "orderIndex"));
		String hql = "isDelete =0 and mobileShowState=0 and groupId in  ("+ids+") ";
		return super.partition().findAll(hql,sort);
	}

	/**
	 * 根据id列表删除人员，多个id之间用，隔开
	 * @param str 人员ID，多个id之间用，隔开
	 */
	public void deleteUserByIds(String str) {
		String ids = str;
		if (ids == null || ids.equals(""))
			return;
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length());
		}
		String hql = "update  " + this.getEntityClass().getSimpleName()
				+ " set isDelete=1,groupId = null,lastUpdateTime=? where userId in (" + ids + ")";
		executeQuery(hql, new Date());
	}
	
    /**
     * 根据id列表删除人员，多个id之间用，隔开
     * @param companyId 单位ID
     * @param ids 人员ID
     */
    public  void deleteUserByIds(Integer companyId,String str)
    {
    	String ids = str;
        if (ids == null || ids.equals(""))
            return;
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        if (ids.startsWith(",")) {
            ids = ids.substring(1, ids.length());
        }
        String hql = "update  " + this.getEntityClass().getSimpleName()
                + " set isDelete=1,lastUpdateTime=? where userId in (" + ids + ") and companyId="+companyId;
        executeQuery(hql, new Date());
    }
	/**
	 *
	 * 功能：更新用户状态
	 * @param ids
	 */
	public void updateStateByIds(String str) {
		String ids = str;
		if (ids == null || ids.equals(""))
			return;
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length());
		}
		String hql = "update  " + this.getEntityClass().getSimpleName()
				+ " set userState=1 where userId in (" + ids + ")";
		executeQuery(hql, new Date());
	}

	/**
	 *
	 * 功能：更新用户密码
	 * @param str 用户ID，多个用户间用","隔开
	 * @param password 密码
	 */
	public void updatePasswordByIds(String str, String password) {
		String ids = str;
		if (ids == null || ids.equals(""))
			return;
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length());
		}
		String hql = "update  " + this.getEntityClass().getSimpleName()
				+ " set loginPass='" + password + "' where userId in (" + ids
				+ ")";
		executeQuery(hql);
	}

	/**
	 * 功能：根据电话号码修改用户密码
	 * @param phone 用户的电话号码
	 * @param password 密码
	 */
	public void updatePasswordByPhone(String phone, String password) {
		if (phone == null || phone.equals(""))
			return;
		if (password == null || password.equals(""))
			return;
	
		String hql = "update  " + this.getEntityClass().getSimpleName() + " set loginPass='" + password + "' where phone='" + phone + "'";
		executeQuery(hql, new Date());
	}


	/**
	 * 根据用户名获取用户
	 * @param  userName  用户名
	 * @return 用户对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public UserInfo getUserByUserName(String userName) {
		String hql = " isDelete = 0 and userName =?";
		return this.partition().findOne(hql,userName);
	}
	
	/**
	 * 获取人员列表
	 * @param companyId 企业ID
	 * @param page 分页对象
	 * @param groupId 部门ID
	 * @return 分页结果对象
	 */
	public Page<UserInfo> findByPage(int companyId, Pageable page, Integer groupId) {
		String hql = "";
		if (groupId == 0) {
			hql = "from " + this.getEntityClass().getSimpleName()
					+ " where isDelete=0 and companyId=" + companyId
					+ " order by orderIndex,userId asc";
			return super.findByPage(page, hql);
		} else {
			hql = "from "
					+ this.getEntityClass().getSimpleName()
					+ " a where a.isDelete=0 and a.groupId = ? and a.companyId=? order by orderIndex,userId asc";
			return super.findByPage(page, hql, groupId, companyId);
		}
	}

	/**
	 * 查询用户列表
	 * @param companyId 单位ID
	 * @param userName 用户名
	 * @param phone 电话
	 * @param PY 拼音
	 * @return List<User> 返回类型
	 */
	public List<UserInfo> findAllUsers(int companyId, String userName, String phone,
			String PY) {
		String hql = "isDelete=0 and userName like ? and phone like ? and py like ? and companyId=?";
		return super.partition(companyId%10).findAll(hql, "%"+userName+"%", "%"+phone+"%", "%"+PY+"%", companyId);
	}

	/**
	 *
	 * 分页查询人员信息
	 * @param vo 查询参数
	 * @param page 分页请求对象
	 * @param groupIds 部门ID集合
	 * @return 分页查询结果对象
	 */
	public Page<UserInfo> findAllUsersByPage(UserVo vo, Pageable page,String groupIds) {
		StringBuilder hql = new StringBuilder();
		if(vo.getIsDelete() == 0){
			hql.append("isDelete = 0");
		}
		if (null != vo) {
			// 公司
			Integer companyId = vo.getCompanyId();
			if (companyId != null) {
				hql.append(" and companyId = "+companyId);
			}
			
			//add by jiayq
			if(groupIds!=null && !groupIds.equals("")){
				hql.append(" and groupId in  ("+groupIds+")");
			}
			
			// 用户名
			String loginName = vo.getLoginName();
			if (vo.TXZL.equals(vo.getProjectName())) {
				if (StringUtils.isNotBlank(loginName)) {
					hql.append(" and (userName like '%"+loginName+"%'")
					.append(" or phone like '%"+loginName+"%'")
					.append(" or vNum like '%"+loginName+"%')");
				}
			} else{
				// 姓名/电话
				if(StringUtils.isNotEmpty(loginName)) {
					loginName = loginName.replaceAll("%", "/%");
					loginName = loginName.replaceAll("_", "/_");
					hql.append(" and (userName like '%"+loginName+"%' escape '/' or phone like '%"+loginName+"%' escape '/'  )");
				}
				
				// 登录用户管理关键字  登录名/姓名
				String keyWordForLogin = vo.getKeyWordForLogin();
				if (StringUtils.isNotEmpty(keyWordForLogin)) {
					keyWordForLogin = keyWordForLogin.replaceAll("%", "/%");
					hql.append(" and (loginName like '%"+keyWordForLogin+"%' escape '/' or userName like '%"+keyWordForLogin+"%' escape '/' )");
				}
				
				// 登录用户管理关键字  登录名/姓名
				String keyWordForLogin_hotline = vo.getKeyWordForLogin_hotline();
				if (StringUtils.isNotEmpty(keyWordForLogin_hotline)) {
					keyWordForLogin_hotline = keyWordForLogin_hotline.replaceAll("%", "/%");
					hql.append(" and (u.loginName like '%"+keyWordForLogin_hotline+"%' escape '/' or u.userName like '%"+keyWordForLogin_hotline+"%' escape '/' or u.phone like '%"+keyWordForLogin_hotline+"%' escape '/' )");
				} 
			}
			
			
			//性别
			if(vo.getSex()!=null){
				hql.append(" and sex = "+vo.getSex());
			}
			//是否显示全部人员，还是只显示隐藏的
			if(vo.getMobileShowState() != null){
				//1为只显示隐藏人员
				if(vo.getMobileShowState() == 1){
					hql.append(" and mobileShowState = "+vo.getMobileShowState());
				}
			}
			if (StringUtils.isNotBlank(vo.getChildrenGroupIds())) {
				hql.append(" and groupId in ("+vo.getChildrenGroupIds()+")");
			}
			
			// 查询来源
			String from = vo.getFrom();
			if (!StringUtils.isEmpty(from) && "loginManager".equals(from)){
			    hql.append(" and (userState = 0 or userState = 1)" );
			}

		}
		
		return super.partition().findAll( hql.toString(),page);
	}

	/**
	 *
	 * 功能：查询人员信息部门层级，部门排序，人员排序，人员拼音 排序
	 * @param vo 查询参数
	 * @param page 分页查询对象
	 * @param groupIds 部门ID
	 * @return Page<Map<String,Object>> 查询结果，自定义用户对象
	 */
	public Page<Map<String, Object>> findByPageAll(UserVo vo,Pageable page,String groupIds) {
		
		String listHql = "select new map(u.companyId as companyId,u.userId as userId,u.userName as userName,u.phone as phone,u.title as title,u.vNum as vNum,u.job as job,u.groupId as groupId,u.loginName as loginName,u.sex as sex,u.lastLoginTime as lastLoginTime,u.phonePublic as phonePublic,u.registerTime as registerTime,u.createTime as createTime,u.userState as userState,u.isDefault as isDefault,u.role as role,g.groupName as groupName,g.path as path,u.mobileShowState as mobileShowState,u.isVirtual as isVirtual,u.linkId as linkId,u.officeTel as officeTel,u.workNo as workNo ) ";
		String countHql = "select count(u.userId) ";
		StringBuilder hql = new StringBuilder();
		//hql.append("select new map(u.userId as userId,u.userName as userName,u.phone as phone,u.title as title,u.vNum as vNum,u.job as job,u.groupId as groupId,u.loginName as loginName,u.sex as sex,u.lastLoginTime as lastLoginTime,u.phonePublic as phonePublic,u.registerTime as registerTime,u.createTime as createTime,u.userState as userState,u.isDefault as isDefault,u.role as role,g.groupName as groupName,u.mobileShowState as mobileShowState )");
		hql.append(" from UserInfo u, GroupInfo g where u.groupId=g.groupId and u.isDelete = 0 and g.isDelete = 0 ");
		if (null != vo) {
			// 公司
			Integer companyId = vo.getCompanyId();
			if (companyId != null) {
				hql.append(" and u.companyId = "+companyId);
			}
			
			//add by jiayq
			if(groupIds!=null && !groupIds.equals("")){
				hql.append(" and u.groupId in ("+groupIds+")");
			}
			
			// 用户名
			String loginName = vo.getLoginName();
			if (vo.TXZL.equals(vo.getProjectName())) {
				if (StringUtils.isNotBlank(loginName)) {
					hql.append(" and (u.userName like '%"+loginName+"%'")
					.append(" or u.phone like '%"+loginName+"%'")
					.append(" or u.vNum like '%"+loginName+"%')");
				}
			} else{
				// 姓名/电话
				if(StringUtils.isNotEmpty(loginName)) {
					loginName = loginName.replaceAll("%", "/%");
					loginName = loginName.replaceAll("_", "/_");
					hql.append(" and (u.userName like '%"+loginName+"%' escape '/' or u.phone like '%"+loginName+"%' escape '/'  )");
				}
				
				// 登录用户管理关键字  登录名/姓名
				String keyWordForLogin = vo.getKeyWordForLogin();
				if (StringUtils.isNotEmpty(keyWordForLogin)) {
					keyWordForLogin = keyWordForLogin.replaceAll("%", "/%");
					hql.append(" and (u.loginName like '%"+keyWordForLogin+"%' escape '/' or u.userName like '%"+keyWordForLogin+"%' escape '/' )");
				} 
				
				// 登录用户管理关键字  登录名/姓名
				String keyWordForLogin_hotline = vo.getKeyWordForLogin_hotline();
				if (StringUtils.isNotEmpty(keyWordForLogin_hotline)) {
					keyWordForLogin_hotline = keyWordForLogin_hotline.replaceAll("%", "/%");
					hql.append(" and (u.loginName like '%"+keyWordForLogin_hotline+"%' escape '/' or u.userName like '%"+keyWordForLogin_hotline+"%' escape '/' or u.phone like '%"+keyWordForLogin_hotline+"%' escape '/' )");
				} 
			}
			
			
			//性别
			if(vo.getSex()!=null){
				hql.append(" and sex = "+vo.getSex());
			}
			//是否显示全部人员，还是只显示隐藏的
			if(vo.getMobileShowState() != null){
				//1为只显示隐藏人员
				if(vo.getMobileShowState() == 1){
					hql.append(" and u.mobileShowState = "+vo.getMobileShowState());
				}
			}
			if (StringUtils.isNotBlank(vo.getChildrenGroupIds())) {
				hql.append(" and u.groupId in ("+vo.getChildrenGroupIds()+")");
			}
			
			// 查询来源
			String from = vo.getFrom();
			if (!StringUtils.isEmpty(from) && "loginManager".equals(from)){
			    hql.append(" and (u.userState = 0 or u.userState = 1)" );
			}
		}
		
		String orderHql = " ORDER BY g.grade,g.orderIndex,u.orderIndex,u.py";
		List<Map<String,Object>> userList=entityManager.createQuery(listHql+ hql.toString()+orderHql).setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
		List<UserInfo> content = new ArrayList<UserInfo>();
		int total = super.findInt(countHql+hql.toString());
		return new PageImpl<Map<String,Object>>(userList,page,total);
	}
	
	/**
	 * 根据登录名查询用户对象
	 * @param loginName 登录名
	 * @return UserInfo 用户对象
	 */
	public UserInfo findByLoginName(String loginName){
		List<UserInfo> list = find("select u from UserInfo u where loginName = ? and isDelete = 0", loginName);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
    /**
     * 功能：根据姓名和电话查询人员
     * @param companyId 公司Id
     * @param name 姓名
     * @param phone 电话
     * @return 人员信息
     */
	public UserInfo getUserByNamePhone(int companyId,String name, String phone){
        String hql = "userName = ? and phone = ? and companyId=? and isDelete = 0";
        return super.partition(companyId%10).findOne(hql, name, phone, companyId);
    }
	
	/**
     * 
     * 功能：根据姓名和电话查询人员
     * @param companyId 公司Id
     * @param phone 电话
     * @return 人员信息
     */
	public UserInfo getUserByNamePhone(int companyId, String phone){
        String hql = " phone = ? and companyId=? and isDelete = 0";
        return super.partition(companyId%10).findOne(hql , phone, companyId);
    }
	
	/**
	 * 分页查询用户对象
	 * @param page 分页请求对象
	 * @return 分页查询结果
	 */
	public List<UserInfo> findUsersByPage(Pageable page){
		String hql = "from "+super.getEntityClass().getSimpleName()+" u where isDelete=0 order by orderIndex";
		Page temp = super.findByPage(page, hql);
		return temp.getContent();
	}
	
	
	/**
	 * 根据用户修改皮肤标志
	 * @param id 用户ID
	 * @param skinLogo 标识ID
	 */
	public void updateUserSkinLogo(int id, int skinLogo) {
		String hql = "update  " + this.getEntityClass().getSimpleName()
				+ " set skinLogo="+skinLogo+" where userId=" + id;
		this.executeQuery(hql);
	}
	
	/**
	 * 功能：查询所有的用户ID、用户姓名的映射
	 * @return Map<Integer,String> key值是用户ID，value是用户姓名
	 */
	public Map<Integer,String> getAllUserName(){
		Map<Integer,String> map = new HashMap<Integer, String>();
		String hql = "select new map(userId as id, userName as name) from "+this.getEntityClass().getSimpleName()+" where  isDelete = 0 ";
		List<Map<String,Object>> list = super.find(hql);
		for(int i=0; i<list.size(); i++){
			Map<String,Object> temp = list.get(i);
			int id = Integer.parseInt(temp.get("id")+"");
			String name = temp.get("name")==null?"":temp.get("name").toString();
			map.put(id, name);
		}
		return map;
	}
	
	/**
	 * 根据电话号码查询用户对象
	 * @param phone 电话号码
	 * @return 用户对象
	 */
	public UserInfo getUserByPhone(String phone){
		String hql = "phone = ? and isDelete=0 ";
		return super.partition().findOne(hql, phone);
	}
	
	/**
	 * 根据ID查询用户对象
	 * @param UserId 用户ID
	 * @return 用户对象
	 */
	public UserInfo getUserById(int UserId){
		String hql ="userId = ? and isDelete=0 ";
		return super.partition().findOne(hql, UserId);
	}
	/**
	 * 功能：根据角色ID，单位ID，类型查询角色下的人员
	 * @param roleId 角色ID
	 * @param companyId 单位ID
	 * @param type 角色类型
	 * @return List<UserInfo> 用户列表
	 */
	public List<UserInfo> findUsersByRoleId(Integer roleId, Integer companyId,
			Integer type) {
		String hql = "companyId = ? and userId in (select userId from RoleUser where type = ? and roleId = ?)";
		return super.partition(companyId%10).findAll(hql, companyId,type,roleId);
	}

    /**
     * 获取排序人员信息
     * @param companyId
     * @param groupId
     * @param top
     * @return
     */
    public UserInfo getSortOrderUser(Integer companyId,Integer groupId,Integer top)
    {
        //首先按照orderIndex正序排列
        String hql="select t from UserInfo t where t.companyId="+companyId+" and t.groupId="+groupId+" order by  t.orderIndex asc";
        List<UserInfo> list= super.entityManager.createQuery(hql).setMaxResults(top).getResultList();
        if(list!=null&&list.size()>0)
        {
            UserInfo userInfo=list.get(list.size()-1);//获取最后一个

            return userInfo;
        }
        return null;
    }

    /**
     * 人员进行排序
     * @param comanyId 单位ID
     * @param groupId 部门Id
     * @param startOrder 开始序号
     * @param endOrder 结束序号
     */
    public void sortOrder(Integer comanyId,Integer groupId,Integer startOrder,Integer endOrder)
    {
        String hql="";
        if(startOrder<endOrder)
        {
            hql="update UserInfo set orderIndex=orderIndex-1, lastUpdateTime='"+ DateTimeUtil.getCurrentTime()+"' where companyId="+comanyId+" and groupId="+ groupId+" and orderIndex>"+startOrder+" and orderIndex<="+endOrder;
        }
        else
        {
            hql="update UserInfo set orderIndex=orderIndex+1, lastUpdateTime='"+ DateTimeUtil.getCurrentTime()+"' where companyId="+comanyId+" and groupId="+ groupId+" and orderIndex>="+endOrder+" and orderIndex<"+startOrder;
        }
        this.entityManager.createQuery(hql).executeUpdate();
    }
    /**
     * 获取某部门下人员的最大排序号
     * @param companyId 单位ID
     * @param groupId 部门ID
     * @return Integer 最大排序号
     */
    @Deprecated
    public Integer getMaxOrderIndex(Integer companyId,Integer groupId)
    {
        String hql="select t from UserInfo t where companyId="+companyId+" and groupId="+groupId+" order by orderIndex desc";
        List<UserInfo> list=this.entityManager.createQuery(hql).setMaxResults(1).getResultList();
        if(list!=null && list.size()>0)
        {
            return list.get(0).getOrderIndex();
        }
        return 0;
    }
    /**
     * 获取单位的最大排序号
     * @param companyId 单位ID
     * @return Integer 最大序号
     */
    public Integer getMaxOrderIndex(Integer companyId)
    {
    	Integer maxOrderIndex=0;
    	String hql="select max(t.orderIndex) as orderIndex from UserInfo t where isDelete =0 and companyId="+companyId;
    	Query query=this.entityManager.createQuery(hql);
    	Object obj=query.getSingleResult();
    	if(obj!=null){
    		maxOrderIndex=(Integer)obj;
    	}
    	return maxOrderIndex;
    }
    /**
     * 功能：根据角色代码和部门ID查询该部门下的人员列表
     * @param roleCode 角色代码
     * @param groupId 部门ID
     * @return 用户列表
     */
    public List<UserInfo> findUsersByRoleCodeAndGroup(String roleCode,int groupId){
    	String hql = "from UserInfo where groupId = ? and userId in (select userId from RoleUser where roleId in (select roleId from RoleInfo where roleCode = ?))";
    	return super.find(hql, groupId,roleCode);
    }
    
    /**
     * 功能：根据电话号码查询用户
     * @param phone 电话号码
     * @return 用户列表
     */
    public List<UserInfo> findByPhone(String phone){
    	String hql = "phone = ? and isDelete = 0 ";
    	return super.partition().findAll(hql, phone);
    }
    
    /**
     * 功能：根据电话号码查询用户(不带公司)
     * @param phone 电话号码
     * @return 用户列表
     */
    public List<UserInfo> findByPhoneWithoutCompany(String phone){
    	String jpql = "select u from UserInfo u where phone = ? and isDelete = 0 ";
    	return super.find(jpql, phone);
    }
    
    /**
     * 根据用户名和电话查询用户
     * @param userName 用户名
     * @param phone 电话
     * @return 用户列表
     */
    public List<UserInfo> findByUserNameAdnPhone(String userName,String phone){
    	String hql = "phone = ? and userName = ? and isDelete = 0";
    	return super.partition().findAll(hql, phone,userName);
    }
    
    /**
     * 功能：根据登陆名查找登陆用户
     * @param loginNames 登录名，多个登录名间用","隔开
     * @return 用户列表
     */
    public List<UserInfo> findByLoginNames(String loginNames){
    	String[] strs = loginNames.split(",");
    	String users = "";
    	for(int i=0; i<strs.length; i++){
    		String s = strs[i];
    		users+="'"+s+"'";
    		if(i<strs.length-1){
    			users+=",";
    		}
    	}
    	String hql = "loginName in ("+users+")";
    	return super.unDeleted().partition().findAll(hql);
    }
    
    /**
     * 功能：根据登陆名查找登陆用户
     * @param loginNames 登录名，多个登录名间用","隔开
     * @return 用户列表
     */
    public List<UserInfo> findByLoginName(String loginName,String status,String companyId){
    	StringBuffer hql = new StringBuffer();
    	hql = hql.append("select u from UserInfo u where loginName = ? and isDelete = ?");
    	
    	if(companyId!=null){
    		hql = hql.append(" and companyId=").append(companyId);
    	}
    	List<UserInfo> list = find(hql.toString(), loginName,status);
    	return list;
    }
    
    
    /**
     * 通过id集合查询用户
     * @param ids List<Integer> 用户ID集合
     * @return List<UserInfo> 用户列表
     */
    public List<UserInfo> findUserInfoByIds(List<Integer> ids){
    		String hql = "userId in (?1)";
        	return super.unDeleted().partition().findAll(hql,ids);
    }

    /**
     * 根据登录名和密码查询用户
     * @param loginName 登录名
     * @param loginPass 密码
     * @return 用户列表
     */
	public  List<UserInfo> findByLoginNamePass(String loginName,String loginPass){
		String hql = "loginName = ? and loginPass = ?  and isDelete = 0";
		return super.partition().findAll(hql, loginName,loginPass);
	}
    /**
     * 根据登录名和密码查询用户，通讯助理专用
     * @param loginName 登录名
     * @param loginPass 密码(MD5加密后的)
     * @return 用户列表
     */
	public  List<UserInfo> findByLoginNamePassTXZL(String loginName,String loginPass){
		String hql = "loginName = ? and loginPass = ?  and isDelete = 0 and companyId in (select companyId from Company where registered=1)";
		return super.partition().findAll(hql, loginName,loginPass);
	}
    
    
    /**
     * 功能：获取已经设置管理范围的人员
     * @param page 分页查询对象
     * @param companyId 单位ID
     * @return Page<UserInfo> 分页查询结果
     */
    public Page<UserInfo> findGroupPowerUsers(Pageable page,Integer companyId){
    	return partition(companyId%10).findAll("companyId=?1 and userId in (select userId from UserGroup where companyId=?1 and groupPower is not null)",page,companyId);
    }
 
    /**
     * 功能：更新用户排序 新增用户或修改用户时重新排序(部门内)
     * @param userInfo 用户对象
     * @return
     */
    public int updateUserOrder(UserInfo userInfo){
    	Object obj  = super.findUnique("select count(*) from UserInfo where orderIndex=?1 and companyId=?2 and userId<>?3 ", userInfo.getOrderIndex(),userInfo.getCompanyId(),userInfo.getUserId());
    	if (null!=obj) {
    		String hql="update UserInfo set orderIndex=orderIndex+1 where orderIndex>="+userInfo.getOrderIndex()+" and userId<>"+userInfo.getUserId()+" and companyId="+userInfo.getCompanyId();
        	return executeQuery(hql);
		}
    	return 0;
    }
    /**
     * 
     * 功能：设置人员信息（是否可见，人工转接，漏话提醒）
     * <br/>@param type  1 设置人员是否可见 2 人工转接设置 3 漏话提醒设置 
     * <br/>@param companyId
     * <br/>@param userIds
     * <br/>@param state  是否可见(0显示1隐藏) 人工转接设置(1：不限制0：仅内部转接2：不可被转接) 漏话提醒设置(0：不需要，1：短信通知，2：语音留言)
     * <br/>@return  更新的记录数
     */
    public int updateUser(int type,int companyId,String userIds,int state){
		if (StringUtils.isNotBlank(userIds)) {
			if (userIds.startsWith(",")) {
				userIds = userIds.substring(1);
			}
			if (userIds.endsWith(",")) {
				userIds = userIds.substring(0, userIds.length()-1);
			}
			String hql = "update UserInfo set lastUpdateTime='"+DateTimeUtil.getCurrentTime()+"'";
			if (type==1) {//设置人员是否可见
				hql += ",mobileShowState="+state;
			}else if (type==2) {//人工转接设置 1：不限制0：仅内部转接2：不可被转接
				hql += ",userPower="+state;
			}else if (type==3) {//漏话提醒设置
				hql += ",mcnType="+state;
			}
			hql += " where companyId="+companyId+" and userId in ("+userIds+")";
			return executeQuery(hql);
		}
    	return 0;
    }

    /**
     * 功能：根据公司id和人员登录名得到用户
     * @param companyId 单位ID
     * @param loginName 登录名
     * @return 用户对象
     */
	public UserInfo loadUserByCompyIdName(Integer companyId,String loginName){
        String jpql = " companyId=? and loginName = ? and isDelete = 0";
        return super.partition(companyId%10).findOne(jpql, companyId, loginName);
    }

    /**
     * 根据单位ID和人员ID获取单位所有人员信息
     * @param companyId 单位ID
     * @param userId 人员ID(可能需要根据该人员的权限)
     * @return 用户列表
     */
    public List<UserInfo> findAll(Integer companyId,Integer userId)
    {
        try {
            List<UserInfo> userInfos=new ArrayList<UserInfo>();
            String sql = " from UserInfo a,GroupInfo b where a.groupId=b.groupId and a.isDelete=0 and a.companyId="+companyId;
            List<Object> list = super.entityManager.createQuery(sql).getResultList();
            if(list!=null)
            {
               for(Object obj:list)
               {
                   Object[] arr=(Object[])obj;
                   if(arr!=null)
                   {
                       UserInfo user=null;
                       GroupInfo group=null;
                       if(arr[0] instanceof UserInfo)
                       {
                            user=(UserInfo)arr[0];
                       }
                       if(arr[1] instanceof GroupInfo)
                       {
                           group=(GroupInfo)arr[1];
                       }
                       if(user!=null && group!=null)
                       {
                           user.setGroupName(group.getGroupName());
                           userInfos.add(user);
                       }
                   }
               }
            }
            return userInfos;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

    }
    
	/**
	 * 功能：获得用户的所有关联人
	 * @param userId 用户Id
	 * @param companyId 单位ID
	 * @return 用户列表
	 */
	public List<UserInfo> findVirtualUsers(int userId,int companyId){
		return this.partition(companyId%10).findAll(" isDelete = 0 and isVirtual = 1 and companyId = ? and linkId = ? ",companyId,userId);
	}
	
	/**
	 * 功能：将手机端传递过来的用户中虚拟用户转换为真实用户
	 * @param userIds
	 * @param flag true 要前后',' false 不要前后','
	 * @return
	 */
	public String transVirtualToTrueUser(String userIds,boolean flag){
		if(StringUtils.isNotEmpty(userIds)){
			if(!userIds.startsWith(",")){
				userIds = ","+userIds;
			}
			if(!userIds.endsWith(",")){
				userIds = userIds+",";
			}
			List<UserInfo> list = this.partition().findAll(" isDelete=0 and isVirtual=1 and userId in ("+userIds.substring(1,userIds.length()-1)+")");
			if(list!=null && list.size()>0){
				for(UserInfo user : list){
					userIds = userIds.replace(","+user.getUserId()+",", ","+user.getLinkId()+",");
				}
			}
			if(!flag){
				userIds = userIds.substring(1,userIds.length()-1);
			}
			return userIds;
		}
		return "";
	}
	
	/**
	 * 获取部门人员数量的映射，
	 * @param companyId
	 * @return key:部门ID，value:部门人员数
	 */
	public Map<Integer,Integer> getUserNumByGroup(int companyId){
		Map<Integer,Integer> result = new HashMap<Integer, Integer>();
		String hql = "select new map(u.groupId as groupId,COUNT(u.userId) as count) from UserInfo u  where  u.isDelete=0 and u.companyId = "+companyId;
		List<Map> list = super.find(hql, companyId);
		for(int i=0; i<list.size(); i++){
			Map<String,String> temp = list.get(i);
			Integer key = Integer.parseInt(temp.get("groupId"));
			Integer count = Integer.parseInt(temp.get("count"));
			result.put(key, count);
		}
		return result;
	}
	
	/**获取指定部门下的人员数
	 * @param groupId
	 * @return
	 */
	public Integer getUserNumsByGroupId(int companyid,int groupId){
		String hql = "select count(userId) as co from UserInfo where isDelete = 0 and companyId = ? and groupId = ?";
		return super.findInt(hql, companyid,groupId);
	}
	
	
	public List<UserInfo> findUserByIdsAndSearchName(String ids,String keyWord,int companyId){
		return this.partition(companyId%10).findAll(" isDelete = 0 and userId in ("+ids+") and companyId = "+companyId+" and (userName like '%"+keyWord+"%' or phone like '%"+keyWord+"%' or py like '%"+keyWord+"%')");
	}
	
	/**
	 * 根据公司id和更新时间获得所有用户 用于手机端接口
	 * @param companyId
	 * @param lastUpdateTime
	 * @return
	 */
	public List<UserInfo> getAllUserByTimeCompanyId(int companyId,Date lastUpdateTime){
		return this.partition(companyId%10).findAll("companyId=?1 and lastUpdateTime>=?2",companyId,lastUpdateTime);
	}
	
	/**
	 * 根据登陆名和公司id获得用户信息 用于手机端接口
	 * @param loginName
	 * @param companyId
	 * @return
	 */
	public UserInfo getUserInfoByLoginName(String loginName,int companyId){
		return partition(companyId%10).findOne("loginName=?1 and companyId=?2", loginName, companyId);
	}

	@Override
	public UserInfo saveOrUpdate(UserInfo userInfo) {
		// TODO Auto-generated method stub
		if(userInfo!=null && userInfo.getCompanyId()!=null){
			userInfo.setPartitionCompanyId(userInfo.getCompanyId()%10);
		}
		
		return super.saveOrUpdate(userInfo);
	}

	@Override
	public List<UserInfo> saveOrUpdate(Iterable<UserInfo> ite) {
		// TODO Auto-generated method stub
		
		Iterator<UserInfo> iter = ite.iterator();
		while(iter.hasNext()){
			UserInfo userInfo = iter.next();
			if(userInfo!=null && userInfo.getCompanyId()!=null){
				userInfo.setPartitionCompanyId(userInfo.getCompanyId()%10);
			}
		}
		
		return super.saveOrUpdate(ite);
	}
	
	/**
	 * 功能：根据部门name 姓名获得用户信息
	 * @param companyId
	 * @param groupId
	 * @param userName
	 * @return
	 */
	public List<UserInfo> getUserByGroupUserName(int companyId,String groupName,String userName){
		String sql = "select a from UserInfo a ,GroupInfo b where a.isDelete = 0 and b.isDelete = 0 and a.groupId = b.groupId and a.companyId = "+companyId+" and b.companyId = "+companyId+" and b.groupName = '"+groupName+"' and a.userName = '"+userName+"' and a.partitionCompanyId = "+companyId%10+" ";
		List<UserInfo> list = this.entityManager.createQuery(sql).getResultList();
		return list;
	}
	
	public Timestamp getLastUpdateNew(int companyId){
		String hql = "select max(lastUpdateTime) as lastUpdateTime from  UserInfo pu  where  companyId = "+companyId;
		List list= this.entityManager.createQuery(hql).getResultList();
		if(list.size()>0){
			return (Timestamp) list.get(0);
		}else{
			return null;
		}
	}
	
	public List<UserInfo> findUsersByLastUpdateTime(int companyId, String lastUpdateTime){
		String hql = "select a.user_id as userId,a.phone as phone,a.group_id as groupId , a.user_name as userName, " +
	            "a.sex as sex ,a.office_tel as telephone,a.home_tel as telephone2,a.phone2 as phone2,a.job as job ,a.title as title,"+
				"a.email as email,a.py as py,a.user_num as userNum,a.order_index as orderIndex,a.v_num as vNum,a.is_virtual as isVirtual,"+
	            "a.link_id as linkId ,a.user_state as userState,a.user_power as userPower,a.sign_name as signName,a.role as role,a.photo as photo ,"+
	            "b.firstClientLoginTime as firstClientLoginTime,a.v_group as vgroup,a.is_delete as isDelete,a.mobile_show_state as mobileShowState, "+
	            "a.full_py as fullPy,a.formatted_number as formattedNumber,a.work_no as workNo,a.hide_phone as hidePhone,a.is_leader as isLeader "+
				"from tb_user_info a left join (select userId,MAX(FirstClientLoginTime) as firstClientLoginTime from UserLoginST group by UserId) b  on a.user_id=b.userId ";
		hql+="  where a.company_id="+ companyId;
		hql+="  and a.partition_companyid="+ (companyId%10);
	//	String hql ="select a from UserInfo a where companyId=31146 and isDelete=0";
		if(StringUtils.isNotBlank(lastUpdateTime)){
			hql+=" and (a.last_update_time>='"+lastUpdateTime+"' or b.firstClientLoginTime>='"+lastUpdateTime+"' )";
		}else{
			hql+=" and a.is_delete=0 ";
		}
		List list= this.entityManager.createNativeQuery(hql).getResultList();
		List<UserInfo> userList = new ArrayList<UserInfo>();
        Iterator it= list.iterator();
        while (it.hasNext())
        {
        	Object[] map = (Object[]) it.next();
        	UserInfo u = new UserInfo();
        	u.setUserId(map[0]!=null?Integer.valueOf(map[0].toString()):0);
        	u.setPhone(map[1]!=null && !"".equals(1)?(String)map[1]:null);
        	u.setGroupId(map[2]!=null?Integer.valueOf(map[2].toString()):null);
        	u.setUserName(map[3]!=null&& !"".equals(map[3])?(String)map[3]:null);
        	u.setSex(map[4]!=null?Integer.valueOf(map[4].toString()):null);
        	u.setOfficeTel(map[5]!=null&& !"".equals(map[4])?(String)map[5]:null);
        	u.setHomeTel(map[6]!=null&& !"".equals(map[6])?(String)map[6]:null);
        	u.setPhone2(map[7]!=null&& !"".equals(map[7])?(String)map[7]:null);
        	u.setJob(map[8]!=null&& !"".equals(map[8])?(String)map[8]:null);
        	u.setTitle(map[9]!=null&& !"".equals(map[9])?(String)map[9]:null);
        	u.setEmail(map[10]!=null&& !"".equals(map[10])?(String)map[10]:null);
        	u.setPy(map[11]!=null&& !"".equals(map[11])?(String)map[11]:null);
        	u.setUserNum(map[12]!=null&&!"".equals(map[12])?(String)map[12]:null);
        	u.setOrderIndex(map[13]!=null?Integer.valueOf(map[13].toString()):null);
        	u.setvNum(map[14]!=null&& !"".equals(map[14])?(String)map[14]:null);
        	u.setVNum(map[14]!=null&& !"".equals(map[14])?(String)map[14]:null);
        	u.setIsVirtual(map[15]!=null?Integer.valueOf(map[15].toString()):null);
        	u.setLinkId(map[16]!=null?Integer.valueOf(map[16].toString()):null);
        	u.setUserState(map[17]!=null?Integer.valueOf(map[17].toString()):null);
        	u.setUserPower(map[18]!=null?Integer.valueOf(map[18].toString()):null);
        	u.setSignName(map[19]!=null&& !"".equals(map[19])?(String)map[19]:null);
        	u.setRole(map[20]!=null?Integer.valueOf(map[20].toString()):null);
        	u.setPhoto(map[21]!=null&& !"".equals(map[21])?(String)map[21]:null);        	
        	u.setIsLogined(map[22]!=null?1:0);
        	u.setvGroup(map[23]!=null?map[23].toString():"");
        	u.setIsDelete(map[24]!=null?Integer.valueOf(map[24].toString()):0);
        	u.setMobileShowState(map[25]!=null?Integer.valueOf(map[25].toString()):0);
        	u.setFullPy(map[26]!=null?(String)map[26]:"");
        	u.setFormattedNumber(map[27]!=null?(String)map[27]:"");
        	u.setWorkNo(map[28]!=null?(String)map[28]:"");
        	u.setHidePhone(map[29]!=null?Integer.valueOf(map[29].toString()):0);
        	u.setIsLeader(map[30]!=null?Integer.valueOf(map[30].toString()):0);
        	userList.add(u);
        	
         }
        
		return userList;
	}
	
	@ClearParamAfterMethod
	public List<UserInfo> findByPhone(int companyId,String phone){
    	String hql = "phone = ? and isDelete = 0 ";
    	return super.companyId(companyId).partition(companyId%10).findAll(hql, phone);
    }
	
	/**
     * 
     * 功能：根据公司id和电话（或v网短号）查询人员
     * @param companyId 公司Id
     * @param phone 电话
     * @return 人员信息
     */
	@ClearParamAfterMethod
	public UserInfo getUserByCompanyIdPhone(int companyId, String phone){
        String hql = " (phone = ? or vNum=?) and companyId=? and isDelete = 0";
        return super.partition(companyId%10).companyId(companyId).findOne(hql , phone,phone, companyId);
    }
	
	/**
	 * 
	 * 功能：通过人员电话号码得到人员集合
	 * @param phones
	 * @return
	 */
	@ClearParamAfterMethod
	public List<UserInfo> getUserInfoByPhones(String phones) {
		if(StringUtils.isBlank(phones)){
			return new ArrayList<UserInfo>();
		}
		if(phones.startsWith(",")){
			phones=phones.substring(1,phones.length());
		}
		if(phones.endsWith(",")){
			phones=phones.substring(0,phones.length()-1);
		}
		String[] strs = phones.split(",");
    	String users = "";
    	for(int i=0; i<strs.length; i++){
    		String s = strs[i];
    		if(StringUtils.isNotBlank(s)){
    			users+="'"+s+"'";
        		if(i<strs.length-1){
        			users+=",";
        		}
    		}
    	}
    	String hql = "phone in ("+users+")";
    	return super.companyId().partition().unDeleted().findAll(hql);
	}
	

	/**
	 * 功能：根据主用户获得虚拟用户
	 * @param userIds
	 * @return
	 */
	@ClearParamAfterMethod
	public List<UserInfo> getVirtualUsers(String userIds){
		return this.companyId().partition().findAll(" isDelete = 0 and isVirtual = 1 and linkId in ("+userIds+")");
	}
	
	/**
     * 获取人员列表
     * @param companyId 单位ID
     * @param lastUpdateTime 上次更新时间
     * @param count 数量
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserInfo> getUserList(Integer companyId,Integer userId,String lastUpdateTime,Integer count)
    {
            String hql = "from UserInfo where companyId=? and lastUpdateTime>='"+lastUpdateTime+"'";
            Query query=this.entityManager.createQuery(hql);
            query.setParameter(1, companyId);
            query.setMaxResults(count);
			List<UserInfo> list =query.getResultList();
            return list;
    }
    
    /**
     * 根据登录名，角色code查询用户列表
     * @param loginName
     * @param roleCode
     * @return
     */
    public List<UserInfo> findUsersWithLoginNameAndRoleCode(String loginName,String roleCode){
    	String hql = " from UserInfo where isDelete = 0 and   loginName = ? and userId in "
    			+ "(select ru.userId from RoleUser ru, RoleInfo r where ru.roleId = r.roleId and r.roleCode = ? and r.isDelete = 0 )";
    	return super.find(hql, loginName,roleCode);
    }
    /**
     * 获取公司的超级管理员
     * @param companyId
     * @return
     */
	public UserInfo findDefaultUser(Integer companyId) {
		String hql = " companyId=? and isDelete = 0 and isDefault=0 ";
        return super.findOne(hql, companyId);
	}
    
    public List<UserInfo> findCompanyUsers(Integer companyId){
    	String hql ="groupId is null";
    	return super.companyId(companyId).unDeleted().findAll(hql);
    }
    
    public List<UserInfo> searchUserList(Integer companyId,String searchKey){
    	Sort sort = new Sort(Direction.ASC,"orderIndex");
    	if(searchKey==null || searchKey.equals("")){
	    	return super.companyId(companyId).unDeleted().findAll(null,sort);
    	}else{
	    	String hql = "(userName like ? or phone like ? or vNum like ? )";
	    	return super.companyId(companyId).unDeleted().findAll(hql,sort, "%"+searchKey+"%","%"+searchKey+"%","%"+searchKey+"%");
    	}
    }
   
    /**
     * 获取企业的所有子账号人员信息
     * @param companyId
     * @return
     */
	public List<UserInfo> findAccount(Integer companyId) {
		String hql = " from UserInfo where isDelete = 0 and isDefault=1 and companyId = ? and userId in "
    			+ "(select ru.userId from RoleUser ru, RoleInfo r where ru.roleId = r.roleId and r.roleCode ='manager' and r.isDelete = 0 and r.companyId=? )";
    	return super.find(hql, companyId,companyId);
	}
	
	
	public List<UserInfo> findByCompanyId(Integer companyId){
		StringBuffer hql = new StringBuffer();
		hql = hql.append(" from UserInfo where companyId = ? order by userId");
		return super.find(hql.toString(),companyId);
	}
	
	public void updateUserOrders(int userId,int orderIndex){
		String hql = "update UserInfo set orderIndex = ? where userId = ?";
		super.bulkUpdate(hql, orderIndex,userId);
	}
	
  public List<UserInfo> searchUserListByNameAndPhone(Integer companyId,String searchKey){
    	Sort sort = new Sort(Direction.ASC,"orderIndex");
    	if(searchKey == null || searchKey.equals("")){
    		return super.companyId(companyId).unDeleted().findAll(null,sort);
    	}else{
	    	String hql = "(userName like ? or phone like ? )";
	    	return super.companyId(companyId).unDeleted().findAll(hql, sort,"%"+searchKey+"%","%"+searchKey+"%");
    	}
    }
  
  @SuppressWarnings("unchecked")
  public List<Map<String,Object>> selectUserMap(Integer companyId){
	  String sql = "select u.user_id as userId,u.user_name as userName,g.group_id as groupId,g.group_name as groupName from tb_user_info u inner join tb_group_info g on u.group_id = g.group_id where u.is_delete=0 and u.company_id="+companyId;
	  sql += " order by g.group_id asc";
	  Query query = entityManager.createNativeQuery(sql);
      query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	  return (List<Map<String,Object>>)query.getResultList();
  } 
  
}

