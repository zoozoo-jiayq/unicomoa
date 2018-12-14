/**
 * 换肤
 * @param result
 * @return
 */
function testskin(result){
	var css=document.getElementById("skinCss");
	if (result==1){
		updateUserSkinLogo(result);
	}
	if (result==2){
		updateUserSkinLogo(result);
	}
}

/**
 * 修改换肤标志
 */
function updateUserSkinLogo(skinLogo){
	$.ajax({
		url : basePath+"login/updateUserSkinLogo.action?skinLogo="+skinLogo,
		type : "post",
		dataType : "html",
		success : function(data) {
			parent.location.reload();
		}
	});
}