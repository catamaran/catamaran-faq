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
			<div class="errorMessage">
			<#if message.success>
				<p class="successMessage">${message.text}</p>
			<#else>
				<p class="failureMessage">${message.text}</p>
			</#if>
			</div>
		</#if>
		
		<div id="leftCol">
			<h3><a href="index">FAQs</a><#if RequestParameters.category??> : Category="${RequestParameters.category}"</#if><#if RequestParameters.tag??> : Tag="${RequestParameters.tag}"</#if><#if RequestParameters.query??> : Search="${RequestParameters.query}"</#if>
			</h3>
			<ul id="level1">
			<#list top.childNodes as level1Node>
				<li class="category"><a href="index?category=${level1Node.name}">${level1Node.name}</a> (${level1Node.faqCount?c})
					<ul>
					<#list level1Node.faqs as faq>
						<li id="${faq.shortId}" class="faq">
							<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
							<ul class="tags">
								<#if (faq.nestedTagsAsList?size > 0)>(</#if><#list faq.nestedTagsAsList as tag><li><a href="index?tag=${tag.name}">${tag.name}</a><#if tag_has_next>, </#if></li></#list><#if (faq.tags?size > 0)>)</#if>
							</ul>
							<div class="answerBox"></div>
						</li>
					</#list>
					<#list level1Node.childNodes as level2Node>
						<li class="category"><a href="index?category=${level1Node.name}">${level1Node.name}</a> : <a href="index?category=${level2Node.name}">${level2Node.name}</a> (${level2Node.faqCount?c})
							<ul>
							<#list level2Node.faqs as faq>
								<li id="${faq.shortId}" class="faq">
									<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
									<ul class="tags">
										<#if (faq.tags?size > 0)>(</#if><#list faq.tags as tag><li><a href="index?tag=${tag.name}">${tag.name}</a><#if tag_has_next>, </#if></li></#list><#if (faq.tags?size > 0)>)</#if>
									</ul>
									<div class="answerBox"></div>
								</li>								
							</#list>
							<#list level2Node.childNodes as level3Node>
								<li class="category"><a href="index?category=${level1Node.name}">${level1Node.name}</a> : <a href="index?category=${level2Node.name}">${level2Node.name}</a> : <a href="index?category=${level3Node.name}">${level3Node.name}</a> (${level3Node.faqCount?c})
									<ul>
									<#list level3Node.faqs as faq>
										<li id="${faq.shortId}" class="faq">
											<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
											<ul class="tags">
												<#if (faq.tags?size > 0)>(</#if><#list faq.tags as tag><li><a href="index?tag=${tag.name}">${tag.name}</a><#if tag_has_next>, </#if></li></#list><#if (faq.tags?size > 0)>)</#if>
											</ul>
											<div class="answerBox"></div>
										</li>
									</#list>						
									<#list level3Node.childNodes as level4Node>
										<li class="category"><a href="index?category=${level1Node.name}">${level1Node.name} : <a href="index?category=${level2Node.name}">${level2Node.name}</a> : <a href="index?category=${level3Node.name}">${level3Node.name}</a> : <a href="index?category=${level4Node.name}">${level4Node.name}</a> (${level4Node.faqCount?c})
											<ul>
											<#list level4Node.faqs as faq>
												<li id="${faq.shortId}" class="faq">
													<a class="toggle" name="${faq.shortId}" nohref>+</a> <a href="faq?key=${faq.key}">${faq.question}</a>
													<ul class="tags">
														<#if (faq.tags?size > 0)>(</#if><#list faq.tags as tag><li><a href="index?tag=${tag.name}">${tag.name}</a><#if tag_has_next>, </#if></li></#list><#if (faq.tags?size > 0)>)</#if>
													</ul>
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
		
			<p>
				<form method="GET" action="search">
					<input type="text" name="query" size="30"/>
					<input type="submit" value="Search" />
				</form>
			</p>
	
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