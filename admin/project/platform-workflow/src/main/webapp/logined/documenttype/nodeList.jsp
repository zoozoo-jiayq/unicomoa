<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/handle.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/smallTab/skins/smallTab_default.css" rel="stylesheet" type="text/css"/>
</head>
<body >
 
<div class="formPage" id="list">
    <div class="formbg">
        <div class="big_title">管理流程步骤(流程名称：${docType.doctypeName })</div>
        <div class="content_form">
          <p class="gray_9 pt5">请设定好各步骤的可写字段和经办权限（此经办权限是经办人员、经办部门、经办角色的合集）</p>
            <table cellpadding="0" cellspacing="0"  class="pretty dataTable">
						<thead>
								<tr>
										<th class="num">序号</th>
										<th width="180px">名称</th>
										<th width="250px">属性设置</th>	
								</tr>
						</thead>
						<tbody>
								<c:forEach items="${nodes}" var="node" varStatus="sta">
										<tr class=<c:if test="${sta.index%2 ==0}">odd</c:if><c:if test="${sta.index%2 ==1}">even</c:if> >
											<td>${sta.index+1 }</td>
											<td class="data_l"  width="180px">${node.nodeName}</td>
											<td><a href="<%=request.getContextPath()%>/documentType/setUpNode.action?nodeId=${node.id}&doctypeId=${doctypeId}">设置</a>
											</td>
										</tr>
								</c:forEach>
						</tbody>
				</table>
        </div>
      </div>
      <div class="buttonArea"> 
			<input onclick="javascript:window.top.closeCurrentTab();" class="formButton_grey" value="关闭" type="button"/>
        </div>
</div>

</body>
</html>