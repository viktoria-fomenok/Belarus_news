package by.epam.newsmanagement.dao;

import java.util.List;

import by.epam.newsmanagement.dao.exception.DaoException;

/**
 * <p>
 * This interface describes the basic methods for classes DAO layer. This
 * interface implements a pattern <strong>DAO</strong>
 * </p>
 *
 * @param <K> describes a unique identifier of the entity
 * @param <T> describes the type of the entity
 * @author Nikita Kobyzov
 */
public interface IGenericDao<K, T> {
    /**
     * <p>
     * This method writes a new object
     * </p>
     *
     * @param entity is the object that to be recorded
     * @return a generated identifier of created object
     * @throws DaoException if the error occurred in working with the data source
     */
    K create(T entity) throws DaoException;

    /**
     * <p>
     * This method reads the object on a key
     * </p>
     *
     * @param key is a unique identifier of the entity
     * @return the object corresponding to the given key or <tt>null</tt> if
     * there isn't entity with the appropriate key
     * @throws DaoException if the error occurred in working with the data source
     */
    T read(K key) throws DaoException;

    /**
     * <p>
     * This method reads all object
     * </p>
     *
     * @return a list of all objects
     * @throws DaoException if the error occurred in working with the data source
     */
    List<T> read() throws DaoException;

    /**
     * <p>
     * This method updates an object in the data source on a key
     * </p>
     *
     * @param key    is a unique identifier of the entity that to be changed
     * @param entity is a object that to be recorded
     * @return <tt>true</tt> if updating was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean update(K key, T entity) throws DaoException;

    /**
     * <p>
     * This method deletes an object in the data source on a key
     * </p>
     *
     * @param key is a unique identifier of the entity that to be deleted
     * @return <tt>true</tt> if deleting was successful
     * @throws DaoException if the error occurred in working with the data source
     */
    boolean delete(K key) throws DaoException;
}
