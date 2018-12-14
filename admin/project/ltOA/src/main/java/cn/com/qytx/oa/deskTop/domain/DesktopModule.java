package cn.com.qytx.oa.deskTop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.ModuleInfo;

/**
 * 功能:个人首页的页面里面的项
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-16 上午10:07
 * 修改日期: 13-4-16 上午10:07
 * 修改人员: 汤波涛
 * 修改列表:
 */
@Entity
@Table(name="tb_op_desktop_module")
public class DesktopModule extends BaseDomain{

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete;
	
    /**
     * 用户ID
     */
	 @Column(name="user_id")
    private Integer userId;
    /**
     * 菜单ID
     */
	@JoinColumn(name="module_info_id")
	@ManyToOne(cascade=CascadeType.REFRESH)
    private ModuleInfo moduleInfo;
    /**
     * 桌面页ID
     */
	 @Column(name="desktop_page_id")
    private Integer desktopPageId;

    /**
     * 排序号
     */
	 @Column(name="order_no")
    private Integer orderNo;

    /**
     * 页面子菜单-不存数据库
     */
	 @Transient
    private String pageMenuJson;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;
    }
    
    public Integer getDesktopPageId() {
        return desktopPageId;
    }

    public void setDesktopPageId(Integer desktopPageId) {
        this.desktopPageId = desktopPageId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getPageMenuJson() {
        return pageMenuJson;
    }

    public void setPageMenuJson(String pageMenuJson) {
        this.pageMenuJson = pageMenuJson;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	
}
