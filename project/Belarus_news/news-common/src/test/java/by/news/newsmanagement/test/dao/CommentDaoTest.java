package by.epam.newsmanagement.test.dao;

import by.epam.newsmanagement.dao.ICommentDao;
import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Comment;
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
@ContextConfiguration(locations = "classpath:/testContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:/dataset/newsDB.xml")
@DatabaseSetup("classpath:/dataset/commentDB.xml")
@DatabaseTearDown(value = "classpath:/dataset/commentDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/newsDB.xml", type = DatabaseOperation.DELETE_ALL)
public class CommentDaoTest {
	@Autowired
	private ICommentDao commentDao;
	@Autowired
	private INewsDao newsDao;
	@BeforeClass
	public static void setLocale(){
		Locale.setDefault(Locale.ENGLISH);
	}
	@Ignore
	@Test
	public void createTest() throws DaoException {
		long countBefore = commentDao.read().size();
		Comment comment = new Comment();
		comment.setCommentText("test");
		comment.setCreationDate(new Date());
		comment.setNewsId(1L);
		Long com_id = commentDao.create(comment);
		long countAfter = commentDao.read().size();
		Comment db_comment = commentDao.read(com_id);
		assertEquals(db_comment.getCommentText(), comment.getCommentText());
		assertEquals(db_comment.getNewsId(), comment.getNewsId());
		assertEquals(db_comment.getCommentId(), com_id);
		assertEquals(countBefore + 1, countAfter);
	}
	@Ignore

	@Test
	public void readTest() throws DaoException {
		long comment_id = 1;
		Comment comment = commentDao.read(comment_id);
		List<Comment> commentList = commentDao.read();
		assertTrue(commentList.contains(comment));
	}
	@Ignore

	@Test
	public void updateTest() throws DaoException {
		long comment_id = 1;
		Comment updComment = commentDao.read(comment_id);
		updComment.setCommentText("updated");
		commentDao.update(comment_id, updComment);
		List<Comment> commentList = commentDao.read();
		assertTrue(commentList.contains(updComment));
	}
	@Ignore

	@Test
	public void deleteTest() throws DaoException {
		long comment_id = 1;
		Comment comment = commentDao.read(comment_id);
		commentDao.delete(comment_id);
		List<Comment> commentList = commentDao.read();
		assertFalse(commentList.contains(comment));
	}
	@Ignore

	@Test
	public void readOnNewsIdTest() throws DaoException {
		Comment comment = new Comment();
		comment.setCommentText("test");
		comment.setNewsId(1L);
		comment.setCreationDate(new Date());
		long id = commentDao.create(comment);
		comment = commentDao.read(id);
		List<Comment> commentList = commentDao.readOnNewsId(1);
		assertTrue(commentList.contains(comment));
	}
	@Ignore

	@Test
	public void deleteOnNewsIdTest() throws DaoException {
		Comment comment = new Comment();
		comment.setCommentText("test");
		comment.setCreationDate(new Date());
		comment.setNewsId(1L);
		long id = commentDao.create(comment);
		comment.setCommentId(id);
		commentDao.deleteOnNewsId(1);
		List<Comment> commentList = commentDao.readOnNewsId(1);
		assertTrue(commentList.isEmpty());
	}

}