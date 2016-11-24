package by.epam.newsmanagement.dao.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import by.epam.newsmanagement.dao.ITagDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.dao.fieldhelper.TagField;
import by.epam.newsmanagement.dao.query.SQL;
import by.epam.newsmanagement.domain.Tag;

/**
 * <p>
 * This class works with the entity of the tag from the Oracle database
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Component
public class TagDaoImpl implements ITagDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public Long create(Tag entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_CREATE_TAG,
                new String[]{TagField.TAG_ID})) {
            ps.setString(1, entity.getTagName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getLong(1);
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with entity = " + entity, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with entity = " + entity, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Tag read(Long key) throws DaoException {
        Tag tag;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_TAG_ON_ID)) {
            ps.setLong(1, key);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    tag = this.getTagFromResultSet(resultSet);
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
        return tag;
    }

    @Override
    public List<Tag> read() throws DaoException {
        List<Tag> tagList = new ArrayList<Tag>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL.SQL_GET_ALL_TAGS)) {
            while (rs.next()) {
                tagList.add(this.getTagFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return tagList;
    }

    @Override
    public boolean deleteNewsTag(long tagId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_NEWS_TAG_ON_TAG_ID)) {
            ps.setLong(1, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + tagId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    @Override
    public boolean update(Long key, Tag entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_UPDATE_TAG)) {
            ps.setString(1, entity.getTagName());
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
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_TAG)) {
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
    public List<Tag> readOnNewsId(long newsId) throws DaoException {
        List<Tag> tagList = new ArrayList<Tag>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_TAGS_ON_NEWS_ID)) {
            ps.setLong(1, newsId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tagList.add(this.getTagFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return tagList;
    }

    /**
     * <p>
     * This method reads one record from the {@link java.sql.ResultSet}
     * </p>
     *
     * @param rs is an resulting query
     * @return an object of class {@link by.epam.newsmanagement.domain.Tag}
     * @throws SQLException if the error occurred in working with the database
     */
    private Tag getTagFromResultSet(ResultSet rs) throws SQLException {
        Tag tag = new Tag();
        tag.setTagId(rs.getLong(TagField.TAG_ID));
        tag.setTagName(rs.getString(TagField.TAG_NAME));
        return tag;
    }

}
