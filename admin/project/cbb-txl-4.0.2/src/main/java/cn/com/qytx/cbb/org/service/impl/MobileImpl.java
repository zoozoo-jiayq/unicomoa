package cn.com.qytx.cbb.org.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.org.service.IMobile;
import cn.com.qytx.cbb.secret.domain.SecretSettings;
import cn.com.qytx.cbb.secret.sevice.ISecretSettings;
import cn.com.qytx.platform.base.PlatformException;
import cn.com.qytx.platform.org.dao.GroupUserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.org.service.IUserGroup;
import cn.com.qytx.platform.utils.UpdateFieldUtil;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.encrypt.MD5;
import cn.com.qytx.platform.utils.pinyin.FormatPinyinTo9Data;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;

/**
 * 手机端接口实现
 * User: 普友
 * Date: 14-6-26
 * Time: 上午11:43
 */
@Service
@Transactional
public class MobileImpl  implements IMobile {
    @Resource(name = "userService")
    IUser userService;//人员接口实现类
    @Resource(name = "groupUserService")
    IGroupUser groupUserService; //部门人员信息实现类

    @Resource(name = "groupService")
    private IGroup groupService; //部门/群组管理接口
    
    @Resource(name="userGroupService")
    private IUserGroup userGroupService;
    
	@Autowired
	private ISecretSettings secretSettingsService;
    
//    /** 推送接口 **/
//    @Resource(name="pushService")
//    IPush pushService;

    @Resource
    private ICompany companyService;
    @Autowired
    GroupUserDao groupUserDao;
    
//    private static String masterSecret="c2130125347e1d34684a50b4";
//    private static String appKey="579901981add492059e9ce07";
    public String getBasicInfo(Integer companyId, Integer userId, String lastUpdateTime, Integer infoType, Integer isOrg) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(infoType==null)
        {
            throw new PlatformException("类型不能为空");
        }
        List<Map<String,Object>> groupMap=new ArrayList<Map<String, java.lang.Object>>();//部门Map
        List<Map<String,Object>> userMap=new ArrayList<Map<String, java.lang.Object>>();//人员Map
        Date time=null;
        if(StringUtils.isNotBlank(lastUpdateTime))
        {
            time=DateTimeUtil.stringToDate(lastUpdateTime,"yyyy-MM-dd HH:mm:ss");
        }
        if ((infoType & (int) Math.pow(2, 0)) > 0) {
            boolean needUserGroup=false;
            if (isOrg==1)
            {
                needUserGroup=true;
            }
            
            List<GroupInfo> groupList= groupService.getGroupListChanged(companyId,userId,time,needUserGroup);
            if(groupList!=null && groupList.size()>0)
            {
            	//得到部门人员Map
        		Map<Integer,Integer> userNumMap=groupService.getCompanyGroupCountMap(companyId);
                for(GroupInfo group:groupList)
                {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("groupId",group.getGroupId());
                    map.put("groupName",group.getGroupName());
                    map.put("parentId",group.getParentId());
                    map.put("unitType",group.getGroupType());
                    map.put("orderIndex",group.getOrderIndex());
                    map.put("createUserId",group.getUserId());
                    //Integer groupUserNum=groupService.getGroupUserAllNum(companyId, group.getGroupId());
                    Integer userNum=userNumMap.get(group.getGroupId());
                    if(userNum==null){
                    	userNum=0;
                    }
                    map.put("groupUserNum", userNum.intValue());
                    map.put("path", group.getPath());
                    map.put("grade", group.getGrade());
                    if(group.getIsDelete()==0)
                    {
                        map.put("action",1);//增加
                    }
                    else if(group.getIsDelete()==1)
                    {
                        map.put("action",3);//删除
                    }

                    //如果为群组，获取群组人员ID
                    if(group.getGroupType()==4||group.getGroupType()==5)
                    {
                      List<Integer> list= groupUserService.getUserIdsBySetId(group.getGroupId());
                      if(list!=null&&list.size()>0)
                      {
                    	  for(int i=0;i<list.size();i++){
                    		  UserInfo user = userService.findOne(list.get(i));
                    		  if(user==null||user.getIsDelete()==1){
                    			  groupUserDao.deleteGroupUserByUserIds(list.get(i)+"",group.getGroupType(),companyId);
                    			  list.remove(i);
                    			  i--;
                    		  }
                    	  }
                    	  if(list!=null&&list.size()>0)
                          {
                    		  map.put("userIds",list);
                          }
                      }
                    }
                    groupMap.add(map);
                }
            }
        }
        //1位 人员数据
        if ((infoType & (int) Math.pow(2, 1)) > 0) {
            List<UserInfo> userList=null;//人员列表
            //todo 需要添加删除人员信息
            if(StringUtils.isBlank(lastUpdateTime))
            {
           //   userList=userService.findAll("companyId=?1 and isDelete=0 ",companyId);
            	 userList=userService.findUsersByLastUpdateTime(companyId,null);
            }
            else
            {
             //  userList=userService.findAll("companyId=?1 and lastUpdateTime>=?2",companyId,time);
               userList=userService.findUsersByLastUpdateTime(companyId,lastUpdateTime);
            }
            
            List<String> ugPowerList = new ArrayList<String>();
            UserGroup ug = null;
            if(userId!=null){
            	ug = userGroupService.findByUserCompany(userId, companyId);
            }
      	    if(ug!=null){
          	  if (StringUtils.isNotBlank(ug.getGroupPower())) {
          		  ugPowerList=Arrays.asList(ug.getGroupPower().split(","));
          	  }
      	    }
            
            /**====获得保密设置======**/
        	List<SecretSettings> listSecretSettings = secretSettingsService.getSettingsByUserAndCompany(companyId,userId);
        	
            if(userList!=null && userList.size()>0)
            {
                for(UserInfo user:userList)
                {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("userId",user.getUserId());
                    map.put("phone",user.getPhone());
                    //int groupId=getGroupId(user.getUserId());
                    map.put("groupId",user.getGroupId());
                    map.put("userName",user.getUserName());
                    map.put("sex",user.getSex());
                    map.put("telephone",user.getOfficeTel());
                    map.put("telephone2",user.getHomeTel());
                    map.put("phone2",user.getPhone2());
                    map.put("job",user.getJob());
                    map.put("title",user.getTitle());
                    map.put("email",user.getEmail());
                    
                    map.put("lastLoginTime","");
                    if(user.getLastLoginTime()!=null){
                    map.put("lastLoginTime",user.getLastLoginTime());
                    }
                    
                    String userPy=user.getPy();
                    if(userPy!=null){
                    	userPy=userPy.toUpperCase();
                    }
                    map.put("userPY",userPy);
                    map.put("userNum",user.getUserNum());
                    map.put("orderIndex",user.getOrderIndex());
                    map.put("vgroup",user.getvGroup());
                    map.put("vNum",user.getVNum());
                    map.put("isVirtual", 0);//是否是虚拟人
                    map.put("linkId", 0);
                    if(user.getIsVirtual()!=null && user.getIsVirtual().intValue() == 1){
                    	map.put("isVirtual", 1);
                    	map.put("linkId", user.getLinkId());
                    }
                    map.put("userState",user.getMobileShowState()); //  //控制手机端该用户是否展示 1,隐藏；0展示，默认0
                    if(user.getIsDelete()==0)
                    {
                        map.put("action",1);//增加
                    }
                    else if(user.getIsDelete()==1)
                    {
                        map.put("action",3);//删除
                    }
                    map.put("userPower",user.getUserPower());
                    map.put("signName",user.getSignName());
                    map.put("role",user.getRole());
                    map.put("photo",user.getPhoto());
                    map.put("isLogined", user.getIsLogined());
                    map.put("fullPy",user.getFullPy()==null?"":user.getFullPy());
                    map.put("formattedNumber",user.getFormattedNumber()==null?"":user.getFormattedNumber());
                    map.put("property1", "测试字段1");
                    map.put("property2", "测试字段2");
                    //保密设置
                    Boolean hasPower =false;
                    if(user.getGroupId()!=null){
                    	 hasPower=ugPowerList.contains(user.getGroupId().toString());
                      }
                    if(listSecretSettings!=null && listSecretSettings.size()>0){
                    	if(userId != user.getUserId().intValue() ){//自己的不用保密设置
                        	for(SecretSettings settings: listSecretSettings){
                        		String applyUserIds = settings.getApplyUserIds();
                        		if((applyUserIds.indexOf(","+user.getUserId()+",") >=0 || (user.getLinkId()!=null&&applyUserIds.indexOf(","+user.getLinkId()+",")>=0)) && !hasPower){
                        			String[] arrs= settings.getAttribute().split(",");
                        			for(String att:arrs){
                        				if(att.equals("officeTel")){
                        					 map.put("telephone","");
                        				}else if(att.equals("homeTel")){
                        					 map.put("telephone2","");
                        				}else{
                        					map.put(att, "");
                        				}
                                   			   
                        			}
                        		}
                        	}
                    	}
                    }
                    
                    userMap.add(map);
                }
            }
        }
        Gson gson =new Gson();
        String groupInfo=gson.toJson(groupMap);
        String userInfo =gson.toJson(userMap);
        Map<String,Object> map=new HashMap<String, Object>();
      //  Map<String,Object> result = new HashMap<String, Object>();
      //  result.put("uDate",DateTimeUtil.getCurrentTime());
       // result.put("groupInfo",groupInfo);
       // result.put("userInfo",userInfo);
        map.put("type", 1);
        map.put("uDate",DateTimeUtil.getCurrentTime());
        map.put("groupInfo",groupInfo);
        map.put("userInfo",userInfo);
        String ret=gson.toJson(map);
        ret = "100||" + ret;

        return ret;
    }

    /**
     * 根据用户id获得groupId
     */
    private int getGroupId(int userId){
    	int groupId = 0;
    	List<GroupInfo> list = groupService.findAll(" groupType = ? and groupId in (select groupId from GroupUser where userId = ?)", GroupInfo.DEPT,userId); 
    	if(list!=null && list.size()>0){
    		for(GroupInfo groupInfo : list){
    			groupId = groupInfo.getGroupId();
    			break;
    		}
    	}
    	return groupId;
    }
    
    
    
    
    
    
    
    
    public String addGroup(Integer companyId, Integer userId, String userName, Integer parentId, String groupName, Integer groupType) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(StringUtils.isBlank(groupName))
        {
            throw new PlatformException("部门/群组名称不能为空");
        }
        if(groupType==null)
        {
            throw new PlatformException("类型不能为空");
        }
        String hql="";
        GroupInfo groupInfo=null;
        //如果是公共群组，企业通讯录，公共群组 名称不能重复
        if(groupType==1 || groupType==2 || groupType==4)
        {
            hql="companyId=?1 and groupName=?2 and groupType=?3";
            groupInfo=groupService.unDeleted().findOne(hql,companyId,groupName,groupType);
        }
        //如果是私人通讯录，个人群组 名称不能重复
        else if(groupType==3||groupType==5)
        {
            hql="companyId=?1 and groupName=?2 and userId=?3 and groupType=?4";
            groupInfo=groupService.unDeleted().findOne(hql,companyId,groupName,userId,groupType);
        }
        if(groupInfo!=null)
        {
            return "101||名称已经存在";
        }
        groupInfo=new GroupInfo();
        groupInfo.setIsDelete(0);
        groupInfo.setGroupName(groupName);
        groupInfo.setGroupType(groupType);
        groupInfo.setCompanyId(companyId);
        groupInfo.setUserId(userId);
        groupInfo.setGroupState(0);
        groupInfo.setParentId(parentId==null?0:parentId);
        
        if(groupInfo.getParentId() ==0){  //设置级别
        	groupInfo.setGrade(0);
        }else{
        	GroupInfo parent = groupService.findOne(groupInfo.getParentId());
        	groupInfo.setGrade(parent.getGrade()+1);
        }
        
        Integer orderIndex=groupService.getMaxOrderIndex(companyId,parentId==null?0:parentId,groupType);
        orderIndex+=1;
        groupInfo.setOrderIndex(orderIndex);
        groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));
        //保存部门信息
//        groupService.saveOrUpdate(groupInfo);
        groupService.addGroup(groupInfo);
        
        if(groupType==5){
        	GroupUser groupUser = new GroupUser();
        	groupUser.setCompanyId(companyId);
        	groupUser.setUserId(userId);
        	groupUser.setGroupId(groupInfo.getGroupId());
        	groupUserDao.saveOrUpdate(groupUser);
        }
        return "100||"+groupInfo.getGroupId();
    }

    
    public String updateGroup(Integer companyId, Integer userId, String userName, Integer groupId, Integer parentId, String groupName) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(groupId==null)
        {
            throw new PlatformException("要更新的部门/群组ID不能为空");
        }
        if(StringUtils.isBlank(groupName))
        {
            throw new PlatformException("部门/群组名称不能为空");
        }

        //查看自己创建的部门/群组
        GroupInfo groupInfo=groupService.findOne("companyId=?1 and groupId=?2",companyId,groupId);
        if(groupInfo==null)
        {
            return "101||部门不存在";
        }
        //如果是公共的部门/群组，则不能重复
        String hql="companyId=?1 and groupName=?2 and groupType in (1,2,4)";
        GroupInfo tmp=groupService.findOne(hql,companyId,groupName);
        if(tmp!=null)
        {
            return "101||部门/群组名称已经存在";
        }
        groupInfo.setGroupName(groupName);
        if(parentId!=null)
        {
          groupInfo.setParentId(parentId);
        }
        groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));

        //保存部门信息
        groupService.updateGroup(groupInfo);
        return "100||修改成功";
    }

    
    public String deleteGroup(Integer companyId, Integer userId, String userName, Integer groupId) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(groupId==null)
        {
            throw new PlatformException("要删除的部门/群组ID不能为空");
        }
         //todo 需要备份数据
        GroupInfo groupInfo=groupService.findOne(groupId);
        if(groupInfo==null)
        {
            return "101||要删除的部门/群组不存在";
        }
        //不从数据库删除
        groupInfo.setLastUpdateTime(new Date());
        groupService.delete(groupInfo,false);
        groupUserService.deleteBySetId(companyId, groupId);
        return "100||"+groupInfo.getParentId();
    }

    
    public String groupOrder(Integer companyId, Integer userId, String userName, String sortList) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(StringUtils.isBlank(sortList))
        {
            throw new PlatformException("排序人员不能为空");
        }

        JSONArray list= JSON.parseArray(sortList);
        if(list!=null)
        {
            for(Object obj:list)
            {
                Map map=(Map)obj;
                Integer groupId=Integer.parseInt(map.get("groupId").toString());
                Integer startIndex=Integer.parseInt(map.get("start").toString());
                Integer endIndex=Integer.parseInt(map.get("end").toString());
                System.out.println(startIndex+"%%"+endIndex);
                groupService.sortOrder(companyId,groupId,startIndex,endIndex);
            }
        }

        return "100||部门排序成功" ;
    }

    
    public String addUsersInfo(Integer companyId, Integer userId, String userName, String userJson) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(StringUtils.isBlank(userJson))
        {
            throw new PlatformException("人员信息不能为空");
        }
        userJson = userJson.replace("“", "\"");
        Map map= JSON.parseObject(userJson);
        String name=map.get("userName").toString();
        Integer groupId=Integer.parseInt(map.get("groupId").toString());
        String phone=map.get("phone").toString();


        if(StringUtils.isBlank(name))
        {
            throw new PlatformException("要添加人员姓名不能为空");
        }
        if(StringUtils.isBlank(phone))
        {
            throw new PlatformException("要添加人员号码不能为空");
        }
        if(groupId==null)
        {
            throw new PlatformException("要添加人员部门不能为空");
        }
        String officeTel="";
        if(map.get("officeTel")!=null)
        {
        	officeTel=map.get("officeTel").toString();
        }
        
        String telephone="";
        if(map.get("telephone")!=null)
        {
        	telephone=map.get("telephone").toString();
        }
        if(StringUtils.isBlank(telephone)){
        	telephone=officeTel;
        }
        
        
        
        
        String telephone2="";
        if(map.get("telephone2")!=null)
        {
        	telephone2=map.get("telephone2").toString();
        }
        String phone2="";
        if(map.get("phone2")!=null)
        {
            phone2=map.get("phone2").toString();
        }
        String job="";
        if(map.get("job")!=null)
        {
            job=map.get("job").toString();
        }
        String email="";
        if(map.get("email")!=null)
        {
            email=map.get("email").toString();
        }
        String userPY="";
//        if(map.get("userPY")!=null)
//        {
//            userPY=map.get("userPY").toString();
//        }
        if(name!=null){
        	userPY=Pinyin4jUtil.getPinYinHeadChar(name);
        }
        String userNum="";
        if(map.get("userNum")!=null)
        {
            userNum=map.get("userNum").toString();
        }
        String vgroup="";
        if(map.get("vgroup")!=null)
        {
            vgroup=map.get("vgroup").toString();
        }
        String signName="";
        if(map.get("signName")!=null)
        {
            signName=map.get("signName").toString();
        }
        Integer sex=1;
        if(map.get("sex")!=null)
        {
            sex=Integer.parseInt(map.get("sex").toString());
        }

        String userPower="";
        if(map.get("userPower")!=null)
        {
            userPower=map.get("userPower").toString();
        }
        String action="";
        if(map.get("action")!=null)
        {
            action=map.get("action").toString();
        }
        String userType="";
        if(map.get("userType")!=null)
        {
            userType=map.get("userType").toString();
        }
        String vNum="";
        if(map.get("vNum")!=null)
        {
            vNum=map.get("vNum").toString();
        }
        String title="";
        if(map.get("title")!=null)
        {
            title=map.get("title").toString();
        }
        Integer mobileShowState=null; 
        if (map.get("mobileShowState")!=null) {
        	mobileShowState=Integer.parseInt(map.get("mobileShowState").toString());
		}
        UserInfo user=  userService.findOne("loginName=?1 and companyId=?2 and isDelete=0", phone, companyId);
        if(user!=null)
        {
           return "101||号码已经存在";
        }
        else
        {
            user=new UserInfo();
            user.setPhone(phone);
            user.setAlterName(name);
            user.setUserName(name);
            user.setCompanyId(companyId);
            user.setPhone2(phone2);
             //获取最大排序号
            Integer orderIndex=userService.getMaxOrderIndex(companyId,groupId);
            orderIndex=orderIndex+1;
            user.setOrderIndex(orderIndex);
            user.setJob(job);
            user.setPy(userPY);
           // user.setRegisterTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));
            user.setCreateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));
            user.setTitle(title);
            user.setSex(sex);
            user.setUserState(0);
            user.setIsDelete(0);
            user.setTurnType(0);
            user.setIsDefault(1);
            user.setUserPower(1);
            user.setUserNumType(0);
            user.setMcnType(0);
            user.setVNum(vNum);
            user.setVGroup(vgroup);
            user.setOfficeTel(telephone);
            user.setHomeTel(telephone2);
            user.setRole(1);
            user.setGroupId(groupId);
            user.setEmail(email);
            user.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
            user.setMobileShowState(mobileShowState);
            user.setLoginName(phone);
            user.setLoginPass(new MD5().encrypt("123456"));
            user.setPartitionCompanyId(companyId%10);
            userService.saveOrUpdate(user);

            //修改部门变动时间
            GroupInfo gi = groupService.findOne(groupId);
            gi.setLastUpdateTime(new Date());
            groupService.saveOrUpdate(gi);
            return "100||"+user.getUserId();
        }
    }

    
    public String updateUsersInfo(Integer companyId, Integer userId, String userName, String userJson) throws Exception {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(StringUtils.isBlank(userJson))
        {
            throw new PlatformException("人员信息不能为空");
        }
        userJson = userJson.replace("“", "\"");
        Map map= JSON.parseObject(userJson);
        String name=null;
        if(map.get("userName")!=null)
        {
            name=map.get("userName").toString();
        }
        Integer updateUserId=Integer.parseInt(map.get("userId").toString());
        String phone=null;
        if(map.get("phone")!=null)
        {
            phone= map.get("phone").toString();
        }
        Integer orderIndex=null;
        if(map.get("orderIndex")!=null)
        {
            orderIndex=Integer.parseInt(map.get("orderIndex").toString());
        }

        if(updateUserId==null)
        {
            throw new PlatformException("要修改的人员ID不能为空");
        }
        String officeTel="";
        if(map.get("officeTel")!=null)
        {
        	officeTel=map.get("officeTel").toString();
        }
        
        String telephone="";
        if(map.get("telephone")!=null)
        {
        	telephone=map.get("telephone").toString();
        }
        if(StringUtils.isBlank(telephone)){
        	telephone=officeTel;
        }
        
        String telephone2=null;
        if(map.get("telephone2")!=null)
        {
        	telephone2=map.get("telephone2").toString();
        }
        String phone2=null;
        if(map.get("phone2")!=null)
        {
            phone2=map.get("phone2").toString();
        }
        String job=null;
        if(map.get("job")!=null)
        {
            job=map.get("job").toString();
        }
        String email=null;
        if(map.get("email")!=null)
        {
            email=map.get("email").toString();
        }
        String userPY=null;
        if(map.get("userPY")!=null)
        {
            userPY=map.get("userPY").toString();
        }
        String userNum=null;
        if(map.get("userNum")!=null)
        {
            userNum=map.get("userNum").toString();
        }
        String vgroup=null;
        if(map.get("vgroup")!=null)
        {
            vgroup=map.get("vgroup").toString();
        }
        String signName=null;
        if(map.get("signName")!=null)
        {
            signName=map.get("signName").toString();
        }
        Integer sex=null;
        if(map.get("sex")!=null)
        {
            sex=Integer.parseInt(map.get("sex").toString());
        }

        String userPower=null;
        if(map.get("userPower")!=null)
        {
            userPower=map.get("userPower").toString();
        }
        String action=null;
        if(map.get("action")!=null)
        {
            action=map.get("action").toString();
        }
        String userType=null;
        if(map.get("userType")!=null)
        {
            userType=map.get("userType").toString();
        }
        String vNum=null;
        if(map.get("vNum")!=null)
        {
            vNum=map.get("vNum").toString();
        }
        String title=null;
        if(map.get("title")!=null)
        {
            title=map.get("title").toString();
        }
        Integer groupId=null;
        if(map.get("groupId")!=null)
        {
        	groupId=Integer.parseInt(map.get("groupId").toString());
        }
        if(groupId!=null){
        	UserInfo virtual = userService.unDeleted().findOne(" companyId=? and isVirtual=1 and linkId=? and groupId=? ",companyId,updateUserId,groupId);
        	if(virtual!=null){
        		return "101||该用户在该部门已存在";
        	}
        }
        
        Integer mobileShowState=null; 
        if (map.get("mobileShowState")!=null) {
        	mobileShowState=Integer.parseInt(map.get("mobileShowState").toString());
		}
        String userState="";
        if(map.get("userState")!=null)
        {
        	userState=map.get("userState").toString();
        }
        
        UserInfo user=  userService.unDeleted().findOne("loginName=?1 and companyId=?2 and userId!=?3", phone, companyId,updateUserId);
        if(user!=null)
        {
            return "101||号码已经存在";
        }
        
        user=userService.findOne(updateUserId);
        if(user==null)
        {
            return "101||人员信息不存在";
        }
        int oldGroupId = user.getGroupId();
        
        List<UserInfo> us = userService.findUsersByPhone(companyId,phone);
        if(us!=null && us.size()>0){
            for(int i=0; i<us.size(); i++){
                UserInfo u = us.get(i);
                if(user.getIsVirtual()!=null && user.getIsVirtual().intValue() == 1){
                	//当是虚拟关联人的时候，不判断关联的主用户和与该用户关联同一用户的虚拟关联人
                	if(user.getLinkId().equals(u.getUserId()) || (u.getIsVirtual() != null && u.getIsVirtual().intValue() ==1 && user.getLinkId().equals(u.getLinkId()))){
                		continue;
                	}
                }
                
                //如果是当前用户的虚拟关联人，则不判断重复号码
                if(u.getIsVirtual() != null && u.getIsVirtual().intValue() == 1 && user.getUserId().equals(u.getLinkId())){
                	continue;
                }
                if((u.getUserId().intValue() != user.getUserId()) && (u.getPhone().equals(user.getPhone()))){
                	return "101||号码已经存在";
                }
            }
        }
        
        if(StringUtils.isNotBlank(phone))
        {
          user.setPhone(phone);
        }
        if(StringUtils.isNotBlank(name))
        {
            user.setAlterName(name);
            user.setUserName(name);
        }
        if(StringUtils.isNotBlank(phone2))
        {
          user.setPhone2(phone2);
        }
        if(orderIndex!=null)
        {
         user.setOrderIndex(orderIndex);
        }
        if(StringUtils.isNotBlank(job))
        {
            user.setJob(job);
        }
        if(StringUtils.isNotBlank(userPY))
        {
            user.setPy(userPY);
        }
        if(StringUtils.isNotBlank(title))
        {
             user.setTitle(title);
        }
        if(sex!=null)
        {
            user.setSex(sex);
        }
        if(StringUtils.isNotBlank(vNum))
        {
            user.setVNum(vNum);
        }
        if(StringUtils.isNotBlank(vgroup))
        {
            user.setVGroup(vgroup);
        }
        if(StringUtils.isNotBlank(telephone))
        {
            user.setOfficeTel(telephone);
        }
        if(StringUtils.isNotBlank(telephone2))
        {
            user.setHomeTel(telephone2);
        }
        user.setLastLoginTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
        if(StringUtils.isNotBlank(phone))
        {
            user.setLoginName(phone);
        }
        if(StringUtils.isNotBlank(email)){
        	user.setEmail(email);
        }
        if(StringUtils.isNotBlank(userNum)){
        	user.setUserNum(userNum);
        }
        if (groupId!=null) {
			user.setGroupId(groupId);
		}
        if (mobileShowState!=null) {
        	user.setMobileShowState(mobileShowState);
		}
        if(StringUtils.isNotBlank(userState)){
        	user.setUserState(Integer.parseInt(userState));
        }
        
        String py = Pinyin4jUtil.getPinYinHeadChar(user.getUserName());
        user.setPy(py);
        //获得全拼拼音 带空格
        String fullPy = Pinyin4jUtil.getPinYinWithBlank(user.getUserName());
        user.setFullPy(fullPy);
        //获得用户姓名全拼对应九宫格按键
        String formattedNumber = FormatPinyinTo9Data.getFormattedNumber(fullPy);
        user.setFormattedNumber(formattedNumber);
        userService.saveOrUpdate(user);
        
//        GroupInfo gi = groupService.findOne(groupId);
//        gi.setLastUpdateTime(new Date());
//        groupService.saveOrUpdate(gi);
        this.updateGroupIFUserChangeGroup(user, oldGroupId);
        
        List<UserInfo> virtualUsers = userService.findVirtualUsers(user.getUserId(), companyId);
		if(virtualUsers!=null && virtualUsers.size()>0){
		  for(UserInfo virtualUser:virtualUsers){
		  	UpdateFieldUtil.update(user, virtualUser,"userId", "loginName","job","groupId","loginPass","userState","officeTel","orderIndex","USERSTATE_UNLOGIN","USERSTATE_LOGIN","USERSTATE_FORBIDDEN_LOGIN","registerTime","lastLoginTime","createTime","lastUpdateTime");
		  	virtualUser.setLastUpdateTime(new Date());
			userService.saveOrUpdate(virtualUser);
		  }
		}
        
        
        return "100||"+user.getUserId();
    }

    public String deleteUsersInfo(Integer companyId, Integer userId, String userName, String userIds) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(StringUtils.isBlank(userIds))
        {
            throw new PlatformException("要删除的人员ID不能为空");
        }
        //修改人员所在部门最后一次修改时间
        groupService.updateGroupsByUserIds(companyId, userIds);
        userService.deleteUserByIds(companyId, userIds);
        //删除人员与部门/群组对应关系
        groupUserService.deleteGroupUserByUserIds(userIds,null,companyId);
        
        return "100||删除成功";
    }

    
    public String addGroupUsers(Integer companyId, Integer userId, String userName, Integer groupId, String userIds) throws PlatformException {
    	if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(groupId==null)
        {
            throw new PlatformException("部门/群组ID不能为空");
        }
        if(StringUtils.isBlank(userIds))
        {
            throw new PlatformException("要添加的人员ID不能为空");
        }
        //首先删除对应关系
        groupUserService.deleteGroupUserByUserIds(companyId,groupId,userIds);
        //把操作人添加到部门/群组里面
        GroupUser masterUser=groupUserService.findOne("companyId=?1 and groupId=?2 and userId=?3",companyId,groupId,userId);
        if(masterUser==null)
        {
            GroupUser groupUser=new GroupUser();
            groupUser.setCompanyId(companyId);
            groupUser.setGroupId(groupId);
            groupUser.setUserId(userId);
            groupUserService.saveOrUpdate(groupUser);
        }
        String[] arr=userIds.split(",");
        for(int i=0;i<arr.length;i++)
        {
        	Integer uId=Integer.parseInt(arr[i]);
        	//操作人员不重复添加
        	if(userId==uId.intValue()){
        		continue;
        	}
            GroupUser groupUser=new GroupUser();
            groupUser.setCompanyId(companyId);
            groupUser.setGroupId(groupId);
            groupUser.setUserId(uId);
            groupUserService.saveOrUpdate(groupUser);
        }
        //更新部门变更时间
        GroupInfo groupInfo=groupService.findOne(groupId);
        if(groupInfo!=null)
        {
            groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
            groupService.saveOrUpdate(groupInfo);
        }
        String temuserIds="";
        List<GroupUser> list=groupUserService.findAll("groupId=?1 and companyId=?2",groupId,companyId);
        if(list!=null)
        {
             for(GroupUser user:list)
             {
            	 if(temuserIds.indexOf(","+user.getUserId()+",") <0 && temuserIds.indexOf(user.getUserId()+",")!=0)
            		 temuserIds+=user.getUserId()+",";
             }
        }
        if(StringUtils.isNotBlank(temuserIds))
        {
            if(temuserIds.endsWith(","))
            {
            	temuserIds=temuserIds.substring(0,temuserIds.length()-1);
            }
        }
        return "100||"+temuserIds;
    }

    
    public String deleteGroupUsers(Integer companyId, Integer userId, String userName, Integer groupId, String userIds) throws PlatformException {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(groupId==null)
        {
            throw new PlatformException("部门/群组ID不能为空");
        }
        if(StringUtils.isBlank(userIds))
        {
            throw new PlatformException("要添加的人员ID不能为空");
        }
        //更新部门变更时间
        GroupInfo groupInfo=groupService.findOne(groupId);
        if(groupInfo!=null && groupInfo.getGroupType().intValue()==5){//如果是个人群组，则需判断不能删除创建人
        	String createUserId = groupInfo.getUserId().toString();
        	
        	if(!userIds.endsWith(",")){
        		userIds+=",";
        	}
        	if(!userIds.startsWith(",")){
        		userIds = ","+userIds;
        	}
        	
        	userIds = userIds.replace(","+createUserId+",", ",");
        	
        	if(userIds.equals(",")){
        		userIds = "";
        	}
        }
        
        //首先删除对应关系
        groupUserService.deleteGroupUserByUserIds(companyId,groupId,userIds);

        if(groupInfo!=null)
        {
            groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
            groupService.saveOrUpdate(groupInfo);
        }

        String temuserIds="";
        List<GroupUser> list=groupUserService.findAll("groupId=?1 and companyId=?2",groupId,companyId);
        if(list!=null)
        {
            for(GroupUser user:list)
            {
            	temuserIds+=user.getUserId()+",";
            }
        }
        if(StringUtils.isNotBlank(temuserIds))
        {
            if(temuserIds.endsWith(","))
            {
            	temuserIds=temuserIds.substring(0,temuserIds.length()-1);
            }
        }
        return "100||"+temuserIds;
    }

    public String userOrder(Integer companyId, Integer userId, String userName, String sortList) {
        if(companyId==null)
        {
            throw new PlatformException("单位ID不能为空");
        }
        if(userId==null)
        {
            throw new PlatformException("操作人员ID不能为空");
        }
        if(StringUtils.isBlank(userName))
        {
            throw new PlatformException("操作人员姓名不能为空");
        }
        if(StringUtils.isBlank(sortList))
        {
            throw new PlatformException("排序人员不能为空");
        }

        JSONArray list= JSON.parseArray(sortList);
        if(list!=null)
        {
            for(Object obj:list)
            {
                Map map=(Map)obj;
                Integer groupId=Integer.parseInt(map.get("groupId").toString());
                Integer uId=Integer.parseInt(map.get("userId").toString());
                Integer startIndex=Integer.parseInt(map.get("start").toString());
                Integer endIndex=Integer.parseInt(map.get("end").toString());
                userService.sortOrder(companyId,groupId,uId,startIndex,endIndex);
            }
        }

        return "100||人员排序成功" ;
    }

    /**
     * 功能：如果用户的部门变动，则同时将两个部门的更新时间换成当前时间
     */
    private void updateGroupIFUserChangeGroup(UserInfo user,int oldGroupId) throws Exception{
    	if(user.getGroupId()!=oldGroupId && !user.getGroupId().equals(oldGroupId)){
    		groupService.updateGroupTime(user.getGroupId());
    		groupService.updateGroupTime(oldGroupId);
    	}
    }
}
