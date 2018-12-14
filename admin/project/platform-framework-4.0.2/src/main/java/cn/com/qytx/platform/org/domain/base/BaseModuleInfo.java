package cn.com.qytx.platform.org.domain.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.DeleteState;

import com.google.gson.annotations.Expose;


@MappedSuperclass
public class BaseModuleInfo implements Serializable {

	// Fields
	@Expose
	@Id
    @Column(name = "module_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer moduleId;
	@Expose
	@Column(name="module_name",length=50)
	private String moduleName;
	@Expose
	@Column(name="parent_id")
	private Integer parentId;
	
	//模块路径
	@Column(name="Url",length=100)
	private String url;
	
	@Column(name="Memo",length=500)
	private String memo;
	
	//排序
	@Column(name="order_index")
	private Integer orderIndex;
	
	//图片
	@Column(name="Icon",length=50)
	private String icon;
	
	//1,菜单；2页面上的TAB菜单
	@Column(name="module_type")
    private Integer moduleType;
    
    //模块编码
	@Expose
    @Column(name="module_code",length=50)
    private String moduleCode;
	
	//模块级别
	@Column(name="module_level")
    private int moduleLevel;
	
	
	@Column(name="sys_name",length=50)
    private String sysName;
	
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;

	//菜单样式
	@Column(name="module_class",length=50)
	private String moduleClass;
	
    public String getModuleClass() {
		return moduleClass;
	}

	public void setModuleClass(String moduleClass) {
		this.moduleClass = moduleClass;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public int getModuleLevel() {
        return moduleLevel;
    }

    public void setModuleLevel(int moduleLevel) {
        this.moduleLevel = moduleLevel;
    }


	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Transient
	private Boolean isSelected;//当前模版是否被选中
	
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getParentId() {
        return parentId;	
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}