package com.bll.core.service;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.bll.core.bean.ConstantBeanConfiguration;
import com.bll.core.bean.ServiceBeanConfiguration;
import com.bll.core.service.appuser.AppUserService;
import com.bll.core.service.appuser.IAppUserService;
import com.bll.core.util.TestUtil;
import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.RegistrationAppUserDto;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserDetail;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.dbunit.database.DatabaseDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

import static com.bll.core.bean.AbstractBeanTest.getOrgHikariDataSource;
import static com.bll.core.bean.AbstractBeanTest.getTenantHikariDataSource;

@ExtendWith(DBUnitExtension.class)
public class ServiceBeanTest extends AbstractBeanTest {

    private static IAppUserService appUserService;
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() throws SQLException {

        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ServiceBeanConfiguration.class, ConstantBeanConfiguration.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;

        appUserService = context.getBean("appUserService", IAppUserService.class);
    }

    @AfterAll
    public static void afterAll() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.DELETE_ALL, "/data/init/initEmptyDataSet.xml");
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void appUserServiceTest() {
        RegistrationAppUserDto registrationAppUserDto = new RegistrationAppUserDto();
        registrationAppUserDto.setEmail("test@mail.com");
        registrationAppUserDto.setPassword("password");

        UserDetail userDetail = new UserDetail();
        userDetail.setPhone("050-5005");

        registrationAppUserDto.setUserDetail(userDetail);
        appUserService.createNewUser(registrationAppUserDto, RoleEnum.ROLE_USER);
    }
}
