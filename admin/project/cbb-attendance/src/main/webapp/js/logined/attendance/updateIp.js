$(function(){
	var ipSetId = $("#ipSetId").val();
	var url = basePath+"attendance/preUpdateIp.action";
	var paramData={"ipSetId":ipSetId};
	$.post(url,paramData,function(data){
		if(data != "1"){
			data = eval("("+data+")");
			var beginIps = data.beginIp.split(".");
			var endIps = data.endIp.split(".");
			$("#beginIp1").val(beginIps[0]);
			$("#beginIp2").val(beginIps[1]);
			$("#beginIp3").val(beginIps[2]);
			$("#beginIp4").val(beginIps[3]);
			$("#endIp1").val(endIps[0]);
			$("#endIp2").val(endIps[1]);
			$("#endIp3").val(endIps[2]);
			$("#endIp4").val(endIps[3]);
		}else{
			qytx.app.dialog.alert("不能修改,请稍后重试!");
		}
	});
	
	var inputArray = $("#tBody tr td input");
	inputArray.each(function(i,value){
		$(this).keyup(function(e){
			var key =  e.which;
			IpFocus($(this),key);
		});
	});
	
	$(".ipClass").keyup(function(){
		var ipv = $.trim($(this).val());
		if(ipv!=""){
			$(this).val(parseInt(ipv));
		}
	});
});

function IpFocus(obj,key){
	//先判断是否大于255     再让其推进后一个焦点 . 右方向键 space
	if(key==110 || key==39 || key==32){
		if(obj.attr("name")=="beginIp4"){
			$("#endIp1").focus();
		}else{
			obj.next().focus();
		}
	}else if(key==37){
		if(obj.attr("name")=="endIp1"){
			$("#beginIp4").focus();
		}else{
			obj.prev().focus();
		}
	}else if(obj.val().length==3){
		if(obj.attr("name")=="beginIp4"){
			$("#endIp1").focus();
		}else{
			obj.next().focus();
		}
	}
}

function updateIp(){
	var re =  /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-4])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-4])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-4])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
	var ipSetId = $("#ipSetId").val();
	//拼接字符串
	var inputArray = $("#tBody tr td input");
	var input1Array = $("#div1 input");
	var input2Array = $("#div2 input");
	var beginIp = "";
	var endIp = "";
	var isNullBeginIp="";
	var isNullEndIp="";
	
	input1Array.each(function(i,value){
		if(i!=3){
			beginIp+=$.trim($(this).val())+".";
		}else{
			beginIp+=$.trim($(this).val());
		}
		isNullBeginIp+=$.trim($(this).val());
		if(isNullBeginIp!=""&&$.trim($(this).val())==""){
			qytx.app.dialog.alert("起始IP格式不正确");
			return;
		}
	});
	input2Array.each(function(i,value){
		if(i!=3){
			endIp+=$.trim($(this).val())+".";
		}else{
			endIp+=$.trim($(this).val());
		}
		isNullEndIp+=$.trim($(this).val());
		if(isNullEndIp!=""&&$.trim($(this).val())==""){
			qytx.app.dialog.alert("截止IP格式不正确");
			return;
		}
	});
	if(isNullBeginIp==""){
		qytx.app.dialog.alert("起始IP不能为空!");
		return;
	}
	if(isNullEndIp==""){
		qytx.app.dialog.alert("截止IP不能为空!");
		return;
	}
	if(!re.test(beginIp)){
		qytx.app.dialog.alert("起始IP格式不正确");
		return false;
	}
	if(!re.test(endIp)){
		qytx.app.dialog.alert("截止IP格式不正确");
		return false;
	}
	//插入Ip
	if(isIpSection(beginIp,endIp)=="notEqual"){
		qytx.app.dialog.alert("起始IP与截止IP不在同一段IP范围内");
		return;
	}else if(isIpSection(beginIp,endIp)=="Error"){
		qytx.app.dialog.alert("截止IP不能小于起始IP");
		return;
	}
	if(parseInt(beginIp4[3])>parseInt(endIp4[3])){
		qytx.app.dialog.alert("起始IP与截止IP范围不正确");
	}
	var urlStr = basePath+"attendance/updateIp.action";
	var paramData = {
			"ipSetId":ipSetId,
			"beginIp":beginIp,
			"endIp":endIp
	};
	qytx.app.ajax({
		url:urlStr,
		type:"post",
		data:paramData,
		success:function(data){
			if(data==""){
				qytx.app.dialog.tips("修改成功!",function(){
					art.dialog.close("updateIp");
				});
			}else{
				qytx.app.dialog.alert("修改失败,请稍后重试!")
			}
		}
	});
}

/**
 * 判断两者是否在同一IP段内
 * @param beginIp
 * @param endIp
 */
function isIpSection(beginIp,endIp){
	var beginIpArray = beginIp.split(".");
	var endIpArray = endIp.split(".");
	var ip1 = (beginIpArray[0]==endIpArray[0]);
	var ip2 = (beginIpArray[1]==endIpArray[1]);
	var ip3 = (beginIpArray[2]==endIpArray[2]);
	var ip4 = (parseInt(beginIpArray[3])<=parseInt(endIpArray[3]));
	if(ip1&&ip2&&ip3){
		if(!ip4){
			return "Error";
		}else{
			return "";
		}
	}else{
		return "notEqual";
	}
	
}