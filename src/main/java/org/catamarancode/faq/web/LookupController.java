package org.catamarancode.faq.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.catamarancode.faq.entity.Faq;
import org.catamarancode.faq.entity.NestedTag;
import org.catamarancode.faq.service.solr.SolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LookupController {

    private Logger logger = LoggerFactory.getLogger(LookupController.class);

    @Resource
    private SolrService solrService;

    /*
    @RequestMapping("/tags.json")
    public ModelAndView tags(HttpServletRequest request,
            @RequestParam(required = true) String term) throws Exception {

        ModelAndView mv = new ModelAndView("json-string");
        
        // Perform solr search
        String query = String.format("tag:%s*", term);
        QueryResponse queryResponse = solrService.search(query);
        List<Faq> faqs = solrService.extractFaqs(queryResponse);
        
        // Build a uniqe list of tag strings
        Set<String> uniqueTags = new HashSet<String>();
        for (Faq faq : faqs) {
            for (Tag tag : faq.getTags()) {
                
                // Only include the tags that start with term (since a doc may have multiple tags)
                String tagStr = tag.getName();
                if (StringUtils.hasText(tagStr) && tagStr.startsWith(term)) {
                    uniqueTags.add(tag.getName());    
                }   
            }
        }
        
        // Convert to a list and Sort alpha
        List<String> orderedTags = new ArrayList(uniqueTags);
        Collections.sort(orderedTags);

        // Build results
        JSONArray json = new JSONArray();
        for (String name : orderedTags) {
            json.add(name);
        }
        mv.addObject("json", json);
        return mv;
    }
    */
    
    @RequestMapping("/tags.json")
    public ModelAndView categories(HttpServletRequest request,
            @RequestParam(required = true) String term) throws Exception {

        ModelAndView mv = new ModelAndView("json-string");
        
        // Perform solr search
        String query1 = String.format("nested-tag-1:%s*", term);
        QueryResponse queryResponse = solrService.search(query1);
        List<Faq> faqs = solrService.extractFaqs(queryResponse);
        
        // Build a uniqe list of category strings
        Set<String> uniqueStrings = new HashSet<String>();
        for (Faq faq : faqs) {
            uniqueStrings.add(faq.getNestedTags()[0].asPipeSeparatedString());
        }
        
        // Convert to a list and Sort alpha
        List<String> orderedStrings = new ArrayList(uniqueStrings);
        Collections.sort(orderedStrings);

        // Build results
        JSONArray json = new JSONArray();
        for (String name : orderedStrings) {
            json.add(name);
        }
        mv.addObject("json", json);
        return mv;
    }
    
    /**
     * 
     * @param nestedLevel the tag level to search for. 1 is top, 4 is bottom.
     * @param term to search for. May be null or empty string.
     * @param element1 conditional top-level tag value (i.e. if solr only has nested tag a|b|c|d this must be a or null or else nothing is returned)
     * @param element2
     * @param element3
     * @return
     */
    private ModelAndView nestedTagSearch(int nestedLevel, String term, String element1, String element2, String element3) {
    	
        ModelAndView mv = new ModelAndView("json-string");
        
        // Perform solr search
        List<Faq> faqs = solrService.searchByNestedTag(nestedLevel, term);
        
        // Build a uniqe list of category strings based on nested tags that match the term.
        // Note that solr search may return multiple tags per faq so we need to ignore tags that don't match
        Set<String> uniqueStrings = new HashSet<String>();
        for (Faq faq : faqs) {
        	for (int i = 0; i < faq.getNestedTags().length; i++) {
        		NestedTag matchCandidate = faq.getNestedTags()[i];
        		if (matchCandidate == null) {
        			continue;
        		}
        		
        		if (matchCandidate.getElements().size() < nestedLevel) {
        			continue;
        		}
    			
        		String candidateString = matchCandidate.getElements().get((nestedLevel-1));
        		
        		// Does the faq result match the original term?
        		if (StringUtils.hasText(term)) {	        		
	    			if (!candidateString.startsWith(term)) {
	    				continue;
	    			}
        		}
        				
				// Was prior element1 specified, and does it match?        				
				if (element1 != null && !(element1.equalsIgnoreCase(matchCandidate.getElements().get(0)))) {
					continue;
				}

				// Was prior element2 specified, and does it match?        				
				if (element2 != null && !(element2.equalsIgnoreCase(matchCandidate.getElements().get(1)))) {
					continue;
				}

				// Was prior element3 specified, and does it match?        				
				if (element3 != null && !(element3.equalsIgnoreCase(matchCandidate.getElements().get(2)))) {
					continue;
				}
				
				// We have a match
				uniqueStrings.add(candidateString);
        	}
        }
        
        // Convert to a list and Sort alpha
        List<String> orderedStrings = new ArrayList(uniqueStrings);
        Collections.sort(orderedStrings);

        // Build results
        JSONArray json = new JSONArray();
        for (String name : orderedStrings) {
            json.add(name);
        }
        mv.addObject("json", json);
        return mv;
    }
    
    private ModelAndView emptyJSON() {
    	ModelAndView mv = new ModelAndView("json-string");
        JSONArray json = new JSONArray();
        mv.addObject("json", json);
        return mv;
    }
    
    @RequestMapping("/tags1.json")
    public ModelAndView tags1(HttpServletRequest request,
            @RequestParam(required = true) String term) throws Exception {

    	return nestedTagSearch(1, term, null, null, null);
    }    
    
    @RequestMapping("/tags2.json")
    public ModelAndView tags2(HttpServletRequest request,
            @RequestParam(required = true) String term, 
            @RequestParam(required = true) String tagX1) throws Exception {
    	
    	return nestedTagSearch(2, term, tagX1, null, null);
    }    
    
    @RequestMapping("/tags3.json")
    public ModelAndView tags3(HttpServletRequest request,
            @RequestParam(required = true) String term, 
            @RequestParam(required = true) String tagX1, 
            @RequestParam(required = true) String tagX2) throws Exception {

    	return nestedTagSearch(3, term, tagX1, tagX2, null);
    }    
    
    @RequestMapping("/tags4.json")
    public ModelAndView tags4(HttpServletRequest request,
            @RequestParam(required = true) String term, 
            @RequestParam(required = true) String tagX1,
            @RequestParam(required = true) String tagX2,
            @RequestParam(required = true) String tagX3) throws Exception {

    	return nestedTagSearch(3, term, tagX1, tagX2, tagX3);
    }    
    
    @RequestMapping("/faq.json")
    public ModelAndView faq(HttpServletRequest request,
            @RequestParam(required = true) String key) throws Exception {

        ModelAndView mv = new ModelAndView("json-string");
        
        Faq faq = solrService.loadFaq(key);
        
        // Build results
        JSONObject json = JSONObject.fromObject(faq);
        mv.addObject("json", json);
        return mv;
    }

}
