package by.epam.newsmanagement.test.service;

import by.epam.newsmanagement.domain.*;
import by.epam.newsmanagement.domain.dto.NewsDTO;
import by.epam.newsmanagement.service.IAuthorService;
import by.epam.newsmanagement.service.ICommentService;
import by.epam.newsmanagement.service.INewsService;
import by.epam.newsmanagement.service.ITagService;
import by.epam.newsmanagement.service.exception.ServiceException;
import by.epam.newsmanagement.service.impl.NewsManagementServiceImpl;
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

public class NewsManagementServiceTest {
    @Mock
    private INewsService newsService;
    @Mock
    private IAuthorService authorService;
    @Mock
    private ITagService tagService;
    @Mock
    private ICommentService commentService;
    @InjectMocks
    private NewsManagementServiceImpl newsManagementService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Ignore

    @Test
    public void deleteNewsTest_ShouldDeleteNews() throws ServiceException {
        long news_id = 1;
        newsManagementService.deleteNews(news_id);
        verify(newsService).deleteNews(news_id);
        verify(commentService).deleteCommentOnNewsId(news_id);
    }
    @Ignore

    @Test
    public void getNewsDTOTest_ShouldReturnNewsDTOList() throws ServiceException {
        long news_id = 1;
        News news = new News(1L);
        Author author = new Author(1L);
        List<Tag> tagList = Arrays.asList(new Tag(1L), new Tag(2L));
        List<Comment> commentList = Arrays.asList(new Comment(1L), new Comment(2L));
        NewsDTO newsDTO = new NewsDTO(author, news, tagList, commentList);
        when(newsService.getNews(news_id)).thenReturn(news);
        when(authorService.readAuthorByNewsId(news_id)).thenReturn(author);
        when(commentService.readByNewsId(news_id)).thenReturn(commentList);
        when(tagService.readByNewsId(news_id)).thenReturn(tagList);
        Assert.assertEquals(newsDTO, newsManagementService.getNewsTO(news_id));
        verify(newsService).getNews(news_id);
        verify(authorService).readAuthorByNewsId(news_id);
        verify(commentService).readByNewsId(news_id);
        verify(tagService).readByNewsId(news_id);
    }

    @Ignore

    @Test
    public void getNewsBySearchCriteriaTest_ShouldReturnNewsDTOList() throws ServiceException {
        long page = 1;
        Long[] newsIDs = {5L, 6L};
        List<Long> tagIdList = Arrays.asList(1L, 2L);
        List<Long> authorIdList = Arrays.asList(3L, 4L);
        SearchCriteria criteria = new SearchCriteria(authorIdList, tagIdList);
        List<News> newsList = Arrays.asList(new News(newsIDs[0]), new News(newsIDs[1]));
        Author author1 = new Author(7L);
        Author author2 = new Author(8L);
        List<Tag> tagList1 = Arrays.asList(new Tag(9L), new Tag(10L));
        List<Tag> tagList2 = Arrays.asList(new Tag(11L), new Tag(12L));
        List<Comment> commentList1 = Arrays.asList(new Comment(13L), new Comment(14L));
        List<Comment> commentList2 = Arrays.asList(new Comment(15L), new Comment(16L));
        when(newsService.getNewsBySearchCriteria(page, criteria)).thenReturn(newsList);
        when(authorService.readAuthorByNewsId(newsIDs[0])).thenReturn(author1);
        when(authorService.readAuthorByNewsId(newsIDs[1])).thenReturn(author2);
        when(tagService.readByNewsId(newsIDs[0])).thenReturn(tagList1);
        when(tagService.readByNewsId(newsIDs[1])).thenReturn(tagList2);
        when(commentService.readByNewsId(newsIDs[0])).thenReturn(commentList1);
        when(commentService.readByNewsId(newsIDs[1])).thenReturn(commentList2);
        List<NewsDTO> newsDTOList = Arrays.asList(new NewsDTO(author1, newsList.get(0), tagList1, commentList1),
                new NewsDTO(author2, newsList.get(1), tagList2, commentList2));
        Assert.assertEquals(newsDTOList, newsManagementService.getNewsBySearchCriteria(page, criteria));
        verify(newsService).getNewsBySearchCriteria(page, criteria);
        verify(authorService).readAuthorByNewsId(newsIDs[0]);
        verify(authorService).readAuthorByNewsId(newsIDs[1]);
        verify(tagService).readByNewsId(newsIDs[0]);
        verify(tagService).readByNewsId(newsIDs[1]);
        verify(commentService).readByNewsId(newsIDs[0]);
        verify(commentService).readByNewsId(newsIDs[1]);
    }
    @Ignore

    @Test
    public void editNewsTest_AuthorsNotEquals() throws ServiceException{
        long news_id = 1L;
        News news = new News(news_id);
        List<Long> tag_id_list = Arrays.asList(2L, 3L, 6L);
        Long author_id = 4L;
        Author author = new Author(5L);
        List<Tag> tagList = Arrays.asList(new Tag(6L), new Tag(7L));
        NewsRecord newsRecord = new NewsRecord();
        newsRecord.setTagIdList(tag_id_list);
        newsRecord.setAuthorId(author_id);
        newsRecord.setNews(news);
        when(authorService.readAuthorByNewsId(news_id)).thenReturn(author);
        when(tagService.readByNewsId(news_id)).thenReturn(tagList);
        newsManagementService.editNews(news_id, newsRecord);
        verify(newsService).editNews(news_id, news);
        verify(newsService).editNewsAuthorConnection(news_id, author_id);
        verify(newsService).editNewsTagsConnection(news_id, tag_id_list);
    }

}
