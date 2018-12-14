package cn.com.qytx.cbb.publicDom.vo;

import java.io.Serializable;

/**
 * 功能：公文基础属性对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:07:09 
 * 修改日期：上午11:07:09 
 * 修改列表：
 */
public class PublicDomVo implements Serializable{

	private int domTypeId;
	private String domTypeName;
	private String secretLevel;
	private String wenhao;
	private String huanji;
	private String title;
	private String domName;
	private String firstLevel;
	
	public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    //来文单位，只有收文的时候才有的属性
	private String sourceDept;

	public int getDomTypeId() {
		return domTypeId;
	}

	public void setDomTypeId(int domTypeId) {
		this.domTypeId = domTypeId;
	}

	public String getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(String secretLevel) {
		this.secretLevel = secretLevel;
	}

	public String getWenhao() {
		return wenhao;
	}

	public void setWenhao(String wenhao) {
		this.wenhao = wenhao;
	}

	public String getHuanji() {
		return huanji;
	}

	public void setHuanji(String huanji) {
		this.huanji = huanji;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSourceDept() {
		return sourceDept;
	}

	public void setSourceDept(String sourceDept) {
		this.sourceDept = sourceDept;
	}

	public String getDomTypeName() {
		return domTypeName;
	}

	public void setDomTypeName(String domTypeName) {
		this.domTypeName = domTypeName;
	}

	public String getDomName() {
		return domName;
	}

	public void setDomName(String domName) {
		this.domName = domName;
	}


}
