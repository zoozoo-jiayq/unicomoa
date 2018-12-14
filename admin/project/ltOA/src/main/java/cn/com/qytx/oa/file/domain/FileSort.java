package cn.com.qytx.oa.file.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

/**
 * 
 * 功能:文件夹的类
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Entity
@Table(name="tb_on_file_sort")
public class FileSort extends BaseDomain{
	
	/**关联字段(one-to-many)*/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="fileSort")
	private Set<FileContent> fileSorts;
	/**ID 自增长  */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="sort_id")
	private int sortId;
	
	/**父节点的id*/
	@Column(name="parent_id")
	private int parentId;
	
	/**排序号  */
	@Column(name="sort_no")
	private String  sortNo;
	
	/**文件分类名称  */
	@Column(name="sort_name")
	private String  sortName;
	
	/**文件类别 */
	//update by jiayq,0代表知识库，1代表空中课堂
	@Column(name="sort_type")
	private String  sortType;
	
	/**创建时间 */
	@Column(name="create_time")
	private Date createTime;
	
	/**创建人 */
	@Column(name="create_user_id")
	private int createUserId;
	
	/**0：表示未删除,1：表示删除 */
	@DeleteState
	@Column(name="is_delete")
	private Integer  isDelete;//
	
	/**最后修改时间 */
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	
	/**最后修改人*/
	@Column(name="last_update_user_id")
	private int lastUpdateUserId;//
	
	/**版本号*/
	@Column(name="version")
	private int version;//
	
	/**创建人 */
	@Column(name="create_user")
	private String createUser;//
	
	/**父节点的 id*/
	@Column(name="path")
	private String path;//
	
	/**
     * 类型 1全部文件 2共享文件
     */
    @Column(name="type")
    private Integer type;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public int getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	public void setLastUpdateUserId(int lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public Set<FileContent> getFileSorts() {
		return fileSorts;
	}
	public void setFileSorts(Set<FileContent> fileSorts) {
		this.fileSorts = fileSorts;
	}
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
}
