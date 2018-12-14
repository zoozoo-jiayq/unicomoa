var ue;
var isSubmit = false;
$(function() {
	ue = UE.getEditor('contentInfo', {
		initialFrameWidth : "100%",
		initialFrameHeight : "300px",
		maximumWords:2000
	});
	var isBeforeOrAfter = $("#isBeforeOrAfter").val();
	if (isBeforeOrAfter == 0) {
		$("#head").attr("style", "display:block");
	}
	$("#addOrUpdate").click(function() {
		var valid = validator($("#addKnowledgeForm")[0]);
		if (valid) {
			addOrUpdate();
		}
	});
	
	var fileupload = new qytx.app.fileupload({
		id:"file_upload",
		hiddenID:"attachmentId",
		queueID:"queue",
		ulName:"attachmentList",
		fileSizeLimit:"20MB",
		deleteFun:function(attachId,filePath){
			var attachmentIdAll = $("#attachmentId").val();
			attachmentIdAll = attachmentIdAll.replace("," + attachId + ",", ",");
			if(attachmentIdAll == ','){
				attachmentIdAll = '';
			}
			$("#attachmentId").val(attachmentIdAll);
		}
	});

	$("#title").keyup(function() {
		var length = $("#title").val().length;

		if (length > 100) {
			var cutStr = $("#title").val().substring(0, 100);
			$("#title").val(cutStr);
			return false;
		}

	});

	$("#groupSel").click(function() {
		showGroup();
		return false;
	});

	/**
	 * 清空
	 * 
	 */

	$("#clean").click(function() {
		art.dialog.confirm('确定清空？', function() {
			$("#contentInfo").val("");
			art.dialog.tips('清空成功！');
		}, function() {
			art.dialog.close();
		});

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
		$("#treeContent").one("mouseleave", function() {
			$("#menuContent").hide();
			showOrHide = true;
			return false;
		});
	} else {
		$("#menuContent").hide();
		showOrHide = true;
	}
}

function addOrUpdate() {
	
	if (isSubmit) {
		return;
	}
	var title = $("#title").val();
	var keyword = $("#keyword").val();
	var parentId = $("#parentId").val();
	var groupSel = $("#groupSel").val();
	var isBeforeOrAfter = $("#isBeforeOrAfter").val();
	var isToView = $("#isToView").val();
	if(isToView==1){
		isBeforeOrAfter = 0;
	}
	var vid = $("#id").val();
	if (title == null || "" == title.trim()) {
		art.dialog.alert("标题不能为空！");
		return;
	}
	if (parentId == 0) {
		art.dialog.alert("请选择分类名称!");
		return;
	}

	var contentInfo = ue.getContent(); 
	
	var oldTitle = $("#oldTitle").val();
	var oldKeyword = $("#oldKeyword").val();
	var oldTypeName = $("#oldTypeName").val();
	var oldContent = $("#oldContent").html();
	var oldAttachmentIds = $("#oldAttachmentIds").html();
	if(oldTitle==title&&oldKeyword==keyword&&oldTypeName==groupSel&&oldContent==contentInfo&&$("#attachmentId").val()==oldAttachmentIds){
		art.dialog.alert("您当前没有任何修改!");
		return;
	}
	
	if (null == contentInfo || '' == contentInfo.trim()) {
		showObjError($("#contentInfo"), 'knowledge.content_not_null');
		isSubmit=false;
		return;
	}
	if (ue.getContentTxt().replace(/[\t\r\n]+/g, '').length > 2000) {
		showObjError($("#contentInfo"), 'knowledge.content_length_limit');
		isSubmit=false;
		return;
	}
	isSubmit = true;
	var paramData = {
	    'knowledge.title' : title,
	    'knowledge.keyword' : keyword,
	    'knowledge.columnId' : $("#columnId").val(),
	    'knowledge.knowledgeType.vid' : parentId,
	    'knowledge.contentInfo' : ue.getContent(),
	    'knowledge.attachmentIds':$("#attachmentId").val(),
	    'knowledge.vid' : vid
	};
	var path = "";
	var toWhere = "";
	if (isBeforeOrAfter == 1) {
		path = basePath + "knowledge/knowledge_addOrUpdateBefore.action";
		toWhere = basePath + 'hotline/seat/jsp/knowledge/beforeKnowledge_list.jsp';
	};
	if (isBeforeOrAfter == 0) {
		// 后台
		path = basePath + "knowledge/knowledge_addOrUpdate.action";
		toWhere = basePath + 'logined/knowledge/backKnowledge_list.jsp?columnId='+$("#columnId").val();
	};
	$.ajax({
	    url : path,
	    type : "post",
	    dataType : "text",
	    data : paramData,
	    success : function(data) {
		    if (data == 0) {
		    	if(vid){
//		    		parent.art.dialog.tips('修改成功！');
		    		art.dialog.alert("修改成功！",function(){
		    			window.location.href = toWhere;
		    		});
		    	}else{
//		    		parent.art.dialog.tips('新增成功！');
		    		art.dialog.alert("新增成功！",function(){
		    			window.location.href = toWhere;
		    		});
		    	}
		    } else if (data == 1) {
			    art.dialog.alert("标题重复！");
			    isSubmit = false;
		    } else if (data == 2){
		    	 art.dialog.alert("更新失败,您的权限不够！");
				    isSubmit = false;
		    } else if (data == 3){
		    	art.dialog.alert("更新失败,知识库类型不存在！");
		    	isSubmit = false;
		    } else {
			    art.dialog.alert("更新失败，请稍后再试。");
			    isSubmit = false;
		    }

	    }
	});

}

function deleteAttachment_knowledge(attachmentId, domAObj) {
	$(domAObj).parent().parent().parent().remove();
	var attachmentIdAll = $("#attachmentId").val();
	attachmentIdAll = attachmentIdAll.replace("," + attachmentId + ",", ",");
	if(attachmentIdAll == ','){
		attachmentIdAll = '';
	}
	$("#attachmentId").val(attachmentIdAll);
}
