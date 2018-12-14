package cn.com.qytx.platform.org.domain.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.com.qytx.platform.base.domain.BaseDomain;


/**
 * User: 黄普友
 * Date: 13-2-19
 * Time: 下午4:59
 */
@MappedSuperclass
public class BaseRoleUser extends BaseDomain{
	
    @Id
    @Column(name = "Vid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vid;
    
    @Column(name="role_id")
    private Integer roleId;
    
    @Column(name="user_id")
    private Integer userId;
    
    //1主角色；0辅助角色
    @Column(name="type")
    private Integer type;


	public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
