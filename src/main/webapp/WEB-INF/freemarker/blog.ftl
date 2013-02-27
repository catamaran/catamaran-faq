<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />

	<link type="text/css" rel="stylesheet" href="static/css/blog.css" media="screen, projection">
</head>
<body>
    <#assign page="blog" />
	<#include "includes/top-nav.ftl" />
	
	<div id="content">
	
	   <div class="clearfix"></div>		

		<ul class="leftNav">
		  <div style="padding-right: 26px;">
			<h3 class="noTopMargin" style="-webkit-margin-before:0px">Recent Posts</h3>
			<#list posts as post>
			     <li><a href="blog-post?id=${post.postid}">${post.title}</a></li>
            </#list> 
		  </div>	
		</ul><div id="mainCol">
			
		    <#if message??>
		        <#if messageSuccess>
		            <p class="successMessage">${message}</p>
		        <#else>
		            <p class="failureMessage">${message}</p>
		        </#if>
		    </#if>
		    
		    <#list posts as post>
		      <a href="blog-post?id=${post.postid}"><h2 class="noTopMargin">${post.title}</h2></a>
		      <p class="postBody">${post.description}</p>
		      <p style="font-style:italic; margin-bottom: 32px;">Posted on ${post.dateCreated?string("MMM, dd yyyy")} at ${post.dateCreated?string("h:mm a")}</p>
			</#list>
				
		</div> <!-- mainCol -->			
	</div> <!-- content -->
	<#include "includes/bottom.ftl" />
</body>
</html>