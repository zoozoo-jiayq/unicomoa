//选择的用户集合
var userIds = "";
mui.plusReady(function(){
	//当前操作用户id
	var userId = window.windowCommon.approveLoginId;
	var userArray = new Array();
	$("#addUser").click(function(){
		//单选人员
		plus.qytxplugin.selectUsers("",1,function(data){
			if(data){
				var tu = data[0].userId;
				var userName = data[0].userName;
				var photourl = data[0].userPhoto;
				processSelectedUser(tu, userName, photourl);
			}
		});
//		processSelectedUser("10987506", "任鹏辉", "");
	});
	
	function processSelectedUser(tu,userName,photourl){
		if(checkExist(tu)){
			return;
		}
		userArray.push({
			userId:tu,
			userName:userName,
			photoUrl:photourl
		});
		userIds = JSON.stringify(userArray);
		var lastName=userName;
		if(lastName.length>2){
			lastName=lastName.substring(lastName.length-2,lastName.length);
		}
		var col=letterCode(lastName);
		if(photourl.indexOf("data")==0){
			photourl = "";
		}
		var html="<div id='"+tu+"' class='head headImg'>";
		if(photourl==null||photourl==undefined|| photourl==""){
			html+="<div class='round head-bg-"+col+"'><img src='"+photourl+"' style='display:none'>"+lastName+"</div>";
		}else{
			html+="<div class='round head-bg-"+col+"'><img src='"+photourl+"'></div>";
		}
        html+="<p class='center'>"+userName+"</p></div>";
        html+="<div class='head arrowBtn'></div>";
		$("#addUser").before(html);
		delUser($("#")+tu,tu);
	}
	
	//存在返回true，不存在返回false
	function checkExist(tu){
		var flag = false;
		for(var i =0; i<userArray.length; i++){
			if(userArray[i].userId == tu){
				mui.toast("该审批人已在列表中");
				flag = true;
			}
		}
		if(tu == userId){
			mui.toast("不能选择自己");
			flag = true;
		}
		return flag;
	}
	
	Array.prototype.del = function(n){
		if (n<0) {
			return this;	
		}
		return this.slice(0,n).concat(this.slice(n+1,this.length));
	};
	
	//删除人员
	function delUser(){
        var headList = $(".headImg");
    	for(var k=0; k < headList.length; k++){
                 (function(k){
                        var hammerLi = new Hammer(headList[k]);
                        hammerLi.on("press", function(e){
    						headList.eq(k).hide();
    						if(headList.eq(k).next(".arrowBtn")){
    						   headList.eq(k).next(".arrowBtn").hide();
    						}
    						var selectId = $(headList.eq(k)).attr("id");
    						var selectIndex = -1 ;
    			            for(var i=0; i<userArray.length; i++){
    			         	   if(userArray[i].userId == selectId){
    			         		   selectIndex = i;
    			         		   break;
    			         	   }
    			            }
    			            userArray = userArray.del(selectIndex);
    			            userIds = JSON.stringify(userArray);
    			            if(userArray.length==0){
    			      		   userIds="";
    			      	   	}
                 });
                     
         })(k);     
       }
	}
});