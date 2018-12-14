/**
 *load more for wap oa by tang
 */
var m = Math,
    isTouchPad = (/hp-tablet/gi).test(navigator.appVersion),
    hasTouch = 'ontouchstart' in window && !isTouchPad,
    START_EV = hasTouch ? 'touchstart' : 'mousedown',
    MOVE_EV = hasTouch ? 'touchmove' : 'mousemove',
    END_EV = hasTouch ? 'touchend' : 'mouseup',
    CANCEL_EV = hasTouch ? 'touchcancel' : 'mouseup';

var _loadMoreDownId = "";
//第一个参数为loadMore所在DIV的ID，第二个参数为回调方法
function initLoadMoreDown(loadMoreDownId, callBack, scrollArea) {
    var loadMoreDownDiv = document.getElementById(loadMoreDownId);
    document.body.addEventListener(START_EV, function (e) {
        if (_isDownInViewArea(loadMoreDownDiv, scrollArea)) {
            var point = hasTouch ? e.touches[0] : e;
            loadMoreDownDiv._pageY = point.pageY;
            //console.log("start:"+point.pageY);
        } else {
            loadMoreDownDiv._pageY = -1;
        }
    }, false);

    document.body.addEventListener(END_EV, function (e) {
        var point = hasTouch ? e.changedTouches[0] : e;
        var pageYStart = loadMoreDownDiv._pageY;
        if (pageYStart != undefined && parseInt(pageYStart) != -1) {
            pageYStart = parseInt(pageYStart);
            var pageYEnd = point.pageY;
            //console.log("end:"+point.pageY);
            if (pageYEnd - pageYStart > 1) {
                _loadMoreDownCallBack(callBack, loadMoreDownId);
            }
        }
    }, false);
    loadMoreDownDiv.onclick = function () {
        _loadMoreDownCallBack(callBack, loadMoreDownId)
    };
    _loadMoreDownId = loadMoreDownId;
}

function _loadMoreDownCallBack(callBack, loadMoreDownId) {
    var loadMoreDiv = document.getElementById(loadMoreDownId);
    if (loadMoreDiv.getAttribute("loadAll")!="loadAll") {
        loadMoreDown.loading(loadMoreDownId);
        callBack();
    }
}

//判断某个接点是否在可见区域中
function _isDownInViewArea(elementNode, scrollArea) {
    var offsetTop = elementNode.offsetTop;
    if (null != scrollArea && undefined != scrollArea){
    	return $("#"+scrollArea).scrollTop() == 0;
    }
    return offsetTop >= $(window).scrollTop() && offsetTop < ($(window).scrollTop() + $(window).height());
}

function LoadMoreDown() {
    this.loading = function (loadMoreDownId) {
        if (loadMoreDownId == undefined) {
            loadMoreDownId = _loadMoreDownId;
        }
        $("#" + loadMoreDownId).addClass("loading").find(".pullDownLabel").text("正在加载...");
    };
    this.loadAll = function (loadMoreDownId) {
        if (loadMoreDownId == undefined) {
            loadMoreDownId = _loadMoreDownId;
        }
        $("#" + loadMoreDownId).attr("loadAll", "loadAll").find(".pullDownLabel").text("已完全加载").prev(".pullDownIcon").hide();
    };
    this.more = function (loadMoreDownId) {
        if (loadMoreDownId == undefined) {
            loadMoreDownId = _loadMoreDownId;
        }
        $("#" + loadMoreDownId).removeAttr("loadAll").attr("class", "pullDown").find(".pullDownLabel").text("下拉加载更多...").prev(".pullDownIcon").show();
    }
}
var loadMoreDown = new LoadMoreDown();