/**获取终端信息**/
var browser={ 
	versions:function(){ 
		var u = navigator.userAgent, app = navigator.appVersion; 
		 return { //移动终端浏览器版本信息 
		 trident: u.indexOf('Trident') > -1, //IE内核 
		 presto: u.indexOf('Presto') > -1, //opera内核 
		 webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核 
		 gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核 
		 mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端 
		 ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
		 android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器 
		 iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器 
		 iPad: u.indexOf('iPad') > -1, //是否iPad 
		 webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部 
		 }; 
	}(),
};

/*********************IOS通用处理程序 begin************************/
 globeBridge =  null;
if(browser.versions.ios){
	function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge);
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge);
			}, false);
		}
	}
	connectWebViewJavascriptBridge(function(bridge) {
		//初始化
		bridge.init(function(message, responseCallback) {
		});
		//注册函数,所有的IOS函数都在此定义
		//单选人员
		bridge.registerHandler("selectOneUser", function(data, responseCallback) {
		});
		//选择时间
		bridge.registerHandler("selectDateTime",function(data,responseCallback){
		});
		//提示
		bridge.registerHandler("tips",function(data,responseCallback){
		});
		//多选人员
		bridge.registerHandler("selectUser",function(data,responseCallback){
		});
		//关闭webView
		bridge.registerHandler("finishWebView",function(data,responseCallback){
		});
		//隐藏标题
		bridge.registerHandler("titleIsHide",function(data,responseCallback){
		});
		//选择照片
		bridge.registerHandler("selectPhotoDialog",function(data,responseCallback){
		});
		globeBridge = bridge;
	});
}
/******************IOS通用处理程序 end******************/
h5Adapter = {
		config:{
			"uploadUrl":"http://10.100.6.140:10000/fileServer/upload",
			"downloadUrl":"http://10.100.6.140:10000/fileServer/download"
		},
		//确认框
		confirm:function(data,callBack){
			 if(browser.versions.ios){
				 if(confirm(data)){
					 callBack({result:true});
					}else{
						
					};
			 }else{
				 SafeJs.confirm(data,callBack);
			 }
		},
		//提示框
		alert:function(title,content){
			var argLen=arguments.length;
			if(argLen==1){
				if(browser.versions.ios){
					 alert(title);
				 }else{
					 SafeJs.alert(title);
				 }
			}else if(argLen==2){
				if(browser.versions.ios){
					 alert(title);
				 }else{
					 SafeJs.alert(title,content);
				 }
			}
		},
		/*
		 * 选择时间
		 * defaultTime:默认时间，
		 * format：时间格式
		 * callBack:安卓调用成功后的回调函数
		 * method:IOS调用成功后，用来区分是哪个控件调用的，onSuccess回调函数的第一个参数
		 */
		
		getTime:function(defaultTime,format,callBack,method){
			if(browser.versions.ios){
				var param = {
					format:format,
					defaultTime:defaultTime
				};
				globeBridge.callHandler("selectDateTime",param,function(responseData){
					callBack(jQuery.parseJSON(responseData));
				});
			}else if(browser.versions.android){
				SafeJs.selectDateTime("selectDateTime",defaultTime,format,callBack);
			}
		},
		//选择人员
		selectUser:function(defaultUserIds,callBack){
			if(browser.versions.ios){
				globeBridge.callHandler("selectUser",defaultUserIds,function(responseData){
					callBack(jQuery.parseJSON(responseData));
				});
			}else if(browser.versions.android){
			    SafeJs.selectUser("selectUser",defaultUserIds,callBack);	
			}
		},
		//关闭窗体
		backDesk:function(moduleName){
			if(browser.versions.ios){
				globeBridge.callHandler("finishWebView","",function(responseData){
				});
			}else if(browser.versions.android){
				SafeJs.finishWebView();
			}
		},
		//返回上一步
		backLast:function(){
			if(browser.versions.ios){
				history.go(-1);
			}else if(browser.versions.android){
				SafeJs.doBack();
			}
		},
		//提示框
		tips:function(content,timeout){
			if(browser.versions.ios){
//				var cmd="tips:";
//				var obj={};
//				obj.method="tips";
//				obj.message=encodeURI(content);
//				_sendObjectMessage(cmd,obj);
				var param = {
					content:content,
					timeout:timeout
				};
				globeBridge.callHandler("tips",param,function(responseData){
				});
			}else if(browser.versions.android){
				if(!timeout){
					timeout = 500;
				}
				SafeJs.tips(content,timeout);
			}
		},
		/**
		 * rightButtonObj:非必填，右上角按钮对象
		 * leftButtonObj: 非必填，默认是左上角返回按钮
		 * 	例：{	"type":"toAdd/text/back", //按钮类型
		 * 		"onClickMethod":"toAdd"//点击按钮出发js函数名称
		 * 		"message":"",//非必填，如果按钮含中文信息则与type=text对应
		 * 			}
		 */
		onHeaderButton:function(rightButtonObj,leftButtonObj){
			var cmd="onHeaderButton:";
			if(browser.versions.ios){
				var obj={};
				obj.method="onHeaderButton";
				if(rightButtonObj){
					obj.rightButton = rightButtonObj;
				}
				if(leftButtonObj){
					obj.leftButton = leftButtonObj;
				}else{
					obj.leftButton = {
							"type":"back",
					};
				}
				_sendObjectMessage(cmd,obj);
			}else if(browser.versions.android){
				if(rightButtonObj){
					if(rightButtonObj.type=='text'){
						SafeJs.addOneRightTextButton(rightButtonObj.message,function(){
							var fun = window[rightButtonObj.onClickMethod];
							fun();
						});
					}else{
						SafeJs.addOneRightPicButton(rightButtonObj.type,function(){
							var fun = window[rightButtonObj.onClickMethod];
							fun();
						});
					}
				}
				if(leftButtonObj){
					if(leftButtonObj.onClickMethod){
						SafeJs.setLeftButtonCallBack(function(){
							var fun = window[leftButtonObj.onClickMethod];
							fun();
						});
					}
				}
			}
		},
		/**
		 * setTitle(title) 修改html5框架导航栏title内容
		 */
		setTitle:function(title){
			if(browser.versions.ios){

			}else if(browser.versions.android){
				SafeJs.setTitle(title);
			}
		},
		
		/**
		 * 使用localStorage存储数据
		 */
		setItem:function(key,value){
			if(window.localStorage){
				window.localStorage.setItem(key, value);
			}
		},
		/*
		 * 从localStorage中读取数据,并调用回调函数执行
		 */
		getItem:function(key,callback){
			if(window.localStorage){
				var d = window.localStorage.getItem(key);
				callback(d);
			}
		},
		/*
		 * 调用照相接口
		 * 返回本地地址
		 */
		photo:function(cb){
			if(browser.versions.ios){
				
			}else{
				SafeJs.selectPhotoDialog(function(localurl){
					cb(localurl);
				});
			}
		},
		
		/*
		 * 单选人员
		 */
		selectOneUser:function(cb){
			if(browser.versions.ios){
				globeBridge.callHandler("selectOneUser","",function(responseData){
					cb(jQuery.parseJSON(responseData));
				});
			}else{
				SafeJs.selectOneUser("", cb);
			}
		},
		//打开指定页面
		loadUrl:function(url){
			if(browser.versions.ios){
				
			 }else{
				 SafeJs.loadUrl(url);
			 }
		},
		//重新加载页面(刷新当前页)
		reLoad:function(){
			if(browser.versions.ios){
			
			 }else{
				 SafeJs.reLoad();
			 }
		},
		//重新加载页面(刷新当前页)
		sendRequest:function(requestInfo, successCallBack, errorCallBack){
			if(browser.versions.ios){
			 }else{
				 SafeJs.sendRequest(requestInfo, successCallBack, errorCallBack);
			 }
		},
		//隐藏标题
		setTitleIsHide:function(isHide){
			if(browser.versions.ios){
				globeBridge.callHandler("titleIsHide",isHide,function(responseData){
				});
			}else{
				SafeJs.setTitleIsHide(isHide);
			}
		},
		//判断客户端注册成功
		registerJsSuccess:function(successFun){
			if(!successFun){
				return;
			}
			if(browser.versions.ios){
				successFun();
			}else{
				var intervalTime=50;
				var addTime=0;
				var totalTime=2000;
				var jsTimer=window.setInterval(function(){
					if(addTime>=totalTime){
						window.clearInterval(jsTimer);
					}else{
						if(window.SafeJs){
						 	window.clearInterval(jsTimer);
						 	successFun();
						}
					}
					addTime=addTime+intervalTime;
				},intervalTime);
			}
		},
		//选择照片
		selectPhoto:function(moduleCode,uploadPath,cb){
			var param = {
					moduleCode:moduleCode,
					uploadPath:uploadPath
			};
			if(browser.versions.ios){
				globeBridge.callHandler("selectPhotoDialog",param,function(responseData){
					cb(jQuery.parseJSON(responseData));
				});
			}else{
				SafeJs.selectPhoto(param,cb);
			}
		}
};

var _sendObjectMessage = function(cmd,parameters) {
  var iframe = document.createElement('iframe');
  iframe.setAttribute('src', "objc://"+cmd+":/"+JSON.stringify(parameters));

  document.documentElement.appendChild(iframe);
  iframe.parentNode.removeChild(iframe);
  iframe = null;
};
