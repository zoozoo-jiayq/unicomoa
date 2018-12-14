var _inputID;
$(document)
		.ready(
				function() {
					// when update initial the page
					$("#setHourInput").keyup(function() {
						var value = $("#setHourInput").val();
						if (value > 23) {
							$("#setHourInput").val(23);
						}
					});
					$("#setHourInput").blur(function() {
						var value = $("#setHourInput").val();
						if (value == null || value == "") {
							$("#setHourInput").val("08");
						}else{
							if(value.length<2){
								$("#setHourInput").val("0"+value);
							}
						}
					});
					$("#setEndHourInput").keyup(function() {
						var value = $("#setEndHourInput").val();
						if (value > 23) {
							$("#setEndHourInput").val(23);
						}
					});
					$("#setEndHourInput").blur(function() {
						var value = $("#setEndHourInput").val();
						if (value == null || value == "") {
							$("#setEndHourInput").val("08");
						}else{
							if(value.length<2){
								$("#setEndHourInput").val("0"+value);
							}
						}
					});
					$("#setRemaidHourInput").keyup(function() {
						var value = $("#setRemaidHourInput").val();
						if (value > 23) {
							$("#setRemaidHourInput").val(23);
						}
					});
					$("#setRemaidHourInput").blur(function() {
						var value = $("#setRemaidHourInput").val();
						if (value == null || value == "") {
							$("#setRemaidHourInput").val("08");
						}else{
							if(value.length<2){
								$("#setRemaidHourInput").val("0"+value);
							}
						}
					});

					$("#setMinuteInput").keyup(function() {
						var value = $("#setMinuteInput").val();
						if (value > 59) {
							$("#setMinuteInput").val(59);
						}
					});
					$("#setMinuteInput").blur(function() {
						var value = $("#setMinuteInput").val();
						if (value == null || value == "") {
							$("#setMinuteInput").val("00");
						}else{
							if(value.length<2){
								$("#setMinuteInput").val("0"+value);
							}
						}
					});
					$("#setEndMinuteInput").keyup(function() {
						var value = $("#setEndMinuteInput").val();
						if (value > 59) {
							$("#setEndMinuteInput").val(59);
						}
					});
					$("#setEndMinuteInput").blur(function() {
						var value = $("#setEndMinuteInput").val();
						if (value == null || value == "") {
							$("#setEndMinuteInput").val("00");
						}else{
							if(value.length<2){
								$("#setEndMinuteInput").val("0"+value);
							}
						}
					});
					$("#setRemaidMinuteInput").keyup(function() {
						var value = $("#setRemaidMinuteInput").val();
						if (value > 59) {
							$("#setRemaidMinuteInput").val(59);
						}
					});
					$("#setRemaidMinuteInput").blur(function() {
						var value = $("#setRemaidMinuteInput").val();
						if (value == null || value == "") {
							$("#setRemaidMinuteInput").val("00");
						}else{
							if(value.length<2){
								$("#setRemaidMinuteInput").val("0"+value);
							}
						}
					});
					$("#dailyName")
							.keyup(
									function() {
										var content = $("#dailyName").val();
										var contentObj = $("#dailyName");
										if (content.length > 1000) {
											qytx.app.valid.showObjError($(contentObj),
													'calendar.calendar_content_too_long');
											return false;
										} else {
											qytx.app.valid.hideError($(contentObj));
										}
									});

					var isUpdate = (update == '' ? false : true);
					if (isUpdate) {

						// 提醒类型
						if (remaindTypeG == 0) {
							$("#affairType1").attr("checked", false);
							$("#affairType1").attr("disabled", true);
							$("#affairType2").attr("checked", false);
							$("#affairType2").attr("disabled", true);
							$("#affairType3").attr("checked", false);
							$("#affairType3").attr("disabled", true);
						} else {
							$("#remaidSpan").show();
							$("#setRemaidHourInput").val(remaindHour);
							$("#setRemaidMinuteInput").val(remaindMinu);
							if(remaindTypeG == 1){
								$("#tips").attr("style","display:''");
								$("#tips").html("注：日程的开始时间同一天进行提醒");
							}else if(remaindTypeG == 2){
								$("#tips").attr("style","display:''");
								$("#tips").html("注：日程的开始时间前一天进行提醒");
							}
						}
						// 选中结束时间逻辑
						if (hasEndDay == 1) {

							$("#checkBoxEndTime").attr("checked", "checked");
							$("#endTimeTr").show();
							if (isAllDay == 0) {
								$("#checkBoxAllDay").removeAttr("checked");
								$("#beginTimeSpan").show();
								$("#setHourInput").val(begHour);
								$("#setMinuteInput").val(begMinu);
								$("#endTimeSpan").show();
								$("#setEndHourInput").val(endHour);
								$("#setEndMinuteInput").val(endMinu);
							}else{
								$("#checkBoxAllDay").attr("checked",true);
								$("#beginTimeSpan").hide();
								$("#endTimeSpan").hide();
							}

						} else {
							$("#checkBoxEndTime").attr("checked", false);
							$("#endTimeTr").hide();
							if (isAllDay == '0') {
								$("#checkBoxAllDay").removeAttr("checked");
								$("#beginTimeSpan").show();
								$("#setHourInput").val(begHour);
								$("#setMinuteInput").val(begMinu);
								$("#endTimeSpan").show();
							}else{
								$("#checkBoxAllDay").attr("checked",true);
								$("#beginTimeSpan").hide();
								$("#endTimeSpan").hide();
							}
						}

						// deal with remaind time
						$("#remaindType").find("option")[parseInt(remaindTypeG)].selected = true;
						// $("#repeatType").find("option")[parseInt(repeatTypeG)].selected
						// = true;
					} else {
						// when add initial the page
						var start = art.dialog.data('start');
						setInitTime(start);
						$("#affairType1").attr("checked", false);
						$("#affairType1").attr("disabled", true);
						$("#affairType2").attr("checked", false);
						$("#affairType2").attr("disabled", true);
						$("#affairType3").attr("checked", false);
						$("#affairType3").attr("disabled", true);
					}

					$("#remaindType").change(function() {
						if ($("#remaindType").val() == 0) {
							$("#affairType1").attr("checked", false);
							$("#affairType1").attr("disabled", true);
							$("#affairType2").attr("checked", false);
							$("#affairType2").attr("disabled", true);
							$("#affairType3").attr("checked", false);
							$("#affairType3").attr("disabled", true);
							$("#tips").attr("style","display:none");
						} else {
							$("#affairType1").attr("checked", true);
							$("#affairType1").attr("disabled", false);
							$("#affairType2").attr("checked", true);
							$("#affairType2").attr("disabled", false);
							$("#affairType3").attr("checked", true);
							$("#affairType3").attr("disabled", false);
							if($("#remaindType").val() == 1){
								$("#tips").attr("style","display:''");
								$("#tips").html("注：日程的开始时间同一天进行提醒");
							}else if($("#remaindType").val() == 2){
								$("#tips").attr("style","display:''");
								$("#tips").html("注：日程的开始时间前一天进行提醒");
							}
						}
					});

					document.onclick = function() {
						document.getElementById("setHour").style.display = "none";
						document.getElementById("setMinute").style.display = "none";
					}

					// 初始化小时选择项
					for ( var i = 0; i < 24; i++) {
						if (i < 10) {
							i = "0" + i;
						}
						var hourLi = '<LI class=menu_item value="' + i
								+ '"><A href="javascript:;"><I></I>' + i
								+ '</A></LI>';
						$("#hourUl").append(hourLi);
					}
					$(".menu_obj ul li").click(function() {
						if (0 == $(this).val()) {
							$("#" + _inputID).val("00");
						} else {
							var valueStr = $(this).val();
							if (Number(valueStr) < 10) {
								valueStr = "0" + valueStr;
							}
							$("#" + _inputID).val(valueStr);
						}

						$(".menu_obj").hide();
					})

					// 结束时间点击事件
					$("#checkBoxEndTime").click(function() {
						if ($(this).attr("checked") == 'checked') {
							$("#endTimeTr").show();
						} else {
							$("#endTimeTr").hide();
						}
					});

					// 开始时间点击事件
					$("#checkBoxAllDay").click(function() {
						if ($(this).attr("checked") == 'checked') {
							$("#beginTimeSpan").hide();
							$("#endTimeSpan").hide();
						} else {
							$("#beginTimeSpan").show();
							$("#endTimeSpan").show();
						}
					});

					// click the endtime to control the repeat type
					$("#endTime").change(function() {
//						dealRepeatType();
					})
					// click the begTime to control the repeat type
					$("#begTime").change(function() {
//						dealRepeatType();
					})

					$("#remaindType").change(function() {
						var remaindType = $("#remaindType").val();
						if (remaindType == 0) {
							$("#remaidSpan").hide();
						} else {
							$("#remaidSpan").show();
						}
					});

				})

function dealRepeatType() {
	$("#repeatType option[value='0']").attr("selected", "selected");
	$("#repeatType").empty();
	$("#repeatType").append(
			'<option value="0">不重复</option>' + '<option value="1">每天</option>'
					+ '<option value="2">每工作日</option>'
					+ '<option value="3">每周</option>'
					+ '<option value="4">每月</option>'
					+ '<option value="5">每年</option>')
	// alert("dklj")
	var begTime = $("#begTime").val();
	var endTime = $("#endTime").val();
	// alert(endTime)
	// alert(begTime)

	var begTimeStr = begTime.split(" ")[0];
	var endTimeStr = endTime.split(" ")[0];

	if (endTime == '' || endTime == undefined || begTime == ''
			|| begTime == undefined) {

	} else {
		var begTimeStr = begTime.split(" ")[0];
		var endTimeStr = endTime.split(" ")[0];
		var begTimeArray = begTimeStr.split("-");
		var endTimeArray = endTimeStr.split("-");
		// alert(begTimeArray)
		var beg = Date.parse(begTimeArray[0] + "/" + begTimeArray[1] + "/"
				+ begTimeArray[2]);
		var end = Date.parse(endTimeArray[0] + "/" + endTimeArray[1] + "/"
				+ endTimeArray[2]);

		// alert(beg);
		var timeLong = (end - beg);

		var days = getDays(timeLong);
		// alert(days)
		if (days >= 1 && days <= 5) {
			// alert("1- 5")
			$("#repeatType option[value='1']").remove();
		} else if (days > 5 && days <= 7) {
			$("#repeatType option[value='1']").remove();
			$("#repeatType option[value='2']").remove();

		} else if (days > 7 && days <= 30) {
			$("#repeatType option[value='1']").remove();
			$("#repeatType option[value='2']").remove();
			$("#repeatType option[value='3']").remove();
		} else if (days > 30 && days <= 365) {
			$("#repeatType option[value='1']").remove();
			$("#repeatType option[value='2']").remove();
			$("#repeatType option[value='3']").remove();
			$("#repeatType option[value='4']").remove();
		} else if (days > 365) {
			$("#repeatType option[value='1']").remove();
			$("#repeatType option[value='2']").remove();
			$("#repeatType option[value='3']").remove();
			$("#repeatType option[value='4']").remove();
			$("#repeatType option[value='5']").remove();
		}

	}
}

// control the time select
function msg(divID, inputID, event) {
	_inputID = inputID;
	$(".menu_obj").hide();
	$("#" + divID + "").show();
	var inputTop = $("#" + inputID + "").position();
	$("#" + divID + "").css("top", inputTop.top + 16);
	$("#" + divID + "").css("left", inputTop.left - 6);

	if ($.browser.msie) {
		event.cancelBubble = true;
	} else {
		event.stopPropagation();
	}
}

// get the days
function getDays(timeLong) {
	return timeLong / (24 * 60 * 60 * 1000);
}

function setInitTime(begin) {
	$("#begTime").val(getFormatDate(begin));
	$("#endTime").val(getFormatDate(begin));
}

function getFormatDate(srcDate) {
	var nowBeg;
	var myDate = srcDate;
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var hours = myDate.getHours();
	var minutes = myDate.getMinutes();
	var second = myDate.getSeconds();
	var week = myDate.getDay();

	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	nowBeg = year + "-" + month + "-" + day + " " + getWeek(week);
	return nowBeg;
}

function getWeek(week) {
	switch (week) {
	case 1:
		return '星期一';
	case 2:
		return '星期二';
	case 3:
		return '星期三';
	case 4:
		return '星期四';
	case 5:
		return '星期五';
	case 6:
		return '星期六';
	case 0:
		return "星期日";

	}
}