package ru.netology;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PostController {
    private final PostService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Post> posts = service.all();
        sendJson(resp, posts);
    }

    public void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getPathInfo().substring(1));
        Post post = service.getById(id);
        if (post == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            sendJson(resp, post);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Post post = readJson(req, Post.class);
        Post saved = service.save(post);
        sendJson(resp, saved);
    }

    public void removeById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getPathInfo().substring(1));
        service.removeById(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private void sendJson(HttpServletResponse resp, Object obj) throws IOException {
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), obj);
    }

    private <T> T readJson(HttpServletRequest req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.getReader(), clazz);
    }
}