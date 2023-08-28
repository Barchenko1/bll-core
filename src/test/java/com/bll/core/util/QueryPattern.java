package com.bll.core.util;

public interface QueryPattern {
    String SELECT_ALL = "SELECT * FROM %s e;";
    String SELECT_BY_PARAM = "SELECT * FROM %s e where e.%s = '%s'";
}
