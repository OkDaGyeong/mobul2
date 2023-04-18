package com.codehows.mobul.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class SessionListener implements ServletContextListener, HttpSessionListener {

    @Override
    public void contextInitialized(ServletContextEvent event){
        // 애플리케이션 초기화 시 실행되는 코드
    }
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // 세션이 생성될 때 실행되는 코드
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // 세션이 종료될 때 실행되는 코드
        HttpSession session = event.getSession();
        ServletContext context = session.getServletContext();
        context.log("SessionListener: Session Destroyed");
        session.invalidate();
    }


    @Override
    public void contextDestroyed(ServletContextEvent event){
        // 애플리케이션 종료 시 실행되는 코드
        ServletContext context = event.getServletContext();
        context.log("SessionListener: Context Destroyed");

        // 모든 세션 무효화
        HttpSession session = null;
        Enumeration<String> sessions = context.getAttributeNames();
        while(sessions.hasMoreElements()){
            session = (HttpSession)context.getAttribute(sessions.nextElement());
            session.invalidate();
        }
    }
}