package com.bll.core.service;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.bll.core.bean.ConstantBeanConfiguration;
import com.bll.core.bean.ServiceBeanConfiguration;
import com.bll.core.service.appuser.IAppUserService;
import com.bll.core.mapper.TestUtil;
import com.bll.core.service.post.IPostService;
import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.post.PostDto;
import com.core.im.tenant.dto.request.post.RegistrationAppUserDto;
import com.core.im.tenant.modal.post.Post;
import com.core.im.tenant.modal.user.UserDetail;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.bll.core.mapper.TestConstant.SOME_STRING;

@ExtendWith(DBUnitExtension.class)
public class ServiceBeanTest extends AbstractBeanTest {

    private static IAppUserService appUserService;
    private static IPostService postService;
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initUserRoleDataSet.xml");

        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ServiceBeanConfiguration.class, ConstantBeanConfiguration.class);
        appUserService = context.getBean("appUserService", IAppUserService.class);
        postService = context.getBean("postService", IPostService.class);
    }

    @AfterAll
    public static void afterAll() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.DELETE_ALL, "/data/init/initEmptyDataSet.xml");
    }

    @Test
    @DataSet(cleanAfter = true)
    void appUserServiceTest() {
        RegistrationAppUserDto registrationAppUserDto = new RegistrationAppUserDto();
        registrationAppUserDto.setEmail("test@mail.com");
        registrationAppUserDto.setPassword("password");

        UserDetail userDetail = new UserDetail();
        userDetail.setPhone("050-5005");

        registrationAppUserDto.setUserDetail(userDetail);

        registrationAppUserDto.setUserStreet(SOME_STRING);
        registrationAppUserDto.setUserApartment(111);
        appUserService.createNewUser(registrationAppUserDto, RoleEnum.ROLE_USER);
    }

    @Test
    @DataSet(cleanAfter = true)
    void postService() {
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
}