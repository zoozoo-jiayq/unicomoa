<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
 	 <!--即时消息-->
 <div class="popMessage"    id="smsbox_panel" >
  	<div class="popTop"><a href="javascript:void();" class="fr" onclick="closeMessageBoxX();" ></a>即时消息</div>
    <div class="popMiddle">
    	<div class="popMidLeft tab picScroll-top">
        	<h2><a  style="cursor:pointer;" onclick="updateReadedByUserId()" class="readed">全部已阅</a></h2>
            <p class="up hd"><a style="cursor:pointer;"  class="next"></a></p>
            <div class="bd">
               <div id="smsbox_list_container" class="list-container"></div>
            </div>
            <p class="down hd"><a style="cursor:pointer;"   class="prev"></a></p>
        </div>
        <div class="popMidRight">
        	  <h2>
        	      <a style="cursor:pointer;"  onclick="updateReadedByUserId(true)" title="已阅以下即时消息"  class="readed">已阅</a>
        	      <a style="cursor:pointer;"   onclick="deleteByUserId()" class="deleted">删除</a>
        	  </h2>
            <!-- 回复内容 -->
               <ul>
                   <div id="smsbox_msg_container" class="center-chat"></div>
               </ul>
            <div class="submitArea">
            	<textarea id="smsbox_textarea"   cols="" rows="">请输入回复内容</textarea>
      			<p><input type="button" id="smsbox_send_msg"  onclick="sendMessage()" class="formButton_green" value="发送" /></p>
            </div>
        </div>
    </div>
    <div class="popBot"><em></em></div>
  </div>
	
	 
	 
        <div class="popMessage" id="no_sms">
           <div class="popTop"><a href="javascript:void();" class="fr" onclick='$("#no_sms").hide();' ></a>即时消息</div>
				      <div class="popMessage_nodata">
				          <dl class="no_data">
				               <dt>暂无新提醒</dt>
				               <dd>本窗口<em class="time"><span id="jsxxTimes">3</span></em>秒后自动关闭</dd>
				            </dl>
				      </div>
      				 <div class="popBot"><em></em></div>
	 </div>