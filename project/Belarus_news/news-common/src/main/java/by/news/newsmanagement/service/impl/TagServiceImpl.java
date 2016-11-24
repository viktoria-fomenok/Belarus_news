package by.epam.newsmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.epam.newsmanagement.dao.ITagDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Tag;
import by.epam.newsmanagement.service.ITagService;
import by.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * This class is designed to solve problems of business logic for a Tag-entity
 * </p>
 *
 * @author Mikita_Kobyzau
 */
@Service("tagService")
public class TagServiceImpl implements ITagService {

    @Autowired
    private ITagDao tagDao;

    public boolean addTag(Tag tag) throws ServiceException {
        try {
            List<Tag> tagList = tagDao.read();
            for (Tag dsTag : tagList) {
                if (dsTag.getTagName().equals(tag.getTagName())) {
                    return false;
                }
            }
            tagDao.create(tag);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Tag Service with tag = " + tag, e);
        }
        return true;
    }

    @Override
    public List<Tag> readByNewsId(Long newsId) throws ServiceException {
        try {
            List<Tag> tagList = tagDao.readOnNewsId(newsId);
            return tagList;
        } catch (DaoException e) {
            throw new ServiceException("Exception in Tag Service with newsId" + newsId, e);
        }
    }

    public List<Tag> readAll() throws ServiceException {
        try {
            List<Tag> tagList =tagDao.read();
            return tagList;
        } catch (DaoException e) {
            throw new ServiceException("Exception in Tag Service", e);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTag(long tagId) throws ServiceException {
        try {
            tagDao.deleteNewsTag(tagId);
            tagDao.delete(tagId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Tag Service with tagId = " + tagId, e);
        }
    }

    @Override
    public void updateTag(long tagId, Tag tag) throws ServiceException {
        try {
            tagDao.update(tagId, tag);
        } catch (DaoException e) {
            throw new ServiceException("Exception in Tag Service with tagId = "
                    + tagId + ", tag = " + tag, e);
        }
    }
}
