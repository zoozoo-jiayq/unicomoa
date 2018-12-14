$(function(){
	 $(".homePage td a").on("touchstart",function() {
        $(this).addClass("active");
     });
     $(".homePage td a").on("touchend",function() {
         $(this).removeClass("active");
     });
});

//tab
function showTab(o, tab, count) {
    o.className = "active";
    var j;
    var id;
    var e;
    for (var i = 1; i <= count; i++) {
        id = tab + i;
        j = document.getElementById(id);
        e = document.getElementById(tab + "Cont" + i);
        if (id != o.id) {
            j.className = "";
            e.style.display = "none";
        } else {
            e.style.display = "block";
        }
    }
}

  