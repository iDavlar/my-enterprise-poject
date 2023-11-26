package by.davlar.servlet;

import by.davlar.dto.UserDto;
import by.davlar.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JspHelper;
import utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("Session {} went to {} page", req.getSession(), UrlPath.LOGIN);
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        log.trace("Session {} tried login ({}, {})", req.getSession(), login, password);

        userService.login(login, password)
                .ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp),
                        () -> onLoginFail(req, resp));
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        resp.sendRedirect(UrlPath.LOGIN + "?error&login=" + login);
        log.warn("Session {} failed login ({}, {})", req.getSession(), login, req.getParameter("password"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect(UrlPath.ALL_USERS);
        log.trace("Session {} was authorized as {}", req.getSession(), userDto);
    }
}
