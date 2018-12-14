// app box js---tbt
var _maxScreentCount = 10;
$(document).ready(function () {
    initSomePageContent();
    //定义应用一级菜单是否滚动
    initAppListScroll();
    //初始化一级菜单点击事件
    initAppListScrollClick();

    initFirstAppInList();

    initDesktopPageSortable();

    initBtnAddScreen();

    initBtnDeleteScreen();

    initNotInDesktopModuleClick();

    //showMsg("Hello World !");
});

//显示提示消息，倒计时3秒关闭
function showMsg(msg) {
    if (msg != undefined && $.trim(msg) != "") {
        $("#portalSettingMsg").html(msg);
        setTimeout(function () {
            $("#portalSettingMsg").html("");
        }, 3 * 1000);
    }
}
//页面应用最大量
var pageModulesMaxCount = 32;

function initNotInDesktopModuleClick() {
 
    //未添加到桌面的项的单击事件，像桌面的当前页添加项
    $("#app_list_record li").live("click", function () {
    	 
        //向当前桌面页添加一个菜单项
        var desktopPageId = artDialog.opener.getCurrentPageId();
        //alert("desktopPageId:"+desktopPageId);
        if (desktopPageId == undefined || $.trim(desktopPageId) == "" || desktopPageId == -1) {
            qytx.app.dialog.alert(sprintf("desktop.please_add_an_screen_before_the_action"));
            return;
        }
        var moduleInfoId = $(this).attr("moduleInfoId");
        var moduleLi = $(this);
        if (artDialog.opener.getCurrentPageModuleCount() >= pageModulesMaxCount) {
        	qytx.app.dialog.confirm(sprintf("desktop.current_screen_module_count_over_32"),
                function () {
                    addDesktopModule(desktopPageId, moduleInfoId, moduleLi);
                }, function () {
                    //cancel;
                });
        } else {
            addDesktopModule(desktopPageId, moduleInfoId, moduleLi);
        }
    })
}

function addDesktopModule(desktopPageId, moduleInfoId, moduleLi) {
	 
	qytx.app.ajax({
        url: basePath + "logined/desktop/addDesktopModule.action",
        type: "post",
        dataType: 'json',
        data: {
            desktopPageId: desktopPageId,
            moduleInfoId: moduleInfoId
        },
        success: function (data) {
            data = eval(data);
            if (data["status"] == "success") {
                var desktopModule = eval(data["content"]);
                if (artDialog.opener.addAnDesktopModule(desktopModule)) {
                    moduleLi.remove();
                    showMsg(sprintf("desktop.add_module_to_current_screen_success"))
                }
            } else if (data["status"] == "error") {
            	qytx.app.dialog.alert(data["content"]);
            }
        }
    });
}

//获取随机数
function getOaRandom() {
    return new Date().getTime() + "" + Math.random();
}


function initBtnAddScreen() {
    $("#btnAddScreen").click(function () {
        addAnDesktopPage();
    });
}

function addAnDesktopPage() {
	
    //添加一个个人桌面页
	qytx.app.ajax({
        url: basePath + "logined/desktop/addDesktopPage.action" + "?_r=" + getOaRandom(),
        type: "post",
        dataType: 'json',
        data: {},
        success: function (data) {
            data = eval(data);
            if (data["status"] == "success") {
                var pageCount = $("#screen_list .minscreenceil").length;
                if(pageCount>=11){
                	$("#btnAddScreen").hide();
                }
                var html = "<li class='minscreenceil' id='miniPage_" + data["id"] + "' pageId='" + data["id"] + "' index='" + data["orderNo"] + "'>" + (pageCount + 1);
                html += "<span title='移除此屏' class='closebtn' style='display: none;'></span></li>";
                $("#btnAddScreen").before(html);
                artDialog.opener.addAnScreenPage(data);
                showMsg(sprintf("desktop.add_screen_success"));
                //artDialog.opener.location.reload();
            } else {
            	qytx.app.dialog.alert("add screen error");
            }
        }
    });
}

//初始化删除屏幕功能
function initBtnDeleteScreen() {
    $("#screen_list .minscreenceil .closebtn").live("click", function () {
    	var count = $("#screen_list .minscreenceil").length;
    	if(count<=1){
    		qytx.app.dialog.alert("至少保留一个分屏!");
    		return false;
    	}
        //删除这个屏幕
        var screenLi = $(this).parent();
        var pageId = screenLi.attr("pageId");
        qytx.app.dialog.confirm(sprintf("desktop.confirm_delete_desktop_screen"),
            function () {
                //删除一个个人桌面页
        	qytx.app.ajax({
                    url: basePath + "logined/desktop/deleteDesktopPage.action",
                    type: "post",
                    dataType: 'text',
                    data: {
                        desktopPageId: pageId
                    },
                    success: function (data) {
                        if (data != "") {
                        	qytx.app.dialog.alert(data);
                        } else {
                            screenLi.remove();
                            if($("#screen_list .minscreenceil").length<12){
                            	$("#btnAddScreen").show();
                            }
                            updateScreenDisplayNo();
                            //从个人桌面上删除该屏幕
                            artDialog.opener.removeAnScreenPage(pageId);
                            showMsg(sprintf("desktop.delete_screen_success"));
                        }
                    }
                });
            },
            function () {
            });
    })
}

//更新每个屏幕的显示号
function updateScreenDisplayNo() {
    var i = 1;
    $("#screen_list .minscreenceil").each(function () {
        var span = $(this).find("span");
        $(this).html((i++));
        span.appendTo($(this));
    });
}

var beforeMovePageIds = "";

//初始化App图标拖拽移动事件
function initDesktopPageSortable() {

    $("#screen_list .ui-sortable").sortable({
        revert: true,
        tolerance: 'pointer',
        connectWith: "#screen_list .ui-sortable",
        cancel: "#btnAddScreen",
        scroll: false,
        stop: function (e, ui) {
        },
        start: function (e, ui) {
            beforeMovePageIds = getPageIds();
            //console.log("beforeMovePageIds:" + beforeMovePageIds);
        },
        update: function (e, ui) {
            //console.log("after move:" + getPageIds());
            if (beforeMovePageIds == getPageIds()) {
                return;
            }
            setTimeout(function () {
                reorderMiniDesktopPage();
            }, 0);
            beforeMovePageIds = "";
        }
    });
}

//获取pageId，英文逗号分割
function getPageIds() {
    var desktopPageIds = "";
    $("#screen_list .ui-sortable .minscreenceil").each(function () {
        var pageId = $(this).attr("pageId");
        //sortable 会临时生成一个[visibility:hidden]的页占据位置,此时pageId不存在
        if (pageId != undefined && $.trim(pageId) != "") {
            desktopPageIds += ",";
            desktopPageIds += $(this).attr("pageId");
        }
    });
    //console.log("desktopPageIds:" + desktopPageIds);
    if (desktopPageIds.length > 1) {
        desktopPageIds = desktopPageIds.substr(1);
    }
    return desktopPageIds;
}

//对桌面页重新排序
function reorderMiniDesktopPage() {
    var pages = $("#screen_list .minscreenceil");
    if (pages.length > 1) {
        var desktopPageIds = getPageIds();
        //console.log("will reorder the mini page:desktopPageIds:" + desktopPageIds);
        qytx.app.ajax({
            url: basePath + "logined/desktop/reorderDesktopPage.action",
            type: "post",
            dataType: 'text',
            data: {
                desktopPageIds: desktopPageIds
            },
            success: function (data) {
                if (data != "") {
                	qytx.app.dialog.alert(data);
                } else {
                    //重新排序父页面
                    artDialog.opener.reorderDesktopPages(desktopPageIds);
                    showMsg(sprintf("desktop.reorder_screen_success"));
                }
            }
        });
    }
}

function initSomePageContent() {
    $("#btnAppSet").click(function () {
        $("#appPageDom").show();//隐藏应用设置
        $("#screenPageDom").hide();//打开分屏设置
        $("#btnAppSet").addClass("on");
        $("#btnScreenSet").removeClass("on");
    });//点击应用设置
    $("#btnScreenSet").click(function () {
        $("#appPageDom").hide();//隐藏应用设置
        $("#screenPageDom").show();//打开分屏设置
        $("#btnScreenSet").addClass("on");
        $("#btnAppSet").removeClass("on");
    });//点击分屏设置
}

function initAppListScrollClick() {
    $("#app_cate_list ul li").click(function () {
        $("#app_cate_list ul li").removeClass("current");
        $(this).addClass("current");
        getNotDesktopModules($(this).attr("moduleId"));
    })
}

//初始化选中第一个并获取第一个菜单所有的子项
function initFirstAppInList() {
    var firstApp = $("#app_cate_list ul li:first");
    if (firstApp.length > 0) {
        firstApp.addClass("current");
        getNotDesktopModules(firstApp.attr("moduleId"));
    }
}

//获取子菜单项放于右边的盒子中
function getNotDesktopModules(parentModuleId) {
	 
	qytx.app.ajax({
        url: basePath + "logined/desktop/getNotInDesktopModules.action" + "?_r=" + getOaRandom(),
        type: "post",
        dataType: 'json',
        data: {moduleInfoId: parentModuleId},
        success: function (data) {
            data = eval(data);
            if (data["status"] == "error") {
            	qytx.app.dialog.alert(data["content"]);
            } else {
                var moduleList = eval(data["content"]);
                var html = "";
                for (var i = 0; i < moduleList.length; i++) {
                    var moduleInfo = moduleList[i];
                    var moduleInfoId = moduleInfo["moduleId"];
                    var moduleName = moduleInfo["moduleName"];
                    var defaultIcon = _cssType+"/images/module_icons/share.png";
                    var moduleIcon = moduleInfo["icon"];
                    if (typeof(moduleIcon) == "undefined" || moduleIcon == "") {
                        moduleIcon = defaultIcon;
                    }
                    moduleIcon = basePath + moduleIcon;
                    html += "<li moduleInfoId='" + moduleInfoId + "' id='module_" + moduleInfoId + "' title='" + moduleName + "' > ";
                    html += "<img  src='" + moduleIcon + "'   />";
                    html += " <p>" + moduleName + "</p> ";
                    html += " </li>";
                }
                $("#app_list_record").html(html);
            }
        }
    });
}

var APP_ITEM_HEIGHT = 28;
var MIN_PNAEL_HEIGHT = 11 * APP_ITEM_HEIGHT;
var SCROLL_HEIGHT = 4 * APP_ITEM_HEIGHT;

function initAppListScroll() {
    var su = $("#app_cate_list .scroll-up:first");
    var sd = $("#app_cate_list .scroll-down:first");
    var scrollHeight = $("#app_cate_list ul").attr('scrollHeight');
    var orgheight = $("#app_cate_list ul").height();
    if (orgheight < scrollHeight) {
        var height = scrollHeight > MIN_PNAEL_HEIGHT ? MIN_PNAEL_HEIGHT : scrollHeight;
        $("#app_cate_list ul").height(height);
    }

    if (orgheight >= scrollHeight) {
        su.hide();
        sd.hide();
    }
    initAppScroll('app_cate_list');
}
//菜单滚动箭头事件,id为app_cate_list
function initAppScroll(id) {
    //菜单向上滚动箭头事件
    $('#' + id + ' > .scroll-up:first').hover(
        function () {
            $(this).addClass('scroll-up-hover');
        },
        function () {
            $(this).removeClass('scroll-up-hover');
        }
    );

    //点击向上箭头
    $('#' + id + ' > .scroll-up:first').click(
        function () {
            var ul = $('#' + id + ' > ul:first');
            ul.animate({'scrollTop': (ul.scrollTop() - SCROLL_HEIGHT)}, 600);
        }
    );

    //向下滚动箭头事件
    $('#' + id + ' > .scroll-down:first').hover(
        function () {
            $(this).addClass('scroll-down-hover');
        },
        function () {
            $(this).removeClass('scroll-down-hover');
        }
    );

    //点击向下箭头
    $('#' + id + ' > .scroll-down:first').click(
        function () {
            var ul = $('#' + id + ' > ul:first');
            ul.animate({'scrollTop': (ul.scrollTop() + SCROLL_HEIGHT)}, 600);
        }
    );
}
//鼠标滑过屏幕样式
$("#screenPageDom #screen_list ul li.minscreenceil").live('mouseenter', function () {
//    $(this).css({"font-size": "60px"});
    if ($('span.closebtn', this).length <= 0)
        $(this).append("<span class='closebtn' title=" + td_lang.inc.msg_79 + "></span>");//'移除此屏'
    $('span.closebtn', this).show();
});

$("#screenPageDom #screen_list ul li.minscreenceil").live('mouseleave', function () {
    $(this).css({"font-size": ""});
    $('span.closebtn', this).hide();
});