<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
  <!--组织弹窗-->
  <div class="organi_box" id="org_pannel" >
		<div class="tab">
				<ul>
						<li class="current one"  id="current" onclick="searchUser(1)" >在线<span style="color:#555; margin-left:7px;">(<span id="onLineCount"></span>)</span></li>
						<li class="two" onclick="searchUser(2)" >全部</li>
				</ul>
		</div>
		<div class="organi_middle">
				<div class="organi_middle_box" >
				      <div class="tabContent" style="display:block;">
						  <ul id="groupUserTree" class="ztree" style="width:226px;">
			              </ul>
					  </div>
				</div>
		</div>
		<div class="organi_footer"></div>
  </div>
   <script type="text/javascript" >
		   $(document).ready(function(){
			  $(".one").click(function(){
			     if($(".tab").has(".tab_cur")){
					 $(".tab").removeClass("tab_cur");
				 }
			  });
			  $(".two").click(function(){
					 $(".tab").addClass("tab_cur");
			  });
		   });
  </script>