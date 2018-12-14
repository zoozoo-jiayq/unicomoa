package cn.com.qytx.cbb.org.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.org.service.MobileUserService;
import cn.com.qytx.cbb.util.UpdateFieldUtil;
import cn.com.qytx.platform.base.BusinessException;
import cn.com.qytx.platform.base.ParameterException;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForAddUser;
import cn.com.qytx.platform.org.service.EventForSortUser;
import cn.com.qytx.platform.org.service.EventForUpdateUser;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


/**
 * <br/>功能: 手机端通讯录人员接口 
 * <br/>版本: 1.0
 * <br/>开发人员: zyf
 * <br/>创建日期: 2015年5月18日
 * <br/>修改日期: 2015年5月18日
 * <br/>修改列表:
 */
@Service("mobileUserService")
@Transactional
public class MobileUserImpl implements MobileUserService{
	
	/**
	 * 人员接口实现类
	 */
	@Resource(name = "userService")
    IUser userService;
	
	/**
	 * 部门/群组管理接口
	 */
	@Resource(name = "groupService")
    private IGroup groupService; 
	
	/**
	 * 群组人员信息实现类
	 */
	@Resource(name = "groupUserService")
    IGroupUser groupUserService; 
	
	/**
	 * 功能：新增用户
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param userJson 人员Json UserInfo实体类的 json格式
	 * @return
	 */
	public Integer addUsersInfo(Integer companyId, Integer userId, String userName, String userJson) throws Exception {
        userJson = userJson.replace("“", "\"");
        Map map= JSON.parseObject(userJson);


        if(map.get("userName") == null || StringUtils.isBlank(map.get("userName").toString()))
        {
            throw new ParameterException(null,null);
        }
        if(map.get("phone") == null || StringUtils.isBlank(map.get("phone").toString()))
        {
        	throw new ParameterException(null,null);
        }
        if(map.get("groupId")==null)
        {
        	throw new ParameterException(null,null);
        }
        String name=map.get("userName").toString();
        Integer groupId=Integer.parseInt(map.get("groupId").toString());
        String phone=map.get("phone").toString();
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
        	throw new BusinessException("号码已经存在");
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
            user.setLoginPass("123456");
            user.setPartitionCompanyId(companyId%10);
            userService.saveOrUpdate(user);

            //修改部门变动时间
            GroupInfo gi = groupService.findOne(groupId);
            gi.setLastUpdateTime(new Date());
            groupService.saveOrUpdate(gi);
            //发布人员新增广播
            PublishEventUtil.publishEvent(new EventForAddUser(user));
            return user.getUserId();
        }
    }
	
	/**
	 * 功能：更新用户
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param userJson 人员Json UserInfo实体类的 json格式
	 * @return
	 */
	public Integer updateUsersInfo(Integer companyId, Integer userId, String userName, String userJson) throws Exception {
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
            throw new BusinessException("要修改的人员ID不能为空");
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
        		throw new BusinessException("该用户在该部门已存在");
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
        	throw new BusinessException("号码已经存在");
        }
        
        user=userService.findOne(updateUserId);
        if(user==null)
        {
        	throw new BusinessException("人员信息不存在");
        }
        String oldPhone = user.getPhone();
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
                	throw new BusinessException("号码已经存在");
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
        
		//发布修改人员广播
		PublishEventUtil.publishEvent(new EventForUpdateUser(user,oldPhone));
				
        return user.getUserId();
    }
	
	/**
	 * 功能：删除用户
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param userIds 人员ID列表，多个人员ID之间,隔开
	 */
	public void deleteUsersInfo(Integer companyId, Integer userId, String userName, String userIds) throws Exception {
        //修改人员所在部门最后一次修改时间
        groupService.updateGroupsByUserIds(companyId, userIds);
        userService.deleteUserByIds(companyId, userIds);
        //删除人员与部门/群组对应关系
        groupUserService.deleteGroupUserByUserIds(userIds,null,companyId);
        
    }
	
	/**
	 * 功能：人员排序
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名  
	 * @param sortList 排序列表 UserOrder实体类的 json格式
	 */
	public void userOrder(Integer companyId, Integer userId, String userName, String sortList) throws Exception {
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
        
        //发布人员排序广播
        PublishEventUtil.publishEvent(new EventForSortUser(companyId));
       
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
