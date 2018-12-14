<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="block-item bt10">
     <a href="#"><div class="fold"></div></a>
     <span class="gray correct">审批人<span>(长按头像可删除)</span></span>
     <div class="swiper-container ">
         <div class="swiper-wrapper" >
             <div class="swiper-slide " style="width:800px"  >
                 <div class="head" id="addUser">
                     <img class="round" src="${ctx }mobile/images/1.png">
                     <p class="center">请选择</p>
                 </div>

             </div>
         </div>
     </div>
 </div>
<script type="text/javascript">
	//当前操作用户id
	var userId = "${adminUser.userId}";
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
		col = Math.floor(Math.random()*7+1); //1-10
		var firstName = userName.substring(0,1);
		//$("#addUser").before("<div id='"+userId+"' class='head headImg'><img class='round' src='"+photourl+"'><p class='center'>"+userName+"</p></div><div class='head'><img class='roo' src='${ctx }mobile/images/right_arrow.png'/></div>");
		if(photourl.indexOf("data")==0){
			photourl = "";
		}
		$("#addUser").before("<div id='"+tu+"' class='head headImg'><div class='round ico-"+col+"'>"+firstName+"<img src='"+photourl+"'></div><p class='center'>"+userName+"</p></div><div class='head arrowBtn'></div>");
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
                 });
                     
         })(k);     
       }
 
        
        
	}
	
	new Swiper('.swiper-container',{
		scrollContainer: true
	});
</script>