package by.epam.newsmanagement.test.service;

import by.epam.newsmanagement.dao.ITagDao;
import by.epam.newsmanagement.dao.exception.DaoException;
import by.epam.newsmanagement.domain.Tag;
import by.epam.newsmanagement.service.exception.ServiceException;
import by.epam.newsmanagement.service.impl.TagServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceTest {

	@Mock
	private ITagDao tagDao;

	@InjectMocks
	private TagServiceImpl tagService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	@Ignore

	@Test
	public void addTagTest_ShouldAddTag() throws ServiceException, DaoException {
		Tag tag = new Tag(1L);
		tagService.addTag(tag);
		verify(tagDao).create(tag);
	}
	@Ignore

	@Test(expected = ServiceException.class)
	public void addTagTest_ShouldThrowException() throws ServiceException, DaoException {
		Tag tag = new Tag(1L);
		when(tagDao.create(tag)).thenThrow(DaoException.class);
		tagService.addTag(tag);
		verify(tagDao).create(tag);
	}
}
