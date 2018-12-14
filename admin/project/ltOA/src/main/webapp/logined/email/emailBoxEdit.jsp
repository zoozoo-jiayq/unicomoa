<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>编辑邮件箱</title>
     <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th{width:68px;}
</style>
</head>

<body>
<body class="bg_white">
<div class="elasticFrame formPage">
	<input type="hidden" name="emailBox.id" id="emailBoxId" value="${emailBox.id}"/>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr>
                            <th><label>序号：</label></th>
                            <td>
								<input name="emailBox.orderNo"  value="${emailBox.orderNo}" id="orderNo" type="text" class="formText numberKeyUp" size="30" maxlength="3"
			                    onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"  onblur="this.value=this.value.replace(/[^0-9]/g,'')"
			                    valid="isNumber|range" errmsg="email.email_box_order_no_must_be_number|email.email_box_order_no_must_over_zero" min="0"/>
		                    </td>
                      </tr>
                      <tr>
                            <th><em class="requireField">*</em><label>名称：</label></th>
                            <td>
                            	<input name="emailBox.boxName" id="boxName" value="${emailBox.boxName}" type="text" class="formText" size="30"
		                           maxlength="25" valid="required" errmsg="email.email_box_name_not_null"/>
                            </td>
                      </tr>
              </tbody>
           </table>
</div>



    <script type="text/javascript" src="${ctx}/js/logined/email/email_box_common.js"></script>
</body>
</html>
