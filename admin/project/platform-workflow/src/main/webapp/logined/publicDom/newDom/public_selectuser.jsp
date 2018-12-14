<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="publicDomHead.jsp"></jsp:include>
    <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
    <script type="text/javascript" src="${ctx}js/user/select_document_user.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OA办公系统-选择人员</title>
</head>
<body>
<div class="doc_wrap">

<div class="contentTitle">${vars.title}</div>
	
<div class="input_new">
<form method="post" >
<input type="hidden" name="action" value="${action}"/>
<input type="hidden" name="taskId" value="${taskId}"/>
<input type="hidden" name="menu" value="${menu}"/>
<input type="hidden" id="nextUser" name="nextUser" value="" />
</form>
<%String action = request.getParameter("action"); %>
<%if(!action.equals("转分发") && !action.equals("转阅读")){ %>
<table width="100%" class="inputTable" border="0" cellSpacing="0" cellPadding="0">
	<tr>
		<th style="width:80px;">选择人员</th>
		<td style="width:100%;"><input name="nextUserName" style="width:95%" readonly type="text" class="formText"/>&nbsp;&nbsp;</td>
		<td style="width:150px;"><a class="icon_add" href="#" id="selectUser">选择</a><a  class="icon_clear" href="#" id="cleanButton">清空</a></td>
</tr></table>
<%}else if(action.equals("转分发")){ %>
<table class="inputTable" border="0" cellSpacing="0" cellPadding="0">
	<tr>
		<th >
	选择发送部门</th><td><TEXTAREA style="WIDTH: 70%" name="nextUserName" class="readOnlyText"  readOnly name="emailBody.toName"></TEXTAREA>&nbsp;&nbsp;<span class="addMember"><a class="icon_add" href="#" id="selectUser">添加</a><a  class="icon_clear" href="#" id="cleanButton">清空</a></span></td>
</tr>
</table>
<%}else if(action.equals("转阅读")){ %>
<table width="100%" class="inputTable" border="0" cellSpacing="0" cellPadding="0">
	<tr>
		<th >
	选择传阅人员</th><td><TEXTAREA style="WIDTH: 70%" name="nextUserName" class="readOnlyText"  readOnly name="emailBody.toName"></TEXTAREA>&nbsp;&nbsp;<span class="addMember"><a class="icon_add" href="#" id="selectUser">添加</a><a  class="icon_clear" href="#" id="cleanButton">清空</a></span></td>
</tr>
</table>
<% }%>
	<div class="buttonArea">
   <input hideFocus="" class="formButton_green" id="sureButton" value="提 交" type="button">
    <input hideFocus=""class="formButton_grey" value="返 回" type="button" onclick="goback();">
    </div>
		<div class="pageTitle"><em class="iconList" style="padding-left:26px">历史记录</em></div>
		<table cellpadding="0" cellspacing="0"  class="pretty dataTable">
			<thead>
				<tr>
					<th  class="num">序号</th>
					<th style="width:140px;">时间</th>
					<th style="width:100px">事件</th>
					<th style="width:70px">姓名</th>
					<th style="width:90px">手机号码</th>
					<!-- <th style="width:100px">角色</th> -->
					<th style="width:100px">单位/部门</th>
					<!-- <th style="width:100%">审批意见/备注</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${records }" var="record" varStatus="idx">
				<tr <c:if test="${idx.index%2 == 0}">class="even"</c:if> <c:if test="${idx.index%2 != 0}">class="odd"</c:if> >
					<td>${idx.index+1}</td>
					<td>${record.approveTime}</td>
					<td>${record.option}</td>
					<td>${record.userName}</td>
					<td>${record.phone}</td>
				<!-- 	<td>${record.role}</td> -->
					<td>${record.group}</td>
					<!-- <td>${record.content}<br/>${record.sign}</td> -->
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
	</div>
	</body>
	<script type="text/javascript">
         
		//选择人员 --------- begin
		$("#selectUser").click(function(){
		var defaultSelectId = $("#nextUser").val();
			var flag = 0;
			var isAll = "";
			var action = $("input[name='action']").val();
			if(action == '转阅读'){
				flag = 1;
			}
			var type = 3;
			if($("input[name='action']").val() == '转分发'){
				type = 1;
				flag = 1;
				//发文分发选择全体部门，不区分二级局
				isAll = "dispatch";
			}
			openDocSelectUser(type,function(data){
				if(data!=undefined)
	            {
	            $("textarea[name='nextUserName']").html("");
				$("input[name='nextUser']").val("");
	                data.forEach(function(value, key) {
	                    //具体参数查看 treeNode.js
                    	if(flag == 0){
	                    	setUser.setOneUser(value.Id,value.Name);
                    	}else if(flag == 1){
                    		setUser.setManyUser(value.Id,value.Name);
                    	}
	                });
	            }
	            else
	            {
	                alert("请选择人员");
	            }
			},flag,"",defaultSelectId,isAll);
		});

		//设置用户
		var setUser = {
			setOneUser:function(id,name){
				$("input[name='nextUserName']").val(name);
				$("input[name='nextUser']").val(id);
			},
			setManyUser:function (id,name) {
				// body...
				var userNames = $("textarea[name='nextUserName']").html();
            	$("textarea[name='nextUserName']").html(userNames+name+",");
            	var ids = $("input[name='nextUser']").val();
            	$("input[name='nextUser']").val(ids+id+",");
			}
		};

		//清空
		$("#cleanButton").click(function(){
        	$("input[name='nextUserName']").val("");
        	$("input[name='nextUser']").val("");
        	$("textarea[name='nextUserName']").html("");
		});

		//提交
		$("#sureButton").click(function(){
			var userId = $("input[name='nextUser']").val();
			if(!userId){
				art.dialog.alert("请选择人员!");
			}else{
				$("body").lock();
				var action = $("input[name='action']").val();
				var taskId = $("input[name='taskId']").val();
				var nextUser = $("input[name='nextUser']").val();
				$.post(basePath+"/dom/public!completeTask.action",{
					"action":encodeURI(action),
					"taskId":taskId,
					"nextUser":nextUser
				},function(data) {
					// body...
					var msg = "";
					var menu = $("input[name='menu']").val();
					menu = menu*1;
					switch(menu){
						case 0:;
						case 1:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“收文记录-已登记”中查看该记录。</span>";break;
						case 2:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“领导批阅-已批阅”中查看该记录。</span>";break;
						case 3:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“收文分发-已分发”中查看该记录。</span>";break;
						case 4:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“收文阅读-已阅读”中查看该记录。</span>";break;
						case 5:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“拟稿记录-已提交”中查看该记录。</span>";break;
						case 6:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“发文核稿-已核稿”中查看该记录。</span>";break;
						case 7:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“套红盖章-已盖章”中查看该记录。</span>";break;
						case 8:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“发文分发-已发文”中查看该记录。</span>";break;
						case 9:
						case 10:msg = "<font class='f20b'>操作成功！<font><br><span class='gray_6'>您可以在“归档查询”中查看该记录。</span>";break;
					}
					if(data == "success"){
						$("body").unlock();
						art.dialog({
						title: '提示',
						content: msg,
						icon: 'succeed',
						ok: function(){
							var moduleCode = "";
							switch(menu){
								case 1:moduleCode = "101200";break;
								case 2:moduleCode = "101300";break;
								case 3:moduleCode = "101400";break;
								case 4:moduleCode = "101500";break;
								case 5:moduleCode = "102200";break;
								case 6:moduleCode = "102300";break;
								case 7:moduleCode = "102400";break;
								case 8:moduleCode = "102500";break;
								case 9:
								case 10:moduleCode = "103100";break;
							}
							
							try{
								window.opener.location.reload(); 
							}catch(e){
								
							}
							var obj = window.parent.document.getElementById("div_tab");
							if(obj){
								window.parent.closeCurrentTab();
							}else{
								window.close();
							}
						}
					});
					}
				});
			}
		});
		//选择人员-----------end
		jQuery(document).ready(function($){
		(function(){
			var menu = $("input[name='menu']").val();
			if(menu == 1 || menu == 2 ||menu == 3||menu == 4 || menu == 9){
				$("#sourceSpan").show();
			}
		})();

		});

		//返回
		function goback(){
			var taskId = $("input[name='taskId']").val();
			window.location.href=basePath+'dom/public!toMainByForward.action?taskId='+taskId;
		}
	</script>

</html>