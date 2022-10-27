package org.sparkera.nft.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

//@WebFilter(value = "/*", filterName = "oauthFilter")
public class OAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("====>进入OAuthFilter doFilterInternal过滤器====");
        System.out.println("请求地址: " + request.getRequestURL());

        System.out.println("GET请求参数: ");
        Enumeration<String> parameters = request.getParameterNames();
        String parameterName = "";
        while (parameters.hasMoreElements()) {
            parameterName = parameters.nextElement();
            System.out.println("参数名称: " + parameterName + ", 值: " + request.getParameter(parameterName));
        }

        // 工具类
        //FilterConfig filterConfig = super.getFilterConfig();
        //ServletContext servletContext = super.getServletContext();
        //Environment environment = super.getEnvironment();

        System.out.println("====>结束OAuthFilter doFilterInternal过滤器====");
        filterChain.doFilter(request, response);
    }
}
