<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../common/flatHead.jsp" />
<title>人员列表</title>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/groupAdd.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/userTree.js"></script>
<!-- 人员选择  start-->
<script type="text/javascript" src="${ctx}js/logined/group/selectUser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/selectGroup.js"></script>
<style type="text/css">
.inputTable th{width:108px;}
</style>
</head>
<body>
<input type="hidden" id="directorId"/>
<input type="hidden" id="assistantId"/>
<input type="hidden" id="topDirectorId"/>
<input type="hidden" id="topChangeId"/>
<input type="hidden" id="parentId" value="0"/>
		<div class="formPage">
  			<div class="formbg">
				<div class="big_title">新增部门</div>
    			<div class="content_form">
	
			<form action="#" id="groupForm">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputTable">
					<tr>
						<th><em class="requireField">*</em><label>部门名称：</label>
						</th>
						<td><input name="" type="text" class="formText" maxlength="25" id="groupName" 
						      valid="required" errmsg="group.group_name_not_null"/>
						</td>
						<th><em class="requireField">*</em><label>电话：</label>
						</th>
						<td><input name="" type="text" class="formText" maxlength="12" id="groupPhone" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"  valid="required|isPhone" errmsg="group.group_phone_not_null|group.group_phone_format_error"/>
						</td>
					</tr>
					<tr>
						<th><label>上级部门：</label>
						</th>
						<td>
							<div id="treeContent" style="z-index:66;position:relative">
								<input id="groupSel" type="text" readonly="readonly" class="formText iconTree" />
								<!-- <a class="icon_clear" href="#" id="parentRemove">清空</a> 
								<span class="selectdot" id="groupSel_div"></span> -->
								<div id="menuContent" style="position: absolute;display: none;">
									<ul id="groupTree" class="ztree" style="position: absolute; margin-top: 0; width: 357px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
								</div>
							</div>
						</td>
						<th><em class="requireField">*</em><label>部门排序号：</label>
						</th>
						<td>
						<input name="input2" type="text" class="formText"  maxlength="3" id="groupOrder" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" valid="required|isNumber" errmsg="group.group_order_not_null|group.group_order_format_error"/>
						</td>
					</tr>
					<tr>
						<th><label>部门主管：</label>
						</th>
						<td><input type="text" class="readOnlyText blur-not-disabled"  id="director" style="width:245px;"/><span class="addMember"><a
							class="icon_add" href="#" id="directorSelect">选择</a></span>
						</td>
						<th><label>部门助理：</label>
						</th>
						<td><input type="text" class="readOnlyText blur-not-disabled"   id="assistant" style="width:245px;"/><span class="addMember"><a
							class="icon_add" href="#" id="assistantSelect">选择</a></span>
						</td>
					</tr>
					<tr>
						<th  style="width:200px;"><label>上级主管领导：</label>
						</th>
						<td><input type="text" class="readOnlyText blur-not-disabled"   id="topDirector" style="width:245px;"/><span class="addMember"><a
							class="icon_add" href="#" id="topDirectorSelect">选择</a></span>
						</td>
						<th><label>上级分管领导：</label>
						</th>
						<td><input type="text" class="readOnlyText blur-not-disabled"   id="topChange" style="width:245px;"/><span class="addMember"><a
							class="icon_add" href="#" id="topChangeSelect">选择</a></span>
						</td>
					</tr>
					<tr style="display:none">
						<th  style="width:200px;"><label>是否分支机构：</label>
						</th>
						<td colspan="3">
							<label class="radio">
                       			<input type="radio" value="0" checked name="isForkGroup"/>否
                    		</label>
                    		<label class="radio">
                       			<input type="radio" value="1" name="isForkGroup"/>是
                    		</label>
						</td>
					</tr>
					<tr>
						<th>部门职能：</th>
						<td colspan="3">
							<textarea class="formTextarea"  id="functions" ></textarea>
						</td>
					</tr>
				</table>				
			</form>
			</div>
			</div>
			<div class="buttonArea">
					<input value="保 存" type="button" class="formButton_green" id="groupAdd"/><span class="blue">点击保存，提交部门信息，*标记为必填项</span>					
			</div>		
</div>
</body>
</html>