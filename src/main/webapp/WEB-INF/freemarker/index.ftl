<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
    
    <script type="text/javascript" src="static/js/jquery-1.4.js"></script>
	<script type="text/javascript" src="static/js/jquery.animate.js"></script>
	<script type="text/javascript" src="static/js/jquery.tagcanvas.min.js"></script>
    <script type="text/javascript" src="static/js/index.js"></script>
</head>
<body>
	<#include "includes/top-nav.ftl" />
	
	<div id="content">
		<ul class="leftNav">
			<#assign menu="overview" />
			<#include "includes/left-nav-content.ftl" />
		</ul><div id="mainCol">

	
	        <h1 class="noTopMargin">Rapid Web Development with Java</h1>
	        <p>Catamaran Framework ties together best-practice Spring MVC, Hibernate, Tomcat, HTML/CSS, and jQuery in a ready-to-use framework, without sacrificing any of the power and flexibility of Java and related industry standard tools.</p>
		</div> <!-- mainCol -->
		
		
	    <div id="tag-cloud">
		    <div id="myCanvasContainer" >
	            <canvas width="960" height="500" id="myCanvas">
	            </canvas>
	        </div>
	        <div id="tags">
	        </div>    			
	    </div>		
		
	</div> <!-- content -->           
	<#include "includes/bottom.ftl" />
</body>
</html>