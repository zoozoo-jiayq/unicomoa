package cn.com.qytx.cbb.customJpdl.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customJpdl.dao.NodeFormAttributeDao;
import cn.com.qytx.cbb.customJpdl.dao.ProcessAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.JsonParserService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customJpdl.service.parseJSON.NodeObject;
import cn.com.qytx.cbb.customJpdl.service.parseJSON.PathObject;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.GroupUserDao;
import cn.com.qytx.platform.org.dao.RoleUserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.RoleUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 功能：流程定义服务接口实现
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:38:36 
 * 修改日期：2013-3-22 上午11:38:36 
 * 修改列表：
 */
@Service("processAttributeService")
@Transactional
public class ProcessAttributeServiceImpl extends
		BaseServiceImpl<ProcessAttribute> implements ProcessAttributeService {

	@Resource
	private ProcessAttributeDao processAttributeDao;
	@Resource
	private NodeFormAttributeDao nodeFormAttributeDao;
	@Resource
	private JsonParserService jsonParserService;
	@Resource
	private transient ProcessEngine processEngine;
	@Resource
	private RoleUserDao<RoleUser> roleUserDao;
	@Resource
	private GroupUserDao<GroupUser> groupUserDao;
	@Resource
	private GroupDao<GroupInfo> groupDao;
	@Resource(name = "formPropertiesService")
	private IFormProperties formPropertiesService;

	/**
	 * 功能：保存流程定义
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public void save(ProcessAttribute processAttribute) {
		generatePrintCode(processAttribute);
		if (processAttribute.getId() == null) {
			processAttributeDao.saveOrUpdate(processAttribute);
		} else {
			List<NodeFormAttribute> nodelist = nodeFormAttributeDao
					.findByProcessAttributeId(processAttribute.getId());
			Set<NodeFormAttribute> setsAttributes = new HashSet<NodeFormAttribute>(
					nodelist);
			processAttribute.setNodeSet(setsAttributes);
			processAttributeDao.saveOrUpdate(processAttribute);
		}
	}

	/**
	 * 功能：根据打印模板生成实际的打印代码,add by 贾永强
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	private void generatePrintCode(ProcessAttribute docType) {
		if (docType.getTaoDa() != null && docType.getTaoDa() == 1) {
			if (docType.getPrintTemplateCode() != null) {
				String result = docType.getPrintTemplateCode();
				List<FormProperties> list = formPropertiesService
						.findByFormId(docType.getFormId());
				for (int i = 0; i < list.size(); i++) {
					FormProperties fp = list.get(i);
					String name = fp.getPropertyName();
					String cnName = fp.getPropertyNameCh();
					result = result.replaceAll("\"" + cnName + "\"",
							"document.getElementsByName(\"" + name
									+ "\")[0].value");
				}
				docType.setPrintCode(result);
				String temp = docType.getPrintTemplateCode();
				temp = temp.replaceAll("<", "&lt;");
				temp = temp.replaceAll(">", "&gt;");
				temp = temp.replaceAll("\r\n", "");
				docType.setPrintTemplateCode(temp);
			}
		} else if (docType.getTaoDa() != null && docType.getTaoDa() == 2) {
			docType.setPrintTemplateCode("");
			docType.setPrintCode("");
		}
	}

	/**
	 * 功能：查询全部的流程定义
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public List<ProcessAttribute> findAll(int companyId) {
		return processAttributeDao
				.getAllProcessAttributesByCompanyId(companyId);
	}

	/**
	 * 功能：根据流程定义ID查询流程定义
	 * 
	 * @param，processId,流程定义ID
	 * @return
	 * @throws
	 */
	@Override
	public ProcessAttribute getProcessById(Integer processId) {
		return processAttributeDao.findOne(processId);
	}

	/**
	 * 功能：更新流程定义
	 * 
	 * @param：processAttributeId,流程定义ID；jsonData：流程定义数据
	 * @return
	 * @throws
	 */
	@Override
	public void updateProcessAttributeByJsonData(int processAttributeId,
			String jsonData, int type) throws Exception {
		nodeFormAttributeDao.deleteBatch(processAttributeId);
		List<NodeObject> pResult = jsonParserService.parser(jsonData);
		String xmlJpdl = jsonParserService.generateJpdl(pResult, type);
		processAttributeDao.saveOrUpdateBysql(processAttributeId, jsonData,
				xmlJpdl);
		List<NodeFormAttribute> list = generateNodeFormAttributes(pResult,
				processAttributeId);
		nodeFormAttributeDao.saveBatch(list);
	}

	private List<NodeFormAttribute> generateNodeFormAttributes(
			List<NodeObject> pResult, int processAttributeId) {
		ProcessAttribute pa = new ProcessAttribute();
		pa.setId(processAttributeId);
		List<NodeFormAttribute> sets = new ArrayList<NodeFormAttribute>();
		int order = 0;
		// 保存各个节点
		for (Iterator<NodeObject> iterator = pResult.iterator(); iterator
				.hasNext();) {
			NodeObject nodeObject = iterator.next();
			if (nodeObject.getType().equals(JpdlInterface.NODE_TYPE_END)) {
				continue;
			}
			NodeFormAttribute nfa = new NodeFormAttribute();
			nfa.setProcessAttribute(pa);
			nfa.setNodeName(nodeObject.getText().getText());
			nfa.setNodeType(nodeObject.getType());
			nfa.setCompanyId(TransportUser.get().getCompanyId());
			if (nodeObject.getProps().getDesc() != null) {
				nfa.setDescri(nodeObject.getProps().getDesc().getValue());
			}
			nfa.setNodeOrder(order++);
			if (nodeObject.getType().equals(JpdlInterface.NODE_TYPE_DECISON)) {
				StringBuffer sbBuffer = new StringBuffer();
				sbBuffer.append("{");
				Set<PathObject> set = nodeObject.getPaths();
				for (Iterator<PathObject> it = set.iterator(); it.hasNext();) {
					PathObject po = it.next();
					sbBuffer.append("\"").append(po.getTo()).append("\":\"")
							.append(po.getExpr()).append("\"");
					if (it.hasNext()) {
						sbBuffer.append(",");
					}
				}
				sbBuffer.append("}");
				nfa.setEl(sbBuffer.toString());
			}
			sets.add(nfa);
		}
		return sets;
	}

	/**
	 * 功能：
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public List<Object> getCountByCategory() {
		return processAttributeDao.getCountByCategory();
	}

	/**
	 * 功能：根据流程定义ID查询流程属性
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public ProcessAttribute findByDefineId(String defineId) {
		return processAttributeDao.getProcessAttributeByDefineId(defineId);
	}

	/**
	 * 功能：
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public List<ProcessAttribute> findByCategoryId(int categoryId, int companyId) {
		return processAttributeDao.findByCategoryId(categoryId, companyId);
	}

	/**
	 * @throws IOException
	 *             功能：
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public void deploy(int processAttributeId) throws IOException {
		ProcessAttribute pa = getProcessById(processAttributeId);
		String oldDefineId = pa.getProcessDefineId();
		if (oldDefineId != null && !oldDefineId.equals("")) {
			try {
				ProcessDefinition oldDefine = processEngine
						.getRepositoryService().createProcessDefinitionQuery()
						.processDefinitionId(oldDefineId).uniqueResult();
				if (oldDefine != null) {
					String oldDefploymentId = oldDefine.getDeploymentId();
					processEngine.getRepositoryService()
							.deleteDeploymentCascade(oldDefploymentId);
				}
			} catch (Exception e) {
			}
		}
		String xml = pa.getProcessDefineByXML();
		File tempFile = new File(pa.getProcessName() + ".jpdl.xml");
		FileWriterWithEncoding fw = new FileWriterWithEncoding(tempFile, "GBK",
				false);
		fw.write(xml);
		fw.flush();
		fw.close();
		String deployId = processEngine.getRepositoryService()
				.createDeployment().addResourceFromFile(tempFile).deploy();
		ProcessDefinition define = processEngine.getRepositoryService()
				.createProcessDefinitionQuery().deploymentId(deployId)
				.uniqueResult();
		pa.setProcessDefineId(define.getId());
		// 如果未发布，则修改状态为发布
		if (pa.getProcessState().intValue() == ProcessAttribute.NOT_DEPLOY) {
			pa.setProcessState(ProcessAttribute.DEPLOY_STATE);
		}
		;
		boolean result = tempFile.delete();
		if (!result) {
			return;
		}
		processAttributeDao.saveOrUpdate(pa);
	}

	/**
	 * 功能：停用
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public void stop(int processAttributeId) {
		ProcessAttribute pa = getProcessById(processAttributeId);
		pa.setProcessState(ProcessAttribute.STOP_STATE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pa.setProcessName(pa.getProcessName()+"(停用时间:"+sdf.format(new Date())+")");
		processAttributeDao.saveOrUpdate(pa);
	}

	/**
	 * 功能：
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public void start(int processAttributeId) {
		ProcessAttribute pa = getProcessById(processAttributeId);
		pa.setProcessState(ProcessAttribute.DEPLOY_STATE);
		String processName = pa.getProcessName();
		String[] strs = processName.split("\\(");
		pa.setProcessName(strs[0]);
		processAttributeDao.saveOrUpdate(pa);
	}

	/**
	 * 功能：流程复制
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public ProcessAttribute copyProcess(int processId) {
		ProcessAttribute newProcess = new ProcessAttribute();
		ProcessAttribute oldProcess = processAttributeDao.findOne(processId);
		newProcess.setCategoryId(oldProcess.getCategoryId());
		newProcess.setCompanyId(oldProcess.getCompanyId());
		newProcess.setDept(oldProcess.getDept());
		newProcess.setDirections(oldProcess.getDirections());
		newProcess.setFormId(oldProcess.getFormId());
		newProcess.setIsAttach(oldProcess.getIsAttach());
		newProcess.setProcessDefineByXML(oldProcess.getProcessDefineByXML());
		// 状态未发布
		newProcess.setProcessState(ProcessAttribute.NOT_DEPLOY);
		newProcess.setProcsssDefinByJSON(oldProcess.getProcsssDefinByJSON());
		newProcess.setProcessNameExpr(oldProcess.getProcessNameExpr());
		newProcess.setProcessNameBeginNum(oldProcess.getProcessNameBeginNum());
		newProcess.setProcessNamLength(oldProcess.getProcessNamLength());
		newProcess
				.setProcessNameCanupdate(oldProcess.getProcessNameCanupdate());
		newProcess.setSelectUserMode(oldProcess.getSelectUserMode());
		processAttributeDao.saveOrUpdate(newProcess);
		return newProcess;
	}

	/**
	 * 功能：根据流程名称重构流程定义
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public void rebuildProcessByProcessName(ProcessAttribute processAttribute,
			int type,int processAttributeId) {
		String jsonData = processAttribute.getProcsssDefinByJSON();
		if (jsonData != null && !jsonData.equals("")) {
			jsonData = jsonData.replaceAll("\"", "'");
			processAttribute.setProcsssDefinByJSON(jsonData);
		}
		// nodeFormAttributeDao.deleteBatch(processAttribute.getId());
		List<NodeObject> pResult = jsonParserService.parser(jsonData);
		String xmlJpdl = jsonParserService.generateJpdl(pResult, type);
		processAttribute.setProcessDefineByXML(xmlJpdl);
		processAttributeDao.saveOrUpdateBysql(processAttribute);
		List<NodeFormAttribute> list = generateNodeFormAttributes(pResult,
				processAttribute.getId());
		
		List<NodeFormAttribute> oldlist = nodeFormAttributeDao.findByProcessAttributeId(processAttributeId);
		for(int i=0; i<list.size(); i++){
		    NodeFormAttribute newNode = list.get(i);
		    for(int j=0; j<oldlist.size(); j++){
		        NodeFormAttribute oldNode = oldlist.get(j);
		        if(newNode.getNodeName().equals(oldNode.getNodeName())){
		            newNode.setCandidate(oldNode.getCandidate());
		            newNode.setDepts(oldNode.getDepts());
		            newNode.setRoles(oldNode.getRoles());
		            newNode.setSecretProperties(oldNode.getSecretProperties());
		            newNode.setWriteableProperties(oldNode.getWriteableProperties());
		        }
		    }
		}
		nodeFormAttributeDao.saveBatch(list);

	}

	/**
	 * 功能：
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public boolean checkProcessNameIsRepeat(String processName,
			Integer processAttributeId) {
		return processAttributeDao.checkProcessNameIsRepeat(processName,
				processAttributeId);
	}

	/**
	 * 功能：根据用户权限查找用户能发起的流程
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public List<ProcessAttribute> findProcessAttributesByPermissions(
			UserInfo userInfo, Integer categoryId,String searchkey) {
		List<ProcessAttribute> result = new ArrayList<ProcessAttribute>();
		// 查询用户的所有角色
		List<RoleUser> rolelist = roleUserDao.findAll("userId = ?", userInfo.getUserId());
		List<Integer> roles = new ArrayList<Integer>();
		if (rolelist != null && rolelist.size() > 0) {
			for (Iterator<RoleUser> iterator = rolelist.iterator(); iterator
					.hasNext();) {
				RoleUser ru = iterator.next();
				roles.add(ru.getRoleId());
			}
		}
		// 查询用户的所属部门
		List<Integer> groups = new ArrayList<Integer>();
		groups.add(userInfo.getGroupId());

		// 查找所有的流程
		List<ProcessAttribute> findlist = processAttributeDao
				.findByCategoryId(categoryId,searchkey);
		if (findlist != null && findlist.size() > 0) {
			for (Iterator<ProcessAttribute> iterator = findlist.iterator(); iterator
					.hasNext();) {
				ProcessAttribute temp = iterator.next();
				NodeFormAttribute start = temp.getStartNode();
				if (start == null) {
					continue;
				}
				boolean buser = start.getUserList().contains(
						userInfo.getUserId());
				if (buser) {
					result.add(temp);
					continue;
				}
				boolean bgroup = false;
				for (int i = 0; i < groups.size(); i++) {
					Integer groupId = groups.get(i);
					if (start.getGroupList().contains(groupId)) {
						bgroup = true;
						break;
					}
				}
				if (bgroup) {
					result.add(temp);
					continue;
				}
				boolean brole = false;
				for (int i = 0; i < roles.size(); i++) {
					Integer roleId = roles.get(i);
					if (start.getRoleList().contains(roleId)) {
						brole = true;
						break;
					}
				}
				if (brole) {
					result.add(temp);
				}
			}
		}

		return result;
	}

	/**
	 * 功能：文号计数器+1
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	@Override
	public void processNameBeginNumAddOne(int processAttributeId) {
		processAttributeDao.updateProcessAttributeBeginNum(processAttributeId);
	}

	@Override
	public boolean isCanApply(UserInfo userInfo, int processId) {
		boolean res = false;
		List<ProcessAttribute> result = findProcessAttributesByPermissions(
				userInfo, 0,"");
		if (result != null && result.size() > 0) {
			for (ProcessAttribute pa : result) {
				if (pa.getId() == processId) {
					res = true;
				}
			}
		}
		return res;
	}

	// 校验是否已设置经办权限
	@Override
	public List<String> checkIsSetCandidate(int processAttributeId) {
		// TODO Auto-generated method stub
		List<String> result = null;
		ProcessAttribute pa = processAttributeDao.findOne(processAttributeId);
		if (pa == null) {
			return null;
		}
		Set<NodeFormAttribute> nodeset = pa.getNodeSet();
		for (Iterator<NodeFormAttribute> it = nodeset.iterator(); it.hasNext();) {
			NodeFormAttribute nfa = it.next();
			if ((!nfa.getNodeType().equals("task"))
					&& (!nfa.getNodeType().equals(
							JpdlInterface.NODE_TYPE_MULTISIGN))
					&& (!nfa.getNodeType()
							.equals(JpdlInterface.NODE_TYPE_START))) {
				continue;
			}
			String str1 = nfa.getCandidate();
			String str2 = nfa.getRoles();
			String str3 = nfa.getDepts();
			if ((str1 == null || "".equals(str1))
					&& (str2 == null || "".equals(str2))
					&& (str3 == null || "".equals(str3))) {
				if (result == null) {
					result = new ArrayList<String>();
				}
				result.add(nfa.getNodeName());
			}
		}
		return result;
	}

	/**
	 * 功能：根据流程名称查询流程定义属性
	 * @param processName
	 * @return
	 */
	@Override
	public ProcessAttribute findByProcessName(String processName) {
		return processAttributeDao
				.getProcessAttributeByProcessName(processName);
	}

	@Override
	public File exportProcessDefine(int processAttributeId) throws IOException,
			TemplateException {
		// TODO Auto-generated method stub
		ProcessAttribute pa = getProcessById(processAttributeId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", pa.getProcessName());
		map.put("directions", pa.getDirections());
		map.put("jsondefine", pa.getProcsssDefinByJSON());
		map.put("expr", pa.getProcessNameExpr());
		map.put("beginNum", pa.getProcessNameBeginNum());
		map.put("numlength", pa.getProcessNamLength());
		map.put("namecanedit", pa.getProcessNameCanupdate());
		map.put("selectmode", pa.getSelectUserMode());
		map.put("companyId", pa.getCompanyId());

		Configuration cfg = new Configuration();
		InputStream is = this.getClass().getResourceAsStream(
				"/cn/com/qytx/cbb/customJpdl/service/exportTemplate.ftl");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String temp = br.readLine();
		while (temp != null) {
			sb.append(temp).append("\r\n");
			temp = br.readLine();
		}
		StringReader reader = new StringReader(sb.toString());
		Template template = new Template("exportTemplate.ftl", reader, cfg);
		template.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		template.process(map, stringWriter);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		File f = File.createTempFile(pa.getProcessName()+date, ".xml");
		FileWriter fw = new FileWriter(f);
		fw.write(stringWriter.toString());
		fw.flush();
		fw.close();
		return f;
	}

	/**
	 * 功能：导入流程定义
	 * @param pa
	 * @param type
	 * @throws Exception
	 */
	@Override
	public void importProcess(ProcessAttribute pa, int type) throws Exception {
		// TODO Auto-generated method stub
		save(pa);
		updateProcessAttributeByJsonData(pa.getId(),
				pa.getProcsssDefinByJSON(), type);
	}

}
