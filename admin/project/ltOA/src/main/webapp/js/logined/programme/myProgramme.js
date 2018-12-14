$(document).ready(function() {
	var year=$("#year").val();
	var month=$("#month").val();
	if(year==null||month==null||year==""||month==""||year=="null"||month=="null"){
		var time=new Date();
		year=time.getFullYear();
		month=time.getMonth();
	}
	var calendar = $('#calendar').fullCalendar({
		year:year, 
		month:month, 
		theme : true,
		header : {
			left : 'prev,next today',
			center : 'title',
			right : ''
		},
		buttonText : { // 按钮对应的文本
			prevYear : '去年', // 不建议改这个值，因为它本身是含[去年、上一周、前天]三个意思，你就让它默认
			nextYear : '明年', // 同上
			today : '今天',
			month : '月',
			week : '周',
			day : '日'
		},
		firstDay : 1,
		axisFormat : 'HH',
		timeFormat : '(HH:mm )',
		allDaySlot : true,
		monthNames : ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月',
				'10月', '11月', '12月'], // 默认为英文月分，这里可以改成中文
		monthNamesShort : ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
				'9月', '10月', '11月', '12月'],
		dayNames : ['周日', '周一', '周二', '周三', '周四', '周五', '周六'], // 短格式的星期
		dayNamesShort : ['周日', '周一', '周二', '周三', '周四', '周五', '周六'], // 短格式的星期
		titleFormat : { // 格式化标题
			month : 'yyyy年MMMM', // 如：September 2009
			week : "[yyyy] yyyy年MMMd日{'至'[MMM] d日}", // 如：Sep 7 - 13 2009
			day : 'yyyy年MMMd日, dddd' // 如：Tuesday, Sep 8, 2009
		},
		loading : function(bool) {
			if (bool)
				$('#loading').show();
			else
				$('#loading').hide();
		},
		allDayText : '跨天',
		selectable : true,
		selectHelper : true,
		select : function(start, end, allDay) {
				Programme.addOnCalendar(start,end);
		},
		editable : false,
		events : function(start, end, callback) {
			qytx.app.ajax({
						url : basePath + "/calendar/myProgramme.action",
						cache : false,
						data:{
							"viewBegDate":new Date(start).format("yyyy-MM-dd"),
							"viewEndDate":new Date(end).format("yyyy-MM-dd")
						},
						success : function(data) {
							eval("var j=" + data);
							var events = [];
							var info = j;
							for (var i = 0; i < info.length; i++) {
								var calendarObj = info[i];
								var content = calendarObj.content;
								var type = calendarObj.calendarType;
								var begTime = '';
								var endTime = '';
								var id = '';
								begTime = calendarObj.begTime;
								endTime = calendarObj.endTime;
								var repeatType=calendarObj.repeatType;
								id = calendarObj.id;
								var timeStr=new Date(begTime).format("hh:mm");
								content="内容："+timeStr+" "+content;
								if (content.length > 15) {
									content = content.substring(0, 15) + "……";
								}
								events.push({
											title : content,
											start : begTime,
											end : endTime,
											id : id,
											repeat:repeatType
										});
							}
							callback(events);
						},
						error : function() {

						}
					})
		},
		eventClick : function(calEvent, jsEvent, view) {
			var id = calEvent.id;
			var repeatType=calEvent.repeat;
			var title = '日程详情';
			var button = [];
			// whether show modify button
			if(new Date().getTime()<=calEvent.start.getTime()){
				button.push({	
					name : '修改',
					callback: function(){
						Programme.modify(Programme.modifyTitle, basePath + "calendar/toUpdateProgramme.action?id=" + id, 800, 450, id);
					}
				});
			}
			
			button.push({
				name : '删除',
				focus: true,
				callback : function() {
					var iframe = this.iframe.contentWindow;
					Programme.dele(id,repeatType,iframe);
					return false;
				}
			});
			// add delete and close button
			button.push(
					 {
						name : '关闭',
						callback:	function(){
							return true;
						}
					}
			);	
				
			qytx.app.dialog.openUrl(
							 {
								url	:	basePath+'calendar/showViewProgramme.action?id='+id,
								title : title,
								size	:	"L",
								init : function() {
									var iframe = this.iframe.contentWindow;
								},
								customButton : button
							});
		}
	});
	

});

/**
 * 弹出添加层
 * 
 * @return
 */
function addCalendar() {
	Programme.addOnCalendar(new Date(), new Date());
}

function toCalendarSearch() {
	var Num=""; 
	for(var i=0;i<6;i++) 
	{ 
	Num+=Math.floor(Math.random()*10); 
	} 
	var id = $(window.top.document).find("#div_tab").find(".crent")[0].id;
	
	window.top.addTab(Num, basePath+ "logined/programme/listProgramme.jsp", '日程查询', 1, '');
	window.top.closeTab(id);
}


function getVal(obj) {
	if (obj) {
		return 1;
	}
	return 0;
}

	/* alert new Date().format("yyyy-MM-dd") */
		Date.prototype.format = function(fmt)   
			{ // author: meizz
			  var o = {   
			    "M+" : this.getMonth()+1,                 // 月份
			    "d+" : this.getDate(),                    // 日
			    "h+" : this.getHours(),                   // 小时
			    "m+" : this.getMinutes(),                 // 分
			    "s+" : this.getSeconds(),                 // 秒
			    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
			    "S"  : this.getMilliseconds()             // 毫秒
			  };   
			  if(/(y+)/.test(fmt))   
			    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
			  for(var k in o)   
			    if(new RegExp("("+ k +")").test(fmt))   
			  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			  return fmt;   
			}  	
