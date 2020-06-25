package com.jiageng.server.servlet;

import com.jiageng.server.Request;
import com.jiageng.server.Response;

import java.util.List;
import java.util.Map;

public class LoginServlet implements Servlet{
    @Override
    public void serve(Request request, Response response) {
        System.out.println("login service");
        Map<String, List<String>> reqparams = request.getParams();
        String userName = (String)reqparams.get("uname").toArray()[0];
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("success");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.println("log in success");
        response.println("welcome back" + userName);
        response.print("</body>");
        response.print("</html>");
        response.respond(200);
    }
}
