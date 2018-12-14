function initNtko(){
	var taskId = $("#taskIds").val();
    var isOpen = $("#isOpen").val();
    if(isOpen=="0"){
       $("#isOpen").val("1");
      	var url = basePath+"logined/publicDom/ntkodoc/ntkoDoc.jsp?taskId="+taskId;
   	    $("#ntkoframe").attr("src",url);
    }
 }

function initNtkoPdf(){
	var isOpenPdf = $("#isOpenPdf").val();
    if(isOpenPdf=="0"){
       $("#isOpenPdf").val("1");
       var documentExtId = $("#documentExtId").val();
      	var url = basePath+"logined/publicDom/ntkodoc/ntkoPdf.jsp?documentExtId="+documentExtId;
   	    $("#ntkoPdfframe").attr("src",url);
    }
}

/**
 * doc 转pdf
 */
function toPdf(){
	var documentExtId =$("#documentExtId").val();
	 window.frames["ntkoframe"].saveFileAsPdfToUrl(documentExtId);
}

 /**
* 重置iFrame的高度
*/
function frameResize1(){
   var frame=$("#ntkoframe");
   if(frame.length>0){
       frame.height(frame.contents().find("body").height()+60);
   }
}
function saveOffice(){
	saveFileToUrl();
}

//复制文号到剪切板
function doCopyWenhao(){
  var taskId = $("#taskId").val();
  var domflow = $("#domflow").val();
  $.post(basePath+"/dom/public!getWenhaoFromVar.action?taskId="+taskId+"&domflow="+domflow,function(data){
	  $("#formDiv").hide();
	  if(data){
		  clipboardData.setData('Text',data); 
			var content =  '文号："'+data+'" 已成功复制到剪切板!您可以在需要插入文号的地方进行粘贴。';
			art.dialog.alert(content,function(){
				$("#formDiv").show();
			});
	  }else{
		  art.dialog.alert("系统尚未生成文号!",function(){
				$("#formDiv").show();
			});
	  }
  });
}

function dogetDocHistoy(){
  var taskId = $("#taskId").val();
  $("#formDiv").hide();
  art.dialog.open(basePath+"/logined/publicDom/newDom/docHistory.jsp?taskId="+taskId,{
    title:"正文历史记录",
    width:"800px",
    height:"450px",
    lock : true,
    opacity: 0.08,
    ok:function(){
      $("#formDiv").show();
      return true;
    },
    close:function(){
    	$("#formDiv").show();
    	return true;
    }
  });
}

/*
 *选择套红模板
 */
function doSelectDocTemplate(){
  var taskId = $("#taskId").val();
  $.get(basePath+"/dom/public!getTohongTemplatePath.action?templateId="+$("#templateId").val()+"&r="+Math.random(),function(data){
      var url = basePath+"/filemanager/downFileByFilePath.action?filePath="+data;
      TANGER_OCX_DoTaoHong(encodeURI(url));
  });
}

/*
 *选择印章
 */
function doSelectSign(){
   $("#formDiv").hide();
  art.dialog.open(basePath+"/logined/publicDom/newDom/public_selectSign.jsp",{
    "title":"选择印章",
    "width":"800px",
    "height":"450px",
    lock : true,
    opacity: 0.08,
    ok:function(){
      $("#formDiv").show();
      var ifr = this.iframe;
      var subWin = ifr.contentWindow;
      var signUrl = $("#webSign",subWin.document).val();
      if(signUrl){
    	  addYZ(signUrl);
       // setWebSign(signUrl);

      }
    }
  });
}

/*
 *选择印章
 */
function doSelectSignByType(type){
   $("#formDiv").hide();
  art.dialog.open(basePath+"/logined/publicDom/newDom/public_selectSign.jsp?webSignType="+type,{
    "title":"选择印章",
    "width":"800px",
    "height":"450px",
    lock : true,
    opacity: 0.08,
    ok:function(){
      $("#formDiv").show();
      var ifr = this.iframe;
      var subWin = ifr.contentWindow;
      var signUrl = $("#webSign",subWin.document).val();
      if(signUrl){
       addYZ(signUrl);
      }
    }
  });
}


/*
 * 套红盖章
 */
function TANGER_OCX_DoTaoHong(URL)
{
  try{
    OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.HomeKey(6);
    OFFICE_CONTROL_OBJ.AddTemplateFromURL(URL);
  }
  catch(err)
  {
  };
};


//没有生成文号,按钮不可操作
function showOrHideWenhao(){
var taskId = $("#taskId").val();
if(taskId!=""){
	  $.post(basePath+"/dom/public!getWenhaoFromVar.action?taskId="+taskId,function(data){
		  if(data){
			  
		  }else{
			  // $("#liwenhao").attr("disabled","disabled");
			  // $("#liwenhao").addClass("disabled");
			   //alert("系统尚未生成文号!");
		  }
	  });
}else{
	  $("#liwenhao").attr("disabled","disabled");
	  $("#liwenhao").addClass("disabled");
}

}


/**
* 印章
*/
function setWebSign(webSignUrl){
webSignUrl =basePath+"upload/webSign/"+webSignUrl;
OFFICE_CONTROL_OBJ.AddSignFromURL("ntko",//印章的用户名
		webSignUrl,//印章所在服务器相对url
		100,//左边距
		100,//上边距 根据Relative的设定选择不同参照对象
		"ntko",//调用DoCheckSign函数签名印章信息,用来验证印章的字符串
		1,  //Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
		100,//缩放印章,默认100%
		1);   //0印章位于文字下方,1位于上方
}

/**
* 初始化
*/
function init(){
	var documentExtId =$(window.parent.document).find("#documentExtId").val();
	$("#documentExtId").val(documentExtId); //将父窗体的流程实例ID放入当前页面
	var documentExtId = $("#documentExtId").val();
	var url = basePath+"dom/public!getOfficeContent.action?documentExtId="+documentExtId;
	intializePage(url);  //读取文件，如果没有保存，则读取默认页
}
