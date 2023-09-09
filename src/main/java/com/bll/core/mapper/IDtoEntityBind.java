package com.bll.core.mapper;

import java.util.Optional;

public interface IDtoEntityBind {
    String getFolderPath();
    Optional<String> get(String field);
    void setKey(String key);

}
