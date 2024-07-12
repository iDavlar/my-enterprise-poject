package by.davlar.spring.servlet;

import by.davlar.spring.http.utils.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(UrlPath.LOGOUT)
public class LogoutServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("Session {} was invalidated", req.getSession());
        req.getSession().invalidate();
        resp.sendRedirect(UrlPath.LOGIN);
    }
}
