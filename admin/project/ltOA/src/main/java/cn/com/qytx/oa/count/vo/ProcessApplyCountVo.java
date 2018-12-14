package cn.com.qytx.oa.count.vo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class ProcessApplyCountVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer month;
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


	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	
}
