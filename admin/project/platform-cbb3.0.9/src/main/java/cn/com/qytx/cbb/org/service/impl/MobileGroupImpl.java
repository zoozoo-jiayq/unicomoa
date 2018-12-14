package cn.com.qytx.cbb.org.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.org.service.MobileGroupService;
import cn.com.qytx.platform.base.BusinessException;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.service.EventForAddGroup;
import cn.com.qytx.platform.org.service.EventForDeleteGroup;
import cn.com.qytx.platform.org.service.EventForSortGroup;
import cn.com.qytx.platform.org.service.EventForUpdateGroup;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * <br/>功能: 手机端通讯录部门接口 
 * <br/>版本: 1.0
 * <br/>开发人员: zyf
 * <br/>创建日期: 2015年5月18日
 * <br/>修改日期: 2015年5月18日
 * <br/>修改列表:
 */
@Service("mobileGroupService")
@Transactional
public class MobileGroupImpl implements MobileGroupService {

	/**
	 * 群组人员信息实现类
	 */
	@Resource(name = "groupUserService")
    IGroupUser groupUserService; 
	
	/**
	 * 部门/群组管理接口
	 */
	@Resource(name = "groupService")
    private IGroup groupService; 
	
	/**
	 * 功能：新增部门/群组
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param parentId 上级ID
	 * @param groupName 部门/群组名称
	 * @param groupType 部门类型 1 企业通讯录 2 公共通讯录 3 私人通讯录 4 公共群组 5 个人群组
	 * @return
	 */
	public Integer addGroup(Integer companyId, Integer userId, String userName, Integer parentId, String groupName, Integer groupType) throws Exception {
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
            throw new BusinessException("名称已经存在");
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
        
        //广播添加部门事件
  		PublishEventUtil.publishEvent(new EventForAddGroup(groupInfo));
        return groupInfo.getGroupId();
    }
	
	/**
	 * 功能：更新部门/群组
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param groupId 要更新的部门/群组ID
	 * @param parentId 上级ID
	 * @param groupName部门/群组名称
	 */
	public void updateGroup(Integer companyId, Integer userId, String userName, Integer groupId, Integer parentId, String groupName) throws Exception {

        //查看自己创建的部门/群组
        GroupInfo groupInfo=groupService.findOne("companyId=?1 and groupId=?2",companyId,groupId);
        if(groupInfo==null)
        {
            throw new BusinessException("部门不存在");
        }
        //如果是公共的部门/群组，则不能重复
        String hql="companyId=?1 and groupName=?2 and groupType in (1,2,4) and groupId !=?3";
        GroupInfo tmp=groupService.findOne(hql,companyId,groupName);
        if(tmp!=null)
        {
            throw new BusinessException("部门/群组名称已经存在");
        }
        groupInfo.setGroupName(groupName);
        if(parentId!=null)
        {
          groupInfo.setParentId(parentId);
        }
        groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(), "yyyy-MM-dd HH:mm:ss"));

        //保存部门信息
        groupService.updateGroup(groupInfo);
        
        //广播修改部门事件
		PublishEventUtil.publishEvent(new EventForUpdateGroup(groupInfo));
    }
	
	/**
	 * 功能：删除部门/群组
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param groupId 要删除的部门/群组ID
	 * @return
	 */
	public Integer deleteGroup(Integer companyId, Integer userId, String userName, Integer groupId) throws Exception {
         //todo 需要备份数据
        GroupInfo groupInfo=groupService.findOne(groupId);
        if(groupInfo==null)
        {
            throw new BusinessException("要删除的部门/群组不存在");
        }
        //不从数据库删除
        groupInfo.setLastUpdateTime(new Date());
        groupService.delete(groupInfo,false);
        groupUserService.deleteBySetId(companyId, groupId);
        
        //广播删除部门事件
        List<GroupInfo> listGroupInfo = new ArrayList<GroupInfo>();
        listGroupInfo.add(groupInfo);
      	PublishEventUtil.publishEvent(new EventForDeleteGroup(listGroupInfo));
        return groupInfo.getParentId();
    }
	
	/**
	 * 功能：部门排序
	 * @param companyId 单位ID
	 * @param userId 操作人员ID
	 * @param userName 操作人员名称
	 * @param sortList 排序列表 GroupOrder实体类的 json格式
	 */
	public void groupOrder(Integer companyId, Integer userId, String userName, String sortList) throws Exception {
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
        //发布部门排序广播
        PublishEventUtil.publishEvent(new EventForSortGroup(companyId));
    }
}
