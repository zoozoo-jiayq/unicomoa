<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>共享联系人</title>
<%@ include file="../../common/taglibs.jsp" %>
<jsp:include page="../../common/head.jsp"/>
<script type="text/javascript">
    var basePath = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/user/user.js"></script>
<script type="text/javascript" src="${ctx}js/logined/address/share_address.js"></script>
<script type="text/javascript" src="${ctx}js/logined/affairs/affairs_check.js"></script>
<style type="text/css">
.tab{background: none; margin-top: 0;}
.tab li{height: auto; background: none; font-weight: bold;}
.tab li a{color: #0647a8; font-weight: bold;}
ul.tab li.current{background: none;}
ul.tab li.current a {color: #333333;font-weight: bold;background:none;}
.tabContent{min-height: 331px;}
.tabContent p {line-height: 26px;margin: 5px 15px; padding: 0 10px;}
</style>
</head>
<body> 
<input id="addressId" name="addressId" type="hidden"  value='<s:property value="#request.address.id" />' />
<div class="list" style="margin-top: 3px;">
    <table cellpadding="0" cellspacing="0" class="BlockTop">
        <tr>
            <td class="left"></td>
            <td class="center">详情</td>
            <td class="right"></td>
        </tr>
    </table>
    <div class="TableBox">
        	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputTable" >
            	<tr>
                	<th>共享时间</th><td><input id="startDate" name="startDate" type="text" class="Wdate formText" size="20"
								onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
								value="<s:date name="#request.address.startShareTime" format="yyyy-MM-dd HH:mm:ss"/>" /> - 
<input id="endDate" name="startDate" type="text" class="Wdate formText"
								onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" size="20"
								value="<s:date name="#request.address.endShareTime" format="yyyy-MM-dd HH:mm:ss"/>"/> <br /><em class="gray_9">如果开始时间为空，视为立即开始共享；如果结束时间为空，视为永久共享！</em></td>
                </tr>
            	<tr>
                	<th>共享范围</th><td>
                	<input id="shareToUserIds" name="shareToUserIds" type="hidden"  value='<s:property value="#request.address.shareToUserIds" />' />
                	<textarea id="shareToUserNames" class="formTextarea" rows="4" readonly="readonly" cols="66"><s:property value="#request.address.shareToUserNames" /></textarea>
                	<a class="icon_add" href="javascript:void(0);" onclick="selectPerson()">添加</a>
                	<a  class="icon_clear" href="javascript:void(0);" onclick="clearPerson()">清空</a><br />
                	<label id="affairsLable" class="radio" style="display: none;"><input id="affairsSign" type="checkbox" />是否向共享人员发送事务提醒</label></td>
                </tr>
            	<tr>
                	<th>修改权限</th><td>
                	<input id="allowUpdateUserIds" name="allowUpdateUserIds" type="hidden"   value='<s:property value="#request.address.allowUpdateUserIds" />' />
                	<textarea id="allowUpdateUserNames" class="formTextarea" rows="4" readonly="readonly" cols="66"><s:property value="#request.address.allowUpdateUserNames" /></textarea>
                	<a class="icon_add" href="javascript:void(0);" onclick="selectAllowUpdatePerson()">添加</a>
                	<a  class="icon_clear" href="javascript:void(0);" onclick="clearAllowUpdatePerson()">清空</a></td>
                </tr>
            </table>
         <div class="buttonArea"><input id="add" hideFocus="" value="确 定" type="submit" class="formButton" />&nbsp;&nbsp;<input hideFocus="" value="返 回" onclick="javascript:window.location.href = document.referrer;" type="button" class="formButton" />
          </div>
    </div>
</div>
</body>
</html>