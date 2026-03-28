package ru.netology;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/posts/*")   // если используете аннотации вместо web.xml
public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() throws ServletException {
        // Сканируем пакет ru.netology.demo для поиска аннотированных компонентов
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.netology.demo");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            controller.all(req, resp);
        } else if (path.matches("/\\d+")) {
            controller.getById(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controller.save(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path != null && path.matches("/\\d+")) {
            controller.removeById(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
