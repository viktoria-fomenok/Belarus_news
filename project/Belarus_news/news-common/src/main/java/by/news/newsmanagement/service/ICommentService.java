package by.epam.newsmanagement.service;

import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

/**
 * <p>
 * This interface is designed to solve problems of business logic for a
 * Comment-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public interface ICommentService {

    /**
     * <p>
     * This method writes a new comment
     * </p>
     *
     * @param comment is the comment that to be recorded
     * @throws ServiceException if an error has occurred with the write operations
     */
    void addComment(Comment comment) throws ServiceException;

    /**
     * <p>
     * This method deletes the comment on comment id
     * </p>
     *
     * @param commentId is a unique identifier of the comment that to be deleted
     * @throws ServiceException if an error has occurred with the delete operations
     */
    void deleteComment(long commentId) throws ServiceException;

    /**
     * <p>
     * This method deletes all comments corresponding to the given news id
     * </p>
     *
     * @param newsId is a unique identifier of the news, which comments to be deleted
     * @throws ServiceException if an error has occurred with the delete operations
     */
    void deleteCommentOnNewsId(Long newsId) throws ServiceException;

    /**
     * <p>
     * This method reads the comments on a news id
     * </p>
     *
     * @param newsId is a unique identifier of the news
     * @return a list of all comments given news id
     * @throws ServiceException if an error has occurred with the read operations
     */
    List<Comment> readByNewsId(Long newsId) throws ServiceException;
}
