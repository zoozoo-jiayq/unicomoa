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
    <title>应用盒子</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/css/desktop_icon.css" rel="stylesheet" type="text/css" />
 
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.core.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.widget.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.mouse.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.sortable.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.draggable.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ui-1.10.12/jquery.ui.droppable.min.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ux/jquery.ux.borderlayout.js.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/jquery-ux/jquery.ux.simulatemouse.js.js"></script>
    <script type="text/javascript" src="${ctx}flat/js/jquery.SuperSlide.2.1.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/desktop/app_box.js"></script>
</head>
<body>
    <div id="portalSetting">
        <div class="ui-layout-north" id="bar" style="">
            <span id="btnAppSet" class="on"  >应用设置</span>
            <span id="btnScreenSet">分屏设置</span>
            <span id="portalSettingMsg"></span>
        </div>
     
        <div   id="appPageAll">
         <!--应用设置-->
            <div class="appPage" id="appPageDom">
                <div   id="app_cate_list">
                     <div class="hd">
                           <div class="scroll-up prev"></div>
                    </div>
                    <div class="bd" >
                       <ul>
                        <s:iterator value="#oneLevelModuleList" var="moduleInfo"  status="st">
	                            <li id="module_${moduleInfo.moduleId}" moduleId="${moduleInfo.moduleId}"
	                                   title="${moduleInfo.moduleName}" >
	                                    <em   class="<s:if test="%{#moduleInfo.moduleClass==null}"></s:if><s:else>${moduleInfo.moduleClass}</s:else>"></em>
	                  					${moduleInfo.moduleName} 
	                            </li>
                        </s:iterator>
                      </ul>
                    </div>
                     <div class="hd">
                    	<div class="scroll-down next"></div>
                    </div>
                </div>
                <div id="app_list_box" >
                        <ul id="app_list_record"></ul>
                </div>
                <div class="clearfix"></div>
            </div>
            <div id="screenPageDom">
                <div id="screen_list">
                    <div class="clearfix"></div>
                    <ul class="ui-sortable">
                        <s:iterator value="#pageList" var="desktopPage" status="status">
                            <li class="minscreenceil" id="miniPage_${desktopPage.id}" index="${desktopPage.orderNo}"
                                pageId="${desktopPage.id}">${status.count}
                                <span title="移除此屏" class="closebtn" style="display: none;"></span>
                            </li>
                        </s:iterator>
                        <li id="btnAddScreen" class="add" title="添加屏幕"></li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
     <script type="text/javascript">
		 jQuery("#app_cate_list").slide({titCell:".hd ul",mainCell:".bd ul",effect:"top",autoPlay:false,pnLoop:false,vis:3,trigger:"click"});
 </script>
    
</body>
</html>