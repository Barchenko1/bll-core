package com.bll.core.service.post;

import com.core.im.tenant.dto.request.post.PostDto;
import com.core.im.tenant.modal.post.Post;
import com.cos.core.dao.post.IPostDao;
import com.cos.core.dto.IDtoEntityDao;
import com.cos.core.mapper.IDtoEntityMapper;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    private final ITransactionManager clientTransactionManager;
    private final IPostDao postDao;
    private final IDtoEntityMapper dtoEntityMapper;
    private final IDtoEntityDao dtoEntityDao;

    @Autowired
    public PostService(@Qualifier("clientTransactionManager")ITransactionManager clientTransactionManager,
                       IPostDao postDao,
                       IDtoEntityMapper dtoEntityMapper,
                       IDtoEntityDao dtoEntityDao) {
        this.clientTransactionManager = clientTransactionManager;
        this.postDao = postDao;
        this.dtoEntityMapper = dtoEntityMapper;
        this.dtoEntityDao = dtoEntityDao;
    }

    @Override
    public void createNewPost(PostDto postDto) {
        Post newPost = new Post();
        dtoEntityMapper.mapDtoToEntity(postDto, newPost, "postDtoBind");
        clientTransactionManager.useTransaction(newPost);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = dtoEntityDao.getDto("SELECT * FROM POST p where p.id='" + postDto.getPostId() +"'", Post.class);
        dtoEntityMapper.mapDtoToEntity(postDto, post, "postDtoBind");

        clientTransactionManager.useTransaction(post);
    }

    @Override
    public void deletePost(PostDto postDto) {
        Post post = dtoEntityDao.getDto("SELECT * FROM POST p where p.id='" + postDto.getPostId() +"'", Post.class);
        postDao.deleteEntity(post);
    }


}
