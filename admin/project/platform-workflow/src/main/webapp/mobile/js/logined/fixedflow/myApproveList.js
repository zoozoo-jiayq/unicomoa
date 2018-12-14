
//当前页数
var currentPage1 = 0;
var currentPage2 = 0;
var isEndItem1 = false;
var isEndItem2 = false;
//带我审批列表上啦加载
function pullupRefresh_approving(currObj) {
		mui.ajax(window.windowCommon.basePath + "baseworkflow/myWaitProcess.c?time="+(new Date()).getTime(), {
			data: {
				"_clientType": "wap",
				"iDisplayStart": currentPage1 * 10,
				"iDisplayLength": 10,
				"userId": window.windowCommon.approveLoginId
			},
			dataType: 'text', //服务器返回json格式数据
			type: 'post', //HTTP请求类型
			timeout: 10000, //超时时间设置为10秒；
			crossDomain: true,
			success: function(data) {
				currentPage1++;
				var table = document.body.querySelector('#item1mobile ul.mui-table-view');
				var replaceData = data.replace("100||", "");
				var jsonData = JSON.parse(replaceData);
				var approveData = jsonData.aaData;
				var iTotalRecords=jsonData.iTotalRecords;
				$("#item1A").html("待审批("+iTotalRecords+")");
				if((currentPage1-1)*10+approveData.length>=iTotalRecords){
					isEndItem1 = true;
				}
				for (var i = 0; i < approveData.length; i++) {
					var photoUrl = approveData[i].photoUrl;
					var title = approveData[i].title;
					var state = approveData[i].state;
					var arriveTime = approveData[i].arriveTime;
					var instanceId = approveData[i].instanceId;
					var currentUserId = approveData[i].currentUserId;
					var createrName = approveData[i].createrName;
					var moduleCode = approveData[i].moduleCode;
					var code="";
					if(instanceId!=null&&instanceId!=undefined){
						code=instanceId.substring(0,instanceId.indexOf("."));
					}
					var showTitle=title;
					var viewUrl = '';
					var viewId='';
					if (code == 'qingjia') { //请假
						viewUrl = '../freeflow/qingjia_detail.html';
						viewId='qingjia_detail';
						title=title+"申请";
					} else if (code == 'baoxiao') { //报销
						viewUrl = '../freeflow/baoxiao_detail.html';
						viewId='baoxiao_detail';
						title=title+"申请";
					} else if (code == 'chuchai') { //出差
						viewUrl = '../freeflow/chuchai_detail.html';
						viewId='chuchai_detail';
						title=title+"申请";
					} else if (code == 'waichu') { //外出
						viewUrl = '../freeflow/waichu_detail.html';
						viewId='waichu_detail';
						title=title+"申请";
					} else if (code == 'lingyong') { //物品领用
						viewUrl = '../freeflow/lingyong_detail.html';
						viewId='lingyong_detail';
						title=title+"申请";
					} else if (code == 'tongyong') { //通用
						viewUrl = '../freeflow/tongyong_detail.html';
						viewId='tongyong_detail';
						title=title+"申请";
					}else{
						viewUrl = 'detail.html';
						viewId='detail';
						showTitle=createrName+"的"+title;
					}
					
					if(createrName!=null&&createrName.length>2){
						createrName=createrName.substring(createrName.length-2,createrName.length);
					}
					var col=letterCode(createrName);
					var li = document.createElement('li');
					li.className = 'mui-table-view-cell mui-media mui-approve';
					var lia = document.createElement('a');
					lia.href = "javascript:void(0);";
					lia.setAttribute("instanceId", instanceId);
					lia.setAttribute("title",showTitle);
					lia.setAttribute("viewUrl", viewUrl);
					lia.setAttribute("viewId", viewId);
					lia.addEventListener("tap", function() {
						//打开详情
						var viewId=this.getAttribute("viewId");
						var viewUrl=this.getAttribute("viewUrl");
						var instanceId=this.getAttribute("instanceId");
						var title=this.getAttribute("title");
						plus.webview.close(this.getAttribute("viewId"));
						setTimeout(function(){
							mui.openWindow({
								url: viewUrl,
								id: viewId,
								extras: {
									instanceId: instanceId,
									title:title,
									operation: "approving"
								},
								show: {
									autoShow: false
								},
								waiting: {
									autoShow: true, //自动显示等待框，默认为true
									title: '' //等待对话框上显示的提示内容
								}
							});
						},300);
						
					});
					var html = ""
					if(photoUrl==null||photoUrl==undefined||photoUrl==""){
						 html+='<div class="textround mui-pull-left head-img head-bg-'+col+'">'+createrName+'</div>';
					}else{
						 html+='<img class="mui-media-object mui-pull-left head-img" src="'+photoUrl+'" >';
					}
					html+='<div class="mui-media-body mui-ellipsis">'+showTitle;
					html+='<p class="state">'+state+'<span class="time mui-pull-right">'+arriveTime+'</span></p>';
					html += '</div>';
					lia.innerHTML = html;
					li.appendChild(lia);
					//下拉刷新，新纪录插到最前面；
					table.appendChild(li);
				}
				currObj.endPullUpToRefresh(isEndItem1);	
				
				if(currentPage1==1 && approveData.length==0){
					$("#item1mobile .mui-pull-loading").text("暂无数据 ");
					$("#item1mobile .mui-pull-bottom-tips").css("margin-top","100px");
				}else if(currentPage1==1&&approveData.length==iTotalRecords&&iTotalRecords!=0){
					$("#item1mobile .mui-pull-loading").text("");
				}
				
			},
			error: function(xhr, type, errorThrown) {
				//异常处理；
				mui.toast("请求数据异常！");
			}
		});
};
//我已审批列表上啦加载
function pullupRefresh_approved(currObj){
	mui.ajax(window.windowCommon.basePath + "baseworkflow/myProcessed.c?time="+(new Date()).getTime(), {
			data: {
				"_clientType": "wap",
				"iDisplayStart": currentPage2 * 10,
				"iDisplayLength": 10,
				"userId": window.windowCommon.approveLoginId
			},
			dataType: 'text', //服务器返回json格式数据
			type: 'post', //HTTP请求类型
			timeout: 10000, //超时时间设置为10秒；
			crossDomain: true,
			success: function(data) {
				currentPage2++;
				var table = document.body.querySelector('#item2mobile ul.mui-table-view');
				var replaceData = data.replace("100||", "");
				var jsonData = JSON.parse(replaceData);
				var approveData = jsonData.aaData;
				var iTotalRecords=jsonData.iTotalRecords;
				if((currentPage2-1)*10+approveData.length>=iTotalRecords){
					isEndItem2 = true;
				}
				for (var i = 0; i < approveData.length; i++) {
					var photoUrl = approveData[i].photoUrl;
					var title = approveData[i].title;
					var state = approveData[i].state;
					var approveTime = approveData[i].approveTime;
					var instanceId = approveData[i].instanceId;
					var currentUserId = approveData[i].currentUserId;
					var createrName = approveData[i].createrName;
					var moduleCode = approveData[i].moduleCode;
					var code="";
					if(instanceId!=null&&instanceId!=undefined){
						code=instanceId.substring(0,instanceId.indexOf("."));
					}
					var showTitle=title;
					var viewUrl = '';
					var viewId='';
					if (code == 'qingjia') { //请假
						viewUrl = '../freeflow/qingjia_detail.html';
						viewId='qingjia_detail';
						title=title+"申请";
					} else if (code == 'baoxiao') { //报销
						viewUrl = '../freeflow/baoxiao_detail.html';
						viewId='baoxiao_detail';
						title=title+"申请";
					} else if (code == 'chuchai') { //出差
						viewUrl = '../freeflow/chuchai_detail.html';
						viewId='chuchai_detail';
						title=title+"申请";
					} else if (code == 'waichu') { //外出
						viewUrl = '../freeflow/waichu_detail.html';
						viewId='waichu_detail';
						title=title+"申请";
					} else if (code == 'lingyong') { //物品领用
						viewUrl = '../freeflow/lingyong_detail.html';
						viewId='lingyong_detail';
						title=title+"申请";
					} else if (code == 'tongyong') { //通用
						viewUrl = '../freeflow/tongyong_detail.html';
						viewId='tongyong_detail';
						title=title+"申请";
					}else{
						viewUrl = 'detail.html';
						viewId='detail';
						showTitle=createrName+"的"+title;
					}
					if(createrName!=null&&createrName.length>2){
						createrName=createrName.substring(createrName.length-2,createrName.length);
					}
					var col=letterCode(createrName);
					var li = document.createElement('li');
					li.className = 'mui-table-view-cell mui-media mui-approve';
					var lia = document.createElement('a');
					lia.href = "javascript:void(0);";
					lia.setAttribute("instanceId", instanceId);
					lia.setAttribute("title",showTitle);
					lia.setAttribute("viewUrl", viewUrl);
					lia.setAttribute("viewId", viewId);
					lia.addEventListener("tap", function() {
						//打开详情
						var viewId=this.getAttribute("viewId");
						var viewUrl=this.getAttribute("viewUrl");
						var instanceId=this.getAttribute("instanceId");
						var title=this.getAttribute("title");
						plus.webview.close(this.getAttribute("viewId"));
						setTimeout(function(){
							mui.openWindow({
								url: viewUrl,
								id: viewId,
								extras: {
									instanceId: instanceId,
									title:title,
									operation: "approved"
								},
								show: {
									autoShow: false
								},
								waiting: {
									autoShow: true, //自动显示等待框，默认为true
									title: '' //等待对话框上显示的提示内容
								}
							});
						},300);
						
					});
					var html = ""
					if(photoUrl==null||photoUrl==undefined||photoUrl==""){
						 html+='<div class="mui-media-object mui-pull-left head-img head-bg-'+col+'">'+createrName+'</div>';
					}else{
						 html+='<img class="mui-media-object mui-pull-left head-img" src="'+photoUrl+'" >';
					}
					html+='<div class="mui-media-body mui-ellipsis">'+showTitle;
					html+='<p class="state">'+state+'<span class="time mui-pull-right">'+approveTime+'</span></p>';
					html += '</div>';
					lia.innerHTML = html;
					li.appendChild(lia);
					//下拉刷新，新纪录插到最前面；
					table.appendChild(li);
				}
				currObj.endPullUpToRefresh(isEndItem2);
				if(currentPage1==1 && approveData.length==0){
					$("#item2mobile .mui-pull-loading").text("暂无数据");
					$("#item2mobile .mui-pull-bottom-tips").css("margin-top","100px");
				}else if(currentPage1==1&&approveData.length==iTotalRecords&&iTotalRecords!=0){
					$("#item2mobile .mui-pull-loading").text("");
				}
			},
			error: function(xhr, type, errorThrown) {
				//异常处理；
				mui.toast("请求数据异常！");
			}
		});
}
(function($) {
	mui.plusReady(function(){
		//判断当前是否有网络
		if(!getNetConnection()){
			$(".mui-content").hide();
			$(".noNet").show();
			return false;
		}
		//循环初始化所有下拉刷新，上拉加载。
		$.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
			$(pullRefreshEl).pullToRefresh({
				up: {
					auto: true,
					callback: function() {
						var self = this;
						setTimeout(function() {
							var ul = self.element.querySelector('.mui-table-view');
							var approve=ul.getAttribute("approve");
							if(approve=="approving"){
								pullupRefresh_approving(self);
							}else{
								pullupRefresh_approved(self);
							}
						}, 500);
					}
				}
			});
		});
	});
	
})(mui);

function pageClose() {
	//隐藏操作页面
	plus.webview.close(plus.webview.getWebviewById("qingjia_detail"));
	plus.webview.close(plus.webview.getWebviewById("baoxiao_detail"));
	plus.webview.close(plus.webview.getWebviewById("chuchai_detail"));
	plus.webview.close(plus.webview.getWebviewById("waichu_detail"));
	plus.webview.close(plus.webview.getWebviewById("lingyong_detail"));
	plus.webview.close(plus.webview.getWebviewById("tongyong_detail"));
	plus.webview.close(plus.webview.getWebviewById("free_operate"));
	plus.webview.close(plus.webview.getWebviewById("operate"));
}
