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
    	var nodeId = parent.attr('id');
    	var idDelim = nodeId.indexOf("-");
    	var faqShortId = nodeId.substring(idDelim+1);
    	var nodeName = nodeId.substring(0, idDelim);
    	console.log("nodeId " + nodeId);
    	console.log("faqShortId " + faqShortId);
    	console.log("nodeName " + nodeName);
    	var answerParagraphSelector = "#" + nodeId + " div";
    	
    	// Detect if answer is currently shown or not
    	var ht = $(answerParagraphSelector).html();
    	var answerVisible = false;
     	if (ht == null || ht.length == 0) {
     		answerVisible = false;
     		console.log("a " + ht);
    	} else {
    		answerVisible = true;
    		console.log("b");
    	}    	
    	
    	//alert('ho=' + faqId);
     	if (answerVisible) {
     		$(answerParagraphSelector).html(null);
     		anchor.html('+');
     	} else {	
	    	$.getJSON('faq.json?key=' + faqShortId, function(data) {
	    		$(answerParagraphSelector).html('<p>' + data.answerAsMarkdown + '</p>');
	    	});
	    	anchor.html('-');
     	}
	});
});
	
