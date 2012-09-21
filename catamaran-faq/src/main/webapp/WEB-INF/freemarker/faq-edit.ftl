<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
	<script type="text/javascript" src="static/js/faq-edit.js"></script>
</head>
<body>
<#include "includes/top-nav.ftl" />

	<div id="bodyContent">
	
		<#if message??>
			<div class="errorMessage">
			<#if message.success>
				<p class="successMessage">${message.text}</p>
			<#else>
				<p class="failureMessage">${message.text}</p>
			</#if>
			</div>
		</#if>	
	
		<form action="faq-edit" method="post">
	        <div>					
		        <label>Question</label>
		        <input type="text" size="100" name="question" value="${(faq.question)!}" />
			</div>
	        <div>					
		        <label>Answer</label>
		        <textarea rows="14" cols="72" name="answer">${(faq.answer)!}</textarea>
		        <!-- input type="text" name="answer" value="" / -->
			</div>
			<div>					
		        <label>Category</label>
		        <input type="text" size="40" name="category" id="categoryAutocomplete" value="${(faq.category)!}" />
			</div>
			<div>					
		        <label>Tag #1</label>
		        <input type="text" size="40" name="tag1" class="tagAutocomplete" value="${(faq.tag1)!}" />
			</div>
			<div>					
		        <label>Tag #2</label>
		        <input type="text" size="40" name="tag2" class="tagAutocomplete" value="${(faq.tag2)!}" />
			</div>
			<div>					
		        <label>Tag #3</label>
		        <input type="text" size="40" name="tag3" class="tagAutocomplete" value="${(faq.tag3)!}" />
			</div>
			
			<#if faq??>
				<input type="hidden" name="key" value="${(faq.key)!}" />
			</#if>
			<div>					
		        <label></label>
		        <input type="submit" value="Save" />
			</div>
			
		</form>

	</div> <!-- bodyContent -->
</body>
</html>