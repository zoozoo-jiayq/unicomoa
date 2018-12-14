<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%
       String type = request.getParameter("webSignType")!=null?request.getParameter("webSignType"):"1";
     %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="publicDomHead.jsp" />
</head>
<body>
<div class="list">
<input type="hidden"  id="webSign"></input>
	<div class="pageTitle"><em class="iconAdd">选择签章</em></div>
	<table width="100%" border="0" cellpadding="0" cellspacing="1"  class="inputTable">
        <tbody id="webSignList">
        </tbody>
	</table>
 </div>
</body>
<script type="text/javascript">
var webSignType = <%=type%>;
	//初始化印章列表
	(function(){
		$.get(basePath+"/dom/public!getWebSignList.action?webSignType="+webSignType,function(argument) {
			// body...
			if(argument && argument.length>0){
				for (var i = argument.length - 1; i >= 0; i--) {
					var temp = argument[i];
					//$("<option>",{"value":temp.url,"text":temp.name}).appendTo($("#webSign"));
					var str="  <tr><td><label class=\"radio\"><input name =\"webSignRadio\" value=\""+temp.url+"\"  onclick=\"setSignVal(this);\"  class=\"inptradio\" type=\"radio\"   >"+temp.name+"</label></td></tr>";
				     $("#webSignList").append(str);
				};
			}
		},"json");
	})();
	
	
	function setSignVal(obj){
	  $("#webSign").val(obj.value);
	}
</script>


</html>