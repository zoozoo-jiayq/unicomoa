/**
 *用户选择js
 */
var _userSelectId = "userSelect" + new Date().getTime();
$(document).ready(function () {
//    $(document.body).append("<tr style='display:none' id='" + _userSelectId + "'><td class='pd0' colspan='2'></td></tr>");
//    setTimeout(function () {
//        var url = basePath + "wap/logined/addressbook/list_select.jsp"+wapParam;
//        $("#" + _userSelectId + " td").load(url,
//            function (response, status, xhr) {
//        	initSelectUse111r();
//            });
//    }, 1);

});

var _userSelectCallBack, _userSelectDataBack;

/**
 *
 * @param btnObj 选择按钮的dom或者jquery对象
 * @param callBack 选择人员完成后的回调方法
 * @param dataBack 该值原样返回
 */
function openUserSelect(btnObj, callBack, dataBack) {
    var userSelect = $("#_userSelectId");
    if (userSelect.is(":visible")) {
        _moveToBottomAndHide();
    }else{
        _userSelectCallBack = callBack;
        _userSelectDataBack = dataBack;
        $("#_userSelectId").insertAfter($(btnObj).parents("tr")).show();
        _initSelectUser();
    }
}
//内部方法，调用回调方法并传值
function _userSelectSelfCallBack(userMap) {
    _userSelectCallBack(userMap, _userSelectDataBack);
    _userSelectCallBack = undefined;
    _userSelectDataBack = undefined;
    _moveToBottomAndHide();
}
//内部方法，将选择人员部门dom内容移动到页面底部并隐藏
function _moveToBottomAndHide(){
    $("#_userSelectId").hide();
}