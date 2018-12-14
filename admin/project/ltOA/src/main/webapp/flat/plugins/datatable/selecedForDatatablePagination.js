var _checkedIds="";//翻页保存选中的id
	$(document).ajaxComplete(function(event,request, settings){

		$("table.dataTable").delegate("input[type=checkbox]","click",function(){
			_changeIds();
		});
		
	});

	function _changeIds() {
		var oneches = $("tbody input[type=checkbox]");
		for ( var i = 0; i < oneches.length; i++) {
			if (oneches[i].checked == true) {
				// 避免重复累计id （不含该id时进行累加）
				if (_checkedIds.indexOf(oneches[i].value+",") != 0 && _checkedIds.indexOf(","+oneches[i].value+",") < 0) {
					_checkedIds = _checkedIds + oneches[i].value + ",";
				}
			}
			if (oneches[i].checked == false) {
				// 取消复选框时 含有该id时将id从全局变量中去除
				if (_checkedIds.indexOf(oneches[i].value+",") == 0 || _checkedIds.indexOf(","+oneches[i].value+",") > 0) {
					_checkedIds = _checkedIds.replace((oneches[i].value + ","), "");
				}
			}
		}
	}
	 

    function _getChecked() {
		var oneches = $("tbody input[type=checkbox]");
		for ( var i = 0; i < oneches.length; i++) {
			//全局变量中含有id，则该复选框选中
			if (_checkedIds.indexOf(oneches[i].value+",") == 0 || _checkedIds.indexOf(","+oneches[i].value+",") > 0) {
				$(oneches[i]).attr("checked", "checked");
			} else {
				$(oneches[i]).removeAttr("checked");
			}
		}
		//判断全选是否被选中
		if($("tbody input[type=checkbox]:checked").length==$("tbody input[type=checkbox]").length && $("tbody input[type=checkbox]").length > 0){
			$("thead input[type=checkbox]").prop("checked",true);
		}else{
			$("thead input[type=checkbox]").prop("checked",false);
		}
	}