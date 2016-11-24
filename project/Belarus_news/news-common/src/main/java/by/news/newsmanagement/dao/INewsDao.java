package by.epam.newsmanagement.dao;

import java.util.List;

import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Pager;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.domain.News;

/**
 * <p>
 * This interface describes the basic methods for DAO that work with News-entity
 * </p>
 *
 * @author Nikita Kobyzov
 * @see IGenericDao
 * @see by.epam.newsmanagement.dao.oracle.NewsDaoImpl
 */
public interface INewsDao extends IGenericDao<Long, News> {

    /**
     * <p>
     * This method reads the news on a search criteria
     * </p>
     *
     * @param page        is the number of the page
     * @param newsPerPage is a count of news, that to be show on each page
     * @param criteria    is a object that contains the search criteria
     * @return a list of all news given search criteria
     * @throws DaoException if the error occurred in working with the data source
     */
    List<News> readByCriteria(Long page, Integer newsPerPage, SearchCriteria criteria) throws DaoException;

    /**
     * <p>
     * This method counts the number of news
     * </p>
     *
     * @param criteria is is a object that contains the search criteria
     * @return count of new news
     * @throws DaoException if the error occurred in working with the data source
     */
    Long countOfNews(SearchCriteria criteria) throws DaoException;

    /**
     * <p>
     * This method connect a news with a tag
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @param tagId  is a unique identifier of the tag
     * @return <tt>true</tt> if the connecting was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean connectNewsWithTag(long newsId, long tagId) throws DaoException;

    /**
     * <p>
     * This method connect a news with a author
     * </p>
     *
     * @param newsId   is a unique identifier of the news
     * @param authorId is a unique identifier of the author
     * @return <tt>true</tt> if the connecting was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean connectNewsWithAuthor(long newsId, long authorId) throws DaoException;

    /**
     * <p>
     * This method delete connections with news and tag on a news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return <tt>true</tt> if deleting was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean deleteNewsTag(long newsId) throws DaoException;

    /**
     * <p>
     * This method delete connection with news and author on a news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return <tt>true</tt> if deleting was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean deleteNewsAuthor(long newsId) throws DaoException;

    /**
     * <p>
     * This method returns an object that contains the next and previous news id
     * on search criteria
     * </p>
     *
     * @param newsId is a unique identifier of the current news
     * @param criteria is a object that contains the search criteria
     * @return an object that contains the next and previous news id
     * @throws DaoException if the error occurred in working with the data source
     */
    Pager getPager(long newsId, SearchCriteria criteria) throws DaoException;
}
