package cn.com.qytx.cbb.publicDom.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.spring.SpringUtil;

public class NtkoTemplateManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public NtkoTemplateManagerServlet() {
		super();
	}
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    FileItem officeFileItem =null ;
	    HttpSession session=request.getSession();//
	    UserInfo userInfo=null;
	    if(session.getAttribute("adminUser")!=null){
	    	userInfo = (UserInfo)session.getAttribute("adminUser");
	    }
	    String docTemplateId="";
	    String docType="";
	    String resStr="success";
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);// 设置最多只允许在内存中存储的数据,单位:字节
			ServletFileUpload upload = new ServletFileUpload(factory);
			//upload.setSizeMax(1024*1024*4);//设置允许用户上传文件大小,单位:字节
			List fileItems = null;
			fileItems=upload.parseRequest(request);
			Iterator iter = fileItems.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if(item.isFormField()){
						if(item.getFieldName().equalsIgnoreCase("docTemplateId"))
						{
							docTemplateId=item.getString("utf-8").trim();
						}	
						if(item.getFieldName().equalsIgnoreCase("docType"))
						{
							docType=item.getString("utf-8").trim();
						}	
					} 
			   }
		   Iterator iter2 = fileItems.iterator();
			while (iter2.hasNext()) {
				FileItem item = (FileItem) iter2.next();
				if(!item.isFormField()){
					if(item.getFieldName().equalsIgnoreCase("upLoadFile")){
						officeFileItem=item;
						InputStream is = officeFileItem.getInputStream();
						int res = saveFileToDisk(officeFileItem,docTemplateId,docType,userInfo,request);
						if(res==0){
							resStr="保存失败！";
						}
					}
				} 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println(resStr);
		out.flush();
		out.close();
	}
	
	public int saveFileToDisk(FileItem officeFileItem, String documentExtId,String fileType,UserInfo userInfo, HttpServletRequest request) throws Exception
	{
		IDocTemplateService docTemplateService =(IDocTemplateService)SpringUtil.getBean("docTemplateService");
		File officeFileUpload = null;
		 String filePath="";
		//查询流程实例是否保存附件信息 如果没保存 保存附件  
		 int id=0;
		 if(documentExtId!=null&&!"".equals(documentExtId)){
			 id = Integer.parseInt(documentExtId);
		 }
		DocTemplate docTemp = docTemplateService.findOne(id);
		String uploadPath = ""; //上传的目录
        //附件未上传
		if(docTemp!=null){
			  //如果附件已经上传
			uploadPath=getFilePath(docTemp,request);
		}
		officeFileUpload = new File(uploadPath);
	     if(officeFileUpload.exists() == false) {
	     		boolean result = officeFileUpload.mkdirs();
	     		if (!result)
	     		{
	     		    return 0;
	     		}
	      } 
	     officeFileUpload =  new File(uploadPath);
	     officeFileItem.write(officeFileUpload);
	     return docTemp!=null?docTemp.getDocTemplateId():0;
	}
	
	  
	
 /**
  * 获取附件文件
  * @param docTemp
  * @param request
  * @return
  */
	private String getFilePath(DocTemplate docTemp, HttpServletRequest request) {
		String res = "";
		if(docTemp!=null){
			String docUrl = 	 docTemp.getDocUrl();
			String filePath ="/upload/docTemplate/"+docTemp.getCompanyId()+"/"+docUrl;
			res= request.getSession().getServletContext().getRealPath(filePath);
		}
		return res;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
