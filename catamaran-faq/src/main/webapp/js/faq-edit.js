$().ready(function() {
	
	
	
	/* See http://docs.jquery.com/UI/Autocomplete */
    $("input.tagAutocomplete").autocomplete({
    	source: "tags.json",  
    	minLength: 1 
	});
    
    $("input.tagAutocomplete").focus(function() {
    	var existingValue = $(this).val();
    	if (existingValue == 'Enter a tag..') {
    		$(this).val('');
    	}
    });
    
    /* See http://docs.jquery.com/UI/Autocomplete */
    $("input#categoryAutocomplete").autocomplete({
    	source: "categories.json",  
    	minLength: 1
	});
    
    $("#categoryAutocomplete").focus(function() {
    	var existingValue = $(this).val();
    	if (existingValue == 'Enter a category..') {
    		$(this).val('');
    	}
    });
    
});
	
