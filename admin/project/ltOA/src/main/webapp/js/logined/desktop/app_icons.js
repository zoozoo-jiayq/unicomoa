/**
 * 应用的icon图标
 */

var appIcons = {
    "电子邮件": "/images/app_icons/email.png",
    "个人事务": "/images/menu/mytable.png",
    "工作流": "/images/menu/workflow.png",
    "行政办公": "/images/menu/mytable.png",
    "知识管理": "/images/menu/knowledge.png",
    "系统管理": "/images/menu/system.png",
    "消息管理": "/images/app_icons/comm.png",
    "即时消息": "/images/app_icons/comm.png",
    "default": "/images/app_icons/info.png"
};


/**
 * 获取App的Icon图标
 * @param appName 应用的名称，Module表中的moduleName
 * @return 图标路径
 */
function getAppIcon(appName) {
    var defaultIcon = appIcons["default"];
    if (typeof(appName) == "undefined" || appName == "") {
        return defaultIcon;
    }
    var icon = appIcons[appName];
    if (typeof(icon) == "undefined") {
        return defaultIcon;
    } else {
        return icon;
    }
}
