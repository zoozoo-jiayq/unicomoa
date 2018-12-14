<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作成功</title>
</head>
<body>
<input type="hidden" id="processAttributeId" value="${processAttributeId}"/>
<input type="hidden" id="processAttributeName" value="${processAttribute.processName}"/>
<input type="hidden" id="option" value="${option}" />
</body>
<script type="text/javascript">
if($("#option").val()=="update"){
   
  // art.dialog.alert("操作成功!",function(){
      //window.location.href="${ctx}/workflow/manager!update.action?processAttributeId="+$("#processAttributeId").val();
      //window.location.href="${ctx}/workflow/manager.action?processAttributeId="+$("#processAttributeId").val();
      //window.top.closeCurrentTab();
  // });

	art.dialog({
			title : '消息',
			content : '操作成功！',
			icon : 'succeed',
			height : 109,
			width : 317,
			ok:function(){},
			close : function() {
				if(window.top && window.top.closeCurrentTab){
					window.top.closeCurrentTab();
				}else{
					window.close();
				}
			}
		});

	} else if ($("#option").val() == "copy") {
		art.dialog({
		   title:"消息",
		   content:"操作成功！",
		   width:320,
		   height:110,
		   icon:"succeed",
		   opacity:0.3,
		   ok:function(){},
		   close:function(){
				window.location.href = "${ctx}/workflow/manager.action?processAttributeId="
									+ $("#processAttributeId").val()+"&type=${type}";
							return true;
		   }
		});
	} else {
		art.dialog({
		   title:"消息",
		   content:"操作成功！",
		   width:320,
		   height:110,
		   icon:"succeed",
		   opacity:0.3,
		   ok:function(){},
		   close:function(){
				window.location.href = "${ctx}/workflow/manager.action?processAttributeId="
									+ $("#processAttributeId").val()+"&type=${type}";
							return true;
		   }
});				
	}
	
	
</script>
</html>