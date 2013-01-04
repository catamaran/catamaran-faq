<div id="topNav">	
	<p><a href="index">Browse FAQs</a> | <a href="about">About</a> | <#if user??><a href="faq-edit">Create FAQ</a> | <#if user.administrator><a href="users">Manage Users</a> | </#if></#if><#if user??><a href="signout">Sign out ${user.email}</a><#else><a href="signin">Sign in</a></#if></p>
</div>
