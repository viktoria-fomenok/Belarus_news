package by.epam.newsmanagement.service.impl;

import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.domain.*;
import by.epam.newsmanagement.domain.dto.NewsDTO;
import by.epam.newsmanagement.service.*;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("newsManagementService")
public class NewsManagementServiceImpl implements INewsManagementService {

    @Autowired
    private INewsService newsService;
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private ITagService tagService;
    @Autowired
    private ICommentService commentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNews(long newsId) throws ServiceException {
        commentService.deleteCommentOnNewsId(newsId);
        newsService.deleteNews(newsId);
    }

    @Override
    public NewsRecord getNewsRecord(long newsId) throws ServiceException {
        NewsRecord newsRecord = new NewsRecord();
        News news = newsService.getNews(newsId);
        newsRecord.setNews(news);
        Author author = authorService.readAuthorByNewsId(newsId);
        newsRecord.setAuthorId(author.getAuthorId());
        List<Tag> tagList = tagService.readByNewsId(newsId);
        newsRecord.setTagIdList(tagList.stream().map(Tag::getTagId).collect(Collectors.toList()));
        return newsRecord;
    }

    @Override
    public NewsDTO getNewsTO(long newsId) throws ServiceException {
        NewsDTO newsDTO = new NewsDTO();
        News news = newsService.getNews(newsId);
        newsDTO.setNews(news);
        Author author = authorService.readAuthorByNewsId(newsId);
        newsDTO.setAuthor(author);
        List<Tag> tagList = tagService.readByNewsId(newsId);
        newsDTO.setTagList(tagList);
        List<Comment> commentList = commentService.readByNewsId(newsId);
        newsDTO.setCommentList(commentList);
        return newsDTO;
    }

    @Override
    public List<NewsDTO> getNewsBySearchCriteria(Long page, SearchCriteria searchCriteria) throws ServiceException {
        List<News> newsList = newsService.getNewsBySearchCriteria(page, searchCriteria);
        List<NewsDTO> newsDTOList = new ArrayList<NewsDTO>(newsList.size());
        for (News news : newsList) {
            NewsDTO newsDTO = new NewsDTO();
            newsDTO.setNews(news);
            Author author = authorService.readAuthorByNewsId(news.getNewsId());
            newsDTO.setAuthor(author);
            List<Tag> tagList = tagService.readByNewsId(news.getNewsId());
            newsDTO.setTagList(tagList);
            List<Comment> commentList = commentService.readByNewsId(news.getNewsId());
            newsDTO.setCommentList(commentList);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editNews(long newsId, NewsRecord newsRecord) throws ServiceException {
        newsService.editNews(newsId, newsRecord.getNews());
        Author author = authorService.readAuthorByNewsId(newsId);
        if (!author.getAuthorId().equals(newsRecord.getAuthorId())) {
            newsService.editNewsAuthorConnection(newsId, newsRecord.getAuthorId());
        }
        if(newsRecord.getTagIdList() == null){
            newsRecord.setTagIdList(Collections.EMPTY_LIST);
        }
        newsService.editNewsTagsConnection(newsId,  newsRecord.getTagIdList());
    }
}
