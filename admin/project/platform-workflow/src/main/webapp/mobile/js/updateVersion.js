mui.plusReady(function(){
	plus.runtime.getProperty(plus.runtime.appid,function(inf){
		var vgtVer=inf.version;
		getNewVersion(vgtVer);
	});
});

//获取最新版本信息
function getNewVersion(vgtVer){
	$.ajax({
		type: "POST",
        url: plus.storage.getItem("baseUrl")+"ydzjMobile/getH5Version.action?r="+Math.random(),
        data: {
        	"_clientType":"wap"
        },
        success: function(msg){
        	var result="";
            if(msg.indexOf("100||")==0){
            	var result = msg.substring(5);
                if(result){
                	result=JSON.parse(result);
                	if(result.flowVersion&&vgtVer!=result.flowVersion){
                		downWgt(result.flowUrl);
                	}
                }
            }
            return result;
        }
		
	});
}

//下载更新文件
function downWgt(wgtUrl){
    plus.nativeUI.showWaiting("有新的应用资源需要更新，请稍等...");
    plus.downloader.createDownload( wgtUrl, {filename:"_doc/update/"}, function(d,status){
        if ( status == 200 ) { 
            //console.log("下载wgt成功："+d.filename);
            installWgt(d.filename); // 安装wgt包
        } else {
           // console.log("下载wgt失败！");
           plus.nativeUI.closeWaiting();
            plus.nativeUI.alert("应用资源更新失败！");
        }
        
    }).start();
}

// 更新应用资源
function installWgt(path){
//  plus.nativeUI.showWaiting("安装wgt文件...");
    plus.runtime.install(path,{force:true},function(){
        plus.nativeUI.closeWaiting();
//      console.log("安装wgt文件成功！");
        plus.nativeUI.alert("应用资源更新完成！",function(){
            plus.runtime.restart();
        });
    },function(e){
        plus.nativeUI.closeWaiting();
//      console.log("安装wgt文件失败["+e.code+"]："+e.message);
        plus.nativeUI.alert("应用资源更新失败！");
    });
}
