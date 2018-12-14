/**
 * Created by lilipo on 2016/12/26.
 */

var view_type,buttons,btn_delete,btn_modify,btn_start,btn_end,btn_apply;
function FormatDate (strTime) {
    var date = new Date(strTime);
    return date.getFullYear();
}
function FormatMS(val) {
    if(val == ''){
        return "无";
    }else {
        return val;
    }

}

function createObj(indicator, age){ //构造对象时可以传入初始化参数
    var obj = new Object(); //创建对象

    obj.indicator = indicator;
    obj.age = age;
    return obj; //返回对象
}

function getSize(value, array) {
    var size = 0;
    for (var i = 0; i < array.length; i++) {
        if (array[i] == value) {
            size++;
        }
    }
    return size;
}
function painting(data) {
    if(view_type!=""){
        buttons.show();
    }

    $("#khsj").text(FormatDate(data.khsj)+"年");
    $("#khjd").text(data.khjdStr);
    $("#ms").text(FormatMS(data.ms));
    $("#khryStr").text(data.khryStr);
    $("#dfryStr").text(data.dfryStr);
    $("#shryStr").text(data.shryStr);
    $("#khmc").text(data.khmc);
    /**
     * 考核进度 -1草稿  1 待发布 2 评分中 3评分结束  4 待审批 5审批退回  6结束
     */
    btn_delete.show();
    switch(data.khjd)
    {
        case 1:
            btn_modify.show();
            btn_start.show();
            $("#khjd").addClass("blue_color");
            break;
        case 2:
            btn_end.show();
            $("#khjd").addClass("color_green");
            break;
        case 3:
            btn_apply.show();
            $("#khjd").addClass("color_green");
            break;
        case 4:
            $("#khjd").addClass("color_red");
            break;
        case 5:
            btn_apply.show();
            $("#khjd").addClass("color_red");
            break;
        default:
    }

    var alltypes =  new Array();
    var types =  new Array();
    var  temp = null ;
    var s = '';
    for (var i=0;i<data.listIndicators.length;i++)
    {
        alltypes.push(data.listIndicators[i].zbfl);
        if(temp!=null){
            if(temp.zbfl != data.listIndicators[i].zbfl){
                s += '<tr><td class="wd115 text_hide" id="type'+data.listIndicators[i].zbfl+'">'+data.listIndicators[i].zbflStr+'</td> ' +
                    '<td class="wd165 text_hide">'+data.listIndicators[i].zbmc+'</td> ' +
                    '<td class="wd500 text_hide">'+FormatMS(data.listIndicators[i].ms)+'</td> ' +
                    '<td class="wd100 text_hide">'+data.listIndicators[i].fz+'</td>' +
                    ' </tr>';
                types.push(data.listIndicators[i].zbfl);
            }else {
                s += '<tr>'+
                    '<td class="wd165 text_hide">'+data.listIndicators[i].zbmc+'</td> ' +
                    '<td class="wd165 text_hide">'+FormatMS(data.listIndicators[i].ms)+'</td> ' +
                    '<td class="wd100 text_hide">'+data.listIndicators[i].fz+'</td>' +
                    ' </tr>';
            }
        }else {
            s += '<tr><td class="wd115 text_hide" id="type'+data.listIndicators[i].zbfl+'">'+data.listIndicators[i].zbflStr+'</td> ' +
                '<td class="wd165 text_hide">'+data.listIndicators[i].zbmc+'</td> ' +
                '<td class="wd165 text_hide">'+FormatMS(data.listIndicators[i].ms)+'</td> ' +
                '<td class="wd100 text_hide">'+data.listIndicators[i].fz+'</td>' +
                ' </tr>';
            types.push(data.listIndicators[i].zbfl);
        }
        temp = data.listIndicators[i];
    }
    $("#table").append(s);
    for(var i=0;i<types.length;i++){
        var id = "#type"+types[i];
        $(id).attr("rowspan",getSize(types[i], alltypes));
    }

}

$(function(){
    view_type = $("#view_type").val();
    buttons = $("#buttons");
    btn_delete = $("#btn_delete");
    btn_modify = $("#btn_modify");
    btn_start = $("#btn_start");
    btn_end = $("#btn_end");
    btn_apply = $("#btn_apply");

    buttons.hide();
    btn_delete.hide();
    btn_modify.hide();
    btn_start.hide();
    btn_end.hide();
    btn_apply.hide();

    var dataParam = {
        "tid":$("#tid").val()
    }
    // 文档就绪
    $.ajax({
        url : basePath+"performance/setup_getAppraisalInfo.action",
        type : 'post',
        dataType : 'json',
        data : dataParam,
        success : function(data) {
            if(data!=null){
                painting(data)
            }else {
                art.dialog.alert("错误");
            }

        }
    });
});
// 更新状态
function updateTask(type,context,isdel) {
    var dataParam = {
        "at.vid":$("#tid").val(),
        "at.khjd":type
    }
    art.dialog.confirm(context,function () {
        $.ajax({
            url : basePath+"performance/task_updateTask.action",
            type : 'post',
            dataType : 'json',
            data : dataParam,
            success : function(data) {
                if(data==1){
                    if(isdel){
                        var obj=window.self;
                        obj=obj.window.parent;
                        obj.location.href =basePath+"performance/welcome.action";
                    }else {
                        window.location.reload();//刷新当前页面.
                    }
                }else {
                    art.dialog.alert("错误");
                }

            }
        });
    });


}

function modifyTask() {
	var obj=window.self;
	obj=obj.window.parent;
	obj.location.href =basePath+"logined/appraisal/jsp/admin/add_createNewCheck.jsp?vid="+$("#tid").val()+"&ck_vid=&type=1,1,1,1";
}