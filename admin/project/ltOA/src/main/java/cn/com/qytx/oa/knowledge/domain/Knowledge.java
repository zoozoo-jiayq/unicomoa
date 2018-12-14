package cn.com.qytx.oa.knowledge.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.com.qytx.oa.util.TimeUtil;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-4
 * 修改日期: 2013-11-4
 * 修改列表:
 */

@Entity
@Table(name="tb_Knowledge")
public class Knowledge extends BaseDomain implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vid; // Id

    /**
     * 知识库类别
     */
    @JoinColumn(name = "knowledge_Type_id")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private KnowledgeType knowledgeType;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容
     */
    @Column(name = "contentInfo")
    private String contentInfo;
    @Column(name = "contentVoice")
    private String contentVoice;
    /**
     * 关键字
     */
    @Column(name = "keyword")
    private String keyword;

    /**
     * 创建人
     */
    @JoinColumn(name = "create_user_id")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private UserInfo createUserInfo;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Timestamp createTime;
    

    /**
     * 审核人
     */
    @JoinColumn(name = "audit_user_id")
   	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private UserInfo auditUserInfo;

    /**
     * 审核时间
     */
    @Column(name = "audit_date")
    private Timestamp auditTime;

    /**
     * 审核标记 1待审核 2 审核通过 3 审核不过
     */
    @Column(name = "audit_sign")
    private Integer auditSign; // Id

    /**
     * 是否删除，0：未删除，1：已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete = 0;
    
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
     * 是否合成录音(0表示未合成录音；1表示已合成录音)
     */
    @Column(name="has_record_audio")
    private Integer hasRecordAudio;
    
    /**
     * 合成录音文件的绝对路径
     */
    @Column(name="vox_path")
    private String voxPath;
    
    /**
     * 模块id
     */
    @Column(name="column_id")
    private Integer columnId;
    
    /**
     * 附件ids
     */
    @Column(name="attachment_ids")
    private String attachmentIds;
    
    /**************************************************************************************************************/

    /**
     * 查询条件
     */
    @Transient
    private String search;
    /**
     * 父类型名称组合
     */
    @Transient
    private String parentNameStr;
    /**
     * 完整路径名称
     */
    @Transient
    private String pathName;
    /**
     * 以详情的样式查询知识库
     */
    @Transient
    private String kdDetail;//封装内容
    @Transient  //创建时间字符串
    private String  createTimeStr;
    @Transient  //创建时间字符串
    private String  createUser;
    

	public String getParentNameStr() {
		return parentNameStr;
	}

	public void setParentNameStr(String parentNameStr) {
		this.parentNameStr = parentNameStr;
	}

	public Integer getVid()
    {
        return vid;
    }

    public void setVid(Integer vid)
    {
        this.vid = vid;
    }

    public KnowledgeType getKnowledgeType()
    {
        return knowledgeType;
    }

    public void setKnowledgeType(KnowledgeType knowledgeType)
    {
        this.knowledgeType = knowledgeType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContentInfo()
    {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo)
    {
        this.contentInfo = contentInfo;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
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

    public UserInfo getAuditUserInfo()
    {
        return auditUserInfo;
    }

    public void setAuditUserInfo(UserInfo auditUserInfo)
    {
        this.auditUserInfo = auditUserInfo;
    }

    public Timestamp getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime)
    {
        this.auditTime = auditTime;
    }

    public Integer getAuditSign()
    {
        return auditSign;
    }

    public void setAuditSign(Integer auditSign)
    {
        this.auditSign = auditSign;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    public String getPathName()
    {
        return pathName;
    }

    public void setPathName(String pathName)
    {
        this.pathName = pathName;
    }
	public Integer getIsForkGroup() {
		return isForkGroup;
	}

	public void setIsForkGroup(Integer isForkGroup) {
		this.isForkGroup = isForkGroup;
	}

	public String getKdDetail() {
		return kdDetail;
	}

	public void setKdDetail(String kdDetail) {
		this.kdDetail = kdDetail;
	}

	/**
	 * @return the createTimeStr
	 */
	public String getCreateTimeStr() {
		if(createTimeStr==null||"".equals(createTimeStr) && this.createTime!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			  return sdf.format(this.createTime);
		}
		return createTimeStr;
	}

	/**
	 * @param createTimeStr the createTimeStr to set
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public Integer getHasRecordAudio() {
		return hasRecordAudio;
	}

	public void setHasRecordAudio(Integer hasRecordAudio) {
		this.hasRecordAudio = hasRecordAudio;
	}

	public String getVoxPath() {
		return voxPath;
	}

	public void setVoxPath(String voxPath) {
		this.voxPath = voxPath;
	}

	/**
	 * @return the contentVoice
	 */
	public String getContentVoice() {
		return contentVoice;
	}

	/**
	 * @param contentVoice the contentVoice to set
	 */
	public void setContentVoice(String contentVoice) {
		this.contentVoice = contentVoice;
	}

    /**
     * 功能：把知识库转换为map形式
     * @param index
     * @return
     */
    public Map<String, Object> thisToMap(int i)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNumber", i);
        map.put("vid", vid);
       
        map.put("title", StringUtils.defaultString(title, CallCenterConst.WORKORDER_TABLE_BLANK));
        map.put("keyword", StringUtils.defaultString(keyword, CallCenterConst.WORKORDER_TABLE_BLANK));

        map.put("userName",StringUtils.defaultString(createUserInfo.getUserName(), CallCenterConst.WORKORDER_TABLE_BLANK));

        map.put("createTime", null != createTime ?TimeUtil.dateToStr(createTime, "yyyy-MM-dd HH:mm")
                : CallCenterConst.WORKORDER_TABLE_BLANK);
        map.put("pathName", StringUtils.defaultString(pathName, CallCenterConst.WORKORDER_TABLE_BLANK));
        map.put("auditTime", auditTime!=null?TimeUtil.dateToStr(auditTime, "yyyy-MM-dd"):CallCenterConst.WORKORDER_TABLE_BLANK);

        map.put("orderIndex", null != knowledgeType.getOrderIndex() ? knowledgeType.getOrderIndex()
                : CallCenterConst.WORKORDER_TABLE_BLANK);
         
        map.put("keyword", StringUtils.defaultString(keyword, CallCenterConst.WORKORDER_TABLE_BLANK));
        map.put("managerKnowledge", StringUtils.defaultString("", CallCenterConst.WORKORDER_TABLE_BLANK));

        map.put("contentInfo", StringUtils.defaultString(contentInfo, CallCenterConst.WORKORDER_TABLE_BLANK));
        return map;
    }
	
    /**
     * 功能：对象转换为String
     * @return
     */
    @Override
    public String toString()
    {
        UserInfo  user=(UserInfo) ServletActionContext.getRequest().getSession().getAttribute("adminUser");
        return "操作人="+user.getUserName()+"Knowledge [vid=" + vid + ", knowledgeType=" + knowledgeType + ", title=" + title + ", contentInfo="
                + contentInfo + ", contentVoice=" + contentVoice + ", keyword=" + keyword + ", createUserInfo="
                + createUserInfo + ", createTime=" + createTime + ", auditUserInfo=" + auditUserInfo + ", auditTime="
                + auditTime + ", auditSign=" + auditSign + ", isDelete=" + isDelete + ", isForkGroup=" + isForkGroup
                + ", isPrivate=" + isPrivate + ", hasRecordAudio=" + hasRecordAudio + ", voxPath=" + voxPath
                + ", search=" + search + ", parentNameStr=" + parentNameStr + ", pathName=" + pathName + ", kdDetail="
                + kdDetail + ", createTimeStr=" + createTimeStr + ", createUser=" + createUser + "]";
    }

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public String getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

}