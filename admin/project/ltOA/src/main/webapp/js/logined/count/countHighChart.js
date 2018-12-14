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
	var dataCount = [];
	var categories = [];
	function ProcessUse(){
			this.y="";
			this.color="";
	};
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
				for(var i = 0;i < data.length;i++){
					var tempProcessUse = data[i];
					categories.push(tempProcessUse.name);
					var processUse = new ProcessUse();
					processUse.y = tempProcessUse.value;
					processUse.color=tempProcessUse.color;
					dataCount.push(processUse);
				}
			}
		}
	});
	var colors = Highcharts.getOptions().colors,name=' ';
    data = dataCount;
function setChart(name, categories, data, color) {
	chart.xAxis[0].setCategories(categories, false);
	chart.series[0].remove(false);
	chart.addSeries({
		name: name,
		data: data,
		color: color || 'white'
	}, false);
	chart.redraw();
}
var chart = $('#flowUseCounts').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {labels: { 

        enabled: false// Highcharts学习交流群294191384 

    },
        categories: categories
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        column: {
            cursor: 'pointer',
            point: {
            },
            dataLabels: {
                enabled: true,
                color: colors[0],
                style: {
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.y;
                }
            }
        }
    },
    tooltip: {
        formatter: function() {
           // var point = this.point,
            s = this.x +':<b>'+ this.y;
            return s;
        }
    },
    series: [{
        name: name,
        data: data,
        color: 'white'
    }],
    exporting: {
        enabled: false
    }
})
.highcharts(); 
}



/**
 * 流程使用频率
 */
function countFlowFrequency(){
	var url = basePath+"count/getProcessFrequencyCount.action";
	var dataCount = [];
	var colors=[];
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
				for(var i = 0;i < data.length;i++){
					var tempFlowFrequency = data[i];
					colors.push(tempFlowFrequency.color);
					if(i == 0){
						var tempObject = {name:"",y:"",sliced:true,selected:true};
						tempObject.name=tempFlowFrequency.name;
						tempObject.y=tempFlowFrequency.value;
						tempObject.sliced=true;
						tempObject.selected=true;
						dataCount.push(tempObject);
					}else{
						var tempArray = [];
						tempArray.push(tempFlowFrequency.name);
						tempArray.push(tempFlowFrequency.value);
						dataCount.push(tempArray);
					}
					
				}
			}
		}
	});
	Highcharts.getOptions().colors = Highcharts.map(colors, function(color) {
	    return {
	        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
	        stops: [
	            [0, color],
	            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
	        ]
	    };
	});
	
	    $('#flowUseFrequency').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: ''
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}<b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: "#7CB5EC",
	                    connectorColor: '#000000',
	                    formatter: function() {
	                    	var pointName = this.point.name;
	                    	if(pointName != "" && pointName.length > 8){
	                    		pointName = pointName.substring(0,8)+"...";
	                    	}
	                    	 return '<b>'+ pointName +'</b>: '+ fmoney(this.percentage,1) +' %';
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: ' ',
	            data: dataCount
	        }]
	    });
}


/**
 * 考勤打卡统计
 */
function countAtt(){
	var dataCount = [];
	var categories = [];
	function AttendanceCount(){
			this.y="";
			this.color="";
	};
	
	var url = basePath+"count/countAttendacne.action";
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
				for(var i = 0;i < data.length;i++){
					var tempAttendanceCount = data[i];
					categories.push(tempAttendanceCount.name);
					var attendanceCount = new AttendanceCount();
					attendanceCount.y = tempAttendanceCount.value;
					attendanceCount.color=tempAttendanceCount.color;
					dataCount.push(attendanceCount);
				}
			}
		}
	});
	var colors = Highcharts.getOptions().colors,name=' ';
    data = dataCount;
function setChart(name, categories, data, color) {
	chart.xAxis[0].setCategories(categories, false);
	chart.series[0].remove(false);
	chart.addSeries({
		name: name,
		data: data,
		color: color || 'white'
	}, false);
	chart.redraw();
}
var chart = $('#attCounts').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {labels: { 

        enabled: true// Highcharts学习交流群294191384 

    },
        categories: categories
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        column: {
            cursor: 'pointer',
            point: {
            },
            dataLabels: {
                enabled: true,
                color: "#7CB5EC",
                style: {
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.y;
                }
            }
        }
    },
    tooltip: {
        formatter: function() {
           // var point = this.point,
            s = this.x +':<b>'+ this.y;
            return s;
        }
    },
    series: [{
        name: name,
        data: data,
        color: 'white'
    }],
    exporting: {
        enabled: false
    }
})
.highcharts(); 
}


function fmoney(s, n)  
{  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
     t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
  return t.split("").reverse().join("") + "." + r;  
}  

/**
 * 申请数量前十
 */
function countApply(){
	var url = basePath+"count/processApplyCount.action";
	var dataCount = [];
	var categories = [];
	function ProcessUse(){
			this.y="";
			this.color="";
	};
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
				for(var i = 0;i < data.length;i++){
					var tempProcessUse = data[i];
					categories.push(tempProcessUse.name);
					var processUse = new ProcessUse();
					processUse.y = tempProcessUse.value;
					processUse.color=tempProcessUse.color;
					dataCount.push(processUse);
				}
			}
		}
	});
	var colors = Highcharts.getOptions().colors,name=' ';
    data = dataCount;
function setChart(name, categories, data, color) {
	chart.xAxis[0].setCategories(categories, false);
	chart.series[0].remove(false);
	chart.addSeries({
		name: name,
		data: data,
		color: color || 'white'
	}, false);
	chart.redraw();
}
	var chart = $('#applyCounts').highcharts({                                           
        chart: {                                                           
            type: 'bar'                                                    
        },                                                                 
        title: {                                                           
        	text: ''                 
        },                                                                 
        subtitle: {                                                        
        	text: ''                            
        },                                                                 
        xAxis: {                                                           
            categories: categories,
            title: {                                                       
                text: null                                                 
            }                                                              
        },                                                                 
        yAxis: {                                                           
            min: 0,                                                        
            title: {                                                       
                text: ' ',                             
                align: 'high'                                              
            },                                                             
            labels: {                                                      
                overflow: 'justify'                                        
            }                                                              
        },                                                                 
        tooltip: {                                                         
        	formatter: function() {
                // var point = this.point,
                 s = this.x +':<b>'+ this.y;
                 return s;
             }                                       
        },                                                                 
        plotOptions: {                                                     
            bar: {                                                         
                dataLabels: {                                              
                    enabled: true                                          
                }                                                          
            }                                                              
        },                                                                 
                                                                       
        credits: {                                                         
            enabled: false                                                 
        },                                                                 
        series: [{
            name: name,
            data: data,
            color: 'white'
        }]                                                                 
    });                         
}
/**
 * 流量统计总量
 */
function countFlowTotal(){
	var url = basePath+"count/getProcessApplyCount.action";
	var dataCount = [];
	var categories = [];
	function ProcessUse(){
			this.y="";
			this.color="";
	};
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
				for(var i = 0;i < data.length;i++){
					var tempProcessUse = data[i];
					categories.push(tempProcessUse.name);
					var processUse = new ProcessUse();
					processUse.y = tempProcessUse.value;
					processUse.color=tempProcessUse.color;
					dataCount.push(processUse);
				}
			}
		}
	});
	var colors = Highcharts.getOptions().colors,name=' ';
    data = dataCount;
function setChart(name, categories, data, color) {
	chart.xAxis[0].setCategories(categories, false);
	chart.series[0].remove(false);
	chart.addSeries({
		name: name,
		data: data,
		color: color || 'white'
	}, false);
	chart.redraw();
}
var chart = $('#flowTotalCounts').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {labels: { 

        enabled: true// Highcharts学习交流群294191384 

    },
        categories: categories
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        column: {
            cursor: 'pointer',
            point: {
            },
            dataLabels: {
                enabled: true,
                color: "#7CB5EC",
                style: {
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.y;
                }
            }
        }
    },
    tooltip: {
        formatter: function() {
           // var point = this.point,
            s = this.x +':<b>'+ this.y;
            return s;
        }
    },
    series: [{
        name: name,
        data: data,
        color: 'white'
    }],
    exporting: {
        enabled: false
    }
})
.highcharts();
}
/**
 * 手机端使用次数
 */
function countLoginWap(){
	var url = basePath+"count/countLogin.action";
	var dataCount = [];
	var categories = [];
	function ProcessUse(){
			this.y="";
			this.color="";
	};
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
				for(var i = 0;i < data.length;i++){
					var tempProcessUse = data[i];
					categories.push(tempProcessUse.name);
					var processUse = new ProcessUse();
					processUse.y = tempProcessUse.value;
					processUse.color=tempProcessUse.color;
					dataCount.push(processUse);
				}
			}
		}
	});
	var colors = Highcharts.getOptions().colors,name=' ';
    data = dataCount;
function setChart(name, categories, data, color) {
	chart.xAxis[0].setCategories(categories, false);
	chart.series[0].remove(false);
	chart.addSeries({
		name: name,
		data: data,
		color: color || 'white'
	}, false);
	chart.redraw();
}
var chart = $('#wapUseCounts').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {labels: { 

        enabled: true// Highcharts学习交流群294191384 

    },
        categories: categories
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        column: {
            cursor: 'pointer',
            point: {
            },
            dataLabels: {
                enabled: true,
                color: "#7CB5EC",
                style: {
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.y;
                }
            }
        }
    },
    tooltip: {
        formatter: function() {
           // var point = this.point,
            s = this.x +':<b>'+ this.y;
            return s;
        }
    },
    series: [{
        name: name,
        data: data,
        color: 'white'
    }],
    exporting: {
        enabled: false
    }
})
.highcharts();
}
/**
 * PC端使用次数
 */
function countLoginPC(){
	var url = basePath+"count/countLogin.action";
	var dataCount = [];
	var categories = [];
	function ProcessUse(){
			this.y="";
			this.color="";
	};
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
				for(var i = 0;i < data.length;i++){
					var tempProcessUse = data[i];
					categories.push(tempProcessUse.name);
					var processUse = new ProcessUse();
					processUse.y = tempProcessUse.value;
					processUse.color=tempProcessUse.color;
					dataCount.push(processUse);
				}
			}
		}
	});
	var colors = Highcharts.getOptions().colors,name=' ';
    data = dataCount;
function setChart(name, categories, data, color) {
	chart.xAxis[0].setCategories(categories, false);
	chart.series[0].remove(false);
	chart.addSeries({
		name: name,
		data: data,
		color: color || 'white'
	}, false);
	chart.redraw();
}
var chart = $('#pcUseCounts').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {labels: { 

        enabled: true// Highcharts学习交流群294191384 

    },
        categories: categories
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        column: {
            cursor: 'pointer',
            point: {
            },
            dataLabels: {
                enabled: true,
                color: "#7CB5EC",
                style: {
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.y;
                }
            }
        }
    },
    tooltip: {
        formatter: function() {
           // var point = this.point,
            s = this.x +':<b>'+ this.y;
            return s;
        }
    },
    series: [{
        name: name,
        data: data,
        color: 'white'
    }],
    exporting: {
        enabled: false
    }
})
.highcharts();
}
