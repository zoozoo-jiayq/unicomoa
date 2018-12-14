package cn.com.qytx.platform.org.domain.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.com.qytx.platform.base.domain.BaseDomain;


/**
 * 功能：基本部门人员关系表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:03:42 
 * 修改日期：上午11:03:42 
 * 修改列表：
 */
@MappedSuperclass
public class BaseGroupUser extends BaseDomain{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "Vid")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer vid;
    
    @Column(name="group_id" ,nullable = false)
	private Integer groupId;
    
    @Column(name="user_id" ,nullable = false)
	private Integer userId;
    

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}