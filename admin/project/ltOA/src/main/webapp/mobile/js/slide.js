//展开显示
function show(id){
	var v_ID = document.getElementById(id);
	jQuery(v_ID).show();
}

  (function(win){
	
	/*
		页面中的滑动区域内的结构必须是.swiper-wrapper包括.swiper-slide(多个)
		config={
			selector:'.swiper-container',//选择器，绑定的滑动区域
			mode:'horizontal',//水平或者垂直 horizontal/vertical 默认水平
			grabCursor:true,//是否选择小手的图标 true/false 默认true
			loop:false,//是否循环显示滑动区域内容 默认false
			freeMode:false,//是否开启自由模式 默认不开启
			slidesPerView:2,//同屏显示slide的数量 与loop不兼容
			speed:300//滑块之间持续的时间 默认300ms
		}
	*/
	  
	  
	win.slide = function(config){
		var _slideObj = new Swiper(config.selector,{
			loop:config.loop||false,
			grabCursor: config.grabCursor||true,
			mode:config.mode||'horizontal',
			freeMode:config.freeMode||false,
			slidesPerView:config.slidesPerView||1,
			speed:config.speed||300
		});
		return _slideObj;
	}
	
	/*
		createSlide(html,class,tag);这个函数其中第一个参数是滑动块的html代码，第二个参数是滑动块样式，第三个参数是滑动块的分类
		config={
			pullDownFun:function(),//下拉松手事件 函数中ajax请求需要是同步执行的
			pullUpFun:function(),//上拉松手事件 函数中ajax请求需要是同步执行的
			downSelector:"#downloader",//下拉时显示内容的选择器 非必填 默认id为downloader
			upSelector:"#uploader",//上拉时显示内容的选择器 非必填 默认id为uploader
		}
	*/
	win.customScroll={
  		init:function(config){
				this.config = config;
  			    var downloader=config.downSelector||"#downloader";
				var uploader=config.upSelector||"#uploader";
				var downPosition=0;
				var upPosition=0;
				var startPosition=-100;
				var mySwiper = new Swiper('.swiper-container',{
					slidesPerView:'auto',
					mode:'vertical',
					watchActiveIndex: false,
					onTouchStart: function() {
					  downPosition=0;
					  upPosition=0;
					  var nowTime=getNowTime();
					  $("#nowTime").html(nowTime);
					},
					onResistanceBefore: function(s, pos){	
					  downPosition = pos;
					},
					onResistanceAfter: function(s, pos){
					  upPosition = pos;
					},
					onTouchEnd: function(s){
					  if (s.touches.diff>0) {
						mySwiper.setWrapperTranslate(0,50,0);
						mySwiper.params.onlyExternal=true;
						$(downloader).addClass("visible");
						//调用下拉
						pullDownAction();
					  }
//					  if (s.touches.diff<0) {
//						mySwiper.params.onlyExternal=true;
//						$(uploader).show();
//						//调用下拉
//						pullUpAction();
//					  }
					}
			  });
				
			  function pullDownAction(){
					//下拉事件
						setTimeout(function(){
							if(config.pullDownFun()||1){
							if(mySwiper){
							  mySwiper.setWrapperTranslate(0,0,0);
							  mySwiper.params.onlyExternal=false;
							  mySwiper.updateActiveSlide(0);
							}
						}
						$(downloader).removeClass("visible");	
						},1000);
							
				}
			  	
				function pullUpAction(){
					//上拉事件
						if(mySwiper){
							//得到最后节点
							var lastSlide=mySwiper.getLastSlide();
							//添加数据
							//var newSlide = mySwiper.createSlide('<div class="title">Slide 7</div>','swiper-slide blue-slide','div');
							//newSlide.append();
							setTimeout(function(){
								
								if(config.pullUpFun()||1){
									lastSlide.append();
									mySwiper.params.onlyExternal=false;

									mySwiper.updateActiveSlide(0);
									$(uploader).hide();
								}
							 							
							},1000);
						}else{
							setTimeout(function(){
								$(uploader).hide();
							},1000);
						}	
				}
				return mySwiper;
  		}
  	};
  })(window);
  
  //获取当前时间
  function getNowTime(){
 	 var time=new Date();
 	 var hours=time.getHours();
 	 var minutes=time.getMinutes();
 	 if(hours<10){
 		 hours="0"+hours;
 	 }
 	 if(minutes<10){
 		 minutes="0"+minutes;
 	 }
 	 return hours+":"+minutes;
  }