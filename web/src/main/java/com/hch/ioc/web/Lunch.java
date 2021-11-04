package com.hch.ioc.web;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Lunch {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8085);
        String contextPath = "/";
        tomcat.setBaseDir("/");
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);
        context.addApplicationListener("com.hch.ioc.web.IocServletContextListener");
        FrontController frontController = new FrontController();
        tomcat.addServlet(contextPath, "front_controller", frontController);
        context.addServletMappingDecoded("/", "front_controller");
        tomcat.start();
        tomcat.getServer().await();
    }
}
