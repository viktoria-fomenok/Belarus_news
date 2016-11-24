package by.epam.newsmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.newsmanagement.dao.ICommentDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.service.ICommentService;
import by.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

/**
 * <p>
 * This comment is designed to solve problems of business logic for a
 * Comment-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentDao commentDao;

    public void addComment(Comment comment) throws ServiceException {
        try {
            commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Comment Service with comment = " + comment, e);
        }
    }

    @Override
    public List<Comment> readByNewsId(Long newsId) throws ServiceException {
        try {
            List<Comment> commentList = commentDao.readOnNewsId(newsId);
            return commentList;
        } catch (DaoException e) {
            throw new ServiceException("Exception in Comment Service with newsId = " + newsId, e);
        }
    }

    public void deleteComment(long commentId) throws ServiceException {
        try {
            commentDao.delete(commentId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Comment Service with commentId = " + commentId, e);
        }
    }

    @Override
    public void deleteCommentOnNewsId(Long newsId) throws ServiceException {
        try {
            commentDao.deleteOnNewsId(newsId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Comment Service with newsId = " + newsId, e);
        }
    }
}
