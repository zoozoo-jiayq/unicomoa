package cn.com.qytx.cbb.customJpdl.service.parseJSON;


/**
 * 功能：JSON的表达式对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:39:36 
 * 修改日期：2013-3-22 上午11:39:36 
 * 修改列表：
 */
public class BaseExprObject {

	//表单属性
	private String property;
	//关系符
	private String rela;
	//值
	private String value;
	//关系连接符
	private String connect;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getRela() {
		return rela;
	}
	public void setRela(String rela) {
		this.rela = rela;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	
}
