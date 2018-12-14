<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看流程</title>
<jsp:include page="../../../common/flatHead.jsp"></jsp:include>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="formPage ">
	<div class="formbg">
		<h1 class="big_title">查看流程</h1>
		<div class="content_form">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
				<tbody>
			          <tr>
	                    <th><label>流程说明：</label></th>
	                    <td>
	                    	<s:if test="%{processAttribute.directions!=null && processAttribute.directions!=''}">
					        	<s:property value="processAttribute.directions" escape="false"/>
					        </s:if>
					        <s:if test="%{processAttribute.directions==null || processAttribute.directions == ''}">
								无
					        </s:if>
	                    </td>
	                  </tr>
					   <tr>
	                    <td colspan="2"><iframe width="100%" height="500" src="${ctx}workflow/jpdl!view.action?processAttributeId=<%=request.getAttribute("processId") %>"></iframe></td>
	                  </tr>
				</tbody>
			</table>
    </div>
  </div>  
  <div class="buttonArea"> 
			<input type="button" value="返  回" class="formButton_grey" onclick="rollback();"/><span class="blue">点击返回，可以回到列表，查看更多申请。</span>
			
  </div> 
</div>
<script type="text/javascript">
	function rollback(){
		window.location.href=basePath+"jbpmflow/listSearch!often.action";
	}
</script>
</body>
</html>