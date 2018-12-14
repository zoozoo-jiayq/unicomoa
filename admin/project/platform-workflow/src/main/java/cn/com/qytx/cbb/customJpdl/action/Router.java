package cn.com.qytx.cbb.customJpdl.action;

import java.io.Serializable;


/**
 * 功能：判断节点转出路径模型
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-21 下午5:19:33 
 * 修改日期：2013-3-21 下午5:19:33 
 * 修改列表：
 */
public class Router implements Serializable{
	
		//转出路径ID
		private String id;
		//转出路径名称
		private String name;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
}
