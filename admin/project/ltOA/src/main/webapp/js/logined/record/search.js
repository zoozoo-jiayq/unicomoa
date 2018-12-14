
/**
 * 功能:人事档案模块，搜索页面js
 */
$(document).ready(function () {
	// 清除table页的cookie数据
	$.removeTableCookie('SpryMedia_DataTables_dataTable_record');
    setSelectDefaultValue();
    setRadioDefaultValue();
    dateStr2ShortForInput();
    //已写到也面上了
//    initMy97DatePicker();
    $("body").show();
    
});

/**
 * 清空操作
 * 
 * @param obj
 * @return
 */
function clearInput(obj1,obj2) {
	if (obj1 == 'roleNames' && obj2 == 'roleIds') {
		$("#roleIds").val('');
		$("#roleNames").val('');
	}else if(obj1 == 'groupNames' && obj2 == 'groupIds'){
		$("#groupNames").val('');
		$("#groupIds").val('');
	}
}
