package org.catamarancode.faq.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.catamarancode.faq.entity.Faq;
import org.catamarancode.faq.entity.NestedTag;
import org.catamarancode.faq.service.MessageContext;
import org.catamarancode.faq.service.SolrService;
import org.catamarancode.faq.service.UserContext;
import org.catamarancode.faq.web.support.NestedTagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Scope("request")
public class VisitorController {

    private Logger logger = LoggerFactory.getLogger(VisitorController.class);

    @Autowired
    private SolrService solrService;
    
    @Autowired
	private MessageContext messageContext;    

    @Autowired
	private UserContext userContext;        

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return index(request, response);
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Get parameters
        //String tagFilter = request.getParameter("tag");
        String query = request.getParameter("query");
        
        // A topic (tag) search?
        String tagFilter = null;
        if (StringUtils.hasText(query) && query.toLowerCase().indexOf("topic:") > -1) {
        	tagFilter = query.toLowerCase().replace("topic:", "").trim();
        	if (tagFilter.indexOf(":") > -1) {
        		tagFilter = tagFilter.replace(":", "|");
        	}
        }
        
        // Find faqs filtered by parameters
        List<Faq> faqs = null;
        NestedTag tagParam = null;
        if (StringUtils.hasText(tagFilter)) {
        	tagParam = NestedTag.createFromPipeSeparatedString(tagFilter);
            faqs = new ArrayList<Faq>(solrService.findByTag(tagParam, true));
        } else if (StringUtils.hasText(query)) {
        	
        	// Strip out characters we don't want in query
        	String filteredQuery = query.replace(":", " ");
            QueryResponse queryResponse = solrService.search(filteredQuery + " AND document-type:FAQ");
            faqs = solrService.extractFaqs(queryResponse);    
        } else {
            
            // Default to showing all faqs (TODO: paginate in the future)
            faqs = solrService.listFaqs();    
        }
        
        ModelAndView mv = new ModelAndView("index");
        userContext.prepareModel(mv.getModel());
        
        // Group faqs by category
        // NOTE: We limit ourselves to 4 levels deep (to avoid headache of recursion)
        NestedTagNode top = new NestedTagNode(null);
        for (Faq faq : faqs) {
        	for (int i = 0; i < faq.getNestedTags().length; i++) {
	            NestedTag tag = faq.getNestedTags()[i];
	            NestedTagNode currentNode = null;
	            if (tag != null) {
	            	
	            	// If tag parameter is specified, skip nodes that don't match
	            	if (tagParam != null) {
	            		if (!tagParam.match(tag, true)) {
	            			continue;
	            		}
	            	}
	            	
	                for (int j = 0; j < tag.getElements().size(); j++) {
	                    if (j == 0) {
	                        currentNode = top;                    
	                    }
	                    currentNode = currentNode.getOrCreateChild(tag.getElements().get(j));                
	                }
	                
	                // Add the FAQ itself
	                currentNode.addFaq(faq);
	            }
        	}
        }
        
        // Create keyword list from tags and categories
        List<String> keywords = extractKeywords(faqs);
        
        // Create keyword list from only
        
        List<String> level1 = new ArrayList<String>();
        
        //mv.addObject("tags", allTags);
        //mv.addObject("faqs", allFaqs);
        mv.addObject("keywords", keywords);
        mv.addObject("top", top);
        return mv;
    }
    
    /**
     * Create keyword list from all tags and categories
     * @return
     */
    private List<String> extractKeywords(List<Faq> faqs) {
        Set<String> keywords = new HashSet<String>();
        for (Faq faq : faqs) {
            
        	for (int i = 0; i < faq.getNestedTags().length; i++) {
        		NestedTag tag = faq.getNestedTags()[i];
        		if (tag != null) {
            		for (String term : tag.getElements()) {
       	                keywords.add(term);    
       	            }
        		}
        	}
        }
        
        List<String> list = new ArrayList<String>(keywords);
        Collections.sort(list);
        return list;
        
    }
  
    @RequestMapping("/faq")
    public ModelAndView faq(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView();
        userContext.prepareModel(mv.getModel());
        
        // One specific faq?
        String faqId = request.getParameter("key");        
        Faq faq = solrService.loadFaq(faqId);
        if (faq != null) {
            mv.addObject("faq", faq);
            return mv;
        }
        
        // By tag?
        String tag = request.getParameter("tag");
        List<Faq> faqs = new ArrayList<Faq>(solrService.findByTag(NestedTag.createFromPipeSeparatedString(tag), false));
        mv.addObject("faqs", faqs);
        return mv;
    }
    
    @RequestMapping("/about")
    public ModelAndView about(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView();
        userContext.prepareModel(mv.getModel());
        return mv;
    }

}
