package by.epam.newsmanagement.domain.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import by.epam.newsmanagement.domain.Author;
import by.epam.newsmanagement.domain.Comment;
import by.epam.newsmanagement.domain.News;
import by.epam.newsmanagement.domain.Tag;

/**
 * <p>
 * This class describes the data transfer object News. This class consist of
 * news, author, list of tags and comments
 * </p>
 * 
 * @author Mikita_Kobyzau
 */
public class NewsDTO implements Serializable{

	private News news;
	private Author author;
	private List<Tag> tagList = Collections.EMPTY_LIST;
	private List<Comment> commentList = Collections.EMPTY_LIST;

	public NewsDTO() {
	}

	public NewsDTO(Author author, News news, List<Tag> tagList, List<Comment> commentList) {
		this.author = author;
		this.news = news;
		this.tagList = tagList;
		this.commentList = commentList;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return NewsDTO.class.getName() + " [news=" + news + ", author=" + author + ", tagList="
				+ tagList + ", commentList=" + commentList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((commentList == null) ? 0 : commentList.hashCode());
		result = prime * result + ((news == null) ? 0 : news.hashCode());
		result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsDTO other = (NewsDTO) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (commentList == null) {
			if (other.commentList != null)
				return false;
		} else if (!commentList.equals(other.commentList))
			return false;
		if (news == null) {
			if (other.news != null)
				return false;
		} else if (!news.equals(other.news))
			return false;
		if (tagList == null) {
			if (other.tagList != null)
				return false;
		} else if (!tagList.equals(other.tagList))
			return false;
		return true;
	}

}
