package cn.com.qytx.platform.org.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.org.domain.base.BaseModuleInfo;

/**
 * 功能:菜单表
 */
@Entity
@Table(name="tb_module_info")
public class ModuleInfo extends BaseModuleInfo {
	// 1移动发布正式功能，2移动业务没有正式对外发布 3全亚自有功能
		@Column(name="menu_type")
		private Integer menuType;
		
		//1,默认用户有权限，否则默认无权限
		@Column(name="default_state")
		private Integer defaultState;
		
		@Transient
		private Integer openState;
		
		public Integer getDefaultState() {
			return defaultState;
		}
		public void setDefaultState(Integer defaultState) {
			this.defaultState = defaultState;
		}
		public Integer getMenuType() {
			return menuType;
		}
		public void setMenuType(Integer menuType) {
			this.menuType = menuType;
		}
		public Integer getOpenState() {
			return openState;
		}
		public void setOpenState(Integer openState) {
			this.openState = openState;
		}

		
}