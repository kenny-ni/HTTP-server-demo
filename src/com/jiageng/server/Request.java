package com.jiageng.server;

import java.util.*;

public class Request {
//    private String query;
    private String method;
    private String url;
    private Map<String, List<String>> params;
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    public Request(String query){
        int startIndex = 0;
        int endIndex = query.indexOf(BLANK);
        this.method = query.substring(startIndex, endIndex);
        startIndex = endIndex + 1;
        endIndex = query.indexOf(BLANK, startIndex);
        this.url = query.substring(startIndex, endIndex);
        if ((url.contains("?"))){
            String[] temp = url.split("\\?");
            url = temp[0];
            this.params = new HashMap<>();
            String[] kvs = temp[1].split("&");
            for (String kv: kvs){
                temp = kv.split("=");
                if (temp.length < 2) continue;
                String key = temp[0];
                String value = temp[1];
                if (!params.containsKey(key)) params.put(key, new ArrayList<>());
                params.get(key).add(value);
            }
        }
    }

    public String getMethod(){
        return this.method;
    }

    public String getUrl(){
        return this.url;
    }

    public Map<String, List<String>> getParams(){
        return this.params;
    }
}
