/*
* 作者：贾永强
* jquery的插件扩展
*/
(function($){
	$.extend({
		//将js对象转化为json格式的字符串
		objToString:function(obj){
			var str = "{";
			for(var prop in obj){
				str+="\""+prop+"\":"+"\""+obj[prop]+"\",";
			}
			str = str.substring(0,str.length-1);
			str+="}";
			return str;
		},
		//将对象数组转化为字符串
		arrayToString:function(array){
			var str = "[";
			for(var i=0; i<array.length ; i++){
				var temp = this.objToString(array[i]);
				str+=temp;
				if(i<array.length-1){
					str+=","
				}
			}
			str+="]";
			return str;
		}
	});
})($);