package org.catamarancode.faq.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.catamarancode.faq.service.solr.SolrService;
import org.catamarancode.type.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class Faq {
    
    private Logger logger = LoggerFactory.getLogger(Faq.class);
    
    private String key;
    private String contextId;
    private String question;
    private String answer;
    private String ownerKey;
    private String shortId; // Used for html links etc
    private Name ownerName;
    private Category category;
    
    /**
     * Pipe-separated tag strings (i.e. "development|java|spring|mail")
     */
    private List<Tag> tags = new ArrayList<Tag>();
    
    private static SolrService solrService;
    
    /**
     * Used so that we can distinguish between a key and a short id by a simple String.startsWith
     */
    public static final String SHORT_ID_PREFIX = "FAQSHORTID";
    
    public Faq() {    
        if (this.shortId == null) {
            this.shortId = SHORT_ID_PREFIX + RandomStringUtils.randomAlphanumeric(10);    
        }        
    }
    
    public Faq(SolrDocument doc) {

        this.key = (String) doc.getFieldValue("key");
        this.shortId = (String) doc.getFieldValue("short-id");
        this.contextId = (String) doc.getFieldValue("context-id");
        this.question = (String) doc.getFieldValue("question");
        this.answer = (String) doc.getFieldValue("answer");
        this.ownerKey = (String) doc.getFieldValue("owner-key");
        String ownerNameStr =  (String) doc.getFieldValue("owner-name");
        if (StringUtils.hasText(ownerNameStr)) {
            this.ownerName = Name.createFromFullNameString(ownerNameStr);    
        }        

        List tagStrings = (List) doc.getFieldValues("tag");
        if (tagStrings != null) {
            for (int i = 0; i < tagStrings.size(); i++) {
                Tag tag = new Tag();
                tag.setName((String) tagStrings.get(i));
                this.tags.add(tag);
            }
        }
        
        List categoryStrings = (List) doc.getFieldValues("category");
        if (categoryStrings != null) {
            this.setCategory(Category.createFromStringList(categoryStrings));
        }
    }

    public SolrInputDocument toSolrInputDocument() {
        SolrInputDocument inputDoc = new SolrInputDocument();

        inputDoc.addField("key", this.getKey());
        inputDoc.addField("short-id", this.getShortId());
        inputDoc.addField("context-id", this.getContextId());
        inputDoc.addField("question", this.getQuestion());
        inputDoc.addField("answer", this.getAnswer());
        inputDoc.addField("owner-key", this.getOwnerKey());
        inputDoc.addField("owner-name", this.getOwnerName());
        
        if (this.category != null) {
            for (String s : this.category.getElements()) {
                inputDoc.addField("category", s);
            }
        }

        for (Tag tag : tags) {
            inputDoc.addField("tag", tag.getName());
        }

        // Default field for all solr docs of this type/class
        inputDoc.addField("document-type", "FAQ");
        
        return inputDoc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestion() {
        return question;
    }

    public static void setSolrService(SolrService service) {
        solrService = service;
    }
    
    /**
     * Sets the question variable and also sets the key value based on the question
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
        if (this.key == null) {
            
            // Create key from question
            String strippedQuestion = question.replaceAll("[^A-Za-z0-9]", "_");
            String newKey = null;
            if (strippedQuestion.length() > 20) {
                newKey = strippedQuestion.substring(0, 20);
            } else {
                newKey = strippedQuestion;
            }
            
            // Replace spaces with underscores
            newKey = newKey.replace(" ", "_");
            
            // Make sure name is unique            
            Faq dupe = solrService.loadFaq(newKey);
            int suffix = 1;
            while (dupe != null) {
                newKey = newKey.concat(String.valueOf(suffix));
                logger.debug("Trying alternate key: " + newKey);
                dupe = solrService.loadFaq(newKey);
                suffix++;
            }
            
            this.setKey(newKey);
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Tag> getTags() {
        return tags;
    }
    
    public Tag getTag1() {
        if (!tags.isEmpty()) {
            return tags.get(0);
        }
        return null;
    }
    
    public Tag getTag2() {
        if (tags.size() > 1) {
            return tags.get(1);
        }
        return null;
    }
    
    public Tag getTag3() {
        if (tags.size() > 2) {
            return tags.get(2);
        }
        return null;
    }
    
    public void addTag(String tag) {
        this.tags.add(Tag.createFromString(tag));
    }
    
    public void clearTags() {
        this.tags = new ArrayList<Tag>();
    }
    
    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getOwnerKey() {
        return ownerKey;
    }

    public void setOwnerKey(String ownerId) {
        this.ownerKey = ownerId;
    }

    public Name getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(Name ownerName) {
        this.ownerName = ownerName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortId() {
        return shortId;
    }

}
