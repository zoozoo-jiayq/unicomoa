<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公文管理</title>
<jsp:include page="publicDomHead.jsp" />

</head>
<body>
<input type="hidden" id="menu" value="5"/>
	<input type="hidden" id="searchType" value="processing"/>
<div class="list">
	<!-- <ul class="tab" style="margin-bottom:10px">
		<li class="current"><a href="#" >未提交</a> </li>
		<li><a href="${ctx }/logined/publicDom/newDom/dispatch_writeDoc_complete.jsp" >已提交</a> </li>
	</ul> -->
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
          
          <td class="right">
          	<label>关键字：</label>
          	<span class="position:relative;">
            <input type="text" id="title" class="formText searchkey" placeholder="标题/文号/公文类型"/>
            </span>
            <input type="button" class="searchButton" value="查询" id="searchButton"/></td>
             <td style="width:120px;"><div class="fButton greenBtn"> <span class="add" onclick="add();">新增发文</span> </div></td>
        </tr>
      </tbody>
    </table>
  </div>
	<table cellpadding="0" cellspacing="0" id="viewlist"  class="pretty dataTable">
		<thead>
			<tr>
				<!-- <th class="chk"  nowrap="nowrap"><input id="selectAll" type="checkbox" value="" /></th> -->
				<th nowrap="nowrap">标题</th>
				<th nowrap="nowrap" >文号</th>
				<th nowrap="nowrap">公文类型</th>
				<th nowrap="nowrap">密级</th>
				<th nowrap="nowrap">缓急</th>
				<th nowrap="nowrap">拟稿时间</th>
				<th nowrap="nowrap">操作</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
</div>
<script type="text/javascript">funPlaceholder(document.getElementById("title"));</script>
<script type="text/javascript" src="${ctx}js/logined/publicDom/search.js"></script>
<script  type="text/javascript">
function add(){
	window.top.addTab(Math.random(),"${ctx}dom/dispatch!toAdd.action","新增发文");
}
</script>
</body>
</html>