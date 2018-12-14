<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../../common/ydzjtaglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新闻发布-发布新闻</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/ydzjhead.jsp" />
<script>
	var downPath = "${session.downPath}";
</script>
<script type="text/javascript" src="${ctx}js/logined/notice/user/columnList.js?v=${jsversion}"></script>
<style type="text/css">
	table.pretty tbody td{padding:5px;}
</style>
</head>
<body>
<div class="list">
        <div class="prettyList">
        <table cellpadding="0" cellspacing="1"  class="pretty dataTable" id="table">
            <thead>
			</thead>
        </table>
        <div class="clear"></div>    
       </div>
</div>
</body>
</html>