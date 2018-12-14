<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>打印预览</title>
    <jsp:include page="../../../common/head.jsp"/>
    <style media="print" type="text/css"> 
    .Noprint{display:none;} 
    </style>  
</head>
<body style="background:#fff;">
    <input type="hidden" id="instanceId" value='<%=request.getParameter("instanceId")%>' />
<input type="hidden" id="taskName" value='<%=request.getParameter("taskName")%>'/>
<input type="hidden" id="documentExtId" value='<%=request.getParameter("documentExtId")%>'/>
<!--查看已办接的任务-->
<input type="hidden" id="source" value='<%=request.getParameter("source")%>'/>
<input type="hidden" id="taskId" value='<%=request.getParameter("taskId")%>'/>

<input type="hidden" id="_advice"   />
<input type="hidden" id="adviceId"    />
<input type="hidden" id="currentUserName"  value="<s:property value="#session.adminUser.userName"/>" />
<input type="hidden" id="currentUserId"  value="<s:property value="#session.adminUser.userId"/>" />

    <div style="margin:50px auto;text-align: center">
    <form id="customForm" >

    </form>
    </div>
</body>
<div class="buttonArea" align="center"><input class="formButton_gray Noprint" onclick='webPrint();' type="button" value="打 印"/></div>
<script type="text/javascript" src="${ctx}/js/logined/publicDom/dispatch/formCommon.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $(".hideleft").click(function(){
        $(".leftShow").hide();
        $(".leftHide").show();
        $(".mainpage").addClass("mainpageHide");
        });//隐藏
        $(".leftHide").click(function(){
        $(".leftShow").show();
        $(".leftHide").hide();
        $(".mainpage").removeClass("mainpageHide");
        });//展开
    $("#more").click(function(){
      $(".moreMemu").slideToggle("slow")
    });
});

async.series({
    //加载表单源代码
    init:function(callback){
        loadFormSource(callback);
    },
    //加载数据
    loadData:function(callback){
        loadFormData(callback);
    },
    generateWidget:function(callback){
        initWidget(callback);
    }
},function(err,result){
}); 

var HKEY_Root,HKEY_Path,HKEY_Key; 
HKEY_Root="HKEY_CURRENT_USER"; 
HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
//设置网页打印的页眉页脚为空 
function PageSetup_Null() 
{ 
    try 
    { 
    var Wsh=new ActiveXObject("WScript.Shell"); 
    HKEY_Key="header"; 
    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
    HKEY_Key="footer"; 
    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
    } 
    catch(e) 
    {
    	alert(e);
    } 
}

function webPrint(){
	PageSetup_Null();
	document.execCommand("print");
}

</script>
<jsp:include page="jsp_ntko_sign.jsp"></jsp:include>
</html>