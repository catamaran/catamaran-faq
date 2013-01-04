package org.catamarancode.faq.web.support;

import java.util.SortedSet;
import java.util.TreeSet;

import org.catamarancode.faq.entity.Faq;

public class NestedTagNode implements Comparable<Object> {

	private NestedTagNode parent;
    private String name;
    private SortedSet<NestedTagNode> childNodes = new TreeSet<NestedTagNode>();
    private SortedSet<Faq> faqs = new TreeSet<Faq>();

    public NestedTagNode(NestedTagNode parent) {
    	this.parent = parent;
    }
    
    public NestedTagNode getParent() {
    	return this.parent;
    }
    
    public String getColonSeparatedName() {
    	StringBuilder sb = new StringBuilder();
    	this.prependParentNames(sb);
    	return sb.toString();
    }
    
    public String getPipeSeparatedName() {
    	StringBuilder sb = new StringBuilder();
    	this.prependPipeParentNames(sb);
    	return sb.toString();
    }    
    
    private void prependParentNames(StringBuilder sb) {
    	boolean bottom = true;
    	if (sb.length() > 0) {
    		bottom = false;
    	}    	
    	if (!bottom) {
    		sb.insert(0, this.name + " : ");
    	} else {
    		sb.insert(0, this.name);
    	}
    	if (this.getParent() != null && this.getParent().getName() != null) {
    		this.getParent().prependParentNames(sb);
    	}    	
    }

    private void prependPipeParentNames(StringBuilder sb) {
    	boolean bottom = true;
    	if (sb.length() > 0) {
    		bottom = false;
    	}    	
    	if (!bottom) {
    		sb.insert(0, this.name + "|");
    	} else {
    		sb.insert(0, this.name);
    	}
    	if (this.getParent() != null && this.getParent().getName() != null) {
    		this.getParent().prependPipeParentNames(sb);
    	}    	
    }

    public int getChildCount() {
        return childNodes.size() + faqs.size();
    }
    
    public int getFaqCount() {
        return faqs.size();
    }
    
    public void addFaq(Faq faq) {
        this.faqs.add(faq);
    }
    
    public NestedTagNode getOrCreateChild(String name) {
        
        // Do we have this child already?
        for (NestedTagNode childNode : childNodes) {
            if (childNode.getName().equals(name)) {
                return childNode;
            }
        }
        
        // No.  Create it
        NestedTagNode childNode = new NestedTagNode(this);
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

    public SortedSet<NestedTagNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(SortedSet<NestedTagNode> childNodes) {
        this.childNodes = childNodes;
    }

    public SortedSet<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(SortedSet<Faq> faqs) {
        this.faqs = faqs;
    }

	@Override
	public int compareTo(Object o) {
		NestedTagNode otherNode = (NestedTagNode) o;
		return this.getColonSeparatedName().compareToIgnoreCase(otherNode.getColonSeparatedName());
	}
}
