<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path+"/" ;
    request.setAttribute("ctx", basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>表单智能设计器</title>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="bookmark" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="icon" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<link href="${ctx}css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/lvMain.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/public.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog_alert.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
<link rel="stylesheet" type="text/css" href="theme/10/style.css" />
<style>
html,body{width:100%;margin:0;}	
button{
	margin:0;
}	
button.btnTool{
	width:120px;
	height:24px;
	text-align:left;
	font-size:9pt;
}
button.btnTool img{
	height:16px;
	width:16px;
	vertical-align:middle;
	margin-left:5px;
}
button.btnControl{
	width:120px;
	height:30px;
	text-align:left;
	font-weight:bold;
}
button.btnControl img{margin:0 5px}
table.btnControl{
	margin-top:5px;
}
</style>
<script type="text/javascript">
var basePath ="${ctx}";
$(document).ready(function() {
	var formId=0;
	formId = <%=request.getParameter("formId")%>;
	var paramData = {
		"formId":formId
	};
	$.ajax({
		url : basePath+"workflowForm/loadFormDesigner.action",
		type : "post",
		dataType :'json',
		data:paramData,
		success : function(data) {
			if(data != null && data.fa != null ) {
               $("#FORM_CONTENT").val(data.fa.formSource);
               $("#FORM_ID").val(data.fa.formId);
            }
	}});
	
});
self.moveTo(0,0);
self.resizeTo(screen.availWidth,screen.availHeight);
self.focus();

var form_id = "27";
function CheckForm()
{


  var FCK = FCKeditorAPI.GetInstance('FORM_CONTENT'); //获得表单设计器的顶层对象FCK，该方法定义位置fckeditorapi.js第47行 by dq 090521
  var FORM_HTML = FCK.EditingArea.Window.document.body.innerHTML;
  if(FORM_HTML == "")
  {
  	art.dialog.alert("表单内容不能为空！");
    return (false);
  }
  return (true);
}







function send()
{
  if(CheckForm())
  {
    var FCK = FCKeditorAPI.GetInstance('FORM_CONTENT'); //获得表单设计器的顶层对象FCK，该方法定义位置fckeditorapi.js第47行 by dq 090521
 	  //alert($("#FORM_CONTENT___Frame").contents().find("body").html());
 	  var FORM_MODE = FCK.EditingArea.Mode;
    //获取编辑区域的常量——源文件模式
    var editingAreaFrame = document.getElementById('FORM_CONTENT___Frame');
    var editModeSourceConst = editingAreaFrame.contentWindow.FCK_EDITMODE_SOURCE;//常量FCK_EDITMODE_SOURCE的定义位置_source/fckconstants.js
    if(FORM_MODE == editModeSourceConst)
    {
    	FCK.Commands.GetCommand( 'Source' ).Execute();
    } 
 	
    var FORM_HTML = FCK.EditingArea.Window.document.body.innerHTML;
    document.form1.CONTENT.value=FORM_HTML;
    //保存表单控件
    var paramData={
			'formContent':FORM_HTML,
			'formId':$("#FORM_ID").val()
	};
	$.ajax({
	      url:basePath+"workflowForm/saveFormProperties.action",
	      type:"post",
	      dataType: "json",
	      data:paramData,
	      success: function(data){
				if(data!=null&&data.marked!=null&&data.marked==1){
					art.dialog.alert("表单保存成功！");
				}else if(data!=null&&data.marked!=null&&data.marked==0){
					art.dialog.alert("表单已经被使用，不能修改。");
				}else{
					art.dialog.alert("表单保存失败！");
				}
	    	}
	 }); 
    //document.form1.submit();
  }
}
function view()
{
  if(CheckForm())
  {
    var FCK = FCKeditorAPI.GetInstance('FORM_CONTENT'); //获得表单设计器的顶层对象FCK，该方法定义位置fckeditorapi.js第47行 by dq 090521
 	  var FORM_MODE = FCK.EditingArea.Mode;
   
    //获取编辑区域的常量——源文件模式
    var editingAreaFrame = document.getElementById('FORM_CONTENT___Frame');
    var editModeSourceConst = editingAreaFrame.contentWindow.FCK_EDITMODE_SOURCE;//常量FCK_EDITMODE_SOURCE的定义位置_source/fckconstants.js
    if(FORM_MODE == editModeSourceConst)
    {
    	FCK.Commands.GetCommand( 'Source' ).Execute();
    } 
    var FORM_HTML = FCK.EditingArea.Window.document.body.innerHTML;
    document.form1.CONTENT.value=FORM_HTML;
    document.form2.formContent.value=FORM_HTML;
   	art.dialog.data("FORM_HTML",FORM_HTML);
    art.dialog.open(basePath+"logined/customForm/html_editor/viewForm.jsp",{
		title:"预览表单",
		width:800,
        height:450,
        lock : true,
	    opacity: 0.3,
		ok:true
	});
	
  }
}

function myclose()
{
   art.dialog.confirm('确定关闭表单设计器吗？', function () {
	       document.form1.CLOSE_FLAG.value="1";
			CloseWin();  
		}, function () {
		    return;
		});
}
function CloseWin() //这个不会提示是否关闭浏览器     
{     
window.opener=null;     
//window.opener=top;     
window.open("","_self");     
window.close();     
}   

function Load_Do()
{
}

//--- 单行输入框（新） ---
function exec_cmd(cmd) {
  var FCK = FCKeditorAPI.GetInstance('FORM_CONTENT'); //获得表单设计器的顶层对象FCK，该方法定义位置fckeditorapi.js第47行 by dq 090521
  FCK.Focus();
  FCK.Commands.GetCommand(cmd).Execute(); //仿照fcktoolbarbutton.js第71行的写法 by dq 090521
}


function checkClose()
{   
  if(event.clientX>document.body.clientWidth-20 && event.clientY<0||event.altKey)
    window.event.returnValue='您确定退出表单设计器吗?';   
}
function getAllControlName(str){
	//var 
}
</script>
<style type="text/css">
.Header { COLOR:#383838; FONT-WEIGHT: bold; FONT-SIZE: 9pt; background:#c4de83; line-height:21px;}
.Data   { BACKGROUND: #FFFFFF;COLOR:#000000;}
</style>
</head>
<body class="bodycolor" leftmargin="0" topmargin="0" onbeforeunload="checkClose();" onLoad="Load_Do();">

<table width="100%" height="100%" class="TableBlock" align="center">
 <tr bgcolor="#DDDDDD">
   <td class="Header" colspan="2" height="20">
    &nbsp;<img src="${ctx }/logined/customForm/html_editor/images/dot3.gif" align="absmiddle" /> 表单智能设计器：首先，将网页设计工具或Word编辑好的表格框架粘贴到表单设计区。然后，创建表单控件。
   </td>
 </tr>
 <tr bgcolor="#DDDDDD">
  <td bgcolor="#DDDDDD" width="100%" height="660" valign="top">
	<textarea style="display:none;" id="FORM_CONTENT" name="FORM_CONTENT"></textarea><input type="hidden" id="FORM_CONTENT___Config" value="" style="display:none" />
<iframe id="FORM_CONTENT___Frame" src="${ctx }/logined/customForm/html_editor/editor/fckeditor.html?InstanceName=FORM_CONTENT&amp;Toolbar=Default" width="100%" height="100%" frameborder="0" scrolling="no" style="border: 0pt none ; margin: 0pt; padding: 0pt; background-color: transparent; background-image: none; width: 100%; height: 100%;"></iframe>   
</td>
   <td valign="top" align="center">
     <table width="120" border="0" cellpadding=0 cellspacing=0 class="TableBlock" align="center">
      <tr class="TableHeader"><td align="center">表单控件</td></tr>
      <tr class="TableData">
      	<td align="center">
      	<button class="btnTool" onClick="exec_cmd('td_textfield')"><img src="${ctx }/logined/customForm/html_editor/images/form/textfield.gif"> 单行输入框</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_textarea')"><img src="${ctx }/logined/customForm/html_editor/images/form/textarea.gif"/> 多行输入框</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_listmenu')"><img src="${ctx }/logined/customForm/html_editor/images/form/listmenu.gif"/> 下拉菜单</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_radio')"><img src="${ctx }/logined/customForm/html_editor/images/form/radio.gif"/> 单选框</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_checkbox')"><img src="${ctx }/logined/customForm/html_editor/images/form/checkbox.gif"/> 复选框</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_calendar')"><img src="${ctx }/logined/customForm/html_editor/images/form/calendar.gif"/> 日历控件</button><br/>
        <!--update by 贾永强，把部门人员控件拆分为部门控件和人员控件-->
        <button class="btnTool" onClick="exec_cmd('td_user')"><img src="${ctx }/logined/customForm/html_editor/images/user.gif"/> 人员控件</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_group')"><img src="${ctx }/logined/customForm/html_editor/images/user_group.gif"/> 部门控件</button><br/>
        <button class="btnTool" onClick="exec_cmd('td_advice')"><img src="${ctx }/logined/customForm/html_editor/images/edit.gif"/> 审批意见控件</button><br/>
        <!-- <button class="btnTool" onClick="exec_cmd('ref_processInstance')"><img src="images/save_file.gif"/> 关联流程</button><br/> -->
        <button class="btnTool" onClick="exec_cmd('td_calcu')"><img src="${ctx }/logined/customForm/html_editor/images/form/calc.gif"/> 计算控件</button><br>
        <button class="btnTool" onClick="exec_cmd('td_calendar_range')"><img src="${ctx }/logined/customForm/html_editor/images/form/calendar.gif"/> 日期范围控件</button><br>
    
        </td>
        </tr>
     </table>
     <table width="120" border="0" cellpadding=0 cellspacing=0 class="TableBlock btnControl" align="center">
      <tr class="TableHeader"><td align="center">保存与退出</td></tr>
      <form action=""  method="post" name="form1">
      <tr class="TableData">
      	<td align="center">
        <button class="btnControl" onClick="send();return false;"><img src="${ctx }/logined/customForm/html_editor/images/save.png"/>保存表单</button><br/>
        <button class="btnControl" onclick="view();return false;"><img src="${ctx }/logined/customForm/html_editor/images/zoom_in.png" />预览表单</button><br/>
        <!-- <button class="btnControl" onClick="send(1);return false;">生成版本</button><br/> -->
        <button class="btnControl" onClick="myclose();return false;"><img src="${ctx }/logined/customForm/html_editor/images/email_back.gif"  />关闭设计器</button>
        </td>
      </tr>
      <textarea style="display:none"  name="CONTENT"  id="CONTENT"></textarea>
      <input type="hidden" name="CLOSE_FLAG"  value="" />
      <input type="hidden" name="FORM_ID" id="FORM_ID"  value="" />
      <input type="hidden" name="itemName" id="itemName"   value="${FORM_ID }" />
      </form>
    </table>

      </form>
      <form action="${ctx}logined/customForm/html_editor/viewForm.jsp" method="post" name="form2" target="_blank">
		 <input type="hidden" name="formContent" id="formContent" />
	  </form>
   </td>
 </tr>

</table>

</body>
</html>