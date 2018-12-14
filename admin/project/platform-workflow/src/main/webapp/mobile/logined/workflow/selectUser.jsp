<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="block-item bt10 addExaPer">
     <span class="gray correct">审批人<span>(长按头像可删除)</span></span>
     <div class="swiper-container-header ">
         <div class="head" id="addUser">
              <div class="round"><img src="${ctx}mobile/images/1.png"/></div>
              <p class="center">请选择</p>
         </div>
     </div>
 </div>
<script type="text/javascript" src="${ctx }mobile/js/logined/workflow/letterCode.js"></script>
<script type="text/javascript">
	//当前操作用户id
	var userId = h5Adapter.getItemValue("currentUserId");
	//选择的用户集合
	var userIds = "";
	var userArray = new Array();
	$("#addUser").click(function(){
		h5Adapter.selectOneUser(function(data){
			if(data.isSuccess){
				var tu = data.userList[0].userId;
				var userName = data.userList[0].userName;
				var photourl = data.userList[0].userPhoto;
				processSelectedUser(tu, userName, photourl);
			}
		});
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
		if(photourl==""){
			html+="<div class='round head-bg-"+col+"'><img src='"+photourl+"' style='display:none'>"+lastName+"</div>";
		}else{
			html+="<div class='round head-bg-"+col+"'><img src='"+photourl+"'>"+lastName+"</div>";
		}
        html+="<p class='center'>"+userName+"</p></div>";
        html+="<div class='head arrowBtn'></div>";
		$("#addUser").before(html);
		delUser($("#")+tu,tu);
		controlSlide();
	}
	
	//存在返回true，不存在返回false
	function checkExist(tu){
		var flag = false;
		for(var i =0; i<userArray.length; i++){
			if(userArray[i].userId == tu){
				h5Adapter.tips("该审批人已在列表中");
				flag = true;
			}
		}
		if(tu == userId){
			h5Adapter.tips("不能选择自己");
			flag = true;
		}
		return flag;
	}
	
	function controlSlide(){
		
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
	
	new Swiper('.swiper-container',{
		scrollContainer: true
	});
</script>