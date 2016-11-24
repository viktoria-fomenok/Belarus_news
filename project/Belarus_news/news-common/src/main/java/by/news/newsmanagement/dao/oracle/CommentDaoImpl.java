package by.epam.newsmanagement.dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import by.epam.newsmanagement.dao.ICommentDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.dao.fieldhelper.CommentField;
import by.epam.newsmanagement.dao.query.SQL;
import by.epam.newsmanagement.domain.Comment;

/**
 * <p>
 * This class works with the entity of the comment from the Oracle database
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Component
public class CommentDaoImpl implements ICommentDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public Long create(Comment entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_CREATE_COMMENT,
                new String[]{CommentField.COMMENT_ID})) {
            ps.setLong(1, entity.getNewsId());
            ps.setString(2, entity.getCommentText());
            ps.setTimestamp(3, new Timestamp(entity.getCreationDate().getTime()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                entity.setCommentId(rs.getLong(1));
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with entity = " + entity, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with entity = " + entity, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return entity.getCommentId();
    }

    @Override
    public Comment read(Long key) throws DaoException {
        Comment comment;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_COMMENT_ON_ID)) {
            ps.setLong(1, key);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    comment = this.getCommentFromResultSet(resultSet);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with key = " + key, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with key = " + key, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return comment;
    }

    @Override
    public List<Comment> read() throws DaoException {
        List<Comment> commentList = new ArrayList<Comment>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL.SQL_GET_ALL_COMMENTS)) {
            while (rs.next()) {
                commentList.add(this.getCommentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return commentList;
    }

    @Override
    public boolean update(Long key, Comment entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_UPDATE_COMMENT)) {
            ps.setString(1, entity.getCommentText());
            ps.setLong(2, key);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with key = "
                    + key + ", entity = " + entity, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    @Override
    public boolean delete(Long key) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_COMMENT)) {
            ps.setLong(1, key);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with key = " + key, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    @Override
    public List<Comment> readOnNewsId(long newsId) throws DaoException {
        List<Comment> commentList = new ArrayList<Comment>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_COMMENTS_ON_NEWS_ID)) {
            ps.setLong(1, newsId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    commentList.add(this.getCommentFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return commentList;
    }

    @Override
    public boolean deleteOnNewsId(long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_COMMENT_ON_NEWS_ID)) {
            ps.setLong(1, newsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    /**
     * <p>
     * This method reads one record from the {@link java.sql.ResultSet}
     * </p>
     *
     * @param rs is an resulting query
     * @return an object of class {@link by.epam.newsmanagement.domain.Comment}
     * @throws SQLException if the error occurred in working with the database
     */
    private Comment getCommentFromResultSet(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getLong(CommentField.COMMENT_ID));
        comment.setCommentText(rs.getString(CommentField.COMMENT_TEXT));
        comment.setCreationDate(rs.getTimestamp(CommentField.CREATION_DATE));
        comment.setNewsId(rs.getLong(CommentField.NEWS_ID));
        return comment;
    }

}
