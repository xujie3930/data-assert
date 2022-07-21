package com.hashtech.config;

import java.io.IOException;
import javax.servlet.*;

public class Filter01 implements Filter {
    //初始化方法.程序启动的时候调用此方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter01 init ......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter01 doFilter ......");
//执行filterChain.doFilter()方法之后才会调用servlet的service()方法
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //销毁方法,程序停止或者重新部署的时候会调用方法
    @Override
    public void destroy() {
        System.out.println("Filter01 destroy ......");
    }
}
