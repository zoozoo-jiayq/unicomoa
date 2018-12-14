package cn.com.qytx.cbb.customJpdl.service.parseJSON;

/**
 * 功能：JSON的TEXT对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:39:57 
 * 修改日期：2013-3-22 上午11:39:57 
 * 修改列表：
 */
public class BaseTextObject {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "值是:"+text;
	}
}
