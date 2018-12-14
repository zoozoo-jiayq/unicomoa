package cn.com.qytx.cbb.file.config;

public class FilePathConfig {
	/**
	 * 文件上传路径
	 */
	private String fileUploadPath;
	
	/**
	 * 文件浏览路径
	 */
	private String fileViewPath;
	
	/*
	 * 单位数据超过此致采用文件传输的方式发送数据
	 */
	private Integer bigDataStand;
	
	/**
	 * 本地临时文件目录
	 */
	private String fileLocalPath;
	
	//文件上传路径
	private String uploadServerURL;
	
	
	public FilePathConfig(){
		
	}
	
	public FilePathConfig(String fileUploadPath,String fileViewPath,Integer bigDataStand,String fileLocalPath){
		this.fileUploadPath = fileUploadPath;
		this.fileViewPath = fileViewPath;
		this.bigDataStand = bigDataStand;
		this.fileLocalPath = fileLocalPath;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public String getFileViewPath() {
		return fileViewPath;
	}

	public void setFileViewPath(String fileViewPath) {
		this.fileViewPath = fileViewPath;
	}

	public Integer getBigDataStand() {
		return bigDataStand;
	}

	public void setBigDataStand(Integer bigDataStand) {
		this.bigDataStand = bigDataStand;
	}

	public String getFileLocalPath() {
		return fileLocalPath;
	}

	public void setFileLocalPath(String fileLocalPath) {
		this.fileLocalPath = fileLocalPath;
	}

	public String getUploadServerURL() {
		return uploadServerURL;
	}

	public void setUploadServerURL(String uploadServerURL) {
		this.uploadServerURL = uploadServerURL;
	}
	
}
