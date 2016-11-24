package by.epam.newsmanagement.domain;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;
import java.io.Serializable;
import java.util.List;

/**
 * Created by NikitOS on 04.04.2016.
 */
public class NewsRecord implements Serializable {
    @NotNull
    @Valid
    private News news;
    private List<Long> tagIdList;
    @NotNull
    private Long authorId;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<Long> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Long> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return NewsRecord.class.getName() + " [news=" + news + ", authorId=" + authorId + ", tagIdList="
                + tagIdList + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NewsRecord that = (NewsRecord) obj;
        if (news != null ? !news.equals(that.news) : that.news != null) {
            return false;
        }
        if (tagIdList != null ? !tagIdList.equals(that.tagIdList) : that.tagIdList != null) {
            return false;
        }
        return !(authorId != null ? !authorId.equals(that.authorId) : that.authorId != null);

    }

    @Override
    public int hashCode() {
        int result = news != null ? news.hashCode() : 0;
        result = 31 * result + (tagIdList != null ? tagIdList.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        return result;
    }
}
