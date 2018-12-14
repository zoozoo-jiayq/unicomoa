<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>离职详情页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!--css-->
    <%
    String basePath = "http://192.168.10.28:8080/qytx-oa-mysql/" ;
    request.setAttribute("ctx", basePath);
%>  
<link href="${ctx}css/wapRecord/wapRecord.css" rel="stylesheet">
<script src="../../js/jquery-1.8.3.min.js"></script> 
<script type="text/javascript"src="${ctx}/js/logined/record/commonUpload.js"></script>
<script>
  var basePath="${ctx}";
</script>
	
	
</head>
<input type="hidden" id="userId" value="1">
<input type="hidden" id="treeType" value="1">
<body class="bg-gray">
    <div class="header-bar">
			<span id="backImg" onclick="javascript:window.history.back()"><i class="icons icon-18x18 icon-return" ></i></span>
			<span class="title" >离职详情</span>
	</div>
		<div class="container-wrapper">	
			<div class="reward-detail detail-list top5">
				<ul>
					<li>
						<span class="title">姓名：</span>
						<span class="txt block">${recordLeave.userInfo.userName}</span>
					</li>
					<li>
						<span class="title">担任职务：</span>
						<span class="txt block">${empty recordLeave.position?"--":recordLeave.position}</span>
					</li>
					<li>
						<span class="title">离职类型：</span>
						<span class="txt block">${empty recordLeave.leaveTypeString?"--":recordLeave.leaveTypeString}</span>
					</li>
					<li>
						<span class="title">申请日期：</span>
						<span class="txt block"> ${empty recordLeave.applyDate?"--":""}
                        <fmt:formatDate value="${recordLeave.applyDate}" pattern="yyyy-MM-dd"/></td></span>
					</li>
					<li>
						<span class="title">拟离职日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordLeave.intendedToLeaveDate}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
					<li>
						<span class="title">实际离职日期：</span>
						<span class="txt block"><fmt:formatDate value="${recordLeave.actualLeaveDate}" pattern="yyyy-MM-dd"/></span>
					</li>
					<li>
						<span class="title">工资截止日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordLeave.wageCutoffDate}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
					
					<li>
						<span class="title">离职部门：</span>
						<span class="txt block">
							${empty recordLeave.leaveDepartmentName?"--":recordLeave.leaveDepartmentName}
						</span>
					</li>
					<li>
						<span class="title">当月薪资(元)：</span>
						<span class="txt block">
							${empty recordLeave.leaveTheMonthWage?"--":recordLeave.leaveTheMonthWage}
						</span>
					</li>
					<li>
						<span class="title">去向：</span>
						<span class="txt block">
							 ${empty recordLeave.whereabouts?"--":recordLeave.whereabouts}
						</span>
					</li>
					<li>
						<span class="title">离职手续办理：</span>
						<span class="txt block">
							  ${empty recordLeave.resignationProcedure?"--":recordLeave.resignationProcedure}
						</span>
					</li>
					<li>
						<span class="title">备注：</span>
						<span class="txt block">
							 ${empty recordLeave.remark?"--":recordLeave.remark}
						</span>
					</li>
					<li>
						<span class="title">离职原因：</span>
						<span class="txt block">
							 ${empty recordLeave.leaveReason?"--":recordLeave.leaveReason}
						</span>
					</li>
					<li>
						<span class="title">登记时间：</span>
						<span class="txt block">
							 <fmt:formatDate value="${recordLeave.createTime}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
				</ul>
				<div class="main-title">附件</div>
				<div class="doc-list">
					<!-- <p class="row-doc">学习文件.txt</p>    
					<p class="row-doc">学习文件.txt</p> -->
					<ul id="attachmentList">
                                <c:forEach items="${recordLeave.attachmentList  }" var="attach">
                                    <p onclick="downFileById(${attach.id},this);">${attach.attachName}${attach.attachPath}</p>
                                   	<br/>
                                </c:forEach>
                               <c:if test="${empty recordLeave.attachmentList }">无</c:if>
                   </ul>
				</div>
				<!--end .doc-list-->
			</div>
			<!--end .reward-detail-->
		</div>
</body>
</html>