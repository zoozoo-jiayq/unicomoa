package cn.com.qytx.cbb.publicDom.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.cbb.publicDom.util.OfficeUtils;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

public class DocTemplateAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	/** 正文模板服务类   */
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	@Resource
	private FilePathConfig filePathConfig;
    /**
     * 用户信息
     */
    @Resource(name = "userService")
    IUser userService;
	private File myfile;
	private String name;
	private String fileName;
	private   Integer docTemplateType;
	private   Integer docTemplateId;
	private String ids;
	private String nextUser;
	private String docUrl;
	
   public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

/**
    * 保存
    * @return
    */
	public String saveDocTemplate(){
			try {
				Object userObject=this.getSession().getAttribute("adminUser");
	        	UserInfo userInfo = null;
				if(userObject!=null){
					  userInfo=(UserInfo)userObject;;
				}
				//基于myFile创建一个文件输入流  
				if(myfile!=null){
					 InputStream is;
						is = new FileInputStream(myfile);
						 // 设置上传文件目录  
			            String uploadPath =getUploadPath();
			            
				        // 设置目标文件  
				       File toFile = new File(uploadPath, getFileName());
				       if(!toFile.exists()){
				    	   toFile.createNewFile();
				       }
				       
				       // 创建一个输出流  
				       OutputStream os = new FileOutputStream(toFile);  
				       //设置缓存  
				       byte[] buffer = new byte[1024];  
				        int length = 0;  
				       //读取myFile文件输出到toFile文件中  
				       while ((length = is.read(buffer)) > 0) {  
				            os.write(buffer, 0, length);  
				        }  
				        //关闭输入流  
				     is.close();  
				      //关闭输出流  
				       os.close(); 
				}
				DocTemplate d = null;
               if(docTemplateId!=null&&docTemplateId>0){
            	     	d = docTemplateService.findOne(docTemplateId);
               }else{
            	   		d = new DocTemplate();
            	   		d.setCreateTime(new Timestamp(new Date().getTime()));
            	   		d.setIsDelete(0);
               }
		       d.setDocTemplateName(name);
		       if(userInfo!=null){
		       d.setCompanyId(userInfo.getCompanyId());
		       }
			   	if(myfile!=null){
			   	   String fileName = getFileNameByScr();
			       String docUrl =getFileName();
			       d.setDocUrl("/redTemplate/"+docUrl);
			       d.setFileName(fileName);
			       d.setDocTemplateName(name);
			   	}
			   d.setUserIds(nextUser);
		       d.setCategoryId(docTemplateType);
		       d.setCompanyId(userInfo.getCompanyId());
		       docTemplateService.saveOrUpdate(d);
			} catch (Exception e) {
				e.printStackTrace();
			}  
		   return SUCCESS;
	}

	/**
	 * 判断是否重复
	 * @return
	 */
	public String isExsit(){
		String res="0";
		try {
			   if(docTemplateId!=null&&docTemplateId>0){
			    	DocTemplate docTemp = docTemplateService.findOne(docTemplateId);
			    	if(docTemp!=null){
			    		if(name!=null&&name.equals(docTemp.getDocTemplateName())){
				    		
				    	}else{
				    		DocTemplate doc = docTemplateService.findByName(name);
				    		if(doc!=null){
				    			res="1";
				    		}
				    	}
			    	}
			    }else{
			    	DocTemplate doc = docTemplateService.findByName(name);
			    	if(doc!=null){
		    			res="1";
		    		}
			    }
			ajax(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 单个删除
	 */
	public String delete(){
		try {
				DocTemplate docT = docTemplateService.findOne(docTemplateId);
				if(docT!=null){
					docT.setIsDelete(1);
					docT.setCompanyId(TransportUser.get().getCompanyId());
					docTemplateService.saveOrUpdate(docT);
					 ajax("1");
				}else{
					ajax("0");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 单个删除
	 */
	public String deleteMore(){
		try {
				Boolean res = docTemplateService.deleteMore(ids);
				if(res){
					 ajax("1");
				}else{
					ajax("0");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取模板内容
	 * @return
	 */
	public  String getDocTemplate(){
		try {
			int companyId =0;
			Object userObject=this.getSession().getAttribute("adminUser");
        	UserInfo userInfo = null;
			if(userObject!=null){
				  userInfo=(UserInfo)userObject;
				  companyId = userInfo.getCompanyId();
			}
			DocTemplate docT = docTemplateService.findOne(docTemplateId);
			if(docT!=null){
			 Gson json = new Gson();
	         String jsons = json.toJson(docT);
		     ajax(jsons);
			}else{
				ajax("0");
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}
	
	/**
	 * 功能：预览套红模板
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String prepView(){
		String filePath = filePathConfig.getFileUploadPath()+"/upload/"+docUrl;
		File doc = new File(filePath);
		OfficeUtils utils = new OfficeUtils();
		String fp = this.getRequest().getRealPath("/");
		String imgPath = getRequest().getContextPath();
		String str = utils.convertToHtml(doc, fp+"/temp",imgPath+"/temp");
		ajax(str);
		return null;
	}
	
	
	private String getFileName() {
	     String fileName = getFileNameByScr();
	      SimpleDateFormat sp = new SimpleDateFormat("yyyyMMddHHmmss");
	      String res  = sp.format(new Date())+""+fileName;
		return res;
	}

	private String getFileType() {
		String res="";
		String fileName = getFileNameByScr();
		String[] arr = fileName.split("\\.");
		if(arr!=null&&arr.length>1){
			res = arr[1];
		}
		return res;
	}

	private String getFileNameByScr() {
		String res = "";
		if(fileName!=null){
				String[] arr = fileName.split("\\\\");
				res = arr[arr.length-1];
		 }		
		return res;
	}

	private String getUploadPath() {
	    String str = filePathConfig.getFileUploadPath()+"/upload/redTemplate";
	    File f = new File(str);
	    if(!f.exists()){
	        f.mkdirs();
	    }
    	return str;
	}

	public File getMyfile() {
		return myfile;
	}

	public void setMyfile(File myfile) {
		this.myfile = myfile;
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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getDocTemplateId() {
		return docTemplateId;
	}

	public void setDocTemplateId(Integer docTemplateId) {
		this.docTemplateId = docTemplateId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNextUser() {
		return nextUser;
	}

	public void setNextUser(String nextUser) {
		this.nextUser = nextUser;
	}

 
	
	
}
