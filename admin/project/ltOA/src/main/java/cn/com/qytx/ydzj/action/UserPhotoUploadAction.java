package cn.com.qytx.ydzj.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

public class UserPhotoUploadAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	protected static final Logger logger = Logger.getLogger(UserPhotoUploadAction.class.getName());

	//用户业务接口
	@Resource(name="userService")
	IUser userService;
	//用户信息
	@Autowired
	IAttachment attachmentService;
	
	@Resource(name = "filePathConfig")
	private FilePathConfig filePathConfig;

	private File fileupload; // 和JSP中input标记name同名
	private String module; // 模块名称
	// private Attachment attachment;// 附件
	// Struts2拦截器获得的文件名,命名规则，File的名字+FileName
	// 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'
	private String fileuploadFileName; // 上传来的文件的名字
	private String filePath;// 文件路径--适用于未存数据库的附件
	private String fileName;// 下载时自己把文件名传上来（若为空着取filePath中的名字）--适用于未存数据库的附件
	private  Integer userId;

	/**
	 * 上传文件
	 * 
	 * @return
	 */
	public String headPictureUpload() throws Exception {
		//返回数据
		Map<String,String> userMap=new HashMap<String,String>();
		String ret = "102||操作失败";
		try {
			if(userId==null){
				ret="101||用户编号不能为空";
				return null;
			}
			if(fileupload==null){
				ret="101||用户头像不能为空";
				return null;
			}
			// 上传文件
			String uploadPath = ""; // 上传的目录
			String catalinaHome = filePathConfig.getFileUploadPath();
			SimpleDateFormat sp = new SimpleDateFormat("MM/dd");
			String datePath = sp.format(new Date()); // 每月一个上传目录
			if (module != null && !"".equals(module)) {
				uploadPath = "/upload/" + module + "/" + datePath + "/";
			} else {
				uploadPath = "/upload/" + datePath + "/";
			}
			String targetDirectory = catalinaHome + uploadPath;
			String uuid = UUID.randomUUID().toString();
			// 新的文件名
			if (fileuploadFileName != null) {
				fileuploadFileName = fileuploadFileName.replace("%", "_");
				fileuploadFileName = URLDecoder.decode(fileuploadFileName, "UTF-8");
			}
	
			// add by jiayq 限制上传文件名字
			if (fileuploadFileName.length() > 100) {
				ajax("filenametoolong");
				return null;
			}
	
			String targetFileName = uuid + "." + getExtension(fileuploadFileName);
			File targetFile = new File(targetDirectory, targetFileName);
			FileUtils.copyFile(fileupload, targetFile);
			Long attachSize = targetFile.length();
	
			UserInfo userInfo=userService.findOne(userId);

			if(userInfo!=null){
				Attachment attachment = new Attachment();
				attachment.setCompanyId(userInfo.getCompanyId());
				attachment.setCreateUserId(userId);
				attachment.setAttachFile(uploadPath.substring(8) + targetFileName);
				attachment.setAttachName(fileuploadFileName);
				attachment.setIsDelete(0);
				attachment.setAttachSize(attachSize);
				attachmentService.saveOrUpdate(attachment);
				// 上传完成，可以进行其他操作了
				// 保存头像
				userInfo.setPhoto(uploadPath.substring(8) + targetFileName);
				userService.saveOrUpdate(userInfo);
				List<UserInfo> listVirtual = userService.getVirtualUsers(userId+"");
				if(listVirtual!=null && listVirtual.size()>0){
					for(UserInfo virtual : listVirtual){
						virtual.setPhoto(userInfo.getPhoto()==null?"":userInfo.getPhoto().toString());
						userService.saveOrUpdate(virtual);
					}
				}
				userMap.put("id", userId+"");
				userMap.put("attachFile", uploadPath.substring(8) + targetFileName);
				userMap.put("createUserId", userId+"");
				userMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				userMap.put("isDelete", "0");
				userMap.put("compyId", userInfo.getCompanyId()+"");
				userMap.put("attachName", fileuploadFileName);
				userMap.put("attachSize", attachSize+"");
				Gson gson=new Gson();
				ret="100||"+gson.toJson(userMap);
			}
		} catch (Exception e) {
			logger.error(e);
			ret = "102||操作失败";
		} finally {
			ajax(ret);
		}
		return null;
	}

	/**
	 * 功能：预览
	 * @throws IOException
	 * @throws FileNotFoundException 
	 * @param
	 * @return
	 * @throws
	 */
	public String headPictureView() throws IOException {
		String catalinaHome = filePathConfig.getFileUploadPath();
		String targetDirectory = catalinaHome + "/upload/";
		String filedownload = targetDirectory + filePath;
		if (org.apache.commons.lang3.StringUtils.isBlank(filePath)) {
			return null;
		}
		File f = new File(filedownload);
		if (!f.exists()) {
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
	 * 得到文件的扩展名,得不到返回空
	 */
	private String getExtension(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int i = fileName.lastIndexOf('.');

			if ((i > -1) && (i < fileName.length() - 1)) {
				return fileName.substring(i + 1);
			}
		}
		return "";
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public File getFileupload() {
		return fileupload;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}

	public String getFileuploadFileName() {
		return fileuploadFileName;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}