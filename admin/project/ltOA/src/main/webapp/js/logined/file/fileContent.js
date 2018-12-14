jQuery(document).ready(function() {
   
	$.removeTableCookie('SpryMedia_DataTables_myTable_fileContentMain.jsp');

     var inputs=document.getElementsByTagName("input");  
            for (var i=0;i<inputs.length; i++) {   
               if(inputs[i].getAttribute("type")=="text")  
                inputs[i].onkeyup=function(){  
                    this.value=this.value.replace(/(^\s+)|\s+$/g,"");  
                };  
            }

    
      //add by jiayq
    var sortFileType = $("#fileSortType").val();
            
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
	//getFileSortNdePriv();

	$("#searchFile").click(function() {
		_checkedIds="";
				getDataTable();
				return false;
			});
					
			// 处理事件的函数
			function FSubmit(e) {
				if (e == 13) {
					// 获取search内容
					jQuery("#searchFile").trigger("click");
					e.returnValue = false;
					// 返回false，在没有使用document.loginForm.submit()时可用于取消提交
				}
			}

			// 回车事件
			document.onkeydown = function(e) {
				// 兼容FF和IE和Opera
				var theEvent = e || window.event;
				var code = theEvent.keyCode || theEvent.which
						|| theEvent.charCode;

				if (code == 13) {
					_checkedIds="";
					getDataTable();
				}

			};

			
			
	$("body").show();

});


function setAccess() {

	window.location.href = basePath + 'logined/file/filesort.jsp?fileSortId='
			+ $("#fileSortId").val() + '&type=' + 2;
}

/**
 * 获取文件列表
 */
function getDataTable() {
	
	$('#myTable').dataTable({
		"bProcessing" : true,
		'bServerSide' : true,
		"bStateSave" : false, // 状态保存
		"bDestroy" : true,
		'fnServerParams' : function(aoData) {
			aoData.push({
						"name" : "sortId",
						"value" : document.getElementById("fileSortId").value
					}, {
						"name" : "subject",
						"value" : $.trim($("#subject").val())
					}, {
						"name" : "starttimeStr",
						"value" : $.trim($("#starttime").val())
					}, {
						"name" : "endtimeStr",
						"value" : $.trim($("#endTime").val())
					}, {
						"name" : "type",
						"value" : type
					}
			);
		},
		"sAjaxSource" : basePath + "file/findAllFileContents.action",
		"sServerMethod" : "POST",
		"sPaginationType" : "full_numbers",
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : true,// 页脚信息
		"bAutoWidth" : false,// 自动宽度
		"iDisplayLength" : 15, // 每页显示多少行
		"aoColumns" : [{
					"sTitle" : "<input type='checkbox' id='total'/>",
					"mDataProp" : null,
					"sClass" : "chk"
				}, {
					"sTitle" : '序号',
					"mDataProp" : "no",
					"sClass" : "num"
				}, {
					"sTitle" : '文件名称',
					"mDataProp" : null,
					"sWidth" : "100%",
					"sClass" : "longTxt"
				}, 
				{
					"sTitle" : '文件大小',
					"mDataProp" : "fileSize",
					"sWidth" : "80px",
					"sClass" : "data_r"
				},
				{
					"sTitle" : '创建人',
					"mDataProp" : "createUser",
					"sWidth" : "100px"
				},{
					"sTitle" : '创建时间',
					"mDataProp" : "createTime",
					"sWidth" : "150px",
					"sClass" : "right_bdr0"
				}

		],
		"oLanguage" : {
			"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
		},
		"fnDrawCallback" : function(oSettings) {
			// 提示
			  $('#myTable tbody  tr td[class="longTxt"]').each(function() {
	  				this.setAttribute('title', $(this).text());
	  			});
			$("#total").prop("checked", false);
			window.parent.frameResize();
			_getChecked();
		},
		"fnInitComplete" : function() {
			// 重置iframe高度
			window.parent.frameResize();
		},

		"aoColumnDefs" : [{
			"aTargets" : [0],// 覆盖第一列
			"fnRender" : function(oObj) {

				return '<input name="chk" value="' + oObj.aData.id
						+ '" attId="' + oObj.aData.attId
						+ '" type="checkbox" />';

			}
		}, {
			"aTargets" : [2],// 覆盖第四列
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
				for (var i = 0; i < strs.length; i++) {
					if (strs[i] != "") {
						var attFileType = strs2[i].substring(strs2[i]
								.lastIndexOf("."));
						var cls = getClassByFileType(attFileType);
						var downFileContent = basePath
								+ "filemanager/downfile.action?attachmentId="
								+ strs[i];
						strsub += '<p><a href="' + downFileContent
								+ '" class="' + cls + '" id="viewUrl">'
								+ strs2[i] + '</a></p>';
					}
				}
				return strsub;
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

function rediectCheckPerson(sortId, id, subject) {

	var url = basePath + "logined/file/getCheckFilePerson.jsp?sortId=" + sortId
			+ "&id=" + id + "&subject=" + subject;
	art.dialog.open(url, {
				id : 'addAttach',
				title : '签阅情况',
				width : 800,
				height : 450,
				lock : true,
			    opacity: 0.08,
				// 在open()方法中，init会等待iframe加载完毕后执行
				init : function() {
					// var iframe = this.iframe.contentWindow;
				}
			});

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


/**
 * 共享文件
 */
function shareFile() {
	
	var fileSortId = $("#fileSortId").val();
	if(fileSortId=="")
    {
    	fileSortId=0;
    }
	
	var path = $("#path").val();
	var titleName = "";
	var sortName = "";
	titleName = '上传文件';
	
	qytx.app.dialog.openUrl({
		url	:	basePath + "logined/file/shareFileAlert.jsp",
		title:	titleName,
		size:	"L",
		customButton:[{name: '确定',
						callback:function() {
							/***/			
							var iframe = this.iframe.contentWindow;
							var userIds = $.trim($(iframe.document).find("#userIds").val());
							var userNames = $.trim($(iframe.document).find("#userNames").val());
							var attachmentId = $.trim($(iframe.document).find("#attachmentId").val());
							if(!userIds || !userNames){
							    qytx.app.dialog.alert("请选择发布范围！");
							    return false;
							}
							if(!attachmentId || attachmentId==','){
								qytx.app.dialog.alert("请选择上传文件！");
								return false;
							}
							
							var attachmentNames=',';
							$(iframe.document).find("#attachmentList").find("li").each(function(){
								attachmentNames += $(this).find("p").eq(0).html()+",";
							});
							
							var paramData = {
									"userIds":userIds,
									"type":type,
									"userNames":userNames,
									"attachNames":attachmentNames,
									"attachIds":attachmentId,
									"sortId" : fileSortId
								};
							qytx.app.ajax({
								type : 'post',
								url : basePath + 'file/shareFile.action',
								data : paramData,
								dataType : 'text',
								success : function(data) {
									
							      if (data == 1) {
							    	  qytx.app.dialog.tips("上传成功！", function(){
							    		  window.location.reload();
							    	  });
									
									}else{
										qytx.app.dialog.alert("上传失败！");
										window.location.reload();
									}
								}
							});
							return true;
						},focus: true},{name: '取消'}]
	});
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
		pTar.style.display = "block";
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