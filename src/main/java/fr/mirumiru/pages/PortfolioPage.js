$(function() {
	// Function to activate the tab
	function activateTab() {
		var id = window.location.hash.replace('/', '');
		if(!id) {
			$.fancybox.close(true);
		}
		var activeTab = $(id);
		activeTab && activeTab.click();
	}

	// Trigger when the page loads
	activateTab();

	// Trigger when the hash changes (forward / back)
	$(window).hashchange(function(e) {
		activateTab();
	});

	// Change hash when a tab changes
	$('a.fancybox').on('click', function() {
		window.location.hash = '/' + $(this).attr('id');
	});
});