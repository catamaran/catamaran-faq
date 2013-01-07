<div id="topNav">	
	<a href="index">Browse</a> | <a href="about">About</a><#if user??> | <a href="faq-edit">Create</a><#if user.administrator> | <a href="users">Manage Users</a> | <a href="audits">History</a></#if></#if>
	<div class="right">
	   <#if user??><a href="signout">Sign out ${user.email}</a><#else><a href="signin">Sign in</a></#if>
   </div>
</div>
<div class="clearfix"></div>
