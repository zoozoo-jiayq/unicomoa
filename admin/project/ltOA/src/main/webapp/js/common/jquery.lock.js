(function($) {
$.fn.lock = function() {
	return this.unlock().each(function() {
		if ($.css(this,'position') == 'static')
			this.style.position = 'relative';
		if ($.browser.msie)
			this.style.zoom = 1;
		$(this).append('<div class="lockUI" style="position:absolute;width:100%;height:100%;top:0;left:0;z-index:1000;background-color:#000;cursor: wait;opacity: 0.08;filter: alpha(opacity=8);"><div>')
	});
};
$.fn.unlock = function() {
	return this.each(function() {
		$('.lockUI',this).remove();
	});
}
})(jQuery);