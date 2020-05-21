//package com.capitalbanker.cbk.delivery.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.web.servlet.DispatcherServlet;
//
//@ComponentScan()
//@EnableAutoConfiguration
//public class MyApplication extends SpringBootServletInitializer {
//    private static Logger LOG = LoggerFactory.getLogger(MyApplication.class);
//    public static void main(String[] args) {
//        ApplicationContext ctx = SpringApplication.run(MyApplication.class, args);
//        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
//    }}