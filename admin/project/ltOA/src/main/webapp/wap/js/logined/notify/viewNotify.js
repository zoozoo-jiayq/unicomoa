	
	$(document).ready(function() {
		 var notifyId = $("#notifyId").val();
		 signCheck(notifyId);
		}
	)
	


	/**
	 * 签阅公告
	 * @param notifyId
	 * @return
	 */
	function signCheck(notifyId){
		dataParam = {
			    	'notifyVo.notifyId':notifyId
		};
	    $.ajax({
               type: 'post',
               url: basePath+"notify/getCheckNotify.action"+"?random="+Math.random(),
               data: dataParam,
               dataType: 'json',
               async:false,
               success:function(data){
               	 // alert(data);
               }
	    });
	}
	
	