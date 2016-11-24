package by.epam.newsmanagement.dao.sql;

/**
 * <p>This class contains the sql queries</p>
 *
 * @author Mikita_Kobyzau
 */
public final class SqlRequest {

    public static final String SQL_GET_ALL_AUTHORS = "SELECT auth_id, auth_name, auth_expired" +
            " FROM AUTHORS";
    public static final String SQL_GET_ALL_NON_EXPIRED_AUTHORS = "SELECT auth_id, auth_name, auth_expired" +
            " FROM AUTHORS WHERE auth_expired IS NULL";
    public static final String SQL_GET_AUTHOR_ON_ID = "SELECT auth_id, auth_name, auth_expired" +
            " FROM AUTHORS WHERE auth_id = ?";
    public static final String SQL_GET_AUTHOR_ON_NEWS_ID = "SELECT auth_id, auth_name," +
            " auth_expired FROM AUTHORS INNTER JOIN NEWS_AUTHORS ON auth_id = NEWS_AUTHORS.na_author_id" +
            " WHERE na_news_id = ?";
    public static final String SQL_CREATE_AUTHOR = "INSERT INTO AUTHORS" +
            "(auth_name) VALUES(?)";
    public static final String SQL_UPDATE_AUTHOR = "UPDATE AUTHORS SET auth_name = ?"
            + "  WHERE auth_id = ?";
    public static final String SQL_DELETE_AUTHOR = "DELETE FROM AUTHORS WHERE auth_id = ?";
    public static final String SQL_MAKE_EXPIRED = "UPDATE AUTHORS SET auth_expired = ? WHERE auth_id = ?";

    public static final String SQL_GET_ALL_COMMENTS = "SELECT com_id, com_news_id, com_text," +
            " com_creation_date FROM COMMENTS";
    public static final String SQL_GET_COMMENT_ON_ID = "SELECT com_id, com_news_id, com_text," +
            " com_creation_date FROM COMMENTS WHERE com_id = ?";
    public static final String SQL_GET_COMMENTS_ON_NEWS_ID = "SELECT com_id, com_news_id, com_text," +
            " com_creation_date FROM COMMENTS WHERE com_news_id = ?";
    public static final String SQL_CREATE_COMMENT = "INSERT INTO COMMENTS( com_news_id, com_text,"
            + " com_creation_date) VALUES(?,?,?)";
    public static final String SQL_UPDATE_COMMENT = "UPDATE COMMENTS SET com_text = ?"
            + " WHERE com_id = ?";
    public static final String SQL_DELETE_COMMENT = "DELETE FROM COMMENTS WHERE com_id = ?";
    public static final String SQL_DELETE_COMMENT_ON_NEWS_ID = "DELETE FROM COMMENTS WHERE com_news_id = ?";

    public static final String SQL_GET_ALL_NEWS = "SELECT news_id, news_title, news_short_text," +
            " news_full_text, news_creation_date, news_modification_date FROM NEWS";
    public static final String SQL_GET_NEWS_ON_ID = "SELECT news_id, news_title, news_short_text," +
            " news_full_text, news_creation_date, news_modification_date FROM NEWS WHERE news_id = ?";
    public static final String SQL_GET_ALL_NEWS_ON_CRITERIA = "SELECT news_id, news_title,"
            + " news_short_text, news_full_text, news_creation_date, news_modification_date, COUNT(com_id) AS" +
            " comment_count  FROM NEWS INNER JOIN NEWS_AUTHORS ON NEWS.news_id = NEWS_AUTHORS.na_news_id LEFT" +
            " JOIN COMMENTS ON NEWS.news_id = COMMENTS.com_news_id";
    public static final String SQL_CREATE_NEWS = "INSERT INTO NEWS( news_title, news_short_text,"
            + " news_full_text, news_creation_date, news_modification_date) VALUES(?,?,?,?, ?)";
    public static final String SQL_UPDATE_NEWS = "UPDATE NEWS SET news_title = ?, news_short_text = ?,"
            + " news_full_text = ?, news_modification_date = ? WHERE news_id = ?";
    public static final String SQL_DELETE_NEWS = "DELETE FROM NEWS WHERE news_id = ?";
    public static final String SQL_GET_COUNT_OF_NEWS = "SELECT COUNT(news_id) FROM (";

    public static final String SQL_GET_ALL_TAGS = "SELECT tag_id, tag_name FROM TAGS";
    public static final String SQL_GET_TAG_ON_ID = "SELECT tag_id, tag_name FROM TAGS WHERE tag_id = ?";
    public static final String SQL_GET_TAGS_ON_NEWS_ID = "SELECT DISTINCT tag_id, tag_name FROM TAGS INNTER JOIN" +
            " NEWS_TAGS ON tag_id = NEWS_TAGS.nt_tag_id WHERE nt_news_id = ?";
    public static final String SQL_CREATE_TAG = "INSERT INTO TAGS(tag_name) VALUES(?)";
    public static final String SQL_UPDATE_TAG = "UPDATE TAGS SET tag_name = ? WHERE tag_id = ?";
    public static final String SQL_DELETE_TAG = "DELETE FROM TAGS WHERE tag_id = ?";

    public static final String SQL_CREATE_NEWS_AUTHOR = "INSERT INTO NEWS_AUTHORS"
            + "(na_news_id, na_author_id) VALUES(?,?)";
    public static final String SQL_DELETE_NEWS_AUTHOR = "DELETE FROM NEWS_AUTHORS WHERE na_news_id = ?";

    public static final String SQL_CREATE_NEWS_TAG = "INSERT INTO News_TAGS" + "(nt_news_id, nt_tag_id) VALUES(?,?)";
    public static final String SQL_DELETE_NEWS_TAG_ON_NEWS_ID = "DELETE FROM NEWS_TAGS WHERE nt_news_id = ?";
    public static final String SQL_DELETE_NEWS_TAG_ON_TAG_ID = "DELETE FROM NEWS_TAGS WHERE nt_tag_id = ?";

    public static final String SQL_SORTING_BY_COMMENTS_AND_MODIFICATION_DATE_QUERY = " GROUP BY news_id, news_title," +
            " news_short_text, news_full_text, news_creation_date, news_modification_date" +
            " order by  news_modification_date desc, comment_count desc";
    public static final String SQL_NEWS_PAGINATION_LEFT_QUERY = "SELECT news_id, news_title, news_short_text," +
            " news_full_text, news_creation_date, news_modification_date FROM ( SELECT rownum as rnum, news_id," +
            " news_title, news_short_text, news_full_text, news_creation_date, news_modification_date FROM (";
    public static final String SQL_NEWS_PAGINATION_RIGHT_QUERY = ")  WHERE rownum <= ? ) WHERE rnum >= ?";
    public static final String SQL_SEARCH_BY_TAGS_LEFT_QUERY = "news_id in (select nt_news_id from news_tags where (nt_tag_id in(";
    public static final String SQL_SEARCH_BY_TAGS_RIGHT_QUERY = ")) group by nt_news_id having count(nt_news_id) = ?)";
    public static final String SQL_PAGER_LEFT_QUERY = "SELECT next_id,previous_id  FROM (SELECT LEAD(news_id) OVER" +
            " (ORDER BY news_modification_date desc, comment_count desc) AS next_id, LAG(news_id) OVER" +
            " (ORDER BY news_modification_date desc, comment_count desc) AS previous_id, news_id, news_title FROM (";
    public static final String SQL_PAGER_RIGHT_QUERY = ")) WHERE news_id = ?";
    public static final String BASIC_QUERY_BY_AUTHORS = " na_author_id =";

    public static final String COMMA = " ,";
    public static final String AND_PHRASE = " AND ";
    public static final String OR_PHRASE = " OR ";
    public static final String CLOSE_BRACKET = ")";
    public static final String OPEN_BRACKET = "(";
    public static final String WHERE_PHRASE = " WHERE ";
    public static final String WILD_CARD = "?";

}
