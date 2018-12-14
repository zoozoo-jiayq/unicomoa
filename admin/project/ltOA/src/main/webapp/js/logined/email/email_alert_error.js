$(document).ready(function () {
    var errorMsg = $("#errorMsg").val();
    var content = sprintf("email." + $.trim(errorMsg));
    $("#errorMsgBox").text(content);
    window.parent.frameResize();
});