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

var _loadMoreId = "";
//第一个参数为loadMore所在DIV的ID，第二个参数为回调方法
function initLoadMore(loadMoreId, callBack, scrollArea) {
    var loadMoreDiv = document.getElementById(loadMoreId);
    document.body.addEventListener(START_EV, function (e) {
    
    	
        if (_isInViewArea(loadMoreDiv, scrollArea)) {
            var point = hasTouch ? e.touches[0] : e;
            loadMoreDiv._pageY = point.pageY;
            //console.log("start:"+point.pageY);
        } else {
            loadMoreDiv._pageY = -1;
        }
    }, false);

    document.body.addEventListener(END_EV, function (e) {
        var point = hasTouch ? e.changedTouches[0] : e;
        var pageYStart = loadMoreDiv._pageY;
        if (pageYStart != undefined && parseInt(pageYStart) != -1) {
            pageYStart = parseInt(pageYStart);
            var pageYEnd = point.pageY;
            //console.log("end:"+point.pageY);
            if (pageYEnd - pageYStart < 0) {
                _loadMoreCallBack(callBack, loadMoreId);
            }
        }
    }, false);
    loadMoreDiv.onclick = function () {
        _loadMoreCallBack(callBack, loadMoreId)
    };
    _loadMoreId = loadMoreId;
}

function _loadMoreCallBack(callBack, loadMoreId) {
    var loadMoreDiv = document.getElementById(loadMoreId);
    if (loadMoreDiv.getAttribute("loadAll")!="loadAll") {
        loadMore.loading(loadMoreId);
        callBack();
    }
}

//判断某个接点是否在可见区域中
function _isInViewArea(elementNode, scrollArea) {
    var offsetTop = elementNode.offsetTop;
    if (null != scrollArea && undefined != scrollArea){
    	return $("#"+scrollArea)[0].offsetHeight + $("#"+scrollArea)[0].scrollTop >= $("#"+scrollArea)[0].scrollHeight;
    }
    return offsetTop >= $(window).scrollTop() && offsetTop < ($(window).scrollTop() + $(window).height());
}

function LoadMore() {
    this.loading = function (loadMoreId) {
        if (loadMoreId == undefined) {
            loadMoreId = _loadMoreId;
        }
        $("#" + loadMoreId).addClass("loading").find(".pullUpLabel").text("正在加载...");
    };
    this.loadAll = function (loadMoreId) {
        if (loadMoreId == undefined) {
            loadMoreId = _loadMoreId;
        }
        $("#" + loadMoreId).attr("loadAll", "loadAll").find(".pullUpLabel").text("已完全加载").prev(".pullUpIcon").hide();
    };
    this.more = function (loadMoreId) {
        if (loadMoreId == undefined) {
            loadMoreId = _loadMoreId;
        }
        $("#" + loadMoreId).removeAttr("loadAll").attr("class", "pullUp").find(".pullUpLabel").text("上拉加载更多...").prev(".pullUpIcon").show();
    }
}
var loadMore = new LoadMore();