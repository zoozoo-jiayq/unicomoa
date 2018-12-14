/**
 * 功能:人事档案模块，编辑页面js
 */
$(document).ready(function () {
    initForm();
    initShowErrorMsg();
    setSelectDefaultValue();
    setRadioDefaultValue();
    dateStr2ShortForInput();
    initMy97DatePicker();
   
    displayPhoto();
    initDeletePhotoClick();
    // initAttachmentHTML();
    Attachment.initAttachmentHTML("attachment","attachUL");
    initSetAgeByBirthDay();
    $("body").show();
    $("#goBackBtn").click(function(){
    	document.location.href = basePath+"/logined/record/list_manager.jsp?timeurl=1409048222685";
    });
    /**
     * 删除头像
     */
    $("#deletePhoto").click(function(){
        $("#photoUrlHidden").val("");
        var photoUrl = basePath + "/flat/plugins/form/skins/default/meeting.png";
        $("#photoImg").attr("src", photoUrl);
        $("#deletePhoto").hide();        
    });
    
});

//初始化删除照片按钮
function initDeletePhotoClick(){
    $("#deletePhoto").live("click",function(){
        $("#photoUrlHidden").val("");
        var photoUrl = basePath + "/flat/plugins/form/skins/default/meeting.png";
        $("#photoImg").attr("src", photoUrl);
        $(this).hide();
    });
}

/**
 * 根据生日自动计算年龄
 */
function initSetAgeByBirthDay() {
    $("#birthDay").bind("blur", function () {
    	changeBirthDay();
    });

}

/**
 * 计算年龄
 */
function changeBirthDay(){
	  var birthDay = $("#birthDay").val();
      birthDay = $.trim(birthDay);
      if (birthDay != "") {
          var dateBirthDay = new Date(birthDay.toString().replace(/\-/g, "\/"));
          var dateNow = new Date();
          var timeOffSet = dateNow.getTime() - dateBirthDay.getTime();
          var age = timeOffSet / (1000 * 60 * 60 * 24 * 365);
          var a  = parseInt(age);
          if(a<0){
        	  $("#age").val("");
          }else{
        	  $("#age").val(a);
          }
          
      }
}

/**
 * 对表单中的某些项进行初始化
 */
function initForm() {
    var userId = $("#userId").val();
    if ($.trim(userId) != "") {
        //灰掉OA登录名
        $("#loginName").attr("readonly", "readonly");
    }
    var recordId = $("#recordId").val();
    if ($.trim(recordId) != "") {
        document.userRecordForm.action = basePath + "logined/record/update.action";
        $(".tipsForCreate").hide();
    }
}
var resumeEditor;
/**
 * 初始化简历编辑器
 */
function initResumeEditor() {
    // 初始化富客户端
    resumeEditor = UE.getEditor('contentInfo', {
        initialFrameWidth:"100%",
        initialFrameHeight:400
    });
    resumeEditor.addListener('ready',function(){
        window.parent.frameResize();
    });
    setTimeout(function(){window.parent.frameResize()},100);


//    KindEditor.ready(function (K) {
//        resumeEditor = K.create('textarea[name="userRecord.resumeInfo"]', {
//            resizeType: 1,
//            allowPreviewEmoticons: false,
//            allowImageUpload: true,
//            uploadJson: basePath
//                + 'plugins/kindeditor/upload_json.jsp',
//            items: [ 'fontname', 'fontsize', '|', 'forecolor',
//                'hilitecolor', 'bold', 'italic', 'underline',
//                'removeformat', '|', 'justifyleft',
//                'justifycenter', 'justifyright',
//                'insertorderedlist', 'insertunorderedlist', '|',
//                'emoticons', 'image', 'link' ]
//        });
//    });
}

function ajaxCheckLoginName(checkOthers) {
     var loginNameObj = $("#loginName");
//    if ((loginNameObj).attr("readonly") == "readonly") {//不需要检查
//        if (checkOthers) {
//            //检查其他的
//            checkAllNoLoginName();
//        }
//        return;
//    }
     var userId = $("#userId").val();
     var recordId = $("#recordId").val();
     if(userId!=""){
    	
    		 qytx.app.ajax({
                 url: basePath + "logined/record/checkLoginName.action",
                 type: "post",
                 dataType: 'text',
                 data: {
                     userId: $("#userId").val()
                 },
                 success: function (data) {
                     if (data == "1"||( data == "0"&&recordId!="") ){ 
                         if (checkOthers) {
                             //检查其他的
                             checkAllNoLoginName();
                         }
                     } else {
                         qytx.app.valid.showObjError(loginNameObj, "record.login_name_already_exist");
                         loginNameObj.focus();
                      }
                  }
              });
     }else{
    	 qytx.app.valid.showObjError(loginNameObj, "record.login_name_not_null");
         loginNameObj.focus();
     }
    
}

/**
 * 检查所有表单，通过后提交
 */
function checkAllNoLoginName() {
    var allPassed = false;
    allPassed = qytx.app.valid.check({form:document.userRecordForm});
    var joinTime = $("#joinTime").val();
    var startPayTime = $("#startPayTime").val();
   // if(joinTime&&startPayTime){
   // 	if(!compareTime(startPayTime,joinTime)){
    		//art.dialog.alert("起薪时间不能早于入职时间");
   // 		return;
   // 	}
   // }
    var yearHoliday = $("#yearHoliday").val();
    if(yearHoliday>365){
    	qytx.app.dialog.alert("年休假不能超过一年的总天数");
    	return ;
    }
    //所有的都校验通过
    if (allPassed) {
        if (typeof(resumeEditor) != "undefined") {
            $("#resumeInfo").val(resumeEditor.getContent());
        }
        document.userRecordForm.submit();
    }
}
function compareTime(startTime,endTime){
	if(!startTime) return true;
	if(!endTime) return true;
	startTime = startTime.replace(/-/g,"");
	endTime = endTime.replace(/-/g,"/");
	if(startTime>endTime){
		return false;
	}else{
		return true;
	}
}
function selectUser()
{
    //具体参数查看 select_document_user.js
    //openDocSelectUser(3,selectCallBack,0);//选择人员
	var userId = $("#userId").val();
    openSelectUser(3,selectCallBack,null,userId,"radio");//选择人员
}
function selectCallBack(data)
{
    if(data && data.length>0)
    {
        qytx.app.ajax({
            url: basePath + "/user/ajaxUserById.action",
            type: "post",
            dataType: 'json',
            data: {
            	userId: data[0].id
            },
            success: function (data) {
                if (null != data){
                	
                	if ($("#userId").val() != data["id"]){
                		// 清空告警信息
                		hideError($("#loginName"));
                	}
                		
                	// 设置显示登录名
                	$("#loginNameDis").val(data["userName"]);
                	var userState = data["userState"];
                	if (userState != '2'){
                		$("#loginNameTd").val(data["loginName"]);
                		$("#loginName").val(data["loginName"]);
                	}else{
                		$("#loginNameTd").val('');
                		$("#loginName").val(data["loginName"]);
                	}
                	
                	
                	// 设置用户Id
                	$("#userId").val(data["id"]);
                	
                	// 设置部门
                	$("#groupNamesTd").val(data["groupName"]);
                	$("#groupNames").val(data["groupName"]);
                	
                	// 设置角色
                	$("#roleNamesTd").val(data["roleNames"]);
                	
                	//工号
                	$("#workNo").val(data["workNo"]);
                	
                	//邮件
                	$("#email").val(data["email"]);
                	//姓名
                	$("#userName").val(data["userName"]);
                	
                	//性别
                	var sex = data["sex"];
                	$("#sex_hidden").val(sex);
                	
                	// 生日
                	var birthDay = data["birthDay"];
                	if (null != birthDay && "" != birthDay){
                		if (birthDay.length>10){
                			$("#birthDay").val(data["birthDay"].substring(0,10));
                		}else{
                			$("#birthDay").val(data["birthDay"]);
                		}
                	}else{
                		$("#birthDay").val("");
                	}
                	
                	changeBirthDay();
                	
                	if(data["photo"]!=null&& ""!=data["photo"]){
                		$("#photoImg").attr("src",basePath + "filemanager/downFileByFilePath.action?filePath=" + data["photo"]);
                		$("#photoUrlHidden").val(data["photo"]);
                	}else{
                		$("#photoImg").attr("src",basePath+"/flat/plugins/form/skins/default/meeting.png");
                		$("#photoUrlHidden").val('');
                	}
                	// 职务
                	$("#job").val(data["job"]);
                	
                	// 联系电话
                	$("#phone2").val(data["phone2"]);
                	
                	// 手机号码
                	$("#phone").val(data["phone"]);
                	
                }
            }
        });
    }
    else
    {
        alert("请选择人员");
    }
}
