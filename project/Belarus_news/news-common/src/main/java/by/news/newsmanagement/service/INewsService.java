package by.epam.newsmanagement.service;

import java.util.List;

import by.epam.newsmanagement.domain.Pager;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.domain.News;
import by.epam.newsmanagement.domain.NewsRecord;
import by.epam.newsmanagement.service.exception.ServiceException;

/**
 * <p>
 * This interface is designed to solve problems of business logic for a
 * News-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public interface INewsService {

    /**
     * <p>
     * This method adds a new news
     * </p>
     *
     * @param newsRecord is the transfer object of news with author id and list of tag id
     *                   that to be recorded
     * @return <tt>true</tt> if the operation was successful
     * @throws ServiceException if an error has occurred with the write operations
     */
    boolean addNews(NewsRecord newsRecord) throws ServiceException;

    /**
     * <p>
     * This method counts the number of news pages
     * </p>
     *
     * @param criteria is a object that contains the search criteria
     * @return count of news pages
     * @throws ServiceException if an error has occurred with the search operations
     */
    Long countOfPages(SearchCriteria criteria) throws ServiceException;


    /**
     * <p>
     * This method reads the news with author and tags on news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return the news corresponding to the given news id
     * @throws ServiceException if an error has occurred with the read operations
     */
    News getNews(long newsId) throws ServiceException;

    /**
     * <p>
     * This method reads  news with authors and tags corresponding to the
     * given search criteria
     * </p>
     *
     * @param page is a number of page
     * @param searchCriteria is an object that contains the criteria for the search
     * @return the news corresponding to the given search criteria
     * @throws ServiceException if an error has occurred with the read operations
     */
    List<News> getNewsBySearchCriteria(Long page, SearchCriteria searchCriteria) throws ServiceException;

    /**
     * <p>
     * This method deletes news on news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @throws ServiceException if an error has occurred with the delete operations
     */
    void deleteNews(long newsId) throws ServiceException;

    /**
     * <p>
     * This method edits news with author and tags on news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @param news    is the  news that to be recorded
     * @throws ServiceException if an error has occurred with the update operations
     */
    void editNews(long newsId, News news) throws ServiceException;

    /**
     * <p>
     * This method sets a new relation between news and author
     * </p>
     *
     * @param newsId       is a unique identifier of the news
     * @param newAuthorId is a unique identifier of the author that to be recorded
     * @throws ServiceException if an error has occurred with the update operations
     */
    void editNewsAuthorConnection(Long newsId, Long newAuthorId) throws ServiceException;

    /**
     * <p>
     * This method returns an object that contains the next and previous news id
     * on search criteria
     * </p>
     *
     * @param newsId  is a unique identifier of the current news
     * @param criteria is a object that contains the search criteria
     * @return an object that contains the next and previous news id
     * @throws ServiceException if an error has occurred with the read operations
     */
    Pager getPager(long newsId, SearchCriteria criteria) throws ServiceException;

    /**
     * <p>
     * This method sets a new relation between news and tags
     * </p>
     *
     * @param newsId         is a unique identifier of the news
     * @param newTagIdList is a list of  unique identifier of the tags that to be recorded
     * @throws ServiceException if an error has occurred with the update operations
     */
    void editNewsTagsConnection(Long newsId, List<Long> newTagIdList) throws ServiceException;
}
