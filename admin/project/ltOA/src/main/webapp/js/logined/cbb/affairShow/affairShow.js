var affairShowType="";
$(function(){
	
	$("#save").click(function(){
		affairShowType="";
		getSendType();
	});
});

/**
 * 获取事务提醒的类型
 */
function getSendType(){
	affairShowType="";
   if($("#affairType1").val()==1 && ($("#affairType1").attr("checked")=="checked")){
	   affairShowType+=1+"_";
   }else{
	   affairShowType+=0+"_";
   }
   if($("#affairType2").val()==1 && ($("#affairType2").attr("checked")=="checked")){
	   affairShowType+=1+"_";
   }else{
	   affairShowType+=0+"_";
   }
   if($("#affairType3").val()==1 && ($("#affairType3").attr("checked")=="checked")){
	   affairShowType+=1;
   }else{
	   affairShowType+=0;
   }
   return affairShowType;
}