package cn.com.qytx.cbb.customJpdl.action;

import java.util.ArrayList;
import java.util.List;

import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;

/**
 * 功能：流程分类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-26 下午5:57:40 
 * 修改日期：2013-3-26 下午5:57:40 
 * 修改列表：
 */
public class ProcessCategory {
//	private List<ProcessAttribute> attributes;
	private FormCategory formCategory;
	private int count = 0 ;
	/**
	 * @param attributes
	 */
	public ProcessCategory(List<ProcessAttribute> attributes,FormCategory formCategory) {
		super();
//		this.attributes = attributes;
		this.formCategory = formCategory;
		if(attributes!=null){
			for(int i=0;i<attributes.size();i++){
				ProcessAttribute pa = attributes.get(i);
				if(pa.getProcessName()==null || pa.getProcessName().equals("")){
					continue;
				}
				if(pa.getCategoryId()!=null && (pa.getCategoryId().intValue() == formCategory.getCategoryId().intValue())){
					this.processAttributes.add(pa);
				}
			}
		}
	}

	private List<ProcessAttribute> processAttributes  = new ArrayList<ProcessAttribute>();

	public List<ProcessAttribute> getProcessAttributes() {
		return processAttributes;
	}

	public void setProcessAttributes(List<ProcessAttribute> processAttributes) {
		this.processAttributes = processAttributes;
	}

	public FormCategory getFormCategory() {
		return formCategory;
	}

	public void setFormCategory(FormCategory formCategory) {
		this.formCategory = formCategory;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
