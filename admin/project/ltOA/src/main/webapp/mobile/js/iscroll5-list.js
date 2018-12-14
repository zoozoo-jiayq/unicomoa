
		document.addEventListener("touchmove", function(e) {e.preventDefault();}, false);
		//上拉滑动
		function upScroll(options){
			var myScroll=null;
			//初始化页数
			var initPage=options.initPage;
			if(!initPage){
				initPage=1;
			}
			//加载数据
			var serverData=options.serverData;
			if(!serverData){
				return;
			}
			//滚动区域元素
			var scrollAreaId=options.scrollAreaId;
			if(!scrollAreaId){
				return;
			}
			//上拉显示元素
			var pullUpAreaId=options.pullUpAreaId;
			if(!pullUpAreaId){
				return;
			}
			var pullUp=$(pullUpAreaId);
			var pullUpHeight=pullUp.outerHeight();
			//正在加载显示样式
			var loadArea=options.loadArea;
			var loadAreaView=loadArea();
			//初始化显示
			pullUp.html(loadAreaView);
			
			//重试显示样式
			var againArea=options.againArea;
			var againAreaView=againArea();
			
			function scrollRequestData(){
				var dataDefer=serverData();
				if(dataDefer){
					dataDefer.done(function(data){
						if(myScroll){
							myScroll.refresh();
						}
					}).fail(function(){
						if(myScroll){
							myScroll.refresh();
						}
						//失败点击重试
						pullUp.html(againAreaView);
					});
				}
			}
			
			myScroll = new IScroll(scrollAreaId,{
				mouseWheel : false,
				click: true,
				useTransition : false,
				useTransform: false,
				bindToWrapper:false,
				scrollY:true
			});
			myScroll.on("scrollEnd",function(){
				var currY=-this.y;
				var maxScrollY=-this.maxScrollY;
				//判断是否到底部
				if(currY>maxScrollY-pullUpHeight){
					scrollRequestData();
				}
			});
			myScroll.on("refresh",function(){
				//页面刷新,显示最初样式
				
			});
			//初始化调用
			for(var pageNum=0;pageNum<initPage;pageNum++){
				scrollRequestData();
			}
		};