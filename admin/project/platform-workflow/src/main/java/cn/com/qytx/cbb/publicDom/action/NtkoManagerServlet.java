package cn.com.qytx.cbb.publicDom.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.domain.HistoryDoc;
import cn.com.qytx.cbb.publicDom.service.HistoryDocService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.spring.SpringUtil;

public class NtkoManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public NtkoManagerServlet() {
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
		IAttachment attachmentService =(IAttachment)SpringUtil.getBean("attachmentService");
		IWorkFlowService workflowService = (IWorkFlowService) SpringUtil.getBean("workFlowService");
		 FilePathConfig filePathConfig = (FilePathConfig) SpringUtil.getBean("filePathConfig");
	    FileItem officeFileItem =null ;
	    HttpSession session=request.getSession();//
	    UserInfo userInfo=null;
	    if(session.getAttribute("adminUser")!=null){
	    	userInfo = (UserInfo)session.getAttribute("adminUser");
	    }
	    String documentExtId="";
	    String docType="";
		boolean isNewRecode = true ;
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
						if(item.getFieldName().equalsIgnoreCase("documentExtId"))
						{
							documentExtId=item.getString("utf-8").trim();
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
						int attachId = saveFileToDisk(officeFileItem,documentExtId,docType,userInfo);
						
						//add by 贾永强
						String taskId = request.getParameter("taskId");
						if(!StringUtils.isEmpty(taskId)){
							String eid = workflowService.getInstanceIdByTask(taskId);
							UserInfo ui = (UserInfo) session.getAttribute("adminUser");
							Attachment attachment = attachmentService.getAttachment(attachId);
							String attachFile = attachment.getAttachFile();
							String catalinaHome = filePathConfig.getFileUploadPath();
							String file = catalinaHome+"/upload/"+attachFile;
							saveHisotyDoc(new File(file), ui.getUserName(), eid,docType);
						}
					}
				} 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println("success");
		out.flush();
	}
	public int saveFileToDisk(FileItem officeFileItem, String documentExtId,String fileType,UserInfo userInfo) throws Exception
	{
		IDocumentExtService documentExtService =(IDocumentExtService)SpringUtil.getBean("documentExtService");
		IAttachment attachmentService =(IAttachment)SpringUtil.getBean("attachmentService");
		 FilePathConfig filePathConfig = (FilePathConfig) SpringUtil.getBean("filePathConfig");
		File officeFileUpload = null;
		 String filePath="";
		//查询流程实例是否保存附件信息 如果没保存 保存附件  
		 int id=0;
		 if(documentExtId!=null&&!"".equals(documentExtId)){
			 id = Integer.parseInt(documentExtId);
		 }
		DocumentExt documentExt=documentExtService.findOne(id);
		String uploadPath = ""; //上传的目录
		String processInstanceId = "";
        String catalinaHome = filePathConfig.getFileUploadPath(); //tomcat根目录
        int attachId =0;
        if(documentExt!=null){ 
        	attachId = documentExt.getAttachId();
        	processInstanceId =documentExt.getProcessInstanceId();
        }
        //附件未上传
		if(attachId<=0){
			SimpleDateFormat sp = new SimpleDateFormat("MM/dd");
	        String datePath = sp.format(new Date()); //每月一个上传目录
	        String module="ntkoDom";
	        uploadPath = "/upload/" + module + "/" + datePath + "/";
	        filePath = catalinaHome+uploadPath+processInstanceId+"."+fileType;
			Attachment att = new Attachment();
         	att.setAttachFile("/" + module + "/" + datePath + "/"+processInstanceId+"."+fileType);
         	att.setAttachName(processInstanceId+"."+fileType);
         	if(userInfo!=null){
         		att.setCreateTime(new Timestamp(new Date().getTime()));
         		att.setCompanyId(userInfo.getCompanyId());
         		att.setCreateUserId(userInfo.getUserId());
         		att.setIsDelete(0);
         	}
         	attachmentService.saveOrUpdate(att);
         	if(documentExt!=null){
         		attachId = att.getId();
         		documentExt.setAttachId(att.getId());
             	documentExtService.saveOrUpdate(documentExt);
         	}
		}else{ //如果附件已经上传
			Attachment attachment =attachmentService.getAttachment(documentExt.getAttachId());
			filePath=catalinaHome+ "/upload/"+attachment.getAttachFile();
		}
		officeFileUpload = new File(catalinaHome+uploadPath);
	     if(officeFileUpload.exists() == false) {
	     		boolean result = officeFileUpload.mkdirs();
	     		if(!result)
	     		{
	     		    return 0;
	     		}
	      } 
	     officeFileUpload =  new File(filePath);
	     officeFileItem.write(officeFileUpload);
	     return attachId;
	}
	
	/**
	 * @throws Exception 
	 * add by 贾永强
	 * 功能：保存每次的操作记录,file:源文件
	 * @param
	 * @return
	 * @throws   
	 */
	private void saveHisotyDoc(File sourceFile,String user,String instanceId,String docType) throws Exception{
		 FilePathConfig filePathConfig = (FilePathConfig) SpringUtil.getBean("filePathConfig");
		 String catalinaHome = filePathConfig.getFileUploadPath(); //tomcat根目录
		 String refPath = "/upload/ntkoDom/";
		 File file = new File(catalinaHome+refPath);
		 file.mkdir();
		 String currens=String.valueOf(Calendar.getInstance().getTimeInMillis());
		 File copyFile = new File(catalinaHome+refPath+currens+"."+docType);
		 copyFile.createNewFile();
		 copyFile(sourceFile,copyFile);
		 HistoryDoc doc = new HistoryDoc();
		 doc.setUpdateUser(user);
		 doc.setUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		 doc.setInstanceId(instanceId);
		 doc.setHostoryDocPath("/ntkoDom/"+currens+"."+docType);
		 doc.setCompanyId(TransportUser.get().getCompanyId());
		 HistoryDocService hds = (HistoryDocService) SpringUtil.getBean("historyDocService");
		 
		 hds.saveOrUpdate(doc);
	}
	
	private void  copyFile(File sourceFile,File destFile) throws IOException{
		  FileChannel in = null;
	        FileChannel out = null;
	        FileInputStream inStream = null;
	        FileOutputStream outStream = null;
	        try {
	            inStream = new FileInputStream(sourceFile);
	            outStream = new FileOutputStream(destFile);
	            in = inStream.getChannel();
	            out = outStream.getChannel();
	            in.transferTo(0, in.size(), out);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	        	if(inStream!=null){
	        		inStream.close();
	        	}
	        	if(in!=null){
	        		in.close();
	        	}
	        	if(outStream!=null){
	        		outStream.close();
	        	}
	        	if(out!=null){
	        		out.close();
	        	}
	        }
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
