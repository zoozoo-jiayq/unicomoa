<%--
 /**
 页面说明
 * 版本: 1.0
 * 开发人员：黄普友
 * 创建日期: 13-4-2
 * 修改日期：
 * 修改列表：
 *
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../common/taglibs.jsp"/>
<!DOCTYpE html pUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>个人桌面</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/home.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/portal_index.css/">
    <!--[if gt IE 6]>
    <script type=text/javascript src="${ctx}/js/home/jquery.min.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.plugins.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery-ui.custom.min.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.ui.draggable.min.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.ui.sortable.min.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.ux.borderlayout.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.ui.droppable.min.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.ux.slidebox.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/jquery.ux.simulatemouse.js.js"></script>
    <script type=text/javascript src="${ctx}/js/home/sys_function.js"></script>
    <script type=text/javascript>
        self.moveTo(0,0);
        self.resizeTo(screen.availWidth,screen.availHeight);
        self.focus();

        //-- 一级菜单 --
        var first_array = ["01","10","20","30","50","65","70","85","90"];
    </script>
    <script type=text/javascript src="../js/home/portal_index.js.js"></script>

    <script type=text/javascript>
        var monInterval = 3;
        var moduleIdStr = '';
        var funcIdStr = '4,7,8,131,1,130,3,256,5,16';
        var ostheme = '11';
        //-- 可用菜单 --
        var fmenu = typeof(first_array) == 'object' ? first_array : top.first_array;
        var smenu = typeof(second_array) == 'object' ? second_array : top.second_array;
        var tmenu = typeof(third_array) == 'object' ? third_array : top.third_array;
        var funcarray = typeof(func_array) == 'object' ? func_array : top.func_array;
    </script>
<![endif]-->
</head>
<body class="home">
<!--[if IE 6]>
<div class="slidebox" stlyle="height: 350px;overflow:hidden;">
	<div style="left: 0px; top: 0px; width: 100%; height: 350px;position: absolute;" class="screen">
						<ul class="ui-sortable">
								<li style="margin-right: 21px; margin-left: 21px;" id="block_4" class="block" title="公告通知" index="4">
										<div class="img">
												<p><img src="../images/app_icons/notify.png"></p>
												<div id="count_4" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>公告通知</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_7" class="block" title="个人考勤" index="7">
										<div class="img">
												<p><img src="../images/app_icons/attendance.png"></p>
												<div id="count_7" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>个人考勤</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_8" class="block" title="日程安排" index="8">
										<div class="img">
												<p><img src="../images/app_icons/calendar.png"></p>
												<div id="count_8" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>日程安排</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_131" class="block" title="工作查询" index="131">
										<div class="img">
												<p><img src="../images/app_icons/searchWork.png"></p>
												<div id="count_131" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>工作查询</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_1" class="block" title="电子邮件" index="1">
										<div class="img">
												<p><img src="../images/app_icons/email.png"></p>
												<div id="count_1" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>电子邮件</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_130" class="block" title="新建工作" index="130">
										<div class="img">
												<p><img src="../images/app_icons/newwork.png"></p>
												<div id="count_130" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>新建工作</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_3" class="block" title="消息管理" index="3">
										<div class="img">
												<p><img src="../images/app_icons/sms.png"></p>
												<div id="count_3" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>消息管理</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_256" class="block" title="数据报表" index="256">
										<div class="img">
												<p><img src="../images/app_icons/dataReport.png"></p>
												<div id="count_256" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>数据报表</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_5" class="block" title="我的工作" index="5">
										<div class="img">
												<p><img src="../images/app_icons/workflow.png"></p>
												<div id="count_5" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)"><span>我的工作</span></a></li>
								<li style="margin-right: 21px; margin-left: 21px;" id="block_16" class="block" title="个人文件柜" index="16">
										<div class="img">
												<p><img src="../images/app_icons/file_folder.png"></p>
												<div id="count_16" class="count"></div>
										</div>
										<a class="icon-text" href="javascript: void(0)" target="_top" ><span>个人文件柜</span></a></li>
						</ul>
				</div>

</div>
<![endif]-->
<!--[if ! IE 6]>
<div class="slidebox">
    <div id="trash" class="ui-droppable" ></div>
    <div style="pOSITION: relative; WIDTH: 1436px; HEIGHT: 153px; LEFT: 0px" id="container" >
        <div style="pOSITION: absolute; WIDTH: 100%; HEIGHT: 100%; TOp: 0px; LEFT: 0px" class="screen">

        </div>
    </div>
</div>
<![endif]-->
</body>
</html>