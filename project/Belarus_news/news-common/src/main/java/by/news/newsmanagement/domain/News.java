package by.epam.newsmanagement.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This class describes the News-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public class News implements Serializable {

    private Long newsId;

    @NotNull
    @Size(min = 2, max = 30)
    private String title;
    @NotNull
    @Size(min = 2, max = 100)
    private String shortText;
    @NotNull
    @Size(min = 2, max = 2000)
    private String fullText;
    private Date creationDate;
    private Date modificationDate;
    private int version;

    public News() {
    }

    public News(Long newsId, String title, String shortText, String fullText, Date creationDate,
                Date modificationDate) {
        super();
        this.newsId = newsId;
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public News(Long newsId) {
        this.newsId = newsId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }


    @Override
    public String toString() {
        return News.class.getName() + " [newsId=" + newsId + ", title=" + title + ", shortText=" + shortText + ", fullText="
                + fullText + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate
                + ", version=" + version + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        News news = (News) obj;
        if (newsId != null ? !newsId.equals(news.newsId) : news.newsId != null) {
            return false;
        }
        if (title != null ? !title.equals(news.title) : news.title != null) {
            return false;
        }
        if (shortText != null ? !shortText.equals(news.shortText) : news.shortText != null) {
            return false;
        }
        if (fullText != null ? !fullText.equals(news.fullText) : news.fullText != null) {
            return false;
        }
        if (creationDate != null ? !creationDate.equals(news.creationDate) : news.creationDate != null) {
            return false;
        }
        return modificationDate != null ? modificationDate.equals(news.modificationDate) : news.modificationDate == null;

    }

    @Override
    public int hashCode() {
        int result = newsId != null ? newsId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (shortText != null ? shortText.hashCode() : 0);
        result = 31 * result + (fullText != null ? fullText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        return result;
    }
}
