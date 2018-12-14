package cn.com.qytx.cbb.dict.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

import com.google.gson.annotations.Expose;

/**
 * 通用的信息维表
 * @author Administrator
 */
@Entity
@Table(name="tb_cbb_dict")
public class Dict extends BaseDomain
{   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Integer id;
	@Expose
	@Column(name="name",length=50,nullable=false)
    private String name; // key
	@Expose
	@Column(name="value")
    private Integer value; // 值
	@Expose
	@Column(name="info_type",length=100)
    private String infoType;// 类型
	@Column(name="create_date")
    private Timestamp createDate; // 创建时间
    @Column(name="modify_date")
    private Timestamp modifyDate; // 修改时间
    @Column(name="record_user_id")
    private Integer recordUserId; // 记录人或最后修改人
    @DeleteState
	@Column(name = "is_delete")
    private Integer isDelete; // 是否删除 1：已删除 0：未删除
    
    @Expose
	@Column(name = "info_order")
    private Integer infoOrder; // 是否删除 1：已删除 0：未删除
    
    @Transient
    private Integer canDelete;//是否为系统默认的类型 ：  0：默认    null或1：用户自定义   默认类型不可以删除和编辑
    /**
     * 来源系统
     */
    @Column(name="sys_tag")
    private Integer sysTag;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getInfoType()
    {
        return this.infoType;
    }

    public void setInfoType(String infoType)
    {
        this.infoType = infoType;
    }

    public Integer getSysTag()
    {
        return this.sysTag;
    }

    public void setSysTag(Integer sysTag)
    {
        this.sysTag = sysTag;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public Timestamp getModifyDate()
    {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate)
    {
        this.modifyDate = modifyDate;
    }

    public Integer getRecordUserId()
    {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId)
    {
        this.recordUserId = recordUserId;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

	public Integer getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Integer canDelete) {
		this.canDelete = canDelete;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the infoOrder
	 */
	public Integer getInfoOrder() {
		return infoOrder;
	}

	/**
	 * @param infoOrder the infoOrder to set
	 */
	public void setInfoOrder(Integer infoOrder) {
		this.infoOrder = infoOrder;
	}
    
}