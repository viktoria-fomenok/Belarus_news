package by.epam.newsmanagement.domain;

import java.util.Collections;
import java.util.List;

import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.domain.Tag;

/**
 * <p>
 * This class contains the search criteria for news
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public class SearchCriteria {

    private List<Long> authorList = Collections.EMPTY_LIST;
    private List<Long> tagList = Collections.EMPTY_LIST;

    public SearchCriteria() {
    }

    public SearchCriteria(List<Long> authorList, List<Long> tagList) {
        this.authorList = authorList;
        this.tagList = tagList;
    }

    public List<Long> getAuthorList() {
        return authorList;
    }

    public List<Long> getTagList() {
        return tagList;
    }

    public void setAuthorList(List<Long> authorList) {
        this.authorList = authorList;
    }

    public void setTagList(List<Long> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return SearchCriteria.class.getName() + " [authorList=" + authorList + ", tagList=" + tagList + "]";
    }
}
