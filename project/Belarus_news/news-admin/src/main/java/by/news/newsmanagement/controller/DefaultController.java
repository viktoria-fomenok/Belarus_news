package by.epam.newsmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class DefaultController {
    private static final String ERROR_KEY = "error";
    private static final String MESSAGE = "Wrong login or password";
    @RequestMapping(value = "/")
    public String homePage(){
        return "redirect:/news/";
    }

    @RequestMapping(value = "/login")
    public String login( @RequestParam(value = ERROR_KEY, required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute(ERROR_KEY, MESSAGE);
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "login";
    }

}
