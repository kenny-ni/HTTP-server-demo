package com.jiageng.server.xml;

import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String servletName;
    private Set<String> urls;

    public Mapping(){
        urls = new HashSet<>();
    }

    public void setServletName(String name){
        this.servletName = name;
    }

    public void addUrl(String pattern){
        urls.add(pattern);
    }

    public Set<String> getUrls(){
        return this.urls;
    }

    public String getServletName(){
        return this.servletName;
    }
}
