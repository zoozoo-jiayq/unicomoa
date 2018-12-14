$(function(){
	//流量使用排名
	countFlow();
	//申请数量前十
	countApply();
	//流程使用频率
	countFlowFrequency();
	//流程总量统计
	countFlowTotal();
	//手机端使用频度
	countLoginWap();
	//PC系统使用次数
	countLoginPC();
	//考勤打卡统计
	countAtt();
});

/**
 * 流量使用排名
 */
function countFlow(){
	var url = basePath+"count/processUseCount.action";
	var dataCount = "";
	var paramData = {
//			"top":10
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	
	var chart = iChart.create({
        render:"flowUseCounts",
        width:500,
        height:220,
        background_color:"#fefefe",
        gradient:false,
        color_factor:0.2,
        border:{
              color:"BCBCBC",
              width:0
        },
        align:"center",
        offsetx:0,
        offsety:0,
        sub_option:{
              border:{
                    color:"#BCBCBC",
                    width:1
              },
              label:{
                    fontweight:500,
                    fontsize:11,
                    color:"#4572a7",
                    sign:"square",
                    sign_size:12,
                    border:{
                          color:"#BCBCBC",
                          width:1
                    },
                    background_color:"#fefefe"
              }
        },
        animation : true,//开启过渡动画
		animation_duration:800,//800ms完成动画
        shadow:false,
        shadow_color:"#666666",
        shadow_blur:2,
        showpercent:false,
        column_width:"60%",
        bar_height:"70%",
        radius:"90%",
        title:{
              text:"",
              color:"#111111",
              fontsize:20,
              font:"微软雅黑",
              textAlign:"left",
              height:30,
              offsetx:0,
              offsety:0
        },
        subtitle:{
              text:"",
              color:"#111111",
              fontsize:16,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        footnote:{
              text:"",
              color:"#111111",
              fontsize:12,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        legend:{
              enable:false,
              background_color:"#fefefe",
              color:"#333333",
              fontsize:10,
              border:{
                    color:"#BCBCBC",
                    width:0
              },
              column:1,
              align:"right",
              valign:"middle",
              offsetx:10,
              offsety:0
        },
        coordinate:{
              width:"80%",
              height:"80%",
              background_color:"#ffffff",
              axis:{
                    color:"#a5acb8",
                    width:[1,"",1,""]
              },
              grid_color:"#d9d9d9",
              label:{
                    fontweight:500,
                    color:"#666666",
                    fontsize:11
              }
        },
        tip:{enable:true,
        	listeners:{
				 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText:function(tip,name,value,text,i){
					//将数字进行千位格式化
					return name;
				}
			}
        },
        label:{
              fontweight:500,
              color:"#666666",
              fontsize:0
        },
        type:"column2d",
        data:dataCount
  });
  chart.draw();
  var childDiv = $("#flowUseCounts").children("div");
  if(childDiv.length>0){
	  childDiv.attr("style","width: 500px; height: 220px; overflow: hidden; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 10px; margin-right: auto; margin-bottom: 0px; margin-left: auto; position: relative;")
  }
}
/**
 * 申请数量前十
 */
function countApply(){
	var url = basePath+"count/processApplyCount.action";
	var dataCount = "";
	var paramData = {
//			"top":10
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	
	new iChart.Bar2D({
		render : 'applyCounts',
		background_color : '#fefefe',
		data : dataCount,
		title : '',
		subtitle : '',
		footnote : '',
		width : 650,
		height : 250,
		coordinate : {
			width : 430,
			height : 200,
//			grid_color:'#4e5464',
			axis : {
//				color:"#BCBCBC",
				width : [0, 0, 1, 1]
			},
			scale : [{
				position : 'bottom',
				start_scale : 0,
				end_scale : 18,
				scale_space : 100
			}]
		},
		animation : true,
		sub_option : {
			listeners : {
				parseText : function(r, t) {
					return t;
				}
			}
		},
		legend : {
			enable : false
		}
	}).draw();
}
/**
 * 流程使用频率
 */
function countFlowFrequency(){
	var url = basePath+"count/getProcessFrequencyCount.action";
	var dataCount = "";
	var paramData = {
//			"top":10
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	new iChart.Pie2D({
		render : 'flowUseFrequency',
		data: dataCount,
		title : '',
		padding:'2 10',
		legend : {
			enable : false
		},
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:12,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			}
		},
		gradient:false,//开启渐变
//		color_factor:0.28,
		animation:true,
		showpercent:true,
		decimalsnum:2,
		width : 560,
		height : 250,
		radius:140
	}).draw();
	var childDiv = $("#flowUseFrequency").children("div");
	  if(childDiv.length>0){
		  childDiv.attr("style","width: 560px; height: 250px; overflow: hidden; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 10px; margin-right: auto; margin-bottom: 0px; margin-left: auto; position: relative;")
	  }
}
/**
 * 流程总量统计
 */
function countFlowTotal(){
	var url = basePath+"count/getProcessApplyCount.action";
	var dataCount = "";
	var paramData = {
//			"top":10
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	
	var chart = iChart.create({
        render:"flowTotalCounts",
        width:500,
        height:220,
        background_color:"#fefefe",
        gradient:false,
        color_factor:0.2,
        border:{
              color:"BCBCBC",
              width:0
        },
        align:"center",
        offsetx:0,
        offsety:0,
        sub_option:{
              border:{
                    color:"#BCBCBC",
                    width:1
              },
              label:{
                    fontweight:500,
                    fontsize:11,
                    color:"#4572a7",
                    sign:"square",
                    sign_size:12,
                    border:{
                          color:"#BCBCBC",
                          width:1
                    },
                    background_color:"#fefefe"
              }
        },
        animation : true,//开启过渡动画
		animation_duration:800,//800ms完成动画
        shadow:false,
        shadow_color:"#666666",
        shadow_blur:2,
        showpercent:false,
        column_width:"60%",
        bar_height:"70%",
        radius:"90%",
        title:{
              text:"",
              color:"#111111",
              fontsize:20,
              font:"微软雅黑",
              textAlign:"left",
              height:30,
              offsetx:0,
              offsety:0
        },
        subtitle:{
              text:"",
              color:"#111111",
              fontsize:16,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        footnote:{
              text:"",
              color:"#111111",
              fontsize:12,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        legend:{
              enable:true,
              background_color:"#fefefe",
              color:"#333333",
              fontsize:10,
              border:{
                    color:"#BCBCBC",
                    width:0
              },
              column:1,
              align:"right",
              valign:"middle",
              offsetx:10,
              offsety:0
        },
        coordinate:{
              width:"80%",
              height:"80%",
              background_color:"#ffffff",
              axis:{
                    color:"#a5acb8",
                    width:[1,"",1,""]
              },
              grid_color:"#d9d9d9",
              label:{
                    fontweight:500,
                    color:"#666666",
                    fontsize:11
              }
        },
        label:{
              fontweight:500,
              color:"#666666",
              fontsize:11
        },
        type:"column2d",
        data:dataCount
  });
  chart.draw();
  var childDiv = $("#flowTotalCounts").children("div");
  if(childDiv.length>0){
	  childDiv.attr("style","width: 500px; height: 220px; overflow: hidden; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 10px; margin-right: auto; margin-bottom: 0px; margin-left: auto; position: relative;")
  }
}
/**
 * 手机端使用频度
 */
function countLoginWap(){
	var url = basePath+"count/countLogin.action";
	var dataCount = "";
	var paramData = {
			"logType":26
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	
	var chart = iChart.create({
        render:"wapUseCounts",
        width:500,
        height:220,
        background_color:"#fefefe",
        gradient:false,
        color_factor:0.2,
        border:{
              color:"BCBCBC",
              width:0
        },
        align:"center",
        offsetx:0,
        offsety:0,
        sub_option:{
              border:{
                    color:"#BCBCBC",
                    width:1
              },
              label:{
                    fontweight:500,
                    fontsize:11,
                    color:"#4572a7",
                    sign:"square",
                    sign_size:12,
                    border:{
                          color:"#BCBCBC",
                          width:1
                    },
                    background_color:"#fefefe"
              }
        },
        animation : true,//开启过渡动画
		animation_duration:800,//800ms完成动画
        shadow:false,
        shadow_color:"#666666",
        shadow_blur:2,
        showpercent:false,
        column_width:"60%",
        bar_height:"70%",
        radius:"90%",
        title:{
              text:"",
              color:"#111111",
              fontsize:20,
              font:"微软雅黑",
              textAlign:"left",
              height:30,
              offsetx:0,
              offsety:0
        },
        subtitle:{
              text:"",
              color:"#111111",
              fontsize:16,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        footnote:{
              text:"",
              color:"#111111",
              fontsize:12,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        legend:{
              enable:true,
              background_color:"#fefefe",
              color:"#333333",
              fontsize:10,
              border:{
                    color:"#BCBCBC",
                    width:0
              },
              column:1,
              align:"right",
              valign:"middle",
              offsetx:10,
              offsety:0
        },
        coordinate:{
              width:"80%",
              height:"80%",
              background_color:"#ffffff",
              axis:{
                    color:"#a5acb8",
                    width:[1,"",1,""]
              },
              grid_color:"#d9d9d9",
              label:{
                    fontweight:500,
                    color:"#666666",
                    fontsize:11
              }
        },
        label:{
              fontweight:500,
              color:"#666666",
              fontsize:11
        },
        type:"column2d",
        data:dataCount
  });
  chart.draw();
  var childDiv = $("#wapUseCounts").children("div");
  if(childDiv.length>0){
	  childDiv.attr("style","width: 500px; height: 220px; overflow: hidden; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 10px; margin-right: auto; margin-bottom: 0px; margin-left: auto; position: relative;")
  }
}
/**
 * PC系统使用次数
 */
function countLoginPC(){
	var url = basePath+"count/countLogin.action";
	var dataCount = "";
	var paramData = {
			"logType":1
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	
	var chart = iChart.create({
        render:"pcUseCounts",
        width:500,
        height:220,
        background_color:"#fefefe",
        gradient:false,
        color_factor:0.2,
        border:{
              color:"BCBCBC",
              width:0
        },
        align:"center",
        offsetx:0,
        offsety:0,
        sub_option:{
              border:{
                    color:"#BCBCBC",
                    width:1
              },
              label:{
                    fontweight:500,
                    fontsize:11,
                    color:"#4572a7",
                    sign:"square",
                    sign_size:12,
                    border:{
                          color:"#BCBCBC",
                          width:1
                    },
                    background_color:"#fefefe"
              }
        },
        animation : true,//开启过渡动画
		animation_duration:800,//800ms完成动画
        shadow:false,
        shadow_color:"#666666",
        shadow_blur:2,
        showpercent:false,
        column_width:"60%",
        bar_height:"70%",
        radius:"90%",
        title:{
              text:"",
              color:"#111111",
              fontsize:20,
              font:"微软雅黑",
              textAlign:"left",
              height:30,
              offsetx:0,
              offsety:0
        },
        subtitle:{
              text:"",
              color:"#111111",
              fontsize:16,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        footnote:{
              text:"",
              color:"#111111",
              fontsize:12,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        legend:{
              enable:true,
              background_color:"#fefefe",
              color:"#333333",
              fontsize:10,
              border:{
                    color:"#BCBCBC",
                    width:0
              },
              column:1,
              align:"right",
              valign:"middle",
              offsetx:10,
              offsety:0
        },
        coordinate:{
              width:"80%",
              height:"80%",
              background_color:"#ffffff",
              axis:{
                    color:"#a5acb8",
                    width:[1,"",1,""]
              },
              grid_color:"#d9d9d9",
              label:{
                    fontweight:500,
                    color:"#666666",
                    fontsize:11
              }
        },
        label:{
              fontweight:500,
              color:"#666666",
              fontsize:11
        },
        type:"column2d",
        data:dataCount
  });
  chart.draw();
  var childDiv = $("#pcUseCounts").children("div");
  if(childDiv.length>0){
	  childDiv.attr("style","width: 500px; height: 220px; overflow: hidden; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 10px; margin-right: auto; margin-bottom: 0px; margin-left: auto; position: relative;")
  }
}

/**
 * 考勤打卡统计
 */
function countAtt(){
	var url = basePath+"count/countAttendacne.action";
	var dataCount = "";
	var paramData = {
			"attSource":1
		};
	$.ajax({
		url:url,
		type:"POST",
		data:paramData,
		async:false,
		dataType:'json',
		success:function(data){
			if(data != null && data != ""){
				dataCount = data;
			}
		}
	});
	
	var chart = iChart.create({
        render:"attCounts",
        width:500,
        height:220,
        background_color:"#fefefe",
        gradient:false,
        color_factor:0.2,
        border:{
              color:"BCBCBC",
              width:0
        },
        align:"center",
        offsetx:0,
        offsety:0,
        sub_option:{
              border:{
                    color:"#BCBCBC",
                    width:1
              },
              label:{
                    fontweight:500,
                    fontsize:11,
                    color:"#4572a7",
                    sign:"square",
                    sign_size:12,
                    border:{
                          color:"#BCBCBC",
                          width:1
                    },
                    background_color:"#fefefe"
              }
        },
        animation : true,//开启过渡动画
		animation_duration:800,//800ms完成动画
        shadow:false,
        shadow_color:"#666666",
        shadow_blur:2,
        showpercent:false,
        column_width:"60%",
        bar_height:"70%",
        radius:"90%",
        title:{
              text:"",
              color:"#111111",
              fontsize:20,
              font:"微软雅黑",
              textAlign:"left",
              height:30,
              offsetx:0,
              offsety:0
        },
        subtitle:{
              text:"",
              color:"#111111",
              fontsize:16,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        footnote:{
              text:"",
              color:"#111111",
              fontsize:12,
              font:"微软雅黑",
              textAlign:"center",
              height:20,
              offsetx:0,
              offsety:0
        },
        legend:{
              enable:true,
              background_color:"#fefefe",
              color:"#333333",
              fontsize:10,
              border:{
                    color:"#BCBCBC",
                    width:0
              },
              column:1,
              align:"right",
              valign:"middle",
              offsetx:10,
              offsety:0
        },
        coordinate:{
              width:"80%",
              height:"80%",
              background_color:"#ffffff",
              axis:{
                    color:"#a5acb8",
                    width:[1,"",1,""]
              },
              grid_color:"#d9d9d9",
              label:{
                    fontweight:500,
                    color:"#666666",
                    fontsize:11
              }
        },
        label:{
              fontweight:500,
              color:"#666666",
              fontsize:11
        },
        type:"column2d",
        data:dataCount
  });
  chart.draw();
  var childDiv = $("#attCounts").children("div");
  if(childDiv.length>0){
	  childDiv.attr("style","width: 500px; height: 220px; overflow: hidden; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 10px; margin-right: auto; margin-bottom: 0px; margin-left: auto; position: relative;")
  }
}