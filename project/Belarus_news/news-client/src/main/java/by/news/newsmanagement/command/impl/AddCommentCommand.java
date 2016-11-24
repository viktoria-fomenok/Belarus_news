package by.epam.newsmanagement.command.impl;

import by.epam.newsmanagement.command.ICommand;
import by.epam.newsmanagement.command.parser.RequestParser;
import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.exception.CommandException;
import by.epam.newsmanagement.service.ICommentService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component("comment-add")
public class AddCommentCommand implements ICommand {

    private static final String VIEW_PAGE = "client?command=news-view&newsId=";
    private static final String ERROR_KEY = "&error";
    @Autowired
    private ICommentService commentService;
    @Autowired
    private RequestParser<Comment> commentRequestParser;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Comment comment = commentRequestParser.parse(request);
        String commentText = comment.getCommentText();
        long news_id = comment.getNewsId();
        if(commentText.length()>30||commentText.isEmpty()){
            return VIEW_PAGE + news_id + ERROR_KEY;
        }
        Date currentDate = new Date();
        comment.setCreationDate(currentDate);
        try {
            commentService.addComment(comment);
        }catch (ServiceException e) {
            throw new CommandException("Exception in Add Comment Command",e);
        }
        return VIEW_PAGE + news_id;
    }
}
