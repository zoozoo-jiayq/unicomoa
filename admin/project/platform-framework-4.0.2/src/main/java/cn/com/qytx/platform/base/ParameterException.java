package cn.com.qytx.platform.base;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 功能: 参数异常 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年5月19日
 * 修改日期: 2015年5月19日
 * 修改列表:
 */
public class ParameterException extends MissingServletRequestParameterException {
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	public ParameterException(String parameterName, String parameterType) {
		super(parameterName, parameterType);
		// TODO Auto-generated constructor stub
	}


}
