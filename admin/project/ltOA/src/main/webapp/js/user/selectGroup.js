$(document).ready(function() {
			getGroupTree();

			$("#groupSel").click(function() {
						showGroup();
						return false;
					});

			// 科室选择不能点击回车
			$("#groupSel").keydown(function(e) {
						if (e.which == 8) {
							return false;
						}
					});
		});

/**
 * 群组树调用群组树
 */
var showOrHide = true;
function showGroup() {
	$('#menuContent').toggle(showOrHide);
	// 相当于
	if (showOrHide) {
		showOrHide = false;
		var groupObj = $("#groupSel");
		var groupOffset = $("#groupSel").position();
		$("#menuContent").css({
					left : groupOffset.left + "px",
					top : groupOffset.top + groupObj.outerHeight() - 1 + "px"
				}).show();
		// $("#menuContent").bgiframe({top: 0, left: 0, width:
		// $("#groupTree").outerWidth(), height:
		// $("#groupTree").outerHeight()});
		$("#treeContent").one("mouseleave", function() {
					$("#menuContent").hide();
					showOrHide = true;
					return false;
				});
	} else {
		$("#menuContent").hide();
		showOrHide = true;
	}
	$("body").bind("mousedown", onBodyDown);
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!event.target.id) {
			hideMenu();
		}
	}
}

/**
 * 获取群组的树结构
 */
function getGroupTree() {
	// 设置树样式
	// 开始隐藏
	$("#menuContent").hide();
	// 组织机构树参数设置
	var setting = {
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onTreeNodeClick
		}
	};
	var dataParam = {
		'type' : 1,
		'showType' : 1
	};
	$.ajax({
				url : basePath + "user/selectUser.action",
				type : 'post',
				dataType : 'json',
				data : dataParam,
				success : function(data) {
					$.fn.zTree.init($("#groupTree"), setting, data);
					// 选择部门
					getGroup();
				}
			});
	/**
	 * 得到部门
	 */
	function getGroup() {
		var zTree = $.fn.zTree.getZTreeObj("groupTree");
		var nodes = zTree.transformToArray(zTree.getNodes());
		for (var i = 0; i < nodes.length; i++) {
			var gName = nodes[i].name;
			var gId = nodes[i].id;
			var groupId = $("#groupId").val();
			if (gId == "gid_" + groupId) {
				zTree.selectNode(nodes[i]);
				$("#groupSel").val(gName);
			}
		};
	}
	/**
	 * 群组树调用点击事件
	 */
	function onTreeNodeClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("groupTree");
		var nodes = zTree.getSelectedNodes();
		if (nodes.length > 0) {
			if(nodes[nodes.length - 1].id == "gid_0"){
				return;
			}
			var groupName = nodes[nodes.length - 1].name;
			var groupId = nodes[nodes.length - 1].id;
			$("#groupId").val(groupId.substring(4, groupId.length));
			$("#groupSel").val(groupName);
			$("#menuContent").hide();
		}
	}
}