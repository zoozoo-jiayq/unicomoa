jQuery(document).ready(function() {

		});

/**
 * 删除文件夹信息
 */
function delCatorlog() {
	var fileSortId = $("#fileSortId").val();
	var paramData = {
		'fileSort.SortId' : fileSortId

	};
	$.ajax({
		type : 'post',
		url : basePath + "file/checkChildFileSort.action",
		data : paramData,
		dataType : 'text',
		success : function(data) {

			if (data == 1) {
				art.dialog.alert("该文件夹下面有子文件夹,不能删除！");
				checkfilesort = 2;
				return;
			} else {
				art.dialog.confirm('目录删除后，该目录下的文件将被删除且不可恢复！确认删除该目录吗？',
						function() {
							$.ajax({
								type : 'post',
								url : basePath + "file/deleteFileSort.action",
								data : paramData,
								dataType : 'text',
								success : function(data) {

									if (data == "") {
										/*art.dialog({
											lock : false,
											background : '#000',
											opacity : 0.1,
											title : '提示',
											left : '500px',
											height : 109,
											width : 317,
											content : '删除成功！',
											icon : 'warning',
											ok : function() {*/
												window.parent.location.reload();
											/*},
											close : function() {
												window.location.href = document.referrer;
												return false;
											}
										});*/
									} else {
										art.dialog.alert(data);
									}
								}
							});
						}, function() {
							return;
						});
			}

		}
	});

}