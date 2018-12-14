jQuery(document).ready(function($){
	var temp = $("#toUserDiv");
	var idTemp = $("#nextProcesser_id");
	$("#nextProcesser_name").click(function(){
		openUserSelect(temp,function(userMap){
			
			var nextAction = $("input[name='nextAction']:checked").val();
			temp.html("");
			idTemp.val("");
			//多选
			if(nextAction == "to 收文阅读"){
				selectManyUser(userMap);
			//单选
			}else{
				selectSingleUser(userMap);
			}

		});
	});

	//选中多个人
	var selectManyUser = function(userMap){
		temp.html()
		userMap.forEach(function(value,key){
				if(value.Type=="user" && value.Id!='gid'){
				temp.html(temp.html()+value.Name+",");
				idTemp.val(idTemp.val()+value.Id+",");
			}
		});
	}

	//选中单个人
	var selectSingleUser = function(userMap){
		var flag = 0;
		userMap.forEach(function(value,key){
			if(value.Type=="user" && value.Id!='gid'){
				if(flag == 0){
					temp.html(value.Name);
					idTemp.val(value.Id);
				}
				flag++;
			}
		});
	}

	//选择走向事件
	$("input[name='nextAction']").click(function(){
		var temp = $("#toUserDiv");
		var idTemp = $("#nextProcesser_id");
		temp.html("");
		idTemp.val("");
	});
	
	$("#comRead").click(function(){
		if(!$("#nextProcesser_id").val()){
			return null;
		}
		$("form").submit();
	});
	
	/*
	$(".bookList li p").click(function(){
	if ($(this).parent().hasClass("current"))
		$(this).parent().removeClass("current");
	else
	{
		$(this).parent().siblings("li").removeClass("current");
		$(this).parent().addClass("current");
	}})
	*/
});