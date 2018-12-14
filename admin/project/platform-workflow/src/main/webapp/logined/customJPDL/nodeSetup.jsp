<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑经办权限</title>
<jsp:include page="../../common/taglibs.jsp"/>
<jsp:include page="../../common/head.jsp"/>
</head>
<body>
    <form action="${ctx}/workflow/nodeManager!nodeSetup.action" method="post">
        <input type="hidden" name="nodeId" value="${node.id}"/>
            <input type="hidden" name="processAttributeId" value="${node.processAttribute.id}"/>
            <input type="hidden" name="type" value="${type}"/>
<div class="input_new">
<div class="pageTitle"><em class="iconAdd">步骤 :${node.nodeName } </em></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
            <th style="width:100px"><label>编辑正文<br /></label></th>
            <td>
                <c:if test="${node.editDoc==1}">
                <label><input type="radio" name="editDoc" checked value="1"/>允许</label>&nbsp;&nbsp;<label><input type="radio" name="editDoc"  value="2"/>禁止</label>
                </c:if>
                <c:if test="${node.editDoc==2}">
                <label><input type="radio" name="editDoc"  value="1"/>允许</label>&nbsp;&nbsp;<label><input type="radio" name="editDoc" checked value="2"/>禁止</label>
                </c:if>
            </td>
        </tr>
       
    </table>
    
    <div class="buttonArea">
        <input hideFocus="" value="确 定" type="submit" class="formButton_green"/>&nbsp;&nbsp;
        <input hideFocus="" onclick="window.history.back(); return false;" value="返 回" type="button" class="formButton_grey" />
        </div>
</div>
</form>
</body>
</html>