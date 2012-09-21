/*
function toggleAnswer(key) {
	
	var answerId = "#answer|" + key;
	alert('answerId=' + answerId);
	$("#answer|people_in_the_world?").html("goto");
	//$(answerId).html('abcde');
	
	$("a#answer-a").hide();
}
*/

$().ready(function() {
	
    $("li.faq a.toggle").click(function(e) {
    	
    	e.preventDefault();
    	
    	var anchor = $(e.target);
    	var parent = anchor.parent('.faq');
    	//var shortId = e.target.name;
    	var shortId = parent.attr('id');
    	//alert(parent.attr('id'));
    	//var faqId="#" + parent.attr('id');
    	var answerParagraphSelector = "#" + shortId + " div";
    	
    	// Detect if answer is currently shown or not
    	var ht = $(answerParagraphSelector).html();
    	var answerVisible = false;
     	if (ht == null || ht.length == 0) {
     		answerVisible = false;
    	} else {
    		answerVisible = true;
    	}    	
    	
    	//alert('ho=' + faqId);
     	if (answerVisible) {
     		$(answerParagraphSelector).html(null);
     		anchor.html('+');
     	} else {	
	    	$.getJSON('faq.json?key=' + shortId, function(data) {
	    		$(answerParagraphSelector).html('<p>' + data.answer + '</p>');
	    	});
	    	anchor.html('-');
     	}
	});
});
	
