package cn.com.qytx.cbb.publicDom.mobile.vo;

import java.util.ArrayList;
import java.util.List;

import cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement.HtmlElement;


/**
 * 功能：公文处理详情视图
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-5-6 下午2:12:33 
 * 修改日期：2013-5-6 下午2:12:33 
 * 修改列表：
 */
public class MobleViewProcessDetail {

	//公文表单属性
	private List<HtmlElement> form = new ArrayList<HtmlElement>();
	
	//附件列表
	private List<AttachObj> attach = new ArrayList<AttachObj>();
	
	private Integer docAttachId;
	
	//系统内收文或者系统外收文标志(out,inner)
	private String isSystem;
	
	private boolean readed;

	public Integer getDocAttachId() {
        return docAttachId;
    }

    public void setDocAttachId(Integer docAttachId) {
        this.docAttachId = docAttachId;
    }

    public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public List<AttachObj> getAttach() {
		return attach;
	}

	public void setAttach(List<AttachObj> attach) {
		this.attach = attach;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public List<HtmlElement> getForm() {
		return form;
	}

	public void setForm(List<HtmlElement> form) {
		this.form = form;
	}
	
	
}
	
