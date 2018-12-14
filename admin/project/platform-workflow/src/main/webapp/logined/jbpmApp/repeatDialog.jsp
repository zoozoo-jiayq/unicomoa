<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
    <title>撤销流程</title>
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
</head>
    <body class="bg_white">
<div class="elasticFrame formPage">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr class="firstEmpty">
                            <th></th>
                            <td></td>
                            <th></th>
                            <td></td>
                      </tr>
                      <tr>
                            <th><label>撤销原因：</label></th>
                            <td colspan="3"><textarea id="advice" class="formTextarea" onKeyUp="if(this.value.length > 50) this.value=this.value.substr(0,50)" rows="5"></textarea></td>
                      </tr>
                 </tbody>   
           </table>
</div>
</body>
</html>
