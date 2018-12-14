<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>打印预览</title>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/common.js"></script>
<script type="text/javascript" src="${ctx }js/common/async.js"></script>
<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js" ></script>
    <style media="print" type="text/css"> 
    .Noprint{display:none;} 
    </style>
</head>
<body>
	<input type="hidden" id="processInstanceId" value="${processInstanceId}"/>
	<input type="hidden" id="instanceId" value="${processInstanceId}"/>
	<input type="hidden" id="processId" value="${processId}" />
<div style="margin:50px auto;width:595px;"  id="form" >
${formSource }
</div>
<div class="formPage">
    <div class="buttonArea" style="text-align:center;"><input class="formButton_grey Noprint" onclick='webPrint();' type="button" value="打 印"/></div>
 </div>   
    <script type="text/javascript">
        setFormDate($("#instanceId").val());
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
	        	
	        } 
        }
        
        function webPrint(){
        	PageSetup_Null();
        	document.execCommand("print");
        }
        async.series({
    		init:function(callback){
    			readonlyForm();
    			callback();
    		},
    		loadData:function(callback){
    			setFormDate(callback);
    		},
    		generateWidget:function(callback){
    			initWidget(callback);
    		}
    	},function(err,result){
    	}); 
    </script>
</body>
</html>