package cn.com.qytx.cbb.org.action;

import cn.com.qytx.platform.utils.tree.TreeNode;

public class TreeNod extends TreeNode {
	/**
	 * 数组存
	 */
	private Object[] arr;

	private String treeType;
		

		public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

		public Object[] getArr() {
			return arr;
		}

		public void setArr(Object[] arr) {
			this.arr = arr;
		}
}
