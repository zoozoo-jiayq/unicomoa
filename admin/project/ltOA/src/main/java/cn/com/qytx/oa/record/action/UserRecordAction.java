package cn.com.qytx.oa.record.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.oa.record.domain.UserRecordConst;
import cn.com.qytx.oa.record.domain.UserRecordSearchVo;
import cn.com.qytx.oa.record.service.IUserRecord;
import cn.com.qytx.oa.util.UpdateFieldUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;

import com.opensymphony.xwork2.ActionContext;

/**
 * 功能:人事档案处理Controller，包含：增、删、改、查、列表等Action
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-22
 * 修改日期: 2013-03-22
 * 修改人员: 汤波涛
 * 修改列表:初始方法加入
 */
public class UserRecordAction extends BaseActionSupport {

	private static final long serialVersionUID = 8796556472885282664L;

	private final static Logger LOGGER = Logger.getLogger(UserRecordAction.class);

    @Autowired
    private IUserRecord userRecordService;

    @Autowired
    private IGroup groupService;

    @Autowired
    private IRole roleService;

    @Autowired
    private IUser userService;

    @Autowired
    private IDict dictService;
    
    /**
     * 判断是否是从子菜单返回的
     */
    private Integer index;
    
    /**
     * 标记来自搜索
     */
    private static final String FROM_SEARCH = "search";

    /**
     * 部门（组）Id
     */
    private int groupId;

    /**
     * 部门（组）名称
     */
    private String groupName;

    /**
     * 人事档案ID
     */
    private Integer id;

    /**
     * 人事档案ID集合，英文逗号分割
     */
    private String ids;

    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 当前手机端登录的用户id
     */
    private Integer loginUserNow;

    /**
     * 人事档案对象
     */
    private UserRecord userRecord;

    /**
     * 登录名
     */
    private String loginName;
    
    /**
     * 手机端传过来的搜索条件
     */
    private String searchName;

    /**
     * 错误消息
     */
    private String errorMsg = null;


    /**
     * 搜索VO对象
     */
    private UserRecordSearchVo userRecordSearchVo;

    /**
     * from 哪个功能
     */
    private String from;

    /**
     * 搜索EmailSearchVo的Json对象
     */
    private String searchJson;

    /**
     * 请求详细页面的来源 android安卓
     */
    private String requestSrc;
    
    
    private String type;
    
    private String birthDay;
    
    private Integer treeType;
    
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 人事档案打开时的首页面（框架页面），加载初始值
     *
     * @return success 跳转入人事档案主页面
     */
    public String mainPage() {

        UserInfo adminUser = getLoginUser();
        List<GroupInfo> groupInfoList = new ArrayList<GroupInfo>();
        GroupInfo g =  groupService.getGroupByUserId(adminUser.getCompanyId(), adminUser.getUserId());
        groupInfoList.add(g);
        //System.out.println(groupInfoList.size());
        ActionContext.getContext().put("groupInfoList", groupInfoList);
        return SUCCESS;
    }

    public String searchPage() {
        if (StringUtils.isNotEmpty(searchJson)) {
            this.userRecordSearchVo = UserRecordSearchVo.getInstanceBySearchJson(this.searchJson);
        }
        putBaseData2Context();
        return SUCCESS;
    }

    /**
     * 进入List列表页面
     *
     * @return success进入列表页面
     */
    public String list() {
        if (this.userRecordSearchVo != null) {
            this.searchJson = userRecordSearchVo.toJson();
            this.from = FROM_SEARCH;
        } else {
        	if(this.groupId==0){
        		this.groupName = "驻马店投资公司";
        	}else{
        		GroupInfo groupInfo = this.groupService.findOne(this.groupId);
                if (groupInfo == null) {
                    LOGGER.error("指定的部门信息不存在，groupId:" + this.groupId);
                    return ERROR;
                }
                this.groupName = groupInfo.getGroupName();
        	}
            
        }
        return SUCCESS;
    }

    /**
     * ajax获取list列表数据
     *
     * @return JSON数据到页面
     */
    public String listAjax() {

        //System.out.println(iDisplayLength);
        //System.out.println(iDisplayStart);

        boolean isFromSearch = StringUtils.equals(this.from, FROM_SEARCH);
        GroupInfo groupInfo = null;
        if(this.groupId!=0){
        	  groupInfo = this.groupService.findOne(this.groupId);
             if (!isFromSearch && groupInfo == null) {
                 if (!StringUtils.equals(this.from, "manager")){
                     LOGGER.error("指定的部门信息不存在，groupId:" + this.groupId);
                     return ERROR;
                 }
             }
        }else{
        	groupInfo = new GroupInfo();
        }
       

        try {
           
            Page<UserRecord> page = null;
            Sort sort = new Sort(new Sort.Order(Direction.ASC, "lastUpdateTime"));
            UserInfo adminUser=null;
            if(userId!=null){
            	adminUser=userService.findOne(userId);
                page =userRecordService.findAllUserRecordList(getPageable(sort), treeType, searchName);
            }else{
            	adminUser= this.getLoginUser();
            	//add by jiayq，添加二级局限制条件
                GroupInfo forkGroup = groupService.getForkGroup(adminUser.getCompanyId(),adminUser.getUserId());
                String searchGroups = "";
                if(forkGroup!=null){
                	List<GroupInfo> glist = groupService.getSubGroupList(forkGroup.getGroupId());
                	glist.add(forkGroup);
                	for(Iterator it = glist.iterator(); it.hasNext(); ){
                		GroupInfo ginfo = (GroupInfo) it.next();
                		searchGroups += ginfo.getGroupId();
                		if(it.hasNext()){
                			searchGroups+=",";
                		}
                	}
                }
                if (isFromSearch) {//处理高级搜索
                	//
                	 UserRecordSearchVo userRecoderSearchVo = UserRecordSearchVo.getInstanceBySearchJson(this.searchJson);
                	 if(userRecoderSearchVo!=null){
                		 UserInfo  userInfo = userRecoderSearchVo.getUserInfo();
                		 if(userInfo!=null){
                			 userInfo.setSignType(null);
                		 }
                		 userRecoderSearchVo.setGroupNames("");
                		 if(StringUtils.isNotBlank(userRecoderSearchVo.getJoinTimeStart())){
                			 userRecoderSearchVo.setJoinTimeStart(userRecoderSearchVo.getJoinTimeStart()+" 00:00:00");
                		 }
                	 }
                    page = this.userRecordService.findAllUserRecordBySearchVo(this.getPageable(), userRecoderSearchVo,searchGroups);
                } else {
                    if (StringUtils.equals(this.from, "manager")){
                        if (null == userRecordSearchVo){
                            userRecordSearchVo = new UserRecordSearchVo();
                        }else{
                            if (null != userRecordSearchVo.getUserInfo()){
                                Integer sex = userRecordSearchVo.getUserInfo().getSex();
                                if (null != sex && -1 == sex.intValue()){
                                    userRecordSearchVo.getUserInfo().setSex(null);
                                }
                                Integer signType = userRecordSearchVo.getUserInfo().getSignType();
                                if (null != signType ){
                                    userRecordSearchVo.getUserInfo().setSignType(null);
                                }
                            }
                            
                        }

                        page = this.userRecordService.findAllUserRecordBySearchVo_new(this.getPageable(), userRecordSearchVo,searchGroups);
                    }else{
                        page = this.userRecordService.findAllUserRecordByGroupId(this.getPageable(sort), groupInfo.getGroupId()!=null?groupInfo.getGroupId():0,treeType);
                    }
                    
                }
            }
            SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");
            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            Map<Integer, String> groupMap= groupInfoMap();
            int i = 0;
            for (UserRecord userRecord : page.getContent()) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                UserInfo userInfo = userRecord.getUserInfo();
                i++;
                dataMap.put("num",  i);
                dataMap.put("id", userRecord.getId());
                dataMap.put("userName", userRecord.getUserName()!=null?userRecord.getUserName().toString():"&nbsp;");
                dataMap.put("sex", userRecord.getSex()!=null?userRecord.getSex():1);
                if(userRecord.getBirthDay()!=null){
                	dataMap.put("birthDay", sdfYmd.format(userRecord.getBirthDay()));
                }else{
                	dataMap.put("birthDay", "");
                }
                
                String job = userRecord.getJob();
                if(StringUtils.isNotBlank(job)){
                	dataMap.put("job", userRecord.getJob());
                }else{
                	dataMap.put("job", "&nbsp;");
                }
                
                
                dataMap.put("nationality", StringUtils.defaultString(userRecord.getNationality()));//民族
                dataMap.put("nativityPlace", StringUtils.defaultString(userRecord.getNativityPlaceSelect()) + StringUtils.defaultString(userRecord.getNativityPlace()));//籍贯
                dataMap.put("politicsFace", getBaseDataValue("politics_face", userRecord.getPoliticsFace()));//政治面貌
                dataMap.put("identityNo", StringUtils.defaultString(userRecord.getIdentityNo()));
                // 手机号码
                dataMap.put("phone", StringUtils.defaultString(userRecord.getPhone()));
                dataMap.put("job", StringUtils.defaultString(userRecord.getJob()));
                dataMap.put("groupName",groupMap.containsKey(userRecord.getUserInfo().getGroupId())?groupMap.get(userRecord.getUserInfo().getGroupId()):"--" );
                aaDataList.add(dataMap);
            }

            this.ajaxPage(page,aaDataList);
        } catch (Exception e) {
        	e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
        } 
        return null;
    }

    
    /**
     * 获得部门map
     * @return
     */
    private Map<Integer, String> groupInfoMap(){
    	Map<Integer, String> map =new HashMap<Integer, String>();
    	List<GroupInfo> list=groupService.findAll();
    	if(list!=null && list.size()>0){
    		for(GroupInfo g:list){
    			map.put(g.getGroupId(),g.getGroupName());
    		}
    	}
		return map;
    }
    
    
    
    /**
     * OA用户的不可重复性检查
     *
     * @return ajax Text
     */
    public String checkLoginName() {
        PrintWriter out = null;
        String result = "1";//1代表通过检查
        try {
            out = getResponse().getWriter();
            if (null==userId) {
                result = "没有传入userId";
            } else {
                UserRecord userInfoCheck = this.userRecordService.findByUserId(userId);
                if (userInfoCheck != null) {
                    result = "0";
                }
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 新建人事页面打开前的预处理Action
     *
     * @return success 转入新增人事档案的jsp
     */
    public String create() {
        
        if (groupId != 0){
            GroupInfo groupInfo = this.groupService.findOne(this.groupId);
            if (groupInfo == null) {
                LOGGER.error("指定的部门信息不存在，groupId:" + this.groupId);
                return ERROR;
            }
            this.userRecord = new UserRecord();
            UserInfo userInfo = new UserInfo();
            userInfo.setGroupName(groupInfo.getGroupName());
            this.userRecord.setUserInfo(userInfo);
            this.userRecord.setGroupNames(groupInfo.getGroupName());
            this.userRecord.setGroupIds(groupInfo.getGroupId().toString());
        }        
        this.from = "create";
        putBaseData2Context();
        return SUCCESS;
    }

    /**
     * 根据用户ID新建或者修改人事档案
     *
     * @return succee 人事档案编辑页面
     */
    public String createOrEditByUserId() {
        String fromLocal = this.from;
        SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");
        UserInfo userInfo = this.userService.findOne( this.userId);
        if (userInfo == null) {
            LOGGER.error("不存在的人员,id:" + this.userId);
            return ERROR;
        }
        Date birthday = userInfo.getBirthDay();

        if(birthday!=null){
        	userInfo.setBirthDay(birthday);
        }
        
        UserRecord userRecordDB = this.userRecordService.findByUserId(userInfo.getUserId());
        
        // 判断是否为手机端访问, 手机访问跳转到wap/logined/addressbook/detail_company.jsp
        boolean isWap = false;
        if (!StringUtils.isEmpty(this.from) && "wap".equals(this.from))
        {
            isWap = true;
        }

        this.from = "edit";
        if (userRecordDB == null)
        {
            this.from = "create";
            userRecordDB = new UserRecord();
            userRecordDB.setUserInfo(userInfo);
            return "noExist";
        }
        this.userRecord = userRecordDB;
        String[] groupNamesIds = getGroupNamesAndIds(userInfo);
        this.userRecord.setGroupNames(groupNamesIds[0]);
        this.userRecord.setGroupIds(groupNamesIds[1]);
        String[] roleNamesIds = getRoleNamesAndIds(userInfo);
        this.userRecord.setRoleNames(roleNamesIds[0]);
        // this.userRecord.setRoleIds(roleNamesIds[1]);
        putBaseData2Context();
        
        if ("onLine".equals(fromLocal))
        {
            return "onLine";
        }else if ("detail".equals(fromLocal)){
            return "detail";
        }
        return isWap ? "wapSuccess" : SUCCESS;
    }


    /**
     * 保存一份新的人事档案
     *
     * @return success 跳转入该条人事档案的详情页面jsp
     */
    public String save() {
        this.from = "create";
        putBaseData2Context();
//        String loginName = this.userRecord.getUserInfo().getLoginName();
//        if (StringUtils.isEmpty(loginName)) {
//            this.errorMsg = "登录名称不能为空";
//            return INPUT;
//        }
//        UserInfo userInfoCheck = this.userService.getUserByUserName(loginName);
//        if (this.userRecord.getUserInfo().getUserId() == null && userInfoCheck != null) {
//            this.errorMsg = "登录名:" + loginName + " 已经存在";
//            return INPUT;
//        }

        if (StringUtils.isEmpty(this.userRecord.getUserName())) {
            this.errorMsg = "姓名不能为空";
            return INPUT;
        }

        if (this.userRecord.getSex() == null) {
            this.errorMsg = "性别不能为空";
            return INPUT;
        }
        if (StringUtils.isEmpty(this.userRecord.getPhone())) {
            this.errorMsg = "手机号码不能为空";
            return INPUT;
        }
        if (this.userRecord.getBirthDay()==null) {
            this.userRecord.setBirthDay(null);
        }
        
        
        userRecord.setCompanyId(getLoginUser().getCompanyId());
        this.userRecordService.save(this.userRecord);
        return SUCCESS;
    }

    /**
     * 修改一份档案，打开编辑页面
     *
     * @return 返回到jsp，打开编辑页面
     */
    public String edit() {
        putBaseData2Context();
        this.userRecord = userRecordService.findById(this.id);
        if (this.userRecord == null) {
            LOGGER.error("找不到符合的人事档案，ID:" + this.id);
            return ERROR;
        }
        UserInfo userInfo = this.userRecord.getUserInfo();
        String[] groupNamesIds = getGroupNamesAndIds(userInfo);
       // Date birthDayDate = userInfo.getBirthDay();
        //if(birthDayDate!=null){
        //	birthDay = format.format(birthDayDate);
        ///}
        this.userRecord.setGroupNames(groupNamesIds[0]);
        this.userRecord.setGroupIds(groupNamesIds[1]);
        String[] roleNamesIds = getRoleNamesAndIds(userInfo);
        this.userRecord.setRoleNames(roleNamesIds[0]);
        this.userRecord.setRoleIds(roleNamesIds[1]);
        this.from = "edit";
        return SUCCESS;
    }

    /**
     * 人事档案更新保存方法
     *
     * @return success 跳转到详情页面，error：系统错误页面
     */
    public String update() {
        putBaseData2Context();
        this.from = "edit";
        if (StringUtils.isEmpty(this.userRecord.getLoginName())) {
            this.errorMsg = "登录名称不能为空";
            return INPUT;
        }
        if (StringUtils.isEmpty(this.userRecord.getUserName())) {
            this.errorMsg = "姓名不能为空";
            return INPUT;
        }

        if (this.userRecord.getSex() == null) {
            this.errorMsg = "性别不能为空";
            return INPUT;
        }
        if (StringUtils.isEmpty(this.userRecord.getPhone())) {
            this.errorMsg = "手机号码不能为空";
            return INPUT;
        }
        if (this.userRecord.getBirthDay()==null) {
            this.userRecord.setBirthDay(null);
        }

        try {
            UserRecord userRecordDB = this.userRecordService.findById(this.userRecord.getId());
            if (userRecordDB == null) {
                LOGGER.error("不存在的人事档案，ID：" + this.userRecord.getId());
                return ERROR;
            }
            UserInfo userInfo = this.userRecord.getUserInfo();
            
            //更新UserInfo表
            UpdateFieldUtil.update(userInfo, userRecordDB.getUserInfo(), "userId", "loginName","USERSTATE_UNLOGIN","USERSTATE_LOGIN","USERSTATE_FORBIDDEN_LOGIN");//不能更新用户Id和登录名
            //更新UserRecord表
            UpdateFieldUtil.update(this.userRecord, userRecordDB, "userInfo");//不能更新UserInfo对象（上一行已经单独更新）
            if(userRecord.getPhotoUrl()!=null){
            	userRecordDB.getUserInfo().setPhoto(userRecord.getPhotoUrl());
            }
            userRecordDB.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
            userRecordDB.setLastUpdateUserInfo(this.getLoginUser());
            this.userRecord = null;//置null,防止Hiberbate报错:同Id的多个对象
            this.userRecordService.saveOrUpdate(userRecordDB);
            this.id = userRecordDB.getId();
            return SUCCESS;
        } catch (Exception e) {
            LOGGER.error("人事档案更新过程中出现异常", e);
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     * 查看一份人事档案
     *
     * @return 返回到人事档案查看页面
     */
    public String show() {
    	
    	if(loginUserNow !=null){
    		
    	}
        putBaseData2Context();
        this.userRecord = userRecordService.findById(this.id);
        if (this.userRecord == null) {
            LOGGER.error("找不到符合的人事档案，ID:" + this.id);
            return ERROR;
        }
        UserInfo userInfo = this.userRecord.getUserInfo();
        String[] groupNamesIds = getGroupNamesAndIds(userInfo);
        this.userRecord.setGroupNames(groupNamesIds[0]);
        this.userRecord.setGroupIds(groupNamesIds[1]);

        String[] roleNamesIds = getRoleNamesAndIds(userInfo);
        this.userRecord.setRoleNames(roleNamesIds[0]);
       // userRecord.getAttachment();
        
//        ActionContext.getContext().put("from", "show");
//        if("detail".equals(from)){
        	return "detail";
//        }
//        return "show";
    }
/**
 * 手机端查看详情
 */
    public String findUserRecordView(){
    	UserInfo adminUser=null;
    	if(loginUserNow!=null){
    		adminUser=userService.findOne(loginUserNow);
    		putBaseData2Context();
        	try {
    			if(id!=null){
    				this.userRecord = userRecordService.findById(this.id);
    				if(userRecord!=null){
    					System.out.println(userRecord.getAttachment());
    					UserInfo userInfo = this.userRecord.getUserInfo();
    					String[] groupNamesIds = getGroupNamesAndIds(userInfo);
    					if(groupNamesIds!=null){
    						this.userRecord.setGroupNames(groupNamesIds[0]);
    				        this.userRecord.setGroupIds(groupNamesIds[1]);
    					}
    					 String[] roleNamesIds = getRoleNamesAndIds(userInfo);
    					if(roleNamesIds!=null){
    						 this.userRecord.setRoleNames(roleNamesIds[0]);
    					}
    				}
    			}else{
    	            ajax("找不到符合的人事档案，ID:" + this.id);
    			}
    		} catch (Exception e) {
    			 e.printStackTrace();
    		}
        	if("fujian".equals(type)){
        		ajax(userRecord);
    			return null;
    		}
//    		ajax(userRecord);
    		return "retailRecord";
    	}else{
    		adminUser=this.getLoginUser();
    	}
    	putBaseData2Context();
    	try {
			if(id!=null){
				this.userRecord = userRecordService.findById(this.id);
				if(userRecord!=null){
					UserInfo userInfo = this.userRecord.getUserInfo();
					String[] groupNamesIds = getGroupNamesAndIds(userInfo);
					if(groupNamesIds!=null){
						this.userRecord.setGroupNames(groupNamesIds[0]);
				        this.userRecord.setGroupIds(groupNamesIds[1]);
					}
					 String[] roleNamesIds = getRoleNamesAndIds(userInfo);
					if(roleNamesIds!=null){
						 this.userRecord.setRoleNames(roleNamesIds[0]);
					}
				}
			}else{
	            ajax("找不到符合的人事档案，ID:" + this.id);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
    	
    	return "retailRecord";
    }
    
    
    /**
     * 删除人事档案，可批量
     *
     * @return null, ajax return Text
     */
    public String delete() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.ids)) {
                result = "没有选中任何行";
            } else {
                String[] idArray = this.ids.split(UserRecordConst.RECORD_SEPARATOR);
                for (String id : idArray) {
                    this.userRecordService.delete(Integer.parseInt(id),false);
                }
                result = "删除" + idArray.length + "份人事档案成功";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 获取某个人的部门（组）信息字符串,索引位置--0：组名字符串，1：组Id字符串
     *
     * @param userInfo 用户对象
     * @return 组名和组id的字符串数组，多个组名和ID时英文逗号分割
     */
    private String[] getGroupNamesAndIds(UserInfo userInfo) {

        StringBuffer groupNames = new StringBuffer();
        StringBuffer groupIds = new StringBuffer();
        String separator = UserRecordConst.RECORD_SEPARATOR;
        List<GroupInfo> list = new ArrayList<GroupInfo>();
        GroupInfo g =  groupService.getGroupByUserId(userInfo.getCompanyId(), userInfo.getUserId());
        list.add(g);
        for (GroupInfo groupInfo : list) {
            groupNames.append(groupInfo.getGroupName());
            groupNames.append(separator);
            groupIds.append(groupInfo.getGroupId());
            groupIds.append(separator);
        }
//        groupNames = StringUtils.removeEnd(groupNames.toString(), separator);
//        groupIds = StringUtils.removeEnd(groupIds.toString(), separator);
        return new String[]{StringUtils.removeEnd(groupNames.toString(), separator), StringUtils.removeEnd(groupIds.toString(), separator)};
    }

    /**
     * 获取某个人的角色信息字符串,索引位置--0：组名字符串，1：组Id字符串
     *
     * @param userInfo 用户对象
     * @return 组名和组id的字符串数组，多个组名和ID时英文逗号分割
     */
    private String[] getRoleNamesAndIds(UserInfo userInfo) {

        StringBuffer roleNames = new StringBuffer();
        StringBuffer roleIds = new StringBuffer();
        String separator = UserRecordConst.RECORD_SEPARATOR;
        List<RoleInfo> list = this.roleService.findRolesByUserId(userInfo.getUserId(), 1);
        for (RoleInfo roleInfo : list) {
            roleNames.append(roleInfo.getRoleName());
            roleNames.append(separator);
            roleIds.append(roleInfo.getRoleId());
            roleIds.append(separator);
        }
        return new String[]{StringUtils.removeEnd(roleNames.toString(), separator), StringUtils.removeEnd(roleIds.toString(), separator)};
    }

    /**
     * 把选项中的基础数据放在Context中
     */
    private void putBaseData2Context() {
        String[] tags = {"user_type", "marriage_status", "politics_face", "registered_type",
                "job_title", "job_title_level", "station", "work_status", "edu_qualifications", "edu_level"};
        
        Map<String, List<Dict>> baseDataMap = new HashMap<String, List<Dict>>();
        for (String tag : tags) {
            List<Dict> list = this.dictService.findList(tag, 1);
            if (list != null) {
                Collections.sort(list, new Comparator<Dict>() {
                    @Override
                    public int compare(Dict o1, Dict o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                });
            }
            baseDataMap.put(tag, list);
        }
        ActionContext.getContext().put("baseDataMap", baseDataMap);
    }

    /**
     * 获取基础数据值
     *
     * @param infoTypeKey infoTypeKey
     * @param value       值
     * @return 名称
     */
    private String getBaseDataValue(String infoTypeKey, Integer value) {
        Dict dict = this.dictService.loadByTypeTagValue(infoTypeKey, 1, value);
        if (dict != null) {
            return dict.getName();
        } else {
            return "";
        }
    }

    public IUserRecord getUserRecordService() {
        return userRecordService;
    }

    public void setUserRecordService(IUserRecord userRecordService) {
        this.userRecordService = userRecordService;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }



    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public UserRecordSearchVo getUserRecordSearchVo() {
        return userRecordSearchVo;
    }

    public void setUserRecordSearchVo(UserRecordSearchVo userRecordSearchVo) {
        this.userRecordSearchVo = userRecordSearchVo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSearchJson() {
        return searchJson;
    }

    public void setSearchJson(String searchJson) {
        this.searchJson = searchJson;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRequestSrc()
    {
        return requestSrc;
    }

    public void setRequestSrc(String requestSrc)
    {
        this.requestSrc = requestSrc;
    }

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public Integer getTreeType() {
		return treeType;
	}

	public void setTreeType(Integer treeType) {
		this.treeType = treeType;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getLoginUserNow() {
		return loginUserNow;
	}

	public void setLoginUserNow(Integer loginUserNow) {
		this.loginUserNow = loginUserNow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
    
	
}
