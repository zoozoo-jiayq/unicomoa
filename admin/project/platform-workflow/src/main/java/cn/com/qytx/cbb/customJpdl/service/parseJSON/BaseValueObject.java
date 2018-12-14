package cn.com.qytx.cbb.customJpdl.service.parseJSON;


/**
 * 功能：JSON的VALUE对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:40:11 
 * 修改日期：2013-3-22 上午11:40:11 
 * 修改列表：
 */
public class BaseValueObject {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "值是:"+getValue();
	}
}
