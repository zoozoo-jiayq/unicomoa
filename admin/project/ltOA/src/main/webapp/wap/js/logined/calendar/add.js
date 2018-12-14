
	$(document).ready(function() {
		var calendarId = $("#calendarId").val();
		if(calendarId==""){
			$("#titleView").html('<h1>新建日程</h1>');
		}else{
			$("#titleView").html('<h1>修改日程</h1>');
		}
		//日程类型
		$('#selctCalType').hradio();
		$("#calTypeTd").click(function(){	
			$(".dialog").show();
			$("#selctCalType").show();
			$("form input").attr("disabled","disabled");
			$("form textarea").attr("disabled","disabled");
		});
	   $("#selctCalType ul li label").click(function(){
			if ($(this).hasClass("hRadio_Checked"))
			{
			 	$("#selctCalType").hide();
				$(".dialog").hide();
				$("form input").removeAttr("disabled");
				$("form textarea").removeAttr("disabled");
			}
			var calType = $('#selctCalType :checked').val();
			
			if(calType==1){
				$("#calType").html('日事务');
			}else if(calType==2){
				$("#calType").html('周事务');
			}else if(calType==3){
				$("#calType").html('月事务');
			}
			$("#calTypeId").val(calType);
		});
		
		//事务类型
		$('#selctWorkType').hradio();
		$("#workTypeTd").click(function(){	
			$(".dialog").show();
			$("#selctWorkType").show();
			$("form input").attr("disabled","disabled");
			$("form textarea").attr("disabled","disabled");
		});
		
	    $("#selctWorkType ul li label").click(function(){
			if ($(this).hasClass("hRadio_Checked"))
			{
			 	$("#selctWorkType").hide();
				$(".dialog").hide();
				$("form input").removeAttr("disabled");
				$("form textarea").removeAttr("disabled");
			}
			var workType = $('#selctWorkType :checked').val();
			
			if(workType==1){
				$("#workType").html('工作事务');
			}else if(workType==2){
				$("#workType").html('个人事务');
			}
			$("#workTypeId").val(workType);
		});
		
		
		//优先级
		$('#selctCalLevel').hradio();
		$("#calLevelTd").click(function(){	
			$(".dialog").show();
			$("#selctCalLevel").show();
			$("form input").attr("disabled","disabled");
			$("form textarea").attr("disabled","disabled");
		});
	   $("#selctCalLevel ul li label").click(function(){
			if ($(this).hasClass("hRadio_Checked"))
			{
			 	$("#selctCalLevel").hide();
				$(".dialog").hide();
				$("form input").removeAttr("disabled");
				$("form textarea").removeAttr("disabled");
			}
			var calLevel = $('#selctCalLevel :checked').val();
			
			if(calLevel==1){
				$("#calLevel").html('[重要/紧急]');
			}else if(calLevel==2){
				$("#calLevel").html('[重要/不紧急]');
			}else if(calLevel==3){
				$("#calLevel").html('[不重要/紧急]');
			}else if(calLevel==4){
				$("#calLevel").html('[不重要/不紧急]');
			}
			$("#calLevelId").val(calLevel);
		});
	   
	    //保存按钮绑定事件
	    $("#addCalendar").bind("click",function(){
	    	add();
	    });
	    
	    var calTypeId=$("#calTypeId").val();
	    if(calTypeId==1){
			$("#calType").html('日事务');
		}else if(calTypeId==2){
			$("#calType").html('周事务');
		}else if(calTypeId==3){
			$("#calType").html('月事务');
		}
	    
		var workTypeId = $('#workTypeId').val();
		if(workTypeId==1){
			$("#workType").html('工作事务');
		}else if(workType==2){
			$("#workTypeId").html('个人事务');
		}

		var calLevelId = $('#calLevelId').val();
		if(calLevelId==1){
			$("#calLevel").html('[重要/紧急]');
		}else if(calLevelId==2){
			$("#calLevel").html('[重要/不紧急]');
		}else if(calLevelId==3){
			$("#calLevel").html('[不重要/紧急]');
		}else if(calLevelId==4){
			$("#calLevel").html('[不重要/不紧急]');
		}
	});
	
	
	function strDateTime(str){
		var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
		var r = str.match(reg); 
		if(r==null){
			return false;
		} 
		var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
	}
	
	 function checkDate(sdate,edate){
	       if(sdate!=""&&edate!=""){
	    	   //输入不为空时；
	           // 对字符串进行处理
	           // 以 - / 或 空格 为分隔符, 将日期字符串分割为数组
	    	   var sdate1 = sdate.split(" ")[0].toString();
		       var edate1 =edate.split(" ")[0].toString();
		       
	    	   var sdate2 = sdate.split(" ")[1].toString();
		       var edate2 =edate.split(" ")[1].toString();

		       var date1 = sdate1.split("-");
	          var date2 = edate1.split("-");
	          
	          var date11 = sdate2.split(":");
	          var date22 = edate2.split(":");
	          var bool = true;
	           if(parseInt(date1[0].toString()+date1[1].toString() + date1[2].toString())
	        		    > parseInt(date2[0].toString()+date2[1].toString() + date2[2].toString())){
	        	   bool=false;
	        	   return bool;
	           }
	           else  if(parseInt(date1[0].toString()+date1[1].toString() + date1[2].toString())
	        		    == parseInt(date2[0].toString()+date2[1].toString() + date2[2].toString())){
	        	   if(parseInt(date11[0].toString()+date11[1].toString()+date11[2].toString())
	        			   >parseInt(date22[0].toString()+date22[1].toString()+date22[2].toString())){
		        	   bool=false;
		        	   return bool;
		           }
	           }
	           return bool;
	          
	       }else{
	             return true;
	       }
	 }
	 

	function add(){
		
		var calType = $("#calTypeId").val();
		var workType = $("#workTypeId").val();
		var calLevel = $("#calLevelId").val();
		var begTime = $.trim($("#begTime").val());
		var endTime = $.trim($("#endTime").val());
		var content = $.trim($("#content").val());
		var calendarId = $.trim($("#calendarId").val());
	    //日程类型否为空
	    if (calType=="") {
	        new WapDialog({title: "提示", content: "日程类型为空，请选择日程类型。"}).show();
	        return false;
	    }
	    //事务类型是否为空
	    if (workType=="") {
	        new WapDialog({title: "提示", content: "事务类型为空，请选择事务类型。"}).show();
	        return false;
	    }
	    //优先级是否为空
	    if (calLevel=="") {
	        new WapDialog({title: "提示", content: "优先级为空，请选择优先级。"}).show();
	        return false;
	    }
	    
	    //开始时间是否为空
	    if (begTime=="") {
	        new WapDialog({title: "提示", content: "起始时间为空，请输入起始时间。"}).show();
	        return false;
	    }
	    
	    //开始时间是否符合正确格式
	    if (!strDateTime(begTime)) {
	        new WapDialog({title: "提示", content: "起始时间不符合日期标准格式，请重新输入。"}).show();
	        return false;
	    }
	    
	    //结束时间是否为空
	    if (endTime=="") {
	        new WapDialog({title: "提示", content: "结束时间为空，请输入结束时间。"}).show();
	        return false;
	    }
	    
	    //结束时间是否符合正确格式
	    if (!strDateTime(endTime)) {
	        new WapDialog({title: "提示", content: "结束时间不符合日期标准格式，请重新输入。"}).show();
	        return false;
	    }
	    if(checkDate(begTime,endTime)==false){
	        new WapDialog({title: "提示", content: "结束时间不能早于起始时间，请重新输入。"}).show();
	        return false;
	    }
	    //结束时间是否为空
	    if (content=="") {
	        new WapDialog({title: "提示", content: "日程内容不能为空，请输入内容。"}).show();
	        return false;
	    }
		new WapDialog({
            title: "提示",
            content: "确认要提交吗？",
            btn1Name: "取消",
            btn2Name: "确认",
            btn2CallBack: function () {
				dataParam = {
				    	'calendarBeanVo.calType':calType,
				    	'calendarBeanVo.workType':workType,
				    	'calendarBeanVo.calLevel':calLevel,
				    	'calendarBeanVo.begTime':begTime,
				    	'calendarBeanVo.endTime':endTime,
				    	'calendarBeanVo.content':content,
				    	'calendarBeanVo.isRemaind':0,
				    	'calendarId':calendarId
	
				};
				$.ajax({
		               type: 'post',
		               url: basePath+"calendar/calendar_addCalendarBean.action"+"?random="+Math.random(),
		               data: dataParam,
		               dataType: 'json',
		               async:false,
		               success:function(data){
		               	   	window.location.href=basePath+'wap/logined/calendar/list.jsp';
		               }
			    });
            }
        }).show();
	}
