<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>权限设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/secret/secretAdd.js"></script>
</head>
<body>
	 <div class="formPage">
	  <div class="formbg">
	    <div class="big_title">新增保密权限设置</div>
	    <div class="content_form">
	             <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
	                <tr>
	                  <th><label>保密字段：</label></th>
	                  <td>
	                  		<select id="select">
	                  	
	                  		</select>
	                  </td>
	                </tr>
	                <tr>
	                    <th><label>控制范围：</label></th>
	                    <td><textarea readonly="readonly" rows="3" class="readOnlyTextarea" id="applyUserNames"></textarea>
	                    	<span class="addMember">
	                    		<a onclick="addApplyUser();" href="javascript:void(0);" class="icon_add">添加</a>
	                    		<a onclick="delApplyUser();" href="javascript:void(0);" class="icon_clear">清空</a>
	                  	  	</span>
	                  	  	<input type="hidden" id="applyUserIds" />
	                    </td>
	                </tr>
	                <tr>
	                    <th><label>不可查看人员：</label></th>
	                    <td><textarea readonly="readonly" rows="3" class="readOnlyTextarea" id="invisibleUserNames"></textarea>
	                    	<span class="addMember">
	                    		<a onclick="addInvisibleUser();" href="javascript:void(0);" class="icon_add">添加</a>
	                    		<a onclick="delInvisibleUser();" href="javascript:void(0);" class="icon_clear">清空</a>
	                  	  	</span>
	                  	  	<input type="hidden" id="invisibleUserIds" />
						</td>
	                </tr>
	             </table>
	         </div>
	    </div>
	    <div class="buttonArea">
	        <input type="button" onclick="javascript:save();" hidefocus="" class="formButton_green" value="保存"/>
	        <input type="button" onclick="javascript:history.back();" hidefocus="" class="formButton_grey" value="返回"/>
	    </div>
	  </div>  
	</div>
</body>
</html>