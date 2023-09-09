package com.bll.core.service.post;

import com.bll.core.mapper.IDtoEntityMapper;
import com.core.im.tenant.dto.request.post.PostDto;
import com.core.im.tenant.modal.post.Post;
import com.cos.core.dao.post.IPostDao;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    private final ITransactionManager clientTransactionManager;
    private final IPostDao postDao;
    private final IDtoEntityMapper dtoEntityMapper;

    @Autowired
    public PostService(@Qualifier("clientTransactionManager")ITransactionManager clientTransactionManager,
                       IPostDao postDao,
                       IDtoEntityMapper dtoEntityMapper) {
        this.clientTransactionManager = clientTransactionManager;
        this.postDao = postDao;
        this.dtoEntityMapper = dtoEntityMapper;
    }

    @Override
    public void createNewPost(PostDto postDto) {
        Post newPost = new Post();
        dtoEntityMapper.mapDtoToEntity(postDto, newPost, "postDtoBind");
        clientTransactionManager.useTransaction(newPost);
    }

}
