package com.jiageng.server.xml;

import com.jiageng.server.servlet.Servlet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser {
//    private String path = "com/jiageng/server/web.xml";
    private Map<String, String> url2name = null;
    private Map<String, String> name2class = null;

    public void parse(String path) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), handler);
        List<Entity>entities = handler.getEntities();
        List<Mapping> mappings = handler.getMappings();
        this.url2name = new HashMap<>();
        for (Mapping mapping: mappings){
            for (String url: mapping.getUrls()){
                url2name.put(url, mapping.getServletName());
            }
        }
        this.name2class = new HashMap<>();
        for (Entity entity: entities){
            name2class.put(entity.getServletName(), entity.getClassName());
        }
    }

    public Servlet getServletbyURL(String url){
        try{
            String servletName = url2name.get(url);
            String className = name2class.get(servletName);
            Class clz= Class.forName(className);
            Servlet servlet = (Servlet)clz.getConstructor().newInstance();
            return servlet;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("invalid url");
            return null;
        }
    }

}

class XMLHandler extends DefaultHandler {
    private List<Entity> entities;
    private Entity entity;
    private List<Mapping> mappings;
    private Mapping mapping;
    private String tag = null;
    private boolean isMapping = false;

    public XMLHandler() {
        this.entities = new ArrayList<>();
        this.mappings = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if(null != qName){
            tag = qName;
            if(tag.equals("servlet")){
                isMapping = false;
                entity = new Entity();
            }
            else if(tag.equals("servlet-mapping")){
                isMapping = true;
                mapping = new Mapping();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();
        if(null!=tag){
            if(isMapping){
                if(tag.equals("servlet_name")){
                    mapping.setServletName(content);
                }else if(tag.equals("url")){
                    mapping.addUrl(content);
                }
            }else{
                if(tag.equals("servlet_name"))entity.setServletName(content);
                else if (tag.equals("servlet_class"))entity.setClassName(content);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName != null){
            if(qName.equals("servlet")) this.entities.add(entity);
            else if(qName.equals("servlet-mapping")) this.mappings.add(mapping);
        }
        tag = null;
    }
}
