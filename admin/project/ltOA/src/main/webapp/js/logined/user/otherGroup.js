var otherGroupJson = [];

/**
 * 新增从属部门
 **/
function addOtherGroup(type,order){
	var url = basePath + 'logined/user/addOtherGroup.jsp';
	var title = '新增从属部门';
	if(type == 'edit'){
		title = "修改从属部门";
		var theObj = otherGroupJson[order];
		if(theObj){
			var id = theObj.id||"";
			var groupId = theObj.groupId;
			var groupName = theObj.groupName;
			var job = theObj.job||"";
			var telphone = theObj.telphone||"";
			var orderIndex = theObj.orderIndex||"";
			if(orderIndex=="0"){
				orderIndex = "";
			}
			url = basePath + 'logined/user/addOtherGroup.jsp?order='+order+'&id='+id+'&groupId='+groupId+'&groupName='+groupName+'&job='+job+'&telphone='+telphone+'&orderIndex='+orderIndex;
		}
	}
	art.dialog.open(url, 
		{
			title: title ,
			width:600,
			height:300,
			lock : true,
			drag:true,
			opacity: 0.08,// 透明度
			button: [
					{
						 name: '确定',
						 focus: true,
						 callback:function () {
							 var iframe = this.iframe.contentWindow;
							 if(!$(iframe.document).find("#groupId").val()||!$(iframe.document).find("#groupSel").val()){
								 qytx.app.dialog.alert("请选择部门！");
								 return false;
							 }
							 var iGroupId = $(iframe.document).find("#groupId").val();
							 var iOrder = $(iframe.document).find("#order").val();
							 if(isRepeat(iGroupId,iOrder)){
								 qytx.app.dialog.alert("从属部门中包含重复的部门!");
								 return false;
							 }
							 var iGroupName = $(iframe.document).find("#groupSel").val();
							 var iId = $(iframe.document).find("#id").val();
							 var iJob = $(iframe.document).find("#job").val();
							 var iTelphone = $(iframe.document).find("#telphone").val();
							 var iOrderIndex = $(iframe.document).find("#orderIndex").val();
							 
							 
							 var obj = {};
							 	 obj.groupId = iGroupId;
							 	 obj.groupName = iGroupName;
							 	 obj.id = iId||"";
							 	 obj.job = iJob||"";
							 	 obj.telphone = iTelphone||"";
							 	 obj.orderIndex = iOrderIndex||"0";
							 	 obj.isDelete = 0;
							 if(!iOrder){
								 otherGroupJson.push(obj);
							 }else{
								 otherGroupJson[iOrder] = obj;
							 }
							 showOtherGroups();//在列表中展示从属部门信息
						 }
					},{
						 name: '取消',
						 callback:function (){
							 //没有
						 }
					}
				]
			});
}

/**
 * 在列表中展示从属部门信息
 */
function showOtherGroups(){
	var n = 0;
	if(otherGroupJson.length > 0 ){
		$("#otherGroupTable tbody").html("");
	}else{
		$("#otherGroupTable tbody").html('<tr class="odd"><td colspan="5">暂无数据</td></tr>');
	}
	$(otherGroupJson).each(function(i,item){
		if(item.isDelete != 1){
			var html = '';
			if(n%2 == 0){
				html += '<tr class="odd">';
			}else{
				html += '<tr class="even">';
			}
			var orderIndex = "";
			if(item.orderIndex != "0"){
				orderIndex = item.orderIndex;
			}
			
			html += '<td class="longTxt">'+item.groupName+'</td>';
			html += '<td class="tdCenter">'+item.job+'</td>';
			html += '<td class="tdCenter">'+item.telphone+'</td>';
			html += '<td class="data_r">'+orderIndex+'</td>';
			html += '<td class="right_bdr0">'
			html += '<a onclick="addOtherGroup(\'edit\',\''+i+'\');" href="javascript:void(0);">修改</a>';
			html += '<a onclick="deleteOtherGroup(\''+i+'\');" href="javascript:void(0);">删除</a>';
			html += '</td>';
			html += '</tr>';
			
			$("#otherGroupTable tbody").append(html);
			n++;
		}
	});
	if($("#otherGroupTable tbody").html() == ""){
		$("#otherGroupTable tbody").html('<tr class="odd"><td colspan="5">暂无数据</td></tr>');
	}
}

/**
 * 删除已选择的从属部门信息
 * @param order
 */
function deleteOtherGroup(order){
    var obj = otherGroupJson[order];
    if(obj){
	    if(!obj.id){//如果是新增的从属部门，则直接删除
	    	otherGroupJson.splice(order, 1);
	    }else{//如果是数据库中已保存的关系，则通知后台删除
	    	obj.isDelete = 1;
	    	otherGroupJson[order] = obj;
	    }
    }
	
	showOtherGroups();
}

/**
 * 判断选中的部门中有没有重复的
 */
function isRepeat(groupId,order){
	var arr = [];
	arr.push($("#groupId").val());
	$(otherGroupJson).each(function(i,item){
		if((order && order == i) || item.isDelete == 1){
			
		}else{
			arr.push(item.groupId);
		}
	});
	if(arr.indexOf(groupId)>=0){
		return true;
	}
	return false;
}

/**
 * 提交时判断选中的部门中是否有重复的部门信息
 * @returns {Boolean}
 */
function isRepeatForSub(){
	var result = false;
	if(otherGroupJson.length > 0){//判断选中的部门中是否有重复的部门信息
		var arr = [];
		arr.push($("#groupId").val());
		$(otherGroupJson).each(function(i,item){
			if(item.isDelete != 1){
				var otherGroupId = item.groupId;
				if(arr.indexOf(otherGroupId)>=0){
					qytx.app.dialog.alert("从属部门中包含重复的部门!");
					result = true;
					return;
				}else{
					arr.push(item.groupId);
				}
			}
		});
	}
	return result;
}