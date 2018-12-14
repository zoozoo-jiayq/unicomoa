<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<title>查看流程</title>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/jbpmApp/common.js"></script>
<style>
  .inputTable th{ width:45px;}
</style>
</head>
<body class="formbg">
<input type="hidden" id="processInstanceId" value="${processInstanceId }"/>
<div class="elasticFrame formPage">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
								<tbody>
                                        
                                        <tr>
												<th><label>名称：</label></th>
												<td><s:property value="#request.processName"/></td>
										</tr>
                                        <tr>
												<th><label>流程：</label></th>
												<td>
				<jsp:include page="processHistoryList.jsp" />
												</td>
										</tr>
                                     </tbody>
                                   </table>     
</div>

</body>
</html>
