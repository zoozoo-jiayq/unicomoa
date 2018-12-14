$(document).ready(function(){
	var processId=$("#processId").val();
	//获取表单属性
	$.ajax({
		type: "POST",
        url: basePath+"/workflow/option!getStartFormProperties.action",
        data: {"processId":processId,"_clientType":"wap"},
        success: function(msg){
            if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                showDataWithHTML(result);
            }
        }
	});
});

function showDataWithHTML(result){
	result =jQuery.parseJSON(result);
	if(result!=null){
		var html="";
		for(var i=0;i<result.length;i++){
			var propertyId=result[i].propertyId;
			var cnName=result[i].cnName;
			var name=result[i].name;
			var canEdit=result[i].canEdit;
			var htmlType=result[i].htmlType;
			
	        html+="<div class=\"input-group\">";
	        html+="<span class=\"input-group-addon bdnone\">"+cnName+"</span>";
	        //表单控件类别
	        if(htmlType=="text"){//输入框
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext\" placeholder=\"请输入内容\" name='"+name+"' />";
	        }else if(htmlType=="users"){//选人控件
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }else if(htmlType=="group"){//选部门
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }else if(htmlType=="radio"){//单选
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }else if(htmlType=="checkbox"){//复选
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }else if(htmlType=="select"){//下拉
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }else if(htmlType=="textarea"){
	        	html+="<input type=\"textarea\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }else if(htmlType=="approve"){
	        	html+="<input type=\"text\" class=\"form-control bdnone rtext arrow\" placeholder=\"请选择\" name='"+name+"' />";
	        }
	        html+="</div>";
			
		}

		$("form").append(html);
	}
	
}

