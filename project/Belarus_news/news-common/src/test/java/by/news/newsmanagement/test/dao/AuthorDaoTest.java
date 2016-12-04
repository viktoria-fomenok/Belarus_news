package by.epam.newsmanagement.test.dao;

import by.epam.newsmanagement.dao.IAuthorDao;
import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.domain.News;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/dataset/authorDB.xml")
@DatabaseSetup("/dataset/newsDB.xml")
@DatabaseSetup("/dataset/newsAuthorDB.xml")
@DatabaseTearDown(value = "/dataset/newsAuthorDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "/dataset/newsDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "/dataset/authorDB.xml", type = DatabaseOperation.DELETE_ALL)
public class AuthorDaoTest {
	@Autowired
	private IAuthorDao authorDao;
	@Autowired
	private INewsDao newsDao;

	@BeforeClass
	public static void setLocale(){
		Locale.setDefault(Locale.ENGLISH);
	}
	@Ignore

	@Test
	public void createTest() throws DaoException {
		long countBefore = authorDao.read().size();
		Author author = new Author();
		author.setAuthorName("Test");
		Long auth_id = authorDao.create(author);
		long countAfter = authorDao.read().size();
		Author db_author = authorDao.read(auth_id);
		assertEquals(author.getAuthorName(), db_author.getAuthorName());
		assertEquals(db_author.getAuthorId(), auth_id);
		assertEquals(countBefore + 1, countAfter);
	}
	@Ignore

	@Test
	public void readTest() throws DaoException {
		long author_id = 1;
		Author author = authorDao.read(author_id);
		List<Author> authorList = authorDao.read();
		assertTrue(authorList.contains(author));
	}
	@Ignore

	@Test
	public void updateTest() throws DaoException {
		long author_id = 1;
		Author updAuthor = authorDao.read(author_id);
		updAuthor.setAuthorName("updated");
		authorDao.update(author_id, updAuthor);
		List<Author> authorList = authorDao.read();
		assertTrue(authorList.contains(updAuthor));
	}
	@Ignore

	@Test
	public void deleteTest() throws DaoException {
		long author_id = 5;
		Author author = authorDao.read(author_id);
		authorDao.delete(author_id);
		List<Author> authorList = authorDao.read();
		assertFalse(authorList.contains(author));
	}
	@Ignore

	@Test
	public void readOnNewsIdTest() throws DaoException {
		Author author = new Author();
		author.setAuthorName("Test");
		long id = authorDao.create(author);
		author.setAuthorId(id);
		News news = new News();
		news.setTitle("Test title");
		news.setShortText("Short test");
		news.setFullText("Full test");
		news.setCreationDate(new Date());
		news.setModificationDate(new Date());
		long news_id = newsDao.create(news);
		newsDao.connectNewsWithAuthor(news_id, id);
		Author foundAuthor = authorDao.readOnNewsId(news_id);
		assertEquals(foundAuthor, author);
	}
	@Ignore

	@Test
	public void makeExpiredTest() throws DaoException {
		long author_id = 1;
		Author author = authorDao.read(author_id);
		assertNull(author.getExpired());
		author.setExpired(new Date());
		authorDao.makeExpired(author);
		author = authorDao.read(author_id);
		assertNotNull(author.getExpired());
	}

}