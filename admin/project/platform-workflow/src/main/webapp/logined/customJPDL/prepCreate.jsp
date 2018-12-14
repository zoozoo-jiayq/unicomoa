<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弹窗--设计流程--新建流程</title>
<jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>

<script type="text/javascript" src="${ctx}/js/logined/customJPDL/prepCreate.js"></script>
</head>
<body  class="bg_white" >
<input type="hidden" id="type" value="${type }"/>
   <div style="width: 800px" class="pop-panel">
		<div class="pop-panel-inner">
				<div style="width: 150px" class="pop-panel-sider">
						<div style="height: auto" class=content>
								<div>
										<h3 class="group-header">常用</h3>
										<ul class="group" id="blackList">
												<li class="item active" title="" id="">空白流程</li>
										</ul>
										<hr/>
										<h3 class="group-header">模版</h3>
										<ul class="group">											
												<c:forEach items="${procssCategorys}" var="cate">
													<li class="item " title="${cate.formCategory.categoryName}" id="${cate.formCategory.categoryId}" >${cate.formCategory.categoryName}</li>
												</c:forEach>
										</ul>
								</div>
						</div>
				</div>
				<div style="margin-left: 150px" class="pop-panel-container">
						<!--当为空白流程时显示-->
						<div id="toolbar-nav">
								<div class="historyNav clearfix"><em id="showTitle">空白流程</em></div>
						</div>
						<!--当为空白流程时显示 end -->
						<div style="height: 401px; overflow: auto" class="content">
								<div id="explorer">
										<div class="explorer-list-group icon48" >
											
												<h2 id="processNums">空白流程(1)</h2>
											
												<hr>
												<ul class="ico-list clearfix" id="ullist">
														<li class="icon-selected" isfolder="">
																<div class="icocontainer">
																		<div class="selectcontainer" title="空白流程">
																				<div class="ico ico-blank"></div>
																				<a>空白流程</a></div>
																</div>
														</li>
													
												</ul>
										</div>
								</div>
						</div>
						<!--
						<div class="buttonArea">
								<input hideFocus="" value="确 定" type="submit" onclick="javascript:openbox('save')" class="formButton">
								&nbsp;&nbsp;
								<input hideFocus="" onclick="javascript:window.location.href = document.referrer;" value="返 回" type="button" class="formButton">
								</div>
							-->
						<div class=pop-panel-tips><b>提示:</b> 您可以使用空白流程进行新建，也可以从模版中选择已有的流程进行复制.</div>
						<div style="height: 45px" class="controllbar-holder"></div>
				</div>
		</div>
   </div>
</body>
</html>