package org.catamarancode.faq.entity;


public class Tag {
    
    /**
     * tag string (i.e. "java" or "freemarker" or "workstation setup"
     */
    private String name;
    
    public static Tag createFromString(String s) {
        Tag tag = new Tag();
        tag.name = s;
        return tag;
    }
    
    public void setName(String element) {
        this.name = element;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return this.getName();
    }
}
