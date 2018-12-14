package cn.com.qytx.cbb.file.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;

public class UploadAction extends BaseActionSupport
{
    /**
     * 用户信息
     */
	@Autowired
    IAttachment attachmentService;
    @Resource(name="filePathConfig")
    private FilePathConfig filePathConfig;
    
    private Attachment attach;// 附件实体类
    private static final long serialVersionUID = 1L;
    private Integer attachmentId;
    private File fileupload; // 和JSP中input标记name同名
    
    private String module; // 模块名称
    private String filePageId;
    // private Attachment attachment;// 附件
    // Struts2拦截器获得的文件名,命名规则，File的名字+FileName
    // 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'
    private String fileuploadFileName; // 上传来的文件的名字

    private String filePath;// 文件路径--适用于未存数据库的附件
    private String fileName;// 下载时自己把文件名传上来（若为空着取filePath中的名字）--适用于未存数据库的附件
    private String filedisplayName; // 下载时显示的名称
    /**
     * 文件批量压缩下载时删除的JSON串
     * 格式为：[{path:0324/xxxxxx.jpg,name:photo.jpg},{path:0324/xxxxxx.jpg,name:
     * photo.jpg}]
     */
    private String filesJson;
    private String attIds;
    
    /**
     * zip名字
     */
    private String zipName;
    /**
     * 上传文件
     * @return
     */
    public String uploadFile() throws Exception
    {
    		
            // 上传文件
            String uploadPath = ""; // 上传的目录
            String catalinaHome = filePathConfig.getFileUploadPath();
            SimpleDateFormat sp = new SimpleDateFormat("MM/dd");
            String datePath = sp.format(new Date()); // 每月一个上传目录
            if (module != null && !"".equals(module))
            {
                uploadPath = "/upload/" + module + "/" + datePath + "/";
            }
            else
            {
                uploadPath = "/upload/" + datePath + "/";
            }
            String targetDirectory = catalinaHome + uploadPath;
            String uuid = UUID.randomUUID().toString();
            // 新的文件名
            if (fileuploadFileName!=null){
            	fileuploadFileName = fileuploadFileName.replace("%", "_");
            	fileuploadFileName = URLDecoder.decode(fileuploadFileName, "UTF-8");
            }
            
            //add by jiayq 限制上传文件名字
            if(fileuploadFileName.length()>30){
            	ajax("filenametoolong");
            	return null;
            }
            
            
            String targetFileName = uuid + "." + getExtension(fileuploadFileName);
            File targetFile = new File(targetDirectory, targetFileName);
            FileUtils.copyFile(fileupload, targetFile);
            Long attachSize = targetFile.length();
            Attachment attachment = new Attachment();
            attachment.setAttachFile(uploadPath.substring(8)+targetFileName);
            attachment.setAttachName(fileuploadFileName);
            attachment.setCreateUserId(getLoginUser().getUserId());
            attachment.setCompanyId(getLoginUser().getCompanyId());
            attachment.setIsDelete(0);
            attachment.setAttachSize(attachSize);
            attachmentService.saveOrUpdate(attachment);
            // 上传完成，可以进行其他操作了
            ajax(attachment);
        return null;
    }

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
            LOGGER.error("uploadImage error", ex);
        }
    }

    /**
     * 根据FilePath下载文件，处理未存数据库的附件
     */
    public String downFileByFilePath()
    {
        HttpServletResponse response = this.getResponse();
        response.reset();
        // 文件下载时采用文件流输出的方式处理：
        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
        try
        {
            if (StringUtils.isEmpty(this.filePath))
            {
                response.getWriter().print("filePath为空，无法下载");
                response.getWriter().flush();
                return null;
            }
            // 获取文件保存路径
            String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = catalinaHome + "/upload/";
            String fileRealPath = targetDirectory + this.filePath;
            String fileDisplayName = UUID.randomUUID().toString()
                    + this.filePath.substring(this.filePath.lastIndexOf("."));
            if (StringUtils.isNotEmpty(this.fileName))
            {
                fileDisplayName = this.fileName;
            }
            fileDisplayName = URLEncoder.encode(fileDisplayName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileDisplayName);
            File tempFile = new File(fileRealPath);
            response.addHeader("Content-Length", ""+tempFile.length());
            outp = response.getOutputStream();
            in = new FileInputStream(fileRealPath);
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
        return null;
    }

    /**
     * 根据filesJson下载所有文件的zip压缩文件，处理未存数据库的附件
     */
    public String downZipFileByFilesJson()
    {
        HttpServletResponse response = this.getResponse();
        response.reset();
        // 文件下载时采用文件流输出的方式处理：
        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        try
        {
            if (StringUtils.isEmpty(this.filesJson))
            {
                response.getWriter().print("filesJson为空，无法下载");
                response.getWriter().flush();
                return null;
            }
            Gson gson = new Gson();
            List jsonList = gson.fromJson(this.filesJson, List.class);
            if (jsonList.size() == 0)
            {
                response.getWriter().print("没有文件可下载");
                response.getWriter().flush();
                return null;
            }
            // 获取文件保存路径
            String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = catalinaHome + "/upload/";

            LinkedHashMap<String, byte[]> filesMap = new LinkedHashMap<String, byte[]>();
            for (Object obj : jsonList)
            {
                if (obj != null && obj instanceof StringMap)
                {
                    StringMap fileMap = (StringMap) obj;
                    String filePath = (String) fileMap.get("path");
                    String fileName = (String) fileMap.get("name");
                    String fileRealPath = targetDirectory + filePath;
                    File file = new File(fileRealPath);
                    if (file.exists())
                    {
                        byte[] bytes = getByte(file);
                        filesMap.put(fileName, bytes);
                    }
                }
            }
            String zipFileName = "所有附件.zip";
            zipFileName = URLEncoder.encode(zipFileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + zipFileName);
            response.setContentType("application/octet-stream");
            byte[] inArr = createZip(filesMap);
            outp = response.getOutputStream();
            outp.write(inArr);
            outp.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 下载文件
     */
    public void downFile()
    {
        // 文件下载时采用文件流输出的方式处理：
        if (this.getRequest().getParameter("attachmentId") != null)
        {
            String attachmentIdStr = this.getRequest().getParameter("attachmentId").toString();
            attachmentId = Integer.parseInt(attachmentIdStr);
        }
        HttpServletResponse response = this.getResponse();
        HttpServletRequest request = this.getRequest();
        response.reset();
        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
        try
        {
            // 获取文件保存路径
            Attachment attachment = attachmentService.getAttachment(attachmentId);
            String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = catalinaHome + "/upload/";
            // String filedownload = targetDirectory + File.separator +
            // fileuploadFileName;
            String filedownload = targetDirectory + attachment.getAttachFile();
            String filedisplay = attachment.getAttachName();
            String agent = (String)this.getRequest().getHeader("USER-AGENT");     
            if(agent != null && agent.indexOf("Firefox") >= 0) {// FF 火狐   
            	filedisplay = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filedisplay.getBytes("UTF-8")))) + "?=";
            }else{
	            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
          
            outp = response.getOutputStream();
            File tempFile = new File(filedownload);
            in = new FileInputStream(filedownload);
    		long p = 0;
    		long l = tempFile.length();
    		if (request.getHeader("Range") != null) //客户端请求的下载的文件块的开始字节   
    		{   
    			//如果是下载文件的范围而不是全部,向客户端声明支持并开始文件块下载   
    			//要设置状态   
    			//响应的格式是:   
    			//HTTP/1.1 206 Partial Content   
    			response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);//206   
    			
    			//从请求中得到开始的字节   
    			//请求的格式是:   
    			//Range: bytes=[文件块的开始字节]-   
    			p = Long.parseLong(request.getHeader("Range").replaceAll("bytes=","").replaceAll("-",""));   
    		} 
    		if (p != 0)   
	  		  {   
	  		   //不是从最开始下载,   
	  		   //响应的格式是:   
	  		   //Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]   
	  		   response.setHeader("Content-Range","bytes " + new Long(p).toString() + "-" + new Long(l -1).toString() + "/" + new Long(l).toString());   
	  		  }
            
            
    		in.skip(p);
            response.addHeader("Content-Length", new Long(l-p).toString());
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
     * 功能：预览
     * @param
     * @return
     * @throws   
     */
    public String prevView(){
    	  // 文件下载时采用文件流输出的方式处理：
        if (this.getRequest().getParameter("attachmentId") != null)
        {
            String attachmentIdStr = this.getRequest().getParameter("attachmentId").toString();
            attachmentId = Integer.parseInt(attachmentIdStr);
        }
        HttpServletResponse response = this.getResponse();
        response.reset();
        try
        {
            // 获取文件保存路径
            Attachment attachment = attachmentService.getAttachment(attachmentId);
            String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = catalinaHome + "/upload/";
            String filedownload = targetDirectory + attachment.getAttachFile();
            FileInputStream fis = new FileInputStream(new File(filedownload));
			OutputStream out = response.getOutputStream();
			byte[] bs = new byte[1024];
			while ((fis.read(bs) != -1)) {
				out.write(bs);
			}
			out.flush();
			out.close();
			out = null;
			fis.close();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
    	return null;
    }
    
    /**
     * @throws IOException 
     * @throws FileNotFoundException 
     * 功能：预览
     * @param
     * @return
     * @throws   
     */
    public String prevViewByPath() throws IOException{
        String catalinaHome = filePathConfig.getFileUploadPath();
        String targetDirectory = catalinaHome + "/upload/";
        String filedownload = targetDirectory + filePath;
        File f = new File(filedownload);
        if(!f.exists()){
        	return null;
        }
        FileInputStream fis = new FileInputStream(f);
		OutputStream out = getResponse().getOutputStream();
		byte[] bs = new byte[1024];
		while ((fis.read(bs) != -1)) {
			out.write(bs);
		}
		out.flush();
		out.close();
		out = null;
		fis.close();
    	return null;
    }

    /**
     * 下载文件
     */
    public void downZipFile()
    {
        // 文件下载时采用文件流输出的方式处理：
        ByteArrayOutputStream baos;
        String attachmentIdStrList = "";
        if (this.getRequest().getParameter("attachmentIds") != null)
        {
            attachmentIdStrList = this.getRequest().getParameter("attachmentIds").toString();
        }
        HttpServletResponse response = this.getResponse();
        response.reset();
        response.setContentType("application/x-download");
        Map mapFile = new HashMap();
        java.io.OutputStream outp = null;
        try
        {
            // 获取文件保存路径
            String[] arr = attachmentIdStrList.split(",");
            for (String idStr : arr)
            {
                if (idStr != null)
                {
                    attachmentId = Integer.parseInt(idStr);
                }
                Attachment attachment = attachmentService.getAttachment(attachmentId);
                String catalinaHome = filePathConfig.getFileUploadPath();
                String targetDirectory = catalinaHome + "/upload/";
                String filedownload = targetDirectory + attachment.getAttachFile();
                File f = new File(filedownload);
                if (f != null && f.exists())
                {
                    byte[] b = getByte(f);
                    String attachName = attachment.getAttachName();
                    // attachName= URLEncoder.encode(attachName, "UTF-8");
                    mapFile.put(attachName, b);
                }

            }
            
            String filedisplay = "压缩文件.zip";
            if(zipName!=null&&!"".equals(zipName)){
            	filedisplay = zipName+".zip";
            }
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            response.setContentType("application/octet-stream");
            byte[] inArr = createZip(mapFile);
            outp = response.getOutputStream();
            int i = 0;
            outp.write(inArr);
            outp.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

        }
    }

    /**
     * 把一个文件转化为字节
     * @param file
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if (file != null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
//            if (length > Integer.MAX_VALUE)   // 当文件的长度超过了int的最大值
//            {
//                System.out.println("this file is max ");
//                return null;
//            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
                offset += numRead;
            }
            // 如果得到的字节长度和file实际的长度不一致就可能出错了
            if (offset < bytes.length)
            {
                is.close();
                return null;
            }
            is.close();
        }
        return bytes;
    }

    /**
     * 功能：把多个文件放入到输出流里面
     * @param files
     * @return
     * @throws IOException
     */
    private byte[] createZip(Map files) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipfile = new ZipOutputStream(bos);
        
        Set<Map.Entry> entrySet = files.entrySet();
        Iterator<Map.Entry> ite= entrySet.iterator();
        String fileName = null;
        ZipEntry zipentry = null;
        while(ite.hasNext())
        {
            Map.Entry tempEntry = ite.next();
            fileName = (String) tempEntry.getKey();
            zipentry = new ZipEntry(fileName);
            zipfile.putNextEntry(zipentry);
            zipfile.setEncoding("utf-8");
            zipfile.write((byte[]) tempEntry.getValue());
        }
        zipfile.close();
        return bos.toByteArray();
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
     * @return 保存附件实体
     */
    public String addAttachment()
    {
        try
        {
            UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");
            attach.setCompanyId(userInfo.getCompanyId());
            attach.setCreateUserId(userInfo.getUserId());
            attachmentService.saveOrUpdate(attach);
            Map<String, Integer> m = (Map<String, Integer>) this.getSession().getAttribute("attachment");
            if (m == null)
            {
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
     * 删除附件
     * 功能：
     * @return
     */
    public String delAttachment()
    {
        try
        {
            Map<String, Integer> m = (Map<String, Integer>) this.getSession().getAttribute(
                    "attachment");
            if (m == null)
            {
                m = new HashMap<String, Integer>();
            }
            Integer i = m.get(filePageId);// filePageId: 页面用于删除的ID
            if (i == null)
            {
                i = 0;
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
    public String removeAttachmentSession()
    {
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

    /**
     * 下载文件
     */
    public void downErrorFile()
    {
        // 文件下载时采用文件流输出的方式处理：
        HttpServletResponse response = this.getResponse();
        response.reset();
        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
        try
        {
            // 获取文件保存路径
            String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = catalinaHome + "/upload";
            String filedownload = targetDirectory + File.separator + fileuploadFileName;
            String filedisplay = "错误数据.xls";
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

    public void downProjectFile()
    {
        // 文件下载时采用文件流输出的方式处理：
        HttpServletResponse response = this.getResponse();
        response.reset();
        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
        try
        {
            // 获取文件保存路径
            ServletContext context = ServletActionContext.getServletContext();
            String targetDirectory = context.getRealPath(this.filePath);
            String filedownload = targetDirectory + File.separator
                    + this.fileName;
            filedisplayName = URLEncoder.encode(filedisplayName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplayName);
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

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
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

    public Attachment getAttach()
    {
        return attach;
    }

    public void setAttach(Attachment attach)
    {
        this.attach = attach;
    }

    public Integer getAttachmentId()
    {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId)
    {
        this.attachmentId = attachmentId;
    }

    public String getFilePageId()
    {
        return filePageId;
    }

    public void setFilePageId(String filePageId)
    {
        this.filePageId = filePageId;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getAttIds()
    {
        return attIds;
    }

    public void setAttIds(String attIds)
    {
        this.attIds = attIds;
    }

    public String getFilesJson()
    {
        return filesJson;
    }

    public void setFilesJson(String filesJson)
    {
        this.filesJson = filesJson;
    }

    public String getFiledisplayName()
    {
        return filedisplayName;
    }

    public void setFiledisplayName(String filedisplayName)
    {
        this.filedisplayName = filedisplayName;
    }

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}
    
}