var tid="";
var userId="";
$(document).ready(function () {
	tid=$("#tid").val();
	//tid=getvl("tid");
	userId=$("#userId").val();
	getList();
});

var find=function() {

}

function FormatMS(val) {
    if(val == ''){
        return "无";
    }else {
        return val;
    }

}

var getList=function() {
	var paramData = {
			"tid":tid,
			"userId":userId
	};
	$.ajax({
	    url : basePath + "performance/setup_getZiPing.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	painting(data);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
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

function format(val) {
	return (typeof(val)=="undefined")?"0":parseInt(val);
}
function painting(data) {
    $("#userName").text(data.userName);
    $("#groupName").text(data.groupName);
    $("#job").text(data.job);
    $("#wdsz").text(data.wdsz);
    if (data.atta!=null) {
        var attaStr = ' <a  onclick="downLoadAttachment(' + data.atta.id + ')" class="file_tishi"><em></em>' + data.atta.attachName + '</a>'
        $("#attachmentList").html(attaStr)
    }
    var alltypes =  new Array();
    var types =  new Array();
    var  temp = null ;
    var s = "";
    var sum=0;
    for (var i=0;i<data.wdpf.length;i++)
    {
        alltypes.push(data.wdpf[i].zbfl);
        if(temp!=null){
            if(temp.zbfl != data.wdpf[i].zbfl){
                var df=0;
            	if(data.wdpf[i].df){
            		df=format(data.wdpf[i].df);
            	}
                s += '  <tr> <td class="wd115 text_hide" id="type'+data.wdpf[i].zbfl+'">'+data.wdpf[i].zbflStr+'</td> ' +
                    '<td class="wd165 text_hide">'+data.wdpf[i].zbmc+'</td> ' +
                    '<td class="wd400 text_hide">'+FormatMS(data.wdpf[i].ms)+'</td> ' +
                    '<td class="wd100 text_hide">'+format(data.wdpf[i].fz)+'</td>' +
                    '<td class="dafen_td wd80 text_hide"> <div class="pr">'+df+'分</div> </td>' +
                    ' </tr>';
                types.push(data.wdpf[i].zbfl);
                sum+=parseInt(df);
            }else {
                var df=0;
            	if(data.wdpf[i].df){
            		df=format(data.wdpf[i].df);
            	}
                s += '  <tr>'+
                    '<td class="wd165 text_hide">'+data.wdpf[i].zbmc+'</td> ' +
                    '<td class="wd400 text_hide">'+FormatMS(data.wdpf[i].ms)+'</td> ' +
                    '<td class="wd100 text_hide">'+format(data.wdpf[i].fz)+'</td>' +
                    '<td class="dafen_td wd80 text_hide"> <div class="pr">'+df+'分</div> </td>' +
                    ' </tr>';
                sum+=parseInt(df);
            }
        }else {
            var df=0;
        	if(data.wdpf[i].df){
        		df=format(data.wdpf[i].df);
        	}
            s += '  <tr> <td class="wd115 text_hide" id="type'+data.wdpf[i].zbfl+'">'+data.wdpf[i].zbflStr+'</td> ' +
                '<td class="wd165 text_hide">'+data.wdpf[i].zbmc+'</td> ' +
                '<td class="wd400 text_hide">'+FormatMS(data.wdpf[i].ms)+'</td> ' +
                '<td class="wd100 text_hide">'+format(data.wdpf[i].fz)+'</td>' +
                '<td class="dafen_td wd80 text_hide"> <div class="pr">'+df+'分</div></td>' +
                ' </tr>';
            types.push(data.wdpf[i].zbfl);
            sum+=parseInt(df);
        }
        temp = data.wdpf[i];
    }
    $("#firstTr").append(s);
    for(var i=0;i<types.length;i++){
        var id = "#type"+types[i];
        $(id).attr("rowspan",getSize(types[i], alltypes));
    }
    $("#sum").text(sum);
}

function getvl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]);  
    return null;  
};