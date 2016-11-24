package by.epam.newsmanagement.service;

import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.domain.NewsRecord;
import by.epam.newsmanagement.domain.dto.NewsDTO;
import by.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

/**
 * Created by NikitOS on 06.03.2016.
 */
public interface INewsManagementService {
    /**
     * <p>
     * This method deletes news on news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @throws ServiceException if an error has occurred with the delete operation
     */
    void deleteNews(long newsId) throws ServiceException;

    /**
     * <p>This method reads a News Record by news id</p>
     *
     * @param newsId is a unique identifier of the news
     * @return a entity that contains news, author id and list of tag id
     * @throws ServiceException if an error has occurred with the read operation
     */
    NewsRecord getNewsRecord(long newsId) throws ServiceException;

    /**
     * <p>
     * This method reads the news with author and tags on news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return the transfer object of news with author and tags
     * @throws ServiceException if an error has occurred with the read operations
     */
    NewsDTO getNewsTO(long newsId) throws ServiceException;

    /**
     * <p>
     * This method reads all news with authors and tags corresponding to the
     * given search criteria
     * </p>
     *
     * @param page is a number of page
     * @param searchCriteria is an object that contains the criteria for the search
     * @return
     * @throws ServiceException if an error has occurred with the read operations
     */
    List<NewsDTO> getNewsBySearchCriteria(Long page, SearchCriteria searchCriteria) throws ServiceException;

    /**
     * <p>
     * This method edits  news with authors and tags corresponding to the
     * given news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @param newsRecord is a transfer object that consists of news, author id and tag id list
     * @throws ServiceException if an error has occurred with the update operations
     */
    void editNews(long newsId, NewsRecord newsRecord) throws ServiceException;
}
