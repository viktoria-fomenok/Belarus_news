package by.epam.newsmanagement.service.impl;

import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.News;
import by.epam.newsmanagement.domain.NewsRecord;
import by.epam.newsmanagement.domain.Pager;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.service.INewsService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * This class is designed to solve problems of business logic for a News-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Service("newsService")
public class NewsServiceImpl implements INewsService {

    private static final int NEWS_PER_PAGE = 5;
    @Autowired
    private INewsDao newsDao;

    @Override
    public List<News> getNewsBySearchCriteria(Long page, SearchCriteria searchCriteria) throws ServiceException {
        try {
            List<News> newsList = newsDao.readByCriteria(page, NEWS_PER_PAGE, searchCriteria);
            return newsList;
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service with page = "
                    + page + ", searchCriteria = " + searchCriteria, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addNews(NewsRecord newsRecord) throws ServiceException {
        try {
            long newsId = newsDao.create(newsRecord.getNews());
            newsDao.connectNewsWithAuthor(newsId, newsRecord.getAuthorId());
            if(newsRecord.getTagIdList() == null){
                newsRecord.setTagIdList(Collections.EMPTY_LIST);
            }
            for (Long tagId : newsRecord.getTagIdList()) {
                newsDao.connectNewsWithTag(newsId, tagId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service with newsRecord = " + newsRecord, e);
        }
        return true;
    }

    @Override
    public void editNews(long newsId, News news) throws ServiceException {
        try {
            newsDao.update(newsId, news);
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service with newsId = "
                    + newsId + ", news = " + news, e);
        }
    }

    @Override
    public News getNews(long newsId) throws ServiceException {
        try {
            News news = newsDao.read(newsId);
            return news;
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service with newsId = " + newsId, e);
        }
    }

    @Override
    public Long countOfPages(SearchCriteria criteria) throws ServiceException {
        try {
            long countOfNews = newsDao.countOfNews(criteria);
            long countOfPages = (countOfNews % NEWS_PER_PAGE == 0)
                    ? countOfNews / NEWS_PER_PAGE : countOfNews / NEWS_PER_PAGE + 1;
            return countOfPages;
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service", e);
        }
    }

    @Override
    public Pager getPager(long newsId, SearchCriteria criteria) throws ServiceException {
        try{
            return newsDao.getPager(newsId, criteria);
        }catch (DaoException e){
            throw new ServiceException("Exception in News Service with newsId = " + newsId
                    + ", criteria = " + criteria, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editNewsAuthorConnection(Long newsId, Long newAuthorId) throws ServiceException {
        try {
            newsDao.deleteNewsAuthor(newsId);
            newsDao.connectNewsWithAuthor(newsId, newAuthorId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service newsId = "
                    + newsId + ", newAuthorId = " + newAuthorId, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editNewsTagsConnection(Long newsId, List<Long> newTagIdList)
            throws ServiceException {

        try {
            newsDao.deleteNewsTag(newsId);
            for (Long tagId : newTagIdList) {
                newsDao.connectNewsWithTag(newsId, tagId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service with newsId = "
                    + newsId + ", newTagIdList = " + newTagIdList, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNews(long newsId) throws ServiceException {
        try {
            newsDao.deleteNewsTag(newsId);
            newsDao.deleteNewsAuthor(newsId);
            newsDao.delete(newsId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in News Service with newsId = " + newsId, e);
        }
    }

}
