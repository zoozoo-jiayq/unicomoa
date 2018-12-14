/**
 * 自定义节点属性
 * @param nodeName  节点名称
 * @param candidate  办理人
 * @param roles  角色
 * @param dept  部门
 * @returns {NodeFormAttribute} 节点属性
 */
  function NodeFormAttribute(nodeName,candidate,roles,dept,nodeType,id){
	  this.nodeName = nodeName;
	  this.candidate=candidate;
	  this.roles=roles;
	  this.dept=dept;
	  this.nodeType=nodeType;
	  this.id =id;
	 }
    
    
     
