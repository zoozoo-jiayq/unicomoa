//javascript for wap email list.jsp

var iDisplayStartOfAffairslList = 0;


$(document).ready(function () {
	  $("#loadMoreNoticeList").click(function () {
      getAllNotifyWapList();
    });
    getAllNotifyWapList();
 //   initLoadMore();
  //   initLoadMore("loadMoreNoticeList", getAllNotifyWapList);
   
});

/*function initLoadMore() {
	alert();
    $("#loadMoreNoticeList").click(function () {
      getAllNotifyWapList();
    });
   
}*/



function getAllNotifyWapList() {
  $("#loadMoreNoticeList a").text("正在加载...");
    $.ajax({
        url: basePath + "notify/getAllNotifyWapList.action"+"?random="+Math.random(),
        type: "post",
        dataType: 'json',
        data: {
            iDisplayStart: iDisplayStartOfAffairslList
        },
        success: function (data) {
        	  if (data == undefined) {
                $("#loadMoreNoticeList a").text("点击加载更多...");
                return;
            }
            var aaData = data["aaData"];
            if (aaData != undefined && aaData.length > 0) {
                var html = "";
                for (var i = 0; i < aaData.length; i++) {
                    var dataMap = aaData[i];
                 
                    var isTop=dataMap["isTop"];
                    if(isTop==1)
                    {
                    html += "<li class='setTop'  onClick='redectDetain("+dataMap["notifyId"]+")'>";
                  
                    html += "<h2><a href='#' onClick='redectDetain("+dataMap["notifyId"]+")'>"+ dataMap["subject"] + "</a><img src='../../images/setTop.png'/></h2><p>"+dataMap["createUserName"]+"<"+dataMap["userByGroup"]+"><time>"+dataMap["publishDate"]+"</time></p>";
                  
                    html += "</li>";
                    
                    }else{
                   var  isCheck=dataMap["isSignCheck"];
                   if(isCheck==1)
                   {
                  
                   
                   	   html += "<li  onClick='redectDetain("+dataMap["notifyId"]+")'> ";
                  
                   html += "<h2><a href='#' onClick='redectDetain("+dataMap["notifyId"]+")'>"+ dataMap["subject"] + "</a></h2><p>"+dataMap["createUserName"]+"<"+dataMap["userByGroup"]+"><time>"+dataMap["publishDate"]+"</time></p>";
                  
                    html += "</li>";
                 
                   }else
                   {
              
                   	  html += "<li  onClick='redectDetain("+dataMap["notifyId"]+")'>";
                  
                   html += "<h2><a  href='#' onClick='redectDetain("+dataMap["notifyId"]+")'>"+ dataMap["subject"] + "</a><img src='../../images/icon_new.png'/></h2><p>"+dataMap["createUserName"]+"<"+dataMap["userByGroup"]+"><time>"+dataMap["publishDate"]+"</time></p>";
                  
                    html += "</li>";
                   }
                 
                    }
                }
                $("#noticeList").append(html);
                 if (iDisplayStartOfAffairslList > 0) {
                window.scrollTo(0, document.body.scrollHeight);
            }
            iDisplayStartOfAffairslList = parseInt(data["iDisplayStart"]);
           
           if (data["loadAll"]) {
                $("#loadMoreNoticeList a").text("已完成加载");
              
            } else {
                $("#loadMoreNoticeList a").text("点击加载更多...");
            }
               
            }
          
        }
    });
}
function  redectDetain(subject)
{
	
	window.location.href=basePath+'wap/logined/notify/detain.jsp?subject='+subject;
}

