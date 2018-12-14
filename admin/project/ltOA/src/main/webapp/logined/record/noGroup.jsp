<%--
功能:人事档案 新建和修改页面
版本:1.0
开发人员: 汤波涛
创建日期: 2013-03-22
修改日期: 2013-03-22
修改人员: 汤波涛
修改列表:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>请选择部门</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/head.jsp"/>
</head>
<body style="height:400px;overflow: hidden;">
<div class="notice" style="margin-top:80px">
   <!--   <h2 class="titbg"></h2>-->
    <div class="txtContent">
        <!--  <em class="nodata"></em>--><p>请选择部门</p>
        <div class="clear"></div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        window.parent.frameResize();
    })
</script>
</body>
</html>