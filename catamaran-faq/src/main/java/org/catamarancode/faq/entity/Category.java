package org.catamarancode.faq.entity;

import java.util.ArrayList;
import java.util.List;

import org.catamarancode.util.CollectionUtils;

public class Category {
    
    /**
     * category taxonomy strings (i.e. "development|java|spring|mail"
     */
    private List<String> elements = new ArrayList<String>();
    
    public String asPipeSeparatedString() {
        return CollectionUtils.toString(elements, "|");        
    }
    
    public static Category createFromPipeSeparatedString(String pipeSeparatedTag) {
        Category tag = new Category();
        tag.setFromPipeSeparatedString(pipeSeparatedTag);
        return tag;
    }
    
    public static Category createFromStringList(List<String> strings) {
        Category tag = new Category();
        tag.setFromStringList(strings);
        return tag;
    }
    
    public void setFromStringList(List<String> strings) {
        for (String s : strings) {
            this.addElement(s);
        }
    }
    
    public void setFromPipeSeparatedString(String pipeSeparatedTags) {
        String[] parts = pipeSeparatedTags.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            this.addElement(parts[i]);
        }
    }
    
    public void addElement(String element) {
        this.elements.add(element);
    }
    
    public List<String> getElements() {
        return elements;
    }
    
    public String toString() {
        return asPipeSeparatedString();
    }
}
