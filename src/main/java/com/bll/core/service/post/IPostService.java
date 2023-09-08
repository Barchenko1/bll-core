package com.bll.core.service.post;

import com.core.im.tenant.dto.request.post.PostDto;

public interface IPostService {
    void createNewPost(PostDto postDto);
}
