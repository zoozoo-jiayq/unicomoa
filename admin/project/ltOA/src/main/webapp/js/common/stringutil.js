/**
 * 字符串帮助类
 */


/**
 * 清除前后空格
 * @return {String}
 */
String.prototype.trim = function() {
    return this.replace(/^\s*|\s*$/g, "");
}