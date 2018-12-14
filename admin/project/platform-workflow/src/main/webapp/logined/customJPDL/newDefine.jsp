<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建流程</title>
<jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<style type="text/css">
  @import "${ctx}plugins/tree/ztree/zTreeStyle/zTreeStyle.css";
</style>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.inputTable th { width:125px;}
.inputTable td .formText { width:60%}
</style>

<script type="text/javascript" src="${ctx}/js/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/common/jquery.validate.methods.js"></script>
<script charset="utf-8" src="${ctx}plugins/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}plugins/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/ztree-wrapper.js"></script>
</head>
<body>
<input type="hidden" id="type" value="${type }"/>
	<input type="hidden" id="tabName" value="${tabName}"/>
	<form action="${ctx}/workflow/manager!saveProcess.action" method="post" id="newForm">
	<input type="hidden"  name="type" value="${type }"/>
		<input type="hidden" name="processAttribute.companyId" value="${adminUser.companyId}"/>
<div class="formPage"  >
	<div class="formbg">
		<div class="big_title">新建流程</div>
			<div class="content_form">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
				<tr>
						<th><em class="requireField">*</em><label>流程名称</label></th>
						<td><input name="processAttribute.processName" style="width:54%" maxlength="30" valid="required" errmsg="customJpdl.process_name_not_null" id="processName" type="text" class="formText" />
								</td>
						<th><em class="requireField">*</em><label>表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单</label></th>
						<td>
						<input type="text" id="formCategory" valid="required" errmsg="customJpdl.form_not_null" class="formText iconTree" readonly/>
						<input type="hidden" name="processAttribute.formId"	 id="formCategory_hidden"/>
						<a href="#" id="prepView">预览表单</a></td>
				</tr>
				<tr>
						<th><em class="requireField">*</em><label>流程分类</label></th>
						<td><select id="categoryIdSelect" name="processAttribute.categoryId">
						   <option value="-1">-请选择-</option>
							<c:forEach items="${categories}" var="cate">
							<option value="${cate.categoryId}">${cate.categoryName}</option>
						</c:forEach>
							
						</select></td>
						<th>允许附件</th><td><select name="processAttribute.isAttach">
										<option value="1">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
										<option value="0">否</option>
								</select></td>
				</tr>
				<tr>
						<th><em class="requireField">*</em>文号表达式</th>
						<td><input style="width:54%" name="processAttribute.processNameExpr" valid="required" errmsg="customJpdl.process_name_expr_not_null" type="text" class="formText" size="30" value="{F}[{Y}-{M}-{D} {H}:{I}:{S}]" />
								<a  href="#" onclick="showHide('showTxt')">查看说明</a>
								<div class="l22" id="showTxt" style="display:none"> 文号表达式说明
										<p class="l22">表达式中可以使用以下特殊标记</p>
										<p class="l22"> {Y}：表示年 </p>
										<p class="l22">{M}：表示月 </p>
										<p class="l22">{D}：表示日 </p>
										<p class="l22">{H}：表示时 </p>
										<p class="l22">{I}：表示分 </p>
										<p class="l22">{S}：表示秒 </p>
										<p class="l22">{F}：表示流程名 </p>
										<p class="l22">{FS}：表示流程分类名称 </p>
										<p class="l22">{U}：表示用户姓名 </p>
										<p class="l22">{DE}：表示部门 </p>
										<p class="l22">{N}：表示编号，通过编号计数器取值并自动增加计数值 </p>
										<p class="l22">例如，表达式为：成建委发[{Y}]{N}号 </p>
										<p class="l22">同时，设置自动编号显示长度为4 </p>
										<p class="l22">则自动生成的文号如下：成建委发[2006]0001号 </p>
										<p class="l22">例如，表达式为：BH{N} </p>
										<p class="l22">同时，设置自动编号显示长度为3 </p>
										<p class="l22">则自动生成的文号如下：BH001 </p>
										<p class="l22">例如，表达式为：{F}流程（{Y}年{M}月{D}日{H}:{I}）{U} </p>
										<p class="l22">自动生成文号如：请假流程（2006年01月01日10:30）张三 </p>
										<p class="l22">系统默认按以下格式，如： </p>
										<p class="l22">请假流程(2006-01-01 10:30:30) </p>
								</div></td>
								<th><em class="requireField">*</em>编号计数器</th>
						<td valign="top" ><input name="processAttribute.processNameBeginNum" style="width: 80px" maxlength="4" valid="required|isNumber|range" min="0" errmsg="customJpdl.process_name_expr_begin_not_null|customJpdl.process_name_expr_begin_int" type="text" class="formText"  value="1" />
								<em class="gray_9">用于表达式编号标记 </em></td>
				</tr>
				<tr>
					<th><em class="requireField">*</em>编&nbsp;号&nbsp;位&nbsp;数</th>
						<td><input style="width: 80px" name="processAttribute.processNamLength" valid="required|isNumber|range" errmsg="customJpdl.process_name_expr_length_not_null|customJpdl.process_name_expr_length" min="0" type="text" class="formText" maxlength="1" value="4"/>
								<em class="gray_9">为0表示按实际编号位数显示</em></td>
						<th>是否允许修改</th>
						<td ><SELECT name="processAttribute.processNameCanupdate">
										<OPTION selected value=1>允许修改</OPTION>
										<OPTION value=0>不允许修改</OPTION>
										<!--
										<OPTION value=2>仅允许输入前缀</OPTION>
										<OPTION value=3>仅允许输入后缀</OPTION>
										<OPTION value=4>仅允许输入前缀和后缀</OPTION>-->
								</SELECT></td>		
				</tr>

               
         <c:if test="${type==2}">
					<tr>
	                    <th><em class="requireField">*</em>办理人选择方式</th>
	                        <td colspan="1">
	                                <select name="processAttribute.selectUserMode">
	                                        <option value="1"  <c:if test="${processAttribute.selectUserMode==1 }">selected</c:if>  >按人员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
	                                        <option value="2"  <c:if test="${processAttribute.selectUserMode==2 }">selected</c:if>>按部门</option>
	                                </select>
	                        </td>
	                    <th>是否公文流程</th>
	                        <td colspan="1">
	                                <select name="processAttribute.isDom" id="dom">
	                                        <option value="0"  <c:if test="${processAttribute.isDom==0 }">selected</c:if>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
	                                        <option value="1"  <c:if test="${processAttribute.isDom==1 }">selected</c:if>>是</option>
	                                </select>
	                        </td>
	                </tr>
	                <tr id="taohong">
	                   <th>套红模板</th>
	                   <td colspan="3"><select name="processAttribute.redTemplate"  style="width:15em">
						 <option value="">请选择</option>
							   <c:forEach items="${docTemplates}" var="doct">
							   <option   <c:if test="${processAttribute.redTemplate==doct.docTemplateId}">selected</c:if> value="${doct.docTemplateId}" >${doct.docTemplateName}</option>
								</c:forEach>
						</select></td>
	                </tr>
        </c:if>        

				<tr><th>说明内容</th><td colspan="3"><textarea name="processAttribute.directions" maxlength="100"   onKeyUp="if(this.value.length >100) this.value=this.value.substr(0,100)"   style="behavior:url(maxlength.htc)"  class="formTextarea"  id="directions"></textarea></td></tr>
		</table>
		</div>
		</div>
		<div class="buttonArea"><input hideFocus="" value="确  定" type="button" id="sure" class="formButton_green" />&nbsp;&nbsp;&nbsp;&nbsp;<input hideFocus="" id="cancel" value="取 消" type="button" class="formButton_grey"></div>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:203px; height:300px;"></ul>
</div>
<div id="menuContentDoc" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemoDoc" class="ztree" style="margin-top:0; width:180px; height:300px;"></ul>
</div>
</form>
<script type="text/javascript" src="${ctx}/js/logined/customJPDL/newDefine.js"></script>
</body>
</html>