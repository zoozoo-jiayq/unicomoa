var _sysTypeId;
var _infoType;
var _value;
$(document).ready(function() {
	getSysTypeList();
	// 添加
	$("#addType").live("click", function() {
				addTypeOpen(1);
			});
	$("#addSysType").live("click", function() {
				addTypeOpen(-1);
			});

	// 删除
	$("#deleteType").live("click", function() {
				deleteType();
			});
	$("#deleteSysType").live("click", function() {
				deleteSysType();
			});

	// 修改
//	$("#updateType").live("click", function() {
//				updateTypeOpen();
//			});
//	$("#updateSysType").live("click", function() {
//				updateSysTypeOpen();
//			});
	// 修改
	$("#search").live("click", function() {
				searchType();
    });

	// 头部全选复选框
	$(".dataTable").delegate("#total", "click", function(event) {
				checkTotal();
				event.stopPropagation();
	 });

	// 子项复选框按钮
	$("#types").delegate(":checkbox[name='typeList']", "click",
			function(event) {
				checkChange();
				event.stopPropagation();
	 });
});

/**
 * 头部全选记录
 */
function checkTotal() {
	var isTotalCheck = $("input:checkbox[id='total']").prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='typeList']").prop("checked", function(i, val) {
					checkNum = checkNum + 1;
					return true;
				});
	} else {
		$("input:checkbox[name='typeList']").prop("checked", false);
	}
}

/**
 * 根据右侧 类型 加载其子节点
 * 
 * @return
 */
function getSysTypeList() {
	var sysTagItem = $("#sysTagItem").val();
	var url = basePath + "dict/getSysDicts.action";
	url =url+ "?sysTagItem=" + sysTagItem;
	$.ajax({
				url : url,
				type : "post",
				dataType : "html",
				success : function(data) {
					jsonData = eval("(" + data + ")");
					var sid=$("#sid").val();
					if(sid==""){sid = jsonData[0].id;}
					$("#sysTypeList").html(" ");
					var html = "";
					 
					for (var i = 0; i < jsonData.length; i++) {
						if(sid!=""&&sid==jsonData[i].id){
						    $("#infoType").val(jsonData[i].infoType);
							// 获取第一个维表数据
							getTypeList();
							html += "<p class='menu-p' onclick='set(\""
									+ jsonData[i].infoType + "\",\""
									+ jsonData[i].id + "\",\""
									+ jsonData[i].value + "\",this);'  > <i class='menu-i on'  id='"
									+ jsonData[i].infoType + "'></i><a href='javascript:void(0);' >"
									+ jsonData[i].name + "</a></p>";
							$("#SysTypeId").val(jsonData[i].id);
							$("#SystypeValue").val(jsonData[i].value);
							  _sysTypeId=jsonData[i].id;
							  _infoType=jsonData[i].infoType;
							  _value=jsonData[i].value;
						}
						else{
							html += "<p class='menu-p'  onclick='set(\""
									+ jsonData[i].infoType + "\",\""
									+ jsonData[i].id + "\",\""
									+ jsonData[i].value + "\",this);' > <i class=\"menu-i\"  id='"
									+ jsonData[i].infoType + "'></i><a href='javascript:void(0);'  >"
									+ jsonData[i].name + "</a></p>";
						}
					}
					$("#sysTypeList").append(html);
				}
			});
}

/**
 * 根据右侧 类型 加载其子节点
 * 
 * @return
 */
function getTypeList() {
	$("input:checkbox[id='total']").prop("checked", false);
	var sysTag = $("#sysTag").val();
	var infoType = $("#infoType").val();
	var paramData = {
		'infoType' : infoType,
		'sysTag' : sysTag
	};
	$.ajax({
		url : basePath + "dict/getDicts.action",
		type : "post",
		dataType : "html",
		data : paramData,
		success : function(data) {
			jsonData = eval("(" + data + ")");
			$("#types").html(" ");
			var html = "";
			var tdClass="";
			for (var i = 0; i < jsonData.length; i++) {
				if(i%2==0){
					tdClass="odd";
				}else{
					tdClass="even";
				}
				var vorder=jsonData[i].infoOrder;
				html += "<tr class=\""+tdClass+"\"   id='"+jsonData[i].id+"' value='"+jsonData[i].value+"'  >";
				html += "  <td><input type=\"checkbox\"   name='typeList'  maxlength='8' id='"+ jsonData[i].id+ "' value='"+ jsonData[i].name+ "'  /></td>";
				html += "   <td>"+(i+1)+"</td>";
				html += "   <td class=\"data_l\">"+jsonData[i].name+"</td>";
				html += "   <td class=\"right_bdr0\"><a href=\"javascript:void(0);\" onclick=\"updateTypeOpen('"+ jsonData[i].id+ "','"+ jsonData[i].name+ "',"+ jsonData[i].value+ ","+vorder+" )\">修改</a></td>";
		        html += "  </tr>";
			}
			$("#types").append(html);
		}
	});
}




/**
 * 添加页码弹出层
 * 
 * @return
 */
 var addArt;
function addTypeOpen(sysType) {
//	
//	var html = "";
//	html += "<div>";
//	html += "<p style='margin-left:20px;'><label>类型名称：</label><input class='inpt' maxlength='8' type='text' value='' id='typeName'/><br>"+
//	"<label>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：</label><input class='inpt' maxlength='2' type='text' value='' id='add_typeValue'/>";
//	if(sysType==-1){
//	  html+="<br><label>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;识：</label><input class='inpt' maxlength='20' type='text' value='' style='ime-mode:disabled' id='add_infoType'/>"
//	}
//	html += "</p></div>";
//	addArt = art.dialog({
//					id : 'addType',
//					title : '新增类型',
//					content : html,
//					width:800,
//					height:450,
//					button : [{
//								name : '确定',
//								callback : function() {
//									var typeName = $.trim($("#typeName").val());
//									var typeValue = $.trim($("#add_typeValue").val());
//									var infoTypeValue = $.trim($("#add_infoType").val());
//		
//									var reg=/^[a-zA-Z0-9_]{1,}$/;
//									if (typeName == ""){art.dialog.alert("类型名称不能为空！");return false;}
//									else if(typeName.length>15){art.dialog.alert("类型名称不能超过15！");return false;}
//									else if(isNaN(typeValue)){art.dialog.alert("排序只能是数字！");return false;}
//									else if(sysType==-1&&infoTypeValue==''){art.dialog.alert("标识不能为空！");return false;}
//									else if(sysType==-1&&!reg.test(infoTypeValue)){art.dialog.alert("标识只能由字母、数字、下划线组成！");return false;}
//								    else{addType(typeName,typeValue,sysType,infoTypeValue);}
//										return false;
//									}
//							},{
//								name:"取消"
//							}]
//					
//				});
	var typeName = $.trim($("#typeName").val());
	var typeValue = $.trim($("#add_typeValue").val());
	var infoTypeValue = "";
	var infoType = $("#infoType").val();
	var url = basePath+'logined/dict/addDict.jsp?infoType='+infoType;
	art.dialog.open(url, {
	    id : "addDict",
	    title : "新增子类型",
	    width : 520,
	    height : 180,
	    lock : true,
	    drag:false,
	    opacity: 0.08,// 透明度
	    close : function(){
			//getSysTypeList();
	    	set(_infoType,_sysTypeId,_value) ;
	    	return true;
	    },
	    ok : function(){
	    	var iframe = this.iframe.contentWindow;
	    	iframe.addType(typeValue,sysType);	    	
	    	return false;
	    },
	    cancel : function(){
	    	return true;
	    }
	});
}

/**
 * 新增类别
 * 
 * @return
 */
function addType(typeName,typeValue,sysType,infoTypeValue) {
	var sysTag = $("#sysTag").val();
	var infoType = $("#infoType").val();
	if(infoTypeValue!=''){
		infoType = infoTypeValue;
	}
	if(!infoType&&sysType==1){
		art.dialog.alert("请选择上级类型！");
		return false;
	}
	var paramData = {
		'infoType' : infoType,
		'sysTag' : sysType,
		'name' : typeName,
		'typeValue':typeValue
	};
	
	$.ajax({
				url : basePath + "dict/setup_addType.action",
				type : "post",
				dataType : "html",
				data : paramData,
				beforeSend:function(){
					$("body").lock();
				},
				complete:function(){
					$("body").unlock();
				},
				
				success : function(data) {
					if (data == 0) {
						addArt.close();
						 if(sysType==1){getTypeList();}
					    else{getSysTypeList();}
					} else if (data == 1) {
						art.dialog.alert("添加失败,类型已存在！");
					} else {
						art.dialog.alert("添加失败！");
					}
				}
			});
}

//修改基础设置
function updateSysTypeOpen(){
 //   var a =$(".s_bg")[0].id;
 	var infoType = $("#infoType").val(); 
 	var sysName=$("#"+infoType+"").html();
 	var SystypeValue=$("#SystypeValue").val();
	var html = "";
	html += "<div >";
	html += "<p style='margin-left:20px;'><label>类型名称：</label><input class='inpt' maxlength='8' type='text' value='"
			+ sysName + "' id='typeName'/><br><label>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：</label><input class='inpt' maxlength='2' type='text' value='"+SystypeValue+"' id='update_SysTypeValue'/>";
	 //html+="<br><label>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;识：</label><input class='inpt' maxlength='20' type='text' value='"+infoType+"' style='ime-mode:disabled' id='add_infoType'/>"
	html += "</p></div>";
	
	art.dialog({
				id : 'modifySysType',
				title : '修改类型',
				content : html,
				 width : 800,
				 height : 450,
				button : [{
							name : '确定',
							callback : function() {
								var SysTypeId=$("#SysTypeId").val();
								$("#sid").val(SysTypeId);
								var typeName = $.trim($("#typeName").val());
								var typeValue= $.trim($("#update_SysTypeValue").val());
								var infoTypeValue = $.trim($("#add_infoType").val());
								var reg=/^[a-zA-Z0-9_]{1,}$/;
								if (typeName == "") {art.dialog.alert("类型名称不能为空！");return false;}
								else if(typeName.length>15){art.dialog.alert("类型名称不能超过15！");return false;}
								else if(isNaN(typeValue)){art.dialog.alert("排序只能是数字！");return false;}
								else if(infoTypeValue==''){art.dialog.alert("标识不能为空！");return false;}
								else if(!reg.test(infoTypeValue)){art.dialog.alert("标识只能由字母、数字、下划线组成！");return false;}
								else {updateType(SysTypeId,typeName,-1,typeValue,infoTypeValue);}
							}
						},{
								name:"取消"
						 }]
			});
}
/**
 * 修改类型
 * 
 * @return
 */
function updateTypeOpen(vid,vname,typeValue,vorder) {
	_value = typeValue;  
	var id = vid;
    var typeName = vname;
	var url = basePath+'logined/dict/addDict.jsp?infoType='+infoType+"&typeName1="+encodeURI(encodeURI(vname))+"&typeOrder="+vorder;
	art.dialog.open(url, {
	    id : "updateDict",
	    title : "修改子类型",
	    width : 520,
	    height : 180,
	    lock : true,
	    drag:false,
	    opacity: 0.08,// 透明度
	    close : function(){
			set(_infoType,_sysTypeId,_value) ;
	    	return true;
	    },
	    ok : function(){
	    	var iframe = this.iframe.contentWindow;
	    	iframe.updateType(id,1,typeValue);	    	
	    	return false;
	    },
	    cancel : function(){
	    	return true;
	    }
	});
}
/**
 * 修改类别
 * 
 * @return
 */
function updateType(id, typeName,sysType,typeValue,infoType) {
	var paramData = {
		'id' : id,
		'name' : typeName,
		'typeValue':typeValue,
		'sysTag':sysType,
		'infoType':infoType
	};
	$.ajax({
				url : basePath + "dict/setup_updateType.action",
				type : "post",
				dataType : "html",
				data : paramData,
				beforeSend:function(){
					$("body").lock();
				},
				complete:function(){
					$("body").unlock();
				},
				success : function(data) {
					if (data == 0) {
					     if(sysType==1){
					     	 getTypeList();
					     } else{
					       getSysTypeList();
					      }
					} else if (data == 1) {
						art.dialog.alert("修改失败,类型已存在！");
					} else {
						art.dialog.alert("修改失败！");
					}
				}
			});
}
//删除基础类别
function deleteSysType(){
        var SysTypeId=$("#SysTypeId").val();
        var infoType = $("#infoType").val();
		art.dialog.confirm('确定删除选中类型吗？', function() {
				$.ajax({
							url : basePath + "dict/setup_deleteType.action",
							type : "post",
							dataType : "html",
							data : {"ids":SysTypeId,"infoType":infoType,"sysTag":-1},
							success : function(data) {
								if (data == 0) {
									//getSysTypeList();
									set(_infoType,_sysTypeId,_value) ;
								} else {
									art.dialog.alert("删除失败！");
								}
							}
						});
			}, function() {
				return;
			});
}
/**
 * 删除类别
 * 
 * @return
 */
function deleteType(typeName) {
	var typeList = $("input[name=typeList]:checked");
	var infoType = $("#infoType").val();
	if (typeList.length == 0) {
		art.dialog.alert("请选择删除的类型！");
		return;
	}

	var ids = "";
	for (var i = 0; i < typeList.length; i++) {
		ids += typeList[i].id + ","
	}
	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	var paramData = {
		'ids' : ids,"infoType":infoType,"sysTag":1
	};
	art.dialog.confirm('确定删除选中类型吗？', function() {
				$.ajax({
							url : basePath + "dict/setup_deleteType.action",
							type : "post",
							dataType : "html",
							data : paramData,
							success : function(data) {
								if (data == 0) {
									/*art.dialog.alert('删除成功！', function() {*/
												getTypeList();
										/*	});*/
								} else {
									art.dialog.alert("删除失败！");
								}
							}
						});
			}, function() {
				return;
			});
}
/**
 * 设置 父类别样式
 */
function set(infoType,id,value,obj) {
	_sysTypeId = id;
	_infoType=infoType;
	_value= value;
	$("#infoType").val(infoType);
	$("#SysTypeId").val(id);
	$("#SystypeValue").val(value);
	var lis = $("#sysTypeList").find("p");
	$("#sysTypeList p").removeClass("menu-p on").addClass("menu-p");
	$(obj).addClass("menu-p on");
	$("input:checkbox[id='total']").prop("checked", false);
	getTypeList();
}

/**
 * 查询
 * 
 * @return
 */
function searchType() {
	var name = $.trim($("#searchName").val());
	if (name == "") {
		art.dialog.alert("查询条件不能为空！");
		$("#searchName").focus();
		return;
	}
	var paramData = {
		'name' : name
	};
	$.ajax({
				url : basePath + "dict/setup_searchType.action",
				type : "post",
				dataType : "html",
				data : paramData,
				success : function(data) {
					jsonData = eval("(" + data + ")");
					$("#types").html(" ");
					var html = "";
					html += "<ul>";
					for (var i = 0; i < jsonData.length; i++) {
						html += "<li><input name='typeList' type='checkbox' id='"
								+ jsonData[i].id
								+ "' value='"
								+ jsonData[i].name
								+ "'/>"
								+ jsonData[i].name
								+ "</li>";
					}
					html += "</ul>";
					$("#types").append(html);
				}
			});
}

/**
 * 子项复选框变更
 */
function checkChange() {
	if ($('input:checkbox[name="typeList"][checked="checked"]').length == $('input:checkbox[name="typeList"]').length) {
		$("input:checkbox[id='total']").prop("checked", true);
	} else {
		$("input:checkbox[id='total']").prop("checked", false);
	}
}