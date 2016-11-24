package by.epam.newsmanagement.dao;

import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Author;

import java.util.List;

/**
 * <p>
 * This interface describes the basic methods for DAO that work with
 * Author-entity
 * </p>
 *
 * @author Nikita Kobyzov
 * @see IGenericDao
 * @see by.epam.newsmanagement.dao.oracle.AuthorDaoImpl
 */
public interface IAuthorDao extends IGenericDao<Long, Author> {

    /**
     * <p>This method reads the author on a news id</p>
     *
     * @param newsId is a unique identifier of the news
     * @return the author corresponding to the given news id or <tt>null</tt> if
     * there isn't author with the appropriate news id
     * @throws DaoException if the error occurred in working with the data source
     */
    Author readOnNewsId(long newsId) throws DaoException;

    /**
     * <p>
     * This method makes author expired. In field "expired" sets the current
     * date
     * </p>
     *
     * @param author is a entity that contains identifier of the author and expired date
     * @throws DaoException if the error occurred in working with the data source
     */
    void makeExpired(Author author) throws DaoException;

    /**
     * <p>
     * This method gets all authors who are not expired
     * </p>
     *
     * @return list of authors who are not expired
     * @throws DaoException if the error occurred in working with the data source
     */
    List<Author> getAllNonExpiredAuthors() throws DaoException;
}
