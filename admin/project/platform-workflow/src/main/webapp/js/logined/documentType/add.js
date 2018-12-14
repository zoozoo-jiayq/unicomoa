
$(document).ready(function() {
	$("#docTypeName").blur(function(){
		var docTypeName = $("#docTypeName").val();
		if(docTypeName!=""){
			var docTypeId = 0 ;
			if($("#docTypeId").val()){
				docTypeId = $("#docTypeId").val();
			}
			$.get(basePath+"/documentType/checkDocNameIsRepeat.action?docName="+encodeURI(docTypeName)+"&doctypeId="+docTypeId,function(data){
				if(data == "repeat"){
					art.dialog.alert("公文类型名称不能重复!");
					$("#docTypeName").val("");
				}
			});
		}
	});
	
	//加载下拉树的数据
	 $.selectTree(basePath+"workflow/manager!getFormCategoryTree.action?r="+Math.random(),"formCategory","menuContent","treeDemo");
	
	//预览
		$("#prepView").click(function(){
			var formId = $("#formCategory_hidden").val();
			if(!formId){
				art.dialog.alert("请选择公文登记单模版!");
				return ;
			}
			art.dialog.open(basePath+"workflowForm/viewForm.action?formId="+formId,{
				title:"预览表单",
				 width : 800,
				 height : 450,
				ok:true,
				lock : true,
			    opacity: 0.08
			});
		});

	// add by 贾永强 --------------begin-----
	var LODOP; //声明为全局变量

	//检测是否安装打印插件
	function CheckIsInstall() {	 
		try{ 
		     var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
			if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")) {
				// alert("本机已成功安装过Lodop控件!\n  版本号:"+LODOP.VERSION); 
			}
		 }catch(err){ 
			// alert("Error:本机未安装或需要升级!"); 
 		 } 
	};
	CheckIsInstall();

	//绑定套打事件
	$("input[name='docType.taoDa']:radio").click(function(){
		var val = $(this).val();
		if(val == 1){
			$("tr[name='taoDaSetUp']").show();
			$("#printTemplateCode").attr("valid","required");
		}else if(val == 2){
			$("tr[name='taoDaSetUp']").hide();
			$("#printTemplateCode").removeAttr("valid");
		}
	});

	$("input[name='docType.taoDa']:radio:checked").click();

 	//打印维护
 	$("#printSetUp").click(function(){
 		if(!document.getElementById('LODOP_OB')){
 			art.dialog.alert("套打控件未启用！");
 			return ;
 		}
 		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		LODOP.PRINT_INIT("打印维护");
 		// var filelist = attatchmap.arr;
		var attachId = $("#imgAttachId").val();
 		if(attachId){
 			var formId = $("#formCategory_hidden").val();
	 		if(formId){
	 			$.get(basePath+"dom/public!getFormProperties.action?formId="+formId+"&r="+Math.random(),function(data){
	 				//设置打印背景图片
	 				LODOP.ADD_PRINT_SETUP_BKIMG("<img border=0 src="+basePath+"filemanager/prevView.action?attachmentId="+attachId+">");
		 			LODOP.SET_SHOW_MODE("BKIMG_WIDTH","210mm");
		 			LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","297mm");

		 			//设置表单字段属性
		 			data = eval('('+data+')');
		 			var top = 30;
		 			for(var i=0;i<data.length;i++){
		 				var temp = data[i];
		 				LODOP.ADD_PRINT_TEXT(top,100,75,30,temp.propertyNameCh);
		 				top+=40;
		 			}

		 			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
		 			LODOP.PRINT_SETUP();
	 			});
	 		}else{
	 			art.dialog.alert("请选择公文登记单模版！");
	 			return;
	 		}
 		}else{
 			art.dialog.alert("请选择打印背景图片!");
 		}
 	});
 	
}) ;


function save(){
	if(!validator(document.getElementById("newForm"))){
		return;
	}
	var formId=$("#formCategory_hidden").val();
	if(formId==""){
		art.dialog.alert("请选择公文登记单模版！");
		return;
	}
	var docDesc=$("#docDesc").val();
	if(docDesc.length>500){
		art.dialog.alert("文字说明不超过500字！");
		return;
	}
	$("input",$("#newForm")).removeAttr("disabled");
	$("#newForm").submit();
}


function update(){
	if(!validator(document.getElementById("newForm"))){
		return;
	}
	var formId=$("#formCategory_hidden").val();
	if(formId==""){
		art.dialog.alert("请选择公文登记单模版！");
		return;
	}
	
	var docDesc=$("#docDesc").val();
	if(docDesc.length>500){
		art.dialog.alert("文字说明不超过500字！");
		return;
	}
	$("input",$("#newForm")).removeAttr("disabled");
	$("#newForm").submit();
}

//展开显示
function showHide(id){
	var v_ID = document.getElementById(id);
	if (jQuery(v_ID).is(":visible"))
	jQuery(v_ID).hide();
	else
	jQuery(v_ID).show();
}

