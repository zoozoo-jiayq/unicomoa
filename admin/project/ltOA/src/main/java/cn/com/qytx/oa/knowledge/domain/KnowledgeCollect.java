package cn.com.qytx.oa.knowledge.domain;

import java.io.Serializable;
import java.sql.Timestamp;
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

@Entity
@Table(name="tb_Knowledge_collect")
public class KnowledgeCollect extends BaseDomain  implements Serializable{

	private static final long serialVersionUID = 1780915188596919432L;
	
	/**
     * 主键
     */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // Id
    
    /**
     * 知识库ID
     */
    @JoinColumn(name = "knowledge_id")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Knowledge knowledge;

    /**
     * 坐席ID
     */
    @JoinColumn(name = "seat_id")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private UserInfo seat;
    
    /**
     * 状态 1：删除 0保存
     */
    @Column(name = "state")
    private Integer state;
    
    /**
     * 更新日期
     */
    @Column(name = "last_update_time")
    private Timestamp lastUpdateTime;
    
    /**
     * 查询条件
     */
    @Transient
    private String search;
    
    /**
     * 查询条件
     */
    @Transient
    private Integer knowledgeTypeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public UserInfo getSeat() {
		return seat;
	}

	public void setSeat(UserInfo seat) {
		this.seat = seat;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

    public Integer getKnowledgeTypeId()
    {
        return knowledgeTypeId;
    }

    public void setKnowledgeTypeId(Integer knowledgeTypeId)
    {
        this.knowledgeTypeId = knowledgeTypeId;
    }

    /**
     * 功能：KnowledgeToMap
     * @param i
     * @return
     */
    public Map<String, Object> thisToMap(int i)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("orderNumber", i);
        map.put("vid", knowledge.getVid());
        map.put("title", StringUtils.defaultString(knowledge.getTitle()
                , CallCenterConst.WORKORDER_TABLE_BLANK));
        map.put("keyword",  StringUtils.defaultString(knowledge.getTitle()
                , CallCenterConst.WORKORDER_TABLE_BLANK));
        map.put("userName",  knowledge.getCreateUserInfo()!=null?knowledge.getCreateUserInfo().getUserName()
                : CallCenterConst.WORKORDER_TABLE_BLANK);

        map.put("createTime", null != knowledge.getCreateTime() ? TimeUtil.dateToStr(knowledge.getCreateTime(), "yyyy-MM-dd HH:mm")
                : CallCenterConst.WORKORDER_TABLE_BLANK);

        map.put("pathName",StringUtils.defaultString(knowledge.getPathName(), CallCenterConst.WORKORDER_TABLE_BLANK));
        //创建时间，格式:yyyy-MM-dd
        map.put("auditTime",  null!=knowledge.getAuditTime()?TimeUtil.dateToStr(knowledge.getAuditTime(),"yyyy-MM-dd"):CallCenterConst.WORKORDER_TABLE_BLANK);


        map.put("orderIndex", null != knowledge.getKnowledgeType().getOrderIndex() ? knowledge.getKnowledgeType().getOrderIndex()
                : CallCenterConst.WORKORDER_TABLE_BLANK);
         
        map.put("keyword", StringUtils.defaultString(knowledge.getKeyword(), CallCenterConst.WORKORDER_TABLE_BLANK));
        map.put("managerKnowledge", StringUtils.defaultString("", CallCenterConst.WORKORDER_TABLE_BLANK));

        map.put("contentInfo", StringUtils.defaultString(knowledge.getContentInfo(), CallCenterConst.WORKORDER_TABLE_BLANK));
        return map;
    }

    /**
     * 功能：
     * @return
     */
    @Override
    public String toString()
    {
        UserInfo  user=(UserInfo) ServletActionContext.getRequest().getSession().getAttribute("adminUser");
        return "操作人="+user.getUserName()+"KnowledgeCollect [id=" + id + ", knowledge=" + knowledge + ", seat=" + seat + ", state=" + state
                + ", lastUpdateTime=" + lastUpdateTime + ", search=" + search + ", knowledgeTypeId=" + knowledgeTypeId
                + "]";
    }
    
    
}
