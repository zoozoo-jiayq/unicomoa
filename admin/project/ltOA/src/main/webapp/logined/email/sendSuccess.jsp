<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'emailTo.jsp' starting page</title>
		<link href="${ctx}css/base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}css/author_main.css" rel="stylesheet"
			type="text/css" />
		<jsp:include page="../../common/head.jsp" />
		<script type="text/javascript">
var basePath = "${ctx}";
</script>

  </head>
  
  <body style="text-align: center;">
    <h2>发送成功</h2>
    <a href="${ctx}logined/email/emailBodyCreate.action">再写一封</a>
  </body>
</html>
