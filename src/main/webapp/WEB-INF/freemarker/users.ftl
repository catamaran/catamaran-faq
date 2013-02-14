<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/site.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/top.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="http://stage.scandilabs.com/css/tabs.css" media="screen, projection">
    <link type="text/css" rel="stylesheet" href="static/css/java.css" media="screen, projection">
</head>
<body>
	<#include "includes/top-nav.ftl" />
	
	<#include "includes/java-tabs.ftl" />

	<h1>Users</h1>
	
	<a href="user-edit">Create User</a>
	<ul>
	<#list users as user>
		<li><a href="user?key=${user.key}">${user.name.last}, ${user.name.first}</a></li>
	</#list>
	</ul>
	
	<#include "includes/bottom.ftl" />	
</body>
</html>