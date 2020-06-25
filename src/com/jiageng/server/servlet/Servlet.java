package com.jiageng.server.servlet;

import com.jiageng.server.Request;
import com.jiageng.server.Response;

public interface Servlet {
    public void serve(Request request, Response response);
}
