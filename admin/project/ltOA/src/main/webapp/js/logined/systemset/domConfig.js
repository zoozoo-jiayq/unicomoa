jQuery(document).ready(function($){
	$("#defaultEmail").click(function(){
		defaultEmail();
	})
	
	function checkboxUtil(str,objs){
		for(var i=0; i<objs.length; i++){
			if(str.indexOf($(objs[i]).val())>=0){
				$(objs[i]).attr("checked",true);
			}
		}
	}

	var gatherRegister = $("#g1").val();
	if(gatherRegister){
 		var objs = $("input[name='configs.dom_gather_register']");
 		checkboxUtil(gatherRegister,objs);
	}
	
	var gatherLeaderApprove = $("#g2").val();
	if(gatherLeaderApprove){
 		objs = $("input[name='configs.dom_gather_approve']");
 		checkboxUtil(gatherLeaderApprove,objs);
	}

	var gatherForceRead = $("#g3").val();
	if(gatherForceRead){
 		objs = $("input[name='configs.force_read']");
 		checkboxUtil(gatherForceRead,objs);
	}

	var gatherautoZip = $("#g4").val();
	if(gatherautoZip){
 		objs = $("input[name='configs.dom_gather_zip']");
 		checkboxUtil(gatherautoZip,objs);
	}

	var disNigao = $("#d1").val();
	if(disNigao){
 		objs = $("input[name='configs.dom_dis_nigao']");
 		checkboxUtil(disNigao,objs);
	}

	var disApprove = $("#d2").val();
	if(disApprove){
 		objs = $("input[name='configs.dom_dis_hegao']");
 		checkboxUtil(disApprove,objs);
	}

	var disTaohong = $("#d3").val();
	if(disTaohong){
 		objs = $("input[name='configs.dom_dis_red']");
 		checkboxUtil(disTaohong,objs);
	}

	var disautoZip = $("#d4").val();
	if(disautoZip){
 		objs = $("input[name='configs.dom_dis_zip']");
 		checkboxUtil(disautoZip,objs);
	}
	
	var disc1 = $("#c1").val();
	if(disc1){
 		objs = $("input[name='configs.approve_widget']");
 		checkboxUtil(disc1,objs);
	}
	
	var disc2 = $("#c2").val();
	if(disc2){
 		objs = $("input[name='configs.reader_widget']");
 		checkboxUtil(disc2,objs);
	}
	
	var disc3 = $("#c3").val();
	if(disc3){
 		objs = $("input[name='configs.approve_comment']");
 		checkboxUtil(disc3,objs);
	}
	
	var disc4 = $("#c4").val();
	if(disc4){
 		objs = $("input[name='configs.notice_update_password']");
 		checkboxUtil(disc4,objs);
	}
	
	$("#sure").click(function(){
		var g1 = "";
		$("input[name='configs.dom_gather_register']:checked").each(function(){
			g1+=$(this).val()+",";
		});
		$("input[name='config.dom_gather_register']").val(g1);
		var g2 = "";
		$("input[name='configs.dom_gather_approve']:checked").each(function(){
			g2+=$(this).val()+",";
		});
		$("input[name='config.dom_gather_approve']").val(g2);
		var g3 = $("input[name='configs.force_read']:checked").val();
		$("input[name='config.force_read']").val(g3);
		var g4 = $("input[name='configs.dom_gather_zip']:checked").val();
		$("input[name='config.dom_gather_zip']").val(g4);
		var d1 = ""; 
		$("input[name='configs.dom_dis_nigao']:checked").each(function(){
			d1+=$(this).val()+",";
		});
		$("input[name='config.dom_dis_nigao']").val(d1);
		var d2 = "";
		$("input[name='configs.dom_dis_hegao']:checked").each(function(){
			d2+=$(this).val()+",";
		});
		$("input[name='config.dom_dis_hegao']").val(d2);
		var d3="";
		$("input[name='configs.dom_dis_red']:checked").each(function(){
			d3+=$(this).val()+",";
		});
		$("input[name='config.dom_dis_red']").val(d3);
		var d4 = $("input[name='configs.dom_dis_zip']:checked").val();
		$("input[name='config.dom_dis_zip']").val(d4);
		
		var bumenzhuanlan = $("#bumenzhuanlan").val();
		$("input[name='config.bumenzhuanlan']").val(bumenzhuanlan);
		
		var c1 = $("input[name='configs.approve_widget']:checked").val();
		$("input[name='config.approve_widget']").val(c1);
		
		var c2 = $("input[name='configs.reader_widget']:checked").val();
		$("input[name='config.reader_widget']").val(c2);
		
		var c3 = $("input[name='configs.approve_comment']:checked").val();
		$("input[name='config.approve_comment']").val(c3);
		
		var c4 = $("input[name='configs.notice_update_password']:checked").val();
		$("input[name='config.notice_update_password']").val(c4);
		
		$("form").submit();
	});
});


function selectBumen(){
	var defaultSelectId = $("#bumenzhuanlan").val();
	var url = basePath + "/logined/user/selectgroup.jsp?defaultSelectId=" + defaultSelectId + "";
	var title = "选择部门";
	art.dialog.open(url, {
				title : title,
				width : 360,
				height : 407,
				lock : true,
			    opacity: 0.08,
				button : [{
							name : '确定',
							callback : function() {
								var userMap = art.dialog.data("userMap");
								selectGoupCallBack(userMap);
								return true;
							}
						}, {
							name : '取消',
							callback : function() {
								return true;
							}
						}]
			}, false);
}

function clearBumen(){
	$("#bumenzhuanlan").val("");
	$("#bumenzhuanlan_name").val("");
}

function selectGoupCallBack(data){
	var ids = '';
	var names = '';
	data.forEach(function(value, key) {
				ids += value.Id + ',';
				names += value.Name + ',';
			});
	$("#bumenzhuanlan").val(ids);
	$("#bumenzhuanlan_name").val(names);
}

function defaultEmail(){
	$.ajax({
		url:	basePath+"logined/email/defaultEmail.action?t="+Math.random(),
		type:	"post",
		dataType:"text",
		beforeSend:function(){
  			$("body").lock();
  	    },
  		complete:function(){
  			$("body").unlock();
  		},
		success:function(data){
			 art.dialog.alert("执行成功！");
		}
	});
}