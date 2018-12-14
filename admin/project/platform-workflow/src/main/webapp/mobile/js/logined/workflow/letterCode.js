function letterCode(name){
	var colorCode="0";
	var num = 0;
	for (var i = 0; i < name.length; i++) {
		num += parseInt(name[i].charCodeAt(0));
	}
	return parseInt(num) % 10
}