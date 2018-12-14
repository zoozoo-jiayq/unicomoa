package cn.com.qytx.cbb.publicDom.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;

import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.vo.ApproveHistoryRecord;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：公文审批意见工具类，保存每一步的审批记录
 * 作者： jiayongqiang
 * 创建时间：2014年7月7日
 */
public class PublicDomAdviceUtil {

	/**
	 * 功能：发起任务的时候创建流程变量
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public static String createPublicDomAdvice(UserInfo userInfo,String operation){
		List<ApproveHistoryRecord> list = new ArrayList<ApproveHistoryRecord>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ApproveHistoryRecord record = new ApproveHistoryRecord();
		Date ts = new Date(Calendar.getInstance().getTimeInMillis());
		record.setApproveTime(sdf.format(ts));
		record.setOption(operation);
		record.setUserName(userInfo.getUserName());
		IRole roleService = (IRole) SpringUtil.getBean("roleService");
		List<RoleInfo> rolelist = roleService.getRoleByUser(userInfo.getUserId());
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<rolelist.size(); i++){
			sb.append(rolelist.get(i).getRoleName());
			if(i<rolelist.size()-1){
				sb.append(",");
			}
		}
		record.setRole(sb.toString());
		IGroup groupService = (IGroup) SpringUtil.getBean("groupService");
		GroupInfo g = groupService.findOne(userInfo.getGroupId());
		if(g!=null){
			record.setGroup(g.getGroupName());
		}
		record.setPhone(userInfo.getPhone());
		list.add(record);
		Gson gson = new Gson();
		String strs = gson.toJson(list);
		return strs;
	}
	
	/**
	 * 功能：审批过程中添加一条审批记录
	 * 作者：jiayongqiang
	 * 参数：operation:操作步骤
	 * 输出：
	 */
	public static void addPublicDomAdvice(String instanceId,UserInfo userInfo,String operation){
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		Gson gson = new Gson();
		String newAdvice = createPublicDomAdvice(userInfo, operation);
		List<ApproveHistoryRecord> newRecords = gson.fromJson(newAdvice, new TypeToken<List<ApproveHistoryRecord>>(){}.getType());
		
		List<ApproveHistoryRecord> list = null;
		String str = (String) engine.getExecutionService().getVariable(instanceId, PublicDocumentConstant.APPROVE_HIST_RECORD);
		if(str==null){
			list = new ArrayList<ApproveHistoryRecord>();
		}else{
			list = gson.fromJson(str, new TypeToken<List<ApproveHistoryRecord>>(){}.getType());
		}
		
		list.addAll(newRecords);
		String strs = gson.toJson(list);
		Map<String,String> map = new HashMap<String, String>();
		map.put(PublicDocumentConstant.APPROVE_HIST_RECORD, strs);
		engine.getExecutionService().createVariables(instanceId, map, false);
	}
}
