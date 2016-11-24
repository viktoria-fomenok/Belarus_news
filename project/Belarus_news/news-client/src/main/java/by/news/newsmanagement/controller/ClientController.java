package by.epam.newsmanagement.controller;

import by.epam.newsmanagement.command.ICommand;
import by.epam.newsmanagement.exception.ClientCommandException;
import by.epam.newsmanagement.exception.CommandException;
import by.epam.newsmanagement.exception.ServerCommandException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class ClientController extends HttpServlet {
    private static final String ERROR_400_PAGE = "WEB-INF/view/error/400.jsp";
    private static final String ERROR_500_PAGE = "WEB-INF/view/error/500.jsp";
    private static final String COMMAND_KEY = "command";
    private static final Logger LOG = LogManager.getLogger(ClientController.class);
    private WebApplicationContext context;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = doProcess(request);
        response.sendRedirect(page);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = doProcess(request);
        request.getRequestDispatcher(page).forward(request, response);
    }


    private String doProcess(HttpServletRequest request)
            throws ServletException, IOException{
        String page;
        try {
            String commandName = request.getParameter(COMMAND_KEY);
			if(commandName == null){
				LOG.warn("Command name is not found");
                return ERROR_400_PAGE;
			}
            if(!context.containsBean(commandName)){
                LOG.warn("Command name is not correct");
                return ERROR_400_PAGE;
            }
            ICommand command = (ICommand) context.getBean(commandName);
            page = command.execute(request);
        } catch (ClientCommandException e) {
            LOG.warn(e);
            return ERROR_400_PAGE;
        } catch (Exception e) {
            LOG.error(e);
            return ERROR_500_PAGE;
        }
        return page;
    }
}
