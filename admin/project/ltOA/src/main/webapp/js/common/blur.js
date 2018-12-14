/**
 * REN
 * 失去焦点
 */
$(document).ready(function() {
   //不能得到焦点
   $(".blur-not-disabled").focus(function() {
       $(this).blur();
   });
});