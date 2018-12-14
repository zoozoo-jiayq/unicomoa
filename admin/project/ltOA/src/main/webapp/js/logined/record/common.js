/**
 * 功能:人事档案模块共用脚本
 * 版本:1.0
 */

/**
 * 在人事档案右边Frame中打开新页面
 * @param url 新页面地址
 */
function openURLForRecord(url) {
    window.open(url, "recordRightFrame");
}


/**
 * 弹出服务端返回的错误信息
 */
function initShowErrorMsg() {
    var errorMsg = $("#errorMsg").val();
    errorMsg = $.trim(errorMsg);
    if (errorMsg != "") {
        qytx.app.dialog.alert(errorMsg);
    }
}

//对于存在 class为date的输入框，进行My97DatePicker初始化
function initMy97DatePicker() {
    $(".date").addClass("Wdate").bind("click focus",function () {
        WdatePicker({maxDate:'%y-%M-%d'});
    });
}

/**
 *修改时初始化 select值
 */
function setSelectDefaultValue() {
    $(".selectHidden").each(function () {
        if ($(this).val() != "") {
            var theId = $(this).attr("id").toString();
            var selectId = theId.substring(0, theId.lastIndexOf("_"));
            $("#" + selectId).val($(this).val());
        }
    });
}

/**
 *修改时，初始化Radio值
 */
function setRadioDefaultValue() {
    $(".radioHidden").each(function () {
        if ($(this).val() != "") {
            var theId = $(this).attr("id").toString();
            var radio = theId.substring(0, theId.lastIndexOf("_"));
            $("#" + radio+"_"+$(this).val()).attr("checked","checked");
        }
    });
}

/**
 *对于Input输入框日期数据格式化成短日期形式，如2012-01-01
 */
function dateStr2ShortForInput() {
    $(".date").each(function () {
        var dateVal = $(this).val();
        if(typeof(dateVal) != "undefined"){
            dateVal= $.trim(dateVal);
            if (dateVal.toString().length > 10) {
                $(this).val(dateVal.substr(0, 10));
            }
        }
    });
}

/**
 *对于HTML标签文本日期数据格式化成短日期形式，如2012-01-01
 */
function dateStr2ShortForTagText() {
    $(".date").each(function () {
        var dateVal = $(this).html();
        if(typeof(dateVal) != "undefined"){
            dateVal= $.trim(dateVal);
            if (dateVal.toString().length > 10) {
                $(this).html(dateVal.substr(0, 10));
            }
        }
    });
}


/**
 * 预览照片
 */
function displayPhoto() {
    var photoName = $("#photoUrlHidden").val();
    if (photoName != "") {
        var photoUrl = basePath + "filemanager/downFileByFilePath.action?filePath=" + photoName;
        $("#photoImg").attr("src", photoUrl);
//        $("#deletePhoto").show();
    }
}

//缩短文本显示并tips完整内容
function miniInfoAndTipsIt(tagClassName, length) {
    var showLength = 20;
    if (typeof(length) != "undefined") {
        showLength = parseInt(length,10);
    }
    $("." + tagClassName).each(function () {
        var spanVal = trimString($(this).html());
        if (spanVal.toString().length > showLength) {
            $(this).attr("title", spanVal);
            $(this).html(spanVal.substr(0, showLength) + "...")
        } else {
            $(this).html(spanVal);
        }
    });
}

/**
 * 数字输入框绑定keyup事件，需要指定class为numberKeyUp
 */
function bindInputNumberKeyUpValid() {
    $(".numberKeyUp").bind("keyup",function(){
        this.value=this.value.replace(/[^0-9]/g,'')
    })
}

/**
 * 公用表单加载完成后的初始化方法
 */
$(document).ready(function(){
    bindInputNumberKeyUpValid();
});
