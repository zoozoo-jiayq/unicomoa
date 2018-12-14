// JavaScript Document
//手风琴效果
$(document).ready(function(){
	function unfoldMenu(pn, cn){
		var p = $('p.menu-p'), c = $('div.menu-c'), cc = $('div.menu-c-current');
		if(c.index(cn) != c.index(cc)){
			p.removeClass('menu-p-current');
			cc.hide(200, function(){
				$(this).removeAttr('style').removeClass('menu-c-current');
			})
			pn.addClass('menu-p-current');
			cn.show(200, function(){
				$(this).removeAttr('style').addClass('menu-c-current');
			});
		}
	}
	function menuHandle(){
		$('p.menu-p').click(function(){
			var pn = $(this), cn = pn.next();
			unfoldMenu(pn, cn);
		});
	}
	
	
	//设置默认下当前展开
	function menuCurrent(){
		var idx = $('input.menu-code-index').val(), m, pn, cn, p = $('p.menu-p'), c = $('div.menu-c'), cc = $('div.menu-c-current');
		if(/c(\d)+/.test(idx)){ //判断c（十进制）条件
			m = $('a[data-service-index="' + idx + '"]').addClass('current');
			cn = m.parents('div.menu-c');
			pn = cn.prev();
			unfoldMenu(pn, cn);
		}
	}
	
	menuCurrent();
	menuHandle();
$(".menu-c ul li a").click(function(){
		$(".menu-c ul li").removeClass("current");
		$(this).parent().addClass("current");
		})	
});
