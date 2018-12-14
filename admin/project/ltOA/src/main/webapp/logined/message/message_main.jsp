<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>即时消息-消息记录</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/InstantMessage.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/flat/plugins/peopleTree/skins/tree_default.css" type="text/css"/>
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}/flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}flat/js/async.js?v=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/message/message_main.js"></script>
</head>
<body>
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>单位组织成员</h1>
      <div class="zTreeDemoBackground">
        <ul id="groupUserTree" class="ztree">
        </ul>
      </div>
    </div>
  </div>
  		<!--左侧end-->
		<iframe  class="iframeresize"   id="message_main"
			class="xx_iframe" style="overflow-x:hidden;" name="message_main"
			frameborder="no" scrolling="auto" hidefocus></iframe>
</div>
</body>
<script type="text/javascript">
		if(menuStyle != "default"){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
</script>
</html>

