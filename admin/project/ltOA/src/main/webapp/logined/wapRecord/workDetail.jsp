<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>工作详情页</title>
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
			<span class="title" >工作详情</span>
	</div>
		<div class="container-wrapper">	
			<div class="reward-detail detail-list top5">
				<ul>
					<li>
						<span class="title">姓名：</span>
						<span class="txt block">${recordWork.userInfo.userName}</span>
					</li>
					<li>
						<span class="title">工作单位：</span>
						<span class="txt block">${recordWork.workUnit}</span>
					</li>
					<li>
						<span class="title">开始时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordWork.startDate}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
					<li>
						<span class="title">结束时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordWork.endDate}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
					<li>
						<span class="title">行业类别：</span>
						<span class="txt block">
							${empty recordWork.industryCategory?"--":recordWork.industryCategory}
						</span>
					</li>
					<li>
						<span class="title">所在部门：</span>
						<span class="txt block">${recordWork.department}</span>
					</li>
					<li>
						<span class="title">担任职务：</span>
						<span class="txt block">
							${recordWork.position}
						</span>
					</li>
					<li>
						<span class="title">证明人：</span>
						<span class="txt block">
							${empty recordWork.reterence?"--":recordWork.reterence}
						</span>
					</li>
					<li>
						<span class="title">工作内容：</span>
						<span class="txt block">
							${empty recordWork.jobContent?"--":recordWork.jobContent}
						</span>
					</li>
					<li>
						<span class="title">主要业绩：</span>
						<span class="txt block">
							${empty recordWork.achievement?"--":recordWork.achievement}
						</span>
					</li>
					<li>
						<span class="title">离职原因：</span>
						<span class="txt block">
							${empty recordWork.leavingReasons?"--":recordWork.leavingReasons}
						</span>
					</li>
					<li>
						<span class="title">备注：</span>
						<span class="txt block">
							${empty recordWork.remark?"--":recordWork.remark}
						</span>
					</li>
					<li>
						<span class="title">登记时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordWork.createTime}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
				</ul>
				<div class="main-title">附件</div>
				<div class="doc-list">
					<!-- <p class="row-doc">学习文件.txt</p>    
					<p class="row-doc">学习文件.txt</p> -->
					<ul id="attachmentList">
                                <c:forEach items="${recordWork.attachmentList}" var="attachment">
                                    <p onclick="downFileById(${attachment.id},this)">${attachment.attachName}</p>
                                </c:forEach>
                               <c:if test="${empty recordWork.attachmentList }">无</c:if>
                   </ul>
				</div>
				<!--end .doc-list-->
			</div>
			<!--end .reward-detail-->
		</div>
</body>
</html>