<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>学习详情页</title>
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
			<span class="title" >学习详情</span>
	</div>
		<div class="container-wrapper">	
			<div class="reward-detail detail-list top5">
				<ul>
					<li>
						<span class="title">姓名：</span>
						<span class="txt block">${recordLearn.userInfo.userName}</span>
					</li>
					<li>
						<span class="title">所学专业：</span>
						<span class="txt block">${recordLearn.major}</span>
					</li>
					<li>
						<span class="title">曾任干部：</span>
						<span class="txt block">${empty recordLearn.classCadre?"--":recordLearn.classCadre}</span>
					</li>
					<li>
						<span class="title">证明人：</span>
						<span class="txt block">${recordLearn.reterence}</span>
					</li>
					<li>
						<span class="title">所获证书：</span>
						<span class="txt block">${empty recordLearn.certificates?"--":recordLearn.certificates}</span>
					</li>
					<li>
						<span class="title">所获学历：</span>
						<span class="txt block">${eduLevel}</span>
					</li>
					<li>
						<span class="title">所获学位：</span>
						<span class="txt block">${empty eduQualifications?"--":eduQualifications}</span>
					</li>
					<li>
						<span class="title">所在院校：</span>
						<span class="txt block">
							${recordLearn.school}
						</span>
					</li>
					<li>
						<span class="title">院校所在地：</span>
						<span class="txt block">${empty recordLearn.schoolAddress?"--":recordLearn.schoolAddress}</span>
					</li>
					<li>
						<span class="title">开始时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordLearn.startDate}" pattern="yyyy-MM-dd"/>
								${empty recordLearn.startDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">结束时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordLearn.endDate}" pattern="yyyy-MM-dd"/>
								${empty recordLearn.endDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">获奖情况：</span>
						<span class="txt block">
							${empty recordLearn.award?"--":recordLearn.award}
						</span>
					</li>
					<li>
						<span class="title">备注：</span>
						<span class="txt block">
							${empty recordLearn.remark?"--":recordLearn.remark}
						</span>
					</li>
					<li>
						<span class="title">登记时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordLearn.createTime}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
				</ul>
				<div class="main-title">附件</div>
				<div class="doc-list">
					<!-- <p class="row-doc">学习文件.txt</p>    
					<p class="row-doc">学习文件.txt</p> -->
					<ul id="attachmentList">
                                <c:forEach items="${recordLearn.attachmentList  }" var="attach">
                                    <p onclick="downFileById(${attach.id},this);">${attach.attachName}</p>
                                </c:forEach>
                               <c:if test="${empty recordLearn.attachmentList }">无</c:if>
                   </ul>
				</div>
				<!--end .doc-list-->
			</div>
			<!--end .reward-detail-->
		</div>
</body>
</html>