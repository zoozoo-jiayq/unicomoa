<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门列表</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/systemManagement.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
</head>
<body>
	<div class="list">
		<!-- <div class="pageTitle">
			<em class="iconList">部门列表</em> 
		</div>-->
		<div class="newtable">
			<table class="pretty" cellpadding="0" cellspacing="0">
			    <thead>
					<tr>
						<th style="width:60%">部门</th>
						<th style="width:100px">部门主管</th>
						<th  style="width:100px">部门助理</th>
						<th  style="width:100px">主管领导</th>
						<th  style="width:100px">分管领导</th>
						<th  style="width:100px">电话</th>
						<th  style="width:40%">职能</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${requestScope.groupTree}" var="group" varStatus="status">
                        <tr class='<c:if test="${status.index%2==0}">old</c:if><c:if test="${status.index%2==1}">even</c:if>'>
							<td class="goodsCategoryName" style="text-align: left;"><c:choose>
									<c:when test="${group.grade==0}">
										<span class="Categorybg"><c:out value="${group.groupName}" /></span>
									</c:when>
									<c:otherwise>
										<span class="Categorybg" style="margin-left: ${(group.grade) * 30}px;"><c:out value="${group.groupName}" /></span>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${group.directorName}</td>
							<td>${group.assistantName}</td>
							<td>${group.topDirectorName}</td>
							<td>${group.topChangeName}</td>
							<td style="text-align: left;">${group.phone}</td>
							<td class="longTxt" title='<c:out value="${group.functions}"/>'><c:out value="${group.functions}"/></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>