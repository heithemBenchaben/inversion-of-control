package com.hch.ioc.web;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Lunch {

    public static final String FRONT_CONTROLLER = "front_controller";

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8085);
        String contextPath = "/";
        tomcat.setBaseDir("/");
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);
        context.addApplicationListener(IocServletContextListener.class.getName());
        FrontController frontController = new FrontController();
        tomcat.addServlet(contextPath, FRONT_CONTROLLER, frontController);
        context.addServletMappingDecoded("/", FRONT_CONTROLLER);
        tomcat.start();
        tomcat.getServer().await();
    }
}
