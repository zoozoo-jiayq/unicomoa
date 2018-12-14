<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分类列表</title>
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/often.js"></script>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/flat/js/base.js"></script>
<script type="text/javascript" src="${ctx }/flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
</head>
<body>
<input type="hidden" id="type" value="${type}" />
<input type="hidden" id="domType" value="${domType}"/>
<div class="list">
<!--   <div class="pageTitle"><em class="iconList">工作流程列表</em></div>-->
<div class="searchArea">
            <table cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                 <td class="right">
                    <label>关键字：</label>
                    <span style="position:relative;">
                    <input type="text" id="searchkey" class="formText searchkey" placeholder="流程名称" value="${searchkey }">
                    </span>
                    <input type="button" class="searchButton" value="查询" id="search">
                </td>
                </tr>
              </tbody>
            </table>
          </div>

<table cellpadding="0" cellspacing="0"  class="pretty dataTable">
<thead>
<tr>
<th class="num">序号</th><th width="40%;">流程名称</th><th width="60%;">流程说明</th><th style="width:100px;"  class="right_bdr0">操作</th>
</tr>
</thead>
<tbody>
<c:if test="${empty processAttributeList}">
<tr>
<td colspan="4">
没有检索到数据!
</td>
</tr>
</c:if>
<s:iterator value="processAttributeList"  id="stuts"   status="status"> 
			   <s:if test="#status.odd == true">
			   	<tr class="even">
			   </s:if>
			   <s:else>
			   	<tr class="odd">
			   </s:else>
			    <td  >
                    <s:property value="#status.index+1"   /> 
				</td>
				<td class="longTxt">
                    <a href="#" onclick="apply(<s:property value="#stuts.id"/>);">	<s:property value="#stuts.processName"/></a>

				</td>
				<td class="longTxt" title='<s:property value="#stuts.directions"/>'>
                    <s:property value="#stuts.directions"/>
				 </td>
				<td class="right_bdr0 " >
                    <a   style="cursor:pointer;"  onclick="apply( <s:property value="#stuts.id"/>);" >申&nbsp;请</a>
                    <a   style="cursor:pointer;"  onclick="viewDetail( <s:property value="#stuts.id"/>);" >查&nbsp;看</a>
				</td>
			</tr>
		 </s:iterator>
</tbody>
</table>
</div>
<script type="text/javascript">funPlaceholder(document.getElementById("searchkey"));</script>
<script type="text/javascript">
	function viewDetail(processId){
		window.parent.location.href=basePath+"jbpmflow/detailSearch!processDefineDetail.action?processId="+processId;
	}
	$("#search").click(function(){
		window.location.href=basePath+"jbpmflow/listSearch!categorylist.action?categoryId=${categoryId}&type=${type}&searchkey="+$("#searchkey").val();
	});
	 //绑定回车事件
    $("body").keydown(function(){
        if(event.keyCode == 13){
            $("#search").click();
        }
        
    });
</script>
</body>
</html>