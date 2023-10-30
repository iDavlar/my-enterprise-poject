package by.davlar.servlet;

import by.davlar.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JspHelper;
import utils.UrlPath;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(UrlPath.ALL_USERS)
public class AllUsersServlet extends HttpServlet {

    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        req.setAttribute("usersList", userService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("all_users")).forward(req, resp);
    }
}
