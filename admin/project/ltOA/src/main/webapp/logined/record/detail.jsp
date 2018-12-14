<%--
功能:人事档案 新建和修改页面
版本:1.0
开发人员: 汤波涛
创建日期: 2013-03-22
修改日期: 2013-03-22
修改人员: 汤波涛
修改列表:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案-查看</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/record/show.js"></script>
<style type="text/css">
.inputTable th{ width:100px;}
</style>
</head>
<body>
<input type="hidden" id="userId" value="${userRecord.userInfo.userId}"/>
<input type="hidden" id="operType" value="${view}"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">档案详情   
    <div style="float: right;margin-top:8px" class="buttonArea"><input hidefocus="" value="相关信息" class="formButton_green" type="button" onClick="otherInfo()" style="margin-right: 0px"></div></div>
    <div class="content_form">
      <div class="small_title">基本信息</div>
      	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
                <tr>      
                  <th><label>姓名：</label></th>
                  <td>${userRecord.userName}</td>
                  <th rowspan="3">头像：</th>
                                    <td rowspan="3">
                                         <dl class="myphoto">
                                               <dt style="cursor:default;">
                                               	<img id="photoImg" src="${ctx}flat/plugins/form/skins/default/meeting.png" width="107" height="107" />
   	 											<input type="hidden" id="photoUrlHidden" value="${userRecord.photoUrl}" />
                                               </dt>    
                                         </dl>
                  </td>
                </tr>
      			<tr>
                    <th><label>部门：</label></th>
                    <td>${userRecord.groupNames}</td>
                </tr>
                <tr>
                    <th><label>角色：</label></th>
                    <td>${userRecord.roleNames}</td>
                </tr>
                <tr>
                    <th><label>档案编号：</label></th>
                    <td>${userRecord.recordNo}</td>
                    <th><label>工号：</label></th>
                    <td>${userRecord.workNo}</td>
                </tr>
                 <tr>
                    <th><label>登录名：</label></th>
                    <td>${userRecord.loginName}</td>
                    <th><label>曾用名：</label></th>
                    <td>${userRecord.nameOld}</td>
                </tr>
                 <tr>
                    <th><label>英文名：</label></th>
                    <td>${userRecord.nameEnglish}</td>
                    <th><label>性别：</label></th>
                    <td>
                    	<s:if test="%{userRecord.sex==1}">
			                	男
			            </s:if>
			            <s:if test="%{userRecord.sex==0}">
			               	 	女
			            </s:if>
                    </td>
                </tr>
                <tr>
                    <th><label>身份证号：</label></th>
                    <td>${userRecord.identityNo}</td>
                    <th><label>出生日期：</label></th>
                    <td>
						<span class="date"><fmt:formatDate value="${userRecord.birthDay}" pattern="yyyy-MM-dd"/></span>
			            <s:if test="%{userRecord.lunarBirthDay==1}">(农历)</s:if>
					</td>
                </tr>
                <tr>
                    <th><label>年龄(岁)：</label></th>
                    <td>${userRecord.age}</td>
                    <th><label>年休假(天)：</label></th>
                    <td>${userRecord.yearHoliday}</td>
                </tr>
                <tr>
                    <th><label>籍贯：</label></th>
                    <td>${userRecord.nativityPlaceSelect}${userRecord.nativityPlace}</td>
                    <th><label>民族：</label></th>
                    <td>${userRecord.nationality}</td>
                </tr>
                <tr>
                    <th><label>婚姻状况：</label></th>
                    <td>
                    	<s:iterator value="#baseDataMap.marriage_status" var="marriageStatusInfoType">
			                <s:if test="%{userRecord.marriageStatus==#marriageStatusInfoType.value}">
			                    ${marriageStatusInfoType.name}
			                </s:if>
		            	</s:iterator>
                    </td>
                    <th><label>健康状况：</label></th>
                    <td>${userRecord.healthInfo}</td>
                </tr>
      			 <tr>
                    <th><label>政治面貌：</label></th>
                    <td>
                    	<s:iterator value="#baseDataMap.politics_face" var="politicsFaceInfoType">
			                <s:if test="%{userRecord.politicsFace==#politicsFaceInfoType.value}">
			                    ${politicsFaceInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>
                    <th><label>入党时间：</label></th>
                    <td><fmt:formatDate value="${userRecord.partyTime}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th><label>户口类型：</label></th>
                    <td colspan="3">
                    	<s:iterator value="#baseDataMap.registered_type" var="registeredTypeInfoType">
			                <s:if test="%{userRecord.registeredType==#registeredTypeInfoType.value}">
			                    ${registeredTypeInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>
                 </tr>
      			 <tr>   
                    <th><label>户口所在地：</label></th>
                    <td colspan="3">${userRecord.registeredAddress}</td>
                </tr>
             </table>
       <div class="small_title">职位情况及联系方式</div>
          <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                 <tr>
                    <th>工种：</th>
                    <td>${userRecord.workType}</td>
                    <th>行政级别：</th>
                    <td>${userRecord.administrationLevel}</td>
                </tr>
				<tr>
                    <th>员工类型：</th>
                    <td>
                    	<s:iterator value="#baseDataMap.user_type" var="userTypeInfoType">
			                <s:if test="%{userRecord.userType==#userTypeInfoType.value}">
			                    ${userTypeInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>
                    <th>职务：</th>
                    <td>${userRecord.job}</td>
                </tr>
                <tr>
                    <th>职称：</th>
                    <td>
                    	<s:iterator value="#baseDataMap.job_title" var="jobTitleInfoType">
			                <s:if test="%{userRecord.jobTitle==#jobTitleInfoType.value}">
			                    ${jobTitleInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>      
                    <th>入职时间：</th>
                    <td><fmt:formatDate value="${userRecord.joinTime}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th>职称级别：</th>
                    <td>
                    	<s:iterator value="#baseDataMap.job_title_level" var="jobTitleLevelInfoType">
			                <s:if test="%{userRecord.jobTitleLevel==#jobTitleLevelInfoType.value}">
			                    ${jobTitleLevelInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>
                    <th>岗位：</th>
                    <td>
                    	<s:iterator value="#baseDataMap.station" var="stationInfoType">
			                <s:if test="%{userRecord.station==#stationInfoType.value}">
			                    ${stationInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>
                </tr>
				<tr>
                    <th>单位工龄(年)：</th>
                    <td>${userRecord.companyWorkAge}</td>
                    <th>起薪时间：</th>
                    <td> <fmt:formatDate value="${userRecord.startPayTime}" pattern="yyyy-MM-dd"/>  </td>
                </tr>
                <tr>
                    <th>在职状态：</th>
                    <td>
                    	<s:iterator value="#baseDataMap.work_status" var="workStatusInfoType">
			                <s:if test="%{userRecord.workStatus==#workStatusInfoType.value}">
			                    ${workStatusInfoType.name}
			                </s:if>
			            </s:iterator>
                    </td>
                    <th>总工龄(年)：</th>
                    <td>${userRecord.totalWorkAge}</td>
                </tr>
				<tr>
                    <th>参加工作时间：</th>
                    <td> <fmt:formatDate value="${userRecord.joinWorkTime}" pattern="yyyy-MM-dd"/>    </td>
                    <th>联系电话：</th>
                    <td>${userRecord.phone2}</td>
                </tr>
            
                <tr>
                    <th>手机号码：</th>
                    <td>${userRecord.phone}</td>
                    <th>MSN：</th>
                    <td>${userRecord.msn}</td>
                </tr>
				<tr>
                    <th>电子邮件：</th>
                    <td>${userRecord.email}</td>
                    <th>QQ：</th>
                    <td>${userRecord.qq}</td>
                </tr>
                <tr>
                    <th>家庭地址：</th>
                    <td colspan="3">${userRecord.homeAddress}</td>
                </tr>
				<tr>
                    <th>其他联系方式：</th>
                    <td colspan="3">${userRecord.otherContactWay}</td>
                </tr>
                <tr>
                    <th>开户行1：</th>
                    <td>${userRecord.bankName1}</td>
                    <th>帐号1：</th>
                    <td>${userRecord.bankNo1}</td>
                </tr>
				 <tr>
                    <th>开户行2：</th>
                    <td>${userRecord.bankName2}</td>
                    <th>帐号2：</th>
                    <td>${userRecord.bankNo2}</td>
                </tr>
            </tbody>
         </table>
	 <div class="small_title">教育情况</div>
         <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
            <tbody><tr>
                <th><label>学历：</label></th>
                <td>
                	<s:iterator value="#baseDataMap.edu_qualifications" var="eduQualificationsInfoType">
		                <s:if test="%{userRecord.eduQualifications==#eduQualificationsInfoType.value}">
		                    ${eduQualificationsInfoType.name}
		                </s:if>
		            </s:iterator>
                </td>
                <th><label>学位：</label></th>
                <td>
                	<s:iterator value="#baseDataMap.edu_level" var="eduLevelInfoType">
		                <s:if test="%{userRecord.eduLevel==#eduLevelInfoType.value}">
		                    ${eduLevelInfoType.name}
		                </s:if>
		            </s:iterator>
                </td>
            </tr>
            <tr>
                <th><label>毕业时间：</label></th>
                <td class="date"><fmt:formatDate value="${userRecord.graduationTime}" pattern="yyyy-MM-dd"/></td>
                <th><label>毕业学校：</label></th>
                <td>${userRecord.graduationSchool}</td>
            </tr>
            <tr>
                <th><label>专业：</label></th>
                <td>${userRecord.profession}</td>
                <th><label>计算机水平：</label></th>
                <td>${userRecord.computerLevel}</td>
            </tr>
            <tr>
                <th><label>外语语种1：</label></th>
                <td>${userRecord.foreignLanguage1}</td>
                <th><label>外语水平1：</label></th>
                <td>${userRecord.languageLevel1}</td>
            </tr>
            <tr>
                <th><label>外语语种2：</label></th>
                <td>${userRecord.foreignLanguage2}</td>
                <th><label>外语水平2：</label></th>
                <td>${userRecord.languageLevel2}</td>
            </tr>
            <tr>
                <th><label>外语语种3：</label></th>
                <td>${userRecord.foreignLanguage3}</td>
                <th><label>外语水平3：</label></th>
                <td>${userRecord.languageLevel3}</td>
            </tr>
            <tr>
                <th>特长：</th>
                <td colspan="3">${userRecord.strongPoint}</td>
            </tr>
            <tr>
                <th>职务情况：</th>
                <td colspan="3">${userRecord.postState}</td>
            </tr>
            <tr>
                <th>担保记录：</th>
                <td colspan="3">${userRecord.vouchRecord}</td>
            </tr>
             <tr>
                <th>社会缴纳情况：</th>
                <td colspan="3">${userRecord.socialSecurityState}</td>
            </tr>
            <tr>
                <th>体检记录：</th>
                <td colspan="3">${userRecord.healthCheckRecord}</td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="3">${userRecord.remark}</td>
            </tr>
            </tbody>
         </table>
    <div class="small_title"  style="display:none;">简历</div>
          <table width="100%"  style="display:none;" cellspacing="0" cellpadding="0" border="0" class="inputTable">
                <tbody>
                   <tr>
                        <td colspan="4">
							<s:if test="userRecord.resumeInfo==null||userRecord.resumeInfo==''">暂无简历</s:if>
						     ${userRecord.resumeInfo}
						</td>
                    </tr>
                </tbody>
           </table> 			
	<div class="small_title">附件</div>
          <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
                <tbody>
                   <tr>
                   		<input type="hidden" id="attachment" value="<s:property value="userRecord.attachment"/>"/>
                        <td colspan="3">
                        	<div class="annex"  id="attachmentDiv">
                              <ul>
                              </ul>
                          </div>
                        </td>
                    </tr>
                </tbody>
           </table>
   </div>
  </div>
  <c:if test="${param.form != 'show'}" >
  	<div class="buttonArea"  id ="buttonAreaDiv">
        <input type="button" onclick="javascript:history.back();" hidefocus="" class="formButton_grey" value="返回" /><span class="blue">点击返回，回到档案管理列表</span>
    </div>                             			
  </c:if>
</div>
</body>
</html>