package com.bll.core.bean.org.create;

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
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.bll.core.util.TestConstant.DATE_OF_CREATE;
import static com.bll.core.util.TestConstant.NAME;
import static com.bll.core.util.TestUtil.getQuery;

@ExtendWith({DBUnitExtension.class})
public class CreateOrgBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = context.getBean("tenantSessionFactory", SessionFactory.class);
        dataSource = getOrgHikariDataSource();
        connectionHolder = dataSource::getConnection;
    }

    // Client beans

    @Test
    void organizationDaoBeanTest() {
        IOrganizationDao organizationDao = context.getBean(IOrganizationDao.class);
        Organization organization = new Organization();
        organization.setClientName(NAME);
        organization.setDateOfCreate(DATE_OF_CREATE);
        String getAllTableList = getQuery("org");
        //clean before
        cleanDbByDao(organizationDao, getAllTableList);
        //save
        organizationDao.saveEntity(organization);
        Assertions.assertFalse(isTableEmpty(organizationDao, getAllTableList));
        //get entity check
        Organization getFromDb =
                (Organization) getEntity(organizationDao, "org", "clientname", NAME);
        Assertions.assertEquals(NAME, getFromDb.getClientName());
        //update
        String updatedParam = "testUpdated";
        getFromDb.setClientName(updatedParam);
        organizationDao.updateEntity(getFromDb);
        Organization entityFromDb =
                (Organization) getEntity(organizationDao, "org", "clientname", updatedParam);
        Assertions.assertEquals(updatedParam, entityFromDb.getClientName());
        //clean after
        cleanDbByDao(organizationDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(organizationDao, getAllTableList));
    }

    @Test
    void tenantConfigurationDaoBeanTest() {
        ITenantConfigDao tenantConfigDao = context.getBean(ITenantConfigDao.class);
        TenantConfiguration tenantConfiguration = new TenantConfiguration();
        tenantConfiguration.setDateOfCreate(DATE_OF_CREATE);

        String getAllTableList = getQuery("tenant_configuration");
        //clean before
        cleanDbByDao(tenantConfigDao, getAllTableList);
        //save
        tenantConfigDao.saveEntity(tenantConfiguration);
        Assertions.assertFalse(isTableEmpty(tenantConfigDao, getAllTableList));
        //get entity check
        TenantConfiguration getFromDb =
                (TenantConfiguration) getEntity(tenantConfigDao, "tenant_configuration", "dateofcreate", DATE_OF_CREATE);
        Assertions.assertEquals(DATE_OF_CREATE, getFromDb.getDateOfCreate());
        //update
        getFromDb.setDateOfCreate(1693179505330L);
        tenantConfigDao.updateEntity(getFromDb);
        TenantConfiguration entityFromDb =
                (TenantConfiguration) getEntity(tenantConfigDao, "tenant_configuration", "dateofcreate", 1693179505330L);
        Assertions.assertEquals(1693179505330L, entityFromDb.getDateOfCreate());
        //clean after
        cleanDbByDao(tenantConfigDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(tenantConfigDao, getAllTableList));
    }

    @Test
    void  tenantDaoBeanTest() {
        ITenantDao tenantDao = context.getBean(ITenantDao.class);
        Tenant tenant = new Tenant();
        tenant.setName(NAME);
        tenant.setDateOfCreate(DATE_OF_CREATE);
        String getAllTableList = getQuery("tenant");
        //clean before
        cleanDbByDao(tenantDao, getAllTableList);
        //save
        tenantDao.saveEntity(tenant);
        Assertions.assertFalse(isTableEmpty(tenantDao, getAllTableList));
        //get entity check
        Tenant getFromDb =
                (Tenant) getEntity(tenantDao, "tenant", "name", NAME);
        Assertions.assertEquals(NAME, getFromDb.getName());
        //update
        String updatedParam = "testUpdated";
        getFromDb.setName(updatedParam);
        tenantDao.updateEntity(getFromDb);
        Tenant entityFromDb =
                (Tenant) getEntity(tenantDao, "tenant", "name", updatedParam);
        Assertions.assertEquals(updatedParam, entityFromDb.getName());
        //clean after
        cleanDbByDao(tenantDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(tenantDao, getAllTableList));
    }

    @Test
    void orgUserDaoBeanTest() {
        IOrgUserDao orgUserDao = context.getBean(IOrgUserDao.class);
        OrgUser orgUser = new OrgUser();
        orgUser.setUsername(NAME);
        orgUser.setEmail(NAME);
        orgUser.setPassword(NAME);

        orgUser.setDateOfCreate(DATE_OF_CREATE);
        String getAllTableList = getQuery("org_user");
        //clean before
        cleanDbByDao(orgUserDao, getAllTableList);
        //save
        orgUserDao.saveEntity(orgUser);
        Assertions.assertFalse(isTableEmpty(orgUserDao, getAllTableList));
        //get entity check
        OrgUser getFromDb =
                (OrgUser) getEntity(orgUserDao, "org_user", "username", NAME);
        Assertions.assertEquals(NAME, getFromDb.getUsername());
        //update
        String updatedParam = "testUpdated";
        getFromDb.setUsername(updatedParam);
        orgUserDao.updateEntity(getFromDb);
        OrgUser entityFromDb =
                (OrgUser) getEntity(orgUserDao, "org_user", "username", updatedParam);
        Assertions.assertEquals(updatedParam, entityFromDb.getUsername());
        //clean after
        cleanDbByDao(orgUserDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(orgUserDao, getAllTableList));
    }

}
