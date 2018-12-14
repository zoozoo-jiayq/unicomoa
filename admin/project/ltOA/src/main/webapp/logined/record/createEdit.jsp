<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案-编辑</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
    <link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${ctx}/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
    
    
    
     <script type="text/javascript" src="${ctx}/js/logined/record/upload_img.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/upload_file.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/create_edit.js"></script>
    
    
 <script type="text/javascript">
$(document).ready(function(){
   $(".myphoto dt").hover(function() {
		$(this).find(".close").fadeToggle();
	});
});
</script>
    <style type="text/css">
		.inputTable th{width:100px;}
		.readOnlyText{width:100%;}
	</style>
</head>

<body  >
<input type="hidden" name="from" value="${from}"/>
<div class="formPage">
<form action="${ctx}logined/record/save.action" method="post" name="userRecordForm">
<div class="formbg">
		<input type="hidden" name="userRecord.id" id="recordId" value="${userRecord.id}"/>
		<input type="hidden" name="userRecord.userInfo.userId" id="userId" value="${userRecord.userInfo.userId}"/>
		<input type="hidden" name="userRecord.groupIds" value="${userRecord.groupIds}"/>
<s:if test="#request.from=='create'">
      <div class="big_title">新增人员</div>
</s:if>
<s:else>
      <div class="big_title">修改人员</div>
</s:else>
    <div class="content_form">
      <div class="small_title">基本信息</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputTable">
    <tr>
        <th><em class="requireField">*</em><label>姓名：</label></th>
        <td>
        			<input type="hidden"    id="loginName" value="${userRecord.loginName}"   />
                   <s:if test="#request.from=='create'">
                        <input type="text"   style="width:282px;"  class="readOnlyText" name="userRecord.userName" id="loginNameDis" value="${userRecord.userName}" maxlength="16" valid="required" errmsg="record.user_name_not_null" readonly="readonly"/>
	                    <span class="addMember">
	                        <a class="icon_add" href="javascript:void(0);" onclick="selectUser()">选择</a>
	                    </span>
                   </s:if><s:else>
                  		 <input type="hidden" class="formText" name="userRecord.userName" value="${userRecord.userName}" maxlength="16"  valid="required" errmsg="record.user_name_not_null"/>
                  		  ${userRecord.userName}
                   </s:else>
        </td>
                    <th rowspan="3">头像：</th>
                      <td rowspan="3">
	                        <dl class="myphoto">
	                              <dt><!-- <em class="close"  id="deletePhoto" ></em> --><img src="${ctx}/flat/plugins/form/skins/default/meeting.png" id="photoImg" width="107" height="107" /></dt>
	                              <!-- <dd><h3><input id="img_upload" name="img_upload" type="file" multiple="true" /></h3></dd>
	                              <dd><p>支持 .jpg .gif .png格式图片，200K以内</p></dd> -->
	                         <div id="img_queue" style="display: none;"></div>
    						  <input type="hidden" name="userRecord.photoUrl" value="${userRecord.photoUrl}" id="photoUrlHidden" > </input>
	                        </dl>
                  </td>
                </tr>
        <tr>
        <th><label>部门：</label></th>
        <td>
         <input readonly="readonly" id="groupNamesTd" type="text"  maxlength="50"  name="userRecord.groupNames"  value="${userRecord.groupNames}" class="readOnlyText"  />
        </td>
    </tr>
    <tr>
                    <th><label>角色：</label></th>
                    <td >
                    <input readonly="readonly" id="roleNamesTd" type="text"  maxlength="20"  name="userRecord.roleNames"  value="${userRecord.roleNames}" class="readOnlyText"  />
                    </td>
                </tr>
                <tr>
                    <th><label>档案编号：</label></th>
                    <td>${userRecord.recordNo}
                    <c:if test="${userRecord== null  }">(系统自动生成)</c:if>
                    </td>
                    <th><label>工号：</label></th>
                    <td><input readonly="readonly" type="text" class="readOnlyText" name="userRecord.workNo" id="workNo"
                   value="${userRecord.workNo}" maxlength="16"/></td>
                </tr>
                 <tr>
                    <th><label>登录名：</label></th>
                    <td >
                     <input id="loginNameTd" readonly="readonly" type="text"  maxlength="16"  name="userRecord.loginName"  value="${userRecord.loginName}" class="readOnlyText"  />
                    </td>
                    <th><label>曾用名：</label></th>
                    <td><input type="text" class="formText" name="userRecord.nameOld" id="nameOld"
                   value="${userRecord.nameOld}" maxlength="16"
                   valid="isChinese" errmsg="record.must_be_chinese"/></td>
                </tr>
                <tr>
                    <th><label>英文名：</label></th>
                    <td><input type="text" class="formText" name="userRecord.nameEnglish" id="nameEnglish"
                   value="${userRecord.nameEnglish}" maxlength="16"
                   valid="isEnglishName" errmsg="record.must_be_english_name"/></td>
                    <th><label>性别：</label></th>
                    <td>
			            <input type="text" readonly="readonly" class="readOnlyText" value="${userRecord.sex==0?'女':'男'}"/>
			            <input type="hidden" name="userRecord.sex" class="radioHidden" id="sex_hidden" value="${userRecord.sex}"/>
            		</td>
                </tr>
                <tr>
                    <th><label>身份证号：</label></th>
                    <td><input type="text" class="formText" name="userRecord.identityNo" id="identityNo"
                   value="${userRecord.identityNo}" maxlength="18" valid="isIdCard"
                   errmsg="record.must_be_idCard"/></td>
                    <th><label>出生日期：</label></th>
                    <td><input readonly="readonly" type="text" class="readOnlyText" name="userRecord.birthDayStr" id="birthDay"
                   value="<fmt:formatDate value="${userRecord.birthDay}" pattern="yyyy-MM-dd"/>"/></td>
                </tr>
                <tr>
                    <th><label>年龄(岁)：</label></th>
                    <td><input readonly="readonly" type="text" class="readOnlyText" name="userRecord.age" id="age" value="${userRecord.age}" 
                    	maxlength="2" valid="isNumber|range" min="0" errmsg="record.must_be_number|record.must_over_zero"/></td>
                    <th><label>年休假(天)：</label></th>
                    <td><input name="userRecord.yearHoliday" id="yearHoliday" value="${userRecord.yearHoliday}" type="text"
                   class="formText numberKeyUp" maxlength="3" valid="isNumber|range"
                   errmsg="record.must_be_number|record.must_over_zero"/></td>
                </tr>

    <tr>
        <th><label>籍贯：</label></th>
        <td>
            <select name="userRecord.nativityPlaceSelect" id="nativityPlaceSelect">
                <option value="">--请选择--</option>
                <option value="北京市">北京市</option>
                <option value="天津市">天津市</option>
                <option value="河北省">河北省</option>
                <option value="山西省">山西省</option>
                <option value="内蒙古自治区">内蒙古自治区</option>
                <option value="辽宁省">辽宁省</option>
                <option value="吉林省">吉林省</option>
                <option value="黑龙江省">黑龙江省</option>
                <option value="上海市">上海市</option>
                <option value="江苏省">江苏省</option>
                <option value="浙江省">浙江省</option>
                <option value="安徽省">安徽省</option>
                <option value="福建省">福建省</option>
                <option value="江西省">江西省</option>
                <option value="山东省">山东省</option>
                <option value="河南省">河南省</option>
                <option value="湖北省">湖北省</option>
                <option value="湖南省">湖南省</option>
                <option value="广东省">广东省</option>
                <option value="广西壮族自治区">广西壮族自治区</option>
                <option value="海南省">海南省</option>
                <option value="重庆市">重庆市</option>
                <option value="四川省">四川省</option>
                <option value="贵州省">贵州省</option>
                <option value="云南省">云南省</option>
                <option value="西藏自治区">西藏自治区</option>
                <option value="陕西省">陕西省</option>
                <option value="甘肃省">甘肃省</option>
                <option value="青海省">青海省</option>
                <option value="宁夏回族自治区">宁夏回族自治区</option>
                <option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
                <option value="香港特别行政区">香港特别行政区</option>
                <option value="澳门特别行政区">澳门特别行政区</option>
                <option value="台湾省">台湾省</option>
                <option value="其它">其它</option>
            </select>&nbsp;&nbsp;
            <input type="hidden" class="selectHidden" id="nativityPlaceSelect_hidden"  value="${userRecord.nativityPlaceSelect}"/>
            <input name="userRecord.nativityPlace" id="nativityPlace" value="${userRecord.nativityPlace}" type="text"
                   class="formText" maxlength="100" style="width:209px;" /></td>
        <th><label>民族：</label></th>
        <td><input type="hidden" class="selectHidden" id="nationality_hidden" value="${userRecord.nationality}"/>
            <select name="userRecord.nationality" id="nationality" >
                <option selected="selected" value="">--请选择--</option>
                <option value="汉族">汉族</option>
                <option value="蒙古族">蒙古族</option>
                <option value="回族">回族</option>
                <option value="藏族">藏族</option>
                <option value="维吾尔族">维吾尔族</option>
                <option value="维吾尔族">维吾尔族</option>
                <option value="苗族">苗族</option>
                <option value="彝族">彝族</option>
                <option value="壮族">壮族</option>
                <option value="布依族">布依族</option>
                <option value="朝鲜族">朝鲜族</option>
                <option value="满族">满族</option>
                <option value="侗族">侗族</option>
                <option value="瑶族">瑶族</option>
                <option value="白族">白族</option>
                <option value="土家族">土家族</option>
                <option value="哈尼族">哈尼族</option>
                <option value="哈萨克族">哈萨克族</option>
                <option value="傣族">傣族</option>
                <option value="黎族">黎族</option>
                <option value="傈僳族">傈僳族</option>
                <option value="佤族">佤族</option>
                <option value="畲族">畲族</option>
                <option value="高山族">高山族</option>
                <option value="拉祜族">拉祜族</option>
                <option value="水族">水族</option>
                <option value="东乡族">东乡族</option>
                <option value="纳西族">纳西族</option>
                <option value="景颇族">景颇族</option>
                <option value="柯尔克孜族">柯尔克孜族</option>
                <option value="土族">土族</option>
                <option value="达斡尔族">达斡尔族</option>
                <option value="仫佬族">仫佬族</option>
                <option value="羌族">羌族</option>
                <option value="布朗族">布朗族</option>
                <option value="撒拉族">撒拉族</option>
                <option value="毛南族">毛南族</option>
                <option value="仡佬族">仡佬族</option>
                <option value="锡伯族">锡伯族</option>
                <option value="阿昌族">阿昌族</option>
                <option value="普米族">普米族</option>
                <option value="塔吉克族">塔吉克族</option>
                <option value="怒族">怒族</option>
                <option value="乌孜别克族">乌孜别克族</option>
                <option value="俄罗斯族">俄罗斯族</option>
                <option value="鄂温克族">鄂温克族</option>
                <option value="德昂族">德昂族</option>
                <option value="保安族">保安族</option>
                <option value="裕固族">裕固族</option>
                <option value="京族">京族</option>
                <option value="塔塔尔族">塔塔尔族</option>
                <option value="独龙族">独龙族</option>
                <option value="鄂伦春族">鄂伦春族</option>
                <option value="赫哲族">赫哲族</option>
                <option value="门巴族">门巴族</option>
                <option value="珞巴族">珞巴族</option>
                <option value="基诺族">基诺族</option>
            </select>
        </td>
       </tr>
         <tr>
            <th><label>婚姻状况：</label></th>
            <td><select name="userRecord.marriageStatus" id="marriageStatus">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.marriage_status" var="marriageStatusInfoType">
                    <option value="${marriageStatusInfoType.value}">${marriageStatusInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="marriageStatus_hidden" value="${userRecord.marriageStatus}"/></td>
            <th><label>健康状况：</label></th>
            <td> <input type="text" class="formText"  maxlength="50"  name="userRecord.healthInfo" id="healthInfo"
                   value="${userRecord.healthInfo}" /></td>
        </tr>
        <tr>
            <th><label>政治面貌：</label></th>
            <td>
            	<select name="userRecord.politicsFace" id="politicsFace">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.politics_face" var="politicsFaceInfoType">
                    <option value="${politicsFaceInfoType.value}">${politicsFaceInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="politicsFace_hidden" value="${userRecord.politicsFace}"/>
			</td>
            <th><label>入党时间：</label></th>
            <td><input name="userRecord.partyTimeStr" id="partyTime" value="${userRecord.partyTime}" type="text"
                   class="formText date"/>
             </td>
        </tr>
        <tr>
            <th><label>户口类型：</label></th>
            <td colspan="3"> <select name="userRecord.registeredType" id="registeredType">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.registered_type" var="registeredTypeInfoType">
                    <option value="${registeredTypeInfoType.value}">${registeredTypeInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="registeredType_hidden" value="${userRecord.registeredType}"/></td>
         </tr>
         <tr>   
            <th><label>户口所在地：</label></th>
            <td colspan="3"> <input name="userRecord.registeredAddress" id="registeredAddress" value="${userRecord.registeredAddress}"
                   type="text" class="formText" maxlength="50"/></td>
        </tr>
     </table>
 <div class="small_title">职位情况及联系方式</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputTable">
    <tbody>
         <tr>
            <th>工种：</th>
            <td><input name="userRecord.workType" id="workType" value="${userRecord.workType}" type="text"
                   class="formText" maxlength="50"/></td>
            <th>行政级别：</th>
            <td><input name="userRecord.administrationLevel" id="administrationLevel"
                   value="${userRecord.administrationLevel}" type="text" class="formText" maxlength="50"/></td>
        </tr>
        <tr>
            <th>员工类型：</th>
            <td> 
	            <select name="userRecord.userType" id="userType">
	                <option value="">--请选择--</option>
	                <s:iterator value="#baseDataMap.user_type" var="userTypeInfoType">
	                    <option value="${userTypeInfoType.value}">${userTypeInfoType.name}</option>
	                </s:iterator>
	            </select>
           		<input type="hidden" class="selectHidden" id="userType_hidden" value="${userRecord.userType}"/>
            </td>
            <th>职务：</th>
            <td><input name="userRecord.job" readonly="readonly" id="job" value="${userRecord.job}" type="text" class="readOnlyText" maxlength="20"/></td>
        </tr>
        <tr>
            <th>职称：</th>
            <td>
				 <select name="userRecord.jobTitle" id="jobTitle">
	                <option value="">--请选择--</option>
	                <s:iterator value="#baseDataMap.job_title" var="jobTitleInfoType">
	                    <option value="${jobTitleInfoType.value}">${jobTitleInfoType.name}</option>
	                </s:iterator>
            	</select>
            <input type="hidden" class="selectHidden" id="jobTitle_hidden" value="${userRecord.jobTitle}"/>
			</td>      
            <th>入职时间：</th>
            <td><input type="text" class="formText Wdate"  readonly="readonly"  
            onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'startPayTime\')||\'${today}\'}'}) " name="userRecord.joinTimeStr" id="joinTime"
                     value='<fmt:formatDate value="${userRecord.joinTime}" pattern="yyyy-MM-dd"/>' /></td>
        </tr>
        <tr>
            <th>职称级别：</th>
            <td><select name="userRecord.jobTitleLevel" id="jobTitleLevel">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.job_title_level" var="jobTitleLevelInfoType">
                    <option value="${jobTitleLevelInfoType.value}">${jobTitleLevelInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="jobTitleLevel_hidden" value="${userRecord.jobTitleLevel}"/></td>
            <th>岗位：</th>
            <td><select name="userRecord.station" id="station">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.station" var="stationInfoType">
                    <option value="${stationInfoType.value}">${stationInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="station_hidden" value="${userRecord.station}"/></td>
        </tr>
        <tr>
            <th>单位工龄(年)：</th>
            <td><input name="userRecord.companyWorkAge" id="companyWorkAge" value="${userRecord.companyWorkAge}" type="text"
                   class="formText numberKeyUp" maxlength="3" valid="isNumber|range" min="0"
                   errmsg="record.must_be_number|record.must_over_zero"/></td>
            <th>起薪时间：</th>
            <td> <input name="userRecord.startPayTimeStr" id="startPayTime"    
                value='<fmt:formatDate value="${userRecord.startPayTime}" pattern="yyyy-MM-dd"/>'   
                type="text" class="formText Wdate"  
                     onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'joinTime\')}',maxDate:'${today}'})"/></td>
        </tr>
        <tr>
            <th>在职状态：</th>
            <td><select name="userRecord.workStatus" id="workStatus">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.work_status" var="workStatusInfoType">
                    <option value="${workStatusInfoType.value}">${workStatusInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="workStatus_hidden" value="${userRecord.workStatus}"/></td>
            <th>总工龄(年)：</th>
            <td><input name="userRecord.totalWorkAge" id="totalWorkAge" value="${userRecord.totalWorkAge}" type="text"
                   class="formText  numberKeyUp" maxlength="3" valid="isNumber|range" min="0"
                   errmsg="record.must_be_number|record.must_over_zero"/></td>
        </tr>
        <tr>
            <th>参加工作时间：</th>
            <td><input name="userRecord.joinWorkTimeStr" id="joinWorkTime" value="${userRecord.joinWorkTime}" type="text"
                   class="formText date"/></td>
            <th>联系电话：</th>
            <td><input readonly="readonly" type="text" class="readOnlyText" name="userRecord.phone2" id="phone2"
                   value="${userRecord.phone2}" valid="isPhoneSimple"
                   errmsg="record.must_be_right_phone_no"/></td>
        </tr>
    
        <tr>
            <th><em class="requireField">*</em>手机号码：</th>
            <td>
				 <input readonly="readonly" type="text" class="readOnlyText" name="userRecord.phone" id="phone"
                   value="${userRecord.phone}" valid="required|isMobile"   maxlength="11"   onkeyup="value=value.replace(/[^\d]/g,'')"
                   errmsg="record.mobile_no_not_null|record.must_be_right_mobile_no"/>
            </td>
            <th>MSN：</th>
            <td><input type="text" class="formText" name="userRecord.msn" id="msn"
                   value="${userRecord.msn}" maxlength="50" valid="isEmail"
                   errmsg="record.must_be_right_msn"/></td>
        </tr>
        <tr>
            <th>电子邮件：</th>
            <td><input type="text" readonly="readonly" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')"  class="readOnlyText" name="userRecord.email" id="email"
                   value="${userRecord.email}" maxlength="50" valid="isEmail"
                   errmsg="record.must_be_right_email"/></td>
            <th>QQ：</th>
            <td><input type="text" class="formText numberKeyUp" name="userRecord.qq" id="qq"
                   value="${userRecord.qq}" maxlength="15" valid="isQQ" errmsg="record.must_be_right_qq"/></td>
        </tr>
        <tr>
            <th>家庭地址：</th>
            <td colspan="3"><input name="userRecord.homeAddress" id="homeAddress" value="${userRecord.homeAddress}"
                   type="text" class="formText" maxlength="100"/></td>
        </tr>
        <tr>
            <th>其他联系方式：</th>
            <td colspan="3"><input name="userRecord.otherContactWay" id="otherContactWay" value="${userRecord.otherContactWay}"
                   type="text" class="formText" maxlength="100"/></td>
        </tr>
        <tr>
            <th>开户行1：</th>
            <td><input name="userRecord.bankName1" id="bankName1" value="${userRecord.bankName1}"
                   type="text" class="formText" maxlength="50"/></td>
            <th>账号1：</th>
            <td><input name="userRecord.bankNo1" id="bankNo1" value="${userRecord.bankNo1}"
                   type="text" class="formText numberKeyUp" maxlength="50" valid="isNumber"
                   errmsg="record.must_be_number"/></td>
        </tr>
        <tr>
            <th>开户行2：</th>
            <td><input name="userRecord.bankName2" id="bankName2" value="${userRecord.bankName2}"
                   type="text" class="formText" maxlength="50"/></td>
            <th>账号2：</th>
            <td><input name="userRecord.bankNo2" id="bankNo2" value="${userRecord.bankNo1}"
                   type="text" class="formText numberKeyUp" maxlength="50" valid="isNumber"
                   errmsg="record.must_be_number"/></td>
        </tr>
    </tbody>
</table>
<div class="small_title">教育情况</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputTable">
   <tbody><tr>
                <th><label>学历：</label></th>
                <td><select name="userRecord.eduQualifications" id="eduQualifications">
            <option value="">--请选择--</option>
            <s:iterator value="#baseDataMap.edu_qualifications" var="eduQualificationsInfoType">
                <option value="${eduQualificationsInfoType.value}">${eduQualificationsInfoType.name}</option>
            </s:iterator>
        </select>
            <input type="hidden" class="selectHidden" id="eduQualifications_hidden"
                   value="${userRecord.eduQualifications}"/></td>
                <th><label>学位：</label></th>
                <td><select name="userRecord.eduLevel" id="eduLevel">
                <option value="">--请选择--</option>
                <s:iterator value="#baseDataMap.edu_level" var="eduLevelInfoType">
                    <option value="${eduLevelInfoType.value}">${eduLevelInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="eduLevel_hidden"
                   value="${userRecord.eduLevel}"/></td>
            </tr>
            <tr>
                <th><label>毕业时间：</label></th>
                <td><input name="userRecord.graduationTimeStr" id="graduationTime" value="${userRecord.graduationTime}"
                   type="text"
                   class="formText date"/></td>
                <th><label>毕业学校：</label></th>
                <td><input name="userRecord.graduationSchool" id="graduationSchool" value="${userRecord.graduationSchool}"
                   type="text"
                   class="formText" maxlength="25" valid="isChinese" errmsg="record.must_be_chinese"/></td>
            </tr>
            <tr>
                <th><label>专业：</label></th>
                <td><input name="userRecord.profession" id="profession" value="${userRecord.profession}" type="text"
                   class="formText" maxlength="30"/></td>
                <th><label>计算机水平：</label></th>
                <td> <input name="userRecord.computerLevel" id="computerLevel" value="${userRecord.computerLevel}" type="text"
                   class="formText" maxlength="30"/></td>
            </tr>
            <tr>
                <th><label>外语语种1：</label></th>
                <td><input name="userRecord.foreignLanguage1" id="foreignLanguage1" value="${userRecord.foreignLanguage1}"
                   type="text"
                   class="formText" maxlength="20"/></td>
                <th><label>外语水平1：</label></th>
                <td><input name="userRecord.languageLevel1" id="languageLevel1" value="${userRecord.languageLevel1}" type="text"
                   class="formText" maxlength="20"/></td>
            </tr>
            <tr>
                <th><label>外语语种2：</label></th>
                <td><input name="userRecord.foreignLanguage2" id="foreignLanguage2" value="${userRecord.foreignLanguage2}"
                   type="text"
                   class="formText" maxlength="20"/></td>
                <th><label>外语水平2：</label></th>
                <td><input name="userRecord.languageLevel2" id="languageLevel2" value="${userRecord.languageLevel2}" type="text"
                   class="formText" maxlength="20"/></td>
            </tr>
            <tr>
                <th><label>外语语种3：</label></th>
                <td><input name="userRecord.foreignLanguage3" id="foreignLanguage3" value="${userRecord.foreignLanguage3}"
                   type="text"
                   class="formText" maxlength="20"/></td>
                <th><label>外语水平3：</label></th>
                <td><input name="userRecord.languageLevel3" id="languageLevel3" value="${userRecord.languageLevel3}" type="text"
                   class="formText" maxlength="20"/></td>
            </tr>
            <tr>
                <th>特长：</th>
                <td colspan="3"><input name="userRecord.strongPoint" id="strongPoint" value="${userRecord.strongPoint}"
                               type="text"
                               class="formText" maxlength="300"/></td>
            </tr>
            <tr>
                <th>职务情况：</th>
                <td colspan="3"><textarea name="userRecord.postState" id="postState" rows="3" class="formTextarea" maxlength="300">${userRecord.postState}</textarea></td>
            </tr>
            <tr>
                <th>担保记录：</th>
                <td colspan="3"><textarea name="userRecord.vouchRecord" id="vouchRecord" rows="3"
                                  class="formTextarea"
                                   maxlength="300">${userRecord.vouchRecord}</textarea></td>
            </tr>
            <tr>
                <th>社会缴纳情况：</th>
                <td colspan="3"><textarea name="userRecord.socialSecurityState" id="socialSecurityState" rows="3"
                                  class="formTextarea" 
                                  maxlength="300">${userRecord.socialSecurityState}</textarea></td>
            </tr>
            <tr>
                <th>体检记录：</th>
                <td colspan="3"><textarea name="userRecord.healthCheckRecord" id="healthCheckRecord" rows="3"
                                  class="formTextarea" 
                                  maxlength="300">${userRecord.healthCheckRecord}</textarea></td>
            </tr>
             <tr>
                <th>备注：</th>
                <td colspan="3"><textarea name="userRecord.remark" id="remark" rows="3" class="formTextarea"
                                  maxlength="500">${userRecord.remark}</textarea></textarea></td>
            </tr>
            </tbody>
         </table>
         
         
<!--  
 <div class="small_title">简历</div>
 -->
<table style="display:none;" width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
     <tbody>
        <tr>
             <td colspan="4"><textarea class="formTextarea" name="userRecord.resumeInfo" id="resumeInfo" rows="8">${userRecord.resumeInfo}</textarea>
             	<input type="hidden" id="contentInfo" value="${userRecord.resumeInfo}"/>
               <%--  <script id="contentInfo" type="text/plain">${userRecord.resumeInfo}</script> --%>
             </td>
         </tr>
     </tbody>
</table>
   <div class="small_title">附件</div>
   <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
         <tbody>
            <tr>
                 <td colspan="3" id="fileAttachTd">
                 	<input id="file_upload" type="file" multiple="true" style="margin-top:5px;" />

            		<div id="queue" style="display: none;"></div>
                    		<input type="hidden" name="userRecord.attachment" id="attachment"  value="<s:property value="userRecord.attachment"/>"/>
                       	<div class="annex fl" >
                    		<ul id="attachUL"> </ul>
				    		<div class="clear"></div>
				 		 </div>
						            
                 <%-- <img src="${ctx}/flat/images/upload.png" style="margin-top:5px;" /> --%>
                 </td>
             </tr>
         </tbody>
    </table>
    
</div>
<!-- input div结束 -->
</div>
<div class="buttonArea">
            <input hideFocus="" value="保 存" type="button" class="formButton_green" onclick="ajaxCheckLoginName(true)" />
        
            <input hideFocus="" value="返 回" type="button" class="formButton_grey"  id="goBackBtn" />
</div>
</form>
</div>
<input type="hidden" id="from" value="${from}"/>
<input type="hidden" id="errorMsg" value="<s:property value="errorMsg"/>"/>
</body>
</html>