jQuery(document).ready(function() {
	$.removeTableCookie('SpryMedia_DataTables_myTable_fileContentMain.jsp');
	 
     var inputs=document.getElementsByTagName("input");  
            for (var i=0;i<inputs.length; i++) {   
               if(inputs[i].getAttribute("type")=="text")  
                inputs[i].onkeyup=function(){  
                    this.value=this.value.replace(/(^\s+)|\s+$/g,"");  
                };  
            }

	/** 全选 */
	$("#selectAll").click(function() {
				checkAll();
				return false;
			});
	/** 反选 */
	$("#unselectAll").click(function() {
				reverseCheck();
				return false;
			});
	/** 头部全选复选 */
	$("#myTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
			});
		/** 第一列复选按钮 */
		$("#myTable").delegate("input:checkbox[name='chk']", "click",
				function(event) {
					check();
					event.stopPropagation();
				});
		getDataTable();
	    
			// 回车事件
			document.onkeydown = function(e) {
				// 兼容FF和IE和Opera
				var theEvent = e || window.event;
				var code = theEvent.keyCode || theEvent.which
						|| theEvent.charCode;

				if (code == 13) {
				
					getDataTable();
				}

			}
	$("body").show();
});

function setAccess() {

	window.location.href = basePath + 'logined/file/filesort.jsp?fileSortId='
			+ $("#fileSortId").val() + '&type=' + 2;
}

/***/
/**
 * 获取文件列表
 */
function getDataTable() {
	_checkedIds="";
	var fileSortId =$.trim($("#fileSortId").val());
	var subject =$.trim($("#subject").val());
	var starttime =$.trim($("#starttime").val());
	var endTime =$.trim($("#endTime").val());
	qytx.app.grid({
		id	:	"myTable",
		url	:	basePath + "file/findAllFileContents.action",
		iDisplayLength:	15,
		sPage:false,
		selectParam:	{
							"sortId":fileSortId,
							"subject":subject,
							"type":type,
							"starttimeStr":starttime,
							"endTimeStr":endTime
						},
		valuesFn	:	[{
							"aTargets" : [0],// 覆盖第一列
							"fnRender" : function(oObj) {
				
								return '<input name="chk" value="' + oObj.aData.id
										+ '" attId="' + oObj.aData.attId
										+ '" type="checkbox" />';
				
							}
						}, {
							"aTargets" : [2],
							"fnRender" : function(oObj) {
				
								var id = oObj.aData.id;
								var attId = oObj.aData.attId;
								var attName = oObj.aData.attName;
								var strs = new Array(); // 定义一数组放id
								var strs2 = new Array();// 定义一个数组放名称
								strs = attId.split(","); // 字符分割
								strs2 = attName.split(",");
								var strsub = "";
								if (strs2 == "") {
									strsub = "&nbsp;";
								}
								for (i = 0; i < strs.length; i++) {
									if (strs[i] != "") {
										var attFileType = strs2[i].substring(strs2[i]
												.lastIndexOf("."));
										var cls = getClassByFileType(attFileType);
										var downFileContent = basePath
												+ "filemanager/downfile.action?attachmentId="
												+ strs[i];
										strsub += '<p><a href="' + downFileContent
												+ '" class="' + cls + '" id="viewUrl">'
												+ strs2[i] + '</a></p>'
									}
								}
								// var
								// downFileContent=basePath+"upload/downfile.action?attachmentId="+attId;
								return strsub;
								// return '<a href="'+downFileContent+'"
								// id="viewUrl">'+oObj.aData.attName+'</a>';
							}
				
						}]
	});
}
/**
 * 根据文件类型获取对应class
 * 
 * @param type
 *            文件类型
 * @return {string} class名称
 */
function getClassByFileType(type) {
	if (type.indexOf(".") != -1) {
		type = type.substr(1, type.length);
	}
	type = type.toLocaleLowerCase();
	var defaultType = {
		txt : "fileTxt",
		doc : "fileWord",
		ppt : "filePPT",
		excel : "fileExcel",
		img : "filePicture",
		rar : "fileRar"
	};
	switch (type) {
		case "txt" :
			return defaultType.txt;
		case "doc" :
		case "docx" :
			return defaultType.doc;
		case "ppt" :
		case "pptx" :
			return defaultType.ppt;
		case "xls" :
		case "xlsx" :
			return defaultType.excel;
		case "gif" :
		case "jpg" :
		case "jpeg" :
		case "png" :
			return defaultType.img;
		case "rar" :
		case "zip" :
		case "7z" :
			return defaultType.rar;
		default :
			return defaultType.txt;
	}
}

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalChecked = $("input:checkbox[id='total']").prop("checked");
	var listCheckbox = $("input:checkbox[name='chk']");
	if (isTotalChecked) {
		listCheckbox.prop("checked", function(i, val) {
					if (!$(this).prop("disabled")) {
						return true;
					}
				});
	} else {
		listCheckbox.prop("checked", false);
	}
}

/**
 * 选择记录
 */
function check() {

	var checkTotalNum = $("input:checkbox[name='chk']");
	var checkNum = 0;
	var isAllChecked = true;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
					checkNum++;
				} else {
					isAllChecked = false;
				}
			});
	if (!isAllChecked) {
		$("input:checkbox[id='total']").prop("checked", false);
	} else {
		$("input:checkbox[id='total']").prop("checked", true);
	}
	$("#selectedNum").html(checkNum);
}
/**
 * 全选
 * 
 * @param name
 */
function checkAll() {
	var checkNum = 0;
	$("input:checkbox[name='chk']").prop("checked", function(i, val) {
				checkNum = checkNum + 1;
				return true;
			});
	$("input:checkbox[name='total']").prop("checked", true);
	$("#selectedNum").html(checkNum);
}
/**
 * 反选
 */
function reverseCheck() {
	var checkNum = 0;
	$("input:checkbox[name='chk']").prop("checked", function(i, val) {
				if (!val) {
					checkNum = checkNum + 1;
				}
				return !val;
			});
	$("#selectedNum").html(checkNum);
}
/** 打开新建文件 */
function addFile() {
	var fileId = document.getElementById("fileSortId").value;
	document.location.href = basePath + 'logined/file/addFile.jsp?fileId='
			+ fileId;
}
/** 批量上传文件 */
function allUpload() {
	var fileId = document.getElementById("fileSortId").value;
	document.location.href = basePath + 'logined/file/allUpload.jsp?fileId='
			+ fileId;
}

/** iframe的适应 */
function dyniframesize(down) {
	var pTar = null;
	if (document.getElementById) {
		pTar = document.getElementById(down);
	} else {
		eval('pTar = ' + down + ';');
	}
	if (pTar && !window.opera) {
		// begin resizing iframe
		pTar.style.display = "block"
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight) {
			// ns6 syntax
			pTar.height = pTar.contentDocument.body.offsetHeight + 20;
			// pTar.width = pTar.contentDocument.body.scrollWidth + 20;
		} else if (pTar.Document && pTar.Document.body.scrollHeight) {
			// ie5+ syntax
			pTar.height = pTar.Document.body.scrollHeight;
			// pTar.width = pTar.Document.body.scrollWidth;
		}
	}

}