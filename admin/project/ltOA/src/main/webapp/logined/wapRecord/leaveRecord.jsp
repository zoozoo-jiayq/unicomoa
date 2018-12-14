<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>PeopleList</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!--css-->
    <%
    String basePath = "http://192.168.10.28:8080/qytx-oa-mysql/" ;
    request.setAttribute("ctx", basePath);
%>   
<script>
  var basePath="${ctx}";
</script>
	<link href="${ctx}css/wapRecord/wapRecord.css" rel="stylesheet">
	<script src="../../js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}js/logined/wapRecord/onlineRecord.js"></script>
</head>
<input type="hidden" id="userId" value="1">
<input type="hidden" id="treeType" value="2">
<body class="bg-gray">
   <div class="header-bar">
			<i class="icons icon-18x18 icon-return" id="backImg"></i>
			<span class="title">离职人员</span>
	</div>
		<!--end .header-bar-->
		<div class="container-wrapper">	
			<div class="search-bar" style="padding-top:1px">
				<!-- <span class="input-area">
					<i class="icons icon-18x18 icon-search"></i>
					<input type="text" placeholder="搜索" id="serchName"/>
				</span>
				<span class="btn" id="serch">搜索</span> -->
			</div>
			<!--end .search-bar-->
			<div class="people-list" id="list" style="padding-top:12px">
				
			</div>
			<p class="loading-more" id="moreData">点击加载更多</p>
		</div>
</body>
</html>