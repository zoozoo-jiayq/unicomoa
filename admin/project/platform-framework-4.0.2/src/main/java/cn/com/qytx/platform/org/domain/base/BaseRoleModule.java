package cn.com.qytx.platform.org.domain.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.com.qytx.platform.base.domain.BaseDomain;


/**
 * 功能：基本角色模块
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:12:05 
 * 修改日期：上午11:12:05 
 * 修改列表：
 */
@MappedSuperclass
public class BaseRoleModule extends BaseDomain {

    @Id
    @Column(name = "Vid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vid;
    
    @Column(name="role_id")
    private Integer roleId;
    
    @Column(name="module_id")
    private Integer moduleId;


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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}
