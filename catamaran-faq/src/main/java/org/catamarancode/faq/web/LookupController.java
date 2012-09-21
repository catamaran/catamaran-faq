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
import org.catamarancode.faq.entity.Tag;
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
    
    @RequestMapping("/categories.json")
    public ModelAndView categories(HttpServletRequest request,
            @RequestParam(required = true) String term) throws Exception {

        ModelAndView mv = new ModelAndView("json-string");
        
        // Perform solr search
        String query = String.format("category:%s*", term);
        QueryResponse queryResponse = solrService.search(query);
        List<Faq> faqs = solrService.extractFaqs(queryResponse);
        
        // Build a uniqe list of category strings
        Set<String> uniqueStrings = new HashSet<String>();
        for (Faq faq : faqs) {
            uniqueStrings.add(faq.getCategory().asPipeSeparatedString());
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
