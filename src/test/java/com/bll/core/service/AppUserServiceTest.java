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

import static com.bll.core.mapper.TestConstant.SOME_STRING;

@ExtendWith(DBUnitExtension.class)
public class AppUserServiceTest extends AbstractBeanTest {

    private static IAppUserService appUserService;
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;

        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ServiceBeanConfiguration.class, ConstantBeanConfiguration.class);
        appUserService = context.getBean("appUserService", IAppUserService.class);
    }

    @AfterAll
    public static void afterAll() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.DELETE_ALL, "/data/init/initEmptyDataSet.xml");
    }

    @Test
    @DataSet(cleanAfter = true)
    void appUserServiceTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initUserRoleDataSet.xml");

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

}
