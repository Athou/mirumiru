$(function() {
	var divs = $('div.thumbnail');
	var maxHeight = Math.max.apply(Math, divs.map(function() {
		return $(this).height();
	}).get());
	divs.height(maxHeight);
});