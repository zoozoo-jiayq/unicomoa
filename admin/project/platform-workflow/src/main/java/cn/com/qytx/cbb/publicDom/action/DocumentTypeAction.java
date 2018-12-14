package cn.com.qytx.cbb.publicDom.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.vo.DocumentTypeVo;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;

import com.opensymphony.xwork2.ActionContext;

/**
 * 功能: 文档类型Action
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-16
 * 修改日期: 2013-4-16
 * 修改列表:
 */
public class DocumentTypeAction extends BaseActionSupport {
	/** 公文类型Serivce */
	@Resource(name="documentTypeService")
	DocumentTypeService  documentTypeService;
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	@Resource(name="documentExtService")
	private IDocumentExtService documentExtService;
	/** 正文模板服务类   */
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	/**组信息*/
	@Resource(name = "groupService")
	IGroup groupService;
	@Resource
	private IDict infoTypeImpl;
    private String doctypeName;
    private int categoryId;
    
	/**
	 * 功能：返回公文类型列表
	 * @return
	 */
	public String docTypeList(){
		
		return SUCCESS;
	}
	
	public List<DocumentTypeVo> getCategories(){
		List<DocumentTypeVo> rlist = new ArrayList<DocumentTypeVo>();
		List<Dict> list = infoTypeImpl.findList("dom_category", 1);
		List<DocumentType> doclist = documentTypeService.findAll();
		for(int i=0; i<list.size(); i++){
			if(categoryId ==0){
				categoryId = list.get(0).getId();
			}
			int dictId = list.get(i).getId();
			int count = 0 ;
			for(int j=0; j<doclist.size(); j++){
				DocumentType dt = doclist.get(j);
				if(dt.getCategoryId() == dictId){
					count++;
				}
			}
			if(count>0){
				DocumentTypeVo dtv = new DocumentTypeVo(list.get(i), count);
				if(list.get(i).getId() == categoryId){
					dtv.setSelect(true);
				}
				rlist.add(dtv);
			}
		}
		
		return rlist;
	}
	
	
	
	public String showList(){
        Page<DocumentType> pageInfo = documentTypeService.findPageList(doctypeName,categoryId, getPageable());
        List<DocumentType> list = pageInfo.getContent();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					DocumentType dt = list.get(i);
                	Map<String, Object> map = new HashMap<String, Object>();
  	                map.put("no", i+1);
  	                if(dt.getDocDesc() == null ){
  	                	dt.setDocDesc("");
  	                }
                	map.put("docType", dt);
                	map.put("count", getCount(dt));
                    mapList.add(map);
				}
			} 
       ajaxPage(pageInfo, mapList);
       return null;
	}

	/**
	 * 功能：获取使用该模版的流程个数
	 * @param dt
	 * @return
	 */
	private int getCount(DocumentType dt) {
		 int res=0;
		 if(dt!=null){
			 res = documentExtService.getDocTypeCount(dt.getDoctypeId());
		 }
		return res;
	}

	public List<DocTemplate> getDocTemplates(){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		return docTemplateService.findAll(user.getCompanyId());
	}

	public String getDoctypeName() {
		return doctypeName;
	}

	public void setDoctypeName(String doctypeName) {
		this.doctypeName = doctypeName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
}
