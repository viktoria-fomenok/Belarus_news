package by.epam.newsmanagement.service;

import java.util.List;

import by.epam.newsmanagement.domain.Tag;
import by.epam.newsmanagement.service.exception.ServiceException;

/**
 * <p>
 * This interface is designed to solve problems of business logic for a
 * Tag-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public interface ITagService {

    /**
     * <p>
     * This method writes a new tag
     * </p>
     *
     * @param tag is the tag that to be recorded
     * @return <tt>true</tt> if the operation was successful
     * @throws ServiceException if an error has occurred with the write operations
     */
    boolean addTag(Tag tag) throws ServiceException;

    /**
     * <p>
     * This method delete tag on tag id
     * </p>
     *
     * @param tagId is a unique identifier of the tag
     * @throws ServiceException if an error has occurred with the delete operations
     */
    void deleteTag(long tagId) throws ServiceException;

    /**
     * <p>
     * This method edits tag on tag id
     * </p>
     *
     * @param tagId is a unique identifier of the tag
     * @param tag    is the tag that to be recorded
     * @throws ServiceException if an error has occurred with the update operations
     */
    void updateTag(long tagId, Tag tag) throws ServiceException;

    /**
     * <p>
     * This method reads all tags
     * </p>
     *
     * @return list of all tags
     * @throws ServiceException if an error has occurred with the read operations
     */
    List<Tag> readAll() throws ServiceException;

    /**
     * <p>
     * This method reads the comments on a news id
     * </p>
     * @param newsId is a unique identifier of the news
     * @return a list of all comments given news id
     * @throws ServiceException if an error has occurred with the read operations
     */
    List<Tag> readByNewsId(Long newsId) throws ServiceException;
}
