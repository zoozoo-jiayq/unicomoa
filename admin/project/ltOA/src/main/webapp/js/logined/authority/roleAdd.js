/**
 * @author REN
 */
$(document).ready(function() {
	//单击添加
	$("#addRole").click(function(){
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
function addRole(){
	var valid=validator($("#roleForm")[0]);
    if(valid){
       submitForm();
    }
	return false;
}
/**
 * 添加角色信息
 */
function submitForm() {
	//角色名称
	var roleName=$.trim($("#roleName").val());
	//角色代码
	var roleCode=$.trim($("#roleCode").val());
	//角色描述
	var roleMemo=$.trim($("#roleMemo").val());

	var paramData = {
		'roleInfo.roleName':roleName,
		'roleInfo.roleCode':roleCode,
		'roleInfo.memo':roleMemo
	};
	$.ajax({
		url : basePath+"authority/addRole.action",
		type : "post",
		dataType :'text',
		data:paramData,
		success : function(data) {
			if(data != "") {
				if (data == 1) {
					var getDataTable = art.dialog.data("getDataTable");
					getDataTable();
					art.dialog.close();
//					art.dialog.alert('添加角色成功！', function(){
//						window.location.href = document.referrer;
//					});
				}else if(data == 2){
					art.dialog.alert('角色代码已存在！');
				}else if(data == 3){
					art.dialog.alert('角色名称已存在！');
				}
            } else {
            	art.dialog.alert('添加角色失败！');
            }
	}
	});
}