package cn.com.qytx.cbb.jbpmApp.service;
/**
 * 功能: 附件
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-3-26
 * 修改日期: 2013-3-26
 * 修改列表:
 */
public class JbpmAttach implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
    private Integer  attachId;  //附件ID
    private Integer  createUserId;  //附件上传人ID
    private String  createUserName; //附件上传人姓名
	private String  attachName;// 文件名称
	private String fileName;//文件名称
	private String attachFile;// 文件路径
	
	public String getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
