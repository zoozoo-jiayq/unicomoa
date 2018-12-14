/**
 * 获取是否显示及选中 发送事务提醒 选项
 * @param moduleName
 * @param checkBoxId
 * @param parentId
 */
function setAffairCheck(moduleName, checkBoxId, parentId){
	var paramData={
	    'moduleName':moduleName
	};

	qytx.app.ajax({
	    url : basePath + "affairs/setup_getAffairPriv.action",
	    type : "post",
	    data: paramData,
	    dataType : "text",
	    success : function(data) {
		    if ("" != data && "error" != data) {
		    	var affairPrivarr = data.split("|");
		    	if ("1" == affairPrivarr[0]){
		    		$("#"+parentId).show();
		    		if (affairPrivarr.length > 1 && "1" == affairPrivarr[1]){
			    		$("#"+checkBoxId).prop("checked", true);
			    	}
		    	}else{
		    		$("#"+parentId).hide();
		    	}
		    }
	    }
	});
}