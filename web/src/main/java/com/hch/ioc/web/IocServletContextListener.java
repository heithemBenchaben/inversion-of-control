package com.hch.ioc.web;

import com.hch.ioc.core.IocRunner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class IocServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        IocRunner.run();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
