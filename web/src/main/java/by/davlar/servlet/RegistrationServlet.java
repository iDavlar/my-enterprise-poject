package by.davlar.servlet;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.RoleDto;
import by.davlar.exceptions.ValidationException;
import by.davlar.service.RoleService;
import by.davlar.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JspHelper;
import utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final RoleService roleService = RoleService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(
                "roles",
                roleService.findAll().stream()
                        .map(RoleDto::getName)
                        .toList()
        );
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
        log.trace("Session {} went to {} page", req.getSession(), UrlPath.REGISTRATION);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthday(req.getParameter("birthday"))
                .telephone(req.getParameter("telephone"))
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .build();

        log.trace("Session {} tried to sing in {}", req.getSession(), userDto);
        try {
            userService.create(userDto);
            resp.sendRedirect(UrlPath.LOGIN);
            log.trace("Session {} signed new user in {}", req.getSession(), userDto);
        } catch (ValidationException e) {
            log.warn("Session {} failed to sign in as {} with errors {}", req.getSession(), userDto, e.getErrors());
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        } catch (ConstraintViolationException e) {
            log.warn(
                    "Session {} failed to sign in as {} with errors {}",
                    req.getSession(),
                    userDto,
                    e.getConstraintViolations()
            );
            req.setAttribute("errors", e.getConstraintViolations());
            doGet(req, resp);
        }
    }
}
