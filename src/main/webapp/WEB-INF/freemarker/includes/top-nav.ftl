<#assign parentSiteBase="http://stage.scandilabs.com/" />        
        
        <div id="outerContent">
        	
	        <div id="border">

				<div id="headerOuter">
			        <div id="header">
			        
						<div class="right" style="float:right; padding-right:20px;">
						   <#if user??><a href="signout">Sign out ${user.email}</a><#else><a href="signin">Sign in</a></#if>
						   
						   <!-- TODO: Enable menu 
						   <#if user??> | <a href="faq-edit">Create</a><#if user.administrator> | <a href="users">Manage Users</a> | <a href="audits">History</a></#if></#if>
						   -->
					   </div>
					   <div class="clearfix"></div>
			        
			        	<div style="position:relative;top:-16px;margin-bottom:-16px;">
			        
				            <div id="logoBox" >
				                <a href="${parentSiteBase}index" ><img src="${parentSiteBase}img/logo246x43_transp.png"></a>
				            </div>   
				            <ul>
				                <li>
				                    <a 
				                        href="${parentSiteBase}what">
				                        <h2>What We Do</h2>
				                    </a>
				                </li>   
				                <li>
				                    <a 
				                        href="${parentSiteBase}how">
				                        <h2>How We Work</h2>
				                    </a>
				                </li>   
				                <li>
				                    <a class='current' 
				                        href="#">
				                        <h2>Java</h2>
				                    </a>
				                </li>   
				                <li>
				                    <a 
				                        href="${parentSiteBase}blog">
				                        <h2>Blog</h2>
				                    </a>
				                </li>   
				                <li>
				                    <a 
				                        href="${parentSiteBase}clients">
				                        <h2>Clients</h2>
				                    </a>
				                </li>   
				                <li>
				                    <a 
				                        href="${parentSiteBase}about">
				                        <h2>About</h2>
				                    </a>
				                </li>   
				            </ul>
				               
			            </div>

			        </div>
				</div>
				<div class="clearfix"></div>
				
				<div id="pageCanvas">



