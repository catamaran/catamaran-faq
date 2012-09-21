<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
</head>
<body>
<#include "includes/top-nav.ftl" />

<div id="bodyContent">
	
	<h1>People</h1>
	
	<ul>
	<#list persons as person>
		<li><a href="person?personId=${person.id?c}">${person.name.last}, ${person.name.first}</a></li>
	</#list>
	</ul>
	
</div> <!-- bodyContent -->	
</body>
</html>