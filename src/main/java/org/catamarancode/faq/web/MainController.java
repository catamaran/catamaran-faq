package org.catamarancode.faq.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.catamarancode.faq.entity.Faq;
import org.catamarancode.faq.entity.NestedTag;
import org.catamarancode.faq.entity.User;
import org.catamarancode.faq.service.solr.SolrService;
import org.catamarancode.faq.web.support.CategoryNode;
import org.catamarancode.faq.web.support.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Resource
    private SolrService solrService;

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return index(request, response);
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Get parameters
        String categoryFilter = request.getParameter("category");
        String tagFilter = request.getParameter("tag");
        String query = request.getParameter("query");
        
        // Find faqs filtered by parameters
        List<Faq> faqs = null;
        if (StringUtils.hasText(categoryFilter)) {
            faqs = solrService.findFaqsByCategory(categoryFilter);
        } else if (StringUtils.hasText(tagFilter)) {
            faqs = solrService.findFaqsByTag(tagFilter);
        } else if (StringUtils.hasText(query)) {
            QueryResponse queryResponse = solrService.search(query + " AND document-type:FAQ");
            faqs = solrService.extractFaqs(queryResponse);    
        } else {
            
            // Default to showing all faqs (TODO: paginate in the future)
            faqs = solrService.listFaqs();    
        }
        
        ModelAndView mv = new ModelAndView("index");
        
        //List<Tag> allTags = solrService.listTags();        
        //List<Faq> allFaqs = solrService.listFaqs();
        
        // Group faqs by category
        // NOTE: We limit ourselves to 4 levels deep (to avoid headache of recursion)
        CategoryNode top = new CategoryNode();
        for (Faq faq : faqs) {
            NestedTag tag = faq.getNestedTags()[0];
            CategoryNode currentNode = null;
            if (tag != null) {
                for (int i = 0; i < tag.getElements().size(); i++) {
                    if (i == 0) {
                        currentNode = top;                    
                    }
                    currentNode = currentNode.getOrCreateChild(tag.getElements().get(i));                
                }
                
                // Add the FAQ itself
                currentNode.addFaq(faq);
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
    
    /*
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView();
        String query = request.getParameter("query");
        if (StringUtils.hasText(query)) {
            QueryResponse queryResponse = solrService.search(query + " AND document-type:FAQ");
            List<Faq> faqs = solrService.extractFaqs(queryResponse);
            mv.addObject("faqs", faqs);            
        }
        return mv;
    }
    */

    @RequestMapping("/faq")
    public ModelAndView faq(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView();
        
        // One specific faq?
        String faqId = request.getParameter("key");        
        Faq faq = solrService.loadFaq(faqId);
        if (faq != null) {
            mv.addObject("faq", faq);
            return mv;
        }
        
        // By tag?
        String tag = request.getParameter("tag");
        List<Faq> faqs = solrService.findFaqsByTag(tag);
        mv.addObject("faqs", faqs);
        return mv;
    }
    
    @RequestMapping("/about")
    public ModelAndView about(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView();
        return mv;
    }
    
    @RequestMapping("/faq-edit")
    public ModelAndView faqEdit(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        User user = HttpSessionUtils.retrieveLoggedInUser(request, solrService);
        
        String faqId = request.getParameter("key");
        
        // GET
        if (request.getMethod().equalsIgnoreCase("GET")) {
            ModelAndView mv = new ModelAndView();
            Faq faq = solrService.loadFaq(faqId);
            mv.addObject("faq", faq);
            return mv;
        }
        
        // POST
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        
        Faq faq = null;
        if (StringUtils.hasText(faqId)) {
            faq = solrService.loadFaq(faqId);    
        } else {
            faq = new Faq();
            faq.setContextId("default");
        }            
        faq.setQuestion(question);
        faq.setAnswer(answer);
        if (user != null) {
            faq.setOwnerKey(user.getKey());
            faq.setOwnerName(user.getName());
        }

        // tags
        for (int i=0; i < faq.getNestedTags().length; i++) {
        	NestedTag currentTag = null;
        	for (int j=0; j < 4; j++) {
        		String tagName = "tag" + (i+1) + (j+1);
        		String value = request.getParameter(tagName);
        		boolean hasValue = StringUtils.hasText(value);
        		
        		// Overwrite an existing tag?
        		if (j == 0 && hasValue) {
        			currentTag = new NestedTag();
        			faq.getNestedTags()[i] = currentTag;
        		}
        		
        		if (hasValue && currentTag != null) {
        			currentTag.addElement(value);
        		} else if (hasValue) {
        			throw new IllegalArgumentException("Missing top value for tag " + (i+1));
        		}
        	}
        }
        
        this.solrService.save(faq);
        
        ModelAndView mv = new ModelAndView("redirect:faq?key=" + faq.getKey());
        return mv;

    }

}
