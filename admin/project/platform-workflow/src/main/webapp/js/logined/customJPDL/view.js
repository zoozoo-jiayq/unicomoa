
$(function(){
	var nodelist = new Array();

	var actives = $("#actives").val();
	if(actives){
		var nodes = actives.split(",");
		for(var i=0;i<nodes.length;i++){
			if(nodes[i]){
				var temp = {};
				temp.name = nodes[i];
				nodelist.push(temp);
			}
		}
	}
	$('#myflow')
				.myflow($.extend(true,{
					basePath : "",
					restore : eval("("+$("#jsonData").val()+")"),
					editable : false
				},{"activeRects":{"rects":nodelist}})
						);

	
});