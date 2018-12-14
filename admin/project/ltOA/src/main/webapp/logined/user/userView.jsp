<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人员详情</title>
<jsp:include page="../../common/flatHead.jsp" />

<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
</head>
<body>
 <form action="#" id="userForm">
<input type="hidden" id="userId" value="${requestScope.user.userId}"/>
<input type="hidden" id="roleId" value="${requestScope.roleIds}"/>
<input type="hidden" id="assistId" value="${requestScope.assistIds}"/>
<input type="hidden" id="groupId" value="${requestScope.groupId}"/>
<input type="hidden" id="defaultgroupId" value="${requestScope.groupId}"/>

<div class="formPage">
  <div class="formbg">
    <div class="big_title">人员信息</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><label>姓名：</label></th>
          <td> ${requestScope.user.userName}</td>
          <th>头像：</th>
          <td  rowspan="3">
          	<dl class="myphoto">
              	<dt>
              		<c:if test="${requestScope.user.photo == '' || requestScope.user.photo == null }">
	              		<img  src="${ctx }flat/plugins/form/skins/default/meeting.png">
              		</c:if>
              		<c:if test="${requestScope.user.photo != '' && requestScope.user.photo != null}">
		    			<img src="${ctx}filemanager/prevViewByPath.action?filePath=${requestScope.user.photo}" id="photoImg" width="107" height="107"/>
	                </c:if>
              	</dt>
            </dl>
          </td>
        </tr>
        <tr>
          <th><label>单位/部门：</label></th>
          <td>${requestScope.groupName} </td>
        </tr>
        <tr>
        	<th><label>手机号码：</label></th>
            <td>${requestScope.user.phone}</td>
        </tr>
        <tr>
        	<th><label>性别：</label></th>
            <td> <s:if test="#request.user.sex==1">男</s:if> 
                 <s:if test="#request.user.sex==0">女</s:if></td>
          <th><label>别名：</label></th>
          <td>${requestScope.user.alterName}</td>
        </tr>
        <tr>
        	<th><label>生日：</label></th>
          <td><fmt:formatDate value="${requestScope.user.birthDay }" pattern="yyyy-MM-dd"/></td>
          <th><label>电子邮件：</label></th>
          <td>${requestScope.user.email} </td>
        </tr>
        <tr>
          <th><label>办公电话：</label></th>
          <td>${requestScope.user.officeTel} </td>
          <th><label>职务：</label></th>
          <td>${user.job }</td>
        </tr>
       <%--  <tr>
          <th><label>排序号：</label></th>
          <td colspan="3">${requestScope.user.orderIndex}</td>
        </tr> --%>
        <tr>
          <th><label>个性签名：</label></th>
          <td>
          
						<c:if test="${user.signType == 0}">不启用</c:if>
                        <c:if test="${user.signType == 1}">文字签名</c:if>
                        <c:if test="${user.signType == 2}">
                        	<img id="imgContent" border="0" width="120px" height="40px" src="${ctx}filemanager/prevViewByPath.action?filePath=${user.signUrl}" />
                        	 <input type="hidden" id="imgSignUrl" value="${user.signUrl}"/>
                        </c:if>
          </td>
          <th><label>集团短号：</label></th>
          <td>${user.workNo }</td>
        </tr>
        <tr>
          <th><label>号码隐藏：</label></th>
          <td>
          	<c:if test="${user.hidePhone == null || user.hidePhone == '0' }">关闭</c:if>
          	<c:if test="${user.hidePhone == '1' }">开启</c:if>
          </td>
          <th><label>高管模式：</label></th>
          <td>
			<c:if test="${user.isLeader == null || user.isLeader == '0' }">关闭</c:if>
          	<c:if test="${user.isLeader == '1' }">开启</c:if>
		  </td>
        </tr>
      </table>
    </div><%--
     <div class="big_title">用户权限</div>
    <div class="content_form">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
          <tr>
            <th>管理范围：</th>
            <td colspan="3">本部门及子部门 </td>
          </tr>
        </table>
    </div>
    --%><div class="big_title" style="display:none;">控件信息</div>
    <div class="content_form" style="display:none;">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
          <tr>
            <th>印章控件：</th>
            <td><c:if test="${user.sinWidget==0}">不启用</c:if><c:if test="${user.sinWidget==1}">启用</c:if></td>
            <th>OFFICE控件：</th>
            <td><c:if test="${user.officeWidget==0}">不启用</c:if><c:if test="${user.officeWidget==1}">启用</c:if></td>
          </tr>
          <tr>
            <th>套打控件：</th>
            <td colspan="3"><c:if test="${user.taoDa==0}">不启用</c:if><c:if test="${user.taoDa==1}">启用</c:if></td>
          </tr>
        </table>
    </div>
  </div>
</div>
</form>
</body>
</html>
