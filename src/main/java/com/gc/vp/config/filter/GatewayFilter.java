package com.gc.vp.config.filter;

import com.gc.vp.config.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/**")
@Component
@Slf4j
public class GatewayFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String loginName = name.split("-")[0];
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        requestWrapper.addHeader("loginUser", loginName);
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
