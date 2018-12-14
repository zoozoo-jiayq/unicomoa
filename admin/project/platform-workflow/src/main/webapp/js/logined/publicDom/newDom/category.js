	function generateSelect(id,data){
		$("<option>",{
			"value":data.id,
			"text":data.name
		}).appendTo($("#"+id));
	}
	
	
	jQuery(document).ready(function($){
		qytx.app.ajax({
			url:basePath+"/dom/public!getAllCategorys.action?r="+Math.random(),
			dataType:"json",
			success:function(datas){
				for(var i=0; i<datas.length ; i++){
					generateSelect("category",datas[i]);
				}
			}
		});
		
		$("#category").change(function(){
			var temp = $(this).val();
			var name = $(this).find("option:selected").text();
			$("#firstLevel").val(name);
			document.getElementById("gongwenType").options.length =0;
			generateSelect("gongwenType", {id:"",name:"－请选择－"});
			var gongwenType = $("#gongwenTypeFlag").val();
			if(temp){
				qytx.app.ajax({
					url:basePath+"/dom/public!getDocumentType.action?categoryId="+temp+"&r="+Math.random()+"&gongwenType="+gongwenType,
					dataType:"json",
					success:function(datas){
						for(var i=0; i<datas.length ; i++){
							generateSelect("gongwenType", datas[i]);
						}
					}
				});
			}else{
				
			}
		});
	});