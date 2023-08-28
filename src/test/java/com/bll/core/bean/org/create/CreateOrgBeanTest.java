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
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.dbunit.DatabaseUnitException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

@ExtendWith({DBUnitExtension.class})
@DbUnitConfiguration(databaseConnection = "dataSource")
public class CreateOrgBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() throws SQLException, DatabaseUnitException {
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = context.getBean("tenantSessionFactory", SessionFactory.class);
        dataSource = getOrgHikariDataSource();
        connectionHolder = dataSource::getConnection;
    }

    // Client beans

    @Test
    void organizationDaoBeanTest() {
        IOrganizationDao<Organization> organizationDao = context.getBean(IOrganizationDao.class);
        Organization organization = new Organization();
        organization.setClientName("test");
        organization.setDateOfCreate(time);
        String getAllTableList = getQuery("org");
        //clean before
        cleanDbByDao(organizationDao, getAllTableList);
        //save
        organizationDao.saveEntity(organization);
        Assertions.assertFalse(isTableEmpty(organizationDao, getAllTableList));
        //get entity check
        Organization getFromDb =
                (Organization) getEntity(organizationDao, "org", "clientname", "test");
        Assertions.assertEquals("test", getFromDb.getClientName());
        //update
        String updatedParam = "testUpdated";
        getFromDb.setClientName(updatedParam);
        organizationDao.updateEntity(getFromDb);
        Organization organizationUpdatedFromDB =
                (Organization) getEntity(organizationDao, "org", "clientname", updatedParam);
        Assertions.assertEquals(updatedParam, organizationUpdatedFromDB.getClientName());
        //clean after
        cleanDbByDao(organizationDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(organizationDao, getAllTableList));
    }

    @Test
    void tenantConfigurationDaoBeanTest() {
        ITenantConfigDao<TenantConfiguration> tenantConfigDao = context.getBean(ITenantConfigDao.class);
        TenantConfiguration tenantConfiguration = new TenantConfiguration();
        tenantConfiguration.setDateOfCreate(time);

        String getAllTableList = getQuery("tenant_configuration");
        //clean before
        cleanDbByDao(tenantConfigDao, getAllTableList);
        //save
        tenantConfigDao.saveEntity(tenantConfiguration);
        Assertions.assertFalse(isTableEmpty(tenantConfigDao, getAllTableList));
        //get entity check
        TenantConfiguration getFromDb =
                (TenantConfiguration) getEntity(tenantConfigDao, "tenant_configuration", "dateofcreate", time);
        Assertions.assertEquals(time, getFromDb.getDateOfCreate());
        //update
        getFromDb.setDateOfCreate(1693179505330L);
        tenantConfigDao.updateEntity(getFromDb);
        TenantConfiguration organizationUpdatedFromDB =
                (TenantConfiguration) getEntity(tenantConfigDao, "tenant_configuration", "dateofcreate", 1693179505330L);
        Assertions.assertEquals(1693179505330L, organizationUpdatedFromDB.getDateOfCreate());
        //clean after
        cleanDbByDao(tenantConfigDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(tenantConfigDao, getAllTableList));
    }

    @Test
    void  tenantDaoBeanTest() {
        ITenantDao<Tenant> tenantDao = context.getBean(ITenantDao.class);
        Tenant tenant = new Tenant();
        tenant.setName("test");
        tenant.setDateOfCreate(time);
        String getAllTableList = getQuery("tenant");
        //clean before
        cleanDbByDao(tenantDao, getAllTableList);
        //save
        tenantDao.saveEntity(tenant);
        Assertions.assertFalse(isTableEmpty(tenantDao, getAllTableList));
        //get entity check
        Tenant getFromDb =
                (Tenant) getEntity(tenantDao, "tenant", "name", "test");
        Assertions.assertEquals("test", getFromDb.getName());
        //update
        String updatedParam = "testUpdated";
        getFromDb.setName(updatedParam);
        tenantDao.updateEntity(getFromDb);
        Tenant organizationUpdatedFromDB =
                (Tenant) getEntity(tenantDao, "tenant", "name", updatedParam);
        Assertions.assertEquals(updatedParam, organizationUpdatedFromDB.getName());
        //clean after
        cleanDbByDao(tenantDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(tenantDao, getAllTableList));
    }

    @Test
    void orgUserDaoBeanTest() {
        IOrgUserDao<OrgUser> orgUserDao = context.getBean(IOrgUserDao.class);
        OrgUser orgUser = new OrgUser();
        orgUser.setUsername("test");
        orgUser.setEmail("test");
        orgUser.setPassword("test");

        orgUser.setDateOfCreate(time);
        String getAllTableList = getQuery("org_user");
        //clean before
        cleanDbByDao(orgUserDao, getAllTableList);
        //save
        orgUserDao.saveEntity(orgUser);
        Assertions.assertFalse(isTableEmpty(orgUserDao, getAllTableList));
        //get entity check
        OrgUser getFromDb =
                (OrgUser) getEntity(orgUserDao, "org_user", "username", "test");
        Assertions.assertEquals("test", getFromDb.getUsername());
        //update
        String updatedParam = "testUpdated";
        getFromDb.setUsername(updatedParam);
        orgUserDao.updateEntity(getFromDb);
        OrgUser organizationUpdatedFromDB =
                (OrgUser) getEntity(orgUserDao, "org_user", "username", updatedParam);
        Assertions.assertEquals(updatedParam, organizationUpdatedFromDB.getUsername());
        //clean after
        cleanDbByDao(orgUserDao, getAllTableList);
        Assertions.assertTrue(isTableEmpty(orgUserDao, getAllTableList));
    }

}
