/**
 * 字符串帮助类
 */


function StringBuilder() {
    this._string_ = new Array();
}

StringBuilder.prototype.Append = function (str) {
    this._string_.push(str);
}

StringBuilder.prototype.toString = function () {
    return this._string_.join("");
}