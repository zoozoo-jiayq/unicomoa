package cn.com.qytx.ydzj.domain;


import java.util.List;

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
@Table(name="publicphonebookcategory")
public class PublicPhoneBookCategory {
	//id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="vid")
	private Integer vid;
	
	@Column(name="categoryname",length=200)
	private String categoryName;
	
	@Transient
	private Integer phoneCount;//类别下的号码个数
	
	@Transient
	private List<PublicPhoneBook> phoneList;//类别下的号码

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
	public void setCategroyName(String categroyName) {
		this.categoryName = categroyName;
	}

	/**
	 * @return the phoneCount
	 */
	public Integer getPhoneCount() {
		return phoneCount;
	}

	/**
	 * @param phoneCount the phoneCount to set
	 */
	public void setPhoneCount(Integer phoneCount) {
		this.phoneCount = phoneCount;
	}

	/**
	 * @return the phoneList
	 */
	public List<PublicPhoneBook> getPhoneList() {
		return phoneList;
	}

	/**
	 * @param phoneList the phoneList to set
	 */
	public void setPhoneList(List<PublicPhoneBook> phoneList) {
		this.phoneList = phoneList;
	}
	
	
}