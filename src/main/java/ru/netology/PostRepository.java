package ru.netology;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Post getById(long id) {
        return posts.get(id);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = counter.getAndIncrement();
            post.setId(id);
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}