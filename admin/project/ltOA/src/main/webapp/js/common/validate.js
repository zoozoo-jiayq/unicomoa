/**
   js验证
 */
/***
 * 常用正则表达式
 * @constructor
 */
var RegExps = function(){};
RegExps.isEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
RegExps.isTel = /^0(([1-9]\d)|([3-9]\d{2}))\d{8}$/;//格式区号+号码 037165711369
RegExps.isPhone = /^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
RegExps.isNaturalNumber = /^\d+(\.\d+)?$/;//非负浮点数
RegExps.isNumber = /^[-\+]?\d+(\.\d+)?$/;
RegExps.isIdCard = /(^\d{15}$)|(^\d{17}[0-9Xx]$)/;
RegExps.isMoney = /^\d+(\.\d+)?$/;
RegExps.isZip = /^[1-9]\d{5}$/;
RegExps.isQQ = /^[1-9]\d{4,10}$/;
RegExps.isInt = /^[-\+]?\d+$/;
RegExps.isEnglish = /^[A-Za-z]+$/;
RegExps.isChinese =  /^[\u0391-\uFFE5]+$/;
RegExps.isUrl =/^http(s)?:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\:+!]*([^<>])*$/;
RegExps.isDate = /^\d{4}-\d{1,2}-\d{1,2}$/;
RegExps.isTime = /^\d{4}-\d{1,2}-\d{1,2}\s\d{1,2}:\d{1,2}:\d{1,2}$/;
RegExps.isNaturalInt=/^[0-9]*[1-9][0-9]*$/;  //非负整数（正整数   +   0）;
RegExps.isLoginPass=/^[0-9a-zA-Z_]\w{5,17}$/;  //6-16位，区分大小写，只能使用字母、数字
RegExps.isLoginName= /^\w+$/;  //登陆名格式验证 用户名必须为数字字母或者下划线！


/**
 * 验证密码格式
 * @param pass
 */
function isLoginName(loginName)
{
    return checkpatrn(RegExps.isLoginName,loginName);
}

/**
 * 验证密码格式
 * @param pass
 */
function isLoginPass(pass)
{
    return checkpatrn(RegExps.isLoginPass,pass);
}
/**
 * 验证是否日期格式
 * @param s
 * @return {*}
 */
function isDate(s)
{
    return checkpatrn(RegExps.isDate,s);
}
/**
 * 验证是否非负整数（正整数   +   0）
 * @param number
 * @return {*}
 */
function isNaturalInt(number)
{
    return checkpatrn(RegExps.isNaturalInt,number);
}

/**
 * 验证是否URL
 * @param s
 * @return {*}
 */
function isUrl(s)
{
    return checkpatrn(RegExps.isUrl,s);
}

/**
 * 验证是否中文
 * @param s
 * @return {*}
 */
function isChinese(s)
{
    return checkpatrn(RegExps.isChinese,s);
}
/**
 * 验证是否英文
 * @param s
 * @return {*}
 */
function isEnglish(s)
{
    return checkpatrn(RegExps.isEnglish,s);
}

/**
 * 验证是否整数
 * @param number
 * @return {*}
 */
function isInt(number)
{
    return checkpatrn(RegExps.isInt,number);
}
/**
 * 验证是否qq
 * @param qq
 * @return {*}
 */
function isQQ(qq)
{
    return checkpatrn(RegExps.isQQ,qq);
}
/**
 * 验证是否区号
 * @param zip
 * @return {*}
 */
function isZip(zip)
{
    return checkpatrn(RegExps.isZip,zip);
}
/**
 * 验证是否数字
 * @param number
 * @return {*}
 */
function isIdCard(idCard)
{
    return checkpatrn(RegExps.isIdCard,idCard);
}
/**
 * 验证是否数字
 * @param number
 * @return {*}
 */
function isNumber(number)
{
    return checkpatrn(RegExps.isNumber,number);
}
/**
 * 验证邮箱
 * @param email
 */
function isEmail(email)
{
    return checkpatrn(RegExps.isEmail,email);
}
/**
 * 验证固定电话号码
 * @param tel
 */
function  isTel(tel)
{
    return checkpatrn(RegExps.isTel,tel);
}

/**
 * 验证/非负浮点数
 * @param number
 * @return {*}
 */
function isNaturalNumber(number)
{
    return checkpatrn(RegExps.isNaturalNumber,number);
}

/**
 * 验证手机号码
 * @param phone
 * @return {*}
 */
function isPhone(phone)
{
    return checkpatrn(RegExps.isPhone,phone);
}

/**
 * 验证正则表达式
 * @param patrn 正则表达式
 * @param s   要检验的数据
 */
function checkpatrn(patrn,s)
{
    if (!patrn.exec(s)) return false
    return true
}

