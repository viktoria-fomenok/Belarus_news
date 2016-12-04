package by.epam.newsmanagement.test.dao;

import by.epam.newsmanagement.dao.IAuthorDao;
import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.ITagDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Author;
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

import javax.persistence.OptimisticLockException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/dataset/tagDB.xml")
@DatabaseSetup("classpath:/dataset/authorDB.xml")
@DatabaseSetup("classpath:/dataset/newsDB.xml")
@DatabaseSetup("classpath:/dataset/newsAuthorDB.xml")
@DatabaseSetup("classpath:/dataset/newsTagDB.xml")
@DatabaseSetup("classpath:/dataset/commentDB.xml")
@DatabaseTearDown(value = "classpath:/dataset/commentDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/newsAuthorDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/newsTagDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/tagDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/newsDB.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseTearDown(value = "classpath:/dataset/authorDB.xml", type = DatabaseOperation.DELETE_ALL)
public class NewsDaoTest {
    @Autowired
    private INewsDao newsDao;

    @Autowired
    private ITagDao tagDao;

    @Autowired
    private IAuthorDao authorDao;

    @BeforeClass
    public static void setLocale() {
        Locale.setDefault(Locale.ENGLISH);
    }
    @Ignore

    @Test
    public void createTest() throws DaoException {
        long countBefore = newsDao.read().size();
        News news = new News();
        news.setTitle("Test title");
        news.setShortText("Short test");
        news.setFullText("Full test");
        news.setCreationDate(new Date());
        news.setModificationDate(new Date());
        Long news_id = newsDao.create(news);
        long countAfter = newsDao.read().size();
        News db_news = newsDao.read(news_id);
        assertEquals(db_news.getTitle(), news.getTitle());
        assertEquals(db_news.getNewsId(), news_id);
        assertEquals(db_news.getShortText(), db_news.getShortText());
        assertEquals(db_news.getFullText(), news.getFullText());
        assertEquals(countBefore + 1, countAfter);
    }
    @Ignore

    @Test
    public void readTest() throws DaoException {
        long news_id = 1;
        News news = newsDao.read(news_id);
        List<News> newsList = newsDao.read();
        assertTrue(newsList.contains(news));
    }
    @Ignore

    @Test
    public void updateTest() throws DaoException {
        long news_id = 1;
        News news = newsDao.read(news_id);
        news.setTitle("Test title");
        news.setModificationDate(new Date());
        newsDao.update(news_id, news);
        News db_news = newsDao.read(news_id);
        assertEquals(db_news.getTitle(), news.getTitle());
        List<News> newsList = newsDao.read();
        assertTrue(newsList.contains(db_news));
    }
    @Ignore

    @Test
    public void deleteTest() throws DaoException {
        News news = new News();
        news.setTitle("Test title");
        news.setShortText("Short test");
        news.setFullText("Full test");
        news.setCreationDate(new Date());
        news.setModificationDate(new Date());
        long id = newsDao.create(news);
        news.setNewsId(id);
        newsDao.delete(id);
        List<News> newsList = newsDao.read();
        assertFalse(newsList.contains(news));
    }
    @Ignore

    @Test
    public void readOnAuthorCriteriaTest() throws DaoException {
        Long news_id = 1L;
        Author author = authorDao.readOnNewsId(news_id);
        List<Long> authorList = new ArrayList<Long>();
        authorList.add(author.getAuthorId());
        SearchCriteria sc = new SearchCriteria(authorList, Collections.EMPTY_LIST);
        List<News> newsList = newsDao.readByCriteria(1L, 100, sc);
        assertEquals(newsList.size(), 1);
        assertEquals(newsList.get(0).getNewsId(), news_id);
    }
    @Ignore

    @Test
    public void readOnTagCriteriaTest() throws DaoException {
        Long news_id = 1L;
        List<Long> tagList = tagDao.readOnNewsId(news_id).stream()
                .map(Tag::getTagId).collect(Collectors.toList());
        SearchCriteria sc = new SearchCriteria(Collections.EMPTY_LIST, tagList);
        List<News> newsList = newsDao.readByCriteria(1L, 10, sc);
        assertTrue(newsList.contains(newsDao.read(news_id)));
    }
    @Ignore

    @Test
    public void readOnEmptyCriteriaTest() throws DaoException{
        List<News> newsList = newsDao.read();
        SearchCriteria sc = new SearchCriteria(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        List<News> searchNewsList = newsDao.readByCriteria(1L, 100,sc);
        assertTrue(searchNewsList.containsAll(newsList));
    }
    @Ignore

    @Test
    public void optimisticLockTest() throws DaoException{
        long newsId = 1;
        News news = newsDao.read(newsId);
        news.setVersion(2);
        try{
            newsDao.update(newsId, news);
        }catch (Exception e){
            assertTrue(e instanceof OptimisticLockException);
        }
    }
    @Ignore

    @Test
    public void readOnCriteriaTest() throws DaoException {
        Long news_id = 1l;
        List<Long> tagList = tagDao.readOnNewsId(news_id).stream()
                .map(Tag::getTagId).collect(Collectors.toList());
        Author author = authorDao.readOnNewsId(news_id);
        List<Long> authorList = new ArrayList<Long>();
        authorList.add(author.getAuthorId());
        SearchCriteria sc = new SearchCriteria(authorList, tagList);
        List<News> newsList = newsDao.readByCriteria(1L, 10, sc);
        assertEquals(newsList.size(), 1);
        assertEquals(newsList.get(0).getNewsId(), news_id);
    }
    @Ignore

    @Test
    public void newsTagTest() throws DaoException {
        long news_id = 1;
        Tag tag = new Tag();
        tag.setTagName("test");
        long tag_id = tagDao.create(tag);
        tag.setTagId(tag_id);
        newsDao.connectNewsWithTag(news_id, tag_id);
        List<Tag> tagList = tagDao.readOnNewsId(news_id);
        assertTrue(tagList.contains(tag));
        newsDao.deleteNewsTag(news_id);
        tagList = tagDao.readOnNewsId(news_id);
        assertFalse(tagList.contains(tag));
    }
    @Ignore

    @Test
    public void newsAuthorTest() throws DaoException {
        long news_id = 6;
        Author author = new Author();
        author.setAuthorName("test");
        long author_id = authorDao.create(author);
        author.setAuthorId(author_id);
        newsDao.deleteNewsAuthor(news_id);
        newsDao.connectNewsWithAuthor(news_id, author_id);
        Author dbAuthor = authorDao.readOnNewsId(news_id);
        assertEquals(dbAuthor, author);
        newsDao.deleteNewsAuthor(news_id);
        dbAuthor = authorDao.readOnNewsId(news_id);
        assertNull(dbAuthor);
    }
    @Ignore

    @Test
    public void countOfNewsTest() throws DaoException{
        SearchCriteria criteria = new SearchCriteria(Arrays.asList(1L,2L,3L,4L), Collections.EMPTY_LIST);
        List<News> newsList = newsDao.readByCriteria(1L, 100, criteria);
        long newsCount = newsDao.countOfNews(criteria);
        assertTrue(newsCount == newsList.size());
    }

   
}