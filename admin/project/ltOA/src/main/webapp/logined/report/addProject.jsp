<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../common/taglibs.jsp"/>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报表基础数据维护-项目添加</title>
<link href="${ctx}/css/qj-css.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../common/head.jsp"/>
  <script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/logined/report/addProject.js"></script>

 
</head>
<body>
<div class="input">
		 <div class="pageTitle"><em class="iconAdd">新建项目字段</em></div>
		 <form   method="post"  name="form1" id="form1">
		<table width="100%" border="0" cellpadding="0" cellspacing="1"  class="inputTable">
    	<tr>
        	<th>项目名称：</th><td><input  id="projectName" name="projectName" maxlength="50" type="text" class="formText" style="width: 225px" valid="required" errmsg="report.projectName_not_null" /> <span class="requireField">*</span></td>
        </tr>
    	<tr>
        	<th>备注：</th><td><textarea id="note" name="note" cols="30"></textarea></td>
        </tr>
    </table>
    </form>
		<div class="buttonArea"> <span class="mainButton">
		<input hideFocus="" value="确 定" type="submit"  id="addProject" name="addProject">
		</span> &nbsp;&nbsp; <span class="mainButton">
		<input hideFocus="" id="backBDList" value="返 回" type="button">
		</span> </div>
		
</div>
</body>
</html>