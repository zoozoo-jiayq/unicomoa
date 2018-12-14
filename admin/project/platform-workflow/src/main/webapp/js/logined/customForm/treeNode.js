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
    this.id =Id;
} ;
TreeNode.prototype.getId=function()
{
    return this.id ;
} ;
//节点名称
TreeNode.prototype.setName=function(name)
{
    this.name =name;
} ;
TreeNode.prototype.getName=function()
{
    return this.name ;
} ;

/**
 * 人员类型
 *0 经办人   1主办人
 * @param type
 */
TreeNode.prototype.setType=function(type)
{
    this.type =type;
} ;
TreeNode.prototype.getType=function()
{
    return this.type ;
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
/**
 * 节点的父id
 * @param data
 */
TreeNode.prototype.setPId=function(pId)
{
    this.pId =pId;
} ;
TreeNode.prototype.getPId=function()
{
    return this.pId ;
} ;