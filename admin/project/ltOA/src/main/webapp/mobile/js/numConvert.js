function Num_to_English(Num){

	 if(isNaN(Num))
	 { //验证输入的字符是否为数字
	 tips_alert("请检查数字是否正确");
	 return;
	 }
	 var tmpnewchar='';
	 switch(Num)
	 {
	
	 case 1: tmpnewchar="A"  ;break;
	 case 2: tmpnewchar="B" ;break;
	 case 3: tmpnewchar="C" ;break;
	 case 4: tmpnewchar="D";break;
	 case 5: tmpnewchar="E" ;break;
	 case 6: tmpnewchar="F" ;break;
	 case 7: tmpnewchar="G" ;break;
	 case 8: tmpnewchar="H";break;
	 case 9: tmpnewchar="I";break;
	 case 10: tmpnewchar="J"  ;break;
	 case 11: tmpnewchar="K" ;break;
	 case 12: tmpnewchar="L" ;break;
	 case 13 : tmpnewchar="M";break;
	 }
	
	 return tmpnewchar;
}
//将数字转换成中文
function Num_to_Chinese(Num)
{

	 if(isNaN(Num))
	 { //验证输入的字符是否为数字
	 tips_alert("请检查数字是否正确");
	 return;
	 }
	 //字符处理完毕后开始转换，采用前后两部分分别转换
	 part = String(Num).split(".");
	 newchar = "";
	 //小数点前进行转化
	 for(i=part[0].length-1;i>=0;i--)
	 {
	 if(part[0].length > 10)
	 {
		 tips_alert("位数过大，无法计算");
	 return "";
	 }//若数量超过拾亿单位，提示
	 tmpnewchar = "";
	 perchar = part[0].charAt(i);
	 switch(perchar)
	 {
	 case "0": tmpnewchar="零" + tmpnewchar ;break;
	 case "1": tmpnewchar="一" + tmpnewchar ;break;
	 case "2": tmpnewchar="二" + tmpnewchar ;break;
	 case "3": tmpnewchar="三" + tmpnewchar ;break;
	 case "4": tmpnewchar="四" + tmpnewchar ;break;
	 case "5": tmpnewchar="五" + tmpnewchar ;break;
	 case "6": tmpnewchar="六" + tmpnewchar ;break;
	 case "7": tmpnewchar="七" + tmpnewchar ;break;
	 case "8": tmpnewchar="八" + tmpnewchar ;break;
	 case "9": tmpnewchar="九" + tmpnewchar ;break;
	 }
	 switch(part[0].length-i-1)
	 {
	 case 0: tmpnewchar = tmpnewchar +"元" ;break;
	 case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"十" ;break;
	 case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"百" ;break;
	 case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"千" ;break;
	 case 4: tmpnewchar= tmpnewchar +"万" ;break;
	 case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"十" ;break;
	 case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"百" ;break;
	 case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"千" ;break;
	 case 8: tmpnewchar= tmpnewchar +"亿" ;break;
	 case 9: tmpnewchar= tmpnewchar +"十" ;break;
	 }
	 newchar = tmpnewchar + newchar;
	 }
	 //替换所有无用汉字
	 while(newchar.search("零零") != -1)
	 newchar = newchar.replace("零零", "零");
	 newchar = newchar.replace("零亿", "亿");
	 newchar = newchar.replace("亿万", "亿");
	 newchar = newchar.replace("零万", "万");
	 newchar = newchar.replace("零元", "");
	 newchar = newchar.replace("元", "");
	 return newchar;
}

