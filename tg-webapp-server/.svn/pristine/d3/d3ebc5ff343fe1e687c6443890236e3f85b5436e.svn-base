package cn.plou.web.common.shiro.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class CorsFilter implements Filter {
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,PUT,PATCH");
        response.addHeader("Access-Control-Max-Age","3600");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type,Authorization, Accept");
        //response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(200);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
