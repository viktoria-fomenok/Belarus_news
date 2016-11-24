package by.epam.newsmanagement.dao.oracle;

import by.epam.newsmanagement.dao.IAuthorDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.dao.fieldhelper.AuthorField;
import by.epam.newsmanagement.dao.query.SQL;
import by.epam.newsmanagement.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class works with the entity of the author from the Oracle database
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Component
public class AuthorDaoImpl implements IAuthorDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public void makeExpired(Author author) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_MAKE_EXPIRED)) {
            ps.setTimestamp(1, new Timestamp(author.getExpired().getTime()));
            ps.setLong(2, author.getAuthorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with author = " + author, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Long create(Author entity) throws DaoException {
        long authorId;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_CREATE_AUTHOR,
                new String[]{AuthorField.AUTHOR_ID})) {
            ps.setString(1, entity.getAuthorName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                authorId = rs.getLong(1);
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with entity = " + entity, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with entity = " + entity, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return authorId;
    }

    @Override
    public Author read(Long key) throws DaoException {
        Author author;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_AUTHOR_ON_ID)) {
            ps.setLong(1, key);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    author = this.getAuthorFromResultSet(resultSet);
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
        return author;
    }

    @Override
    public List<Author> read() throws DaoException {
        List<Author> authorList = new ArrayList<Author>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL.SQL_GET_ALL_AUTHORS)) {
            while (rs.next()) {
                authorList.add(this.getAuthorFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return authorList;
    }

    @Override
    public boolean update(Long key, Author entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_UPDATE_AUTHOR)) {
            ps.setString(1, entity.getAuthorName());
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
    public List<Author> getAllNonExpiredAuthors() throws DaoException {
        List<Author> authorList = new ArrayList<Author>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL.SQL_GET_ALL_NON_EXPIRED_AUTHORS)) {
            while (rs.next()) {
                authorList.add(this.getAuthorFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return authorList;
    }

    @Override
    public boolean delete(Long key) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_AUTHOR)) {
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
    public Author readOnNewsId(long newsId) throws DaoException {
        Author author;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_AUTHOR_ON_NEWS_ID)) {
            ps.setLong(1, newsId);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    author = this.getAuthorFromResultSet(resultSet);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return author;
    }

    /**
     * <p>
     * This method reads one record from the {@link java.sql.ResultSet}
     * </p>
     *
     * @param rs is an resulting query
     * @return an object of class {@link by.epam.newsmanagement.domain.Author}
     * @throws SQLException if the error occurred in working with the database
     */
    private Author getAuthorFromResultSet(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getLong(AuthorField.AUTHOR_ID));
        author.setAuthorName(rs.getString(AuthorField.AUTHOR_NAME));
        author.setExpired(rs.getTimestamp(AuthorField.EXPIRED));
        return author;
    }

}
