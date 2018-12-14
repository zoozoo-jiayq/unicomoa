<%--
  Created by IntelliJ IDEA.
  User: lilipo
  Date: 2016/12/26
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>模板分级</title>
    <jsp:include page="../../common/taglibs.jsp"/>
    <script src="../../js/template/left_template.js"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>

	<script type="text/javascript">
    	function changeIframe(src){
    		$("#MyIframe").attr("src",src);
    		 
    	}
    </script>
</head>

<body>
<jsp:include page="../header.jsp"/>
	<ul class="nav">
		<li onclick="javascript:window.location.href='${ctx}logined/appraisal/jsp/admin/adminHome.jsp'">
			<a href="javascript:void(0);" class="index">返回首页
			</a>
		</li>
		<li class="active" onclick="changeIframe('${ctx}logined/appraisal/jsp/template/templateGrade.jsp');">
			<a href="javascript:void(0);" class="nav_fenji_list" >分级模板
				<em class="red_circle"></em>
			</a>
		</li>
		<li onclick="changeIframe('${ctx}logined/appraisal/jsp/dict/dict.jsp');">
			<a href="#" class="nav_zhibiao_fenlei" >指标分类
				<em class="red_circle"></em>
			</a>
		</li>
	</ul>
	<div class="right_content" style="overflow:hidden;">		
		<iframe id="MyIframe" src="templateGrade.jsp" frameborder="0" width="100%" height="100%"></iframe>
	</div>
</body>
</html>
