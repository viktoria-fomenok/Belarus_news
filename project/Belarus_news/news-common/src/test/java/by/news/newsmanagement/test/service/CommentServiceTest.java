package by.epam.newsmanagement.test.service;

import by.epam.newsmanagement.dao.ICommentDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.service.exception.ServiceException;
import by.epam.newsmanagement.service.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommentServiceTest {
	@Mock
	private ICommentDao commentDao;

	@InjectMocks
	private CommentServiceImpl commentService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	@Ignore

	@Test
	public void addCommentTest_ShouldAddComment() throws ServiceException, DaoException {
		Comment comment = new Comment(1L);
		commentService.addComment(comment);
		verify(commentDao).create(comment);
	}
	@Ignore

	@Test(expected = ServiceException.class)
	public void addCommentTest_ShouldThrowException() throws ServiceException, DaoException {
		Comment comment = new Comment(1L);
		when(commentDao.create(comment)).thenThrow(DaoException.class);
		commentService.addComment(comment);
		verify(commentDao).create(comment);
	}
	@Ignore

	@Test
	public void readByNewsIdTest_ShouldReturnCommentList() throws ServiceException, DaoException{
		List<Comment> commentList = Arrays.asList(new Comment(1L), new Comment(2L));
		long news_id = 1;
		when(commentDao.readOnNewsId(news_id)).thenReturn(commentList);
		Assert.assertEquals(commentList, commentService.readByNewsId(news_id));
		verify(commentDao).readOnNewsId(news_id);

	}
	@Ignore

	@Test
	public void deleteCommentTest_ShouldAddComment() throws ServiceException, DaoException {
		long comment_id = 1;
		commentService.deleteComment(comment_id);
		verify(commentDao).delete(comment_id);
	}
	@Ignore

	@Test(expected = ServiceException.class)
	public void deleteCommentTest_ShouldThrowException() throws ServiceException, DaoException {
		long comment_id = 1L;
		when(commentDao.delete(comment_id)).thenThrow(DaoException.class);
		commentService.deleteComment(comment_id);
		verify(commentDao).delete(comment_id);
	}
	@Ignore

	@Test
	public void deleteCommentByNewsId() throws ServiceException, DaoException{
		long news_id = 1;
		commentService.deleteCommentOnNewsId(news_id);
		verify(commentDao).deleteOnNewsId(news_id);
	}
}
