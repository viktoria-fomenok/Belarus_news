package by.epam.newsmanagement.dao.oracle;

import by.epam.newsmanagement.dao.INewsDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.dao.fieldhelper.NewsField;
import by.epam.newsmanagement.dao.fieldhelper.PagerField;
import by.epam.newsmanagement.dao.query.SQL;
import by.epam.newsmanagement.domain.News;
import by.epam.newsmanagement.domain.Pager;
import by.epam.newsmanagement.domain.SearchCriteria;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.persistence.OptimisticLockException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class works with the entity of the news from the Oracle database
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Component
public class NewsDaoImpl implements INewsDao {

    @Autowired
    private DataSource dataSource;
    private static final Logger LOG = LogManager.getLogger(NewsDaoImpl.class);
    @Override
    public Long create(News entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_CREATE_NEWS,
                new String[]{NewsField.NEWS_ID})) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getShortText());
            ps.setString(3, entity.getFullText());
            ps.setTimestamp(4, new Timestamp(entity.getCreationDate().getTime()));
            ps.setDate(5, new Date(entity.getModificationDate().getTime()));
            ps.setInt(6, entity.getVersion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                entity.setNewsId(rs.getLong(1));
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with entity = " + entity, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with entity = " + entity, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return entity.getNewsId();
    }

    @Override
    public News read(Long key) throws DaoException {
        News news;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_GET_NEWS_ON_ID)) {
            ps.setLong(1, key);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    news = this.getNewsFromResultSet(resultSet);
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
        return news;
    }

    @Override
    public List<News> read() throws DaoException {
        List<News> newsList = new ArrayList<News>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL.SQL_GET_ALL_NEWS)) {
            while (rs.next()) {
                newsList.add(this.getNewsFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return newsList;
    }

    @Override
    public boolean update(Long key, News entity) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_UPDATE_NEWS)) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getShortText());
            ps.setString(3, entity.getFullText());
            ps.setDate(4, new Date(entity.getModificationDate().getTime()));
            ps.setLong(5, key);
            ps.setInt(6, entity.getVersion());
            if (ps.executeUpdate() == 0) {
                throw new OptimisticLockException();
            }
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
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_NEWS)) {
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
    public Long countOfNews(SearchCriteria criteria) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String query = SQL.SQL_GET_COUNT_OF_NEWS + getSearchCriteriaQuery(criteria)
                + SQL.CLOSE_BRACKET;
        LOG.debug("Count of news query: " + query);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            for (Long id : criteria.getTagList()) {
                ps.setLong(parameterIndex++, id);
            }
            if (!criteria.getTagList().isEmpty()) {
                ps.setInt(parameterIndex++, criteria.getTagList().size());
            }
            for (Long id : criteria.getAuthorList()) {
                ps.setLong(parameterIndex++, id);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new DaoException("Empty ResultSet");
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with"
                        + " criteria = " + criteria, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with" +
                    " criteria = " + criteria, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<News> readByCriteria(Long page, Integer newsPerPage, SearchCriteria criteria) throws DaoException {
        List<News> newsList = new ArrayList<News>();
        int parameterIndex = 1;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String query = SQL.SQL_NEWS_PAGINATION_LEFT_QUERY + getSearchCriteriaQuery(criteria)
                + SQL.SQL_NEWS_PAGINATION_RIGHT_QUERY;
        LOG.debug("Pagination query: " + query);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            long firstNewsPosition = newsPerPage * (page - 1) + 1;
            long lastNewsPosition = page * newsPerPage;
            for (Long id : criteria.getTagList()) {
                ps.setLong(parameterIndex++, id);
            }
            if (!criteria.getTagList().isEmpty()) {
                ps.setInt(parameterIndex++, criteria.getTagList().size());
            }
            for (Long id : criteria.getAuthorList()) {
                ps.setLong(parameterIndex++, id);
            }

            ps.setLong(parameterIndex++, lastNewsPosition);
            ps.setLong(parameterIndex++, firstNewsPosition);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    newsList.add(this.getNewsFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with page = "
                        + page + ", newsPerPage = " + newsPerPage + ", criteria = " + criteria, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with page = "
                    + page + ", newsPerPage = " + newsPerPage + ", criteria = " + criteria, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return newsList;
    }

    @Override
    public boolean connectNewsWithTag(long newsId, long tagId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_CREATE_NEWS_TAG)) {
            ps.setLong(1, newsId);
            ps.setLong(2, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = "
                    + newsId + ", tagId = " + tagId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    @Override
    public boolean connectNewsWithAuthor(long newsId, long authorId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_CREATE_NEWS_AUTHOR)) {
            ps.setLong(1, newsId);
            ps.setLong(2, authorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = "
                    + newsId + ", authorId = " + authorId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    @Override
    public boolean deleteNewsTag(long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_NEWS_TAG_ON_NEWS_ID)) {
            ps.setLong(1, newsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    @Override
    public Pager getPager(long newsId, SearchCriteria criteria) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String query = SQL.SQL_PAGER_LEFT_QUERY + getSearchCriteriaQuery(criteria)
                + SQL.SQL_PAGER_RIGHT_QUERY;
        LOG.debug("Pager query: " + query);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            for (Long id : criteria.getTagList()) {
                ps.setLong(parameterIndex++, id);
            }
            if (!criteria.getTagList().isEmpty()) {
                ps.setInt(parameterIndex++, criteria.getTagList().size());
            }
            for (Long id : criteria.getAuthorList()) {
                ps.setLong(parameterIndex++, id);
            }
            ps.setLong(parameterIndex++, newsId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pager pager = new Pager();
                    pager.setNextId(rs.getLong(PagerField.NEXT_ID));
                    if (rs.wasNull()) {
                        pager.setNextId(null);
                    }
                    pager.setPreviousId(rs.getLong(PagerField.PREVIOUS_ID));
                    if (rs.wasNull()) {
                        pager.setPreviousId(null);
                    }
                    return pager;
                } else {
                    throw new DaoException("Empty ResultSet");
                }
            } catch (SQLException e) {
                throw new DaoException("Exception in working with the database with"
                        + " criteria = " + criteria, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with" +
                    " criteria = " + criteria, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean deleteNewsAuthor(long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL.SQL_DELETE_NEWS_AUTHOR)) {
            ps.setLong(1, newsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in working with the database with newsId = " + newsId, e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return true;
    }

    private String getSearchCriteriaQuery(SearchCriteria criteria) {
        StringBuilder sb = new StringBuilder();
        sb.append(SQL.SQL_GET_ALL_NEWS_ON_CRITERIA);
        boolean isTagAdded = false;
        if (criteria.getTagList() != null && !criteria.getTagList().isEmpty()) {
            isTagAdded = true;
            sb.append(SQL.WHERE_PHRASE);
            sb.append(SQL.SQL_SEARCH_BY_TAGS_LEFT_QUERY);
            for (int i = 0; i < criteria.getTagList().size(); i++) {
                sb.append(SQL.WILD_CARD + SQL.COMMA);
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(SQL.SQL_SEARCH_BY_TAGS_RIGHT_QUERY);
        }
        if (criteria.getAuthorList() != null && !criteria.getAuthorList().isEmpty()) {
            if (isTagAdded) {
                sb.append(SQL.AND_PHRASE + SQL.OPEN_BRACKET);
            } else {
                sb.append(SQL.WHERE_PHRASE);
            }
            for (int i = 0; i < criteria.getAuthorList().size(); i++) {
                sb.append(SQL.BASIC_QUERY_BY_AUTHORS + SQL.WILD_CARD);
                sb.append(SQL.OR_PHRASE);
            }
            sb.delete(sb.length() - 3, sb.length() - 1);
            if (isTagAdded) {
                sb.append(SQL.CLOSE_BRACKET);
            }
        }
        sb.append(SQL.SQL_SORTING_BY_COMMENTS_AND_MODIFICATION_DATE_QUERY);
        LOG.debug("Search Criteria query: " + sb.toString());
        return sb.toString();
    }

    private News getNewsFromResultSet(ResultSet rs) throws SQLException {
        News news = new News();
        news.setNewsId(rs.getLong(NewsField.NEWS_ID));
        news.setTitle(rs.getString(NewsField.TITLE));
        news.setShortText(rs.getString(NewsField.SHOTR_TEXT));
        news.setFullText(rs.getString(NewsField.FULL_TEXT));
        news.setCreationDate(rs.getTimestamp(NewsField.CREATION_DATE));
        news.setModificationDate(rs.getTimestamp(NewsField.MODIFICATION_DATE));
        news.setVersion(rs.getInt(NewsField.VERSION));
        return news;
    }

}
