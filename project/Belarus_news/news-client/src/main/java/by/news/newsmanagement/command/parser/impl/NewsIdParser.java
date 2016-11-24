package by.epam.newsmanagement.command.parser.impl;

import by.epam.newsmanagement.command.parser.RequestParser;
import by.epam.newsmanagement.exception.ParserException;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class NewsIdParser implements RequestParser<Long>{

    private static final String NEWS_ID_KEY = "newsId";
    @Override
    public Long parse(HttpServletRequest request) throws ParserException {
        String newsIdString = request.getParameter(NEWS_ID_KEY);
        if(newsIdString == null){
            throw new ParserException("Parameter " + NEWS_ID_KEY + "is not found");
        }
        if(!NumberUtils.isNumber(newsIdString)){
            throw new ParserException("Incorrect parameter format of " + NEWS_ID_KEY  + ": " + newsIdString);
        }
        return Long.valueOf(newsIdString);
    }
}
