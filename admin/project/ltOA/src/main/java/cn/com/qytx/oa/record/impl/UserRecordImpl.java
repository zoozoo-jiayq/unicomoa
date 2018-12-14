package cn.com.qytx.oa.record.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Cell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.oa.record.dao.UserRecordDao;
import cn.com.qytx.oa.record.domain.OaConst;
import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.oa.record.domain.UserRecordConst;
import cn.com.qytx.oa.record.domain.UserRecordSearchVo;
import cn.com.qytx.oa.record.service.IUserRecord;
import cn.com.qytx.oa.record.util.RandomUtils;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.CompanyDao;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

/**
 * 功能:人事档案服务接口实现类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-22
 * 修改日期: 2013-03-22
 * 修改人员: 汤波涛
 * 修改列表:初始添加方法
 */
@Service
@Transactional
public class UserRecordImpl extends BaseServiceImpl<UserRecord> implements IUserRecord {
	@Autowired
    private UserRecordDao userRecordDao;
	@Autowired
	private IUser userService;
	@Autowired
    private IGroupUser groupUserService;
	@Autowired
	private IGroup groupService;
	
	@Autowired
	private CompanyDao<CompanyInfo> companyDao;
	
	@Autowired
    private IEmailBox emailBoxService;


    /**
     * 保存一份新的人事档案信息
     *
     * @param userRecord 人事档案实例
     */
    public void save(UserRecord userRecord) {
    	  userRecord.setRecordNo(generateRecordNo());
          UserInfo adminUser = TransportUser.get();
          UserInfo userInfo = userRecord.getUserInfo();
          if(userRecord.getPhotoUrl()!=null){
          	userRecord.getUserInfo().setPhoto(userRecord.getPhotoUrl());
          }
          userRecord.setUserInfo(userInfo);
          userRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
          userRecord.setCreateUserInfo(adminUser);
          userRecord.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
          userRecord.setLastUpdateUserInfo(adminUser);
          this.userRecordDao.saveOrUpdate(userRecord);
    }

    /**
     * 保存用户部门信息，此时必须保证UserRecord中存在UserInfo且存在userId
     *
     * @param companyId 公司ID
     * @param userId    用户ID
     * @param groupIds  部门ID集合字符串
     */
    private void saveGroupInfo(int companyId, int userId, String groupIds) {
        for (String groupId : groupIds.split(UserRecordConst.RECORD_SEPARATOR)) {
            GroupUser groupUser = new GroupUser();
            groupUser.setCompanyId(TransportUser.get().getCompanyId());
            groupUser.setGroupId(Integer.parseInt(groupId));
            groupUser.setUserId(userId);
            this.groupUserService.saveOrUpdate(groupUser);
        }
    }



    /**
     * 根据组Id查询出所有的人事档案列表
     *
     * @param page    翻页信息
     * @param groupId 组ID
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordByGroupId(Pageable page, int groupId,Integer treeType) {
//    	String hql = " x.userInfo.userId in (select gu.userId from GroupUser as gu where groupId=?) and x.isDelete=?";
    	String hql = "  x.isDelete=?";//and x.userInfo.isDelete=?
    	if(groupId!=0){
    		hql += " and x.userInfo.groupId = "+groupId;
    	}
    	if(treeType!=null&&treeType.intValue()==2){
    		hql += " and x.userInfo.userId in (select rl.userInfo.userId from RecordLeave rl)";
    	}else{
    		hql += " and x.userInfo.userId not in (select rl.userInfo.userId from RecordLeave rl)";
    	}
        return this.userRecordDao.findAll( hql, page,  OaConst.NOT_DELETE);
    }
    
    /**
     * 根据姓名查询出所有的人事档案列表
     *
     * @param page    翻页信息
     * @param groupId 组ID
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordList(Pageable page,Integer treeType,String userName) {
//    	String hql = " x.userInfo.userId in (select gu.userId from GroupUser as gu where groupId=?) and x.isDelete=?";
    	
        return this.userRecordDao.findAllUserRecordList(page, treeType, userName);
    }
    
    
    
    
    

    /**
     * 根据高级搜索VO对象查询
     * update by jiayq，添加查询条件groupIds
     * @param page     翻页信息
     * @param searchVo 高级搜索信息
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordBySearchVo(Pageable page, UserRecordSearchVo searchVo,String groupIds) throws Exception {

        LinkedList<Object> valueList = new LinkedList<Object>();
        StringBuilder hql = new StringBuilder("");
        hql.append(" isDelete=? and userInfo.isDelete=0 ");
        
        //add by jiayq
        if(groupIds!=null && !groupIds.equals("")){
        	hql.append(" and userInfo.userId in (select userId from UserInfo where groupId in ("+groupIds+")  ) ");
        }
        valueList.add(OaConst.NOT_DELETE);

        //不需要反射处理的特殊字段
        ArrayList<String> specialProcessList = new ArrayList<String>();
        specialProcessList.add("birthDayStart");//数据存储在userInfo中，搜索字段在VO中，特殊处理
        specialProcessList.add("birthDayEnd");//数据存储在userInfo中，搜索字段在VO中，特殊处理
        specialProcessList.add("serialVersionUID");//类版本字段和数据库无关
        specialProcessList.add("groupIds");
      //  specialProcessList.add("groupNames");
        specialProcessList.add("roleIds");
       // specialProcessList.add("roleNames");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotEmpty(searchVo.getBirthDayStart())) {
            hql.append(" and birthDay>=?");
            valueList.add(format.parse(searchVo.getBirthDayStart()));
        }
        if (StringUtils.isNotEmpty(searchVo.getBirthDayEnd())) {
            hql.append(" and birthDay<=?");
            valueList.add(format.parse(searchVo.getBirthDayEnd()));
        }
        if (StringUtils.isNotEmpty(searchVo.getGroupIds())) {
            hql.append(" and userInfo.groupId in (");
            hql.append(searchVo.getGroupIds());
            hql.append(")");
        }
        if (StringUtils.isNotEmpty(searchVo.getRoleIds())) {
            hql.append(" and userInfo.userId in (select ru.userId from RoleUser as ru where ru.roleId in (");
            hql.append(searchVo.getRoleIds());
            hql.append("))");
        }
        //添加权限hql
//        UserInfo loginUser = this.commonService.getLoginedUser();
//        hql.append(this.modulePrivService.generatePrivHql(loginUser.getUserId(), loginUser.getCompanyId(),
//                "人事档案查询", "userInfo.userId"));

        //遍历所有searchVo不为空的字段进行搜索
        Field[] fields = UserRecordSearchVo.class.getDeclaredFields();
        Field[] fieldsSuper = UserRecordSearchVo.class.getSuperclass().getDeclaredFields();
        Field[] totalFields = new Field[fields.length + fieldsSuper.length];
        System.arraycopy(fields, 0, totalFields, 0, fields.length);
        System.arraycopy(fieldsSuper, 0, totalFields, fields.length, fieldsSuper.length);
        for (Field field : totalFields) {
            String fieldName = field.getName();
            if (specialProcessList.contains(fieldName)) {
                continue;
            }
            String methodNamePart = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String getMethodName = "get" + methodNamePart;
            //从新对象中执行get方法获取新的值
            Object value = UserRecordSearchVo.class.getMethod(getMethodName, new Class[]{}).invoke(searchVo);
            if (value != null && StringUtils.isNotEmpty(String.valueOf(value))) { //有搜索值，处理
                //处理UserInfo中的字段搜索
                if (value instanceof UserInfo) {
                    UserInfo userInfo = (UserInfo) value;
                    /**获得继承对象**/
    				Field[] superArr = UserInfo.class.getSuperclass().getDeclaredFields();
    				Field[] currentArr = UserInfo.class.getDeclaredFields();
    				Field[] newArr = new Field[(superArr.length+currentArr.length)];
    				int i =0;
    				if(superArr!=null&&superArr.length>0){
    					for(Field object : superArr){
    						newArr[i]=object;
    						i++;
    					}
    				}
    				if(currentArr!=null&&currentArr.length>0){
    					for(Field object : currentArr){
    						newArr[i]=object;
    						i++;
    					}
    				}
    				
    				/**获得继承对象 end**/
                    for (Field userInfoField : newArr) {
                        String userInfoFieldName = userInfoField.getName();
                        String userInfoMethodNamePart = userInfoFieldName.substring(0, 1).toUpperCase() + userInfoFieldName.substring(1);
                        if(userInfoMethodNamePart.equals("USERSTATE_UNLOGIN")||userInfoMethodNamePart.equals("USERSTATE_LOGIN")||userInfoMethodNamePart.equals("USERSTATE_FORBIDDEN_LOGIN")){
    						continue;
    					}
                        String userInfoGetMethodName = "get" + userInfoMethodNamePart;
                        Object userInfoValue = UserInfo.class.getMethod(userInfoGetMethodName, new Class[]{}).invoke(userInfo);
                        if (userInfoValue != null && StringUtils.isNotEmpty(String.valueOf(userInfoValue))) {
                        	System.out.println("userInfoFieldName"+userInfoFieldName+"  userInfoValue： "+userInfoValue);
                            if (userInfoValue instanceof Integer){
                              //除birthDay外的搜索
                            	if(userInfoValue!=null&&(Integer)userInfoValue>=0){
                            		 String tempHql = " and userInfo." + userInfoFieldName + " = ?  ";
                            		 System.out.println(" and userInfo." + userInfoFieldName + " = "+userInfoValue);
                                      hql.append(tempHql);
                                       valueList.add(userInfoValue);
                            	}
                               
                            }else{
                                //除birthDay外的搜索
                            	userInfoValue = userInfoValue.toString().replaceAll("%", "/%");
                            	userInfoValue = userInfoValue.toString().replaceAll("_", "/_");
                                String tempHql = " and userInfo." + userInfoFieldName + " like ?  escape '/'";
                                System.out.println(" and userInfo." + userInfoFieldName + " like "+userInfoValue);
                                hql.append(tempHql);
                                valueList.add("%"+userInfoValue+"%");
                            }
                            
                        }
                    }
                } else if (fieldName.endsWith("Start")) {//处理起始日期搜索
                    String fieldNameDB = fieldName.substring(0, fieldName.lastIndexOf("Start"));
                    String tempHql = " and " + fieldNameDB + ">='"+value+"' ";
                    hql.append(tempHql);
                   
                    //valueList.add(value);
                } else if (fieldName.endsWith("End")) { //处理终止日期搜索
                    String fieldNameDB = fieldName.substring(0, fieldName.lastIndexOf("End"));
                    String tempHql = " and " + fieldNameDB + "<='"+value+"' ";
                    hql.append(tempHql);
                    //valueList.add(value);
                } else {
                	if(!fieldName.equals("createTime")){
                		String tempHql = " and cast(  " + fieldName + " as string)  like ?";
                		if(fieldName.equals("nativityPlace")){
                			tempHql = " and (nativityPlaceSelect+nativityPlace) like ? ";
                		}
	                     hql.append(tempHql);
	                     valueList.add("%"+value+"%");
	                }
//                    System.out.println(" and  " + fieldName + " like "+value);
                }
            }
        }
        return this.userRecordDao.findAll(hql.toString(), page, valueList.toArray());
    }
    /**
     * 6月18日  李贺 修改
     * 根据高级搜索VO对象查询
     * update by jiayq，添加查询条件groupIds
     * @param page     翻页信息
     * @param searchVo 高级搜索信息
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordBySearchVo_new(Pageable page, UserRecordSearchVo searchVo,String groupIds) throws Exception {
    	
    	Map<String, String> key_word = new HashMap<String, String>();
    	LinkedList<Object> valueList = new LinkedList<Object>();
    	StringBuilder hql = new StringBuilder("");
    	hql.append(" isDelete=? and userInfo.isDelete=0 ");
    	
    	//add by jiayq
    	if(groupIds!=null && !groupIds.equals("")){
    		hql.append(" and userInfo.userId in (select userId from UserInfo where groupId in ("+groupIds+")  ) ");
    	}
    	valueList.add(OaConst.NOT_DELETE);
    	
    	//不需要反射处理的特殊字段
    	ArrayList<String> specialProcessList = new ArrayList<String>();
    	specialProcessList.add("birthDayStart");//数据存储在userInfo中，搜索字段在VO中，特殊处理
    	specialProcessList.add("birthDayEnd");//数据存储在userInfo中，搜索字段在VO中，特殊处理
    	specialProcessList.add("serialVersionUID");//类版本字段和数据库无关
    	specialProcessList.add("groupIds");
    	specialProcessList.add("keyWord");
    	//specialProcessList.add("groupNames");
    	specialProcessList.add("roleIds");
    	//specialProcessList.add("roleNames");
    	
    	if (searchVo.getSex()!=null&&searchVo.getSex().intValue()>=0) {
    		hql.append(" and sex =?");
    		valueList.add(searchVo.getSex().intValue());
    	}

    	if (StringUtils.isNotEmpty(searchVo.getKeyWord())) {
    		hql.append(" and ( userName like ? ) ");
    		valueList.add("%"+searchVo.getKeyWord()+"%");
    	}
    	
    	if (StringUtils.isNotEmpty(searchVo.getBirthDayStart())) {
    		hql.append(" and birthDay>=?");
    		valueList.add(searchVo.getBirthDayStart());
    	}
    	if (StringUtils.isNotEmpty(searchVo.getBirthDayEnd())) {
    		hql.append(" and birthDay<=?");
    		valueList.add(searchVo.getBirthDayEnd());
    	}
    	if (StringUtils.isNotEmpty(searchVo.getGroupIds())) {
    		hql.append(" and userInfo.userId in (select gu.userId from GroupUser as gu where gu.groupId in (");
    		hql.append(searchVo.getGroupIds());
    		hql.append("))");
    	}
    	if (StringUtils.isNotEmpty(searchVo.getRoleIds())) {
    		hql.append(" and userInfo.userId in (select ru.userId from RoleUser as ru where ru.roleId in (");
    		hql.append(searchVo.getRoleIds());
    		hql.append("))");
    	}
    	//添加权限hql
//        UserInfo loginUser = this.commonService.getLoginedUser();
//        hql.append(this.modulePrivService.generatePrivHql(loginUser.getUserId(), loginUser.getCompanyId(),
//                "人事档案查询", "userInfo.userId"));
    	
    	//遍历所有searchVo不为空的字段进行搜索
    	Field[] fields = UserRecordSearchVo.class.getDeclaredFields();
    	Field[] fieldsSuper = UserRecordSearchVo.class.getSuperclass().getDeclaredFields();
    	Field[] totalFields = new Field[fields.length + fieldsSuper.length];
    	System.arraycopy(fields, 0, totalFields, 0, fields.length);
    	System.arraycopy(fieldsSuper, 0, totalFields, fields.length, fieldsSuper.length);
    	for (Field field : totalFields) {
    		String fieldName = field.getName();
    		if (specialProcessList.contains(fieldName)) {
    			continue;
    		}
    		String methodNamePart = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    		String getMethodName = "get" + methodNamePart;
    		//从新对象中执行get方法获取新的值
    		Object value = UserRecordSearchVo.class.getMethod(getMethodName, new Class[]{}).invoke(searchVo);
    		if (value != null && StringUtils.isNotEmpty(String.valueOf(value))) { //有搜索值，处理
    			//处理UserInfo中的字段搜索
    			if (value instanceof UserInfo) {
    				UserInfo userInfo = (UserInfo) value;
    				/**获得继承对象**/
    				Field[] superArr = UserInfo.class.getSuperclass().getDeclaredFields();
    				Field[] currentArr = UserInfo.class.getDeclaredFields();
    				Field[] newArr = new Field[(superArr.length+currentArr.length)];
    				int i =0;
    				if(superArr!=null&&superArr.length>0){
    					for(Field object : superArr){
    						newArr[i]=object;
    						i++;
    					}
    				}
    				if(currentArr!=null&&currentArr.length>0){
    					for(Field object : currentArr){
    						newArr[i]=object;
    						i++;
    					}
    				}
    				
    				/**获得继承对象 end**/
    				for (Field userInfoField : newArr) {
    					String userInfoFieldName = userInfoField.getName();
    					String userInfoMethodNamePart = userInfoFieldName.substring(0, 1).toUpperCase() + userInfoFieldName.substring(1);
    					if(userInfoMethodNamePart.equals("USERSTATE_UNLOGIN")||userInfoMethodNamePart.equals("USERSTATE_LOGIN")||userInfoMethodNamePart.equals("USERSTATE_FORBIDDEN_LOGIN")){
    						continue;
    					}
						String userInfoGetMethodName = "get" + userInfoMethodNamePart;
						Object userInfoValue = UserInfo.class.getMethod(userInfoGetMethodName, new Class[]{}).invoke(userInfo);
    					if (userInfoValue != null && StringUtils.isNotEmpty(String.valueOf(userInfoValue))) {
    						if (userInfoValue instanceof Integer){
    								if("mobileShowState".equals(userInfoFieldName)){
    									continue;
    								}
    							if(userInfoValue!=null&&(Integer)userInfoValue>=0){
    								String tempHql = " and userInfo." + userInfoFieldName + " = ?  ";
    								System.out.println(" and userInfo." + userInfoFieldName + " = "+userInfoValue);
    								hql.append(tempHql);
    								valueList.add(userInfoValue);
    							}
    						}else{
    							if("userName".equals(userInfoFieldName)){
    								key_word.put("userName", userInfoFieldName);
    								valueList.add("%"+userInfoValue+"%");
    							}
    							if( "phone".equals(userInfoFieldName)){
    								key_word.put("phone", userInfoFieldName);
    								valueList.add("%"+userInfoValue+"%");
    							}
    							if(key_word.size() == 2){
    								userInfoValue = userInfoValue.toString().replaceAll("%", "/%");
    								userInfoValue = userInfoValue.toString().replaceAll("_", "/_");
    								String tempHql = " and (userInfo." + key_word.get("userName") + " like ?  escape '/' or userInfo." + key_word.get("phone") + " like ?  escape '/')";
    								hql.append(tempHql);
    							}
    						}
    						
    					}
    				}
    			}
    		}
    	}
    	return this.userRecordDao.findAll(hql.toString(), page, valueList.toArray());
    }


    /**
     * 根据人事档案ID查询一份人事档案
     *
     * @param id 人事档案ID
     * @return 符合该ID的人事档案
     */
    public UserRecord findById(int id) {
        return this.userRecordDao.findById(id);
    }

    /**
     * 根据用户ID查询一份人事档案
     *
     * @param userId 用户ID
     * @return 符合该ID的人事档案
     */
    public UserRecord findByUserId(int userId) {
        String condition = " isDelete=? and userInfo.userId=?";
        return (UserRecord) this.userRecordDao.findOne(condition, OaConst.NOT_DELETE, userId);
    }
    
    /**
     * 功能：根据用户ID删除档案信息
     * @param userId
     */
    public void deleteByUserIds(String userIds){
        userRecordDao.deleteByUserIds(userIds);
    }

    /**
     * 生成档案编号
     *
     * @return 生产的唯一档案编号
     */
    public String generateRecordNo() {
        return RandomUtils.createRandomStringFromDate();
    }

    public void setUserRecordDao(UserRecordDao userRecordDao) {
        this.userRecordDao = userRecordDao;
    }

	@Override
	public List<TreeNode> userRecordTree(UserInfo userInfo,
			GroupInfo forkGroup, int treeType, String path) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
      	CompanyInfo companyInfo = companyDao.findOne(userInfo.getCompanyId());
        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
        if(companyInfo!=null)
        {
            if(forkGroup==null){
            	TreeNode treeHead = new TreeNode();
            	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
            	treeHead.setName(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
              	treeHead.setTitle(StringUtils.isBlank(companyInfo.getShortName())?companyInfo.getCompanyName():companyInfo.getShortName());
            	treeHead.setPId("gid_-1");
            	treeHead.setIcon(path + "/images/company.png");
            	treeHead.setOpen(true);
            	treeNodes.add(treeHead);
            	groupList = groupService.getGroupList(companyInfo.getCompanyId(), 1);
            }else{
            	TreeNode treeHead = new TreeNode();
            	treeHead.setId("gid_"+forkGroup.getGroupId());
            	treeHead.setName(forkGroup.getGroupName());
            	treeHead.setTitle(forkGroup.getGroupName());
            	treeHead.setPId("gid_-1");
            	treeHead.setIcon(path + "/images/group.png");
            	treeHead.setOpen(true);
            	treeNodes.add(treeHead);
            	groupList = groupService.getSubGroupList(forkGroup.getGroupId());
            }
        }
        if (groupList != null)
        {
            String ids = "";
            // 遍历部门
            for (GroupInfo group : groupList)
            {
                ids += group.getGroupId() + ",";
               // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
                TreeNode treeNode = new TreeNode();
                treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
                treeNode.setName(group.getGroupName());
                treeNode.setTitle(group.getGroupName());
                treeNode.setObj(group.getOrderIndex());
                treeNode.setPId("gid_" + group.getParentId().toString());
                treeNode.setIcon(path + "/images/group.png");
                treeNodes.add(treeNode);
            }
            // 显示人员
            if (ids.endsWith(","))
            {
                ids = ids.substring(0, ids.length() - 1);
            }
            if(forkGroup!=null){
            	ids+=","+forkGroup.getGroupId();
            }
            // 遍历人员
            List<UserInfo> userList = userRecordDao.findUserListByType(userInfo.getCompanyId(),treeType);
            if (userList != null)
            {
                for (UserInfo user : userList)
                {
                    TreeNode treeNode = new TreeNode();
                    treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
                    treeNode.setName(user.getUserName());
                    treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
                    treeNode.setPId("gid_"
                            + user.getGroupId());
                    if (null != user.getSex() && 0 == user.getSex()){
                            treeNode.setIcon(path + "/images/woman.png");
                        }
                        else{
                            treeNode.setIcon(path + "/images/man.png");
                        }
                    treeNodes.add(treeNode);
                }
            }
        }
		
		return treeNodes;
	}
	
	/**
	 * 导入保存
	 * @param cells
	 * @param loginUser
	 * @return
	 */
	public String importSave(Cell[] cells, UserInfo loginUser,Map<String, Map<String, Integer>> baseDataMap) {
		List<String> params = new ArrayList<String>();
		params.add(loginUser.getCompanyId().toString());//公司id
		params.add(loginUser.getUserId().toString());//创建人id
		params.add(generateRecordNo());//档案编号
		
		for (int i = 0; i < 53; i++) {
		   if(StringUtils.isBlank(cells[i].getContents())){
				params.add(null);
		   }else{
			   if(i ==9){//婚姻状况
		        	Map<String, Integer> map = baseDataMap.get("marriage_status");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==11){//政治面貌
		        	Map<String, Integer> map = baseDataMap.get("politics_face");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==13){//户口类型
		        	Map<String, Integer> map = baseDataMap.get("registered_type");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==17){//员工类型
		        	Map<String, Integer> map = baseDataMap.get("user_type");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==18){//职称
		        	Map<String, Integer> map = baseDataMap.get("job_title");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==20){//职称级别
		        	Map<String, Integer> map = baseDataMap.get("job_title_level");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==21){//岗位
		        	Map<String, Integer> map = baseDataMap.get("station");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==24){//在职状态
		        	Map<String, Integer> map = baseDataMap.get("work_status");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==35){//学历
		        	Map<String, Integer> map = baseDataMap.get("edu_qualifications");
		        	setCellValue(cells, params, i, map);
		        }else if(i ==36){//学位
		        	Map<String, Integer> map = baseDataMap.get("edu_level");
		        	setCellValue(cells, params, i, map);
		        }else{
		        	params.add(cells[i].getContents());
		        }
		   }
	       
		}
		return userRecordDao.myExecuteProcedure("sp_import_user_record", params);
	}

	/**
	 * 验证单元格是否为空
	 * @param cells
	 * @param params
	 * @param i
	 * @param map
	 */
	private void setCellValue(Cell[] cells, List<String> params, int i,
		Map<String, Integer> map) {
		String temp = cells[i].getContents();
		if(StringUtils.isNoneBlank(temp)){
			params.add(map.get(temp)==null?null:map.get(temp).toString());
		}else{
			params.add(null);
		}
	}

	
}
