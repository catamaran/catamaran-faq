<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
</head>
<body>
<div id="bodyContent">

	<form action="signin" method="post">
	
		<#if message??>
			<#if message.success>
				<p class="successMessage">${message.text}</p>
			<#else>
				<p class="failureMessage">${message.text}</p>
			</#if>
		</#if>
	
		<div>
	    <fieldset>
	        <legend>Sign in</legend>
			<div>			
		        <label for="email">Email</label>
		        <input type="text" name="email" />
			</div>
			<div>
		        <label for="password">Password</label>
		        <input type="password" name="password" />
			</div>
			<div>
				<label for="button"></label>
				<input type="submit" value="Sign in" />
			</div>
		</fieldset>
		</div>
		
	</form>
	
</div> <!-- bodyContent -->
</body>
</html>