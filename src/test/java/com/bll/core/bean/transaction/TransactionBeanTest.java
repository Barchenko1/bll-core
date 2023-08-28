package com.bll.core.bean.transaction;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.core.im.org.modal.OrgUser;
import com.core.im.org.modal.Organization;
import com.core.im.org.modal.Tenant;
import com.core.im.org.modal.TenantConfiguration;
import com.cos.core.dao.org.IOrgUserDao;
import com.cos.core.dao.org.IOrganizationDao;
import com.cos.core.dao.org.ITenantConfigDao;
import com.cos.core.dao.org.ITenantDao;
import com.cos.core.transaction.ITransactionManager;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

@ExtendWith({DBUnitExtension.class})
public class TransactionBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    private static ITransactionManager transactionManager;

    @BeforeAll
    public static void getSessionFactory() {
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = context.getBean("tenantSessionFactory", SessionFactory.class);
        transactionManager = context.getBean("transactionManager", ITransactionManager.class);

        dataSource = getOrgHikariDataSource();
        connectionHolder = dataSource::getConnection;
    }

    @Test
    void organizationUserTransactionTest() {
        IOrganizationDao organizationDao = context.getBean(IOrganizationDao.class);
        IOrgUserDao orgUserDao = context.getBean(IOrgUserDao.class);
        ITenantDao tenantDao = context.getBean(ITenantDao.class);
        ITenantConfigDao tenantConfigDao = context.getBean(ITenantConfigDao.class);

        Organization organization = new Organization();
        organization.setClientName("test");
        organization.setDateOfCreate(time);

        OrgUser orgUser = new OrgUser();
        orgUser.setUsername("test");
        orgUser.setEmail("test");
        orgUser.setPassword("test");
        orgUser.setOrganizationList(Arrays.asList(organization));


        TenantConfiguration tenantConfiguration = new TenantConfiguration();
        tenantConfiguration.setDateOfCreate(time);

        Tenant tenant = new Tenant();
        tenant.setName("test");
        tenant.setDateOfCreate(time);
        tenant.setOrganizationList(Arrays.asList(organization));
        tenant.setTenantConfigurationList(Arrays.asList(tenantConfiguration));

        cleanDbByDao(tenantDao, getQuery("tenant"));
        cleanDbByDao(orgUserDao, getQuery("org_user"));
        cleanDbByDao(organizationDao, getQuery("org"));
        cleanDbByDao(tenantConfigDao, getQuery("tenant_configuration"));

        List<?> list = Arrays.asList(organization, orgUser, tenant, tenantConfiguration);
        transactionManager.useTransaction(list);
        Assertions.assertFalse(isTableEmpty(organizationDao, getQuery("org")));


        cleanDbByDao(tenantDao, getQuery("tenant"));
        cleanDbByDao(orgUserDao, getQuery("org_user"));
        cleanDbByDao(organizationDao, getQuery("org"));
        cleanDbByDao(tenantConfigDao, getQuery("tenant_configuration"));

        Assertions.assertTrue(isTableEmpty(orgUserDao, getQuery("org_user")));

    }
}
