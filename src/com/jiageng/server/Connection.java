package com.jiageng.server;

import com.jiageng.server.servlet.MainPageServlet;
import com.jiageng.server.servlet.Servlet;
import com.jiageng.server.xml.XMLParser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.Socket;

public class Connection implements Runnable{
    private Socket client;
    private XMLParser parser;

    public Connection(Socket client, XMLParser parser){
        this.client = client;
        this.parser = parser;
    }

    @Override
    public void run() {
        try {
            InputStream is = new BufferedInputStream(client.getInputStream());
            byte[] buffer = new byte[1024 * 100];
            int len = is.read(buffer);
            Response response = new Response(client);
            if (len == -1) { //received a query
                response.respond(404);
            }
            String query = new String(buffer, 0, len);
            Request request = new Request(query);
            String url = request.getUrl();
//            String url = null;
            if ("/".equals(url)) {
                Servlet servlet = new MainPageServlet();
                servlet.serve(request, response);
                return;
            }
            Servlet servlet = parser.getServletbyURL(url);
            if (null == servlet) {
                response.respond(404);
                return;
            }
            servlet.serve(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                client.close();
            }catch (Exception closeexcep){
                closeexcep.printStackTrace();
            }
        }
    }
}
