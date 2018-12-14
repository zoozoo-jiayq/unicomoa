/**
 * @author REN
 */
$(document).ready(function() {
	//单击编辑
	$("#updateRole").click(function(){
       var valid=validator($("#roleForm")[0]);
        if(valid){
           submitForm();
        }
        return false;
	});
	//单击取消
	$("#cancelRole").click(function(){
		art.dialog.close();
		return false;
	});
});
function updateRole(){
	var valid=validator($("#roleForm")[0]);
    if(valid){
       submitForm();
    }
    return false;
	
}


/**
 * 编辑角色信息
 */
function submitForm() {
    //角色id
	var roleId=$.trim($("#roleId").val());
	//角色名称
	var roleName=$.trim($("#roleName").val());
	//角色代码
	var roleCode=$.trim($("#roleCode").val());
	//角色描述
	var roleMemo=$.trim($("#roleMemo").val());

	var paramData = {
		'role.roleId':roleId,
		'role.roleName':roleName,
		'role.roleCode':roleCode,
		'role.memo':roleMemo
	};
	$.ajax({
		url : basePath+"authority/updateRole.action",
		type : "post",
		dataType :'text',
		data:paramData,
		success : function(data) {
			if(data != "") {
				if (data == 1) {
//					art.dialog.alert('编辑角色成功！');
					var getDataTable = art.dialog.data("getDataTable");
					getDataTable();
					art.dialog.close();
				}else if(data == 2){
					art.dialog.alert('角色代码已存在！');
				}else if(data == 3){
					art.dialog.alert('角色名称已存在！');
				}
            } else {
            	art.dialog.alert('编辑角色失败！');
            }
	}
	});
}