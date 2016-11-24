package by.epam.newsmanagement.command.impl;

import by.epam.newsmanagement.command.ICommand;
import by.epam.newsmanagement.command.parser.impl.NewsIdParser;
import by.epam.newsmanagement.command.parser.impl.SearchCriteriaParser;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.exception.CommandException;
import by.epam.newsmanagement.exception.ServerCommandException;
import by.epam.newsmanagement.service.INewsManagementService;
import by.epam.newsmanagement.service.INewsService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("news-view")
public class NewsViewCommand implements ICommand {
    private static final String VIEW_PAGE = "WEB-INF/view/news-view.jsp";
    private static final String NEWS_TO_KEY = "newsTO";
    private static final String PAGER_KEY = "pager";
    private static final String SEARCH_CRITERIA_KEY = "criteria";
    @Autowired
    private INewsManagementService newsManagementServiceService;
    @Autowired
    private INewsService newsService;
    @Autowired
    private NewsIdParser newsIdParser;
    @Autowired
    private SearchCriteriaParser searchCriteriaParser;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        long newsId = newsIdParser.parse(request);
        SearchCriteria criteria = searchCriteriaParser.parse(request);
        try {
            request.setAttribute(NEWS_TO_KEY, newsManagementServiceService.getNewsTO(newsId));
            request.setAttribute(SEARCH_CRITERIA_KEY, criteria);
            request.setAttribute(PAGER_KEY, newsService.getPager(newsId, criteria));
        } catch (ServiceException e) {
            throw new ServerCommandException("Exception in HomePage Command", e);
        }
        return VIEW_PAGE;
    }

}
