package com.jiageng.server.servlet;

import com.jiageng.server.Request;
import com.jiageng.server.Response;

public class RegisterServlet implements Servlet{
    @Override
    public void serve(Request request, Response response) {
        System.out.println("register service");
    }
}
