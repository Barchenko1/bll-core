package com.bll.core.service.post;

import com.core.im.tenant.dto.request.post.PostDto;
import com.core.im.tenant.modal.post.Post;

public interface IPostService {
    void createNewPost(PostDto postDto);
    void updatePost(PostDto postDto);
    void deletePost(PostDto postDto);
}
