package by.epam.newsmanagement.dao;

import java.util.List;

import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Comment;

/**
 * <p>
 * This interface describes the basic methods for DAO that work with
 * Comment-entity
 * </p>
 *
 * @author Nikita Kobyzov
 * @see IGenericDao
 * @see by.epam.newsmanagement.dao.oracle.CommentDaoImpl
 */
public interface ICommentDao extends IGenericDao<Long, Comment> {

    /**
     * <p>
     * This method reads the comments on a news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return a list of all comments given news id
     * @throws DaoException if the error occurred in working with the data source
     */
    List<Comment> readOnNewsId(long newsId) throws DaoException;

    /**
     * <p>
     * This method delete comments in the data source on a news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return <tt>true</tt> if deleting was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean deleteOnNewsId(long newsId) throws DaoException;
}
