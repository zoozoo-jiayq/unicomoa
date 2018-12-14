<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--  -->
     <div class="formPage">
            <div class="formbg">
               <div class="content_form" style="padding-top: 20px; min-height: 310px;background: #F5F5DC">
                  <div id="fileManager"><input id="file_upload" name="fileupload" type="file"  multiple="true"/></div>
                   <div class="annex">
						               <ul id="attachUL">
						                    
						              </ul>
					              </div>
                      <div class="clear"></div>
                 </div>     
          </div>
      </div>
    <!--  -->
    
    <input type="hidden" id="currentUser" value="${loginUser.userName}" />
<script type="text/javascript" src="${ctx }js/logined/publicDom/upload.js" ></script>
<script type="text/javascript">
	if($("#history").val()=='history'){
		$("#fileManager").hide();
	}
	</script>   