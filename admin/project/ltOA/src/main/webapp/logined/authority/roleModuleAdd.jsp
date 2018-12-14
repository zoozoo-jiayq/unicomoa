<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限维护</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/authority.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	html,body{
		height:100%;
		width:100%:
	}
	body{
		overflow:auto;
	}
</style>
<script type="text/javascript">
var basePath="${ctx}";
	function goback(){
		window.parent.location.href=basePath +"logined/authority/roleList.jsp";
	}
</script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/authority/roleModuleAdd.js"></script>
</head>
<body style="overflow:auto;overflow-x:auto;">
<input type="hidden" name="roleId" id="roleId" value="${param.roleId}"/>
<div class="anthority">
		<div class="title_an">
			<span class="name">功能权限维护</span>
				<c:if test="${requestScope.roleInfo.roleType!=0 || (requestScope.roleInfo.roleType==0 && requestScope.roleInfo.roleCode!='admin')}">
					</c:if>
				<input id="addRoleModule" name="addRoleModule" type="button" value="保  存" class="formButton_green" />
		<input id="cancelRoleModule" name="cancelRoleModule" type="button" class="formButton_grey" onclick="goback();" value="返  回" />
		</div>
		<table border="0" cellpadding="0" cellspacing="5" class="access" style="width:100%" id="moduleTable">
			<tbody>
			<tr>
				<!-- 一级菜单子模块 -->
				<c:forEach items="${requestScope.firstModuleList}" var="firstList">
					<c:if test="${firstList.parentId==0}">
						<td valign="top">
									<h2>
										<input type="checkbox" class="inptchkbx" id="oneCheckbox" name="firstChk" value='<c:out value="${firstList.moduleId}"/>' <c:if test="${firstList.isSelected}"> checked="checked" </c:if> />
			                            <c:out value="${firstList.moduleName}"/>
			                            <c:if test="${firstList.moduleType==2}"></c:if>
									</h2>
									<!-- 二级菜单子模块 -->
									<ul  class="subset">
										<c:forEach items="${requestScope.secondModuleList}" var="secondList">
											<c:if test="${secondList.parentId==firstList.moduleId}">
												<li>
							                        <input type="checkbox" class="inptchkbx" name="secondChk" value='<c:out value="${secondList.moduleId}"/>' <c:if test="${secondList.isSelected}"> checked="checked" </c:if> />
							                        <c:out value="${secondList.moduleName}"/>
							                        <c:if test="${secondList.moduleType==2}"></c:if>
							                    <!-- 三级菜单子模块 -->
							                    <ul>
													<c:forEach items="${requestScope.thirdModuleList}" var="thirdList">
														<c:if test="${thirdList.parentId==secondList.moduleId}">
															<li>
																<input type="checkbox" class="inptchkbx" name="thirdChk" value='<c:out value="${thirdList.moduleId}"/>' <c:if test="${thirdList.isSelected}"> checked="checked" </c:if> />
						                                        <c:out value="${thirdList.moduleName}"/>
						                                        <c:if test="${thirdList.moduleType==2}"></c:if>
						                                        <ul>
							                                        <!-- 四级功能子模块 -->
											                        <c:forEach items="${requestScope.fourthModuleList}" var="fourthList">
												                        <c:if test="${fourthList.parentId==thirdList.moduleId}">
												                            <li>
												                        		<input type="checkbox" class="inptchkbx" name="fourthChk" value='<c:out value="${fourthList.moduleId}"/>' <c:if test="${fourthList.isSelected}"> checked="checked" </c:if> />
											                                    <c:out value="${fourthList.moduleName}"/>
											                                    <c:if test="${fourthList.moduleType==2}"></c:if>
										                                    </li>
												                        </c:if>
											                        </c:forEach>
						                                        </ul>
					                                        </li>
					                                    </c:if>
									                </c:forEach>
								                </ul>
												</li>
											</c:if>
										</c:forEach>
									</ul>
						</td>
					</c:if>
				</c:forEach>
			</tr>
			</tbody>
		</table>
	</div>
</body>
</html>