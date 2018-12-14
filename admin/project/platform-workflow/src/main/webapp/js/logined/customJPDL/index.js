jQuery(document).ready(function(){
	//存放流程定义,页面加载完毕后自动创建该对象
	var datas = new Array();
	$("div.menu-c ul li a").each(function(){
		var temp = {};
		var name = $(this).html();
		var cateType = $(this).attr("cateType");
		temp.name = name;
		temp.ao = $(this);
		temp.cateType = cateType;
		datas.push(temp);
	});
	var cid = $("#categoryId").val();
	if(cid){
		$("#page").attr("src",basePath+"/workflow/manager!editProcess.action?processAttributeId="+$("#processAttributeId").val()+"&categoryId="+$("#categoryId").val()+"&type="+$("#type").val());
	}else{
		$("#page").attr("src","");
	}
	/*
	$('#searchProcess').autocomplete(datas, { 
		max: 20,    //列表里的条目数
        minChars: 0,    //自动完成激活之前填入的最小字符
        width: 165,     //提示的宽度溢出隐藏
        scrollHeight: 300,   //提示，的高度，溢出显示滚动条
        matchContains: true,    //包含匹配，就是data参数里的数 据，是否只要包含文本框里的数据就显示
        autoFill: false,    //自动填充
        formatItem: function(row, i, max) {
             return row.name ;//做格式处理
        },
        formatMatch: function(row, i, max) {
             return row.name;//做匹配的数据格式
        },
        formatResult: function(row) {
             return row.name;//返回结果                     
        }
     	}).result(function(event, row, formatted) {
         $("#page").attr("src",$(row.ao).attr("href"));
     });
	*/
	qytx.app.autocompleted({
		id:"searchProcess",
		data:datas,
		formatItem: function(row, i, max) {
             return row.name ;//做格式处理
        },
        formatMatch: function(row, i, max) {
             return row.name;//做匹配的数据格式
        },
        formatResult: function(row) {
             return row.name;//返回结果                     
        },
        callback:function(event, row, formatted) {
            $("#page").attr("src",$(row.ao).attr("href"));
        }
	});
});