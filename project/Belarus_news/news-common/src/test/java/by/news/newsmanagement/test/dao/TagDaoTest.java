package by.epam.newsmanagement.test.dao;

import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.ITagDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.News;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.domain.Tag;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:/dataset/newsDB.xml")
@DatabaseSetup("classpath:/dataset/tagDB.xml")
@DatabaseSetup("classpath:/dataset/authorDB.xml")
@DatabaseSetup("classpath:/dataset/newsTagDB.xml")
@DatabaseSetup("classpath:/dataset/newsAuthorDB.xml")
@DatabaseTearDown(value = "classpath:/dataset/newsAuthorDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/newsTagDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/authorDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/tagDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/newsDB.xml", type = DatabaseOperation.DELETE_ALL)

public class TagDaoTest {
	@Autowired
	private ITagDao tagDao;
	@Autowired
	private INewsDao newsDao;
	@BeforeClass
	public static void setLocale(){
		Locale.setDefault(Locale.ENGLISH);
	}
	@Ignore

	@Test
	public void createTest() throws DaoException {
		long countBefore = tagDao.read().size();
		Tag tag = new Tag();
		tag.setTagName("test");
		long tag_id = tagDao.create(tag);
		tag.setTagId(tag_id);
		long countAfter = tagDao.read().size();
		assertEquals(countBefore + 1, countAfter);
		List<Tag> tagList = tagDao.read();
		assertTrue(tagList.contains(tag));
	}
	@Ignore

	@Test
	public void readTest() throws DaoException {
		long tag_id = 1;
		Tag tag = tagDao.read(tag_id);
		List<Tag> tagList = tagDao.read();
		assertTrue(tagList.contains(tag));
	}
	@Ignore

	@Test
	public void updateTest() throws DaoException {
		long tag_id = 1;
		Tag updTag = tagDao.read(tag_id);
		updTag.setTagName("updated");
		tagDao.update(tag_id, updTag);
		List<Tag> tagList = tagDao.read();
		assertTrue(tagList.contains(updTag));
	}
	@Ignore

	@Test
	public void deleteTest() throws DaoException {
		long tag_id = 4;
		Tag tag = tagDao.read(tag_id);
		assertNotNull(tag);
		tagDao.delete(tag_id);
		List<Tag> tagList = tagDao.read();
		assertFalse(tagList.contains(tag));
	}
	@Ignore

	@Test
	public void readOnNewsIdTest() throws DaoException {
		long tag_id = 2;
		Tag tag = tagDao.read(tag_id);
		long news_id = 1;
		newsDao.connectNewsWithTag(news_id, tag_id);
		List<Tag> tagList = tagDao.readOnNewsId(news_id);
		assertTrue(tagList.contains(tag));
	}
	@Ignore

	@Test
	public void deleteNewsTagTest() throws DaoException{
		long tag_id = 1;
		SearchCriteria criteria = new SearchCriteria(Collections.EMPTY_LIST, Arrays.asList(tag_id));
		List<News> newsList = newsDao.readByCriteria(1L, 20, criteria);
		assertTrue(newsList.size() > 0);
		tagDao.deleteNewsTag(tag_id);
		newsList = newsDao.readByCriteria(1L, 20,
				new SearchCriteria(Collections.EMPTY_LIST, Arrays.asList(tag_id)));
		assertTrue(newsList.size() == 0);
	}

}