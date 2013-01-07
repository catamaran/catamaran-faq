<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
	<link type="text/css" rel="stylesheet" href="static/css/index.css" media="screen, projection">
	<script type="text/javascript" src="static/js/index.js"></script>	
	
</head>
<body>
	<#include "includes/top-nav.ftl" />
	
	<div id="bodyContent">

        <#if message??>
            <#if messageSuccess>
                <p class="successMessage">${message}</p>
            <#else>
                <p class="failureMessage">${message}</p>
            </#if>
        </#if>
		
		<div id="leftCol">
			<!-- <h3><a href="index">FAQs</a><#if RequestParameters.tag??> : Tag="${RequestParameters.tag}"</#if><#if RequestParameters.query??> : Search="${RequestParameters.query}"</#if>
			</h3> -->    
            <p>
                <form method="GET" action="index">
                    <input type="text" name="query" value="${RequestParameters.query!}" size="30"/>
                    <input type="submit" value="Search" />
                </form>
                <form method="GET" action="index">
                    <input type="hidden" name="query" value="" />
                    <input type="submit" value="Clear" />
                </form>
            </p>
			
			<ul id="level1">
			<#if (top.childNodes?size == 0)>
			    <li>
			         No results found for search '${RequestParameters.query!}'
			    </li>
			</#if>			
			<#list top.childNodes as level1Node>
				<li class="category">
				    <#if (level1Node.faqCount > 0)>
				        <a href="index?query=topic:${level1Node.colonSeparatedNameNoSpaces}">${level1Node.name}</a>
				    </#if>
					<ul>
					<#list level1Node.faqs as faq>
						<li id="${level1Node.nodeId}-${faq.shortId}" class="faq">
							<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
							<div class="answerBox"></div>
						</li>
					</#list>
					<#list level1Node.childNodes as level2Node>
						<li class="category">
						    <#if (level2Node.faqCount > 0)>
						      <a href="index?query=topic:${level1Node.colonSeparatedNameNoSpaces}">${level1Node.name}</a> : <a href="index?query=topic:${level2Node.colonSeparatedNameNoSpaces}">${level2Node.name}</a>
						    </#if>
							<ul>
							<#list level2Node.faqs as faq>
								<li id="${level2Node.nodeId}-${faq.shortId}" class="faq">
									<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
									<div class="answerBox"></div>
								</li>								
							</#list>
							<#list level2Node.childNodes as level3Node>
								<li class="category">
								    <#if (level3Node.faqCount > 0)>
								        <a href="index?query=topic:${level1Node.colonSeparatedNameNoSpaces}">${level1Node.name}</a> : <a href="index?query=topic:${level2Node.colonSeparatedNameNoSpaces}">${level2Node.name}</a> : <a href="index?query=topic:${level3Node.colonSeparatedNameNoSpaces}">${level3Node.name}</a><!-- (${level3Node.faqCount?c}) -->
								    </#if>
									<ul>
									<#list level3Node.faqs as faq>
										<li id="${level3Node.nodeId}-${faq.shortId}" class="faq">
											<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
											<div class="answerBox"></div>
										</li>
									</#list>						
									<#list level3Node.childNodes as level4Node>
										<li class="category">
										    <#if (level4Node.faqCount > 0)>
										      <a href="index?query=topic:${level1Node.colonSeparatedNameNoSpaces}">${level1Node.name} : <a href="index?query=topic:${level2Node.colonSeparatedNameNoSpaces}">${level2Node.name}</a> : <a href="index?query=topic:${level3Node.colonSeparatedNameNoSpaces}">${level3Node.name}</a> : <a href="index?query=topic:${level4Node.colonSeparatedNameNoSpaces}">${level4Node.name}</a><!-- (${level4Node.faqCount?c}) -->
										    </#if>
											<ul>
											<#list level4Node.faqs as faq>
												<li id="${level4Node.nodeId}-${faq.shortId}" class="faq">
													<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
													<div class="answerBox"></div>
												</li>
											</#list>
											</ul>
										</li>									
									</#list>
									</ul>
								</li>
							</#list>
							</ul>
						</li>
					</#list>
					</ul>					
				</li>
			</#list>
			</ul>
		</div> <!-- leftCol -->
		
		<div id="rightCol">
		
			<h3>Related topics</h3>
			<ul>
			<#list keywords as keyword>
				<li><a href="index?query=${keyword}">${keyword}</a></li>
			</#list>
			</ul>
		
		</div>  <!-- rightCol -->
		
	</div> <!-- bodyContent -->	
</body>
</html>