<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>关系详情页</title>
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
			<span class="title" >关系详情</span>
	</div>
		<div class="container-wrapper">	
			<div class="reward-detail detail-list top5">
				<ul>
					<li>
						<span class="title">姓名：</span>
						<span class="txt block">${recordRelation.userInfo.userName}</span>
					</li>
					<li>
						<span class="title">成员姓名：</span>
						<span class="txt block">${recordRelation.memberName}</span>
					</li>
					<li>
						<span class="title">与本人关系：</span>
						<span class="txt block">
							${recordRelation.relationStr}
						</span>
					</li>
					<li>
						<span class="title">出生日期：</span>
						<span class="txt block">
							<c:choose>
							      <c:when test="${recordRelation.birthDate==null}">无</c:when>
							      <c:otherwise>
									<fmt:formatDate value="${recordRelation.birthDate}" pattern="yyyy-MM-dd"/>				      
							      </c:otherwise>
							</c:choose>
						</span>
					</li>
					
					<li>
						<span class="title">政治面貌：</span>
						<span class="txt block">${empty recordRelation.politicalStatusStr?"--":recordRelation.politicalStatusStr}</span>
					</li>
					<li>
						<span class="title">职业：</span>
						<span class="txt block">
							${empty recordRelation.occupation?"--":recordRelation.occupation}
						</span>
					</li>
					<li>
						<span class="title">工作单位：</span>
						<span class="txt block">
							${empty recordRelation.workUnit?"--":recordRelation.workUnit}
						</span>
					</li>
					
					<li>
						<span class="title">单位电话：</span>
						<span class="txt block">
							${empty recordRelation.workTelephone?"--":recordRelation.workTelephone}
						</span>
					</li>
					<li>
						<span class="title">个人电话：</span>
						<span class="txt block">
							${recordRelation.personalPhone}
						</span>
					</li>
					<li>
						<span class="title">家庭电话：</span>
						<span class="txt block">
							${recordRelation.homePhone}
						</span>
					</li>
					<li>
						<span class="title">单位地址：</span>
						<span class="txt block">
							${empty recordRelation.unitAddress?"--":recordRelation.unitAddress}
						</span>
					</li>
					<li>
						<span class="title">家庭地址：</span>
						<span class="txt block">
							${empty recordRelation.homeAddress?"--":recordRelation.homeAddress}
						</span>
					</li>
					<li>
						<span class="title">备注：</span>
						<span class="txt block">
							${empty recordRelation.remark?"--":recordRelation.remark}
						</span>
					</li>
					<li>
						<span class="title">登记时间：</span>
						<span class="txt block">
							<fmt:formatDate value="${recordRelation.createTime}" pattern="yyyy-MM-dd"/>
						</span>
					</li>
				</ul>
				<div class="main-title">附件</div>
				<div class="doc-list">
					<!-- <p class="row-doc">学习文件.txt</p>    
					<p class="row-doc">学习文件.txt</p> -->
					<ul id="attachmentList">
                                <c:forEach items="${recordRelation.attachmentList}" var="attachment">
                                    <p onclick="downFileById(${attachment.id},this)">${attachment.attachName}</p>
                                </c:forEach>
                               <c:if test="${empty recordRelation.attachmentList }">无</c:if>
                   </ul>
				</div>
				<!--end .doc-list-->
			</div>
			<!--end .reward-detail-->
		</div>
</body>
</html>