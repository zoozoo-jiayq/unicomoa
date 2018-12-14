<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弹框-考勤设置</title>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/attendance.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
  <script type="text/javascript" src="${ctx}js/logined/js_lang_cn.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/logined/attendance/updateIp.js"></script>
</head>
<body class="formbg">
<div class="elasticFrame formPage">
	<input type="hidden" id="ipSetId" name="ipSetId" value="${param.ipSetId}"/>
    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
      <tbody id="tBody">
        <tr>	
          <th>起始IP：</th>
          <td><div class="inputIP" id="div1"><input name="beginIp1" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass" id="beginIp1" type="text" />.<input name="beginIp2" id="beginIp2" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass" type="text" />.<input name="beginIp3" id="beginIp3" type="text" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass" />.<input name="beginIp4" id="beginIp4" type="text" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass" /></div>
           </td>
        </tr>
         <tr>	
          <th>截止IP：</th>
          <td> 
            <div class="inputIP" id="div2"><input name="endIp1" id="endIp1" type="text" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass"/>.<input name="endIp2" id="endIp2" type="text" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass"/>.<input name="endIp3" id="endIp3" type="text" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass"/>.<input name="endIp4" id="endIp4" type="text" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" class="ipClass"/></div></td>
        </tr>
        
      </tbody>
    </table>
    <input type="button" style="display:none;" id="a" class="formButton" value="保存" onclick="updateIp()" />
  </div>
</div>
</body>
</html>

