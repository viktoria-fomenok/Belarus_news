package by.epam.newsmanagement.controller;
import by.epam.newsmanagement.domain.Tag;
import by.epam.newsmanagement.service.ITagService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/tag")
@Controller
public class TagController {

    private static final Logger LOG = LogManager.getLogger(TagController.class);
    private static final String TAG_LIST_KEY = "tagList";
    private static final String TAG_KEY = "tag";
    @Autowired
    private ITagService tagService;

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        LOG.error(e);
        return "error";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTagPage(Model model) throws ServiceException{
            if(!model.containsAttribute(TAG_LIST_KEY)){
                List<Tag> authorList = tagService.readAll();
                model.addAttribute(TAG_LIST_KEY, authorList);
            }
            if (!model.containsAttribute(TAG_KEY)) {
                model.addAttribute(TAG_KEY, new Tag());
            }
        return "tag_editor";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editAuthor(@Valid Tag tag, BindingResult bindingResult,
                             Model model)  throws ServiceException{
            if (bindingResult.hasErrors()) {
                String view = editTagPage(model);
                return view;
            }
            tagService.updateTag(tag.getTagId(), tag);
        return "redirect:/tag/edit";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAuthor(@Valid Tag tag, BindingResult bindingResult,
                            Model model)  throws ServiceException{
            if (bindingResult.hasErrors()) {
                String view = editTagPage(model);
                return view;
            }
            tagService.addTag(tag);
        return "redirect:/tag/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteTag(@RequestParam long tagId)  throws ServiceException{
            tagService.deleteTag(tagId);
        return "redirect:/tag/edit";
    }
}
