package cn.com.qytx.ydzj.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 功能:公共电话本类别
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2014-9-15
 * <br/>修改日期: 2014-9-15
 * <br/>修改列表:
 */
@Entity
@Table(name="PublicPhoneBook")
public class PublicPhoneBook {
	//id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="vid")
	private Integer vid;
	
	@Column(name="categoryid")
	private Integer categoryId;
	
	@Column(name="categoryname",length=200)
	private String categoryName;
	
	@Column(name="inserttime")
	private Timestamp insertTime;
	
	@Column(name="phone",length=50)
	private String phone;
	
	@Column(name="name",length=200)
	private String name;
	
	@Column(name="picture",length=500)
	private String picture;
	@Transient
	private String phoneCount;
	@Transient
	private String pictureUrl;
	

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
	}

	/**
	 * @return the categroyName
	 */
	public String getCategroyName() {
		return categoryName;
	}

	/**
	 * @param categroyName the categroyName to set
	 */
	public void setCategroyName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the insertTime
	 */
	public Timestamp getInsertTime() {
		return insertTime;
	}

	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phoneCount
	 */
	public String getPhoneCount() {
		return phoneCount;
	}

	/**
	 * @param phoneCount the phoneCount to set
	 */
	public void setPhoneCount(String phoneCount) {
		this.phoneCount = phoneCount;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	
	
}