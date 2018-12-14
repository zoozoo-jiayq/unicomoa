<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    int showType = 3;//查找类型 默认按人员选择
			if (request.getParameter("showType") != null) {
				try {
					showType = Integer.parseInt(request
							.getParameter("showType"));
				} catch (Exception ex) {
					showType = 3;
				}
			}
			
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择人员</title>
<jsp:include page="../../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/selectMember/skins/selectMember_default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${ctx}flat/plugins/peopleTree/skins/tree_default.css" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>

<script type="text/javascript" src="${ctx}flat/js/smallTabSelectTree.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
</head>
<body  class="bg_white" >
	<input type="hidden" value="<%=showType%>" id="showType" />
	<input type="hidden" id="defaultSelectId" value='${param.defaultSelectId }' />
	<input type="hidden" id="checkType" value='${param.checkType}' />
 
	<div class="selectMember">
       <input type="text" class="search" placeholder="搜索人员"   id="btnSearch" />
       <div class="member">
	       <div class="tab">
	         <ul>
	           	<li class="current" id="liSelectGroup" ><a  href="javascript:void(0);" id="btnSelectGroup" >部门</a></li>
           		<li  id="liSelectRole" ><a   href="javascript:void(0);"  id="btnSelectRole" >工作角色</a></li>
	           	<li  id="liSelectGroups"><a   href="javascript:void(0);"  id="btnSelectGroups" >群组</a></li>
	         </ul>  
	       </div>
	       <div class="tabContent" >
	          	<ul id="groupUserTree" class="ztree">
				</ul>
	       </div>
	   </div>
	</div>
		<div class="clear"></div>
	</div>
	<script>funPlaceholder(document.getElementById("btnSearch"));</script>
<script type="text/javascript">
	/**
	 * checkType check(默认)多选 radio 单选
	 * type 1 部门 2 角色 3 群组人员 (暂未启用) 4 群组  5 搜索
	 * showType=3 查找类型 默认按人员选择 1 部门 2 角色 3 人员
	 */
	var async = "${param.async}";
	var _selectId = $("#defaultSelectId").val();//默认选中的人的id
	var showType = $("#showType").val();
	var _result = [];
	var checkType = $("#checkType").val()||"check";
	var fnSearch = "";
	$(document).ready(function(){
		art.dialog.data("result",_result);
		/** 标签绑定点击事件 **/
		initButton();
		if(showType == 1){
			$("#btnSearch").hide();
			$("#liSelectRole").hide();
			$("#liSelectGroups").hide();
			$(".tabContent").css("height","330px");
			/** 初始化选择树 **/
		    initTrees(1,_selectId);
		}else if(showType == 2){
			$("#btnSearch").hide();
			$("#liSelectGroup").hide();
			$("#liSelectGroups").hide();
			$("#liSelectRole").addClass("current");
			$(".tabContent").css("height","330px");
			/** 初始化选择树 **/
		    initTrees(2,_selectId);
		}else{
			/** 初始化选择树 **/
		    initTrees(1,_selectId);
		}
		              
			

		/** 搜索人员 **/
		$("#btnSearch").keyup(function(){
			var keyWord = $.trim($("#btnSearch").val());
			if(fnSearch != keyWord){
				fnSearch = keyWord;
			}else{
				return;
			}
			if(!keyWord){
				initTrees(1,_selectId);
			}else{
				$(".tab li").removeClass("current");
				$("#liSelectGroup").addClass("current");
				initTrees(5,_selectId,keyWord);
			}
		});
	});
	
	//初始化选择树
	function initTrees(type,selectIds,keyWord){
		/**
		 * selectId 选中的id
		 * type 1 部门人员 2 角色人员 4 群组人员 5 搜索
	     */
	    var param={};
		    param.id="groupUserTree";
		    param.type = checkType;
		    param.defaultSelectId = selectIds;
		    param.showType = $("#showType").val();
		    param.dataParam = {"type":type,"searchName":keyWord,"showType":$("#showType").val()};
		    param.click = function(data,treeNode){
							    	if(type == 5 && checkType!="radio"){//搜索人员时
										if(treeNode){
											var obj = {};
											obj.id = treeNode.id.substr(4);
											obj.name = treeNode.name;
											if(!treeNode.checked){
												removeObj(_result,obj);
											}else{
												addObj(_result,obj);//返回值
												_selectId = _selectId+","+treeNode.id.substr(4);
											}
										}
										return;
									}
									_result = [];
									_selectId = "";
									if(data&&data.length>0){
										var str = "";
											for(var i=0;i<data.length;i++){
												var node = data[i];
												if(showType == 3){//选择人员
													if(node.id.substr(0,4) == "uid_"){
														str += node.id.substr(4) + ",";
														var obj = {};
															obj.id = node.id.substr(4);
															obj.name = node.name;
														addObj(_result,obj);//返回值
													}
												}else if(showType == 1){
													if(node.id.substr(0,4) == "gid_" && node.id !="gid_0"){
														str += node.id.substr(4) + ",";
														var obj = {};
															obj.id = node.id.substr(4);
															obj.name = node.name;
														addObj(_result,obj);//返回值
													}
												}else if(showType == 2){
													if(node.id.substr(0,4) == "rid_"){
														str += node.id.substr(4) + ",";
														var obj = {};
															obj.id = node.id.substr(4);
															obj.name = node.name;
														addObj(_result,obj);//返回值
													}
												}
											}
										if(str.length>0){
											_selectId = str.substr(0,str.length-1);//修改全局选中的人员的id
										}
									}
								art.dialog.data("result",_result);
							};
			param.loadComplete=function(){
		    	if(type != 5){
			    	_result = [];
			        if ("" != _selectId) {
				        var ids = _selectId.split(",");
				        var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
				        var showType = $("#showType").val();
			
				        for ( var i = 0; i < ids.length; i++) {
					        var node = null;
					        if (showType == 1) {
						        node = treeObj.getNodeByParam("id", "gid_" + ids[i], null);
						        if (null != node) {
						        	var obj = {};
									obj.id = node.id.substr(4);
									obj.name = node.name;
								addObj(_result,obj);//返回值
						        }
					        } else if (showType == 2) {
						        node = treeObj.getNodeByParam("id", "rid_" + ids[i], null);
						        if (null != node) {
						        	var obj = {};
									obj.id = node.id.substr(4);
									obj.name = node.name;
								addObj(_result,obj);//返回值
						        }
					        } else if (showType == 3) {
						        node = treeObj.getNodesByParam("id", "uid_" + ids[i], null);
						        for ( var j in node) {
						        	var obj = {};
										obj.id = node[j].id.substr(4);
										obj.name = node[j].name;
									addObj(_result,obj);//返回值
						        }
					        }
				        }
			        }
			        art.dialog.data("result",_result);
		    	}
		    
			};
			if(type != 5 && async == "async"){//非搜索人员
				param.url=basePath+"/user/defaultSelect.action";
				param.asyncUrl=basePath+"/user/getTreeByNode.action?type=" + type + "&showType=" + showType;
			}
		qytx.app.tree.userCheckOrRadio(param);
	}
	
	//绑定标签点击事件
	function initButton(){
		
		/** 部门 **/
		$("#btnSelectGroup").click(function(){
			$("#btnSearch").val("");
			initTrees(1,_selectId);
		});
		
		/** 角色 **/
		$("#btnSelectRole").click(function(){
			$("#btnSearch").val("");
			initTrees(2,_selectId);
		});
		
		/** 群组 **/
		$("#btnSelectGroups").click(function(){
			$("#btnSearch").val("");
			initTrees(4,_selectId);
		});
	}
	
	
	var addObj = function(arr,obj){
		if(arr){
			$(arr).each(function(i,item){
				if(item.id == obj.id){
					arr.splice(i,1);
				}
			});
		}
		arr.push(obj);
		return arr;
	}
	

	var removeObj = function(arr,obj){
		if(arr){
			$(arr).each(function(i,item){
				if(item.id == obj.id){
					arr.splice(i,1);
				}
			});
		}
		return arr;
	}
    
</script>
</body>
</html>