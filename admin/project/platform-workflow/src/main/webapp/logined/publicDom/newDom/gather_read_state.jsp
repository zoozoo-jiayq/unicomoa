<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>传阅状态</title>
<jsp:include page="publicDomHead.jsp" />
<link href="${ctx }/flat/plugins/infoDetail/skins/infoDetail_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />

</head>
<body>
    <!--  -->
    <div class="detailsPage">
  <div class="detailbg">
    <h2 id="subject">${wenhaoForReadState }</h2>
    <div class="mt10">
      <h4 class="annex_title">阅读记录：<font>应读${readStateCount.total}人，已读${readStateCount.readComplete}人，未读${readStateCount.reading}人</font></h4>
      <table cellpadding="0" cellspacing="0"  class="pretty mt10">
        <thead>
          <tr>
            <th class="num">序号</th>
            <th style="width:100px;">姓名</th>
            <th style="width:100%;">单位/部门</th>
            <th style="width:100px;">状态</th>
            <th class="right_bdr0" style="width:140px;">阅读时间</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${readStateViews}" var="view" varStatus="idx">
			<tr <c:if test="${idx.index%2 == 0}">class="even"</c:if> <c:if test="${idx.index%2 != 0}">class="odd"</c:if> >
				<td>${idx.index+1}</td>
				<td>${view.userName}</td>
				<td class="data_l">${view.groupName}</td>
				<td>${view.state}</td>
				<td>${view.readTime}</td>
			</tr>
			</c:forEach>
     
        </tbody>
      </table>
    </div>
  </div>
      <div class="buttonArea">
<input type="button" value="返回" class="formButton_grey" onclick="javascript:window.top.top.closeCurrentTab();"/>
<span class="blue">点击返回，回到收文分发列表。</span>

</div>
</div>
    
    
</body>
</html>