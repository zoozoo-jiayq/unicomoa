package cn.com.qytx.fileserver.bussiness;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import cn.com.qytx.fileserver.service.FileserverService;
import cn.com.qytx.fileserver.utils.SysUtil;
import cn.com.qytx.fileserver.utils.ZipUtil;

/**
 * 功能: 处理上传逻辑 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年4月23日
 * 修改日期: 2015年4月23日
 * 修改列表:
 */
public class UploadBusssiness {
	/**
	 * 上传文件
	 */
	private List<FileItem> list;
	
	/**
	 * 相对路径
	 */
	private String relatePath;
	
	/**
	 * 目标路径
	 */
	private String targetDirectory;
	
	/**
	 * 上下文
	 */
	private ApplicationContext context;
	
	/**
	 * 单位id
	 */
	private Integer companyId;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	
	/**
	 * 构造函数 
	 * @param item
	 * @param relatePath
	 * @param targetDirectory
	 */
	public UploadBusssiness(List<FileItem> list,String relatePath,String targetDirectory,ApplicationContext context,Integer companyId,Integer userId){
		this.list = list;
		this.relatePath = relatePath;
		this.targetDirectory = targetDirectory;
		this.context = context;
		this.companyId = companyId;
		this.userId = userId;
	}

	/**
	 * 功能：上传操作
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> upload() throws Exception{
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		Iterator<FileItem> itr = list.iterator();
        while (itr.hasNext()) {// 依次处理每个 form field 
               FileItem item = (FileItem) itr.next(); 
               FileserverService fileService = (FileserverService) context.getBean("fileserverService");
               if(!item.isFormField()){ /* 判断是否为表单控件（非File控件），如果不是表单控件，则上传此文件 */ 
               	String fileName = item.getName();//获得文件名称
               	String ext = SysUtil.getExtension(fileName);//获得文件后缀
               	String uuid = UUID.randomUUID().toString();
               	if(ext!=null && !"".equals(ext)){
               		if(!SysUtil.isSaveFile(ext)){//文件不安全，需要压缩后保存
               			String targetFileName = uuid + "." +ext;//存储的临时文件名字
               			File targetFile = new File(targetDirectory, targetFileName);
               			item.write(targetFile);
               			
               			//压缩
               			String zipFileName = uuid+".rar";
               			ZipUtil.fileZip(targetDirectory+"/"+targetFileName,targetDirectory+"/"+zipFileName);
               			
               			File zipFile = new File(targetDirectory+"/"+zipFileName);
               			
               			long size = zipFile.length();
               			//保存上传文件
               			Integer vid = fileService.saveAttachment(relatePath+"/"+zipFileName, fileName, size, companyId, userId);
               			
               			Map<String, Object> map = new HashMap<String, Object>();
               			map.put("name", zipFileName);
               			map.put("size", size);
               			map.put("id", vid);
               			map.put("fileType", SysUtil.getAttacthSuffix(zipFileName));
               			map.put("path", relatePath+"/"+zipFileName);
               			listMap.add(map);
               		}else if(SysUtil.isVideoFile(ext)){//如果是音频文件，则同时需要返回音频文件中的缩略图
               			String targetFileName = uuid + "." +ext;//存储的临时文件名字
               			File targetFile = new File(targetDirectory, targetFileName);
               			item.write(targetFile);
               			
               			//视频截图
               			String cutPic = VideoFileProcesser.getInstanceId().cutPic(targetDirectory+"/"+targetFileName, 500, 500, 5);
               			//时长
               			String duration = VideoFileProcesser.getInstanceId().duration(targetDirectory+"/"+targetFileName);
               			if(StringUtils.isNotBlank(duration)){
							duration = duration.substring(0,duration.lastIndexOf("."));
						}
               			
               			long size = targetFile.length();
               			//保存上传文件
               			Integer vid = fileService.saveAttachment(relatePath+"/"+targetFileName, fileName, size, companyId, userId);
               			
               			Map<String, Object> map = new HashMap<String, Object>();
               			map.put("name", targetFileName);
               			map.put("size", size);
               			map.put("id", vid);
               			map.put("fileType", SysUtil.getAttacthSuffix(targetFileName));
               			map.put("path", relatePath+"/"+targetFileName);
               			map.put("duration", duration);
               			
               			File cutPicFile = new File(cutPic);
               			if(cutPicFile.exists()){//截图成功
               				String cutPicName = UUID.randomUUID().toString()+".jpg";//此处和截图工具类的一致
               				
               				File targetCutPicFile = new File(targetDirectory,cutPicName);
               				FileUtils.copyFile(cutPicFile, targetCutPicFile);//将图片从缓存目录移动到目标目录
               				cutPicFile.delete();//删除缓存目录截图
               				
               				long cutPicSize = targetCutPicFile.length();
               				//保存上传文件
                   			Integer cutPicId = fileService.saveAttachment( relatePath+"/"+cutPicName, cutPicName, cutPicSize, companyId, userId);
               				
               				Map<String, Object> cutPicMap = new HashMap<String, Object>();
               				cutPicMap.put("name", cutPicName);
               				cutPicMap.put("size", cutPicSize);
               				cutPicMap.put("id", cutPicId);
               				cutPicMap.put("fileType", SysUtil.getAttacthSuffix(cutPicName));
               				cutPicMap.put("path", relatePath+"/"+cutPicName);
               				
               				map.put("cutPic", cutPicMap);//将截图返回给对象
               			}
               			
               			listMap.add(map);
               		}else{//正常文件保存
               			String targetFileName = uuid + "." +ext;//存储的临时文件名字
               			File targetFile = new File(targetDirectory, targetFileName);
               			item.write(targetFile);
               			
               			long size = targetFile.length();
               			//保存上传文件
               			Integer vid = fileService.saveAttachment( relatePath+"/"+targetFileName, fileName, size, companyId, userId);
               			
               			Map<String, Object> map = new HashMap<String, Object>();
               			map.put("name", targetFileName);
               			map.put("size", size);
               			map.put("id", vid);
               			map.put("fileType", SysUtil.getAttacthSuffix(targetFileName));
               			map.put("path", relatePath+"/"+targetFileName);
               			listMap.add(map);
               		}
               		
               		
               	}
               }
        }
		return listMap;
	}
	
	
	public List<FileItem> getList() {
		return list;
	}

	public void setList(List<FileItem> list) {
		this.list = list;
	}

	public String getRelatePath() {
		return relatePath;
	}

	public void setRelatePath(String relatePath) {
		this.relatePath = relatePath;
	}

	public String getTargetDirectory() {
		return targetDirectory;
	}

	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
}
