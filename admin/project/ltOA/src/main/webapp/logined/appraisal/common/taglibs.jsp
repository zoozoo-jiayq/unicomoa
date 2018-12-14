<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="req" class="cn.com.qytx.oa.util.RequestParamter" scope="session" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path+"/" ;
    request.setAttribute("ctx", basePath);
    request.setAttribute("version", "3.1");
%>
<script type="text/javascript">
	var basePath = "${ctx}";
</script>

<link rel="stylesheet" href="${ctx}/logined/appraisal/css/jxkh_style.css">
<script src="${ctx}/logined/appraisal/common/jquery-1.8.0.min.js"></script>