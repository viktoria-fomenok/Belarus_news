package by.epam.newsmanagement.command.parser.impl;

import by.epam.newsmanagement.command.parser.RequestParser;
import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.exception.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CommentParser implements RequestParser<Comment>{

    private static final String COMMENT_TEXT_KEY = "commentText";
    @Autowired
    NewsIdParser newsIdParser;

    @Override
    public Comment parse(HttpServletRequest request) throws ParserException {
        String commentText = request.getParameter(COMMENT_TEXT_KEY);
        long news_id = newsIdParser.parse(request);
        if(commentText == null){
            throw new ParserException("Parameter " + COMMENT_TEXT_KEY + "is not found");
        }
        Comment comment = new Comment();
        comment.setNewsId(news_id);
        comment.setCommentText(commentText);
        return comment;
    }
}
