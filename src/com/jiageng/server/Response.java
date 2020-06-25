package com.jiageng.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    private StringBuilder content;
    private StringBuilder head;
    private int len;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    private boolean error = false;

    public Response(){}
    public Response(OutputStream os){
        this.bw = new BufferedWriter(new OutputStreamWriter(os));
    }
    public Response(Socket client){
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.content = new StringBuilder();
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        }
    }

    //add content
    public Response print(String info){
        content.append(info);
        len += info.getBytes().length;
        return this;
    }

    public Response println(String info){
        content.append(info).append(CRLF);
        len += info.getBytes().length;
        return this;
    }

    public void respond(int code) {
        try {
            bw.write(buildHead(code));
            bw.write(content.toString());
            bw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String buildHead(int code){
        head = new StringBuilder();
        head.append("HTTP/1.0").append(BLANK);
        if (error) code = 505;
        switch (code){
            case 200:
                head.append("OK").append(BLANK);
                break;
            case 404:
                head.append("NOT FOUND").append(BLANK);
                break;
            case 505:
                head.append("SERVER ERROR").append(BLANK);
        }
        head.append("Date:").append(new Date()).append(CRLF);
        head.append("Server:").append("test Server/0.0.1;charset=UTF-8").append(CRLF);
        head.append("Content-type:text/html").append(CRLF);
        head.append("Content-length:").append(len).append(CRLF);
        head.append(CRLF);
        return head.toString();
    }
}
