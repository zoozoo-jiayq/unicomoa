package cn.com.qytx.cbb.customForm.domain;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 控件属性（json封装）
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
public class Form extends BaseDomain{

    private String name;//控件名字
    private String value;//控件的值
    private boolean flag = false;//add by Jiayongqiang

    public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
     * 得到控件名字
     * @return 控件名字
     */
    public String getName() {
        return name;
    }
    /**
     * 设置控件名字
     * @param name 控件名字
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 得到控件的值
     * @return 控件的值
     */
	public String getValue() {
		return value;
	}
	/**
	 * 设置控件的值
	 * @param value 控件的值
	 */
	public void setValue(String value) {
		this.value = value;
	}

    
}