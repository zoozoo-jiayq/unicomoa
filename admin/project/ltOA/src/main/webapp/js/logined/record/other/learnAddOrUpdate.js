$(document).ready(function() {
	loadCapitalUnit();
	loadCapitalUnit2();
	var learnId = $("#learnId").val();
	if(learnId){
		var award = $("#award").val();
		$("#limitAward").empty().text(255-award.length);
		var certificates = $("#certificates").val();
		$("#limitCertificates").empty().text(255-certificates.length);
		var remark = $("#remark").val();
		$("#limitRemark").empty().text(255-remark.length);
	}
} );

function addOrUpdate(obj){
	$(obj).attr("disabled",true);
	// 框架校验
	if (!validator(document.getElementById("form1"))) {
		$(obj).attr("disabled",false);
		return;
	}
	var learnId = $("#learnId").val();
	var major = $("#major").val();
	if(typeof(major) == "undefined" || major.trim() == ""){
		qytx.app.valid.showObjError($("#majorHidden"), 'recordLearn.major_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#majorHidden"));
	}
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(startDate&&endDate&&(startDate>endDate)){
		qytx.app.valid.showObjError($("#dateHidden"), 'recordLearn.date_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#dateHidden"));
	}
	var education = $("#education").val();
	if(!education){
		qytx.app.valid.showObjError($("#educationHidden"), 'recordLearn.education_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#educationHidden"));
	}
	var degree = $("#degree").val();
	var classCadre = $("#classCadre").val();
	var reterence = $("#reterence").val();
	if(typeof(reterence) == "undefined" || reterence.trim() == ""){
		qytx.app.valid.showObjError($("#reterenceHidden"), 'recordLearn.reterence_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#reterenceHidden"));
	}
	var school = $("#school").val();
	if(typeof(school) == "undefined" || school.trim() == ""){
		qytx.app.valid.showObjError($("#schoolHidden"), 'recordLearn.school_not_null');
		$(obj).attr("disabled",false);
		return;
	}else{
		qytx.app.valid.hideError($("#schoolHidden"));
	}
	var schoolAddress = $("#schoolAddress").val();
	var award = $("#award").val();
	var certificates = $("#certificates").val();
	var remark = $("#remark").val();
    var attment = $("#attachmentId").val();
	if (null == attment || "," == attment || "" == attment) {
		attment = "";
	}
	var param = {
		"learnId":learnId,
		"recordLearn.major":major,
		"recordLearn.userInfo.userId":$("#userId").val(),
		"recordLearn.education":education,
		"recordLearn.degree":degree,
		"recordLearn.startDate":startDate,
		"recordLearn.endDate":endDate,
		"recordLearn.classCadre":classCadre,
		"recordLearn.reterence":reterence,
		"recordLearn.school":school,
		"recordLearn.schoolAddress":schoolAddress,
		"recordLearn.award":award,
		"recordLearn.remark":remark,
		"recordLearn.certificates":certificates,
		"recordLearn.attment":attment
	};
	var url = basePath+"/logined/recordOther/savelearn.action";
	$.ajax({
		url:url,
		type:'POST',
		data:param,
		dataType:'json',
		success:function(result){
			if(result=="1"){
				art.dialog.tips("添加成功!");
				setTimeout(function(){
					window.close();
				},1000);
			}else if(result=="2"){
				art.dialog.tips("修改成功!");
				setTimeout(function(){
					window.close();
				},1000);
			}else{
				art.dialog.alert("操作失败!");
			}
		}
	});
		
}

//加载计量单位下拉选
function loadCapitalUnit(){
	var param={
			"infoType":"edu_level",
			"sysTag":1
	}
	$.ajax({
		url:basePath+"dict/getDicts.action",
		type:'post',
		data:param,
		dataType:'json',
		success:function(data){
			var html="<option value =''>请选择</option>";
			var ldegree=$("#ldegree").val();
			if(data!=null){
				for(var i=0;i<data.length;i++){     
					if(data[i].value==ldegree){
						html+="<option value='"+data[i].value+"' selected='selected'>"+data[i].name+"</option>";
					}else{
						html+="<option value='"+data[i].value+"'>"+data[i].name+"</option>";
					}				
				}
			}
			$("#degree").html(html);
		}
	})
};
function loadCapitalUnit2(){
	var param={
			"infoType":"edu_qualifications",
			"sysTag":1
	}
	$.ajax({
		url:basePath+"dict/getDicts.action",
		type:'post',
		data:param,
		dataType:'json',
		success:function(data){
			var html="<option value =''>请选择</option>";
			var leducation=$("#leducation").val();
			if(data!=null){
				for(var i=0;i<data.length;i++){
					if(data[i].value==leducation){
						html+="<option value='"+data[i].value+"' selected='selected'>"+data[i].name+"</option>";
					}else{
						html+="<option value='"+data[i].value+"'>"+data[i].name+"</option>";
					}	
				}
			}
			$("#education").html(html);
		}
	})
};

