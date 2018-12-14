package cn.com.qytx.oa.deskTop.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

/**
 * 功能:个人首页的页面
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-16 上午10:07
 * 修改日期: 13-4-16 上午10:07
 * 修改人员: 汤波涛
 * 修改列表:
 */
@Entity
@Table(name="tb_op_desktop_page")
public class DesktopPage extends BaseDomain{
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
     * 排序号
     */
	@Column(name="order_no")
    private Integer orderNo;

    /**
     * 该Page中的的所有Module，仅显示用
     */
	@Transient
    private List<DesktopModule> desktopModuleList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public List<DesktopModule> getDesktopModuleList() {
        return desktopModuleList;
    }

    public void setDesktopModuleList(List<DesktopModule> desktopModuleList) {
        this.desktopModuleList = desktopModuleList;
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
