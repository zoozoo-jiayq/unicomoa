package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customJpdl.dao.NodeFormAttributeDao;
import cn.com.qytx.cbb.customJpdl.dao.ProcessAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.publicDom.dao.DocumentTypeDao;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.RoleUserDao;
import cn.com.qytx.platform.org.domain.RoleUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.StringUtil;

/**
 * 功能: 公文类型实现类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-16
 * 修改日期: 2013-4-16
 * 修改列表:
 */
@Service("documentTypeService")
@Transactional
public class DocumentTypeImpl extends BaseServiceImpl<DocumentType> implements DocumentTypeService {
	
	/** 公文类型Dao */
	@Resource(name="documentTypeDao")
	private DocumentTypeDao  documentTypeDao;
	
	@Resource(name="formPropertiesService")
	private IFormProperties formPropertiesService;
	
	@Resource
	private IDict dictService;
	
	@Resource
	private NodeFormAttributeDao nodeFormAttributeDao;
	
	@Resource
	private ProcessAttributeDao processAttributeDao;

	@Resource
	private RoleUserDao<RoleUser> roleUserDao;
	
	@Resource
	private IGroup groupService;
	
	@Resource
	private IUser userService;
	
	/**
	 * 查询分页信息
	 */
	@Override
	public Page<DocumentType> findPageList(String doctypeName, int categoryId,
			Pageable pageInfo) {
		return documentTypeDao.findPageList(doctypeName,categoryId,pageInfo);
	}

	@Override
	public DocumentType saveOrUpdate(DocumentType entity) {
		// TODO Auto-generated method stub
		//重构打印代码
		generatePrintCode(entity);
		//收/发文标识是否变更，如果变更，则重新生成4个相应的节点
		if(entity.getDoctypeId()!=null){
			int oldflag = documentTypeDao.getDocumentTypeFlag(entity.getDoctypeId());
			int newflag = (entity.getGongwenType()==null?1:entity.getGongwenType());
			if(oldflag!=newflag){
				documentTypeDao.deleteNodesByDocumentType(entity.getDoctypeId());
				super.saveOrUpdate(entity);
				initDocumentTypeToNodes(entity.getDoctypeId());
			}else{
				super.saveOrUpdate(entity);
			}
		}else{
			super.saveOrUpdate(entity);
		}
		return null;
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void beginNumAddOne(int id) {
		DocumentType type = documentTypeDao.findOne(id);
		type.setBeginNum(type.getBeginNum()+1);
		documentTypeDao.saveOrUpdate(type);
	}

	/**
	 * 功能：根据打印模板生成实际的打印代码,add by 贾永强
	 * @param
	 * @return
	 * @throws   
	 */
	private void generatePrintCode(DocumentType docType){
		if(docType.getTaoDa()!=null && docType.getTaoDa()==1){
			if(docType.getPrintTemplateCode()!=null){
				String result = docType.getPrintTemplateCode();
				List<FormProperties> list = formPropertiesService.findByFormId(docType.getFormId());
				for(int i=0;i<list.size();i++){
					FormProperties fp = list.get(i);
					String name = fp.getPropertyName();
					String cnName = fp.getPropertyNameCh();
					result = result.replaceAll("\""+cnName+"\"", "document.getElementsByName(\""+name+"\")[0].value");
				}
				docType.setPrintCode(result);		
				String temp = docType.getPrintTemplateCode();
				temp = temp.replaceAll("<", "&lt;");
				temp = temp.replaceAll(">", "&gt;");
				temp = temp.replaceAll("\r\n", "");
				docType.setPrintTemplateCode(temp);
			}
		}else if(docType.getTaoDa()!=null && docType.getTaoDa() == 2){
			docType.setPrintTemplateCode("");
			docType.setPrintCode("");
		}
		
		
	}

	@Override
	public List<DocumentType> getDocumentTypeListByUser(UserInfo loginUser,int groupId) {
		    return documentTypeDao.getDocumentTypeListByUser(loginUser,  groupId);
	}

	@Override
	public List<Dict> getSecretLevel() {
		// TODO Auto-generated method stub
		return dictService.findListByInfoType("miji");
	}

	@Override
	public List<Dict> getHuanji() {
		// TODO Auto-generated method stub
		return dictService.findListByInfoType("huanji");
	}


	@Override
	public List<DocumentType> getDocumentTypesByCategoryId(int infoTypeId,UserInfo currentUser,Integer gongwenType) {
		// TODO Auto-generated method stub
		List<DocumentType> result = new ArrayList<DocumentType>();
		
		List<DocumentType> list = documentTypeDao.getDocumentTypesbyInfoTypeId(infoTypeId,gongwenType);
		
		String nodeName = "";
		if(gongwenType == null){
			
		}else if(gongwenType == 1){
			nodeName = "收文登记";
		}else if(gongwenType == 2){
			nodeName = "发文拟稿";
		}
		List<NodeFormAttribute> nodelist = nodeFormAttributeDao.findAll("nodeName = ? ", nodeName); 
		
		for(int i=0; i<list.size(); i++){
			DocumentType doc = list.get(i);
			NodeFormAttribute nfa = nodeFormAttributeDao.findOne("nodeName = ? and processAttribute.id = ?", nodeName,doc.getDoctypeId()*-1);
			boolean flag = isHasPower(nfa, currentUser);
			if(flag){
				result.add(doc);
			}
		}
		return result;
	}
	
	/**
	 * @param nfa
	 * @param currentUser
	 * @return  true:有权限,false无权限
	 */
	private boolean isHasPower(NodeFormAttribute nfa,UserInfo currentUser){
		if(nfa == null){
			return false;
		}
		
		String candidate = nfa.getCandidate();
		if(candidate!=null){
			String[] candidates = candidate.split(",");
			for(String str:candidates){
				if(!StringUtil.isEmpty(str) && str.equals(currentUser.getUserId().toString())){
					return true;
				}
			}
			
		}
		
		String roles = nfa.getRoles();
		if(roles!=null && !roles.equals("")){
			roles = roles.substring(0, roles.length()-1);
			List<RoleUser> rolelist = roleUserDao.findAll("userId = ? and roleId in ("+roles+")", currentUser.getUserId());
			if(rolelist!=null && rolelist.size()>0){
				return true;
			}
		}
		
		String groupIds = nfa.getDepts();
		if(groupIds!=null){
			List<UserInfo> userlist = userService.findUsersByGroupId(groupIds);
			for(int i=0; i<userlist.size(); i++){
				UserInfo temp = userlist.get(i);
				if(temp.getUserId().intValue() == currentUser.getUserId().intValue()){
					return true;
				}
			}
		}
		
		return false;
	}
	

	@Override
	public DocumentType getDocumentByName(String docName) {
		// TODO Auto-generated method stub
		return documentTypeDao.getDocumentTypeByDocName(docName);
	}

	/* (non-Javadoc)
	 * 根据公文类型初始化各个节点，如果已经初始化则返回
	 * @see cn.com.qytx.cbb.publicDom.service.DocumentTypeService#initDocumentTypeToNodes(int)
	 */
	@Override
	public List<NodeFormAttribute> initDocumentTypeToNodes(int documentType) {
		// TODO Auto-generated method stub
		List<NodeFormAttribute> list = nodeFormAttributeDao.findByProcessAttributeId(documentType*-1);
		if(list==null || list.size() == 0 ){
			DocumentType dt = this.findOne(documentType);
			if(dt.getGongwenType() == DocumentType.GATHER){
				list = generateGatherNode(documentType);
			}else if(dt.getGongwenType() == DocumentType.DISPATCH){
				list = generateDispatchNode(documentType);
			}
		}
		return list;
	}
	
	
	
	/**
	 * 构造发文节点列表
	 * @param documentType
	 * @return
	 */
	private List<NodeFormAttribute> generateDispatchNode(int documentType){
		List<NodeFormAttribute> list = new ArrayList<NodeFormAttribute>();
		ProcessAttribute pa = new ProcessAttribute();
		pa.setId(documentType*-1);
		NodeFormAttribute nf1 = new NodeFormAttribute();
		nf1.setCreateDate(new Date());
		nf1.setNodeName("发文拟稿");
		nf1.setNodeOrder(1);
		nf1.setCompanyId(TransportUser.get().getCompanyId());
		nf1.setProcessAttribute(pa);
		
		NodeFormAttribute nf2 = new NodeFormAttribute();
		nf2.setCreateDate(new Date());
		nf2.setNodeName("发文核稿");
		nf2.setNodeOrder(2);
		nf2.setCompanyId(TransportUser.get().getCompanyId());
		nf2.setProcessAttribute(pa);
		
		NodeFormAttribute nf3 = new NodeFormAttribute();
		nf3.setCreateDate(new Date());
		nf3.setNodeName("套红盖章");
		nf3.setNodeOrder(3);
		nf3.setCompanyId(TransportUser.get().getCompanyId());
		nf3.setProcessAttribute(pa);
		
		NodeFormAttribute nf4 = new NodeFormAttribute();
		nf4.setCreateDate(new Date());
		nf4.setNodeName("发文分发");
		nf4.setNodeOrder(4);
		nf4.setCompanyId(TransportUser.get().getCompanyId());
		nf4.setProcessAttribute(pa);;
		
		list.add(nf1);
		list.add(nf2);
		list.add(nf3);
		list.add(nf4);
		nodeFormAttributeDao.saveBatch(list);
		return list;
	}
	
	/**
	 * 构造发文节点列表
	 * @param documentType
	 * @return
	 */
	private List<NodeFormAttribute> generateGatherNode(int documentType){
		ProcessAttribute pa = new ProcessAttribute();
		pa.setId(documentType*-1);
		List<NodeFormAttribute> list = new ArrayList<NodeFormAttribute>();
		NodeFormAttribute nf1 = new NodeFormAttribute();
		nf1.setCreateDate(new Date());
		nf1.setNodeName("收文登记");
		nf1.setNodeOrder(1);
		nf1.setCompanyId(TransportUser.get().getCompanyId());
		nf1.setProcessAttribute(pa);;
		
		NodeFormAttribute nf2 = new NodeFormAttribute();
		nf2.setCreateDate(new Date());
		nf2.setNodeName("领导批阅");
		nf2.setNodeOrder(2);
		nf2.setCompanyId(TransportUser.get().getCompanyId());
		nf2.setProcessAttribute(pa);;
		
		NodeFormAttribute nf3 = new NodeFormAttribute();
		nf3.setCreateDate(new Date());
		nf3.setNodeName("收文分发");
		nf3.setNodeOrder(3);
		nf3.setCompanyId(TransportUser.get().getCompanyId());
		nf3.setProcessAttribute(pa);
		
		NodeFormAttribute nf4 = new NodeFormAttribute();
		nf4.setCreateDate(new Date());
		nf4.setNodeName("收文阅读");
		nf4.setNodeOrder(4);
		nf4.setCompanyId(TransportUser.get().getCompanyId());
		nf4.setProcessAttribute(pa);;
		
		list.add(nf1);
		list.add(nf2);
		list.add(nf3);
		list.add(nf4);
		nodeFormAttributeDao.saveBatch(list);
		return list;
	}

	@Override
	public void updateBeginNum(int docTypeId) {
		documentTypeDao.updateBeginNum(docTypeId);
	}

	
}
