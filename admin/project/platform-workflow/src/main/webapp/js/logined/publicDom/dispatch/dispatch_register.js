jQuery(document).ready(function($){
	//新增公文
	$("#add").click(function(){
		addDispatchDialog();
	});
});


/**
 * 添加页面
 */
function addDispatchDialog(){
	art.dialog.open(basePath+"dispatchDom/dispatch!toAddRegister.action",{
		title:"新增发文",
		 width : 800,
		 height : 450,
			lock : true,
		    opacity: 0.08,
		ok:function(){
			//子窗口对象
			var ifr = this.iframe;
			var subWin = ifr.contentWindow;
	    	if(!subWin["check"]()){
	    		return false;
	    	}
	    	var data = subWin["getData"]();
	    	addDispatch(data);
		},
		cancel:true
	});
}


function addDispatch(data){
	var gongwenTypeName=data.gongwenType;
	var gongwenTypeId = data.gongwenTypeId;
	$.post(basePath+"dispatchDom/dispatch!checkTitleIsRepeat.action?title="+encodeURI(data.title),function(result){
		if(result.indexOf("success")>=0){
			art.dialog.alert("标题重复,请重新输入!",function(){  addDispatchDialog();   }); 
			
		} else{
				var paramData={
						'gongwenType':gongwenTypeName,
						'gongwenTypeId':gongwenTypeId,
						'title':data.title,
						'secretLevel':data.secretLevel,
						'huanji':data.huanji,
						'sourceDept':data.sourceDept
				 };
					 $.ajax({
					      url:basePath+"dispatchDom/ajax_addDispatch.action",
					      type:"post",
					      dataType: "html",
					      data:paramData,
					      beforeSend:function(){
								$("body").lock();
							},
							complete:function(){
								$("body").unlock();
							},
					      success: function(data){
					    	     if(data==1){
					    	    	  getDataTable();
					    	     } 
					    	}
					 }); 
		}
	});

	
	
}

 

 


