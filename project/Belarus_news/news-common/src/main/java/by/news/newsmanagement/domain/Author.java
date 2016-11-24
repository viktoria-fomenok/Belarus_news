package by.epam.newsmanagement.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This class describes the Author-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public class Author implements Serializable {

    private Long authorId;
    @NotNull
    @Size(min = 3, max = 20)
    private String authorName;
    private Date expired;

    public Author() {
    }

    public Author(Long authorId, String authorName, Date expired) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.expired = expired;
    }

    public Author(Long authorId) {
        this.authorId = authorId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return Author.class.getName() + " [authorId=" + authorId + ", authorName="
                + authorName + ", expired=" + expired + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Author author = (Author) obj;
        if (authorId != null ? !authorId.equals(author.authorId) : author.authorId != null) {
            return false;
        }
        if (authorName != null ? !authorName.equals(author.authorName) : author.authorName != null) {
            return false;
        }
        return !(expired != null ? !expired.equals(author.expired) : author.expired != null);

    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }
}
