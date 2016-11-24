package by.epam.newsmanagement.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Locale;

/**
 * Created by NikitOS on 18.04.2016.
 */
public class ProjectServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
