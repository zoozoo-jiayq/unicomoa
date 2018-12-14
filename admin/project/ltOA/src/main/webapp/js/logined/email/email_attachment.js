/**
 * 功能:邮件附件处理相关js方法类,需要引入json2.js支持
 * 版本:1.0
 * 说明：附件JSON串格式
 * [{"name":"xxxxxx.xls","path":"/ddd/ccc/asdfasdfa.xls","size":"454645"},
 * {"name":"xxxxxx222.xls","path":"/ddd/ccc/asdfasdfa2222.xls","size":"454645222"}]
 */

/**
 * 添加一个附件
 * @param path 附件路径
 * @param name 附件名称
 * @param size 附件大小
 * @param jsonListVal 附件JSON串
 */
function addAttachment(path, name, size, jsonListVal) {

    //把新的附件信息构造成一个Map
    var newJson = {"path": path, "name": name, "size": size};
    var result = "";
    if (typeof(jsonListVal) != "undefined" && jsonListVal != "") {
        jsonListVal = JSON.parse(jsonListVal);
        for (var i = 0; i < jsonListVal.length; i++) {
            result += JSON.stringify(jsonListVal[i]);
            result += ",";
        }
    }
    result += JSON.stringify(newJson);
    result += ",";
    result = result.substr(0, result.length - 1);
    result = "[" + result + "]";
    //alert("新增后:" + result);
    return result;
}

/**
 * 删除一个附件
 * @param path 要删除的附件的path
 * @param jsonListVal 附件JSON串
 */
function deleteAttachmentByPath(path, jsonListVal) {
    var result = "";
    if (typeof(jsonListVal) != "undefined" && jsonListVal != "") {
        jsonListVal = JSON.parse(jsonListVal);
        for (var i = 0; i < jsonListVal.length; i++) {
            var jsonVal = jsonListVal[i];
            //alert(jsonVal["path"]);
            if (jsonVal["path"] != path) {
                result += JSON.stringify(jsonVal);
                result += ",";
            }
        }
    }
    if (result.indexOf(",") != -1) {
        result = result.substr(0, result.length - 1);
    }
    if(result!=""){
        result = "[" + result + "]";
    }
    //alert("删除后:" + result);
    return result;
}

/**
 * 获取附件JSON字符串中所有附件的总大小
 * @param jsonListVal 附件字符串
 * @return {number} 总大小，byte为单位
 */
function getTotalSizeByJson(jsonListVal){
    var size = 0;
    if (typeof(jsonListVal) != "undefined" && jsonListVal != "") {
        jsonListVal = JSON.parse(jsonListVal);
        for (var i = 0; i < jsonListVal.length; i++) {
            size+=parseInt(jsonListVal[i]["size"],10);
        }
    }
    return size;
}