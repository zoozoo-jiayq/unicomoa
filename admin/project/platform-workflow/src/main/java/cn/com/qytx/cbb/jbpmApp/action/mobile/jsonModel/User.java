package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;


public class User{
	private String id;
	private String name;
	private String photo;
	
	public User(String id, String name,String photo) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}