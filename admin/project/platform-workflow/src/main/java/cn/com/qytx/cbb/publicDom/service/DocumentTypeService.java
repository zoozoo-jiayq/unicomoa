package cn.com.qytx.cbb.publicDom.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：公文类型接口
 * 版本: 1.0
 * 开发人员：陈秋利
 * 创建日期: 2013-4-16 上午11:12:50 
 * 修改日期：2013-4-16 上午11:12:50 
 * 修改列表：
 */
public interface DocumentTypeService extends BaseService<DocumentType> ,Serializable{

	/**
	 * 功能：查询分页信息
	 * @param doctypeName 名称
	 * @param categoryId  类别
	 * @param pageInfo
	 * @return
	 */
	Page<DocumentType> findPageList(String doctypeName, int categoryId,
			Pageable pageInfo);

	
	/**
	 * 功能：文号计数器+1
	 * @param
	 * @return
	 * @throws   
	 */
	public void beginNumAddOne(int id);

	/**
	 * 功能：查询当前用户有权限的公文类型
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	List<DocumentType> getDocumentTypeListByUser(UserInfo loginUser,int groupId);
	
	/**
	 * 功能：密级列表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public List<Dict> getSecretLevel();
	
	/**
	 * 功能：缓急列表
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public List<Dict> getHuanji();
	
	
	/**
	 * 功能：根据一级分类查询二级公文类型,查询我有权限发起的公文
	 * 作者：jiayongqiang
	 * 参数：infoTypeId:公文类型(一级分类)，currentUser:当前登录用户，gongwenType:收/发文类型
	 * 输出：
	 */
	public List<DocumentType> getDocumentTypesByCategoryId(int infoTypeId,UserInfo currentUser,Integer gongwenType);
	
	/**
	 * 功能：根据公文名称获取公文类型
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public DocumentType getDocumentByName(String docName);
	
	/**根据公文类型，初始化各个节点，如果该类型已经初始化，则返回
	 * 
	 * @param documentType：对应DocumentType表的ID，同时对应tb_cbb_node_form_attribute表的process_attribute_id,对应规则：documentType*-1
	 * @return
	 */
	public List<NodeFormAttribute> initDocumentTypeToNodes(int documentType);
	
	public void updateBeginNum(int docTypeId);
	
}
