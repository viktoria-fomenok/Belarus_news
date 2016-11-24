package by.epam.newsmanagement.command;

import by.epam.newsmanagement.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>This interface describes the basic method of the processing requests.This
 * interface implements a pattern <strong>Command</strong></p>
 * @author Nikita Kobyzov
 */
public interface ICommand {
    /**
     * <p>This method process http request</p>
     *
     * @param request is http request sent from the client
     * @return the url of view page
     * @throws CommandException if the error occurred on the request process
     */
    String execute(HttpServletRequest request) throws CommandException;
}
