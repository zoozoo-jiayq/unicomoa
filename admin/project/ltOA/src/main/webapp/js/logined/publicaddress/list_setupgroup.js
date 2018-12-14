$(document).ready(function() {
	queryAddress();

	// 单击修改
	$("#addGroup").click(function() {
		window.location.href = basePath + "logined/publicaddress/add_group.jsp";
		return false;
	});
});

function queryAddress() {
	
	qytx.app.grid({
		id	:	"myTable",
	   url	:	basePath + "addressGroup/setup_getPublicAddressGroupList.action",
	   bInfo	:	false,
	   valuesFn	:	[{
			"aTargets" : [3],// 覆盖第四列
			"fnRender" : function(oObj) {
				// 详情 编辑 删除
				var id = oObj.aData.id;
				// 编辑
				var updateUrl = '<a href="' + basePath
						+ "addressGroup/getDetailAddressGroup.action?id=" + id
						+ '" >修改与授权</a>';
				// 删除
				var deleteUrl = '<a href="javascript:void(0);" onclick=deleteAddressGroup("'
						+ id + '") >删除</a>';
				
				// 清空
				var clearUrl = '<a href="javascript:void(0);" onclick=clearAddressGroup("'
						+ id + '","' + oObj.aData.name + '") >清空</a>';
				clearUrl="";

				// 导入文件
				var importUrl = '<a href="javascript:void(0);" onclick=importAddress("'
						+ id + '","' + oObj.aData.name + '") >导入</a>';

				// 导出文件
				var exportUrl = '<a href="'
						+ basePath
						+ "address/setup_exportAddress.action?addressVo.sex=-1&addressVo.addressGroupId="
						+ id + '" >导出</a>';

				var result = "";
				// 默认的公共通讯簿不显示编辑,删除和维护权限
				result += importUrl;
				result += exportUrl;
				result += clearUrl;
				if ('-2' != id) {
					result += deleteUrl;
					result += updateUrl;
				}

//				if ('-2' != id) {
//					result += maintain;
//				}

				return result;

				// 编辑 删除 清空 导入 维护权限 打印 导出Foxmail格式 导出OutLook格式
			}
		}]
	});
}

/**
 * 删除分组信息
 * 
 * @param id
 */
function deleteAddressGroup(id) {
	qytx.app.dialog.confirm(sprintf("addressbook.msg_del_group_confirm1"),
			function() {
				qytx.app.ajax({
					url : basePath
							+ "addressGroup/setup_deleteAddressGroup.action?deleteType=1&id="
							+ id,
					type : "post",
					dataType : "text",
					success : function(data) {
						if ("success" == data) {
							window.location.href = window.location.href;
						} else {
							qytx.app.dialog
									.alert(sprintf("addressbook.msg_del_group_error"));
						}
					}
				});
			}, function() {
				return;
			});
}

/**
 * 清空分组信息
 * 
 * @param id
 */
function clearAddressGroup(id, groupName) {
	art.dialog.confirm(sprintf("addressbook.msg_clear_address_confirm&&"
					+ groupName), function() {
				$.ajax({
					url : basePath
							+ "addressGroup/setup_deleteAddressGroup.action?deleteType=2&id="
							+ id,
					type : "post",
					dataType : "text",
					success : function(data) {
						if ("success" == data) {
							window.location.href = window.location.href;
						} else {
							art.dialog
									.alert(sprintf("addressbook.msg_clear_group_error"));
						}
					}
				});
			}, function() {
				return;
			});

}

/**
 * 打开维护权限页面
 * 
 * @param id
 */
function openMaintain(id) {
	urlStr = basePath
			+ "addressGroup/getDetailAddressGroup.action?maintain=1&id=" + id;
	qytx.app.dialog.openUrl({
		url	:	urlStr,
		title:	"维护权限",
		size:	"L",
		});
}

/**
 * 打开导入通讯簿联系人页面
 * 
 * @param id
 */
function importAddressOld(id, groupNameSrc) {
	var groupName = groupNameSrc;
	var urlStr = basePath + "logined/address/import_address.jsp?groupId=" + id
			+ "&groupName=" + groupName;
	art.dialog.data('queryAddress', queryAddress2);
	qytx.app.dialog.openUrl({
		url	:	urlStr,
		title:	"联系人导入",
		size:	"L",
		close : function() {
			var iframe = this.iframe.contentWindow;
			var importTr = $(iframe.document).find("#importTr");
			if (importTr.length > 0)
				importTr.get(0).parentNode.removeChild(importTr.get(0));
			return true;
		}
		});
}

function queryAddress2(){
	// 空方法，防止报错
}


var freshPage = false;


function importAddress(id, groupNameSrc) {
	var groupName = encodeURI(groupNameSrc);
	var urlStr = basePath + "logined/publicaddress/alertMsg.jsp";
	qytx.app.dialog.openUrl({
		url	:	urlStr,
		title:	"导入",
		size:	"M",
		customButton : [{
			name : '验 证',
			focus:true,
			callback : function() {
				var obj = this.iframe.contentWindow;
				obj.checkFileFormat();
				return false;
			}
		}, {
			name : '导 入',
			focus:true,
			callback : function() {
				var obj = this.iframe.contentWindow;
				obj.startAjaxFileUpload(id, groupName);
				
				return false;
			}
		}, {
			name : '取 消',
			callback : function() {
				return true;
			}
		}]
		});
	
}

