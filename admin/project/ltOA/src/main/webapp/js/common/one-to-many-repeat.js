/*
* 作者    	:贾永强
* 创建时间	:2013-2-22
* 功能		：封装argDialog框架的弹出层功能，实现连续添加
*/

(function($){
	$.extend({
		repeatAdd:function(options){
			//触发事件的dom对象ID,
			var id = options.id ;

			if(!id){
				alert("id不能为空!");
				return ;
			}
			//子页面的路径
			var url = options.url ;
			if(!url){
				alert("url不能为空!");
				return;
			}
			//是否连续添加，默认是连续添加
			var isRepeat = options.isRepeat ;
			if(isRepeat == undefined){
				isRepeat = true;
			}
			//子页面的标题
			var title = options.title||"";
			//验证子页面的方法接口,返回true表示验证通过
			var check = options.subCheck||"";
			//获取子页面的方法
			var getData = options.getSubData;
			if(!getData){
				alert("getSubData属性不能为空!");
				return ;
			}
			//在父页面中显示数据
			var showHtml = options.showHTML;
			if(!showHtml){
				alert("showHTML函数不能为空!");
				return ;
			}
			//初始化子页面
			var init = options.subInit ||"";
			//回调函数
			var callback = options.callback ;
			if(!callback){
				alert("callback回调函数不能为空!");
				return ;
			}
			//父页面验证
			var parentCheck = options.parentCheck;

			//绑定点击事件
			$("#"+id).click(function(){
				$(this).attr("disabled",true);
				art.dialog.open(url,{
				title:title,
				ok:function(){
					//子窗口对象
					var ifr = this.iframe;
					var subWin = ifr.contentWindow;
			    	
			    	//第一步：调用子窗口对象的验证方法，如果没有则不调用,返回true表示验证通过，false验证未通过
			    	if(check && (!subWin[check]())){
			    		return false;
			    	}
			    	//第二步，获取子窗口的数据
			    	var data = subWin[getData]();

			    	//第三部，将数据保存在array对象中
			    	// datas.push(data);
			    	//父页面验证,如果验证通过继续，否则退出
			    	if(parentCheck){
			    		if(!parentCheck(data)){
			    			return;
			    		}
			    	}
			    	
			    	//第四部，将数据显示在父页面中
			    	showHtml(data);
			    	
			    	//第五步，调用回调函数返回数据，判断是否连续添加
			    	callback(data);
			    	if(isRepeat){
				    	subWin[init]();
				    	return false;
			    	}else{
			    		$("#"+id).attr("disabled",false);
			    		return true;
			    	}
				},
				cancel:function(){
					$("#"+id).attr("disabled",false);
					return true;	
				}
			});
		});
		}
	});
})($);