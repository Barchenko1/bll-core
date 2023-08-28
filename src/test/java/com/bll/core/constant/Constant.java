package com.bll.core.constant;

public interface Constant {
    String H2_DRIVER = "org.h2.Driver";
    String H2_DB_URL = "jdbc:h2:mem:test";
    String H2_USERNAME = "sa";
    String H2_PASSWORD = "sa";
    String H2_DIALECT = "org.hibernate.dialect.H2Dialect";

    String POSTGRES_DRIVER = "org.postgresql.Driver";
    String POSTGRES_TENANT_DB_URL = "jdbc:postgresql://127.0.0.1:5432/test_db";
    String POSTGRES_TENANT_USERNAME = "sa";
    String POSTGRES_TENANT_PASSWORD = "sa";
    String POSTGRES_ORG_DB_URL = "jdbc:postgresql://127.0.0.1:5433/tenant_test_db";
    String POSTGRES_ORG_USERNAME = "postgres";
    String POSTGRES_ORG_PASSWORD = "password";

    String POSTGRES_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";

}
