/*
* 功能：构建级联select 并初始化
* 作者:jiayq
* 版本:v1.0
*/
(function($,document,window){
	$.fn.extend({
		generate:function(options){
			var selectObj 	= this;
			//数据源地址
			var url   		= options.url || "";
			//数据集合
			var datas 		= options.data || new Array(0);
			//几级关联,默认只显示1级
			var level 		= options.level || 1;
			//初始name值,["",""]
			var initName    = options.initName || [];
			//初始化value值["value1","value2"]
			var initValue   = options.initValue || [];
			//onchange回调函数
			var callback	= options.callback || function(data){};
			//初始化class样式
			var classStyle  = options.classStyle || "";
			

			var setUp = function(select,datas,level,callback,initName,initValue,classStyle){
				var array = new Array();
				$(select).empty();
				$(select).append(getDefaultOption());
				array.push(select);
				//创建select并将select放到数组中
				var tempSelect = select;
				for(var i=0; i<level-1; i++){
 					var temp = getDefaultSelect();
					array.push(temp);
					$(tempSelect).after(temp);
					tempSelect = temp;
				}

				//初始化命名和change事件
				for(var i=0; i<array.length; i++){
					if(initName[i]){
						$(array[i]).attr("name",initName[i]);
					}
					$(array[i]).change(function( ){
						callback(this);
					});
				}

				//初始化第一个select的数据
				for(var i = 0 ;i<datas.length; i++){
					select.append(getDefaultOption(datas[i].id,datas[i].name));
				}

				//change事件
				function actionEvent(){
					changeEvent(0,datas,array);
				}
				array[0].bind("change",actionEvent);
	
				//初始化默认值
				dataChange(0,initValue,array);
				
				//添加样式
				if(classStyle){
					addClassStyle(classStyle,array);
				}

			},
			getDefaultOption = function(){
				var temp   = ""; 
				var id 	   = arguments[0]||"";
				var text   = arguments[1]||"--please select--";
				var	temp   = "<option value="+id+">"+text+"</option>"	
				return temp;
			},
			getDefaultSelect = function(){
				var select = document.createElement("select");
				$(select).append(getDefaultOption());
				return select;
			},
			changeEvent = function(targetSelectIndex,datas,array){
				if(array.length-1>targetSelectIndex){
					for(var i=targetSelectIndex+1;i<array.length;i++){
						reset(array[i]);
					}
					var targetSelect = $(array[targetSelectIndex]); 
					var targetValue =  targetSelect.val();
					var data = getData(targetValue,datas);
					if(data && data instanceof Array){
						for(var i=0; i<data.length; i++){
							var tempData = data[i];
							$(array[targetSelectIndex+1]).append(getDefaultOption(tempData.id,tempData.name));
						}
						$(array[targetSelectIndex+1]).change(function(){
							changeEvent(targetSelectIndex+1,data,array);
						});
					}
				}
			},
			reset = function(select){
				if(select){
					select.options.length = 0;
					$(select).append(getDefaultOption());
				}
			},
			getData = function(id,datas){
				if(datas && datas instanceof Array){
					for(var i=0;i<datas.length;i++){
						if(datas[i].id == id){
							return datas[i].children;
						}else{
							arguments.callee(id,datas[i].children);
						}
					}
				}
			},
			addClassStyle  = function(classStyle,array){
				for(var i=0; i<array.length;i++){
					$(array[i]).addClass(classStyle);
				}
			},
			dataChange = function(index,initValue,array){
				if(initValue && initValue[index]){
					$(array[index]).val(initValue[index]);
					$(array[index]).change(function(){
						if(index < array.length){
							dataChange(index+1,initValue,array);
						}
					});
					$(array[index]).change();
				}
			};	

			if(url){
				$.get(url,function(jsondata){
					setUp(selectObj,jsondata,level,callback,initName,initValue,classStyle);
				});
			}else if(datas){
				setUp(selectObj,datas,level,callback,initName,initValue,classStyle);
			}			
			
		}
	});
})(jQuery,document,window);