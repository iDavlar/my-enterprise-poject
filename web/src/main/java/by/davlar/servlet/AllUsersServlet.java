package by.davlar.servlet;

import by.davlar.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JspHelper;
import utils.UrlPath;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(UrlPath.ALL_USERS)
public class AllUsersServlet extends HttpServlet {

    private static final UserService userService = UserService.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        req.setAttribute("usersList", userService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("all_users")).forward(req, resp);
        log.trace("Session {} went to {} page", req.getSession(), UrlPath.ALL_USERS);
    }
}
