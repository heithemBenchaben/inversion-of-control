package com.hch.ioc.test;

import com.hch.ioc.core.IocRunner;
import com.hch.ioc.core.registries.BeanRegistry;
import com.hch.ioc.test.beans.impl.C;
import com.hch.ioc.test.beans.impl.P;
import com.hch.ioc.web.Lunch;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        Lunch.main(args);
        print();
    }

    private static void print() {
        System.out.println("============== Print Results ");
        System.out.println("============== Singleton Beans ==============");
        System.out.println("for singleton all request share the same instance");
        C c1 = (C) BeanRegistry.getInstance().getBean(C.class);
        C c2 = (C) BeanRegistry.getInstance().getBean(C.class);
        c1.getI2().print();
        c2.getI2().print();
        System.out.println("============== Prototype Beans ==============");
        System.out.println("for prototype each request will get a new instance");
        P p1 = (P) BeanRegistry.getInstance().getBean(P.class);
        P p2 = (P) BeanRegistry.getInstance().getBean(P.class);
        p1.getI6().print();
        p2.getI6().print();
        System.out.println("===============================================");
    }
}