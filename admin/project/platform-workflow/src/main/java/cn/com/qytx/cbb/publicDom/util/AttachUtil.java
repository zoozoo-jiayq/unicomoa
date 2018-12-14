package cn.com.qytx.cbb.publicDom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.platform.base.application.SpringContextHolder;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：附件工具类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:17:53 
 * 修改日期：下午3:17:53 
 * 修改列表：
 */
public class AttachUtil {

	/**
	 * 功能：保存附件
	 * @param
	 * @return
	 * @throws   
	 */
	public static void saveAttach(int attach,String instanceId,UserInfo userInfo){
			ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
			IAttachment attachmentService = (IAttachment) SpringUtil.getBean("attachmentService");
			String old = getAttachment(instanceId);
			Gson gson = new Gson();
			List<Attachment> oldList = null;
			if(old!=null&&!"".equals(old)){
				  oldList = gson.fromJson(old,new TypeToken<List<Attachment>>(){}.getType());
			}
			Attachment a = attachmentService.findOne(attach);
			if(oldList == null){
				oldList = new ArrayList<Attachment>();
			}
			oldList.add(a);
			engine.getExecutionService().createVariable(instanceId,PublicDocumentConstant.ATTACH,gson.toJson(oldList),false);
	}
	
	/**
	 * 功能：获取附件信息
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public static String getAttachment(String instanceId){
		IWorkFlowService workflowService = SpringContextHolder.getBean(IWorkFlowService.class);
		String str = workflowService.getVariablebyInstance(instanceId, PublicDocumentConstant.ATTACH);
		return str;
	}
	
	/**
	 * 功能：获取附近信息列表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public static List<Attachment> getAttachmentList(String instanceId){
		Gson gson = new Gson();
		String str = getAttachment(instanceId);
		List<Attachment> list = gson.fromJson(str,new TypeToken<List<Attachment>>(){}.getType());
		return list;
	}
	
	/**
	 * 功能：删除附件
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public static void deleteAttach(String instanceId,int attachId){
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		IAttachment attachmentService = (IAttachment) SpringUtil.getBean("attachmentService");
		String old = getAttachment(instanceId);
		Gson gson = new Gson();
		List<Attachment> oldList = new ArrayList<Attachment>();
		if(old!=null&&!"".equals(old)){
			  oldList = gson.fromJson(old,new TypeToken<List<Attachment>>(){}.getType());
		}
		
		int index = -1;
		for(int i=0; i<oldList.size(); i++){
			if(oldList.get(i).getId().intValue() == attachId){
				index = i;
				break;
			}
		}
		oldList.remove(index);
		engine.getExecutionService().createVariable(instanceId,PublicDocumentConstant.ATTACH,gson.toJson(oldList),false);
	}
	
	
	/**
	 * 功能：根据文件名获取文件的CLASS
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public static String  getClassByFileTypeApply(String fileName) {
	    String subfix = "";
		if (fileName.lastIndexOf(".") != -1) {
			subfix = fileName.substring(fileName.lastIndexOf(".") + 1);
	    }
	    subfix = subfix.toLowerCase();
	    String r = TYPE_MAP.get(subfix);
	    if(r == null){
	    	r = "txt";
	    }
	    return r;
	}
	
	private static Map<String,String> TYPE_MAP = new HashMap<String, String>(){{
		this.put("txt", "txt");
		this.put("doc", "doc");
		this.put("docx", "doc");
		this.put("ppt", "ppt");
		this.put("pptx", "ppt");
		this.put("xls", "excel");
		this.put("xlsx", "excel");
		this.put("gif", "img");
		this.put("jpg", "img");
		this.put("jpeg", "img");
		this.put("png", "img");
		this.put("doc", "img");
		this.put("rar", "rar");
		this.put("zip", "rar");
		this.put("7z", "rar");
		this.put("js", "txt");
		this.put("jsp", "txt");
		this.put("html", "txt");
		this.put("mp3", "txt");
		this.put("mp4", "txt");
		this.put("rmvb", "txt");
		this.put("avi", "txt");
	}};

}
