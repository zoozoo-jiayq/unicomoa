<%--
功能:人事档案 搜索页面
版本:1.0
开发人员: 汤波涛
创建日期: 2013-03-25
修改日期: 2013-03-25
修改人员: 汤波涛
修改列表:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案-搜索</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}js/common/base.js"></script>
    <script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/treeNode.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common_select_group_role.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/search.js"></script>
    <style type="text/css">
		.inputTable th{ width:100px;}
	</style>
</head>
<body style="display: none">
<div class="formPage">
<form action="${ctx}logined/record/list.action" method="post" name="searchForm">
	<div class="formbg">

		<div class="big_title">查询条件</div>
			<div class="content_form">
<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
    <tr>
        <th><label>登录名：</label></th>
        <td><input name="userRecordSearchVo.loginName" id="loginName"
                   value="${userRecordSearchVo.loginName}" type="text" class="formText" maxlength="12"   /></td>
        <th>姓名：</th>
        <td><input name="userRecordSearchVo.userName" id="userName"
                   value="${userRecordSearchVo.userName}" type="text" class="formText" maxlength="10" 
                   valid="isChinese" errmsg="record.must_be_chinese"/></td>
    </tr>
    <tr>
        <th><label>曾用名：</label></th>
        <td><input name="userRecordSearchVo.nameOld" id="nameOld"
                   value="${userRecordSearchVo.nameOld}" type="text" class="formText" maxlength="10" 
                   valid="isChinese" errmsg="record.must_be_chinese" /></td>
        <th><label>编号：</label></th>
        <td><input name="userRecordSearchVo.recordNo" id="recordNo" value="${userRecordSearchVo.recordNo}"
                   type="text" class="formText" maxlength="20"    /></td>
    </tr>
    <tr>
        <th><label>英文名：</label></th>
        <td><input name="userRecordSearchVo.nameEnglish" id="nameEnglish" value="${userRecordSearchVo.nameEnglish}"
                   type="text" class="formText" maxlength="30" 
                  valid="isEnglishName"   errmsg="record.must_be_english"/></td>
        <th><label>性别：</label></th>
        <td>
            <select name="userRecordSearchVo.sex" id="sex" >
                <option value="">全部</option>
                <option value="1">男</option>
                <option value="0">女</option>
            </select>
            <input type="hidden" class="selectHidden" id="sex_hidden" value="${userRecordSearchVo.sex}"/>
        </td>
    </tr>
    <tr>
        <th><label>身份证号：</label></th>
        <td>
            <input onkeyup="validateNum(this);return false;"  name="userRecordSearchVo.identityNo" id="identityNo"
                   value="${userRecordSearchVo.identityNo}" type="text" class="formText" maxlength="18" 
                   valid="isIdCard" errmsg="record.must_be_idCard"/>
        </td>
        <th><label>出生日期：</label></th>
        <td><input type="text" name="userRecordSearchVo.birthDayStart" id="birthDayStart" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'birthDayEnd\')||\'%y-%M-%d\'}'})"
                   value="${userRecordSearchVo.birthDayStart}" class="formText Wdate date"  readonly="readonly"   />&nbsp;-&nbsp;<input type="text" name="userRecordSearchVo.birthDayEnd" id="birthDayEnd"
                   value="${userRecordSearchVo.birthDayEnd}" class="formText Wdate date" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'birthDayStart\')}',maxDate:'%y-%M-%d'}) " readonly="readonly" />
        </td>
    </tr>
    <tr>
        <th><label>员工类型：</label></th>
        <td>
            <select name="userRecordSearchVo.userType" id="userType">
                <option value="">全部</option>
                <s:iterator value="#baseDataMap.user_type" var="userTypeInfoType">
                    <option value="${userTypeInfoType.value}">${userTypeInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="userType_hidden" value="${userRecordSearchVo.userType}"/>
        </td>
        <th><label>入职时间：</label></th>
        <td><input type="text" name="userRecordSearchVo.joinTimeStart" id="joinTimeStart" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'joinTimeEnd\')||\'%y-%M-%d\'}'})"
                   value="${userRecordSearchVo.joinTimeStart}" class="formText Wdate date"  readonly="readonly"   />&nbsp;-&nbsp;<input type="text" name="userRecordSearchVo.joinTimeEnd" id="joinTimeEnd"
                              onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'joinTimeStart\')}',maxDate:'%y-%M-%d'}) "  value="${userRecordSearchVo.joinTimeEnd}" class="formText Wdate date"  readonly="readonly"  />
        </td>
    </tr>
    <tr>
        <th><label>工号：</label></th>
        <td><input name="userRecordSearchVo.workNo" id="workNo"
                   value="${userRecordSearchVo.workNo}" type="text" class="formText"  maxlength="10"  /></td>
        <th><label>工种：</label></th>
        <td><input name="userRecordSearchVo.workType" id="workType" value="${userRecordSearchVo.workType}" type="text"
                   class="formText" maxlength="10" /></td>
    </tr>
    <tr>
        <th><label>部门：</label></th>
        <td>
            <input name="userRecordSearchVo.groupIds" id="groupIds" value="${userRecordSearchVo.groupIds}"
                   type="hidden"/>
            <input class="readOnlyText" name="userRecordSearchVo.groupNames" id="groupNames" value="${userRecordSearchVo.groupNames}"
                   type="text" class="readOnlyText" readonly="readonly" />
            <span class="addMember auto_addMember">
            <a class="icon_add" href="javascript:selectGroup('groupNames','groupIds')">选择</a>
            <a class="icon_clear" href="#" onclick="clearInput('groupNames','groupIds')">清空</a>
            </span>
        </td>
        <th><label>学历：</label></th>
        <td><select name="userRecordSearchVo.eduQualifications" id="eduQualifications">
            <!-- 学历：1:无，2：小学 3：初中 4：高中 5：中专 6:大专 7：本科  -->
            <option value="">全部</option>
            <s:iterator value="#baseDataMap.edu_qualifications" var="eduQualificationsInfoType">
                <option value="${eduQualificationsInfoType.value}">${eduQualificationsInfoType.name}</option>
            </s:iterator>
        </select>
            <input type="hidden" id="eduQualifications_hidden"
                   value="${userRecordSearchVo.eduQualifications}"/>
        </td>
    </tr>
    <tr>
        <th><label>学位：</label></th>
        <td>
            <select name="userRecordSearchVo.eduLevel" id="eduLevel">
                <option value="">全部</option>
                <s:iterator value="#baseDataMap.edu_level" var="eduLevelInfoType">
                    <option value="${eduLevelInfoType.value}">${eduLevelInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="eduLevel_hidden"
                   value="${userRecordSearchVo.eduLevel}"/>
        </td>
        <th><label>专业：</label></th>
        <td><input name="userRecordSearchVo.profession" id="profession" value="${userRecordSearchVo.profession}"
                   type="text" class="formText" size="30"  maxlength="20"   /></td>
    </tr>
    <tr>
        <th><label>民族：</label></th>
        <td>
            <input type="hidden" class="selectHidden" id="nationality_hidden" value="${userRecordSearchVo.nationality}"/>
            <select name="userRecordSearchVo.nationality" id="nationality" >
                <option selected="selected" value="">全部</option>
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
        <th><label>籍贯：</label></th>
        <td>
            <input name="userRecordSearchVo.nativityPlace" id="nativityPlace"
                   value="${userRecordSearchVo.nativityPlace}" type="text"
                   class="formText" size="30" maxlength="20"    />
        </td>
    </tr>
    <tr> 
        <th><label>婚姻状况：</label></th>
        <td><select name="userRecordSearchVo.marriageStatus" id="marriageStatus">
            <option value="">全部</option>
            <s:iterator value="#baseDataMap.marriage_status" var="marriageStatusInfoType">
                <option value="${marriageStatusInfoType.value}">${marriageStatusInfoType.name}</option>
            </s:iterator>
        </select>
            <input type="hidden" class="selectHidden" id="marriageStatus_hidden"
                   value="${userRecordSearchVo.marriageStatus}"/>
        </td>
        <th><label>政治面貌：</label></th>
        <td>
            <select name="userRecordSearchVo.politicsFace" id="politicsFace">
                <option value="">全部</option>
                <s:iterator value="#baseDataMap.politics_face" var="politicsFaceInfoType">
                    <option value="${politicsFaceInfoType.value}">${politicsFaceInfoType.name}</option>
                </s:iterator>
            </select>
            <input type="hidden" class="selectHidden" id="politicsFace_hidden"
                   value="${userRecordSearchVo.politicsFace}"/>
        </td>
    </tr>
    <tr>
        <th><label>入党时间：</label></th>
        <td><input name="userRecordSearchVo.partyTimeStart" id="partyTimeStart" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'partyTimeEnd\')||\'%y-%M-%d\'}'})"
                   value="${userRecordSearchVo.partyTimeStart}" type="text" class="formText Wdate date"  readonly="readonly"  />&nbsp;-&nbsp;<input name="userRecordSearchVo.partyTimeEnd" id="partyTimeEnd"
                   value="${userRecordSearchVo.partyTimeEnd}" type="text" class="formText Wdate date"   readonly="readonly" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'partyTimeStart\')}',maxDate:'%y-%M-%d'}) " /></td>
        <th><label>联系电话：</label></th>
        <td>
            <input type="text" class="formText"  onkeyup="validateNum(this);return false;" name="userRecordSearchVo.phone2" id="phone2"
                   value="${userRecordSearchVo.phone2}"maxlength="12"  />
        </td>
    </tr>
    <tr>
        <th><label>角色：</label></th>
        <td colspan="3">
            <input name="userRecordSearchVo.roleIds" id="roleIds" value="${userRecordSearchVo.roleIds}"
                   type="hidden"/>
            <input name="userRecordSearchVo.roleNames" style="width:85%" id="roleNames" value="${userRecordSearchVo.roleNames}"
                   type="text" class="readOnlyText"/>
            <span class="addMember auto_addMember">
            <a class="icon_add" href="javascript:selectRole('roleNames','roleIds')">选择</a>
            <a class="icon_clear" href="#" onclick="clearInput('roleNames','roleIds')">清空</a>
            </span>
        </td>
    </tr>
</table>
<div class="small_title">
    <a href="javascript:void(0)" onclick="showHide('explainTable')" class="shareShow">更多查询条件</a>
</div>
    <table id="explainTable" style="display:none" width="100%"  cellspacing="0" cellpadding="0" border="0" class="inputTable">
    <tbody>
        <tr>
            <th>行政级别：</th>
            <td><input name="userRecordSearchVo.administrationLevel" id="administrationLevel"
                       value="${userRecordSearchVo.administrationLevel}" type="text" class="formText"  maxlength="20"    /></td>
            <th>手机号码：</th>
            <td>
                <input type="text" class="formText"  onkeyup="validateNum(this);return false;"  name="userRecordSearchVo.phone" id="phone"
                       value="${userRecordSearchVo.phone}" maxlength="11"   />
            </td>
        </tr>
        <tr>
            <th>电子邮件：</th>
            <td>
                <input type="text" class="formText" name="userRecordSearchVo.email" id="email"
                       value="${userRecordSearchVo.email}" maxlength="20"   />
            </td>
            <th>MSN：</th>
            <td>
                <input type="text" onkeyup="validateNum(this);return false;"    class="formText" name="userRecordSearchVo.msn" id="msn"
                       value="${userRecordSearchVo.msn}" maxlength="20"   />
            </td>
        </tr>
        <tr>
            <th>QQ：</th>
            <td>
                <input type="text"  onkeyup="validateNum(this);return false;"  class="formText" name="userRecordSearchVo.qq" id="qq"
                       value="${userRecordSearchVo.qq}" maxlength="10"   />
            </td>
            <th>家庭地址：</th>
            <td>
                <input name="userRecordSearchVo.homeAddress" id="homeAddress" value="${userRecordSearchVo.homeAddress}"
                       type="text" class="formText" maxlength="20"   />
            </td>
        </tr>
        <tr>
            <th>职务：</th>
            <td>
                <input name="userRecordSearchVo.job" id="job" value="${userRecordSearchVo.job}"
                       type="text" class="formText" maxlength="10"   />
            </td>
            <th>职称：</th>
            <td>
                <select name="userRecordSearchVo.jobTitle" id="jobTitle">
                    <option value="">全部</option>
                    <s:iterator value="#baseDataMap.job_title" var="jobTitleInfoType">
                        <option value="${jobTitleInfoType.value}">${jobTitleInfoType.name}</option>
                    </s:iterator>
                </select>
                <input type="hidden" class="selectHidden" id="jobTitle_hidden" value="${userRecordSearchVo.jobTitle}"/>
            </td>
        </tr>
        <tr>
            <th>参加工作时间：</th>
            <td>
                <input name="userRecordSearchVo.joinWorkTimeStart" id="joinWorkTimeStart" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'joinWorkTimeEnd\')||\'%y-%M-%d\'}'})"
                       value="${userRecordSearchVo.joinWorkTimeStart}" type="text" class="formText Wdate date" readonly="readonly"   />&nbsp;-&nbsp;<input name="userRecordSearchVo.joinWorkTimeEnd" id="joinWorkTimeEnd"
                       value="${userRecordSearchVo.joinWorkTimeEnd}" type="text" class="formText Wdate date"  readonly="readonly" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'joinWorkTimeStart\')}',maxDate:'%y-%M-%d'}) " />
            </td>
            <th>毕业时间：</th>
            <td>
                <input name="userRecordSearchVo.graduationTimeStart" id="graduationTimeStart" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'graduationTimeEnd\')||\'%y-%M-%d\'}'})"
                       value="${userRecordSearchVo.graduationTimeStart}" type="text" class="formText Wdate date"  readonly="readonly"/>&nbsp;-&nbsp;<input name="userRecordSearchVo.graduationTimeEnd" id="graduationTimeEnd"
                       value="${userRecordSearchVo.graduationTimeEnd}" type="text" class="formText Wdate date"  readonly="readonly"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'graduationTimeStart\')}',maxDate:'%y-%M-%d'}) " />
            </td>
        </tr>
        <tr>
            <th>单位工龄(年)：</th>
            <td>
                <input onkeyup="validateNum(this);return false;"  name="userRecordSearchVo.companyWorkAge" id="companyWorkAge"
                       value="${userRecordSearchVo.companyWorkAge}" type="text"
                       class="formText" maxlength="3"    />
            </td>
            <th>总工龄(年)：</th>
            <td>
                <input  onkeyup="validateNum(this);return false;" name="userRecordSearchVo.totalWorkAge" id="totalWorkAge"
                       value="${userRecordSearchVo.totalWorkAge}" type="text"
                       class="formText" maxlength="3"   />
            </td>
        </tr>
        <tr>
            <th>健康状况：</th>
            <td>
                <input type="text" class="formText" name="userRecordSearchVo.healthInfo" id="healthInfo"
                       value="${userRecordSearchVo.healthInfo}" maxlength="20"   />
            </td>
            <th>户口所在地：</th>
            <td>
                <input name="userRecordSearchVo.registeredAddress" id="registeredAddress"
                       value="${userRecordSearchVo.registeredAddress}"
                       type="text" class="formText" maxlength="20"  />
            </td>
        </tr>
        <tr>
            <th>毕业学校：</th>
            <td>
                <input name="userRecordSearchVo.graduationSchool" id="graduationSchool"
                       value="${userRecordSearchVo.graduationSchool}"
                       type="text"
                       class="formText" maxlength="20" />
            </td>
            <th>计算机水平：</th>
            <td>
                <input name="userRecordSearchVo.computerLevel" id="computerLevel"
                       value="${userRecordSearchVo.computerLevel}" type="text"
                       class="formText" maxlength="10"   />
            </td>
        </tr>
        <tr>
            <th>起薪时间：</th>
            <td>
                <input name="userRecordSearchVo.startPayTimeStart" id="startPayTimeStart" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'startPayTimeEnd\')||\'%y-%M-%d\'}'})"
                       value="${userRecordSearchVo.startPayTimeStart}" type="text" class="formText Wdate date" readonly="readonly"  />&nbsp;-&nbsp;<input name="userRecordSearchVo.startPayTimeEnd" id="startPayTimeEnd"
                       value="${userRecordSearchVo.startPayTimeEnd}" type="text" class="formText Wdate date"  readonly="readonly" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startPayTimeStart\')}',maxDate:'%y-%M-%d'}) " />
            </td>
            <th>外语语种1：</th>
            <td>
                <input name="userRecordSearchVo.foreignLanguage1" id="foreignLanguage1"
                       value="${userRecordSearchVo.foreignLanguage1}"
                       type="text"
                       class="formText" maxlength="20"  />
            </td>
        </tr>
        <tr>
            <th>外语语种2：</th>
            <td>
                <input name="userRecordSearchVo.foreignLanguage2" id="foreignLanguage2"
                       value="${userRecordSearchVo.foreignLanguage2}"
                       type="text"
                       class="formText" maxlength="20"    />
            </td>
            <th>外语语种3：</th>
            <td>
                <input name="userRecordSearchVo.foreignLanguage3" id="foreignLanguage3"
                       value="${userRecordSearchVo.foreignLanguage3}"
                       type="text"
                       class="formText" maxlength="20"   />
            </td>
        </tr>
        <tr>
            <th>外语水平1：</th>
            <td>
                <input name="userRecordSearchVo.languageLevel1" id="languageLevel1"
                       value="${userRecordSearchVo.languageLevel1}" type="text"
                       class="formText" maxlength="20"   />
            </td>
            <th>外语水平2：</th>
            <td>
                <input name="userRecordSearchVo.languageLevel2" id="languageLevel2"
                       value="${userRecordSearchVo.languageLevel2}" type="text"
                       class="formText" maxlength="20"   />
            </td>
        </tr>
        <tr>
            <th>外语水平3：</th>
            <td colspan="3">
                <input name="userRecordSearchVo.languageLevel3" id="languageLevel3"
                       value="${userRecordSearchVo.languageLevel3}" type="text"
                       class="formText" maxlength="20"    />
            </td>
        </tr>
        </tbody>
    </table>
</div>
	</div>

			<div class="buttonArea">
			    <input class="formButton_green" value="查 询" type="submit"/><span class="blue">点击进行查询</span>
			</div>
		</form>
</div>
</body>
</html>