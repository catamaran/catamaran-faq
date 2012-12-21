<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
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
		
		<h3><a href="index">FAQs</a></h3>
		<#if faq.category??>		
			<ul class="categoryList"><#list faq.category.elements as category><li><a href="index?category=${category}">${category}</a><#if category_has_next> : </#if></li></#list></ul>
		</#if>
		<div class="faq">		
	        ${faq.question}
	        <ul class="tags">
				<#if (faq.tags?size > 0)>(</#if><#list faq.tags as tag><li><a href="index?tag=${tag.name}">${tag.name}</a><#if tag_has_next>, </#if></li></#list><#if (faq.tags?size > 0)>)</#if>
			</ul>
       		<div class="answerBox"><p>${faq.answer}<p></div>
        </div>
        <br/>
        <a href="faq-edit?key=${faq.key}">edit</a>

	</div> <!-- bodyContent -->
</body>
</html>