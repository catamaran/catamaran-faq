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
            this.addElement(parts[i].trim());
        }
    }
    
    public void addElement(String element) {
        this.elements.add(element);
    }
    
    public List<String> getElements() {
        return elements;
    }
    
    public String getElementAtLevel(int level) {
    	if (this.elements.size() >= level) {
    		return this.elements.get(level-1);
    	}
    	return null;
    }
    
    public String toString() {
        return asPipeSeparatedString();
    }
    
    public String getLastElement() {
    	return this.elements.get(this.elements.size() - 1);
    }
    
    public String getFirstElement() {
    	if (this.elements.size() > 0) {
    		return this.elements.get(0);
    	}
    	return null;
    }
    
    public String getSecondElement() {
    	if (this.elements.size() > 1) {
    		return this.elements.get(1);
    	}
    	return null;
    }
    
    public String getThirdElement() {
    	if (this.elements.size() > 2) {
    		return this.elements.get(2);
    	}
    	return null;
    }
    
    public String getFourthElement() {
    	if (this.elements.size() > 3) {
    		return this.elements.get(3);
    	}
    	return null;
    }    
    
    public int getNestedLevel() {
    	return this.elements.size();
    }
    
    public boolean match(NestedTag otherTag) {
    	return match(otherTag, false);
    }
    
    /**
     * 
     * @param otherTag
     * @param lenient if you also want to match with parent tags
     * @return
     */
    public boolean match(NestedTag otherTag, boolean lenient) {
    	if (otherTag == null) {
    		return false;
    	}
    	
    	// Identical match?
    	if (this.asPipeSeparatedString().equals(otherTag.asPipeSeparatedString())) {
    		return true;
    	}
    	
    	if (!lenient) {
    		return false;
    	}
    	
    	// Determine shortest match level
    	int compareLevel = this.getNestedLevel();
    	if (otherTag.getNestedLevel() < this.getNestedLevel()) {
    		compareLevel = otherTag.getNestedLevel();
    	}
    	
    	for (int i = 0; i < compareLevel; i++) {
    		if (!this.elements.get(i).equals(otherTag.getElements().get(i))) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    /**
     * Clone this object into a new tag that has the last element removed
     * @return
     */
    public NestedTag getParentTag() {
    	if (this.getNestedLevel() < 2) {
    		return null;
    	}
    	return this.getTagAtLevel(this.getNestedLevel() - 1);
    }
    
    public NestedTag getTagAtLevel(int level) {
    	NestedTag aTag = new NestedTag();
    	if (level < 1) {
    		return null;
    	}
    	for (int i = 0; i < level; i++) {
    		aTag.addElement(this.getElements().get(i));
    	}
    	return aTag;
    }

}
