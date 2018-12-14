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
</script>
<style type="text/css">
  .inputTable th{width:75px;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收文登记</title>
</head>
<body>
<input type="hidden" id="gongwenTypeFlag" value="2"/>
	<div class="header_hd">
	    <div class="operate">
	           <div class="tab">
	                <ul>
	                  <li class="current"><em class="sw"></em><a href="#">基本信息</a></li>
	                  <li><em class="fwd"></em><a href="#">发文单</a></li>
	                  <li><em class="zw"></em><a href="#">正文</a></li>
	                  <li><em class="fj"></em><a href="#">附件</a></li>
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



	<form id="add">
	<input type="hidden" name="publicDomVo.domName" id="domName" value="dispatch"/>
	<input type="hidden"  id="domTypeName" />
	<input type="hidden" id="firstLevel">
	<div class="content_hd">
    <div class="tabContent" style="display:block">
    <div class="formPage">
    <div class="formbg">
    <div class="content_form">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
		<tr>
			<th><label>公文类型：</label></th>
			<td><select  id="category" style="width:16em" valid="required" errmsg="documentType.first_level_not_null">
			  <option selected="selected" value="">－请选择－</option>
		    </select>　<select name="publicDomVo.domTypeId" valid="required" id="gongwenType" errmsg="documentType.second_level_not_null" style="width:16em">
			  <option selected="selected" value="">－请选择－</option>
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
			<th><label class="requireField"></label><label>发文单位：</label></th>
			<td><input name="publicDomVo.sourceDept"  id="dept"    type="text" class="formText"  valid="required" errmsg="gather_file.sourceDept_not_null" maxlength="30" size="107"  value="${defaultName}" /></td>
		</tr>
		<tr>
			<th><label class="requireField"></label><label>公文标题：</label></th>
			<td><input name="publicDomVo.title" id="title" value="" type="text" class="formText" valid="required" maxlength="50" errmsg="gather_file.title_not_null" size="107" /></td>
		</tr>
        	
	</table>
	<div class="con_explain">
      <ul>
        <li>注：核稿完成后生成文号，生成规则由管理员在系统设置中配置；</li>
      </ul>
    </div>
	</div>
	</div>
	</div>
	</div>
	</div>
</form>
</body>
<script type="text/javascript">
	function windowOpen(url){
    	var width = window.screen.availWidth;
    	var height = window.screen.availHeight;
    	window.open(url,"","width="+width+",height="+height+",menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes");
    }

	$("#next").click(function(){
		if(validator(document.getElementById("add"))){
			$("body").lock();
			var domTypeId = $("#gongwenType").val();
			var secretLevel = $("#secretLevel").val();
			var huanji = $("#huanji").val();
			var title = $("#title").val();
			var dept = $("#dept").val();
			var domName = $("#domName").val();
			var domTypeName = $("#domTypeName").val();
			var firstLevel = $("#firstLevel").val();
			var query = "publicDomVo.domTypeId="+domTypeId+"&publicDomVo.secretLevel="+secretLevel+"&publicDomVo.huanji="+huanji+"&publicDomVo.title="+title+"&publicDomVo.sourceDept="+dept+"&publicDomVo.domName="+domName+"&publicDomVo.domTypeName="+domTypeName+"&publicDomVo.firstLevel="+firstLevel;
			qytx.app.ajax({
				type:"post",
				url:basePath+"/dom/public!startDom.action?"+encodeURI(query)+"&r="+Math.random(),
				success:function(data){
					if(data){
						window.location.href=basePath+"dom/public!toMainByForward.action?taskId="+data+"&menu=5";
					}
				}		
			});
		}
	});

	$("#gongwenType").change(function(){
		var typeId = $(this).val();
		var name = $(this).find("option:selected").text();
		$("#domTypeName").val(name);
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
		$("input[name='publicDomVo.title']").val($("#title_init").val());
		if($("#sourceDept_init").val()){
			$("input[name='publicDomVo.sourceDept']").val($("#sourceDept_init").val());
		}
	})();

function alertInfo(){
	qytx.app.dialog.alert("请先保存基本信息!");
}
</script>
</html>