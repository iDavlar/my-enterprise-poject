package by.davlar.servlet;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.RoleDto;
import by.davlar.service.RoleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JspHelper;

import java.io.IOException;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final RoleService roleService = RoleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(
                "roles",
                roleService.findAll().stream()
                        .map(RoleDto::getName)
                        .toList()
        );
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
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


    }
}
