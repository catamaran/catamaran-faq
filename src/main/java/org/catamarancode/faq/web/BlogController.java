package org.catamarancode.faq.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;

import org.catamarancode.faq.service.MessageContext;
import org.catamarancode.faq.service.SolrService;
import org.catamarancode.faq.service.UserContext;
import org.catamarancode.util.LRUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("request")
public class BlogController {

	private Logger logger = LoggerFactory.getLogger(BlogController.class);

	// Live 24hrs
	// Note pageCache is static because of @Scope("request") annotation.  Without that annotation the controller would be a singleton so it would not have to be static. 
	private static final LRUCache<String, List<Page>> pageCache = new LRUCache<String, List<Page>>(200, 86400000);
	
	@Autowired
	private SolrService solrService;

	@Autowired
	private MessageContext messageContext;

	@Autowired
	private UserContext userContext;
	
	private Wordpress getWordpressClient() throws Exception {
		Wordpress wp = new Wordpress("sladmin", "scandi12",
				"http://wordpress.scandilabs.com/xmlrpc.php");
		return wp;
	}
	
	private List<Page> getRecentPostsCached() throws Exception {
		Wordpress wp = getWordpressClient();
		String cacheKey = "recent200";
		List<Page> recentPosts = pageCache.get(cacheKey);
		if (recentPosts == null) {
			logger.debug("blog cache miss on " + cacheKey);
			recentPosts = wp.getRecentPosts(200);
			pageCache.put(cacheKey, recentPosts);
		} else {
			logger.debug("blog cache hit on " + cacheKey);
		}
		
		return recentPosts;
	}
	
	private Page getPostCached(int id) throws Exception {
		Wordpress wp = getWordpressClient();
		String cacheKey = "id" + id;
		List<Page> recentPosts = pageCache.get(cacheKey);
		if (recentPosts == null) {
			logger.debug("blog cache miss on " + cacheKey);
			Page post = wp.getPost(id);
			if (post != null) {
				recentPosts = new ArrayList<Page>();
				recentPosts.add(post);
				pageCache.put(cacheKey, recentPosts);				
			}
		} else {
			logger.debug("blog cache hit on " + cacheKey);
		}
		if (recentPosts != null) {
			return recentPosts.get(0);
		} else {
			return null;
		}		
	}

	@RequestMapping("/blog")
	public ModelAndView blog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Page> recentPosts = getRecentPostsCached();

		ModelAndView mv = new ModelAndView();
		mv.addObject("posts", recentPosts);
		userContext.prepareModel(mv.getModel());
		messageContext.addPendingToModel(mv.getModel());

		return mv;
	}
	
	@RequestMapping("/blog-post")
	public ModelAndView blogPost(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));

		Page post = this.getPostCached(id);		
		List<Page> recentPosts = getRecentPostsCached();

		ModelAndView mv = new ModelAndView();
		mv.addObject("posts", recentPosts);
		mv.addObject("post", post);
		userContext.prepareModel(mv.getModel());
		messageContext.addPendingToModel(mv.getModel());

		return mv;
	}

}
