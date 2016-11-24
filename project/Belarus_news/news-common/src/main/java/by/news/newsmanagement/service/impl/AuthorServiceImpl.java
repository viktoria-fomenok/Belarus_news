package by.epam.newsmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.newsmanagement.dao.IAuthorDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.service.IAuthorService;
import by.epam.newsmanagement.service.exception.ServiceException;

/**
 * <p>
 * This class is designed to solve problems of business logic for a
 * Author-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Service("authorService")
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private IAuthorDao authorDao;

    @Override
    public void makeExpired(Author author) throws ServiceException {
        try {
            authorDao.makeExpired(author);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Author Service with author= " + author, e);
        }

    }

    @Override
    public Author readAuthorByNewsId(Long newsId) throws ServiceException {
        try {
            Author author = authorDao.readOnNewsId(newsId);
            return author;
        } catch (DaoException e) {
            throw new ServiceException("Exception in Author Service with newsId = " + newsId, e);
        }
    }

    public void addAuthor(Author author) throws ServiceException {
        try {
            authorDao.create(author);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Author Service with author = " + author, e);
        }
    }

    public List<Author> getAllAuthors() throws ServiceException {
        try {
            return authorDao.read();
        } catch (DaoException e) {
            throw new ServiceException("Exception in Author Service", e);
        }
    }

    @Override
    public List<Author> getAllNoExpiredAuthors() throws ServiceException {
        try{
            return authorDao.getAllNonExpiredAuthors();
        }catch (DaoException e){
            throw new ServiceException("Exception in Author Service", e);
        }
    }

    @Override
    public void editAuthor(long authorId, Author author) throws ServiceException {
        try {
            authorDao.update(authorId, author);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Author Service with authorId = "
                    + authorId + ", author = " + author, e);
        }

    }

}
