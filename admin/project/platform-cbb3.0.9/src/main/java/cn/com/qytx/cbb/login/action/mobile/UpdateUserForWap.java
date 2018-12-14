package cn.com.qytx.cbb.login.action.mobile;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

public class UpdateUserForWap extends BaseActionSupport{
	@Autowired
	IUser userService;
	@Autowired
    IAttachment attachmentService;
    @Resource(name="filePathConfig")
    private FilePathConfig filePathConfig;
	
	private int userId = -1;
	private String sign;
	private File image;
	private String imageFileName;
	private String imageContentType;
	
	public void updateSignName(){
		String result = "";
		if(userId==-1||sign==null){
			result = "102||参数不正确";
		}else{
			UserInfo user = userService.findOne(userId);
			if(user==null){
				result = "101||用户不存在";
			}else{
				user.setSignName(sign);
				userService.saveOrUpdate(user);
				result = "100||保存成功";
			}
		}
		ajax(result);
	}
	
	/**
	 * 上传并保存头像
	 * @return
	 * @throws Exception 
	 */
	public void uploadUserPhoto(){
		try{
			String path = this.getRequest().getContextPath();
			String basePath = this.getRequest().getScheme() + "://"
					+ this.getRequest().getServerName() + ":"
					+ this.getRequest().getServerPort() + path + "/";	
			
			UserInfo currUser = userService.findOne(userId);
			if(currUser != null){
				// 上传文件
				String uploadPath = ""; // 上传的目录
				String catalinaHome = filePathConfig.getFileUploadPath();
				SimpleDateFormat sp = new SimpleDateFormat("MM/dd");
				String datePath = sp.format(new Date()); // 每月一个上传目录
				uploadPath = "/upload/user/" + datePath + "/";//上传一个用户文件夹 
				
				String targetDirectory = catalinaHome + uploadPath;
				String uuid = UUID.randomUUID().toString();
				// 新的文件名
				if (imageFileName!=null){
					imageFileName = imageFileName.replace("%", "_");
					imageFileName = URLDecoder.decode(imageFileName, "UTF-8");
				}
				String targetFileName = uuid + "." + getExtension(imageFileName);
				File targetFile = new File(targetDirectory, targetFileName);
				FileUtils.copyFile(image, targetFile);
				Long attachSize = targetFile.length();
				Attachment attachment = new Attachment();
				attachment.setAttachFile(uploadPath.substring(8)+targetFileName);
				attachment.setAttachName(imageFileName);
				attachment.setCompanyId(currUser.getCompanyId());
				attachment.setCreateUserId(currUser.getUserId());
				attachment.setIsDelete(0);
				attachment.setAttachSize(attachSize);
				Attachment userPhoto = attachmentService.saveOrUpdate(attachment);
				// 上传完成，可以进行其他操作了
				currUser.setPhoto(userPhoto.getAttachFile());
				currUser.setLastUpdateTime(new Date());
				currUser = userService.saveOrUpdate(currUser);
				
				List<UserInfo> virtualUsers = userService.findVirtualUsers(currUser.getUserId(), currUser.getCompanyId());
				if(virtualUsers!=null && virtualUsers.size()>0){
					for(UserInfo virtualUser:virtualUsers){
						virtualUser.setLastUpdateTime(new Date());
						virtualUser.setPhoto(userPhoto.getAttachFile());
						userService.saveOrUpdate(virtualUser);
					}
				}
				
				Map<String,String> userMap=new HashMap<String,String>();
				userMap.put("id", userId+"");
				userMap.put("attachFile", uploadPath.substring(8) + targetFileName);
				userMap.put("createUserId", userId+"");
				userMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				userMap.put("isDelete", "0");
				userMap.put("compyId", currUser.getCompanyId()+"");
				userMap.put("attachName", imageFileName);
				userMap.put("attachSize", attachSize+"");
				Gson gson=new Gson();
				ajax("100||"+gson.toJson(userMap));
			}else{
				ajax("102||用户不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			ajax("101||服务器异常");
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
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	
	
}
