<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公文管理--基础设置--公文类型设置</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx }flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/logined/documentType/docTypeList.js"></script>
<script type="text/javascript">
$(function(){
	if($(".service-menu p").length>0){
		$(".service-menu p").eq(0).trigger("click");
	}
});
</script>
</head>
<body>

<div class="mainpage">
<div class="leftMenu">
<div class="service-menu">
   <h1>公文分类</h1>
   		<c:forEach items="${categories }" var="cate">
       <p class="menu-p <c:if test='${cate.select==true }'>on</c:if>" onclick="javascript:selectCate(${cate.dict.id },this)"><i class="menu-i"></i><a href="javascript:void(0);">${cate.dict.name }</a>&nbsp;<em class="f12 grey_9">(${cate.count})</em></p>
       </c:forEach>
  </div>
            </div>
<div class="list">
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          <td class="right">
	<form   onsubmit=" getDataTable(); return false;"  >
            <label>关键字：</label>
            <span class="position:relative;">
            <input type="text" id="doctypeName" class="formText searchkey" placeholder="分类名称" maxlength="30" name="doctypeName"/>
            </span>
            <input type="submit" class="searchButton" value="查询"/>
</form>
            </td>
            <td style="width:93px;"><div class="fButton greenBtn"><span class="add" onclick="add();">新增</span></div></td>
        </tr>
      </tbody>
    </table>
  </div>
  <table class="pretty" id="myTable" cellspacing="0" cellpadding="0">
    <thead>
      <tr >
        <th class="tdCenter num" id="no">序号</th>
        <th style="width:20%;" class="longTxt" id="docType.doctypeName" >公文类型名称</th>
        <th style="width:55%;" class="longTxt" id="docType.docDesc">说明</th>
        <th style="width:60px;" class="data_r" id="count">公文数</th>
        <th class="data_l right_bdr0" style="width:140px;" >操作</th>
      </tr>
    </thead>
			        <tfoot>
			        </tfoot>
			    </table>
</div>
</div>
<input type="hidden" id="categoryId" value="${categoryId }"/>
</body>
<script>funPlaceholder(document.getElementById("doctypeName"));</script>
</html>
