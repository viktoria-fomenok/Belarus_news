package by.epam.newsmanagement.controller;

import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.service.ICommentService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    private static final Logger LOG = LogManager.getLogger(CommentController.class);
    private static final String NEWS_ID_KEY = "newsId";
    private static final String COMMENT_ID_KEY = "commentId";
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        LOG.error(e);
        return "error";
    }

    @RequestMapping(value = "add/{newsId}", method = RequestMethod.POST)
    public String addComment(@Valid Comment comment, BindingResult bindingResult,
                             @PathVariable(NEWS_ID_KEY) Long newsId) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "redirect:/news/view/" + newsId;
        }
        Date currentDate = new Date();
        comment.setCreationDate(currentDate);
        commentService.addComment(comment);
        return "redirect:/news/view/" + newsId;
    }

    @RequestMapping(value = "/delete/{newsId}/{commentId}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable(NEWS_ID_KEY) Long newsId,
                                @PathVariable(COMMENT_ID_KEY) Long commentId) throws ServiceException {
        commentService.deleteComment(commentId);
        return "redirect:/news/view/" + newsId;
    }

}
