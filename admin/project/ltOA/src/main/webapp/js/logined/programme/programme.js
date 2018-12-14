//format date style
function getdate(begTime, begHour, begMinu) {
	if (begTime == " " || begTime == undefined||begTime=="") {
		return "2100-11-11 23:59:23";
	}
	var yyyy_MM_dd = begTime.substring(0, 10);
	if (begHour.length < 2) {
		begHour = "0" + begHour;
	}
	if (begMinu.length < 2) {
		begMinu = "0" + begMinu;
	}
	var result = yyyy_MM_dd + " " + begHour + ":" + begMinu + ":00";
	return result;
}

// format time
function getTime(hour, minu) {
	if (minu.length == 1) {
		minu = "0" + minu;
	}
	return hour + ":" + minu;
}

// list programme show the detail
function openArtDialogOnlyCancel(titleName, url, widthSet, heightSet) {
	qytx.app.dialog.openUrl({
		url	:	url,
		id	:	url,
		title	:titleName,
		size:	"L"
	})
}

function addOrUpdateProgrammeLogical(iframe, id, dialog) {
	
	var content = $(iframe.document).find("#dailyName").val();
	var begTime = $(iframe.document).find("#begTime").val();
	var begHour = $(iframe.document).find("#setHourInput").val();
	var begMinu = $(iframe.document).find("#setMinuteInput").val();
	
	var checkBoxAllDay = $(iframe.document).find("#checkBoxAllDay")[0].checked;
	if (checkBoxAllDay) {
		begHour = "00";
		begMinu = "00";
	}
	var endTime = $(iframe.document).find("#endTime").val();
	var endHour = $(iframe.document).find("#setEndHourInput").val();
	var endMinu = $(iframe.document).find("#setEndMinuteInput").val();
	if (checkBoxAllDay) {
		endHour = "23";
		endMinu = "59";
	}

	var remaindType = $(iframe.document).find("#remaindType").val();
	var remaindHour = $(iframe.document).find("#setRemaidHourInput").val();
	var remaindMinu = $(iframe.document).find("#setRemaidMinuteInput").val();

	var repeatType = 0;

	var begTimeObj = $(iframe.document).find("#begTime")[0];
	var endTimeObj = $(iframe.document).find("#endTime")[0];
	var contentObj = $(iframe.document).find("#dailyName")[0];
	var remaindTypeObj = $(iframe.document).find("#remaindType")[0];
	
	
	if (content == null || $.trim(content) == "") {
		qytx.app.valid.showObjError($(contentObj), 'calendar.calendar_content_not_null');
		return false;
	} else if (content.length > 1000) {
		qytx.app.valid.showObjError($(contentObj), 'calendar.calendar_content_too_long');
		return false;
	} else {
		qytx.app.valid.hideError($(contentObj));
	}
	if (begTime == null || begTime == "") {
		qytx.app.valid.showObjError($(begTimeObj), 'calendar.calendar_begTime_not_null');
		return false;
	} else {
		qytx.app.valid.hideError($(begTimeObj));
	}

	if (endTime != null && endTime != " " && endTime != '') {
		if (endTime < begTime) {
			qytx.app.valid.showObjError($(endTimeObj), 'calendar.calendar_less_than_begTime');
			return false;
		} else {
			qytx.app.valid.hideError($(endTimeObj));
		}
	}
	
	//判断结束时间与开始时间，结束时间不能小于开始时间
	var checkBoxHasEnd = $(iframe.document).find("#checkBoxEndTime")[0].checked;
	if(checkBoxHasEnd){
		if(endTime==null||$.trim(endTime)==""){
			qytx.app.valid.showObjError($(endTimeObj), 'calendar.calendar_endTime_not_null');
			return;
		}
	}
	if(!checkBoxAllDay && checkBoxHasEnd && begTime == endTime){
		if(Number(begHour) > Number(endHour)){
			//qytx.app.valid.showObjError($(endTimeObj), 'calendar.calendar_less_than_begTime');
			qytx.app.dialog.alert(sprintf('calendar.calendar_less_than_begTime'));
			return;
		}else if(Number(begHour) == Number(endHour)){
			if(Number(begMinu) > Number(endMinu)){
				//qytx.app.valid.showObjError($(endTimeObj), 'calendar.calendar_less_than_begTime');
				qytx.app.dialog.alert(sprintf('calendar.calendar_less_than_begTime'));
				return;
			}
		}
	}
	
	//判断提醒时间，当同一天的提醒时，提醒时间不能大于开始时间。
	if(!checkBoxAllDay && remaindType==1){
		if(Number(begHour) < Number(remaindHour)){
			//qytx.app.valid.showObjError($(remaindTypeObj), 'calendar.remaind_less_than_begTime');
			qytx.app.dialog.alert(sprintf('calendar.remaind_less_than_begTime'));
			return;
		}else if( Number(begHour) == Number(remaindHour)){
			if(Number(begMinu) < Number(remaindMinu)){
				//qytx.app.valid.showObjError($(remaindTypeObj), 'calendar.remaind_less_than_begTime');
				qytx.app.dialog.alert(sprintf('calendar.remaind_less_than_begTime'));
				return;
			}
		}
	}
	
	var type=$(iframe.document).find("#type").val();
//	qytx.app.dialog.confirm(sprintf("calendar.confirm_add_info"), function() {
		qytx.app.ajax({
			url : basePath + "calendar/addProgramme.action",
			data : {
				"bean.content" : content,
				"bean.repeatType" : repeatType,
				"bean.remaindTime" : getTime(remaindHour, remaindMinu),
				"bean.remaindType" : remaindType,
				// "bean.isSysRemaind" : isSysRemaind,
				"bean.isAllDay" : checkBoxAllDay ? 1 : 0,
				"isUpdate" : id,
				"bean.begTime":getdate(begTime, begHour, begMinu),
				"bean.endTime":checkBoxHasEnd ?getdate(endTime, endHour, endMinu):"",
				"bean.remindStyle":iframe.getSendType()
			},
			type : 'post',
			success : function(data) {
				if(type!=null&&type=="list"){
					window.location.href=basePath+"logined/programme/listProgramme.jsp";
				}else{
					var yMd = begTime.substring(0, 10);
					var year=yMd.split("-")[0];
					var month=yMd.split("-")[1];
					month=Number(month)-1;
					window.location.href=basePath+"logined/programme/myProgramme.jsp?year="+year+"&month="+month;
				}
				
			}
		});
//	});

	return false;

}

function openArtDialog(titleName, url, widthSet, heightSet, id) {
	var dialog = qytx.app.dialog.openUrl({ // open params begin
		url	:	url,
		id : url,
		title : titleName,
		size	:	"L",
		cancelVal : '关闭',
		ok : function() {
			var iframe = this.iframe.contentWindow;
			addOrUpdateProgrammeLogical(iframe, id, dialog);
		},
		cancel : true
	});
}

function deleteProgramme(id, repeatType,iframe) {
	var prompt = '确认要删除该日程吗？删除后不可恢复！';
	qytx.app.dialog.confirm(prompt, function() {
		qytx.app.ajax({
			url : basePath + "calendar/deleteProgramms.action",
			data : {
				'ids' : id
			},
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				var begTime=iframe.document.getElementById("begTime").innerHTML;
				var year=begTime.substring(0,4);
				var month=begTime.substring(5,7);
				month=Number(month)-1;
				window.location.href=basePath+"logined/programme/myProgramme.jsp?year="+year+"&month="+month;
			}
		});
		return true;
	}, function() {
		return;
	});
}

function openArtDialogOnCalendar(start, end) {
	art.dialog.data('start', start);
	art.dialog.data('end', end);

	qytx.app.dialog.openUrl({
		url	:	basePath + 'logined/programme/addProgramme.jsp',
		title : '新增日程',
		size	:	"L",
		init : function() {
			var iframe = this.iframe.contentWindow;
		},
		cancelVal : '关闭',
		ok : function() {
			var frame = this.iframe.contentWindow;
			addOrUpdateProgrammeLogical(frame, -1);// -1 代表不是 添加，而是新增
			return false;
		},
		cancel : true
	});
}

var Programme = {
	modifyTitle : '修改日程',
	addTitle : '添加日程',
	detailTitle : '日程详情',

	add : openArtDialog, // 添加日程
	modify : openArtDialog,// 修改日程
	show : openArtDialogOnlyCancel,// show detail programme
	dele : deleteProgramme, // delete
	addOnCalendar : openArtDialogOnCalendar
}
