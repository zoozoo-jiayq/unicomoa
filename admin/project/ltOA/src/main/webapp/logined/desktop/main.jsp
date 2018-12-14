<%--
功能:人事档案 新建和修改页面
版本:1.0
开发人员: 汤波涛
创建日期: 2013-04-16
修改日期: 2013-04-16
修改人员: 汤波涛
修改列表:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="../../common/flatHead.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>个人桌面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
     <link rel="stylesheet" type="text/css" href="${ctx}flat/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}flat/css/desktop_icon.css"/>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.core.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.widget.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.mouse.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.sortable.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.draggable.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.droppable.min.js"></script>

    <script type="text/javascript" src="${ctx}/plugins/jquery-ux/jquery.ux.borderlayout.js.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ux/jquery.ux.simulatemouse.js.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ux/jquery.ux.slidebox.js.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/desktop/main.js"></script>
    
   
</head>
<body class="home" id="desktopBody">
<span id="desktopRollTips" class="dn"></span>

<div id="control">
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <td class="control-l"></td>
            <td class="control-c"></td>
            <td class="control-r"><a id="openAppBox" class="cfg" title="打开应用盒子" href="javascript: void(0)"></a></td>
        </tr>
        </tbody>
    </table>
</div>

<div class="slidebox">
    <div id="trash" class="ui-droppable"></div>
    <div id="container"></div>
</div>
<input type="hidden" id="currentPageId" value=""/>
<input type="hidden" id="desktopPageListJson" value="<s:property value="#desktopPageListJson"/>"/>

<!--[if lte IE 6]>
<script type="text/javascript">
    var tips = "为了更好的桌面体验效果，建议您使用IE8及以上版本浏览器!";
    alert(tips);
    var roleTips = document.createElement("MARQUEE");
    roleTips.scrolldelay = 300;
    roleTips.direction = "left";
    roleTips.innerHTML = tips;
    roleTips.style.width = "100%";

    var roleTipsSpan = document.getElementById("desktopRollTips");
    roleTipsSpan.style.width = "100%";
    roleTipsSpan.style.display = "block";
    roleTipsSpan.appendChild(roleTips);
</script>
<![endif]-->
</body>
</html>
