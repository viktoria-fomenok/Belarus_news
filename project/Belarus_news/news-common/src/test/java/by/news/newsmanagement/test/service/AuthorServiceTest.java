package by.epam.newsmanagement.test.service;

import by.epam.newsmanagement.dao.IAuthorDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.service.exception.ServiceException;
import by.epam.newsmanagement.service.impl.AuthorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorServiceTest {

    @Mock
    private IAuthorDao authorDao;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Ignore

    @Test
    public void makeExpiredTest_ShouldMakeExpired() throws ServiceException, DaoException {
        Author author = new Author();
        author.setAuthorId(1L);
        author.setExpired(new Date());
        authorService.makeExpired(author);
        verify(authorDao).makeExpired(author);
    }
    @Ignore

    @Test
    public void readAuthorByNewsId_ShouldReturnAuthor() throws ServiceException, DaoException {
        Author author = new Author(1L);
        long news_id = 1;
        when(authorDao.readOnNewsId(news_id)).thenReturn(author);
        Assert.assertEquals(author, authorService.readAuthorByNewsId(news_id));
        verify(authorDao).readOnNewsId(news_id);
    }
    @Ignore

    @Test
    public void addAuthorTest_ShouldCreateAuthor() throws DaoException, ServiceException {
        Author author = new Author(1L);
        authorService.addAuthor(author);
        verify(authorDao).create(author);
    }
    @Ignore

    @Test(expected = ServiceException.class)
    public void addAuthorTest_ShouldThrowException() throws DaoException, ServiceException {
        Author author = new Author(1L);
        when(authorDao.create(author)).thenThrow(DaoException.class);
        authorService.addAuthor(author);
        verify(authorDao).create(author);
    }
    @Ignore

    @Test
    public void getAllAuthorsTest_ShouldReturnAuthorList() throws DaoException, ServiceException {
        List<Author> authorList = Arrays.asList(new Author(1L), new Author(2L));
        when(authorDao.read()).thenReturn(authorList);
        Assert.assertEquals(authorList, authorService.getAllAuthors());
        verify(authorDao).read();
    }
    @Ignore

    @Test(expected = ServiceException.class)
    public void getAllAuthorsTest_ShouldThrowException() throws DaoException, ServiceException {
        when(authorDao.read()).thenThrow(DaoException.class);
        authorService.getAllAuthors();
        verify(authorDao).read();
    }
    @Ignore

    @Test
    public void editAuthorTest_ShouldEdit() throws DaoException, ServiceException {
        Author author = new Author(1L);
        long author_id = 1L;
        authorService.editAuthor(author_id, author);
        verify(authorDao).update(author_id, author);
    }
    @Ignore

    @Test(expected = ServiceException.class)
    public void editAuthorTest_ShouldThrowException() throws DaoException, ServiceException {
        Author author = new Author(1L);
        long author_id = 1L;
        when(authorDao.update(author_id, author)).thenThrow(DaoException.class);
        authorService.editAuthor(author_id, author);
        verify(authorDao).update(author_id, author);
    }

}
