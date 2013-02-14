<#import "spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<#assign page = "notFound">
<head>
	<link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/site.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/top.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/tabs.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="static/css/java.css" media="screen, projection">
</head>
<#flush>
<body>
	<#include "includes/top.ftl" />
	
	<#include "includes/java-tabs.ftl" />

	<h1>Sorry, but there has been some sort of error.</h1>				
	<#if logExceptionMessage??>
		<p>${logExceptionMessage}</p>
	</#if>			
	<p>If you receive this error message a second time, please <a href="mailto:support@catamaranframework.org?subject=FAQ error code: ${logExceptionID}">contact our support staff</a> and mention error code ${logExceptionID}.</p>
		
	<#include "includes/bottom.ftl" />
</body>
</html>