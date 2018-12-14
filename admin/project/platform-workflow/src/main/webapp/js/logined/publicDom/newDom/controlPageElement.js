jQuery(document).ready(function($){
	(function(){
		//$(".tab li").unbind();
		var taskName = $("#taskName").val();
		var source = $("#source").val();
		var history = $("#history").val();
		/*
		 *发文的时候不显示是系统外收外/系统内收文
		 *收文显示收文单，发文显示发文单
		 */
		var instanceId = $("#instanceId").val();
		if(instanceId.indexOf("dispatch")==0){
			$("#sourceSpan").hide();
			$("#shouwenOrFawen").html("<i></i>发文单");
		}else{
			$("#shouwenOrFawen").html("<i></i>收文单");
		}

		function selectLi(liId){
			$(".tab li").removeClass("current");
			$("#"+liId).addClass("current");
		}

		function selectDiv(divId){
			$(".tabContent").hide();
			$("#"+divId).show();
		}
		
		$("#liform").click(function(){
			selectLi("liform");
			selectDiv("customForm_div");
		});
		$("#liattach").click(function(){
			selectLi("liattach");
			selectDiv("attach_div");
		});
		$("#lidoc").click(function(){
			selectLi("lidoc");
			selectDiv("doc_div");
			if(!docflag){
				window["ntkoframe"].location.reload();
				docflag = true;
			}
		});
		$("#lipdf").click(function(){
			selectLi("lipdf");
			selectDiv("pdf_div");
		});
		$("#lihistoryform").click(function(){
			selectLi("lihistoryform");
			selectDiv("history_form_div");
			iframehistoryform.window.location.reload();
		});
		$("#libaseinfo").click(function(){
			selectLi("libaseinfo");
			selectDiv("baseInfo_div");
		});
		
		$("#libaseinfo").click();
	})();
});





