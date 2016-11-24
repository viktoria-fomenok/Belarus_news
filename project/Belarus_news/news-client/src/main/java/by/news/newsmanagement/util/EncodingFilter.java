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
public class EncodingFilter implements Filter {
   private static final String UTF_8_ENCODING_KEY = "UTF-8";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fChain)
            throws IOException, ServletException {
        request.setCharacterEncoding(UTF_8_ENCODING_KEY);
        response.setCharacterEncoding(UTF_8_ENCODING_KEY);
        fChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
