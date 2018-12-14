$(function(){
	
	//加载关系下拉
	findDict("family_relation",1);
	//加载政治面貌下拉
	findDict("political_status",2);
	var recordRelationId=$("#recordRelationId").val();
	if(recordRelationId){
		
		var remark =$("#remark").val();
		$("#limitRemark").empty().text(255-remark.length);
		
	}
});


/**
 * 加载下拉列表
 */
function findDict(infoType,type){
	var param={
			"infoType":infoType,
			"sysTag":1
	}
	$.ajax({
		url:basePath+"dict/getDicts.action",
	   type:"post",
	dataType:"json",
	    data:param,
	    success:function(data){
	    	var html="<option value =''>请选择</option>";
			if(data!=null){
				for(var i=0;i<data.length;i++){ 
					html+="<option value='"+data[i].value+"'>"+data[i].name+"</option>";
				}
				if(type==1){
					$("#relation").html(html);
					if($("#recordRelationId").val()){
						$("#relation").val($("#relationId").val());
					}
				}else{
					$("#politicalStatus").html(html);
					if($("#recordRelationId").val()){
						$("#politicalStatus").val($("#politicalStatusId").val());
					}
				}
			}
			
	    }
	  
	})
}

function saveOrUpdateRelation(obj){
	$(obj).attr("disabled",true);
	var userId=$("#userId").val();
	var relationId=$("#recordRelationId").val();
	var memberName=$("#memberName").val();
	if(!memberName){
		qytx.app.valid.showObjError($("#memberName"), 'relation.relation_memberName_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#memberName"));
	}
	
	var relation=$("#relation").val();
	if(!relation){
		qytx.app.valid.showObjError($("#relation"), 'relation.relation_relation_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#relation"));
	}
	
	var birthDate= $("#birthDate").val();
	var politicalStatus=$("#politicalStatus").val();
	var occupation=$("#occupation").val();
	var duties=$("#duties").val();
	var personalPhone=$("#personalPhone").val();
	if(!personalPhone){
		qytx.app.valid.showObjError($("#personalPhone"), 'relation.relation_personalPhone_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#personalPhone"));
	}
	if(!validatePhone(personalPhone)){
		qytx.app.valid.showObjError($("#personalPhone"), 'relation.relation_phone_not');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#personalPhone"));
	}
	var homePhone=$("#homePhone").val();
	if(!homePhone){
		$(obj).attr("disabled",false);
		qytx.app.valid.showObjError($("#homePhone"), 'relation.relation_homePhone_not_null');
		return;
	}else{
		qytx.app.valid.hideError($("#homePhone"));
	}
	if(!validatePhone(homePhone)){
		$(obj).attr("disabled",false);
		qytx.app.valid.showObjError($("#homePhone"), 'relation.relation_phone_not');
		return;
	}else{
		qytx.app.valid.hideError($("#homePhone"));
	}
	var workTelephone=$("#workTelephone").val();
	if(workTelephone){
		if(!validatePhone(workTelephone)){
			$(obj).attr("disabled",false);
			qytx.app.valid.showObjError($("#workTelephone"), 'relation.relation_phone_not');
			return;
		}else{
			qytx.app.valid.hideError($("#workTelephone"));
		}
	}
	var workUnit=$("#workUnit").val();
	var unitAddress=$("#unitAddress").val();
	var homeAddress=$("#homeAddress").val();
	if(!homeAddress){
		$(obj).attr("disabled",false);
		qytx.app.valid.showObjError($("#homeAddress"), 'relation.relation_homeAddress_not_null');
		return;
	}else{
		qytx.app.valid.hideError($("#homeAddress"));
	}
	var remark=$("#remark").val();
	var attment=$("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var param={
			"recordRelation.id":relationId,
			"recordRelation.userInfo.userId":userId,
			"recordRelation.memberName":memberName,
			"recordRelation.relation":relation,
			"recordRelation.birthDate":birthDate,
			"recordRelation.politicalStatus":politicalStatus,
			"recordRelation.occupation":occupation,
			"recordRelation.duties":duties,
			"recordRelation.personalPhone":personalPhone,
			"recordRelation.homePhone":homePhone,
			"recordRelation.workTelephone":workTelephone,
			"recordRelation.workUnit":workUnit,
			"recordRelation.unitAddress":unitAddress,
			"recordRelation.homeAddress":homeAddress,
			"recordRelation.remark":remark,
			"recordRelation.attment":attment
	}
	$.ajax({
		url:basePath+"/logined/recordOther/saveOrUpdateRelation.action",
	    type:"post",
	    dataType:"text",
	    data:param,
	    success:function(result){
	    	if(result==1){
	    		art.dialog.tips("添加成功!");
				setTimeout(function(){
					window.close();
				},1000);
	    	}else if(result==2){
	    		art.dialog.tips("修改成功!");
				setTimeout(function(){
					window.close();
				},1000);
	    	}else{
	    		$(obj).attr("disabled",false);
	    		art.dialog.alert("操作失败！");
	    	}
	    }
	})
}
/**
 * 验证电话
 */
function validatePhone(phone){
	var re=/(^((0?1[358]\d{9})|((0(10|2[1-3]|[3-9]\d{2}))?[1-9]\d{6,7}))$)/;
	if(!re.test(phone)){
		return false;
	}else{
		return true;
	}
} 
