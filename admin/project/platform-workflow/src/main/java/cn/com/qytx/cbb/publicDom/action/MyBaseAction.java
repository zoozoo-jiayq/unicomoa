package cn.com.qytx.cbb.publicDom.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;

import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 下午5:52:55 
 * 修改日期：2013-4-16 下午5:52:55 
 * 修改列表：
 */
public class MyBaseAction extends BaseActionSupport {
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	@Resource(name="documentTypeService")
	 DocumentTypeService documentTypeService;
	
	@Resource
	 IDict infoTypeService;
	
	@Resource(name="formCategoryService")
	 IFormCategory formCategory;
	
	@Resource(name="processEngine")
    ProcessEngine processEngine;
	int documentTypeId;
	String taskId;
	String instanceId;
	String searchType;
	Map<String, Object> varMap;
	int menu;

	public Map<String, Object> getVarMap() {
		return varMap;
	}

	public void setVarMap(Map<String, Object> varMap) {
		this.varMap = varMap;
	}

	public int getMenu() {
		return menu;
	}

	public void setMenu(int menu) {
		this.menu = menu;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	/**
	 * 功能：获取密级列表
	 * @param
	 * @return
	 * @throws   
	 */
	public List<Dict> getSecretLevels(){
		return documentTypeService.getSecretLevel();
	}
	
	/**
	 * 功能：获取缓急列表
	 * @param
	 * @return
	 * @throws   
	 */
	public List<Dict> getHuanji(){
		return documentTypeService.getHuanji();
	}
	
}
