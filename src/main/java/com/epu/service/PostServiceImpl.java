package com.epu.service;

import com.epu.models.Post;
import com.epu.models.User;
import com.epu.repository.PostRepository;
import com.epu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {

        User user  = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setId(post.getId());
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getUser().getId() != user.getId()){
            throw new Exception("Bạn không thể xóa bài đăng của người dùng khác");
        }
        postRepository.delete(post);
        return "Xóa bài đăng thành công";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> opt = postRepository.findById(postId);
        if (opt.isEmpty()){
            throw new Exception("Không tìm thấy bài đăng có id là :" + postId);
        }
        return opt.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
        }
        else user.getSavedPost().add(post);
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getLiked().contains(user)){
            post.getLiked().remove(user);
        }else {
            post.getLiked().add(user);
        }

        return postRepository.save(post);
    }
}
