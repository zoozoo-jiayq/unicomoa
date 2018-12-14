mui.plusReady(function(){
	var self=plus.webview.currentWebview();
	var cnName=self.cnName;
	var name=self.name;
	var radios=self.radios;
	var defaltValue=self.defaltValue;
    var isDetail=self.isDetail;
    if(isDetail==1){
    	cnName=cnName.substring(0,cnName.lastIndexOf("_"));
    }
	$("#title").html(cnName);
	var radiosAry="";
	if(radios){
		radiosAry=radios.split(",");
	}
	var type=self.type;
	var html="";
	if(type=="checkbox"){
		$("nav").show();
		for(var j=0;j<radiosAry.length;j++){
			var flag=true;
			if(defaltValue){
				var valueAry=defaltValue.split(",");
				for(var i=0;i<valueAry.length;i++){
						if(radiosAry[j]==valueAry[i]){
							html+='<li class="mui-table-view-cell on" onclick="selectChk(this);" ><span>'+radiosAry[j]+'</span><i class="checkoff mui-pull-right"></i></li>';
							flag=false;
							break;
						}		
				}
			}
			if(flag){
				html+='<li class="mui-table-view-cell" onclick="selectChk(this);" ><span>'+radiosAry[j]+'</span><i class="checkoff mui-pull-right"></i></li>';
			}
			
		}
	}else{
		for(var j=0;j<radiosAry.length;j++){
			html+='<li class="mui-table-view-cell" onclick="selectMenu(this)" name='+name+'>'+radiosAry[j]+'</li>';
		}
	}
	$("ul").append(html);
	
	$("#cancle").click(function(){
		mui.back();
	});
	$("#sureChk").click(function(){
		sureChk(name);
	});
	
});

//复选框选择
function selectChk(obj){
	$(obj).toggleClass('on');
}
function selectMenu(obj){
	var name=$(obj).attr("name");
	var cnName=$(obj).html();
	plus.webview.getWebviewById("apply").evalJS("selectMenu('"+name+"','"+cnName+"')");
	mui.back();
}

//选择复选框确定
function sureChk(name){
	var chkCont="";
	$("ul li.on").each(function(){
		chkCont+=$(this).children("span").html()+",";
	});
	if(chkCont.length>0){
		chkCont=chkCont.substring(0,chkCont.length-1);
	}
	plus.webview.getWebviewById("apply").evalJS("sureChk('"+name+"','"+chkCont+"')");
	mui.back();
}