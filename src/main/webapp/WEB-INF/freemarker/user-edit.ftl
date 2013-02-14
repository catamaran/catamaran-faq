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
    <h1>Edit User</h1>
    Context ID: ${(displayUser.contextId)!}<br/>
    Key: ${(displayUser.key)!}<br/>
    Short ID: ${(displayUser.shortId)!}<br/>
    <form method="POST" action="user-edit">
        <input type="hidden" name="key" value="${(displayUser.key)!}" />
        First: <input type="text" name="firstName" value="${(displayUser.name.first)!}" /><br/>
        Last: <input type="text" name="lastName" value="${(displayUser.name.last)!}" /><br/>
        Email: <input type="text" name="email" value="${(displayUser.email)!}" /><br/>
        Password: <input type="password" name="password" value="${(displayUser.cleartextPassword)!}" /><br/>
        Administrator?: <input type="text" name="administratorFlag" value="${(displayUser.administrator?string)!}" /><br/>
        
        <input type="submit" value="Save" />    
    </form>	
	
	<#include "includes/bottom.ftl" />	
</body>
</html>