package cn.com.qytx.fileserver.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import cn.com.qytx.fileserver.bussiness.UploadBusssiness;
import cn.com.qytx.fileserver.utils.SysUtil;

import com.google.gson.Gson;
/**
 * 功能: 上传servlet 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年4月23日
 * 修改日期: 2015年4月23日
 * 修改列表:
 */
public class FileUploadServlet extends HttpServlet{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1255315978495521503L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		 //if(!ServletFileUpload.isMultipartContent(request)){//用 ServletFileUpload 类的静态方法 isMultipartContent 判断 request 是否是 multipart/form-data 类型
	         // SysUtil.toClient("只能 multipart/form-data 类型数据", response);
	        //  return;
	    // }
		 
		 String companyId = request.getParameter("companyId")==null?"0":request.getParameter("companyId").toString();
		 String userId = request.getParameter("userId")==null?"0":request.getParameter("userId").toString();
		 String moduleCode = request.getParameter("moduleCode")==null?"0":request.getParameter("moduleCode").toString();
		 String origin =  request.getParameter("origin")==null?"":request.getParameter("origin").toString();
		 
		 SimpleDateFormat sp = new SimpleDateFormat("yyyy/MM/dd");
         String datePath = sp.format(new Date()); // 每天一个上传目录
		 
		 //创建 DiskFileItemFactory 对象
	     DiskFileItemFactory factory = new DiskFileItemFactory();
	     ServletFileUpload upload = new ServletFileUpload(factory);//创建 ServletFileUpload 对象，构造的时候传一个 DiskFileItemFactory 对象进去
	     upload.setHeaderEncoding("UTF-8");//设置普通字段名称和文件字段的文件名所采用的字符集编码
	     List<FileItem> list = null;
	     try {
	    	 Properties prop = new Properties();
	    	 prop.load(this.getClass().getResourceAsStream("/application.properties"));//获得配置文件信息
	    	 
	    	 String uploadPath = prop.getProperty("uploadPath").toString();
	    	 
			 list = upload.parseRequest(request);
			 for(FileItem fileItem:list){
				    if (fileItem.isFormField()) {
				    	if(fileItem.getFieldName().equals("companyId")){
				    		String tempCompanyId = fileItem.getString("utf-8");
				    		if(StringUtils.isNotEmpty(tempCompanyId)){
				    			companyId = tempCompanyId;
				    		}
				    	}else if(fileItem.getFieldName().equals("userId")){
				    		String tempUserId = fileItem.getString("utf-8");
				    		if(StringUtils.isNotEmpty(tempUserId)){
				    			userId = tempUserId;
				    		}
				    	}else if(fileItem.getFieldName().equals("moduleCode")){
				    		String tempModuleCode = fileItem.getString("utf-8");
				    		if(StringUtils.isNotEmpty(tempModuleCode)){
				    			moduleCode = tempModuleCode;
				    		}
				    	}else if(fileItem.getFieldName().equals("origin")){
				    		String tempOrigin = fileItem.getString("utf-8");
				    		if(StringUtils.isNotEmpty(tempOrigin)){
				    			origin = tempOrigin;
				    		}
				    	}
				        
				    }
				}
			 System.out.println("接收到的参数----------------------------companyId:"+companyId+" userId:"+userId+" moduleCode:"+moduleCode+" origin:"+origin);
			 String relatePath = companyId+"/"+moduleCode+"/"+datePath;
	    	 String targetDirectory = uploadPath+"/"+relatePath;
	    	 File folder = new File(targetDirectory);
	    	 if(!folder.exists()){
	    		 folder.mkdirs();
	    	 }
	    	 
			 /**
			  * 处理上传文件
			  */
			 ApplicationContext context = SysUtil.getApplicationContext(request);
			 List<Map<String, Object>> listMap = new UploadBusssiness(list, relatePath, targetDirectory,context,Integer.valueOf(companyId),Integer.valueOf(userId)).upload();
			 if(origin.equals("app")){
				SysUtil.toClient("100||"+new Gson().toJson(listMap), response);
			 }else{
				SysUtil.toClient(new Gson().toJson(listMap), response);
			 }
			
		 } catch (Exception e) {
			e.printStackTrace();
			if(origin.equals("app")){
				SysUtil.toClient("101||", response);
			}
		 }//解析 request 对象 得到一个包含 FileItem 对象的 list
	}
}
