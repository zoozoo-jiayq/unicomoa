<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>detailRecord</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!--css-->
    <%
    String basePath = "http://192.168.10.28:8080/qytx-oa-mysql/" ;
    request.setAttribute("ctx", basePath);
    String loginUserNow=request.getParameter("loginUserNow");
    request.setAttribute("loginUserNow", loginUserNow);
%>   

	<link href="${ctx}css/wapRecord/wapRecord.css" rel="stylesheet">
	<script src="../../js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}js/logined/wapRecord/detailRecore.js"></script>
	<%-- <script type="text/javascript" src="${ctx}js/logined/wapRecord/onlineRecord.js"></script> --%>
<script>
		  var basePath="${ctx}";
		  var loginUserNow="${loginUserNow}";   //从url中获取当前登录人的id
		  	
		  $("#backImg").click(function(){
				window.history.back()
			})
</script>

</head>
<input type="hidden" id="userId" value="${userRecord.userInfo.userId}">
<input type="hidden" id="treeType" value="2">
<input type="hidden" id="id" value="${userRecord.id}">
<input type="hidden" id="indexTab" value="${index}">
<body>
   	<div class="header-bar">
			<i class="icons icon-18x18 icon-return" id="backImg"></i>
			<span class="title">档案详情</span>
	</div>
		<!--end .header-bar-->
			<div class="container-wrapper">	
			<div class="head-portrait-box">
				<span class="img-area">
					<img src="${ctx}flat/plugins/form/skins/default/meeting.png" alt="img">
				</span>
				<p class="name">${userRecord.userName}</p>
			</div>
			<!--end .head-portrait-box-->
			<ul class="tab">
				<li><span class="current">基本信息</span></li>
				<li><span>职位</span></li>
				<li><span>联系</span></li>
				<li><span>教育</span></li>
				<li><span>其他</span></li>
			</ul>
			<!--end .tab-->
			<div class="detail-list tab-content">
				<ul>
					<li>
						<span class="title">部门：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.groupNames==''}">无</c:when>
		                         <c:otherwise>${userRecord.groupNames}</c:otherwise>
                   			</c:choose>
						
						</span>
					</li>
					<li>
						<span class="title">档案编号：</span>
						<span class="txt block">
						    <c:choose>
		                         <c:when test="${userRecord.recordNo==''}">无</c:when>
		                         <c:otherwise>${ userRecord.recordNo}</c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">身份证号：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.identityNo==''}">无</c:when>
		                         <c:otherwise>${ userRecord.identityNo}</c:otherwise>
                   			</c:choose>
						
						</span>
					</li>
					<li>
						<span class="title">工号：</span>
						<span class="txt block">
						  <c:choose>
		                         <c:when test="${userRecord.workNo==''}">无</c:when>
		                         <c:otherwise>${ userRecord.workNo}</c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">性别：</span>
						<span class="txt block">
						  <c:choose>
		                         <c:when test="${userRecord.sex==null}">无</c:when>
		                         <c:otherwise>
		                         	<s:if test="%{userRecord.sex==1}">
						                	男
						            </s:if>
						            <s:if test="%{userRecord.sex==0}">
						               	 	女
						            </s:if>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">年龄(岁)：</span>
						<span class="txt block">
						  <c:choose>
		                         <c:when test="${userRecord.age==null}">无</c:when>
		                         <c:otherwise>${userRecord.age}</c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">出生日期：</span>
						<span class="txt block">
						 <c:choose>
		                         <c:when test="${userRecord.birthDay==null}">无</c:when>
		                         <c:otherwise>${ userRecord.birthDay}</c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">年休假(天)：</span>
						<span class="txt block">
						<c:choose>
		                         <c:when test="${userRecord.yearHoliday==null}">无</c:when>
		                         <c:otherwise>${ userRecord.yearHoliday}</c:otherwise>
                   		</c:choose>
						</span>
					</li>
					<li>
						<span class="title">籍贯：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.nativityPlaceSelect==''}">
		                         	 <c:choose>
				                         <c:when test="${userRecord.nativityPlace==''}">无</c:when>
                   					</c:choose>
		                         </c:when>
		                         <c:otherwise>${userRecord.nativityPlaceSelect}${userRecord.nativityPlace}</c:otherwise>
                   			</c:choose>
						
						</span>
					</li>
					<li>
						<span class="title">民族：</span>
						<span class="txt block">
						    <c:choose>
		                         <c:when test="${userRecord.nationality==''}">无</c:when>
		                         <c:otherwise>${ userRecord.nationality}</c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">婚姻状况：</span>
						<span class="txt block">
							<c:choose>
								
		                         <c:when test="${userRecord.marriageStatus != ''}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.marriage_status" var="marriageStatusInfoType">
			                		<s:if test="%{userRecord.marriageStatus==#marriageStatusInfoType.value}">
			                   		 ${marriageStatusInfoType.name}
			                		</s:if>
		            				</s:iterator>
		                         </c:otherwise>
                   			</c:choose> 
		
						</span>
					</li>
					<li>
						<span class="title">健康状况：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.healthInfo==''}">无</c:when>
		                         <c:otherwise>${ userRecord.healthInfo}</c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">政治面貌：</span>
						<span class="txt block">
			            	<c:choose>
		                         <c:when test="${userRecord.politicsFace == null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.politics_face" var="politicsFaceInfoType">
					                	<s:if test="%{userRecord.politicsFace==#politicsFaceInfoType.value}">
					                    ${politicsFaceInfoType.name}
					                	</s:if>
			            			</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">入党时间：</span>
						<span class="txt block">
						
							<c:choose>
		                         <c:when test="${userRecord.partyTime==null}">无</c:when>
		                         <c:otherwise>
		                         	<fmt:formatDate value="${userRecord.partyTime}" pattern="yyyy-MM-dd"/>
		                         </c:otherwise>
                   			</c:choose>
							
						</span>
					</li>
					<li>
						<span class="title">户口类型：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.registeredType==null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.registered_type" var="registeredTypeInfoType">
					                <s:if test="%{userRecord.registeredType==#registeredTypeInfoType.value}">
					                    ${registeredTypeInfoType.name}
					                </s:if>
					            	</s:iterator>
		                         
		                         </c:otherwise>
                   			</c:choose>
						
							
							
						</span>
					</li>
					<li>
						<span class="title">户口所在地：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.registeredAddress==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.registeredAddress}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
				</ul>
			</div>
			<!--end .detail-list-->
			
			<div class="detail-list tab-content hide">
				<ul>
					<li>
						<span class="title">工种：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.workType==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.workType}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">行政级别：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.administrationLevel==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.administrationLevel}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">员工类型：</span>
						<span class="txt block">
			            	<c:choose>
		                         <c:when test="${userRecord.userType==null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.user_type" var="userTypeInfoType">
					                <s:if test="%{userRecord.userType==#userTypeInfoType.value}">
					                    ${userTypeInfoType.name}
					                </s:if>
					            	</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">职务：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.job==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.job}
		                         </c:otherwise>
                   			</c:choose>
							
						</span>
					</li>
					
					<li>
						<span class="title">职称：</span>
						<span class="txt block">
			           		<c:choose>
		                         <c:when test="${userRecord.jobTitle==null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.job_title" var="jobTitleInfoType">
					                <s:if test="%{userRecord.jobTitle==#jobTitleInfoType.value}">
					                    ${jobTitleInfoType.name}
					                </s:if>
					           		</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">入职时间：</span>
						<span class="txt block">
								<c:choose>
		                         <c:when test="${userRecord.joinTime == null}">无</c:when>
		                         <c:otherwise>
		                         	<fmt:formatDate value="${userRecord.joinTime}" pattern="yyyy-MM-dd"/>
		                         </c:otherwise>
                   				</c:choose>
						</span>
					</li>
					<li>
						<span class="title">职称级别：</span>
						<span class="txt block">
							
			           		 
			           		 <c:choose>
		                         <c:when test="${userRecord.jobTitleLevel == null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.job_title_level" var="jobTitleLevelInfoType">
					                <s:if test="%{userRecord.jobTitleLevel==#jobTitleLevelInfoType.value}">
					                    ${jobTitleLevelInfoType.name}
					                </s:if>
					           		</s:iterator>
		                         </c:otherwise>
                   				</c:choose>
						</span>
					</li>
					<li>
						<span class="title">单位工龄(年)：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.companyWorkAge == null}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.companyWorkAge}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">岗位：</span>
						<span class="txt block">
			            	<c:choose>
		                         <c:when test="${userRecord.station== null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.station" var="stationInfoType">
					                <s:if test="%{userRecord.station==#stationInfoType.value}">
					                    ${stationInfoType.name}
					                </s:if>
					            	</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">起薪时间：</span>
						<span class="txt block"> 
							<c:choose>
		                         <c:when test="${userRecord.startPayTime == null}">无</c:when>
		                         <c:otherwise>
		                         	<fmt:formatDate value="${userRecord.startPayTime}" pattern="yyyy-MM-dd"/> 
		                         </c:otherwise>
                   			</c:choose>
						 </span>
					</li>
					<li>
						<span class="title">在职状态：</span>
						<span class="txt block">
							
			            	
			            	<c:choose>
		                         <c:when test="${userRecord.workStatus == null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.work_status" var="workStatusInfoType">
					                <s:if test="%{userRecord.workStatus==#workStatusInfoType.value}">
					                    ${workStatusInfoType.name}
					                </s:if>
					            	</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">总工龄(年)：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.totalWorkAge == null}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.totalWorkAge}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">参加工作时间：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.joinWorkTime == null}">无</c:when>
		                         <c:otherwise>
		                         	<fmt:formatDate value="${userRecord.joinWorkTime}" pattern="yyyy-MM-dd"/>   
		                         </c:otherwise>
                   			</c:choose>
						
						</span>
					</li>
				</ul>
			</div>
			<!--end .detail-list-->
			
			<div class="detail-list tab-content hide">
				<ul>
					<li>
						<span class="title">联系电话：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.phone2==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.phone2}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">手机号码：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.phone==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.phone}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">电子邮件：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.email==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.email}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">QQ：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.qq==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.qq}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">家庭地址：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.homeAddress==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.homeAddress}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">其他联系方式：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.otherContactWay==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.otherContactWay}
		                         </c:otherwise>
                   			</c:choose>
						<span>
					</li>
					<li>
						<span class="title">开户行1：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.bankName1==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.bankName1}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">账号1：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.bankNo1==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.bankNo1}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">开户行2：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.bankName2==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.bankName2}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">账号2：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.bankNo2==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.bankNo2}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
				</ul>
			</div>
			<!--end .detail-list-->
			
			<div class="detail-list tab-content hide">
				<ul>
					<li>
						<span class="title">学历：</span>
						<span class="txt block">
						
		            	
		            	<c:choose>
		                         <c:when test="${userRecord.eduQualifications == null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.edu_qualifications" var="eduQualificationsInfoType">
					                <s:if test="%{userRecord.eduQualifications==#eduQualificationsInfoType.value}">
					                    ${eduQualificationsInfoType.name}
					                </s:if>
		            				</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					
					<li>
						<span class="title">专业：</span>
						<span class="txt block">
						
							<c:choose>
		                         <c:when test="${userRecord.profession==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.profession}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					
					<li>
						<span class="title">学位：</span>
						<span class="txt block">
							
		            		
		            		<c:choose>
		                         <c:when test="${userRecord.eduLevel==null}">无</c:when>
		                         <c:otherwise>
		                         	<s:iterator value="#baseDataMap.edu_level" var="eduLevelInfoType">
					                	<s:if test="%{userRecord.eduLevel==#eduLevelInfoType.value}">
					                    	${eduLevelInfoType.name}
					                	</s:if>
				            		</s:iterator>
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">毕业时间：</span>
						<span class="txt block">
						
							<c:choose>
		                         <c:when test="${userRecord.graduationTime == null}">无</c:when>
		                         <c:otherwise>
		                         	<fmt:formatDate value="${userRecord.graduationTime}" pattern="yyyy-MM-dd"/>
		                         </c:otherwise>
                   			</c:choose>
						
						</span>
					</li>
					<li>
						<span class="title">毕业学校：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.graduationSchool==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.graduationSchool}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					
					<li>
						<span class="title">计算机水平：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.computerLevel==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.computerLevel}
		                         </c:otherwise>
                   			</c:choose>
						<span>
					</li>
					<li>
						<span class="title">外语语种1：</span>
						<span class="txt block">
						
							<c:choose>
		                         <c:when test="${userRecord.foreignLanguage1==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.foreignLanguage1}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">外语水平1：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.languageLevel1==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.languageLevel1}
		                         </c:otherwise>
                   			</c:choose>
						
						
						</span>
					</li>
					<li>
						<span class="title">外语语种2：</span>
						<span class="txt block">
						
							<c:choose>
		                         <c:when test="${userRecord.foreignLanguage2==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.foreignLanguage2}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">外语水平2：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.languageLevel2==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.languageLevel2}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">特长：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.strongPoint==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.strongPoint}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">职务情况：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.postState==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.postState}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">担保记录：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.vouchRecord==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.vouchRecord}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">社会缴纳情况：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.socialSecurityState==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.socialSecurityState}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">体检记录：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.healthCheckRecord==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.healthCheckRecord}
		                         </c:otherwise>
                   			</c:choose>
						</span>
					</li>
					<li>
						<span class="title">备注：</span>
						<span class="txt block">
							<c:choose>
		                         <c:when test="${userRecord.remark==''}">无</c:when>
		                         <c:otherwise>
		                         	${userRecord.remark}
		                         </c:otherwise>
                   			</c:choose>
						
						</span>
					</li>
				</ul>
			</div>
			<!--end .detail-list-->
			
			<div class="detail-list tab-content hide">
				<div class="row" id="goRewardList">
					<span>奖惩</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goStudyList">
					<span>学习</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goWorkList">
					<span>工作</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goRelationList">
					<span>关系</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goMoveList">
					<span>调动</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goPositionList">
					<span>职称</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<div class="row" id="goTrainList">
					<span>培训</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goLeaveList">
					<span>离职</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
				<div class="row" id="goAccessoryList">
					<span>附件</span>
					<i class="icons icon-18x18 icon-arrow-right"></i>
				</div>
				<!--end .row-->
			</div>
			<!--end .detail-list-->
		</div>
	
		<!--end .container-wrapper-->
		<script>
			//tab switch
			/*  var indexTab=$("#indexTab").val();
			alert(indexTab) */
			 /* if(indexTab==4){
				var index = 4;
			    $(".tab li span").removeClass("current");
			    $(".tab li span").eq(index).addClass("current");
					  
			    $(".tab-content").fadeIn(500);
			    $(".tab-content").addClass("hide");
			    $(".tab-content").eq(index).removeClass("hide");
			} */
			$(".tab li span").on("click",function(){
			    if($(this).hasClass("current"))
			    return;
					 
			    var index = $(".tab li span").index($(this));
			    $(".tab li span").removeClass("current");
			    $(".tab li span").eq(index).addClass("current");
					  
			    $(".tab-content").fadeIn(500);
			    $(".tab-content").addClass("hide");
			    $(".tab-content").eq(index).removeClass("hide");
			})
		</script>
</body>
</html>