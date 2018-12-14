package cn.com.qytx.cbb.notify.vo;

import java.io.Serializable;

public class TbColumnSetting implements Serializable{
	private static final long serialVersionUID = 1L;
	private int isComment; //是否允许评论
	private String isAuditing;  //是否需要审批
	private int isSeeAttach;//是否可以查看附件
	private int isEdit; //审批人是否可以编辑
	private int showImage; //0 不显示 1 显示
	private String publishUserIds;//可直接发布人员ID
	private String publishUserNames;//发布人员名称
	private int isSmipleText;//1纯文本0富文本
	private int isDataFilter=0;//是否添加数据权限
	private Integer clientType=1;//手机端显示风格
	public int getIsComment() {
		return isComment;
	}
	public void setIsComment(int isComment) {
		this.isComment = isComment;
	}
	public String getIsAuditing() {
		return isAuditing;
	}
	public void setIsAuditing(String isAuditing) {
		this.isAuditing = isAuditing;
	}
	public int getIsSeeAttach() {
		return isSeeAttach;
	}
	public void setIsSeeAttach(int isSeeAttach) {
		this.isSeeAttach = isSeeAttach;
	}
	public int getShowImage() {
		return showImage;
	}
	public void setShowImage(int showImage) {
		this.showImage = showImage;
	}
	public String getPublishUserIds() {
		return publishUserIds;
	}
	public void setPublishUserIds(String publishUserIds) {
		this.publishUserIds = publishUserIds;
	}
	public String getPublishUserNames() {
		return publishUserNames;
	}
	public void setPublishUserNames(String publishUserNames) {
		this.publishUserNames = publishUserNames;
	}
	public int getIsSmipleText() {
		return isSmipleText;
	}
	public void setIsSmipleText(int isSmipleText) {
		this.isSmipleText = isSmipleText;
	}
	public int getIsDataFilter() {
		return isDataFilter;
	}
	public void setIsDataFilter(int isDataFilter) {
		this.isDataFilter = isDataFilter;
	}
	public int getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
}
