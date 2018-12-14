mui.init({
		gestureConfig:{
			tap:true,
			hold:true,
			release:true
		},
		pullRefresh: {
			container: '#pullrefresh',
			up: {
				contentrefresh: '正在加载...',
				contentnomore:'没有更多数据了',
				callback: pullupRefresh
			}
		}
});
mui.plusReady(function() {
	//判断当前是否有网络
	if(!getNetConnection()){
		$(".mui-content").hide();
		$(".noNet").show();
		return false;
	}
	setTimeout(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
	}, 100);

	$("#dataList").on("tap", "a[name='startLi']", function(event) {
		var processerUser = $(this).data("processerUser");
		var viewId = this.getAttribute("viewId");
		var viewUrl = this.getAttribute("viewUrl");
		var instanceId = this.getAttribute("instanceId");
		var title = this.getAttribute("title");
		//打开详情
		plus.webview.close(this.getAttribute("viewId"));
		setTimeout(function() {
			mui.openWindow({
				url: viewUrl,
				id: viewId,
				extras: {
					instanceId: instanceId,
					title: title,
					processerUser: processerUser,
					operation: "view"
				},
				show: {
					autoShow: false
				},
				waiting: {
					autoShow: true, //自动显示等待框，默认为true
					title: '' //等待对话框上显示的提示内容
				}
			});
		}, 300);
		return false;
	});
	$("#dataList").on("tap", "span[name='didi']", function(event) {
		var userIds = $(this).attr("userIds");
		var title = $(this).attr("title");
		var content = window.windowCommon.approveLoginName + "催您审批他的" + title;
		sendDidi(userIds, content);
		return false;
	});
});
//当前页数
var currentPage = 0;
var isEnd = false;
/**
 * 上拉加载具体业务实现table.appendChild(li);
 */
function pullupRefresh() {
	setTimeout(function() {
		mui.ajax(window.windowCommon.basePath + "baseworkflow/myStarted.c?time="+(new Date()).getTime(), {
			data: {
				"_clientType": "wap",
				"iDisplayStart": currentPage * 10,
				"iDisplayLength": 10,
				"userId": window.windowCommon.approveLoginId
			},
			dataType: 'text', //服务器返回json格式数据
			type: 'post', //HTTP请求类型
			timeout: 10000, //超时时间设置为10秒；
			crossDomain:true,
			success: function(data) {
				currentPage++;
				var table = document.body.querySelector('.mui-table-view');
				var cells = document.body.querySelectorAll('.mui-table-view-cell');
				var replaceData = data.replace("100||", "");
				var jsonData = JSON.parse(replaceData);
				var approveData = jsonData.aaData;
				var iTotalRecords=jsonData.iTotalRecords;
				if((currentPage-1)*10+approveData.length>=iTotalRecords){
					isEnd = true;
				} 
				for (var i = 0; i < approveData.length; i++) {
					var code = approveData[i].code;
					var title = approveData[i].title;
					var state = approveData[i].state;
					var applyTime = approveData[i].applyTime;
					var instanceId = approveData[i].instanceId;
					var moduleCode = approveData[i].moduleCode;
					var processerUser=approveData[i].processerUser;
					var processerIds="";
					
					for(var j=0 ;j<processerUser.length;j++){
						processerIds+=processerUser[j].userId+",";
					}
					var pattern = /^(.)+,$/;
					if(pattern.test(processerIds)){
						processerIds=processerIds.substring(0,processerIds.length-1);
					}
					var viewUrl = '';
					var viewId='';
					var imgClass = '';
					var imgTxt = '';
					var j=i%6+1;
					if (code == 'qingjia') { //请假
						viewUrl = '../freeflow/qingjia_detail.html';
						viewId='qingjia_detail';
						imgClass = 'ico-5';
						imgTxt = '休';
						title=title+"申请";
					} else if (code == 'baoxiao') { //报销
						viewUrl = '../freeflow/baoxiao_detail.html';
						viewId='baoxiao_detail';
						imgClass = 'ico-1';
						imgTxt = '报';
						title=title+"申请";
					} else if (code == 'chuchai') { //出差
						viewUrl = '../freeflow/chuchai_detail.html';
						viewId='chuchai_detail';
						imgClass = 'ico-7';
						imgTxt = '出';
						title=title+"申请";
					} else if (code == 'waichu') { //外出
						viewUrl = '../freeflow/waichu_detail.html';
						viewId='waichu_detail';
						imgClass = 'ico-2';
						imgTxt = '外';
						title=title+"申请";
					} else if (code == 'lingyong') { //物品领用
						viewUrl = '../freeflow/lingyong_detail.html';
						viewId='lingyong_detail';
						imgClass = 'ico-4';
						imgTxt = '物';
						title=title+"申请";
					} else if (code == 'tongyong') { //通用
						viewUrl = '../freeflow/tongyong_detail.html';
						viewId='tongyong_detail';
						imgClass = 'ico-3';
						imgTxt = '通';
						title=title+"申请";
					}else{
						viewUrl = 'detail.html';
						viewId='detail';
						imgClass = 'ico-'+j;
						imgTxt = title.substring(0,1);
					}
					var detailTitle="我的"+title;
					var currLi=$('<li class="mui-table-view-cell mui-media mui-approve" ></li>');
					var currDA=$('<span  name="didi" class="didiIterm" title="'+title+'" userIds="'+processerIds+'" ><a href="javascript:void(0);"><em class="water1"></em></a></span>');
					var currA="";
					if(state.indexOf("审批中")>-1){
						currA=$('<a href="javascript:void(0);" name="startLi" class="didiLeft" instanceId="'+instanceId+'" title="'+title+'" viewUrl="'+viewUrl+'" viewId="'+viewId+'">');
					}else{
						currA=$('<a href="javascript:void(0);" name="startLi" instanceId="'+instanceId+'" title="'+title+'" viewUrl="'+viewUrl+'" viewId="'+viewId+'">');
					}
					
					currA.data("processerUser",JSON.stringify(processerUser));
					var html = "";
					html +='<div class="mui-media-object mui-pull-left head-img '+imgClass+'">'+imgTxt+'</div>';
					html += '<div class="mui-media-body mui-ellipsis text-hide">'+title+'</div>';
					html += '<p class="state">'+state+'<span class=" mui-pull-right">'+applyTime+'</span></p>';
					var currHtml=$(html);
					currA.append(currHtml);
					if(state.indexOf("审批中")>-1){
						currLi.append(currDA);
					}
					currLi.append(currA);
					
					//下拉刷新，新纪录插到最前面；
					$("#dataList").append(currLi);
				}
				
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(isEnd); //参数为true代表没有更多数据了。
				if(currentPage==1 && approveData.length==0){
					$(".listNoData").show();	
					mui('#pullrefresh').pullRefresh().disablePullupToRefresh();
				}else if(currentPage==1 && approveData.length==iTotalRecords&&iTotalRecords!=0){
					$(".mui-pull-caption-nomore").text("");
				}
			},
			error: function(xhr, type, errorThrown) {
				//异常处理；
				mui.toast("请求数据异常！");
			}
		});
	}, 500);
	
}

/**
 * 发送嘀嘀提醒
 * @param {Object} userId
 * @param {Object} content
 */
function sendDidi(userIds,content){
	var didi={
		"userIds":userIds,//接收人userIds
		"userIdsEditable":true,//接收人是否可以编辑  true可以 false不可以
		"remindType":1,//提醒方式 1语音4录音8短信0应用内
		"remindTypeEditable":true,//提醒方式是否能编辑 true可以 false不可以
		"contentType":3,//内容类型 1录音 2短语 3文字
		"contentTypeEditable":true,//内容类型是否能编辑 true可以 false不可以
		"content":content,//嘀嘀内容
		"contentEidtable":true,//内容是否能编辑 true可以 false不可以
		"systemName":"lgz",//系统名字
		"moduleName":"approve",//模块名字
		"fromWhere":"approveList"//审批模块列表
	};
	var didiJson=JSON.stringify(didi);
	//添加蒙版
	var flag = false;
	var mask = mui.createMask(function(){
	    return flag;
	});
	//显示遮罩
	mask.show();
	window.plus.qytxplugin.sendDidi(didiJson,function(){
		//关闭遮罩
		setTimeout(function(){
			//关闭遮罩
			flag = true;
			mask.close();
		},1000);
	});
	//防止手机端出错,3秒自动消失
	setTimeout(function(){
		//关闭遮罩
		flag = true;
		mask.close();
	},3000);
}

function pageClose(){
	plus.webview.close("qingjia");
	plus.webview.close("baoxiao");
	plus.webview.close("chuchai");
	plus.webview.close("waichu");
	plus.webview.close("lingyong");
	plus.webview.close("tongyong");
	plus.webview.close("qingjia_detail");
	plus.webview.close("baoxiao_detail");
	plus.webview.close("chuchai_detail");
	plus.webview.close("waichu_detail");
	plus.webview.close("lingyong_detail");
	plus.webview.close("tongyong_detail");
	plus.webview.close("free_operate");
	plus.webview.close("detail");
	plus.webview.close("apply");
	plus.webview.close("operate");
	plus.webview.close( "myStart_pullrefresh_sub", "auto", 100);
}
