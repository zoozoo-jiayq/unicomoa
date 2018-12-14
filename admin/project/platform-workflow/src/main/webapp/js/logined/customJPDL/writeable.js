/**
 * 设置可编辑字段
 */
jQuery(document).ready(function(){

	//修改的时候从备选字段里去掉已选中的
	var ws = $("#writeableProperties").val();
	ws = ws.split(",");
	$("#props").find("option").each(function(){
		var temp = $(this).attr("value");
		for(var i=0;i<ws.length;i++){
			if(ws[i] == temp){
				$(this).remove();
			}
		}
	});

	$("#writeAbles").find("option").each(function(){
		if(!$(this).attr("value")){
			$(this).remove();
		}
	});

	//选择全部可写字段
	$("#allW").click(function(){
		$("#writeAbles").find("option").each(function(){
			$(this).attr("selected",true);
		});
	});
	$("#allProps").click(function(){
		$("#props").find("option").each(function(){
			$(this).attr("selected",true);
		});
	});

	//向左选择
	$("#leftSelect").click(function(){
		$("#props").find("option").each(function(){
			if($(this).attr("selected")){
				var id = $(this).attr("value");
				var name = $(this).html();

				var temp = document.createElement("option");
				temp.value = id;
				temp.text = name;
				document.getElementById("writeAbles").options.add(temp);
				$(this).remove();
			}
		});
	});

	//向右选择
	$("#rightSelect").click(function(){
		$("#writeAbles").find("option").each(function(){
			if($(this).attr("selected")){
				var id = $(this).attr("value");
				var name = $(this).html();
		
				var temp = document.createElement("option");
				temp.value = id;
				temp.text = name;
				document.getElementById("props").options.add(temp);
				$(this).remove();
			}
		});
	});

	//确认
	$("#sure").click(function(){
		var str = "";
		$("#writeAbles").find("option").each(function(){
			var id = $(this).attr("value");
			if(id){
				str+=id+",";
			}
		});
		$("#writeableProperties").val(str);
		$("form").submit();
	});


});