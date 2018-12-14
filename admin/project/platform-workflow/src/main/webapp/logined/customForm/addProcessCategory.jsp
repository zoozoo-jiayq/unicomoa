<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建流程分类</title>
 <jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/addProcessCategory.js" ></script>
<style type="text/css">
.inputTable th{width:60px;}
</style>
</head>
<body class="bg_white">
<jsp:include page="shareProcessCategory.jsp" />
<!-- 隐藏域 -->
<div class="elasticFrame formPage">
<form action="#" id="formCategoryform">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr>
                            <th><label>排序号：</label></th>
                            <td><input onkeyup="testNum(this);return false;" valid="required" errmsg="formcategory.cate_order_not_null" maxlength="2" type="text" class="formText" size="5" name="formProcessIndex" id="formProcessIndex"/></td>
                      </tr>
                      <tr>
                            <th><label>名称：</label></th>
                            <td><input name="input" type="text" maxlength="20" valid="required" errmsg="formcategory.cate_name_not_null" class="formText" size="50" name="formProcessName" id="formProcessName"/>
								</td>
                      </tr>
              </tbody>
           <%
				String type = request.getParameter("type");
			    if(type.equals("12")){
			    	out.write("<tr>");
			    	out.write("<th style=\"width: 80px\"><span class=\"requireField\">*</span><label>收文/发文</label></th>");
			    	out.write("<td>");
			    	out.write("<label><input type='radio' value='1' name='domType' checked/>收文</label>&nbsp;&nbsp;<label><input type='radio' value='2' name='domType'/>发文</label>");
			    	out.write("</td>");
			    	out.write("</tr>");
			    }
				%>
           </table>
           </form>
 <input name="saveProcessCategory" style="display:none" id="saveProcessCategory" value="确 定" type="button" class="formButton_green"   onclick="saveProcessCategoryInfo()"/>
</div>
</body>
</html>
