package org.catamarancode.faq.web.support;

import java.util.ArrayList;
import java.util.List;

import org.catamarancode.faq.entity.Faq;

public class CategoryNode {

    private String name;
    private List<CategoryNode> childNodes = new ArrayList<CategoryNode>();
    private List<Faq> faqs = new ArrayList<Faq>();

    public int getChildCount() {
        return childNodes.size() + faqs.size();
    }
    
    public int getFaqCount() {
        return faqs.size();
    }
    
    public void addFaq(Faq faq) {
        this.faqs.add(faq);
    }
    
    public CategoryNode getOrCreateChild(String name) {
        
        // Do we have this child already?
        for (CategoryNode childNode : childNodes) {
            if (childNode.getName().equals(name)) {
                return childNode;
            }
        }
        
        // No.  Create it
        CategoryNode childNode = new CategoryNode();
        childNode.setName(name);
        this.childNodes.add(childNode);
        return childNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryElement) {
        this.name = categoryElement;
    }

    public List<CategoryNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<CategoryNode> childNodes) {
        this.childNodes = childNodes;
    }

    public List<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<Faq> faqs) {
        this.faqs = faqs;
    }
}
