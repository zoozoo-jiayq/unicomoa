var tid;//任务id
var menuSrc = new Array();//菜单
$(document).ready(function () {
	tid=$("#tid").val();
	
	menuSrc.push(basePath+"logined/appraisal/jsp/staff/appraisalInfo.jsp?tid="+tid);//0:考核信息
	menuSrc.push(basePath+"logined/appraisal/jsp/staff/self-assessment.jsp?zt=1&tid="+tid);//1自评自述
	menuSrc.push(basePath+"logined/appraisal/jsp/staff/checkOther.jsp?tid="+tid);//2考核他人
	menuSrc.push(basePath+"logined/appraisal/jsp/staff/leadResultAppr.jsp?tid="+tid);//3结果审批
	menuSrc.push(basePath+"logined/appraisal/jsp/staff/myResult.jsp?tid="+tid);//4我的考核成绩
	staffMenu();
	setInterval("staffMenu()",3000);//1000为1秒钟
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


/**
 * 刷新菜单 
 */
function staffMenu(){
	var dataParam = {
            "tid":$("#tid").val()
        }
	$.ajax({
        url : basePath+"performance/setup_staffMenu.action",
        type : 'post',
        dataType : 'json',
        data : dataParam,
        success : function(data) {
        	//自述自评
        	if(data.zszp==0){//未自述自评
        		menuSrc[1] = basePath+"logined/appraisal/jsp/staff/self-assessment.jsp?zt=1&tid=" +tid;
        		$("#zpzsPoint").addClass("disblock");
        	}else if(data.zszp==1){//已自述
        		menuSrc[1] = basePath+"logined/appraisal/jsp/staff/self-assessment_details.jsp?tid="+tid+"&type=1";
        		$("#zpzsPoint").removeClass("disblock");
        	}else{//无数据
        		menuSrc[1] = basePath+"logined/appraisal/jsp/no_data.jsp?type=0";
        		$("#zpzsPoint").removeClass("disblock");
        	}
        	
        	//考核他人
        	if(data.dfry==0){//0当前人员是打分人员 需要红点
        		$("#khtrPoint").addClass("disblock");
        	}else if(data.dfry==1){//1当前人员是打分人员 不需要红点
        		$("#khtrPoint").removeClass("disblock");
        	}else{//1 当前人员是打分人员
        		menuSrc[2] = basePath+"logined/appraisal/jsp/no_data.jsp?type=1";
        		$("#khtrPoint").removeClass("disblock");
        	}
        	
        	//结果审批
        	if(data.sprw==0){//0当前人员是审核人员 需要红点
        		$("#jgshPoint").addClass("disblock");
        	}else if(data.sprw==1){//1当前人员是审核人员 不需要红点
        		$("#jgshPoint").removeClass("disblock");
        	}else if(data.sprw==2){//2评分没有结束
        		menuSrc[3] = basePath+"logined/appraisal/jsp/no_data.jsp?type=4";
        		$("#jgshPoint").removeClass("disblock");
        	}else{//-1当前人员不是审核人员
        		menuSrc[3] = basePath+"logined/appraisal/jsp/no_data.jsp?type=2";
        		$("#jgshPoint").removeClass("disblock");
        	}
        	
        	//我的考核成绩
        	if(data.wdcj==0){//0当前人员是被考核人员 考核结束
        		menuSrc[4] = basePath+"logined/appraisal/jsp/staff/myResult.jsp?tid="+tid;
        	}else{//1当前人员是被考核人员
        		menuSrc[4] = basePath+"logined/appraisal/jsp/no_data.jsp?type=3";
        	}
        	
        	
        	
        }
    });
}

