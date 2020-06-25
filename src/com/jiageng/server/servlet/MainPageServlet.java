package com.jiageng.server.servlet;

import com.jiageng.server.Request;
import com.jiageng.server.Response;

import java.util.List;
import java.util.Map;

public class MainPageServlet implements Servlet {
    public void serve(Request request, Response response){
        System.out.println("main page");
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("fail");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.println("main page");
        response.println("welcome");
        response.print("</body>");
        response.print("</html>");
        response.respond(200);
    }
}
