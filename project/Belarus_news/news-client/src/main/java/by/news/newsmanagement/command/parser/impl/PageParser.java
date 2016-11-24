package by.epam.newsmanagement.command.parser.impl;

import by.epam.newsmanagement.command.parser.RequestParser;
import by.epam.newsmanagement.exception.ParserException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PageParser implements RequestParser<Long> {
    private static final String PAGE_KEY = "page";
    private static final Long DEFAULT_PAGE = 1L;

    @Override
    public Long parse(HttpServletRequest request) throws ParserException {
        String pageString = request.getParameter(PAGE_KEY);
        if (pageString == null) {
            return DEFAULT_PAGE;
        }
        if (!NumberUtils.isNumber(pageString)) {
            throw new ParserException("Incorrect parameter format of " + PAGE_KEY + ": " + pageString);
        }
        return Long.valueOf(pageString);
    }
}
