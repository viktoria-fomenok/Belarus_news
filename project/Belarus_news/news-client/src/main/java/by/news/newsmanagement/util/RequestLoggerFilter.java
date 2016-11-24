package by.epam.newsmanagement.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Created by NikitOS on 18.04.2016.
 */
public class RequestLoggerFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(RequestLoggerFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fChain)
            throws IOException, ServletException {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> parameterNames = parameterMap.keySet();
        for(String parameterName: parameterNames){
            String[] parameters = parameterMap.get(parameterName);
            for(String parameter: parameters){
                sb.append(parameterName).append("=").append(parameter).append(", ");
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        LOG.info(sb.toString());
        fChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
