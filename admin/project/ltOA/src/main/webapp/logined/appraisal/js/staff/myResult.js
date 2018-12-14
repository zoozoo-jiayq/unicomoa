var tid="";
var userId="";
var typeId="";
var tzyy="";
$(document).ready(function () {
	tid=$("#tid").val();
	userId=$("#userId").val();
	typeId=$("#typeId").val();
    if (typeId == 1) {
        $('#back').attr("href",basePath+"logined/appraisal/jsp/staff/leadResultAppr.jsp?tid="+tid);
        $('#back').css('display', 'block');
    } else if (typeId == 2) {
        $('#back').attr("href",basePath+"logined/appraisal/jsp/staff/leadResultAppr.jsp?tid="+tid+"&view_type=1");
        $('#back').css('display', 'block');
    } else {
        $('#back').css('display', 'none');
    }
    getList();
    getName();
});

var find=function() {

}
var getName=function() {
	var paramData = {
			"tid":tid
	};
	$.ajax({
	    url : basePath + "performance/setup_getAppraisalInfo.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
			   // art.dialog.alert(data.khmc+"111")
		    	$("#khmc").text(data.khmc);	
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
var getList=function() {
	var paramData = {
			"tid":tid,
			"userId":userId
	};
	$.ajax({
	    url : basePath + "performance/setup_myTaskInfo.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	/*$("#dpfrs").html("");
		    	$("#firstTr").html("");
		    	var html ="";
		    	var text="";
		    	var num=0;*/
		    	painting(data);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
function show() {
	//art.dialog.alert(tzyy);
	artDialog.alert(tzyy)
}
/**  
 * 警告  
 * @param {String}消息内容  
 */  
artDialog.alert = function (content, callback) {  
	return artDialog({  
	id: 'Alert',  
	title:'调整原因',
	//icon: 'warning',  
	//padding:'20px',
	fixed: true,  
	//lock: true,  
	width:500,  
	height:130,  
	content:'<p class="gonggao_detail" style="font-size:14px;">' +content+'</p>',  
	ok: true,  
	close: callback  
	});  
};  

function getSize(value, array) {
    var size = 0;
    for (var i = 0; i < array.length; i++) {
        if (array[i] == value) {
            size++;
        }
    }
    return size;
}

function formatNull(val) {
    return (typeof(val)=="undefined")?"无":val;
}
function format(val) {
    return (val=="")?"无":val;
}
function FormatMS(val,text) {
    if(typeof(val)=="undefined"||val == ''){
        return text;
    }else {
        return val;
    }
}
function painting(data) {
    $("#userName").text(data.userName);
    $("#groupName").text(data.groupName);
    $("#job").text(format(data.job));
    if(data.tzfz&&data.tzfz!="0.00"){
        $("#ysfz").text(data.fz);
        var zzfz=data.zzfz;
        $("#zzfz").text(zzfz);
        if(data.tzyy){
            tzyy=data.tzyy;
        }else{
        	tzyy="无原因";
        }

    	 $("#ks_fz").css('display','none'); 
     	$("#gd_zz").css('display','block'); 
    	$("#gd_ys").css('display','block'); 
    }else{
        $("#fz").text(data.fz);
    	$("#ks_fz").css('display','block'); 
    	$("#gd_zz").css('display','none'); 
    	$("#gd_ys").css('display','none'); 
    }

    $("#jb").text(data.jb);
    var alltypes =  new Array();
    var types =  new Array();
    var  temp = null ;
    var s = "";
    var htm = "";
    var sum=0;
    var df=0;
    if(data.wdpf){
        for (var i=0;i<data.wdpf.length;i++)
        {
        	if(data.wdpf[i].df){
        		df=data.wdpf[i].df;
        	}else{
        		df=0;
        	}
        	
            alltypes.push(data.wdpf[i].zbfl);
            if(temp!=null){
                if(temp.zbfl != data.wdpf[i].zbfl){
                    s += '  <tr> <td class="wd115 text_hide" id="type'+data.wdpf[i].zbfl+'">'+data.wdpf[i].zbflStr+'</td> ' +
                        '<td class="wd165 text_hide">'+data.wdpf[i].zbmc+'</td> ' +
                        '<td class="wd400 text_hide">'+FormatMS(data.wdpf[i].ms,"无")+'</td> ' +
                        '<td class="wd100 text_hide">'+data.wdpf[i].fz+'</td>' +
                        '<td class="wd80 text_hide">'+FormatMS(df,"0.00")+'</td>' +
                        ' </tr>';
                    types.push(data.wdpf[i].zbfl);
                    sum+=df;
                }else {
                    s += '  <tr>'+
                        '<td class="wd165 text_hide">'+data.wdpf[i].zbmc+'</td> ' +
                        '<td class="wd400 text_hide">'+FormatMS(data.wdpf[i].ms,"无")+'</td> ' +
                        '<td class="wd100 text_hide">'+data.wdpf[i].fz+'</td>' +
                        '<td class="wd80 text_hide">'+FormatMS(df,"0.00")+'</td>' +
                        ' </tr>';
                    sum+=df;
                }
            }else {
                s += '  <tr> <td class="wd115 text_hide" id="type'+data.wdpf[i].zbfl+'">'+data.wdpf[i].zbflStr+'</td> ' +
                    '<td class="wd165 text_hide">'+data.wdpf[i].zbmc+'</td> ' +
                    '<td class="wd400 text_hide">'+FormatMS(data.wdpf[i].ms,"无")+'</td> ' +
                    '<td class="wd100 text_hide">'+data.wdpf[i].fz+'</td>' +
                    '<td class="wd80 text_hide">'+FormatMS(df,"0.00")+'</td>' +
                    ' </tr>';
                types.push(data.wdpf[i].zbfl);
                sum+=df;
            }
            temp = data.wdpf[i];
        }
    }else{
    	s +="<tr><td colspan='5'>暂无相关数据</td></tr>";
    }

    $("#firstTr").append(s);
    for(var i=0;i<types.length;i++){
        var id = "#type"+types[i];
        $(id).attr("rowspan",getSize(types[i], alltypes));
    }
    function sxgd(){
		var lists=$(".pingjia_list li p");
		for(var i=0;i<lists.length;i++ ){
			var p = lists[i];
			var liH=$(p).height()+14;
			$(p).prev().height(liH);
		}
	}
		
    $("#sum").text(data.fz);
    if(data.wdkhpy&&data.wdkhpy.length>0){
    	for(var i=0;i<data.wdkhpy.length;i++){
    		if(data.wdkhpy[i].pl){
        		htm+="<li><span class='pingjia_list_title'>"+data.wdkhpy[i].khjssjStr+"：</span><p class='pingjia_list_content'>"+data.wdkhpy[i].pl+"</p></li>";   			
    		}else{
        		//htm+="<li><span class='pingjia_list_title'>"+data.wdkhpy[i].khjssjStr+"：</span><p class='pingjia_list_content'>无</p></li>";   			
    		}
    	}
    	sxgd();
    }else{
    	htm+='<span style="line-height:24px;">无</span>';
    }
	$("#firstLi").append(htm);
}
