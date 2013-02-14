<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/site.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/top.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/tabs.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="static/css/java.css" media="screen, projection">

	<link type="text/css" rel="stylesheet" href="static/css/faq.css" media="screen, projection">
</head>
<body>
	<#include "includes/top-nav.ftl" />

	<#include "includes/java-tabs.ftl" />

    <#if message??>
        <#if messageSuccess>
            <p class="successMessage">${message}</p>
        <#else>
            <p class="failureMessage">${message}</p>
        </#if>
    </#if>
	
	<h2>${faq.question}</h2>
	<div class="faq">		
        <div class="answerBox"><p>${faq.answerAsMarkdown}<p></div>
    </div>
    <div class="faqMeta">
        Owner: ${faq.ownerName!} | 
        Tagged as: <ul class="tags" style="list-style:none">               
            <#list faq.nestedTagsAsList as tag><li style="">
                <a href="faqs?query=topic:${tag.colonSeparatedNoSpaces}">${tag.colonSeparated}</a>
            </#list>
        </ul>
    </div>           
    <#if user??>
        <a href="faq-edit?key=${faq.key}">edit</a>
        <#if ((user.administrator) && !(faq.visibility == "PUBLIC"))>
            | <a href="faq-publicize?key=${faq.key}">publicize</a>
        </#if>
    </#if>
    
    <#if (comments?size > 0)>
        <hr/>
        <h4>Comments:</h4>
        <#list comments as comment>
            ${comment.ownerName} on ${comment.lastModifiedTime?datetime?string} said:<br/>
            ${comment.body}<br/><br/>
        </#list>
    </#if>
    <#if user??>
        <hr/>
        <h4>New comment:</h4>
        <form method="post" action="comment-edit">
            <input type="hidden" name="faqKey" value="${(faq.key)!}" />
            <input type="hidden" name="commentKey" value="${(currentComment.key)!}" />
            <textarea rows="4" cols="72" name="body">${(currentComment.body)!}</textarea>
            <input type="submit" tabindex=99 value="Save" />
        </form>
    </#if>
    
    <#if user??>
        <hr/>
        <h4>Activity:</h4>
        <#list audits as audit>
            ${audit.ownerName} on ${audit.lastModifiedTime?datetime?string}: ${audit.body}<br/><br/>
        </#list>
    </#if>

	<#include "includes/bottom.ftl" />
</body>
</html>