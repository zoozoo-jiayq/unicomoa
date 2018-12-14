<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开讲设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/d_index.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}js/logined/speak/speakerSet.js"></script>
</head>
<body>
<div class="nav">
	<div class="navLeft">基础设置<span>></span>开讲设置</div>
</div>
<div class="list">
		<div class="searchArea">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<!-- <td class="right">
				              	<label>开奖人：</label>
				              	<span style="position:relative;">
					            	<input name="" class="formText iconTree" type="text" />
					            </span>
				            </td> -->
			            <td class="fr" style="width:92px;">
						  	<div class="fButton greenBtn">
						    	<span class="add" onclick="addSpeaker()">添加</span>
						  	</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<table id="myTable" width="100%" class="pretty" cellspacing="0" cellpadding="0">
				<thead>
					<tr role="row">
						<th class="num" >序号</th>
						<th style="width:20%;" >姓名</th>
						<th style="width:30%;" >部门</th>
						<th style="width:30%;" >职务</th>
						<th style="width:20%">创建时间</th>
						<th class="right_bdr0" style="width:90px;" >操作</th>
					</tr>
				</thead>
				<tbody>
					<s:if test="#request.list.size()>0">
						<s:iterator value="#request.list" id="list" status="s">
							<tr <s:if test="#s.Odd">class="odd"</s:if><s:else>class="even"</s:else>>
								<td><s:property value="#s.index+1"/></td>
								<td><s:property value="#list.userName"/></td>
								<td class="longTxt"><s:property value="#list.groupName"/></td>
								<td class="longTxt"><s:property value="#list.job"/></td>
								<td><s:property value="#list.createTime"/></td>
								<td class="right_bdr0"><a href="javascript:void(0);" onclick="deleteSpeaker('<s:property value="#list.id"/>')">删除</a></td>
							</tr>
						</s:iterator>
					</s:if>
				</tbody>
		</table>
</div>
</body>
</html>
