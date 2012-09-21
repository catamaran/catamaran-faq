$().ready(function() {
	
	/* person search stuff */
	
	/* See http://docs.jquery.com/UI/Autocomplete */
    $("input#autocomplete").autocomplete({
    	source: "person-lookup.json",  
    	minLength: 2 
	});
    
    $("#autocomplete").focus(function() {
    	var existingValue = $(this).val();
    	if (existingValue == 'Enter a name..') {
    		$(this).val('');
    	}
    });
    
    $('#searchBox .button').click(function(e) {
		e.preventDefault() && e.preventDefault;
		
		var selectedName = $('#autocomplete').val();
		/* $('#filterName').val(selectedName); */
		$('#filterName').text(selectedName); 
		
		window.location = "index?personName=" + selectedName;
		
	});
    
    /* end person search stuff */
	
    /* vote stuff */
	$('.voteLink').click(function(e) {
		e.preventDefault() && e.preventDefault;
		
		$.getJSON($(e.target).attr("href"), function(json) {

			var complaintId = json['complaintId'];
			var votes = json['totalVotes'];
			var voteNumberId = '#complaintVoteNumber_' + complaintId;			
			$(voteNumberId).text(votes);
			$("#complaintVoteLink_" + complaintId).hide();
		});		
	});
	/* end vote stuff */
	
});
	
