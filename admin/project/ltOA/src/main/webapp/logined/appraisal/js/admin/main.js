var tid;//任务id
var menuSrc = new Array();//菜单
$(document).ready(function () {
	tid=$("#tid").val();
	menuSrc.push(basePath+"logined/appraisal/jsp/staff/appraisalInfo.jsp?tid="+tid+"&type=1");//0:考核信息
    menuSrc.push(basePath+"logined/appraisal/jsp/admin/checkProgress.jsp?tid="+tid);//1:考核进度
    menuSrc.push(basePath+"logined/appraisal/jsp/staff/leadResultAppr.jsp?tid="+tid+"&view_type=1");//2:考核成绩
});

/**
 * 切换菜单
 * @param i
 */
function changeIframe(src){
	$("#MyIframe").attr("src",src);
}

/**
 * 切换菜单
 * @param i
 */
function clickMenu(i){
	$("#MyIframe").attr("src",menuSrc[i]);
}

function clickKhtr() {
	$("#khtr").click();
}



