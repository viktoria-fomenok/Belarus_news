package by.epam.newsmanagement.command.impl;

import by.epam.newsmanagement.command.ICommand;
import by.epam.newsmanagement.command.parser.impl.PageParser;
import by.epam.newsmanagement.command.parser.impl.SearchCriteriaParser;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.exception.CommandException;
import by.epam.newsmanagement.exception.ServerCommandException;
import by.epam.newsmanagement.service.IAuthorService;
import by.epam.newsmanagement.service.INewsManagementService;
import by.epam.newsmanagement.service.INewsService;
import by.epam.newsmanagement.service.ITagService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("home-page")
public class HomePageCommand implements ICommand {
    private static final String VIEW_PAGE = "WEB-INF/view/home.jsp";
    private static final String PAGE_KEY = "page";
    private static final String SEARCH_CRITERIA_KEY = "criteria";
    private static final String AUTHOR_LIST_KEY = "authorList";
    private static final String TAG_LIST_KEY = "tagList";
    private static final String NEWS_LIST_KEY = "newsList";
    private static final String COUNT_OF_PAGES_KEY = "countOfPages";
    @Autowired
    private SearchCriteriaParser searchCriteriaParser;
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private ITagService tagService;
    @Autowired
    private INewsManagementService newsManagementServiceService;
    @Autowired
    private INewsService newsService;
    @Autowired
    private PageParser pageParser;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        SearchCriteria criteria = searchCriteriaParser.parse(request);
        Long page = pageParser.parse(request);
        try {
            request.setAttribute(SEARCH_CRITERIA_KEY, criteria);
            request.setAttribute(AUTHOR_LIST_KEY, authorService.getAllAuthors());
            request.setAttribute(TAG_LIST_KEY, tagService.readAll());
            request.setAttribute(NEWS_LIST_KEY, newsManagementServiceService
                    .getNewsBySearchCriteria(page, criteria));
            request.setAttribute(COUNT_OF_PAGES_KEY, newsService.countOfPages(criteria));
            request.setAttribute(PAGE_KEY, page);
        } catch (ServiceException e) {
            throw new ServerCommandException("Exception in Home Page Command", e);
        }
        return VIEW_PAGE;
    }

}
