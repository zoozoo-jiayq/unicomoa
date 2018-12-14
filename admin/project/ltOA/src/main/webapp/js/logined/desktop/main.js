/**
 * desktop main js---tbt
 */


$(document).ready(function () {

    //初始化所有的
    initModulesContainer();
    //initRefixAppPos();
    initModuleClick();
    initTrash();
    initAppSortable($(".screen ul"));
    initOpenAppBox();
    initDesktopTaskTimer();
});

//删除一个屏幕
function removeAnScreenPage(pageId) {
    var screen = $("#container .screen[pageId='" + pageId + "']");
    var allScreenCount = $("#container .screen").length;
    if (screen.length > 0) {
        var index = getPageIndex(pageId);
        slideBox.removeScreen(index);
        if (allScreenCount == 1) {
            //仅为1个，且被删除掉了
            setCurrentPageId(-1);
        }
        if (allScreenCount > 1 && pageId == getCurrentPageId()) {
            slideBox.scroll(0);
            var firstPageId = slideBox.getScreen(0).attr("pageId");
            setCurrentPageId(firstPageId);
        }
    }
}

//根据PageId获取其索引位置
function getPageIndex(pageId) {
    var pageIndex = -1;
    $("#container .screen").each(function (index) {
        if ($(this).attr("pageId") == pageId) {
            pageIndex = index;
            return pageIndex;//这里返回仅each方法执行完毕，相当于break;
        }
    });
    return pageIndex;
}

function getNewScreenIndexArray() {
    var i = 0;
    var indexArray = new Array();
    $("#container .screen").each(function () {
        indexArray.push(i);
        i++;
    });
    return indexArray;
}

//向个人桌面中添加一个屏幕
function addAnScreenPage(desktopPageJson) {
    var orderNo = desktopPageJson["orderNo"];
    var pageId = desktopPageJson["id"];
    var newIndex = $("#container .screen").length;
    slideBox.addScreen(newIndex);
    var screen = slideBox.getScreen(newIndex);
    screen.html("<ul class='ui-sortable'></ul>");
    screen.attr("id", "screen_" + pageId);
    screen.attr("pageId", pageId);
    screen.attr("index", orderNo);
    slideBox.scroll(newIndex);
    setCurrentPageId(pageId);
    initAppSortable(screen.find("ul"));
}

//向左面添加一个项
function addAnDesktopModule(desktopModuleJson) {

    var moduleInfoIcon = desktopModuleJson["moduleInfo.icon"];
    var moduleInfoUrl = desktopModuleJson["moduleInfo.url"];
    var moduleInfoId = desktopModuleJson["moduleInfo.moduleId"];
    var moduleInfoName = desktopModuleJson["moduleInfo.moduleName"];

    var desktopModuleId = desktopModuleJson["id"];
    var desktopModuleOrderNo = desktopModuleJson["orderNo"];
    var desktopPageId = desktopModuleJson["desktopPageId"];
    var pageMenuJson = desktopModuleJson["pageMenuJson"];

    if (typeof(moduleInfoIcon) == "undefined" || moduleInfoIcon == "") {
        moduleInfoIcon = _cssType+"/images/module_icons/share.png";
    }
    moduleInfoIcon = basePath + moduleInfoIcon;
    var html = "<li class='block' moduleUrl='" + moduleInfoUrl + "' moduleId='" + moduleInfoId + "' desktopModuleId='" + desktopModuleId + "' ";
    html += " pageMenuJson='" + pageMenuJson + "' ";
    html += " id='module_block_" + desktopModuleId + "' title='" + moduleInfoName + "' index='" + desktopModuleOrderNo + "' >";
    html += "<div class='img'>";
    html += "<p><img src='" + moduleInfoIcon + "' /></p>";
    html += "<div class='count' id='module_count_" + moduleInfoId + "'></div>";
    html += "</div>";
    html += "<a class='icon-text' href='javascript:void(0)'><span>" + moduleInfoName + "</span></a>";
    html += "</li>";

    var screenPageUL = $(".screen[pageId='" + desktopPageId + "'] .ui-sortable");
    screenPageUL.append(html);
//更新任务情况
    updateDesktopTasks(moduleInfoName);
    return true;
}

//获取当前显示的的个人桌面页id
function getCurrentPageId() {
	 
    var screenPage = $(".screen");
    if (screenPage.length == 0) {
        return -1;
    }
    return $("#currentPageId").val();
}

//获取当前页面的应用个数
function getCurrentPageModuleCount() {
    return $(".screen[pageId='" + getCurrentPageId() + "'] .ui-sortable .block").length;
}

function setCurrentPageId(pageId) {
    $("#currentPageId").val(pageId);
}

//更新指定页面的桌面人数数量
function updateDesktopTaskByPageId(pageId) {
    if (pageId == undefined || $.trim(pageId) == "" || pageId == "-1") {
        return;
    }
    var moduleNames = "";
    $("#container .screen[pageId='" + pageId + "'] .ui-sortable li").each(function () {
        moduleNames += $(this).attr("title");
        moduleNames += ",";
    });
    updateDesktopTasks(moduleNames);
}

//初始化定时器，页面打开时调用，延时5秒启动定时器
function initDesktopTaskTimer() {
    setTimeout(desktopTaskTimer, 15 * 1000);
}

//桌面任务数量更新定时器
function desktopTaskTimer() {
    updateDesktopTaskByPageId(getCurrentPageId());
    setTimeout(desktopTaskTimer, 2 * 60 * 1000);
}


function testCallBack() {
    alert("Just a test for callBack");
}

/**
 * 初始化所有modules的容器
 */
function initModulesContainer() {

    var desktopPageListJson = $("#desktopPageListJson").val();
    desktopPageListJson = JSON.parse(desktopPageListJson);

    var pageCount = desktopPageListJson.length;
    window.slideBox = $("#container").slideBox({
        count: pageCount,
        cancel: isTouchDevice() ? "" : ".block",
        obstacle: "200",
        speed: "slow",
        touchDevice: isTouchDevice(),
        control: "#control .control-c",
        listeners: {
            afterScroll: function (i) {
                var pageId = slideBox.getScreen(i).attr("pageId");
                if (typeof(pageId) != "undefined") {
                    setCurrentPageId(slideBox.getScreen(i).attr("pageId"));
                    updateDesktopTaskByPageId(getCurrentPageId());
                }
            },
            beforeScroll: function (i) {
                var pageId = slideBox.getScreen(i).attr("pageId");
                loadModuleIcon(pageId);
            }
        }
    });

//添加page页以及Page页里面的项
    for (var i = 0; i < desktopPageListJson.length; i++) {
        var desktopPage = desktopPageListJson[i];
        var ul = $("<ul class='ui-sortable'></ul>");
        var desktopModuleList = desktopPage["desktopModuleList"];
        var moduleLiHtml = "";
        for (var j = 0; j < desktopModuleList.length; j++) {
            var desktopModule = desktopModuleList[j];
            var moduleInfo = desktopModule["moduleInfo"];
            moduleLiHtml += "<li class='block' ";
            moduleLiHtml += " moduleUrl='" + moduleInfo["url"] + "'";
            moduleLiHtml += " moduleId='" + moduleInfo["moduleId"] + "'";
            moduleLiHtml += " desktopModuleId='" + desktopModule["id"] + "'";
            moduleLiHtml += " pageMenuJson='" + desktopModule["pageMenuJson"] + "'";
            moduleLiHtml += " title='" + moduleInfo["moduleName"] + "'";
            moduleLiHtml += " index='" + desktopModule["orderNo"] + "'";
            moduleLiHtml += " >";

            var iconImgSrc = _cssType+"/images/module_icons/share.png";
            var moduleIcon = moduleInfo["icon"];
            if (moduleIcon != undefined && $.trim(moduleIcon) != "") {
                iconImgSrc = moduleIcon;
            }
            iconImgSrc = basePath + iconImgSrc;
            var transparentIcon = basePath + "/images/app_icons/transparent.gif";
            moduleLiHtml += "<div class='img'><p><img src='" + transparentIcon + "' _src='" + iconImgSrc + "' /></p><div class='count'></div></div>";
            moduleLiHtml += "<a class='icon-text' href='javascript:void(0)'><span>" + moduleInfo["moduleName"] + "</span></a> ";
            moduleLiHtml += "</li>";
        }
        ul.html(moduleLiHtml);
        var screen = slideBox.getScreen(i);
        screen.attr("id", "screen_" + desktopPage["id"]);
        screen.attr("pageId", desktopPage["id"]);
        screen.attr("index", desktopPage["orderNo"]);
        screen.append(ul);
    }
    $("#container").show();
    var pageId = $(".screen .ui-sortable:first").parent().attr("pageId");
    setCurrentPageId(pageId);
    loadModuleIcon(pageId);
    updateDesktopTaskByPageId(pageId);
}

//显示某个Screen应用的图标
function loadModuleIcon(pageId) {
    var moduleIcons = $("#container .screen[pageId='" + pageId + "'] .ui-sortable .block .img img");
    moduleIcons.each(function () {
        var _src = $(this).attr("_src");
        if (_src != undefined && $.trim(_src) != "") {
            $(this).attr("src", _src);
            $(this).removeAttr("_src");
        }
    });
}

/**
 *初始化app图标间距
 */
function initRefixAppPos() {
    var _margin = getAppMargin() + "px";
    $("#container .screen li.block").css({"margin-left": _margin, "margin-right": _margin})
}

function getAppMargin() {
    var clientSize = $(document.body).outerWidth(true);
    var rowAppNum = $("#container .screen li.block").length;
    var appsize = 120 * rowAppNum;
    var _margin = 0;
    if (clientSize > appsize) {
        _margin = Math.floor((clientSize - appsize - 70 * 2) / 16);
    }
    return _margin;
}

/**
 *初始化图标点击事件
 */
function initModuleClick() {
    $('#container .screen ul li.block').live("click", moduleClick);
}

function moduleClick() {
    var module = $(this);
    var taskUrl = module.attr("taskUrl");
    var moduleName = $(this).attr("title");
    if (typeof(taskUrl) != "undefined" && $.trim(taskUrl) != "") {
        taskUrl = basePath + taskUrl;
        window.parent.gotoUrl(module.attr("moduleId")+"id", moduleName, taskUrl, null);
    } else {
        var pageMenuJson = module.attr("pageMenuJson");
        if (pageMenuJson == undefined || $.trim(pageMenuJson) == "") {
            pageMenuJson = undefined;
        }
        window.parent.gotoUrl(module.attr("moduleId"), module.attr("title"), basePath + module.attr("moduleUrl"), pageMenuJson);
    }
}

/**
 *初始化垃圾箱
 */
function initTrash() {
    $("#trash").droppable({
        over: function () {
            $("#trash").addClass("hover");
        },
        out: function () {
            $("#trash").removeClass("hover");
        },
        drop: function (event, ui) {
            ui.draggable.addClass("remove").hide();
            var desktopModuleId = ui.draggable.attr("desktopModuleId");
            deleteDesktopModule(desktopModuleId);
        }
    });
}

var beforeMoveModuleIds = "";
//初始化App图标拖拽移动事件
function initAppSortable(jObj) {

    jObj.sortable({
        revert: true,
        tolerance: 'pointer',
        connectWith: ".screen ul",
        scroll: false,
        stop: function (e, ui) {
            setTimeout(function () {
                $(".block.remove").remove();
                $("#trash").hide();
                ui.item.click(moduleClick);
            }, 0);
        },
        start: function (e, ui) {
            $("#trash").show();
            refixminScreenbtn();
            ui.item.unbind("click");
            beforeMoveModuleIds = getDesktopModuleIds($(ui.item));
        },
        update: function (e, ui) {
            if (beforeMoveModuleIds == getDesktopModuleIds($(ui.item))) {
                return;
            }
            setTimeout(function () {
                reorderDesktopModules($(ui.item));
            }, 0);
        }
    });
}

//绑定“界面设置”事件
function initOpenAppBox() {
    $("#openAppBox").click(function () {
        var appBoxURL = basePath + "logined/desktop/appBoxPage.action?t="+Math.random();
        qytx.app.dialog.openUrl({
        	id	:	"appBox",
        	title:	"应用盒子",
        	url	:	appBoxURL,
        	size:	"L"
        });
    });
}

//修正点击按钮出现屏幕小按钮width为0的现象
function refixminScreenbtn() {
    $('#control').width(window.document.documentElement.clientWidth);
}

//删除一个桌面Module
function deleteDesktopModule(desktopModuleId) {
    //删除选中的记录ajax
    qytx.app.ajax({
        url: basePath + "logined/desktop/deleteDesktopModule.action",
        type: "post",
        dataType: 'text',
        data: {
            desktopModuleId: desktopModuleId
        },
        success: function (data) {
            if (data != "") {
            	qytx.app.dialog.alert(data);
            }
        }
    });
}

function reorderDesktopPages(desktopPageIds) {
    var pageIds = desktopPageIds.split(",");
    var oldIndexArray = [];
    $.each(pageIds, function (i, val) {
        oldIndexArray.push(getPageIndex(val));
    });
    if (oldIndexArray.length > 1) {
        var currentPageIndex = getPageIndex(getCurrentPageId());
        slideBox.sortScreen(oldIndexArray);
        slideBox.scroll(currentPageIndex);
        //重置CurrenPageId
        setCurrentPageId(slideBox.getScreen(currentPageIndex).attr("pageId"));
    }
}

//传入参数为某一个DesktopModule，
// 获取到该DesktopModule所在Screen的所有ModuleId
function getDesktopModuleIds(desktopModule) {
    var desktopModuleIds = "";
    desktopModule.parent().find("li.block").each(function () {
        var desktopModuleId = $(this).attr("desktopModuleId");
        //移动过程中 sortable 会临时生成一个[visibility:hidden]的页占据位置,此时pageId不存在
        if (desktopModuleId != undefined && $.trim(desktopModuleId) != "") {
            desktopModuleIds += ",";
            desktopModuleIds += $(this).attr("desktopModuleId");
        }
    });
    if (desktopModuleIds.length > 1) {
        desktopModuleIds = desktopModuleIds.substr(1);
    }
    return desktopModuleIds;
}


function reorderDesktopModules(desktopModule) {
    var desktopModuleIds = getDesktopModuleIds(desktopModule);
    if (desktopModuleIds == "" || desktopModuleIds.split(",").length < 2) {
        return;
    }
    //console.log("reorder desktop modules:"+desktopModuleIds);
    qytx.app.ajax({
        url: basePath + "logined/desktop/reorderDesktopModule.action",
        type: "post",
        dataType: 'text',
        data: {
            desktopModuleIds: desktopModuleIds
        },
        success: function (data) {
            if (data != "") {
            	qytx.app.dialog.alert(data);
            }
        }
    });
}

//更新桌面任务，moduleNames为英文逗号分割的数组形式
function updateDesktopTasks(moduleNames) {
    if (!moduleNames) {
        return;
    }
    qytx.app.ajax({
        url: basePath + "logined/desktop/getDesktopTasks.action",
        type: "post",
        dataType: 'json',
        data: {
            moduleNames: moduleNames
        },
        success: function (data) {
            data = eval(data);
            var status = data["status"];
            var content = data["content"];
            if (status == "error") {
            	qytx.app.dialog.alert(content);
            } else if (status == "success") {
                var taskList = eval(content);
                for (var i = 0; i < taskList.length; i++) {
                    var taskMap = taskList[i];
                    var moduleObj = $("#container .screen .ui-sortable .block[title='" + taskMap["moduleName"] + "']");
                    if (moduleObj.length > 0) {
                        var taskCount = taskMap["taskCount"];
                        var taskUrl = taskMap["taskUrl"];
                        taskCount = parseInt(taskCount, 10);
                        taskCount = taskCount > 10 ? taskCount = 10 : taskCount;
                        if (taskCount > 0) {
                            moduleObj.attr("taskUrl", taskUrl);
                        } else {
                            moduleObj.removeAttr("taskUrl");
                        }
                        var countObj = moduleObj.find(".img .count");
                        var clz = "count count" + taskCount;
                        countObj.attr("class", clz);
                    }
                }
            }
        }
    });
}