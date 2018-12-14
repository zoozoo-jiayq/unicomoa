package cn.com.qytx.cbb.publicDom.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.base.action.BaseActionSupport;

public class NtkoTemplateManagerAction  extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
    
	/** 正文模板服务类   */
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	/** 公文类型Serivce */
	@Resource(name="documentTypeService")
	DocumentTypeService  documentTypeService;
	
 
	private Integer docTemplateId; 

	public String getOfficeContent(){
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		String path="";
	   path=getPath(docTemplateId,request,response);
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			OutputStream out = response.getOutputStream();
			byte[] bs = new byte[1024];
			while ((fis.read(bs) != -1)) {
				out.write(bs);
			}
			out.flush();
			out.close();
			out = null;
			fis.close();
			fis = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	/**
	 * 功能：获取文件路径
	 * @param processInstanceId
	 * @param request
	 * @param response
	 * @return
	 */
	private String getPath(Integer docTemplateId, HttpServletRequest request, HttpServletResponse response) {
		String res="";
		//查询流程实例是否保存附件信息 如果没保存 保存附件  
		  DocTemplate documentTemp = docTemplateService.findOne(docTemplateId);
//		 String docUrl;
		 String filePath = "/logined/publicDom/ntkodoc/newWordTemplate.doc";
		if(documentTemp!=null){
//			docUrl = 	 documentTemp.getDocUrl();
			filePath ="/upload/docTemplate/"+documentTemp.getCompanyId()+"/"+ documentTemp.getDocUrl();
			res= request.getSession().getServletContext().getRealPath(filePath);
		}
		return res;
	}
	

	
	
	public Integer getDocTemplateId() {
		return docTemplateId;
		
	}
  
	public void setDocTemplateId(Integer docTemplateId) {
		this.docTemplateId = docTemplateId;
	}


 
	
	 
 
}
