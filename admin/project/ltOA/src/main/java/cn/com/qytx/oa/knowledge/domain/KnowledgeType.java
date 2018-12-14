package cn.com.qytx.oa.knowledge.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.ServletActionContext;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库分类
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-4
 * 修改日期: 2013-11-4
 * 修改列表:
 */
@Entity
@Table(name="tb_Knowledge_type")
public class KnowledgeType extends BaseDomain implements Serializable
{
    /**
     * 描述含义
     */
    private static final long serialVersionUID = 2858143593313348224L;

    /**
     * 主键  唯一标识
     */
    @Id
   	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vid;
    
    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 上级分类Id 顶级分类时为0
     */
    @Column(name = "parentId")
    private Integer parentId;
    
    /**
     * 层级关系,从顶级到本层的路径
     */
    @Column(name = "path")
    private String path;
       
    /**
     * 创建人
     */
    @JoinColumn(name = "create_user_id")
  	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private UserInfo createUserInfo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;
    
    /**
     * 是否删除，0：未删除，1：已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete = 0;

    /**
     * 分类排序号
     */
    @Column(name = "orderIndex")
    private Integer orderIndex;
    
    /**
     * 权限控制
     */
    @Column(name="is_fork_group")
    private Integer isForkGroup;
    /**
     * 是否是个人知识库  0不是   >0 代表是某个人的知识库
     */
    @Column(name="is_private")
    private Integer isPrivate;
    /**
     * 模块id
     */
    @Column(name="column_id")
    private Integer columnId;
    public Integer getVid()
    {
        return vid;
    }

    public void setVid(Integer vid)
    {
        this.vid = vid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public UserInfo getCreateUserInfo()
    {
        return createUserInfo;
    }

    public void setCreateUserInfo(UserInfo createUserInfo)
    {
        this.createUserInfo = createUserInfo;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public Integer getOrderIndex()
    {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex)
    {
        this.orderIndex = orderIndex;
    }

	public Integer getIsForkGroup() {
		return isForkGroup;
	}

	public void setIsForkGroup(Integer isForkGroup) {
		this.isForkGroup = isForkGroup;
	}

	/**
	 * @return the isPrivate
	 */
	public Integer getIsPrivate() {
		return isPrivate;
	}

	/**
	 * @param isPrivate the isPrivate to set
	 */
	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}

    /**
     * 功能：
     * @return
     */
    @Override
    public String toString()
    {
        UserInfo  user=(UserInfo) ServletActionContext.getRequest().getSession().getAttribute("adminUser");
        return "操作人="+user.getUserName()+"KnowledgeType [vid=" + vid + ", name=" + name + ", parentId=" + parentId + ", path=" + path
                + ", createUserInfo=" + createUserInfo + ", createTime=" + createTime + ", isDelete=" + isDelete
                + ", orderIndex=" + orderIndex + ", isForkGroup=" + isForkGroup + ", isPrivate=" + isPrivate + "]";
    }

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	
	
}
