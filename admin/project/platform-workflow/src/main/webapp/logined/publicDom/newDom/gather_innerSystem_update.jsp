<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/handle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/logined/publicDom/newDom/category.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收文登记</title>
</head>
<body>
<input type="hidden" id="gongwenTypeFlag" value="1"/>
	<input type="hidden" id="secretLevel_init" value="${varMap.secretLevel}"/>
	<input type="hidden" id="wenhao_init" value="${varMap.wenhao}"/>
	<input type="hidden" id="huanji_init" value="${varMap.huanji}"/>
	<input type="hidden" id="title_init" value="${varMap.title}"/>
	<input type="hidden" id="sourceDept_init" value="${varMap.sendDept}"/>

	<input type="hidden" id="instanceId" name="instanceId" value="${instanceId}" />
	<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
	<input type="hidden" name="publicDomVo.domTypeName" id="domTypeName" value="${varMap.gongwenType}"/>
	<input type="hidden" name="publicDomVo.domName" id="domName" value="gather"/>
	
		<div class="header_hd">
	    <div class="operate">
	           <div class="tab">
	                <ul>
	                  <li class="current"><em class="sw"></em><a href="javascript:void();">基本信息</a></li>
	                  <li><em class="sw"></em><a href="javascript:void();">收文单</a></li>
	                  <li><em class="fj"></em><a href="javascript:void();">附件</a></li>
	                  <li><em class="bs"></em><a href="javascript:void();">板式文件</a></li>
	                </ul>
	          </div>
			  <div class="btn">
	            <input type="button" class="formButton" value="保存" id="next"/>
	            <c:forEach items="${startOperations }" var="oper">
	            <input type="button" class="formButton" value="${oper }" onclick="alertInfo();"/>
	            </c:forEach>
	          </div>
		</div>
	</div>
	<input type="hidden" name="publicDomVo.domName" id="domName" value="gather"/>
	<input type="hidden"  id="domTypeName" />
	<input type="hidden" id="firstLevel">
	<div class="content_hd">
    <div class="tabContent" style="display:block">
    <div class="formPage">
    <div class="formbg">
	<form action="" id="add">
    <div class="content_form">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		<tr>
			<th><label>公文类型：</label></th>
			<td><select  id="category" style="width:16em">
			  <option selected="selected" value="">－请选择一级分类－</option>
		    </select>　<select name="publicDomVo.domTypeId" valid="required" id="gongwenType" errmsg="documentType.DispatchTypeName_not_null" style="width:16em">
			  <option selected="selected" value="">－请选择二级分类－</option>
		    </select></td>
		</tr>		
		<tr>
		  <th><label class="requireField"></label><label>密　　级：</label></th>
		  <td><select  style="width:16em" name="publicDomVo.secretLevel" id="secretLevel" valid="required"  errmsg="documentType.secretLevel_not_null">
			  <option value="">－请选择－</option>
			  <c:forEach items="${secretLevels}" var="secret">
			  	<option value="${secret.name }" >${secret.name }</option>
			  </c:forEach>
		    </select></td>
  		</tr>
		<tr>
		  <th><label class="requireField"></label><label>缓　　急：</label></th>
		  <td><select  style="width:16em" name="publicDomVo.huanji" id="huanji" valid="required"  errmsg="documentType.huanji_not_null">
			  <option value="">－请选择－</option>
			  <c:forEach items="${huanji }" var="hj">
			  	<option value="${hj.name }">${hj.name }</option>
			  </c:forEach>
		    </select></td>
  		</tr>
  		<tr>
			<th><label>文　　号：</label></th>
			<td><input name="publicDomVo.wenhao" id="wenhao" type="text" class="formText" size="107" maxlength="50" /></td>
		</tr>
		<tr>
			<th><label class="requireField"></label><label>公文标题：</label></th>
			<td><input name="publicDomVo.title" id="title" value="" type="text" class="formText" valid="required" maxlength="50" errmsg="gather_file.title_not_null" size="107" /></td>
		</tr>
        <tr>
			<th><label class="requireField"></label><label>来文单位：</label></th>
			<td><input name="publicDomVo.sourceDept" id="dept"  value="" type="text" class="formText"valid="required" errmsg="gather_file.sourceDept_not_null" size="107" maxlength="100" /></td>
		</tr>		
	</table>
	<div class="con_explain">
      <ul>
        <li>注释：文号自动生成，生成规则可进行配置；</li>
      </ul>
    </div>
	</div>
	</form>
	</div>
	</div>
	</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	 $(".closeMenu").click(function(){
	     $(".mainpage").removeClass("mainpage_45");
	 });
	 $(".openMenu h2").click(function(){
	     $(".mainpage").addClass("mainpage_45");
	 });
	  $(".openMenu h3").click(function(){
		if($(".more_cz").is(":visible")){
	      $(this).next(".more_cz").slideToggle();
		}else{
		  $(this).next(".more_cz").slideToggle();
	    }
		
	 });
});
function windowOpen(url){
   	var width = window.screen.availWidth;
   	var height = window.screen.availHeight;
   	window.open(url,"","width="+width+",height="+height+",menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes");
}
	function getWenhao(domTypeId,dstInput){
		$.post(basePath+"dom/gather!getWenhao.action?documentTypeId="+domTypeId,function(data){
			data = eval("("+data+")");
			var wenhao = data.wenhao;
			var canUpdate = data.canUpdate;
			$(dstInput).val(wenhao);
			if(canUpdate == "0"){
				$(dstInput).attr("disabled","disabled");
			}
		});
 	}

$("#gongwenType").change(function(){
	var typeId = $(this).val();
	var name = $(this).find("option:selected").text();
	$("#domTypeName").val(name);
	if(typeId){
		getWenhao(typeId,$("#wenhao"));
	}
});

$("#next").click(function(){
	if(validator(document.getElementById("add"))){
		$("body").lock();
			var taskId = $("#taskId").val();
			var domTypeId = $("#gongwenType").val();
			var wenhao = $("#wenhao").val();
			var secretLevel = $("#secretLevel").val();
			var huanji = $("#huanji").val();
			var title = $("#title").val();
			var dept = $("#dept").val();
			var domName = $("#domName").val();
			var domTypeName = $("#domTypeName").val();
			var query = "publicDomVo.domTypeId="+domTypeId+"&publicDomVo.wenhao="+wenhao+"&publicDomVo.secretLevel="+secretLevel+"&publicDomVo.huanji="+huanji+"&publicDomVo.title="+title+"&publicDomVo.sourceDept="+dept+"&publicDomVo.domName="+domName+"&publicDomVo.domTypeName="+domTypeName+"&publicDomVo.firstLevel="+$("#firstLevel").val()+"&taskId="+taskId+"&instanceId="+$("#instanceId").val();
			$.post(basePath+"/dom/gather!updateInnerSystem.action?"+encodeURI(query)+"&r="+Math.random(),function(data){
				window.location.href=basePath+"dom/public!toMainByForward.action?taskId="+taskId+"&menu=1";
			});
	}
});

//赋值初始化
(function(){
	$("#gongwenType").val($("#gongwentypeId_init").val());
	$("select[name='publicDomVo.secretLevel']").find("option").each(function(){
		if($(this).text() == $("#secretLevel_init").val()){
			$(this).attr("selected","selected");
		}
	});
	$("#wenhao").val($("#wenhao_init").val());
	$("select[name='publicDomVo.huanji']").find("option").each(function(){
		if($(this).text() == $("#huanji_init").val()){
			$(this).attr("selected","selected");
		}
	});
	$("input[name='publicDomVo.title']").val($("#title_init").val()+"(收文)");
	$("input[name='publicDomVo.sourceDept']").val($("#sourceDept_init").val());
})();
function alertInfo(){
	art.dialog.alert("请先保存基本信息!");
}
</script>
</html>