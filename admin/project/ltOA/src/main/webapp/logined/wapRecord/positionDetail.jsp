<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>职称详情页</title>
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
			<span class="title" >职称详情</span>
	</div>
		<div class="container-wrapper">	
			<div class="reward-detail detail-list top5">
				<ul>
					<li>
						<span class="title">姓名：</span>
						<span class="txt block">${recordTitle.userInfo.userName}</span>
					</li>
					<li>
						<span class="title">批准人：</span>
						<span class="txt block">${empty recordTitle.approveUser.userName?"--":recordTitle.approveUser.userName}</span>
					</li>
					<li>
						<span class="title">职称名称：</span>
						<span class="txt block">
							${recordTitle.title}
						</span>
					</li>
					<li>
						<span class="title">获取方式：</span>
						<span class="txt block">
							${recordTitle.accessTypeStr}
						</span>
					</li>
					
					<li>
						<span class="title">申报日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordTitle.applyDate}" pattern="yyyy-MM-dd"/>
								${empty recordTitle.applyDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">获取日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordTitle.giveDate}" pattern="yyyy-MM-dd"/>
								${empty recordTitle.giveDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">下次申报职称：</span>
						<span class="txt block">
							${empty recordTitle.nextTitle?"--":recordTitle.nextTitle}
						</span>
					</li>
					<li>
						<span class="title">下次申报日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordTitle.nextApplyDate}" pattern="yyyy-MM-dd"/>
								${empty recordTitle.nextApplyDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">聘用职务：</span>
						<span class="txt block">
							${empty recordTitle.hiringPosition?"--":recordTitle.hiringPosition}
						</span>
					</li>
					<li>
						<span class="title">聘用单位：</span>
						<span class="txt block">
							${empty recordTitle.hiringUnits?"--":recordTitle.hiringUnits}
						</span>
					</li>
					<li>
						<span class="title">聘用开始日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordTitle.hiringBeginDate}" pattern="yyyy-MM-dd"/>
								${empty recordTitle.hiringBeginDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">聘用结束日期：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordTitle.hiringEndDate}" pattern="yyyy-MM-dd"/>
								${empty recordTitle.hiringEndDate?"--":""}
						</span>
					</li>
					<li>
						<span class="title">评定详情：</span>
						<span class="txt block">
							${empty recordTitle.remark?"--":recordTitle.remark}
						</span>
					</li>
				</ul>
				<div class="main-title">附件</div>
				<div class="doc-list">
					<!-- <p class="row-doc">学习文件.txt</p>    
					<p class="row-doc">学习文件.txt</p> -->
					<ul id="attachmentList">
                                <c:forEach items="${recordTitle.attachmentList}" var="attachment">
                                    <p onclick="downFileById(${attachment.id},this);">${attachment.attachName}</p>
                                </c:forEach>
                               <c:if test="${empty recordTitle.attachmentList}">无</c:if>
                   </ul>
				</div>
				<!--end .doc-list-->
			</div>
			<!--end .reward-detail-->
		</div>
</body>
</html>