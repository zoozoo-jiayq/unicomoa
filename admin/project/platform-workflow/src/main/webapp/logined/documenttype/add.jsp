<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="../../common/taglibs.jsp" />
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公文管理--基础设置--公文类型设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<style type="text/css">
  @import "${ctx}plugins/tree/ztree/zTreeStyle/zTreeStyle.css";
</style>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/ztree-wrapper.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/common/ztree-wrapper.js"></script>
<jsp:include page="docTypeHead.jsp"/>
<script type="text/javascript">
var filePath = "";
var attachId = null;
</script>
<script type="text/javascript" src="${ctx}js/logined/documentType/add.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
<script type="text/javascript" src="${ctx}js/logined/documentType/upload.js"></script>
<c:if test="${adminUser.taoDa==1}">
<script type="text/javascript" src="${ctx}js/logined/publicDom/LodopFuncs.js"></script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>
</object>
</c:if>
<style type="text/css">
.inputTable th { width:100px;}
.inputTable td .formText { width:60%}
</style>
</head>
<body>
<p id="showInfo"></p>
<form action="${ctx}documentType/docTypeSave.action" method="post" id="newForm">
<input type="hidden" name="docType.attachId" id="imgAttachId"/>
<div class="formPage">
<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
<input type="hidden" name="docType.companyId" value="${adminUser.companyId}"/>
  <div class="formbg">
    <div class="big_title">新增公文类型</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><em class="requireField">*</em><label>所属分类：</label></th>
          <td><select valid="required" errmsg="documentType.docCateName_not_null"  name="docType.categoryId">
			      <option value="">--请选择--</option>
					<c:forEach items="${categories}" var="cate">
							<option value="${cate.id}">${cate.name}</option>
						</c:forEach>
				</select>
		  </td>
		  <th><em class="requireField">*</em><label>收发类别：</label></th>
		  <td>
		   	<label class="radio">
				<input type="radio" name="docType.gongwenType"  value="1" checked/>收文
			</label>
			<label class="radio">
				<input type="radio" name="docType.gongwenType"  value="2"/>发文
			</label>
		   </td>
		 </tr>
		 <tr>  			
          <th><em class="requireField">*</em><label>类型名称：</label></th>
          <td colspan="3"><input name="docType.doctypeName" id="docTypeName" maxlength="20"  valid="required" errmsg="documentType.doctypeName_not_null"  type="text" class="formText" /></td>
        </tr>
        <tr>
          <th><span class="requireField">*</span><label>文号表达式：</label></th>
          <td><input name="docType.expr" valid="required" errmsg="documentType.expr_not_null" type="text" class="formText" value="{F}({Y}-{M}-{D} {H}:{I}:{S})"  />
            <a  class="txtindru"  style="cursor: pointer;" onclick="showHide('showTxt')">查看说明</a>
            <div class="122"  id="showTxt" style="display:none"> 文号表达式说明
              <p >表达式中可以使用以下特殊标记</p>
              <p > {Y}：表示年 </p>
              <p >{M}：表示月 </p>
              <p >{D}：表示日 </p>
              <p >{H}：表示时 </p>
              <p >{I}：表示分 </p>
              <p >{S}：表示秒 </p>
              <p >{F}：表示流程名 </p>
              <p >{FS}：表示流程分类名称 </p>
              <p >{U}：表示用户姓名 </p>
              <p >{DE}：表示部门 </p>
              <p >{N}：表示编号，通过编号计数器取值并自动增加计数值 </p>
              <p >例如，表达式为：成建委发[{Y}]{N}号 </p>
              <p >同时，设置自动编号显示长度为4 </p>
              <p >则自动生成的文号如下：成建委发[2006]0001号 </p>
              <p >例如，表达式为：BH{N} </p>
              <p >同时，设置自动编号显示长度为3 </p>
              <p >则自动生成的文号如下：BH001 </p>
              <p >例如，表达式为：{F}流程（{Y}年{M}月{D}日{H}:{I}）{U} </p>
              <p >自动生成文号如：请假流程（2006年01月01日10:30）张三 </p>
              <p >系统默认按以下格式，如： </p>
              <p >请假流程(2006-01-01 10:30:30) </p>
            </div></td>
          <th><span class="requireField">*</span><label>编号计数器：</label></th>
          <td valign="top"><input  maxlength="5" name="docType.beginNum" valid="required|isNumber|range" min="0" errmsg="documentType.beginNum_not_null|documentType.beginNum_int" type="text" class="formText" value="1" /><span class="gray_d ml5">用于表达式编号标记 </span></td>
        </tr>
        <tr>
          <th><span class="requireField">*</span><label>编号位数：</label></th>
          <td><input maxlength="2" style="width:80px;"  name="docType.numLength" valid="required|isNumber|range" errmsg="documentType.numLength_not_null|documentType.numLength_int" min="0" max="8" type="text" class="formText"  value="4"/><span class="gray_d ml5">为0表示按实际编号位数显示</span></td>
          <th><label>文件编号：</label></th>
          <td> <select name="docType.canUpdate" >
		                    	<option value="0">不允许修改</option>
		                    	<option value="1" selected>允许修改</option>
		                    </select></td>
        </tr>
        <tr>
          <th><span class="requireField">*</span><label>表单模版：</label></th>
          <td> <input type="text" id="formCategory" valid="required" errmsg="documentType.formCategory_not_null"   class="formText iconTree" readonly/>
							<input type="hidden"   valid="required" errmsg="documentType.formCategory_not_null"   name="docType.formId"	 id="formCategory_hidden"/>
            <a style="cursor: pointer;" id="prepView">预览表单</a>
          <th><label>套红模板：</label></th>
          <td><select name="docType.docTemplateId"   style="width:15em">
						     <option value="">请选择</option>
							   <c:forEach items="${docTemplates}" var="doct">
									<option value="${doct.docTemplateId}">${doct.docTemplateName}</option>
								</c:forEach>
						</select></td>
		  </td>
						
        </tr>
        <tr>
          <th><label>文字说明：</label></th>
          <td colspan="3"><textarea id="docDesc" maxlength="300" name="docType.docDesc"  class="formTextarea">${docType.docDesc}</textarea></td>
        </tr>
        
        <tr>
          <th><label>是否套打：</label></th>
          <td colspan="3">
          <label class="radio">
				<input type="radio" name="docType.taoDa"  value="1"/>是
			</label>
				<label class="radio">
					<input type="radio" name="docType.taoDa" checked value="2"/>否
				</label>
          </td>
        </tr>
        <tr name="taoDaSetUp">
          <th><label>套打背景图片：</label></th>
          <td colspan="3"> <!-- 上传按钮 -->
					    <span id="showUpload"><input id="file_upload" name="fileupload" type="file" multiple="true"/></span>
					    <input type="hidden" name="moduleName" id="moduleName" value="workflow"/>
					    <!-- 上传队列 -->
					    <div id="queue"></div>
					    <span class="ml20" id="result"></span></td>
        </tr>
        <tr name="taoDaSetUp">
          <th><label>打印设置：</label></th>
          <td colspan="3"><input hideFocus="" class="blueBtn"  value="打印设置" type="button" id="printSetUp"  /></td>
        </tr>
        <tr name="taoDaSetUp">
          <th><span class="requireField">*</span><label>维护代码：</label></th>
          <td colspan="3"><textarea class="formTextarea" valid="required" id="printTemplateCode"  errmsg="documentType.printTemplateCode_not_null"     name="docType.printTemplateCode"  rows="8" >${docType.printTemplateCode}</textarea></td>
        </tr>
      </table>
    </div>
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="保存"  onclick="save();"/>
    <input type="button" class="formButton_grey" value="返回"  onclick="javascript:window.location.href = document.referrer;" />
  </div>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height:300px;"></ul>
</div>
</form>
</body>
</html>