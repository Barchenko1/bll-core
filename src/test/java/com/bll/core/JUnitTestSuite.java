package com.bll.core;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Suite
@Execution(CONCURRENT)
@SelectPackages({
        "com.bll.core.bean.constant",
        "com.bll.core.bean.tenant.create",
        "com.bll.core.bean.tenant.update",
        "com.bll.core.bean.tenant.delete",
        "com.bll.core.bean.org.create",
        "com.bll.core.bean.transaction"})
class JUnitTestSuite {
}
