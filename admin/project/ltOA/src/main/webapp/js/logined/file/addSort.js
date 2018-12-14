jQuery(document).ready(function() {
			$("#addSort").click(function() {
						saveFileSort();
						return false;
					});

		});

/**
 * 添加文件夹信息
 */
function saveFileSort() {
	var sortNo = $("#sortNo").val();
	var sortName = $("#sortName").val();

	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		return;
	}

	var dataParam = {
		'fileSort.sortNo' : sortNo,
		'fileSort.sortName' : sortName
	};
	$.ajax({
				type : 'post',
				url : basePath + "file/addFileSort.action",
				data : dataParam,
				dataType : 'text',
				success : function(data) {
             
					if (data == 2) {
						/*art.dialog({
									lock : false,
									background : '#000',
									opacity : 0.1,
									title : '提示',
									content : '添加成功！',
									height : 109,
									width : 317,
									icon : 'warning',
									ok : function() {*/
										window.location.href = basePath
												+ 'logined/file/fileList.jsp';
								/*		return false;
									},
									close : function() {
										window.location.href = document.referrer;
										return false;
									}
								});*/

					} else if (data == 1) {
						art.dialog({
									lock : true,
									background : '#000',
									opacity : 0.1,
									title : '提示',
									height : 109,
									width : 317,
									content : '文件夹已经存在！',
									icon : 'warning',
									ok : function() {
										window.location.href = basePath
												+ 'logined/file/fileList.jsp';
										return false;
									},
									close : function() {
										window.location.href = document.referrer;
										return false;
									}
								});

					} else {
						art.dialog({
									lock : true,
									background : '#000',
									opacity : 0.1,
									title : '提示',
									content : data,
									icon : 'succeed',
									height : 109,
									width : 317,
									ok : function() {
										window.location.href = basePath
												+ 'logined/file/fileList.jsp';
										return false;
									},
									close : function() {
										window.location.href = document.referrer;
										return false;
									}
								});
					}

				}

			});

}

/**
 * 更新文件夹信息
 */
function updateFileSort(filesort) {

	var titleName = "";
	var contentName = "";
	titleName = '修改文件夹信息';
	contentName = '<table class="inputTable" style="width:500px;font-family:\"微软雅黑\""  cellspacing="0" cellpadding="0" border="0" ><tr><th><font class="requireField">*</font>文件夹名称</th><td><input type="text" class="formText" id="sortName" maxlength="50" value='
		+ filesort.sortName
		+ ' ></td></tr><tr><th>排序号</th><td><input type="text" class="formText" id="sortNo" maxlength="50"  value='
		+ filesort.sortNo+ ' ><input type="hidden" id="sortId" value='
		+ filesort.sortId + ' /></td></tr><table>';
	var dialog = art.dialog({
		title : titleName,
		content : contentName,
		 width : 800,
		 height : 450,
		ok : function() {
			var sortNo = $("#sortNo").val();
			var sortName = $("#sortName").val();
			var sortId = $("#sortId").val();
			if (sortName == "") {
				art.dialog.alert('文件夹名称不能为空！');
				return false;
			} else {
				var dataParam = {
					'fileSort.sortNo' : sortNo,
					'fileSort.sortName' : sortName,
					'fileSort.SortId' : sortId
				};
				$.ajax({
					type : 'post',
					url : basePath + "file/updateFileSort.action",
					data : dataParam,
					dataType : 'text',
					success : function(data) {

						if (data == "") {
							art.dialog({
										lock : true,
										background : '#000',
										opacity : 0.1,
										title : '提示',
										height : 109,
										width : 317,
										content : '修改成功！',
										icon : 'succeed',
										ok : function() {

											window.location.href = basePath
													+ 'logined/file/fileList.jsp';
											return false;
										},
										close : function() {

											window.location.href = document.referrer;
											return false;
										}
									});
						} else {
							art.dialog.alert(data);
						}

					}
				});
			}
		},
		okVal : '提交',
		cancelVal : '关闭',
		cancel : true
	});

}
