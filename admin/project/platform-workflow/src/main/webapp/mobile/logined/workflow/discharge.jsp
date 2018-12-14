<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" manifest="<%=request.getContextPath() %>/mobile/workflow.manifest">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>撤销</title>
   <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx}mobile/css/style.css"/>
</head>
<body>
<input type="hidden" id="instanceId" value="<%=request.getParameter("instanceId") %>"/>
<div class="container-fluid">
    <form class="form-horizontal">
        <div class="container">
            <div class="block-item bt10">
                <textarea rows="7" placeholder="请输入撤销原因" id="advice"></textarea>
            </div>
        </div>
         <div class="buttonArea">
            <button class="btn btn-primary" id="sbm" type="button">撤销</button>
        </div>

    </form>
</div>
</body>
<script type="text/javascript">
	
	$("#sbm").click(function(){
		$("#sbm").attr("disabled",true);
		var instanceId = $("#instanceId").val();
		var userId = h5Adapter.getItemValue("currentUserId");
		var advice = $("#advice").val();
		if(advice!=null&&advice!=""&&advice.length>300){
			h5Adapter.tips("撤销原因字数不超过300");
			$("#sbm").attr("disabled",false);
			return false;
		}
		$.ajax({
			url:basePath+"baseworkflow/approve.c?_clientType=wap",
			type:"post",
			data:{"instanceId":instanceId,"approveResult":"2","userId":userId,"advice":advice},
			success:function(msg){
				if(msg.indexOf("100||")==0){
					window.location.href= basePath+"mobile/logined/workflow/myStartList.jsp?_clientType=wap";
				}else{
					$("#sbm").attr("disabled",false);
				}
			}
		});
	});
</script>
</html>