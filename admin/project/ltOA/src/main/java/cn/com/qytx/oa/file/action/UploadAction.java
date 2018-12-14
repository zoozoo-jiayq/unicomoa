package cn.com.qytx.oa.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.oa.file.service.IFileContent;
import cn.com.qytx.oa.file.service.IFileSort;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.google.gson.Gson;

/**
 * 上传及导入文件
 * 功能:
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
public class UploadAction extends BaseActionSupport
{
	 /**用户信息*/
	@Autowired
    IAttachment attachmentService;
	/**文件类别impl*/
	@Autowired
    IFileContent fileContentImpl;
	/**文件impl*/
	@Autowired
    IFileSort fileSortImpl;
    /**
     * 文件路径管理
     */
	@Resource(name = "filePathConfig")
	private FilePathConfig filePathConfig;
    
    
    private Attachment attach;//附件实体类
	private static final long serialVersionUID = 1L;
    private Integer attachmentId;
	private File fileupload; // 和JSP中input标记name同名
    
	private String module; //模块名称
	private String filePageId;
	private int sortId;
	private int type;
	
	private String attachIds;
	private String attachNames;
	private String userIds;
	private String userNames;
	

	//private Attachment attachment;// 附件
	// Struts2拦截器获得的文件名,命名规则，File的名字+FileName
    // 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'
    private String fileuploadFileName; // 上传来的文件的名字
  
    /**
     * 上传文件
     * @return
     */
    public String uploadFile()
    {
        try
        {
        	
            // 上传文件
            String uploadPath = ""; //上传的目录
            String catalinaHome = filePathConfig.getFileUploadPath();
            SimpleDateFormat sp = new SimpleDateFormat("MM/dd");
            String datePath =sp.format(new Date()); //每月一个上传目录
            if(module!=null&&!"".equals(module)){
            	uploadPath= "/upload/"+module+"/"+datePath+"/";
            }else{
            	uploadPath=   "/upload/"+datePath+"/";
            }
            String targetDirectory = catalinaHome+uploadPath;
            String uuid = UUID.randomUUID().toString();
            // 新的文件名
            String targetFileName = uuid + "." + getExtension(fileuploadFileName);
            File targetFile = new File(targetDirectory, targetFileName);
            FileUtils.copyFile(fileupload, targetFile);
            // 上传完成，可以进行其他操作了
            PrintWriter writer = new PrintWriter(this.getResponse().getWriter());
            writer.print(module+"/"+datePath+"/"+targetFileName+"/"+sortId);
            writer.flush();
            writer.close();
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
   /**
    * 
    * 功能：上传图片
    */
    public void uploadImage()
    {
        try
        {
            // 上传文件
            ServletContext context = ServletActionContext.getServletContext();
            String targetDirectory = context.getRealPath("/upload/img");
            String uuid = UUID.randomUUID().toString();
            // 新的文件名
            String targetFileName = uuid + "." + getExtension(fileuploadFileName);
            File targetFile = new File(targetDirectory, targetFileName);
            FileUtils.copyFile(fileupload, targetFile);
            // 上传完成，可以进行其他操作了
            PrintWriter writer = new PrintWriter(this.getResponse().getWriter());
            writer.print(targetFileName);
            writer.flush();
            writer.close();
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * 下载文件
     */
    public void downFile()
    {
        // 文件下载时采用文件流输出的方式处理：
    	if(this.getRequest().getParameter("attachmentId")!=null){
    		String attachmentIdStr =this.getRequest().getParameter("attachmentId").toString();
    		attachmentId = Integer.parseInt(attachmentIdStr);
    	} 
        HttpServletResponse response = this.getResponse();
        response.reset();
        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
        try
        {
            // 获取文件保存路径
        	Attachment attachment= attachmentService.getAttachment(attachmentId);
        	 String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = catalinaHome+"/upload/";
            //String filedownload = targetDirectory + File.separator + fileuploadFileName;
            String filedownload = targetDirectory +attachment.getAttachFile();
            String filedisplay = attachment.getAttachName();
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            outp = response.getOutputStream();
            in = new FileInputStream(filedownload);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0)
            {
                outp.write(b, 0, i);
            }
            outp.flush();
        }
        catch (Exception e)
        {
           // System.out.println("Error!");
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    LOGGER.error("downFile error.", e);
                }
                in = null;
            }
        }
    }

    /**
     * 得到文件的扩展名,得不到返回空
     */
    private String getExtension(String fileName)
    {
        if ((fileName != null) && (fileName.length() > 0))
        {
            int i = fileName.lastIndexOf('.');

            if ((i > -1) && (i < fileName.length() - 1))
            {
                return fileName.substring(i + 1);
            }
        }
        return "";
    }
    
    /**
     * 功能：
     * @return  保存附件实体
     */
    public String addAttachment(){
    	  try
          {
    		  UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");
    		  attach.setCompanyId(userInfo.getCompanyId());
    		  attach.setCreateTime(new Timestamp(new Date().getTime()));
    		  attach.setCreateUserId(userInfo.getUserId());
    		  attachmentService.saveOrUpdate(attach);
    		  Map<String, Integer> m = (Map<String, Integer>) this.getSession().getAttribute("attachment");
			  if(m==null){
				  m = new HashMap<String, Integer>();
			  }
    		  m.put(filePageId, attach.getId());
    		  
    		  this.getSession().setAttribute("attachment", m);
    		  Map<String, Object> jsonMap = new HashMap<String, Object>();
    		  jsonMap.put("attachmentId", attach.getId());
    		  jsonMap.put("map", m);
    		  
  	          Gson json = new Gson();
  	          String jsons = json.toJson(jsonMap);
              PrintWriter writer = new PrintWriter(this.getResponse().getWriter());
              writer.print(jsons);
              writer.flush();
              writer.close();
          }
          catch (Exception ex)
          {
              LOGGER.error(ex.getMessage());
          }
          return null;
    }
    /**
     * 功能：
     * @return  保存附件实体
     */
    public void addAttachFileContent(){
        	PrintWriter out = null;
        	 String name="";
             int fileSortId=0;
             String path="";
    	  try
          {
			  out = this.getResponse().getWriter();
			  UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");
//			  attach.setCompyId(1);
//			  attach.setCreateTime(new Timestamp(new Date().getTime()));;
//			  attach.setCreateUserId(userInfo.getUserId());
//			  attachmentService.saveOrUpdate(attach);
    		  FileSort  fileSort=fileSortImpl.findByFileSortId(sortId);
    		  
            
            name=fileSort.getSortName();
            fileSortId=fileSort.getSortId();
            path=fileSort.getPath();
    		FileContent fileContent=new FileContent();
    		fileContent.setFileSort(fileSort);
    		fileContent.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
           	fileContent.setCreateUserId(userInfo.getUserId());
           	fileContent.setCreateUser(userInfo.getUserName());
           	fileContent.setIsDelete(0);    
           	fileContent.setType(type);
           	fileContent.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
           	fileContent.setLastUpdateUserId(userInfo.getUserId());
           	fileContent.setCompanyId(userInfo.getCompanyId());
           	fileContent.setSubject(attach.getAttachName());
           	fileContent.setAttachment(","+attach.getId()+",");
    		fileContentImpl.saveOrUpdate(fileContent);
    		
    		Map<String, Object> jsonMap = new HashMap<String, Object>();
	        jsonMap.put("name", name);
	        jsonMap.put("fileSortId", fileSortId);
	        jsonMap.put("path",path);
	        Gson json = new Gson();
	        String jsons = json.toJson(jsonMap);
	        out.print(jsons);
          }
          catch (Exception ex)
          {
              LOGGER.error(ex.getMessage());
          }
          finally
          {
        	  if(out!=null){
  			    out.close();
  			}
          }
    }
    
    /**
     * 功能：
     * @return  保存附件实体
     */
    public void shareFile(){
    	PrintWriter out = null;
    	  try
          {
			  out = this.getResponse().getWriter();
			  UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");
    		  FileSort  fileSort=fileSortImpl.findByFileSortId(sortId);
            
            if(StringUtils.isNotEmpty(attachIds) && StringUtils.isNotEmpty(attachNames)){
            	String[] idArr = attachIds.split(",");
            	String[] nameArr = attachNames.split(",");
            	
            	if(userIds.indexOf(userInfo.getUserId()+",")!=0 && userIds.indexOf(","+userInfo.getUserId()+",")<0){
            		userIds+=userInfo.getUserId()+",";
            		userNames+=userInfo.getUserName()+",";
            	}
            	
            	List<FileContent> list = new ArrayList<FileContent>();
            	for(int i=0;i<idArr.length;i++){
            		if(StringUtils.isNotEmpty(idArr[i])){
            			FileContent fileContent=new FileContent();
            			fileContent.setFileSort(fileSort);
            			fileContent.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            			fileContent.setCreateUserId(userInfo.getUserId());
            			fileContent.setCreateUser(userInfo.getUserName());
            			fileContent.setIsDelete(0);    
            			fileContent.setType(type);
            			fileContent.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            			fileContent.setLastUpdateUserId(userInfo.getUserId());
            			fileContent.setCompanyId(userInfo.getCompanyId());
            			fileContent.setSubject(nameArr[i]);
            			fileContent.setAttachment(","+idArr[i]+",");
            			fileContent.setUserIds(userIds);
            			fileContent.setUserNames(userNames);
            			list.add(fileContent);
            		}
            	}
            	if(list.size()>0){
            		fileContentImpl.saveOrUpdate(list);
            	}
            }
    		
	        out.print("1");
          }
          catch (Exception ex)
          {
        	  ex.printStackTrace();
              LOGGER.error(ex.getMessage());
              out.print("0");
          }
          finally
          {
        	  if(out!=null){
  			    out.close();
  			}
          }
    }
    
    /**
     * 删除附件
     * 功能：
     * @return
     */
    public String delAttachment(){
  	  try
      {
		  Map<String, Integer> m = (Map<String, Integer>) this.getSession().getAttribute("attachment");
		  if(m==null){
			  m = new HashMap<String, Integer>();
		  }
		  Integer i  = m.get(filePageId);// filePageId:  页面用于删除的ID
		  if(i==null){
			  i=0;
		  }
          PrintWriter writer = new PrintWriter(this.getResponse().getWriter());
          writer.print(i);
          writer.flush();
          writer.close();
      }
      catch (Exception ex)
      {
          LOGGER.error(ex.getMessage());
      }
      return null;
    }
    
    /**
     * 功能： 清除附件session
     * @return
     */
    public String removeAttachmentSession(){
    	  try
          {
    		  this.getSession().removeAttribute("attachment");
              PrintWriter writer = new PrintWriter(this.getResponse().getWriter());
              writer.print(0);
              writer.flush();
              writer.close();
          }
          catch (Exception ex)
          {
              LOGGER.error(ex.getMessage());
          }
          return null;
    }
    public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
    
	  public File getFileupload()
	    {
	        return fileupload;
	    }

	    public void setFileupload(File fileupload)
	    {
	        this.fileupload = fileupload;
	    }

	    public String getFileuploadFileName()
	    {
	        return fileuploadFileName;
	    }

	    public void setFileuploadFileName(String fileuploadFileName)
	    {
	        this.fileuploadFileName = fileuploadFileName;
	    }

		public Attachment getAttach() {
			return attach;
		}

		public void setAttach(Attachment attach) {
			this.attach = attach;
		}

		public Integer getAttachmentId() {
			return attachmentId;
		}

		public void setAttachmentId(Integer attachmentId) {
			this.attachmentId = attachmentId;
		}

		public String getFilePageId() {
			return filePageId;
		}

		public void setFilePageId(String filePageId) {
			this.filePageId = filePageId;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
		public int getSortId() {
			return sortId;
		}

		public void setSortId(int sortId) {
			this.sortId = sortId;
		}

		public String getAttachIds() {
			return attachIds;
		}

		public void setAttachIds(String attachIds) {
			this.attachIds = attachIds;
		}

		public String getAttachNames() {
			return attachNames;
		}

		public void setAttachNames(String attachNames) {
			this.attachNames = attachNames;
		}

		public String getUserIds() {
			return userIds;
		}

		public void setUserIds(String userIds) {
			this.userIds = userIds;
		}

		public String getUserNames() {
			return userNames;
		}

		public void setUserNames(String userNames) {
			this.userNames = userNames;
		}
}
