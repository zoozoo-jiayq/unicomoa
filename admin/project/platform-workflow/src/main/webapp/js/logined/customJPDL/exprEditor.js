var exprlist = {};

	//构造表达式对象
	function generateExpr(path){
		var pro = $("#formProperty_"+path).val();
		var rela = $("#realtive_"+path).val();
		var val = $("#exprValue_"+path).val();
		var con = $("#connect_"+path).val();
		if(!val){
			art.dialog.alert("表达式的值不能为空!");
			return ;
		}
		//检查是否重复
		if(exprlist && exprlist[path]){
			for(var i=0; i<exprlist[path].length;i++){
				var temp = exprlist[path][i];
				if(temp.property == pro && temp.rela == rela && temp.value==val){
					art.dialog.alert("表达式重复!");
					return ;
				}
			}
		}
		var expr = {};
		expr.property = pro;
		expr.rela 	  = rela;
		expr.value	  = val;
		expr.connect  = con;
		if(!exprlist[path]){
			exprlist[path] = new Array();
		}
		exprlist[path].push(expr);

		var trObj = document.createElement("tr");
		var td1Obj = document.createElement("td");
		$(td1Obj).append($("#formProperty_"+path).find("option:selected").text()+" "+exprMap[rela]+" "+val);
		var td2Obj = document.createElement("td");
		var aObj = document.createElement("a");
		$(aObj).html("删除");
		$(aObj).attr("href","#");
		$(aObj).click(function(){
			deleteExpr(path,pro,rela,val,trObj);
		});
		$(td2Obj).append(aObj);
		$(trObj).append(td1Obj).append(td2Obj);
		if($("#plainTr_"+path)){
			$("#plainTr_"+path).remove();
		}
		$("#table_"+path).append(trObj);
	}
	
	//删除表达式对象
	function deleteExpr(path,property,rela,value,trObj){
		var array = exprlist[path];
		for(var i=0;i<array.length;i++){
			var temp = array[i];
			if(temp.property == property && temp.rela == rela && temp.value == value){
				array.splice(i,1);
			}
		}
		$(trObj).remove();
		// $("#table_"+path).find("tr").each(function(){
		// 	var tds = $(this).find("td");
		// 	alert(tds.length);
		// 	if(tds.length==2){
		// 		if($(tds[0]).html() == cnpro && $(tds[1]).html() == cnrela && $(tds[2]).html() == value ){
		// 			$(this).remove();
		// 		}
		// 	}
		// });
		
	}

	//构造条件表达式
	function buildExpr(){
		var exprText = "";
		for(var p in exprlist){
			exprText+=p+":";
			var temp = exprlist[p];
			for(var i=0;i<temp.length;i++){
				var tempExpr = "";
				if(i>0){
					exprText+=exprMap[temp[i].connect];
				}
				tempExpr = "(";
				if(isNaN(temp[i].value)){
					tempExpr += temp[i].property+" "+exprMapForJpdl[temp[i].rela]+" @"+temp[i].value+"@";
				}else{
					tempExpr += temp[i].property+" "+exprMapForJpdl[temp[i].rela]+" ~"+temp[i].value+"~";
				}
				tempExpr +=")";
				exprText+=tempExpr;
			}
			exprText+=",";
		}
		exprText = exprText.substring(0,exprText.length-1);
		return exprText;
	}