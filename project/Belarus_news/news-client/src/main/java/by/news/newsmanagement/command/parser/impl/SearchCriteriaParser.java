package by.epam.newsmanagement.command.parser.impl;

import by.epam.newsmanagement.command.parser.RequestParser;
import by.epam.newsmanagement.domain.SearchCriteria;
import by.epam.newsmanagement.exception.ParserException;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SearchCriteriaParser implements RequestParser<SearchCriteria> {

    private static final String AUTHOR_LIST_KEY = "authorList";
    private static final String TAG_LIST_KEY = "tagList";

    @Override
    public SearchCriteria parse(HttpServletRequest request) throws ParserException {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setAuthorList(parseList(request, AUTHOR_LIST_KEY));
        criteria.setTagList(parseList(request, TAG_LIST_KEY));
        return criteria;
    }

    private List<Long> parseList(HttpServletRequest request, String param_name) throws ParserException{
        String[] stringList = request.getParameterValues(param_name);
        if (stringList == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<Long> list = new ArrayList<>();
            for (String idString : stringList) {
                if (!NumberUtils.isNumber(idString)) {
                    throw new ParserException("Incorrect parameter format of " + param_name +": " + idString);
                }
                list.add(Long.parseLong(idString));
            }
            return list;
        }
    }

}
