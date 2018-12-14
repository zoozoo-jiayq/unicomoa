package cn.com.qytx.oa.count.vo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class ProcessApplyVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Expose
	private String name;
	@Expose
	private Integer value;
	@Expose
	private String color;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	
	
}
