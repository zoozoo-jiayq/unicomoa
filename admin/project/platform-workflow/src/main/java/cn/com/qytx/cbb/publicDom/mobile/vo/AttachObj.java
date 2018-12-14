package cn.com.qytx.cbb.publicDom.mobile.vo;

/**
 * 功能：手机端使用的附件对象
 * 作者： jiayongqiang
 * 创建时间：2014年7月3日
 */
public	class AttachObj{
		private  String name;
		private String size;
		private int id;
		private String fileSize;
		private String attachmentName;
		private String suffix;
		private String url;
		private String viewUrl;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public String getFileSize() {
			return fileSize;
		}
		public void setFileSize(String fileSize) {
			this.fileSize = fileSize;
		}
		public String getAttachmentName() {
			return attachmentName;
		}
		public void setAttachmentName(String attachmentName) {
			this.attachmentName = attachmentName;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getViewUrl() {
			return viewUrl;
		}
		public void setViewUrl(String viewUrl) {
			this.viewUrl = viewUrl;
		}
		
	}
