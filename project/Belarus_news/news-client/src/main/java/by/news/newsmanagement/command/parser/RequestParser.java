package by.epam.newsmanagement.command.parser;

import by.epam.newsmanagement.exception.ParserException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by NikitOS on 19.04.2016.
 */
public interface RequestParser<T> {

    T parse(HttpServletRequest request) throws ParserException;
}
