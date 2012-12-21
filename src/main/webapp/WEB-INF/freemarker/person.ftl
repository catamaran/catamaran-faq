<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
</head>
<body>
<#include "includes/top-nav.ftl" />

<div id="bodyContent">	
	<h1>${person.name.first} ${person.name.last}</h1>
	
	<a href="complaint-edit?personId=${person.id?c}">File a new complaint</a><#if !person.user> | <a href="signup?subject=${person.id}">I am ${person.name} and I wish to claim this entry</a></#if>
	
	<ul>
	<#list complaints as complaint>
		<li><a href="complaint?complaint=${complaint.id?c}">${complaint.title}</a></li>
	</#list>
	</ul>
	
</div> <!-- bodyContent -->	
</body>
</html>