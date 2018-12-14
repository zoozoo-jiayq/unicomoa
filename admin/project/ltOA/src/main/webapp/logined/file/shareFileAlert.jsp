<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资料管理-文件上传</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/flat/plugins/org/org.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/file/shareFileAlert.js"></script>
<style type="text/css">
.uploadify{margin:7px 0px;}
.inputTable th{width:100px;}
</style>
</head>
<body class="bg_white">
<div class="elasticFrame formPage">
	<form id="form1">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                      <tr>
                            <th><label>发布范围：</label></th>
                            <td colspan="3">
	                            <input type="hidden" id="userIds" name="userIds"/>
								<textarea class="readOnlyTextarea" style="width:80%" rows="5" id="userNames" name="userNames" readonly="readonly"></textarea>
								<span class="addMember">
									<a class="icon_add" href="#" onclick="selectUser();">添加</a>
									<a class="icon_clear" href="#" onclick="clearUser()">清空</a>
								</span>
                            </td>
                      </tr>
                      <tr>
						<th><label>文件：</label></th>
						<td colspan="3">
						    <input type="hidden" id="attachmentId" name="attachmentId"/>
						    <input id="file_upload" name="fileupload" type="file" multiple="true" />
						    <!-- 上传队列 -->
						    <div id="queue"  style="display:none;"></div>
			               <div class="annex">
			                <ul id="attachmentList">
			               	</ul>
			               </div>
			            </td>
			         </tr>
              </tbody>
           </table>
     </form>
</div>
</body>
</html>