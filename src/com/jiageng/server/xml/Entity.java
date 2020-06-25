package com.jiageng.server.xml;

public class Entity {
    private String servletName;
    private String className;
    public Entity(){
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getServletName() {
        return servletName;
    }

    public String getClassName() {
        return className;
    }
}
