$(document).ready(function () {
	$("#confirm").click(function() {
		 
		var appraisalExamArray = new Array(); 
		
		var appraisalExam1 = {};
		appraisalExam1.bz = 1;
		
		var appraisalIndicatorsArray = new Array(); 
		var appraisalIndicators1 =  new Object();
		appraisalIndicators1.zbmc = "指标名称1";
		appraisalIndicators1.xh = "1";
		appraisalIndicators1.zbfl = 10;
		appraisalIndicators1.fz = 80;
		appraisalIndicators1.ms = "描述1";
		appraisalIndicatorsArray.push(appraisalIndicators1);
		alert(11);
		var appraisalIndicators2 =  new Object();
		appraisalIndicators2.zbmc = "指标名称1";
		appraisalIndicators2.xh = "1";
		appraisalIndicators2.zbfl = 10;
		appraisalIndicators2.fz = 80;
		appraisalIndicators2.ms = "描述1";
		appraisalIndicatorsArray.push(appraisalIndicators2);
		alert(22);
		appraisalExam1.idtList = appraisalIndicatorsArray;
		alert(33);
		appraisalExamArray.push(appraisalExam1);
		alert(JSON.stringify(appraisalExamArray));

		var paramData = {
			    'appraisalExamArr' : JSON.stringify(appraisalExamArray),
			    'at.vid' : 2
			};
		$.ajax({
		    url : basePath + "performance/task_updateAppraisalExam.action",
		    type : "post",
		    dataType : 'json',
		    data : paramData,
		    success : function(data) {
			    if (data == "success") {
//				    art.dialog.alert('删除成功！');
				    queryAllAffairs();
			    } else {
			    	art.dialog.alert(sprintf("affairs.msg_del_failure"));
			    }
		    }
		});
		return false;
	});
	
	
	$("#confirm2").click(function() {
		 
		 
		
		var aht = {};
		aht.fjmc = "月度考核指标";
		aht.fjms = "月度考核指标描述";
		aht.lx = 1;
		
		
		var ahiArray = new Array(); 
		var ahi =  new Object();
		ahi.jb = 1;
		ahi.mc = "及格";
		ahi.qsf = 60;
		ahi.jsf = 100;
		ahiArray.push(ahi);
		alert(111);
		var ahi2 =  new Object();
		ahi2.jb = 2;
		ahi2.mc = "不及格";
		ahi2.qsf = 0;
		ahi2.jsf = 59;
		ahiArray.push(ahi2);
		alert(222);
		aht.ahiList = ahiArray;
		 
	 
alert( JSON.stringify(aht))
		var paramData = {
			    'ahtParam' : JSON.stringify(aht)
			};
		$.ajax({
		    url : basePath + "performance/aht_saveOrupdate.action",
		    type : "post",
		    dataType : 'json',
		    data : paramData,
		    success : function(data) {
			    alert(123);
		    }
		});
		return false;
	});
});