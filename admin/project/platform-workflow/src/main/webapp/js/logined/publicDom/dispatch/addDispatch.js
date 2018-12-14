
	function check(){
		return validator(document.getElementById("addDispatch"));
	}

	function getData(){
		return {
			gongwenTypeId:$("#gongwenType").val(),
			gongwenType:$("#gongwenType").find("option:selected").text(),
			title:$("#domTitle").val(),
			secretLevel:$("#secretLevel").val(),
			huanji:$("#huanji").val(),
			sourceDept:$("#sourceDept").val()
		};
	}
