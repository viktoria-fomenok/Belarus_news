package by.epam.newsmanagement.test.service;

import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.News;
import by.epam.newsmanagement.domain.NewsRecord;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.service.exception.ServiceException;
import by.epam.newsmanagement.service.impl.NewsManagementServiceImpl;
import by.epam.newsmanagement.service.impl.NewsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsServiceTest {

	@Mock
	private INewsDao newsDao;


	@InjectMocks
	private NewsManagementServiceImpl newsManagementService;

	@InjectMocks
	private NewsServiceImpl newsService;



	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	@Ignore

	@Test
	public void getNewsBySearchCriteriaTest_ShouldReturnNewsList() throws ServiceException, DaoException{
		List<News> newsList = Arrays.asList(new News(1L) , new News(2L));
		SearchCriteria criteria = new SearchCriteria(Arrays.asList(4L, 5L), Arrays.asList(6L, 7L));
		long page = 3L;
		when(newsService.getNewsBySearchCriteria(page, criteria)).thenReturn(newsList);
		Assert.assertEquals(newsList, newsService.getNewsBySearchCriteria(page, criteria));
	}
	@Ignore

	@Test
	public void addNewsTest_ShouldCreateNewsWithEmptyTagList() throws ServiceException, DaoException {
		Long news_id = new Long(1);
		Long author_id = new Long(2);
		News news = new News();
		NewsRecord newsRecord = new NewsRecord();
		newsRecord.setTagIdList(Collections.EMPTY_LIST);
		newsRecord.setAuthorId(author_id);
		newsRecord.setNews(news);
		when(newsDao.create(news)).thenReturn(news_id);
		assertTrue(newsService.addNews(newsRecord));
		verify(newsDao).create(news);
		verify(newsDao).connectNewsWithAuthor(news_id, author_id);
	}
	@Ignore

	@Test
	public void deleteNewsTest_ShouldDeleteNews() throws DaoException, ServiceException {
		long news_id = 1L;
		newsService.deleteNews(news_id);
		verify(newsDao).deleteNewsAuthor(news_id);
		verify(newsDao).deleteNewsTag(news_id);
		verify(newsDao).delete(news_id);
	}
	@Ignore

	@Test(expected = ServiceException.class)
	public void deleteNewsTest_ShouldThrowException() throws DaoException, ServiceException {
		long news_id = 1L;
		when(newsDao.deleteNewsTag(news_id)).thenThrow(DaoException.class);
		newsService.deleteNews(news_id);
	}
	@Ignore

	@Test
	public void countOfPages_ShouldReturnCount() throws DaoException, ServiceException{
		SearchCriteria criteria = new SearchCriteria();
		when(newsDao.countOfNews(criteria)).thenReturn(15L);
		assertTrue(3 == newsService.countOfPages(criteria));
		when(newsDao.countOfNews(criteria)).thenReturn(27L);
		assertTrue(6 == newsService.countOfPages(criteria));
		when(newsDao.countOfNews(criteria)).thenReturn(5L);
		assertTrue(1 == newsService.countOfPages(criteria));

	}
	@Ignore

	@Test
	public void addNewsTest_ShouldCreateNewsWithFullTagList() throws ServiceException, DaoException {
		Long news_id = new Long(1);
		Long author_id = new Long(2);
		News news = new News(news_id);
		List<Long> tagIdList = Arrays.asList(3L ,4L,5L);
		NewsRecord newsTO = new NewsRecord();
		newsTO.setTagIdList(tagIdList);
		newsTO.setAuthorId(author_id);
		newsTO.setNews(news);
		when(newsDao.create(news)).thenReturn(news_id);
		assertTrue(newsService.addNews(newsTO));
		verify(newsDao).create(news);
		verify(newsDao).connectNewsWithAuthor(news_id, author_id);
		verify(newsDao).connectNewsWithTag(news_id, 3L);
		verify(newsDao).connectNewsWithTag(news_id, 4L);
		verify(newsDao).connectNewsWithTag(news_id, 5L);

	}
	@Ignore

	@Test(expected = ServiceException.class)
	public void addNewsTest_ShouldThrowException() throws ServiceException, DaoException {
		News news = new News();
		NewsRecord newsTO = new NewsRecord();
		newsTO.setNews(news);
		when(newsDao.create(news)).thenThrow(DaoException.class);
		newsService.addNews(newsTO);
	}

	@Ignore

	@Test
	public void editNewsTagsConnectionTest() throws ServiceException, DaoException{
		Long news_id = 1L;
		List<Long> new_tag_id_list = Arrays.asList(4L, 5L, 6L, 7L);
		newsService.editNewsTagsConnection(news_id, new_tag_id_list);
		verify(newsDao).deleteNewsTag(news_id);
		verify(newsDao).connectNewsWithTag(news_id, 6L);
		verify(newsDao).connectNewsWithTag(news_id, 7L);
	}

}
