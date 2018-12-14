/**
 * 已选择的树节点
 * 主要用在人员选择和部门角色选择后返回的数据
 **/

function TreeNode()
{

}
 //节点Id
TreeNode.prototype.setId=function(Id)
{
    this.Id =Id;
} ;
TreeNode.prototype.getId=function()
{
    return this.Id ;
} ;
//节点名称
TreeNode.prototype.setName=function(name)
{
    this.Name =name;
} ;
TreeNode.prototype.getName=function()
{
    return this.Name ;
} ;

/**
 * 节点类型
 *group 部门 role 角色 user 人员
 * @param type
 */
TreeNode.prototype.setType=function(type)
{
    this.Type =type;
} ;
TreeNode.prototype.getType=function()
{
    return this.Type ;
} ;

/**
 * 存放的数据，比方人员可以存放人员手机号码等。。。
 * @param data
 */
TreeNode.prototype.setData=function(data)
{
    this.data =data;
} ;
TreeNode.prototype.getData=function()
{
    return this.data ;
} ;