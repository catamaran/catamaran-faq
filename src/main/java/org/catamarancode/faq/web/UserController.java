package org.catamarancode.faq.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.catamarancode.faq.entity.Faq;
import org.catamarancode.faq.entity.NestedTag;
import org.catamarancode.faq.entity.User;
import org.catamarancode.faq.service.MessageContext;
import org.catamarancode.faq.service.SolrService;
import org.catamarancode.faq.service.UserContext;
import org.catamarancode.faq.web.support.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Scope("request")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SolrService solrService;
    
    @Autowired
	private UserContext userContext;        

    @Autowired
	private MessageContext messageContext;   
    
	@RequestMapping(value = "/faq-edit", method = RequestMethod.GET)	
	public String faqEditGet(Map<String,Object> model, @RequestParam(value="key", required=false) String faqId, HttpServletRequest request) {

		messageContext.addPendingToModel(model);
		if (!userContext.isLoggedIn(request)) {
    		return "redirect:/signin";
    	}
		userContext.prepareModel(model);
		
        Faq faq = solrService.loadFaq(faqId);
        model.put("faq", faq);
        return "faq-edit";		
	}
    
    @RequestMapping(value = "/faq-edit", method = RequestMethod.POST)
    public String faqEditPost(Map<String,Object> model, HttpServletRequest request) throws Exception {
    	
    	if (!userContext.isLoggedIn(request)) {
    		return "redirect:/signin";
    	}
    	userContext.prepareModel(model);    	

        String faqId = request.getParameter("key");
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        
        Faq faq = null;
        if (StringUtils.hasText(faqId)) {
            faq = solrService.loadFaq(faqId);            
        } else {
            faq = new Faq();
            faq.setContextId("default");
            faq.setCreatedTime(new Date());
            
            // Note we assign owner upon creation.  Edits do not change the owner.
            faq.setOwnerKey(userContext.getUserKey());
            faq.setOwnerName(userContext.getUser().getName());
        }            
        faq.setQuestion(question);
        faq.setAnswerAndParseMarkdown(answer);
        faq.setLastModifiedTime(new Date());

        // tags
        boolean hasTags = false;
        for (int i=0; i < faq.getNestedTags().length; i++) {
        	NestedTag currentTag = null;
        	for (int j=0; j < 4; j++) {
        		String tagName = "tag" + (i+1) + (j+1);
        		String value = request.getParameter(tagName);
        		boolean hasValue = StringUtils.hasText(value);
        		if (hasValue) {
        			hasTags = true;
        		}
        		
        		// Overwrite an existing tag?
        		if (j == 0 && hasValue) {
        			currentTag = new NestedTag();
        			faq.getNestedTags()[i] = currentTag;
        		}
        		
        		if (hasValue && currentTag != null) {
        			currentTag.addElement(value);
        		} else if (hasValue) {
        			throw new IllegalArgumentException("Missing values above for tag " + (i+1));
        		}
        	}
        }
        
        if (!hasTags) {
        	throw new IllegalArgumentException("At least one Topic must be entered");
        }
        
        this.solrService.save(faq);
        
        return "redirect:faq?key=" + faq.getKey();
    }

}
