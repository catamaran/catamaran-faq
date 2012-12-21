<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
	<#include "includes/head.ftl" />
</head>
<body>
<div id="bodyContent">
<form action="signup-submit" method="post" id="makeDepositForm">
    <fieldset>
    	<#if signupForm.subjectId??>
    		<legend>Sign up and claim your profile</legend>
    	<#else>
    		<legend>Personal Information</legend>
    	</#if>
		<div>
			<@spring.bind "signupForm.firstName" />
	        <label for="${spring.status.expression}">* First name</label>
	        <input type="text" name="${spring.status.expression}" value="${spring.status.value!}" />
			<span class="fieldErrors"><#list spring.status.errorMessages as error>${error}</b><#if error_has_next>,</#if></#list></span>
		</div>
		<div>
			<@spring.bind "signupForm.lastName" />
	        <label for="${spring.status.expression}">* Last name</label>
	        <input type="text" name="${spring.status.expression}" value="${spring.status.value!}" />
			<span class="fieldErrors"><#list spring.status.errorMessages as error>${error}</b><#if error_has_next>,</#if></#list></span>
		</div>
    	<div>
    		<@spring.bind "signupForm.email" />
	        <label for="${spring.status.expression}">* Email address</label>
	        <input type="text" name="${spring.status.expression}" value="${spring.status.value!}" />
			<span class="fieldErrors"><#list spring.status.errorMessages as error>${error}</b><#if error_has_next>,</#if></#list></span>
		</div>
		<div>
			<@spring.bind "signupForm.password" />
	        <label for="${spring.status.expression}">* Password</label>
	        <input type="password" name="${spring.status.expression}" value="${spring.status.value!}" />
			<span class="fieldErrors"><#list spring.status.errorMessages as error>${error}</b><#if error_has_next>,</#if></#list></span>
		</div>
	</fieldset>
	<#if signupForm.subjectId??>
		<input type="hidden" name="subjectId" value="${signupForm.subjectId?c}" />
	</#if>
	<input type="submit" value="Sign up" />	
	
</form>

</div> <!-- bodyContent -->
</body>
</html>