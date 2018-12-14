/**
 * 功能:邮件箱功能公用js脚本
 * 版本:1.0
 */

/**
 * 数字输入框绑定keyup事件，需要指定class为numberKeyUp
 */
function bindInputNumberKeyUpValid() {
    $(".numberKeyUp").bind("keyup",function(){
        this.value=this.value.replace(/[^0-9]/g,'')
    })
}

$(document).ready(function(){
    bindInputNumberKeyUpValid();
});

//获取随机数
function getOaRandom() {
    return new Date().getTime() + "" + Math.random();
}

/**
 * 检查邮件箱名是否已存在--后期实现
 * @param callBack
 */
function checkBoxNameExist(callBack){

}