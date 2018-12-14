package cn.com.qytx.cbb.publicDom.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;

public class DocTemplateListAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	/** 正文模板服务类   */
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	// 分类设置服务类
	@Resource(name = "formCategoryService")
	IFormCategory formCategoryService;
	@Resource
	IDict dictService;
		
	private String name;
	private   Integer docTemplateType;
    
    public String list(){
    	int companyId =0;
    	 Object userObject = this.getSession().getAttribute("adminUser");
    	 if(userObject!=null){
    		 UserInfo userInfo = (UserInfo) userObject;
    		 companyId = userInfo.getCompanyId();
    	 }
    	List<DocTemplate> list=null;
        Page<DocTemplate> pageInfo = docTemplateService.findPageList(docTemplateType,name,companyId, getPageable());
        list = pageInfo.getContent();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if(list!=null){
			for(int i=0; i<list.size();i++){
				DocTemplate dt = list.get(i);
            	Map<String, Object> map = new HashMap<String, Object>();
            	String createTimeStr="&nbsp;";
            	Date createTime = dt.getCreateTime();
            	if(createTime!=null){
            		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            		createTimeStr = sm.format(createTime);
            	}
            	String typeName = "";
            	if(dt.getCategoryId()!=null){
//            		FormCategory fc = formCategoryService.findById(dt.getCategoryId());
//            		if(fc!=null){
//            			typeName = fc.getCategoryName();
//            		}
            		Dict d = dictService.findOne(dt.getCategoryId());
            		if(d!=null){
            			typeName = d.getName();
            		}
            	}
	            map.put("no", i+1);
            	map.put("docTemplateName", dt.getDocTemplateName());
            	map.put("fileName", dt.getFileName());
            	map.put("typeName", typeName);
            	map.put("createTime", createTimeStr);
            	map.put("docTemplateId", dt.getDocTemplateId());
            	map.put("docurl", dt.getDocUrl());
                mapList.add(map);
			}
		} 
    	ajaxPage(pageInfo, mapList);
    	return null;
    }
    
    /**
     * 判断当前用户是否有可以使用的模板
     * @return
     */
    public String existListByCurrentUser(){
            try {
            	int companyId =0;
            	int userId =0;
            	 Object userObject = this.getSession().getAttribute("adminUser");
            	 if(userObject!=null){
            		 UserInfo userInfo = (UserInfo) userObject;
            		 companyId = userInfo.getCompanyId();
            		 userId = userInfo.getUserId();
            	 }
            	 List<DocTemplate> list = docTemplateService.findAll(companyId,userId);
            	 if(list!=null&&list.size()>0){
            		 ajax("1");
            	 }else{
            		 ajax("0");
            	 }
			} catch (Exception e) {
				e.printStackTrace();
			}
            return null;
        }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDocTemplateType() {
		return docTemplateType;
	}

	public void setDocTemplateType(Integer docTemplateType) {
		this.docTemplateType = docTemplateType;
	}
}
