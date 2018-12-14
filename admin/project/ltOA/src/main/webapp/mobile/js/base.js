// JavaScript Document
$(function(){
	 $(".editing .top_edit p a").on("touchstart",function() {
        $(this).addClass("active");
     });
     $(".editing .top_edit p a").on("touchend",function() {
         $(this).removeClass("active");
     });
     $(".listBox a").on("touchstart",function() {
         $(this).addClass("active");
     });
     $(".listBox a").on("touchend",function() {
         $(this).removeClass("active");
     });
	 $(".que_list a").on("touchstart",function() {
         $(this).addClass("active");
     });
     $(".que_list a").on("touchend",function() {
         $(this).removeClass("active");
     });
	 $(".buttonArea .btn").on("touchstart",function() {
         $(this).addClass("active");
     });
     $(".buttonArea .btn").on("touchend",function() {
         $(this).removeClass("active");
     });
 	 $(".block-item").on("touchstart",function() {
         $(this).addClass("active");
      });
      $(".block-item").on("touchend",function() {
          $(this).removeClass("active");
      }); 
      $(".homePage td a").on("touchstart",function() {
          $(this).addClass("active");
       });
       $(".homePage td a").on("touchend",function() {
           $(this).removeClass("active");
       });
})
//tab
function showTab(o, tab, count) {
    o.className = "active";
    o.className = "current";
    var j;
    var id;
    var e;
    for (var i = 1; i <= count; i++) {
        id = tab + i;
        j = document.getElementById(id);
        e = document.getElementById(tab + "Cont" + i);
        if(j==null||j==undefined||e==null||e==undefined){
        	continue;
        }
        if (id != o.id) {
        	if(j){
        		j.className = "";
        	}
        	if(e){
        		e.style.display = "none";
        	}
        } else {
        	if(e){
        		e.style.display = "block";
        	}
        }
    }
}

  