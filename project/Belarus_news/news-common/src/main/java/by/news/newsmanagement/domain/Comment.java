package by.epam.newsmanagement.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This class describes the Comment-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
public class Comment implements Serializable {


    private Long commentId;
    @NotNull
    private Long newsId;
    @NotNull
    @Size(min = 1, max = 100)
    private String commentText;
    private Date creationDate;


    public Comment() {
    }

    public Comment(Long commentId, Long newsId, String commentText, Date creationDate) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.commentText = commentText;
        this.creationDate = creationDate;
    }

    public Comment(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return Comment.class.getName() + " [commentId=" + commentId + ", newsId=" + newsId
                + ", commentText=" + commentText + ", creationDate=" + creationDate + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Comment comment = (Comment) obj;
        if (commentId != null ? !commentId.equals(comment.commentId) : comment.commentId != null) {
            return false;
        }
        if (newsId != null ? !newsId.equals(comment.newsId) : comment.newsId != null) {
            return false;
        }
        if (commentText != null ? !commentText.equals(comment.commentText) : comment.commentText != null) {
            return false;
        }
        return creationDate != null ? creationDate.equals(comment.creationDate) : comment.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (newsId != null ? newsId.hashCode() : 0);
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
