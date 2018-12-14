package cn.com.qytx.oa.modulePriv.domain;


/**
 * 模块权限实体类
 * @author lhw
 *
 */
public class ModulePrivVo
		implements java.io.Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5926084464541986761L;
 /**
  * 引用的id
  */
   private String refId;
   /**
   * 引用的的文件夹名字
   */
   private String refName;
   /**
    * 访问权限
    */
    private String visitPriv;
    /**
     * 新建权限
     */
     private String newPriv;
     /**
      * 下载权限
      */
      private String downPriv;
      /**
       * 编辑权限
       */
       private String editPriv;
       /**
        * 删除权限
        */
        private String delPriv;
        /**
         * 所有者权限
         */
         private String ownPriv;
		public String getRefId() {
			return refId;
		}
		public void setRefId(String refId) {
			this.refId = refId;
		}
		public String getRefName() {
			return refName;
		}
		public void setRefName(String refName) {
			this.refName = refName;
		}
		public String getVisitPriv() {
			return visitPriv;
		}
		public void setVisitPriv(String visitPriv) {
			this.visitPriv = visitPriv;
		}
		public String getNewPriv() {
			return newPriv;
		}
		public void setNewPriv(String newPriv) {
			this.newPriv = newPriv;
		}
		public String getDownPriv() {
			return downPriv;
		}
		public void setDownPriv(String downPriv) {
			this.downPriv = downPriv;
		}
		public String getEditPriv() {
			return editPriv;
		}
		public void setEditPriv(String editPriv) {
			this.editPriv = editPriv;
		}
		public String getDelPriv() {
			return delPriv;
		}
		public void setDelPriv(String delPriv) {
			this.delPriv = delPriv;
		}
		public String getOwnPriv() {
			return ownPriv;
		}
		public void setOwnPriv(String ownPriv) {
			this.ownPriv = ownPriv;
		}
    
}