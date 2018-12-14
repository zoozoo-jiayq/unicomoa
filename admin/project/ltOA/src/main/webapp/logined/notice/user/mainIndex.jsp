<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../comm/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公告发布</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../comm/head.jsp" />
<link href="${ctx}css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/main.js?v=${jsversion}"></script>
<style type="text/css">
html {
	overflow: hidden;
}
</style>
</head>
<body  class="frame_class">

<div id="mainContainer" class="mainbody">
  <div id="menu">
    <div class="s_menu">
      <ul class="houtai_left">
        <li>
          <a target="iframe_main1" style="text-decoration:none;" href="${ctx}logined/notice/manage/list.jsp"><p id="tag4" class="open noborder_top"><em class="em_10" style="background:url(${ctx}images/content/content.png) no-repeat;"></em>栏目管理</p></a>
        </li>
        <li>
          <a target="iframe_main1" style="text-decoration:none;" href="${ctx}logined/notice/user/columnList.jsp"><p id="tag1" class="open noborder_top  current_left"><em class="em_10" style="background:url(${ctx}images/content/content.png) no-repeat;"></em>发布公告</p></a>
        </li>
        <li>
          <a target="iframe_main1" style="text-decoration:none;" href="${ctx}logined/notice/user/issueList.jsp"><p id="tag2" class="open noborder_top"><em class="em_11" style="background:url(${ctx}images/content/published.png) no-repeat;"></em>公告列表</p></a>
        </li>
        <li>
          <a target="iframe_main1" style="text-decoration:none;" href="${ctx}logined/notice/user/issueDraft.jsp"><p id="tag3" class="open noborder_top"><em class="em_12" style="background:url(${ctx}images/content/inbox.png) no-repeat;"></em>公告草稿</p></a>
        </li>
      </ul>
    </div>
  </div>
  <!--右侧内容 begin-->
  <div  id="mainFrameContainer">
    <iframe src="${ctx}logined/notice/user/columnList.jsp" id="iframe_main1" class="meetingIframe" name="iframe_main1" frameborder="no" scrolling="auto" hidefocus ></iframe>
  </div>
  <!--右侧内容 end--> 
</div>
</body>
</html>
