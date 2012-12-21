package org.catamarancode.faq.entity;

import org.catamarancode.type.Name;

public class User {

    private String key;
    private Name name;
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Name getName() {
        return name;
    }
    public void setName(Name name) {
        this.name = name;
    }
}
