<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>常用工作流程</title>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/often.js"></script>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/flat/js/base.js"></script>
<script type="text/javascript" src="${ctx }/flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
</head>
<body>
<input type="hidden" id="type" value="${type}"/>
<input type="hidden" id="domType" value="${domType}" />
<div class="mainpage">
<!--左侧begin-->
	<div class="leftMenu">
		<div class="service-menu">
		   <h1>流程分类</h1>
				<c:forEach items="${procssCategorys}" var="cate"  varStatus="pc">
                     <c:if test="${pc.index==0}">
                         <input type="hidden" value="${cate.formCategory.categoryId}" id="currentCategoryId" />
                     </c:if>
					<p class="menu-p " onclick="toSubProcessList(${cate.formCategory.categoryId});addSelectState(this);" ><i class="menu-i"></i>
						<a href="#" >${cate.formCategory.categoryName }</a>&nbsp;<em class="f12 gray_9">(${cate.count })</em>
					</p>
				</c:forEach>
		</div>
	</div>	
<!--左侧end-->
<iframe name="page"  id="page"   class="iframeresize" border="0" frameBorder="0" frameSpacing="0" marginWidth="0" marginHeight="0" style="width: 100%; height: 100%;"></iframe>
</div>
</body>
</html>