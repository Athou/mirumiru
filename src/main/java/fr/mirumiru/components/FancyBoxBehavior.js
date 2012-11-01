$(function() {
	$(".fancybox").fancybox({
    	openEffect	: 'elastic',
    	closeEffect	: 'elastic',
    	afterClose : function() {
    		${onclose}
    	}
	});
});