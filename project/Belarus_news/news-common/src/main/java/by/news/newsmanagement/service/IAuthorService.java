package by.epam.newsmanagement.service;

import java.util.List;

import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.service.exception.ServiceException;

/**
 * <p>
 * This interface is designed to solve problems of business logic for a
 * Author-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public interface IAuthorService {

    /**
     * <p>
     * This method writes a new author
     * </p>
     *
     * @param author is the author that to be recorded
     * @throws ServiceException if an error has occurred with the write operations
     */
    void addAuthor(Author author) throws ServiceException;

    /**
     * <p>
     * This method makes author expired.
     * </p>
     *
     * @param author is a entity that contains identifier of the author and expired date
     * @throws ServiceException if an error has occurred with the update operations
     */
    void makeExpired(Author author) throws ServiceException;

    /**
     * <p>
     * This method reads all authors
     * </p>
     *
     * @return a list of all found authors
     * @throws ServiceException if an error has occurred with the search operations
     */
    List<Author> getAllAuthors() throws ServiceException;

    /**
     * <p>
     * This method updates an author on a given author id
     * </p>
     *
     * @param authorId is a unique identifier of the author that to be changed
     * @param author    is a author that to be recorded
     * @throws ServiceException if an error has occurred with the update operations
     */
    void editAuthor(long authorId, Author author) throws ServiceException;

    /**
     * <p>
     * This method reads all authors who are not expired
     * </p>
     *
     * @return list of authors who are not expired
     * @throws ServiceException if an error has occurred with the read operations
     */
    List<Author> getAllNoExpiredAuthors() throws ServiceException;

    /**
     * <p>
     * This method reads all authors by news id
     * </p>
     * @param newsId is a unique identifier of the news
     * @return the author corresponding to the given news id or <tt>null</tt> if
     * there isn't author with the appropriate news id
     * @throws ServiceException is an error has occurred with the read operations
     */
    Author readAuthorByNewsId(Long newsId) throws ServiceException;
}
