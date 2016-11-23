package by.epam.newsmanagement.controller;

import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.domain.NewsRecord;
import by.epam.newsmanagement.service.IAuthorService;
import by.epam.newsmanagement.service.INewsManagementService;
import by.epam.newsmanagement.service.INewsService;
import by.epam.newsmanagement.service.ITagService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/news"})
public class NewsController {

    @Autowired
    private INewsManagementService newsManagementServiceService;
    @Autowired
    private INewsService newsService;
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private ITagService tagService;
    private static final Logger LOG = LogManager.getLogger(NewsController.class);
    private static final String CRITERIA_KEY = "criteria";
    private static final String AUTHOR_LIST_KEY = "authorList";
    private static final String TAG_LIST_KEY = "tagList";
    private static final String NEWS_LIST_KEY = "newsList";
    private static final String COUNT_OF_PAGES_KEY = "countOfPages";
    private static final String NEWS_RECORD_KEY = "newsRecord";
    private static final String PAGE_KEY = "page";
    private static final String PAGER_KEY = "pager";
    private static final String NEWS_ID_KEY = "newsId";

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        LOG.error(e);
        return "error";
    }

    @RequestMapping(value = {"/", "/page-{page}"}, method = RequestMethod.GET)
    public String homePage(Model model, @PathVariable Optional<Long> page,
                           @ModelAttribute(CRITERIA_KEY) SearchCriteria criteria) throws ServiceException {
        model.addAttribute(CRITERIA_KEY, criteria);
        model.addAttribute(AUTHOR_LIST_KEY, authorService.getAllAuthors());
        model.addAttribute(TAG_LIST_KEY, tagService.readAll());
        model.addAttribute(NEWS_LIST_KEY, newsManagementServiceService
                .getNewsBySearchCriteria(page.orElse(1L), criteria));
        model.addAttribute(COUNT_OF_PAGES_KEY, newsService.countOfPages(criteria));
        model.addAttribute(PAGE_KEY, page.orElse(1L));
        return "news";
    }

    @RequestMapping(value = "/view/{newsId}", method = RequestMethod.GET)
    public String newsViewPage(Model model, @PathVariable(NEWS_ID_KEY) Long newsId,
                               @ModelAttribute(CRITERIA_KEY) SearchCriteria criteria) throws ServiceException {
        model.addAttribute("newsTO", newsManagementServiceService.getNewsTO(newsId));
        model.addAttribute("comment", new Comment());
        model.addAttribute(CRITERIA_KEY, criteria);
        model.addAttribute(PAGER_KEY, newsService.getPager(newsId, criteria));
        return "news_view";
    }

    @RequestMapping(value = "/edit/{newsId}", method = RequestMethod.GET)
    public String editNewsPage(Model model, @PathVariable(NEWS_ID_KEY) Long newsId) throws ServiceException {
        model.addAttribute(TAG_LIST_KEY, tagService.readAll());
        model.addAttribute(AUTHOR_LIST_KEY, authorService.getAllNoExpiredAuthors());
        if (!model.containsAttribute(NEWS_RECORD_KEY)) {
            model.addAttribute(NEWS_RECORD_KEY, newsManagementServiceService.getNewsRecord(newsId));
        }
        return "news_editor";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editNews(@Valid NewsRecord newsRecord, BindingResult bindingResult,
                           Model model) throws ServiceException {
        long newsId = newsRecord.getNews().getNewsId();
        if (bindingResult.hasErrors()) {
            String view = editNewsPage(model, newsId);
            return view;
        }
        Date current_date = new Date();
        newsRecord.getNews().setModificationDate(current_date);
        try {
            newsManagementServiceService.editNews(newsId, newsRecord);
        }catch (OptimisticLockException|HibernateOptimisticLockingFailureException e){
            model.addAttribute("OPTIMISTIC_LOCK", true);
            return editNewsPage(model,newsRecord.getNews().getNewsId());
        }
        return "redirect:/news/";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addNewsPage(Model model) throws ServiceException {
        model.addAttribute(TAG_LIST_KEY, tagService.readAll());
        model.addAttribute(AUTHOR_LIST_KEY, authorService.getAllNoExpiredAuthors());
        if (!model.containsAttribute(NEWS_RECORD_KEY)) {
            model.addAttribute(NEWS_RECORD_KEY, new NewsRecord());
        }
        return "add_news";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNews(@Valid NewsRecord newsRecord, BindingResult bindingResult,
                          Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            String view = addNewsPage(model);
            return view;
        }
        Date currentDate = new Date();
        newsRecord.getNews().setCreationDate(currentDate);
        newsRecord.getNews().setModificationDate(currentDate);
        newsService.addNews(newsRecord);
        return "redirect:/news/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteNews(@RequestParam(required = false) List<Long> newsIdList) throws ServiceException {
        if (newsIdList == null) {
            return "redirect:/news/";
        }
        for (Long newsId : newsIdList) {
            newsManagementServiceService.deleteNews(newsId);
        }
        return "redirect:/news/";
    }

}