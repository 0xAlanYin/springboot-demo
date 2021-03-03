package com.alan.spring.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Alan Yin
 * @date 2021/3/2
 */

public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 创建 Filter 时自动调用
        // 其中 FilterConfig 包含这个 Filter 的配置参数，比如 name 之类（从配置文件中读取）
    }

    @Override
    public void destroy() {
        // 销毁 Filter 时自动调用
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("拦截客户端发送的请求");
        chain.doFilter(request, response);
        System.out.println("拦截发给客户端的响应");
    }
}
