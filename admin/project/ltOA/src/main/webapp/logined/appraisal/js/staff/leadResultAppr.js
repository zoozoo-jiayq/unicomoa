var tid="";
var span_sptg ,span_spbtg,span_dc,view_type;
var khjd = "";
var  cz_isShow = false;
$(document).ready(function () {
	tid=$("#tid").val();
    span_sptg = $("#span_sptg");
    span_spbtg = $("#span_spbtg");
    span_dc = $("#span_dc");
    span_sptg.hide() ;
    span_spbtg.hide();
    span_dc.hide();
    view_type = $("#view_type").val();
    if(view_type=="1"){
        span_dc.show();
        $("#dh").text("> 考核成绩");
	}else {
        $("#dh").text("> 考核成绩");
	}

	//tid=getvl("tid");
	var groupId=$("#groupId").val();
	var pj=$("#pj").val();
    getData(groupId,pj);
	getName();
	getPingji();
});

var find=function() {
	var state=$("#state").val();
	var userName=$("#userName").val();
	getList(state,userName);
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
//			    art.dialog.alert('成功！')
		    	$("#khmc").text(data.khmc);	
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
function format(val) {
	return (typeof(val)=="undefined")?"0.00":val;//Math.round()
}
var getData=function(groupId,pj) {
	var paramData = {
			"tid":tid,
			"groupId":groupId,
			"jb":pj
	};
	$.ajax({
	    url : basePath + "performance/setup_approveList.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
                khjd =data.hkjd;
		    	$("#avg").text(format(data.avg));
		    	if(khjd==4&&view_type!="1"){
                    span_sptg.show() ;
                    span_spbtg.show();
		    	}
		    	if((khjd == 3||khjd == 5)&&view_type=="1"){
                    cz_isShow = true;
				}
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    },
        complete:function () {
            getList(groupId,pj);
        }
    });
	return false;
};
var getPingji=function() {
	var paramData = {
			"at.vid":tid
	};
	$.ajax({
	    url : basePath + "performance/task_findHierarchicalIndicators.action",
	    type : "post",
	    dataType : 'json',
	    data : paramData,
	    success : function(data) {
		    if (data) {
//			    art.dialog.alert('成功！')
		    	var text="";
		    	for(var i=0;data.ahiList.length>i;i++){
		    		text+="<option value='"+data.ahiList[i].qsf+","+data.ahiList[i].jsf+"'>"+data.ahiList[i].mc+"</option>";		 
		    	}
		    	$('#pj').append(text);
		    } else {
		    	art.dialog.alert("获取信息失败！");
		    }
	    }
	});
	return false;
};
function gradeChange(){
	var groupId=$("#groupId").val();
	var pj=$("#pj").val();
	getList(groupId,pj);
	getData(groupId,pj);
}
var appr=function(obj) {
	var context = "";
	if(obj == 6){
        context = "确定要审核通过吗？";
	}else if(obj == 5){
        context = "确定要审核不过吗？";
	}
	art.dialog.confirm(context,function () {
        updateTask(obj);
    })


	return false;
};

function updateTask(obj) {
    $('#shenhe').css('display','none');
    var paramData = {
        "at.vid":tid,
        "at.khjd":obj
    };
    $.ajax({
        url : basePath + "performance/task_updateTask.action",
        type : "post",
        dataType : 'json',
        data : paramData,
        success : function(data) {
            if (data) {
                art.dialog.alert('操作成功！')
            } else {
                art.dialog.alert("审核信息失败！");
            }
        }
    });
}

function getvl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]);  
    return null;  
};

/*
var getList=function(groupId,pj){
qytx.app.grid({
	id:'myTable',
	url:basePath + "performance/setup_approveList.action?tid="+tid+"&groupId="+groupId+"&jb="+pj,
	iDisplayLength:	15,
	valuesFn:[{
                    "aTargets": [6],// 覆盖
                    "fnRender": function ( oObj ) {
                        var userId =oObj.aData.khry;
                        var sfzs =oObj.aData.sfzs;
                    	var res="";  
				        res+="<a href='"+basePath+"logined/appraisal/jsp/staff/myResult.jsp?tid="+tid+"&userId="+userId+"&typeId="+1+"' class='enter_color mr20'>成绩分析</a>";
				        if(sfzs==1){
				        	res+="<a href='"+basePath+"logined/appraisal/jsp/staff/leadership_assessment_details.jsp?tid="+tid+"&userId="+userId+"' class='enter_color'>自述自评</a>";
				        }
				        return   res;
                    }
                }
		]
	});
    console.log(khjd+"*******");
    console.log( $('table tr').find('td:eq(6)'));
    $('table tr').find('td:eq(5)').hide();
    $('table tr').find('td:eq(1)').hide();

}
*/
function refresh(){
    var groupId=$("#groupId").val();
    var pj=$("#pj").val();
    getData(groupId,pj);
    setTimeout(function(){
    	var list = art.dialog.list;
        for (var i in list) {
            list[i].close();
        };
    }, 100)
}

function tzfc(khry,tid) {
    var openUrl = basePath+"/logined/appraisal/jsp/admin/adjustment.jsp?khry="+khry+"&tid="+tid;
    art.dialog.data("refresh", refresh)
    qytx.app.dialog.openUrl({
        url	: openUrl,
        title:	"调整考核成绩",
        size:	"L",
        customButton: [
            {
                name: '确定',
                callback: function () {
                    var iframe = this.iframe.contentWindow;
                    iframe.post();
                    return false;
                },
                focus: true
            },
            {name: '取消'}
        ]
    });
}

var toExcel=function(){
	var groupId=$("#groupId").val();
	var pj=$("#pj").val();
	window.location.href= basePath + "performance/export_approveList.action?tid="+tid+"&groupId="+groupId+"&jb="+pj;
}
function getList(groupId,pj){
var userId=$("#userId").val();
$('#myTable').dataTable({
	"bProcessing" : true,
	'bServerSide' : true,
	'fnServerParams' : function(aoData) {
		aoData.push({
					"name" : "recordWork.userInfo.userId",
					"value" : userId
				}
			);
	},
	"sAjaxSource" :basePath + "performance/setup_approveList.action?tid="+tid+"&groupId="+groupId+"&jb="+pj,
	"sServerMethod" : "POST",
	"sPaginationType" : "full_numbers",
	"bPaginate" : true, // 翻页功能
	"bLengthChange" : false, // 改变每页显示数据数量
	"bFilter" : false, // 过滤功能
	"bSort" : false, // 排序功能
	"bInfo" : true,// 页脚信息
	"bAutoWidth" : false,// 自动宽度
	"bDestroy" : true,
	"iDisplayLength" : 15, // 每页显示多少行
    "aoColumns": [
        {
            "mDataProp": "no",
        }, {
            "mDataProp": "user_name",
        }, {
            "mDataProp": "group_name"
        }, {
            "mDataProp": "job"
        }, {
            "mDataProp": "jb"
        }, {
            "mDataProp": "fz"
        }, {
            "mDataProp": null
        },
        {	"bVisible":cz_isShow,
            "mDataProp": null
        }],
	"oLanguage" : {
		"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
	},
	"fnDrawCallback" : function(oSettings) {
		$('#myTable tbody  tr td').each(function() {
			this.setAttribute('title', $(this).text());
		});
          
	},
	"fnInitComplete" : function() {
		// 重置iframe高度
	
	},
	"aoColumnDefs" : [{
		"aTargets" : [6],
		"fnRender" : function(oObj) {
            var userId =oObj.aData.khry;
            var sfzs =oObj.aData.sfzs;
            var typeId = 1;
            var res="";
            if( $("#view_type").val()=="1"){
                typeId = 2;
			}
            res+="<a href='"+basePath+"logined/appraisal/jsp/staff/myResult.jsp?tid="+tid+"&userId="+userId+"&typeId="+typeId+"' class='enter_color mr20'>成绩分析</a>";
            if(sfzs==1){
                res+="<a href='"+basePath+"logined/appraisal/jsp/staff/leadership_assessment_details.jsp?tid="+tid+"&userId="+userId+"&view_type="+typeId+"' class='enter_color'>自述自评</a>";
            }
            return   res;
		}
	},{
        "aTargets" : [7],
        "fnRender" : function(oObj) {
            var khry =oObj.aData.khry;
            var tid =oObj.aData.tid;
            var res="";
            res+="<a href='javascript:void(0);' onclick='tzfc("+khry+","+tid+")"+"' class='enter_color mr20'>调整</a>";
            return   res;
        }
    }
	]
});
}
