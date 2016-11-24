package by.epam.newsmanagement.dao;

import java.util.List;

import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Tag;

/**
 * <p>
 * This interface describes the basic methods for DAO that work with Tag-entity
 * </p>
 *
 * @author Nikita Kobyzov
 * @see IGenericDao
 * @see by.epam.newsmanagement.dao.oracle.TagDaoImpl
 */
public interface ITagDao extends IGenericDao<Long, Tag> {

    /**
     * <p>
     * This method reads the tags on a news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return a list of all tags given news id
     * @throws DaoException if the error occurred in working with the data source
     */
    List<Tag> readOnNewsId(long newsId) throws DaoException;

    /**
     * <p>This method deletes all connections with news and tag on a tag id</p>
     * @param tagId
     * @return <tt>true</tt> if deleting was successful
     * @throws DaoException
     */
    boolean deleteNewsTag(long tagId) throws DaoException;
}
