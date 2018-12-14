<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>" />
    <jsp:include page="../../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
	<div class="list">
   <table cellpadding="0" cellspacing="0"  class="pretty dataTable">
				<thead>
						<tr>
								<th class="num">序号</th>
								<th style="width:30%">审批时间</th>
								<th nowrap="nowrap">步骤</th>
								<th nowrap="nowrap">姓名</th>
								<th nowrap="nowrap">单位/部门</th>
								<th style="width: 10%">审批结果</th>
								<th nowrap="nowrap">审批意见</th>
						</tr>
				</thead>
                <tbody>
                   <s:iterator value="adviceHistoryList"  id="advice" var="ad"  status="status">
                                <s:set id="total" value="advice.value.size"/>
				                        <s:if test="#status.odd == true">
				                       			 <tr class="even" >
				                        </s:if><s:else>
				                       			 <tr class="odd" >
				                        </s:else>
		                                <td>   <s:property value="#status.index+1" /></td>
		                                <td><s:date name="#ad.createTime" format="yyyy-MM-dd  HH:mm"/> </td>
		                                <td> 
		                                 
		                                  <s:property value="#ad.taskName"/>
		                                </td>
		                                <td> <s:property value="#ad.userName"/></td>
		                                <td class="longTxt" title="<s:property value="#ad.groupName"/>"><s:property value="#ad.groupName"/></td>
		                                <td > <s:property value="#ad.result"/></td>
		                                <td class="longTxt" title="<s:property value="#ad.content"/>"><s:if test="#ad.result=='提交申请'">
		                                     <font color="green"><s:property value="#ad.content"/></font>
		                                  </s:if>
		                                  <s:elseif test="#ad.result=='不同意'">
		                                   			<font color="red"><s:property value="#ad.content"/></font>
		                                  </s:elseif>
		                                   <s:elseif test="#ad.result=='挂起'">
		                                   			<font color="#CC9933"><s:property value="#ad.content"/></font>
		                                  </s:elseif>
		                                    <s:elseif test="#ad.result=='同意'">
		                                   			<font color="green"><s:property value="#ad.content"/></font>
		                                  </s:elseif>
		                                  <s:elseif test="#ad.result=='撤销'">
		                                   			<font color="red"><s:property value="#ad.content"/></font>
		                                  </s:elseif>
		                                  <s:else>
		                                  		<s:property value="#ad.content"/>
		                                  </s:else></td>
		                        </tr>
                     </s:iterator>
			</tbody>
			</table>
</div>
  </body>
</html>
