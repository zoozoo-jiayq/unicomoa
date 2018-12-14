<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
    <c:if test="${adminUser.taoDa==1}">
<script type="text/javascript" src="${ctx}js/logined/publicDom/LodopFuncs.js"></script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="<%=request.getContextPath()%>/logined/publicDom/lodop/install_lodop32.exe"></embed>
	<param name="Caption" value="">
</object> 
</c:if>
<input type="hidden" id="printCode" />
<div id="signPicContent" style="left:-1000px;"></div>
<script type="text/javascript">
//---------------打印 begin---------------
	var LODOP; //声明为全局变量
	//检测是否安装控件
	function CheckIsInstall() {	 
		try{ 
		     var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
			if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")) {
				// alert("本机已成功安装过Lodop控件!\n  版本号:"+LODOP.VERSION); 
			}
		 }catch(err){ 
			// alert("Error:本机未安装或需要升级!"); 
 		 } 
	};
	CheckIsInstall();

	// // //套打，只打印数据
	function printDataOnly(){
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		eval($("#printCode").val());
		//打印盖章
		printRedSign();
		LODOP.PRINT_DESIGN();
	}


	//打印HTML
	function printForm(){
		window.open(basePath+"/dom/public!printView.action?instanceId="+encodeURI($("#instanceId").val()));
	};

	function createImg(){
		var objs = document.getElementsByTagName("object");
		for (var i = objs.length - 1; i >= 0; i--) {
			var obj = objs[i];
			// <object width="152" height="152" classid="clsid:AA4B3728-B61C-4bcc-AEE7-0AA47D3C0DDA" style="position: absolute; top: 68px; left: 261px;" codebase="http://localhost:8080/qyoa/js/logined/ntko/ntkoWebSign.cab#version=3,0,5,0"/>
			if(obj.classid == "clsid:DB5B521C-DA92-48e0-AE32-BDC944858D42"){
				var str = "data:image/gif;base64," + obj.SignPicBase64Str;
				var _style = "position:absolute;z-index:-1;";
				$("#customForm").append("<img border='0' style='"+_style+"' src='"+str+"'>");
			}
		}
	}

	//打印盖章
	function printRedSign(){
		var objs = document.getElementsByTagName("object");
		for (var i = objs.length - 1; i >= 0; i--) {
			var obj = objs[i];
			if(obj.classid == "clsid:AA4B3728-B61C-4bcc-AEE7-0AA47D3C0DDA"){
				var _top = obj.style.top;
				var _left = obj.style.left;
				var width = obj.style.width;
				var height = obj.style.heigth;
				_top = _top.replace("px","");
				_left = _left.replace("px","");
				_top = _top*1+200;
				_left = _left*1-500;
				var str = "data:image/gif;base64," + obj.SignPicBase64Str;
				var _style = "position:absolute;z-index:-1;";
				LODOP.add_print_image(_top,_left,"600px","600px","<img border='0' transcolor='#FFFFFF' style='"+_style+"' src='"+str+"'>");
			}
		}
	}

	function getPrintCode(){
		$.get("${ctx}/dom/public!printSet.action?instanceId="+encodeURI($("#instanceId").val()),function(data){
			  $("#printCode").val(data);
		});
	}

	getPrintCode();


		//打印
	function doPrint() {
		var instanceId = $("#instanceId").val();
		$.post("${ctx}/dom/public!isTaoDa.action?instanceId="+encodeURI(instanceId),function(data) {
			// body...
			if(data == "yes"){
				if($("#printFlag").val()==1){
					printDataOnly();
				}else{
					art.dialog.alert("打印控件未启用！");		
				}
			}else{
				printForm();
			}
		});	
	}

</script>