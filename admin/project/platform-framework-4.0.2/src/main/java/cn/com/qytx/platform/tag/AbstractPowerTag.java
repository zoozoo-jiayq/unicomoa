package cn.com.qytx.platform.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 功能:tag模板类 开发人员: zhangjingjing 创建日期: 2015年1月31日
 */
public abstract class AbstractPowerTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	public int doStartTag() {
	try {
		JspWriter out = super.pageContext.getOut();
		Object value=null;
		if (hasPower()) {
			value=withPower();
		} else {
			value=withNotPower();
		}
		out.print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public abstract boolean hasPower();

	public abstract String withPower();

	public abstract String withNotPower();
}
