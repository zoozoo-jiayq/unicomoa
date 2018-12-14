$(document).ready(function() {
		var accordion_head = $('.accordion > li > a');
		var accordion_body = $('.accordion li > .sub-menu');
		// 默认将第一个打开
		accordion_head.first().addClass('active').next().slideDown('normal');
		// 标题头的点击事件
		accordion_head.on('click', function(event) {
				//识别是否存在子项
				if ($(this).next().children().length > 0) {
					//禁用a标签打开超链接
					event.preventDefault();
				}else{
					return;
				}
				// 打开子菜单
				if ($(this).attr('class') != 'active') {
					accordion_body.slideUp('normal');
					$(this).next().stop(true, true).slideToggle('normal');
					accordion_head.removeClass('active');
					$(this).addClass('active');
				}
			});
	});