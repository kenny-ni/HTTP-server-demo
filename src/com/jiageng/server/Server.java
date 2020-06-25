package com.jiageng.server;

import com.jiageng.server.servlet.MainPageServlet;
import com.jiageng.server.servlet.Servlet;
import com.jiageng.server.xml.XMLParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private XMLParser parser = new XMLParser();

    public void start(){
        System.out.println("starting server");
        try {
            this.serverSocket = new ServerSocket(8888);
            parser.parse("web.xml");
        }catch (Exception e){
            System.out.println("server start failed");
            System.exit(-1);
        }
    }

    public void connect(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("a client established connection");
            new Thread(new Connection(client, parser)).run();

        } catch (IOException e) {
            System.out.println("client error");
        }
    }

    public void close() {
        try {
            System.out.println("turning down server");
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
