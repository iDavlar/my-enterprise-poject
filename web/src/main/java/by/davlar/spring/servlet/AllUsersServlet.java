package by.davlar.spring.servlet;

import by.davlar.spring.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import by.davlar.spring.utils.JspHelper;
import by.davlar.spring.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ALL_USERS)
@RequiredArgsConstructor
public class AllUsersServlet extends HttpServlet {

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        req.setAttribute("usersList", userService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("all_users")).forward(req, resp);
        log.trace("Session {} went to {} page", req.getSession(), UrlPath.ALL_USERS);
    }
}
