package by.epam.newsmanagement.controller;

import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.service.IAuthorService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/author")
public class AuthorController {

    private static final Logger LOG = LogManager.getLogger(AuthorController.class);
    private static final String AUTHOR_LIST_KEY = "authorList";
    private static final String AUTHOR_KEY = "author";
    @Autowired
    private IAuthorService authorService;

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        LOG.error(e);
        return "error";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editAuthorPage(Model model) throws ServiceException {
        if (!model.containsAttribute(AUTHOR_LIST_KEY)) {
            List<Author> authorList = authorService.getAllAuthors();
            model.addAttribute(AUTHOR_LIST_KEY, authorList);
        }
        if (!model.containsAttribute(AUTHOR_KEY)) {
            model.addAttribute(AUTHOR_KEY, new Author());
        }
        return "author_editor";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editAuthor(@Valid Author author, BindingResult bindingResult,
                             Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            String view = editAuthorPage(model);
            return view;
        }
        authorService.editAuthor(author.getAuthorId(), author);
        return "redirect:/author/edit";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAuthor(@Valid Author author, BindingResult bindingResult,
                            Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            String view = editAuthorPage(model);
            return view;
        }
        authorService.addAuthor(author);
        return "redirect:/author/edit";
    }

    @RequestMapping(value = "/expire", method = RequestMethod.POST)
    public String expireAuthor(@RequestParam long authorId) throws ServiceException {
        Date currentDate = new Date();
        Author author = new Author();
        author.setExpired(currentDate);
        author.setAuthorId(authorId);
        authorService.makeExpired(author);
        return "redirect:/author/edit";
    }
}
