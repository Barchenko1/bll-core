package com.bll.core.service;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.bll.core.bean.ConstantBeanConfiguration;
import com.bll.core.bean.ServiceBeanConfiguration;
import com.bll.core.mapper.TestUtil;
import com.bll.core.service.appuser.IAppUserService;
import com.bll.core.service.post.IPostService;
import com.core.im.tenant.dto.request.post.PostDto;
import com.core.im.tenant.modal.post.Post;
import com.cos.core.dao.post.IPostDao;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@ExtendWith(DBUnitExtension.class)
public class PostServiceTest extends AbstractBeanTest {

    private static IAppUserService appUserService;
    private static IPostService postService;
    private static IPostDao postDao;
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;

        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ServiceBeanConfiguration.class, ConstantBeanConfiguration.class);
        postService = context.getBean("postService", IPostService.class);
        postDao = context.getBean("postDao", IPostDao.class);
    }

    @AfterAll
    public static void afterAll() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.DELETE_ALL, "/data/init/initEmptyDataSet.xml");
    }

    @Test
    @DataSet(cleanAfter = true)
    void createNewPost() {
        Post postParent = new Post();
        postParent.setParent(null);
        postParent.setAuthorEmail("parent@mail.com");
        postParent.setAuthorName("parentAuthor");
        postParent.setMessage("parent message1");

        PostDto postDto = new PostDto();
        postDto.setEmail("post@mail.com");
        postDto.setName("middleAuthor");
        postDto.setMessage("some message2");
        postDto.setParentPost(postParent);

        postService.createNewPost(postDto);
    }

    @Test
    @DataSet(cleanAfter = true)
    void addNewPost() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initPostDataSet.xml");

        PostDto postDto = new PostDto();
        postDto.setEmail("post@mail.com");
        postDto.setName("middleAuthor");
        postDto.setMessage("some message2");
        postDto.setParentPostId(100L);

        Post parentPost = (Post) getEntity(postDao, "post", "id", postDto.getParentPostId());
        postDto.setParentPost(parentPost);

        postService.createNewPost(postDto);
    }

    @Test
    @DataSet(cleanAfter = true)
    void updatePost() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initPostDataSet.xml");

        PostDto postDto = new PostDto();
        postDto.setEmail("postUpdated@mail.com");
        postDto.setName("middleUpdatedAuthor");
        postDto.setMessage("some message Updated");
        postDto.setPostId(101L);

        postService.updatePost(postDto);
    }

    @Test
    @DataSet(cleanAfter = true)
    void deletePost() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initPostDataSet.xml");

        PostDto postDto = new PostDto();
        postDto.setEmail("post@mail.com");
        postDto.setName("middleAuthor");
        postDto.setMessage("some message2");
        postDto.setPostId(101L);

        postService.deletePost(postDto);
    }


}
