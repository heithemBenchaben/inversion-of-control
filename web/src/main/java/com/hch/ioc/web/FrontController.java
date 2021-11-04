package com.hch.ioc.web;

import com.hch.ioc.core.registries.ScanPathRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class FrontController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>Welcome Into Embedded DEV </title>");
        out.println("</head>");
        out.println("<body>");
        ScanPathRegistry
                .getInstance()
                .getScanPathList()
                .stream()
                .forEach(path -> out.println(String.format("<p>%s</>", path)));
        out.println("</body>");
        out.println("</html>");
    }
}
