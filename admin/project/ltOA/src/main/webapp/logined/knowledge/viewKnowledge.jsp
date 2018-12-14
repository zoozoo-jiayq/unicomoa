<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="../../common/taglibs.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看知识库</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/plugins/infoDetail/skins/infoDetail_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function(){
	var kcId = $("#kcId").val();
	if(kcId!=null&&kcId!=""){
		$("div.detailbg h3 em").attr("style","background: url(${ctx}common/images/icon/shoucang.png) no-repeat center left;width: 14px;height: 14px;display: inline-block;padding-right: 3px;");
	}else{
		$("div.detailbg h3 em").attr("style","background: url(${ctx}common/images/icon/s_cang.png) no-repeat center left;width: 14px;height: 14px;display: inline-block;padding-right: 3px;");
	}
	
});

function load(){
    var t = $("#beforeOrAfter").val();
    if(t==1){
    	$("#head").attr("style","display:none");
    }
}

function back(){
	 var t = $("#beforeOrAfter").val();
	 var fromPage = $("#fromPage").val();
	 if(t==1){
		// 前台 返回
	    location.href=basePath+'logined/knowledge/jsp/beforeKnowledge_list.jsp';
	  }else{
		  //alert(t);
		  //alert("fromPage: "+fromPage);
		  if(fromPage=="backWelcome"){
		  	window.location.href =basePath+'logined/welcome.jsp';
		  }else{
		  	window.location.href =basePath+'logined/knowledge/backKnowledge_list.jsp';    
		  }
	  }
};

/**
*添加收藏
*/
function addCollect(){
	$.ajax({
		url : basePath + "/knowledge/collect_collectKnowledge.action",
		data : {
			"knowledge.vid" : $("#knowledgeId").val()
		},
		type : "post",
		dataType : "text",
		async : false,
		success : function(data){
			if(data=="alreadyCollect"){
				//art.dialog.tips("已收藏！",1);
			}else if(data=="error1"){
				art.dialog.tips("参数传递错误，加入收藏失败！",1);
			}else if(data=="error2"){
				art.dialog.tips("加入收藏失败，请联系管理员处理！",1);
			}else{
				$("#kcId").val(data);
				$("div.detailbg h3 span a").html("<em></em>取消收藏");
				$("div.detailbg h3 span a").attr("onclick","deleteCollect()");
				$("div.detailbg h3 span em").attr("style","background: url(${ctx}common/images/icon/shoucang.png) no-repeat center left;width: 14px;height: 14px;display: inline-block;padding-right: 3px;");
				window.top.refreshKnowledgeCollect();
			}
		}
	});
}

/**
*取消收藏
*/
function deleteCollect(){
	var kcId = $("#kcId").val();
	if(kcId!=null&&kcId!=""){
		$.ajax({
			url : basePath + "/knowledge/collect_deleteCollect.action",
			data : {
				"kcId" : kcId
			},
			type : "post",
			dataType : "text",
			async : false,
			success : function(data){
				if(data==0){
					//art.dialog.tips("收藏成功！",1);
					$("#kcId").val("");
					$("div.detailbg h3 span a").html("<em></em>加入收藏");
					$("div.detailbg h3 span a").attr("onclick","addCollect()");
					$("div.detailbg h3 span em").attr("style","background: url(${ctx}common/images/icon/s_cang.png) no-repeat center left;width: 14px;height: 14px;display: inline-block;padding-right: 3px;");
					window.top.refreshKnowledgeCollect();
				}else{
					art.dialog.tips("参数传递错误，取消收藏失败！",1);
				}
			}
		});
	}
}

function playAudio(){
	var voxPath = $("#voxPath").val();
	window.top.playKnowledgeAudio(voxPath);
}

function downFileById(id) {
	window.open(basePath + 'filemanager/downfile.action?attachmentId=' + id);
}

</script>
<%-- <style type="text/css">
    .detailsContent .keywords{
    	text-indent:2em;
    }
    .detailbg .keywords{
    	margin:20px 0 25px 0;
    }
</style> --%>

</head>
<body  onload="load()">
<div class="detailsPage">
	<input id="voxPath" type="hidden" value="${knowledge.voxPath}"/>
	<input id="kcId" type="hidden" value="${kcId}"/>
	<input id="knowledgeId" type="hidden" value="${knowledge.vid}"/>
	<input id="beforeOrAfter" type="hidden" value="${isBeforeOrAfter}" />
    <input id="fromPage" type="hidden" value="${fromPage}" />
  <div class=" detailbg">
	<h2 id="subject">${knowledge.title}</h2>
   <h3><span>所属分类：<s:if test="#request.parentNameStr!=null&&#request.parentNameStr!=''"><s:property value="#request.parentNameStr"/></s:if></span></h3>
    <h3><span>录入人：${knowledge.createUserInfo.userName}</span><span>创建时间：${time}</span>
    <s:if test="isBeforeOrAfter==1">
	    <s:if test="knowledge.hasRecordAudio != null && knowledge.hasRecordAudio != '' && knowledge.hasRecordAudio == 1">
	    <a href="javascript:;" onclick="playAudio();">播放合成录音</a>
	    </s:if><s:else>
	    <a href="javascript:;" style="color:#ccc;">播放合成录音</a>
	    </s:else>
    </s:if>
    <span>
    <c:if test="${knowledge.isPrivate==0}">
    <c:if test="${isBeforeOrAfter==1&&isCollect==true}">
    <a style="padding-left:20px;" href="javascript:void(0);" id="deleteCollect" onclick="deleteCollect();"><em></em>取消收藏</a>
    </c:if>
    <c:if test="${isBeforeOrAfter==1 && isCollect==false }">
    <a style="padding-left:20px;" href="javascript:void(0);" id="addCollect"  onclick="addCollect();"><em></em>加入收藏</a>
    </c:if>  
    </c:if>
  </span>
    
    </h3>

	    <div class="detailsContent">
	    	<s:if test="knowledge.keyword != null && knowledge.keyword != '' ">
				<div class="keywords"><span>关键字：</span>${knowledge.keyword}</div>
			</s:if>
		    <p>${knowledge.contentInfo}</p>
	    </div>
	    <s:if test="#request.fileList!=null && #request.fileList.size()>0">
		    <div class="expFile" id="fileListDiv" style="overflow:hidden;">
		    	<h4 class="annex_title">附件:</h4>
		 		<div class="annex">
			    	<ul id="attachmentList">
			    		<s:iterator id="f" value="#request.fileList">
			    			<li>
			    				<div class="icon">
			    					<em class="doc"></em>
			    				</div>
			    				<div class="txt">
			    					<p><s:property value="#f.attachName"/></p>
			    					<p>
			    						<a  href="javascript:void(0);"  onclick="downFileById('<s:property value="#f.id"/>',this);">下载</a>
									</p>
								</div>
							</li>
			    		</s:iterator>
			    	</ul>
			    	<div class="clear"></div>
		    	</div>
	    	</div>
    	</s:if>
    </div>
    <div class="buttonArea">
    	<c:choose>
    		<c:when test="${isBeforeOrAfter==1 }">
	    		<input type="button" class="formButton_grey" value="关闭" onclick="window.top.closeCurrentTab();" />
    		</c:when>
    		<c:otherwise>
				<input type="button" class="formButton_grey" value="返回" onclick="javascript:history.back();"/>
    		</c:otherwise>
    	</c:choose>
            <%-- <span class="blue">点击回到知识库列表</span> --%>
    </div>
</div>
</body>

</html>