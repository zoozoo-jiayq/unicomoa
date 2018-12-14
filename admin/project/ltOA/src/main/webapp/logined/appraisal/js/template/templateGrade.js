var fenJiList=null;//所有分级模板接口数据
var defaultVid=null;//默认显示模板的vid	
var vid=null;//当前显示模板的vid
var lx=null;//当前显示模板的lx
// 先创建一个js数组
var ahiList = new Array();
$(function() {
	// 设置分级列表高度
	var fenjiListH=$(window).height()-216;
	$(".fenji_list").css("height",fenjiListH);
	var templateH=$(window).height()-90-20;
	$("#templateBox").css({"height":templateH,"overflow-y":"auto"});
	
	initTemplateList();
	
	$(".fenji_list li").live("click",function(){
		$(this).children().addClass("active");
		$(this).siblings().children('.active').removeClass('active');
	});
	
	$(".fenji_list li a").live("click",function(){
		var fenJiName=$(this).html();
		$.each(fenJiList,function(i,fenji){
			if(fenJiName==fenji.fjmc){
				$("#fenjiName").val(fenji.fjmc);
				$("#fenjiDescrible").val(fenji.fjms);
				vid=fenji.vid;
				lx=fenji.lx;
			}
		})	
		
		findByVid(vid);
        $("#tiJiao").text("修改");
        $("#delete").show();
		defaultTemplateInput(lx)
	});
	
	//增加模板
	$("#addTemplate").live("click",function(){
		vid=null;
		lx=null;
		$("#templateBox input").val("");
		$("#templateBox textarea").val("");
		/*
		$(".btn_container2").show();*/
        $("#templateBox input").attr("disabled",false);
        $("#templateBox textarea").attr("disabled",false);
        $(".btn_container2").show();
        $("#tiJiao").text("新增");
        $("#templateTable tbody").html("");
        addTr();
        $("#delete").hide();
	})

	function addTr() {
        if(lx==-1){
             
            art.dialog.alert("默认模板，不能修改！");
        }else{
            var tdContent="";
            tdContent+='<tr><td><input type="checkbox" name="checked"></td>'
                +'<td><input type="text" class="creat_list_input text_center" name="mc"></td>'
                +'<td>'
                +'<div class="qvjian_box">'
                +'<div class="pr fl wd60"><input type="text" maxlength="5" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" class="creat_list_input text_center" name="smallValue"></div>'
                +'<span class="fl">&nbsp; ~ &nbsp;</span>'
                +'<div class="pr fl wd60"><input type="text" maxlength="5" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" onblur="checkfz(this)" class="creat_list_input text_center" name="bigValue"></div>'
                +'<span class="fl ml5 mt3">&nbsp;分</span>'
                +'</div>'
                +'</td>'
                +'</tr>';
            $("#templateTable tbody").append(tdContent);
        }
    }

	//	增加标准
	$("#addStandard").live("click",function(){
        addTr();
	})
	
	//	删除标准
	$("#delStandard").live("click",function(){
		if(lx==-1){
			art.dialog.alert("默认模板，不能修改！");
		}else{	
			var currentTr=$("input[name='checked']:checked").parent().parent();
			if(currentTr.html()==undefined){
				art.dialog.alert("请选择要删除的分级标准！");
			}else{
				currentTr.remove();
			}
		}
	})

	function checkNum() {
		var sta = false;
        var inputSVob = $("#templateTable tbody input[name='smallValue']");
        var inputBVob = $("#templateTable tbody input[name='bigValue']");
        var bigValue = findNum(inputSVob, inputBVob, 0);
        var index = 0;
        while (bigValue != 101){
            bigValue = findNum(inputSVob, inputBVob, bigValue);
            index ++;
            if (bigValue == -1){
            	break;
			}
		}
		if (bigValue == 101 && index == inputSVob.length - 1){
        	sta = true;
		}else{
			sta = false;
		}
		return sta;
    }
    function findNum(inputSVob, inputBVob, realValue) {
		for (var i = 0; i < inputSVob.length; i++){
            if(inputSVob.eq(i).val()==realValue){
            	return parseInt(inputBVob.eq(i).val()) + 1;
			}
		}
		return -1;
    }





	//	取出表格里每个input的值
	function getAhiList(){
        ahiList = [];
		var sta =false;
		var inputMCObjs = $("#templateTable tbody input[name='mc']");
		var inputSVobjs = $("#templateTable tbody input[name='smallValue']");
		var inputBVobjs = $("#templateTable tbody input[name='bigValue']");
		for(var i=0; i< inputMCObjs.length; i++){
			if(inputMCObjs.eq(i).val() == ""){
                sta = false;
                break;
			}
			ahiList.push({
				"mc": inputMCObjs.eq(i).val(), 
				"qsf": inputSVobjs.eq(i).val(),
				"jsf": inputBVobjs.eq(i).val(),		
			});
            sta = true;
		}
		return sta;
	}
	function isRepeat(){ 
		var arr = $("#templateTable tbody input[name='mc']");
		var hash = {}; 
		for(var i in arr) { 
			var s  = arr.eq(i).val();
			hash[s] = i;
		} 
		var num=0;
		for(var i in hash) { 
			num++;
		} 
		if(arr.length==(num-1)){
			return true; 
		}
		return false; 
	} 
	//提交按钮
	function saveOrUpdate(){
		// 取页面中fjmc的值
		var fjmc = $("#fenjiName").val();
		if(!fjmc){
			art.dialog.alert("请输入考核名称!");
			return;
		}
		// 取页面中fjms的值
		var fjms = $("#fenjiDescrible").val();
		if(!fjms){
			art.dialog.alert("请输入考核描述");
			return;
		}
        if(!checkNum()){
            art.dialog.alert("分值区间输入有误，请重新输入！");
            return;
        }
        if(!getAhiList()){
            art.dialog.alert("标准名称不可为空");
            return;
        }
        if(!isRepeat()){
            art.dialog.alert("标准名称不可相同");
            return;
        }
        art.dialog.confirm("确定要保存此模板？",function () {
            var aht ={
                "vid": vid,
                "fjmc": fjmc,
                "fjms": fjms,
                "ahiList": ahiList
            };
            $.ajax({
                url:basePath +'performance/aht_saveOrupdate.action',
                type:'post',
                data:{'ahtParam' : JSON.stringify(aht)},
                dataType:'json',
                success:function(data){
                    if(data!=0){
                        art.dialog.tips("操作成功!");
                        initTemplateList(true,data);
                    }else{
                        art.dialog.alert('添加失败');
                    }
                }
            });
        });
	}
	//	提交按钮
	$("#tiJiao").live("click",function(){
			saveOrUpdate()
	})

	//取消按钮
	$("#qvxiao").live("click",function(){
		 art.dialog.confirm("确定要取消操作？",function () {			 
			 initTemplateList();
		 });
	});
	
	/*删除模板*/
	$("#delete").live("click",function(){
		deleteTemplate();
	})
});

/*删除模板*/
function deleteTemplate(){
	 art.dialog.confirm("确定要删除此模板吗？",function () {	
		 $.ajax({
				url: basePath + "performance/aht_deleteByVid.action",
				type:"post",
				data:{"vid":vid},
				dataType: "text",
				success:function(data){
					if(data=="1"){
						art.dialog.tips("删除成功!");
						initTemplateList();
					}else{
						art.dialog.alert("删除失败,请稍候重试!");
					}
				}
			})
	 });
}

//刷新页面
function initTemplateList(sta,val){
	$.ajax({
		url: basePath + "performance/aht_findAll.action",
		type:"post",
		async:false,
		dataType: "json",
		success:function(data){
			if(data){
				fenJiList=data;
			}
			var fenJiStr='';
			$.each(data,function(i,fenji){
				if(fenji.lx==-1){
					fenJiStr='<li><a id="'+fenji.vid+'" class="active" style="cursor:pointer">'+fenji.fjmc+'</a></li>';
					$("#fenjiName").val(fenji.fjmc);
					$("#fenjiDescrible").val(fenji.fjms);
					vid=fenji.vid;
					lx=-1;
					defaultVid=fenji.vid;
									
					findByVid(defaultVid);
					return;
				}
									
				fenJiStr+='<li><a href="#" id="'+fenji.vid+'">'+fenji.fjmc+'</a></li>';
			}) 
			$("#fenji_list").html("");
			$("#fenji_list").append(fenJiStr);
			var fenjiListH=$(window.top).height()-90-156;
	        $(".fenji_list").css("height",fenjiListH);
			defaultTemplateInput(lx);
			if(sta){
                $("#"+val).click();
			}
		}
	})
}



/**
 * @param 异步加载详情页面
 */
function findByVid(vid){
	$.ajax({
		url: basePath + "performance/aht_findByVid.action?vid="+vid,
		type:"post",
		async:false,
		dataType: "json",
		success:function(data){
			var templatetds="";
			$.each(data.ahiList,function(i,standard){
				templatetds+='<tr><td><input type="checkbox" name="checked"></td>'
					+'<td><input type="text" class="creat_list_input text_center" name="mc" value="'+standard.mc+'"></td>'
					+'<td>'
					+'<div class="qvjian_box">'
					+'<div class="pr fl wd60"><input type="text" maxlength="5" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)"  class="creat_list_input text_center" name="smallValue" value="'+standard.qsf+'"></div>'
					+'<span class="fl">&nbsp; ~ &nbsp;</span>'
					+'<div class="pr fl wd60"><input type="text" maxlength="5" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" onblur="checkfz(this)" class="creat_list_input text_center" name="bigValue" value="'+standard.jsf+'"></div>'
					+'<span class="fl ml5 mt3">&nbsp;分</span>'
					+'</div>'
					+'</td>'
					+'</tr>';
			})
			$("#templateTable tbody").html("");
			$("#templateTable tbody").append(templatetds);
		}
	})
}

//默认模板下输入框禁用、修改，取消，删除按钮隐藏
function defaultTemplateInput(lx){
	if(lx==-1){
		$("#templateBox input").attr("disabled",true);
		$("#templateBox textarea").attr("disabled",true);
		$(".btn_container2").hide();
		return;
	}
	$("#templateBox input").attr("disabled",false);
	$("#templateBox textarea").attr("disabled",false);
	$(".btn_container2").show();
}

function checkfz(obj) {
	var smNum = $(obj).parent().prev().prev().children().val();
	var bigNum = $(obj).val();
	if(parseInt(smNum) > parseInt(bigNum)){
        $(obj).val("");
        art.dialog.alert("分值输入有误，请重新输入！");
	}
}
 