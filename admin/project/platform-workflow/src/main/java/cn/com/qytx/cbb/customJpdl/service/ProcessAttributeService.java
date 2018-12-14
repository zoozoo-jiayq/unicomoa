package cn.com.qytx.cbb.customJpdl.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：流程定义服务接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:38:36 
 * 修改日期：2013-3-22 上午11:38:36 
 * 修改列表：
 */
public interface ProcessAttributeService extends BaseService<ProcessAttribute> {

	/**
	 * 功能：保存流程
	 * @param
	 * @return
	 * @throws   
	 */
	public void save(ProcessAttribute processAttribute);
	
	/**
	 * 功能：根绝公司ID查询所有流程
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ProcessAttribute> findAll(int companyId);
	
	/**
	 * 功能：根绝ID查询指定的流程
	 * @param
	 * @return
	 * @throws   
	 */
	public ProcessAttribute getProcessById(Integer processId);

	/**
	 * 功能：根绝JSON格式的数据更新流程定义
	 * 更新内容包括:1，流程定义（json/xml 格式的JPDL）
	 * 			   2，各个流程节点的定义。
	 * @param type:2表示工作流；12表示公文流程
	 * @return
	 * @throws   
	 */
	public void updateProcessAttributeByJsonData(int processAttributeId,String jsonData,int type) throws Exception;
	
	/**
	 * 功能：查询没一个类别下的数量
	 * @param
	 * @return
	 * @throws   
	 */
	public List<Object> getCountByCategory();
	
	/**
	 * 功能：根据流程定义ID查询流程定义属性
	 * @param
	 * @return
	 * @throws   
	 */
	public ProcessAttribute findByDefineId(String defineId);
	
	/**
	 * 功能：根据类别ID查询该类别下的所有流程
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ProcessAttribute> findByCategoryId(int categoryId,int companyId);
	
	/**
	 * 功能：根据流程定义ID发布流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public void deploy(int processAttributeId) throws IOException;
	
	/**
	 * 功能：停用指定的流程
	 * @param
	 * @return
	 * @throws   
	 */
	public void stop(int processAttributeId);
	
	
	/**
	 * 功能：启用流程
	 * @param
	 * @return
	 * @throws   
	 */
	public void start(int processAttributeId);
	
	/**
	 * 功能：复制流程
	 * @param
	 * @return
	 * @throws   
	 */
	public ProcessAttribute copyProcess(int processId);
	
	/**
	 * 功能：根据流程名字重构流程
	 * @param
	 * @return
	 * @throws   
	 */
	public void rebuildProcessByProcessName(ProcessAttribute processAttribute,int type,int processAttributeId);
	
	/**
	 * 功能：验证流程定义名称是否重复
	 * @param
	 * @return true:重复
	 * @throws   
	 */
	public boolean checkProcessNameIsRepeat(String processName,Integer processAttributeId);
	
	/**
	 * 功能：根据用户权限查找用户能发起的流程
	 * @param
	 * @return
	 * @throws   
	 */
	public List<ProcessAttribute> findProcessAttributesByPermissions(UserInfo userInfo,Integer categoryId,String searchkey);
	
	/**
	 * 功能：文号计数器+1
	 * @param
	 * @return
	 * @throws   
	 */
	public void processNameBeginNumAddOne(int processAttributeId);

   /**
    * 判断是否可以申请该流程
 * @param userInfo 
    * @param userId
    * @param processId
    * @return
    */
	public boolean isCanApply( UserInfo userInfo, int processId);
	
	/**
	 * 功能：校验是否已设置经办权限
	 * @param
	 * @return
	 * @throws   
	 */
	public List<String> checkIsSetCandidate(int processAttributeId);

	/**
	 * 功能：根据流程名称查询流程定义属性
	 * @param processName
	 * @return
	 */
	public ProcessAttribute findByProcessName(String processName);
	
	/**
	 * 功能：导出流程定义属性，不包含自定义自定义表单属性和分类属性
	 * @param
	 * @return
	 * @throws   
	 */
	public File exportProcessDefine(int processAttributeId) throws Exception;

	/**
	 * 功能：导入流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public void importProcess(ProcessAttribute pa,int type) throws Exception;
}
