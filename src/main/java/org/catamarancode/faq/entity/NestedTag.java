package org.catamarancode.faq.entity;

import java.util.ArrayList;
import java.util.List;

import org.catamarancode.util.CollectionUtils;

/* A tag with multiple levels - e.g. programming|java|persistence
 * 
 */
public class NestedTag {
    
    /**
     * tag element strings (i.e. "development|java|spring|mail"
     */
    private List<String> elements = new ArrayList<String>();
    
    public String asPipeSeparatedString() {
        return CollectionUtils.toString(elements, "|");        
    }
    
    public String getPipeSeparated() {
    	return asPipeSeparatedString();
    }
    
    public String getColonSeparated() {
    	return CollectionUtils.toString(elements, " : ");
    }
    
    public static NestedTag createFromPipeSeparatedString(String pipeSeparatedTag) {
        NestedTag tag = new NestedTag();
        tag.setFromPipeSeparatedString(pipeSeparatedTag);
        return tag;
    }
    
    public static NestedTag createFromStringList(List<String> strings) {
        NestedTag tag = new NestedTag();
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
