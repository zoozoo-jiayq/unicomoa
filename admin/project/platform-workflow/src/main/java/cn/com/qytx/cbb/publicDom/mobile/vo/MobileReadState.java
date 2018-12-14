package cn.com.qytx.cbb.publicDom.mobile.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：阅读状态对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:46:43 
 * 修改日期：上午10:46:43 
 * 修改列表：
 */
public class MobileReadState {

	private int count =0 ;
	private int readed = 0;
	private int notRead = 0 ;
	private List<Reader> notReadList = new ArrayList<Reader>();
	private List<Reader> readedList = new ArrayList<Reader>();
	
	public void addNotRead(String name,String group,String role){
		this.notReadList.add(new Reader(name,group,role,null));
	}
	
	public void addReadlist(String name,String group,String role,String readTime){
		this.readedList.add(new Reader(name, group, role, readTime));
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getReaded() {
		return readed;
	}
	public void setReaded(int readed) {
		this.readed = readed;
	}
	public int getNotRead() {
		return notRead;
	}
	public void setNotRead(int notRead) {
		this.notRead = notRead;
	}
	public List<Reader> getNotReadList() {
		return notReadList;
	}
	public void setNotReadList(List<Reader> notReadList) {
		this.notReadList = notReadList;
	}
	public List<Reader> getReadedList() {
		return readedList;
	}
	public void setReadedList(List<Reader> readedList) {
		this.readedList = readedList;
	}
	
}

class Reader{
	private String name;
	private String group;
	private String role;
	private String readTime;
	
	
	public Reader() {
	    super();
	    // TODO Auto-generated constructor stub
    }
	public Reader(String name, String group, String role, String readTime) {
	    super();
	    this.name = name;
	    this.group = group;
	    this.role = role;
	    this.readTime = readTime;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	
}