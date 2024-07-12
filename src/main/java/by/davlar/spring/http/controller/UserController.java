package by.davlar.spring.http.controller;

import by.davlar.spring.dto.UserCreateEditDto;
import by.davlar.spring.dto.UserReadDto;
import by.davlar.spring.http.utils.TemplatePath;
import by.davlar.spring.http.utils.UrlPath;
import by.davlar.spring.service.RoleService;
import by.davlar.spring.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static by.davlar.spring.http.utils.AttributeHelper.*;
import static by.davlar.spring.http.utils.PathHelper.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping(UrlPath.ALL_USERS)
    public String findAll(HttpSession httpSession, Model model) {
        return Optional.ofNullable(httpSession.getAttribute(SESSION.USER))
                .map(value -> {
                    model.addAttribute(MODEL.USERS, userService.findAll());
                    return TemplatePath.ALL_USERS;
                })
                .orElse(redirect(UrlPath.LOGIN));
//        model.addAttribute(MODEL.USERS, userService.findAll());
//        return TemplatePath.ALL_USERS;
    }

    @GetMapping(UrlPath.USER_ID)
    public String findById(@PathVariable("id") Integer id,
                           HttpSession httpSession,
                           Model model) {
        return Optional.ofNullable(httpSession.getAttribute(SESSION.USER))
                .map(user -> userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(user -> {
                    model.addAttribute(MODEL.USER, user);
                    model.addAttribute(MODEL.ROLES, roleService.findAll());
                    return TemplatePath.USER;
                })
                .orElse(redirect(UrlPath.LOGIN));
    }

    @GetMapping(UrlPath.LOGIN)
    public String login(HttpSession session) {
        return Optional.ofNullable(session.getAttribute(SESSION.USER))
                .map(value -> {
                    return redirect(UrlPath.USER + ((UserReadDto) value).getId());
                })
                .orElse(TemplatePath.LOGIN);
    }

    @PostMapping(UrlPath.LOGIN)
    public String loginPost(@RequestParam String login,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        return userService.login(login, password)
                .map(userReadDto -> {
                    session.setAttribute(SESSION.USER, userReadDto);
                    if (userReadDto.getRole().getIsAdmin()) {
                        return redirect(UrlPath.ALL_USERS);
                    }
                    return redirect(UrlPath.USER + userReadDto.getId());
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute(MODEL.LOGIN, login);
                    redirectAttributes.addFlashAttribute(MODEL.PASSWORD, password);
                    return redirect(UrlPath.LOGIN);
                });

    }

    @GetMapping(UrlPath.REGISTRATION)
    public String registration(HttpSession session, Model model) {
        return Optional.ofNullable(session.getAttribute(SESSION.USER))
                .map(value -> {
                    model.addAttribute(MODEL.USERS, userService.findAll());
                    return redirect(UrlPath.USER + ((UserReadDto) value).getId());
                })
                .orElseGet(() -> {
                    model.addAttribute(MODEL.ROLES, roleService.findAll());
                    model.addAttribute(MODEL.USER, UserCreateEditDto.newEmptyObject());
                    return TemplatePath.REGISTRATION;
                });
    }

    @PostMapping(UrlPath.REGISTRATION)
    public String create(@ModelAttribute @Validated UserCreateEditDto userCreateEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(MODEL.USER, userCreateEditDto);
            redirectAttributes.addFlashAttribute(MODEL.ERRORS, bindingResult.getAllErrors());
            return redirect(UrlPath.REGISTRATION);
        }
        var userReadDto = userService.create(userCreateEditDto);
        return redirect(UrlPath.USER + userReadDto.orElseThrow().getId());
    }

    @PostMapping("/user/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated UserCreateEditDto userCreateEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(MODEL.USER, userCreateEditDto);
            redirectAttributes.addFlashAttribute(MODEL.ERRORS, bindingResult.getAllErrors());
            return redirect(UrlPath.USER + id);
        }

        var userReadDto = userService.update(id, userCreateEditDto);
        return redirect(UrlPath.USER + userReadDto.getId());
    }

    @PostMapping("/user/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return redirect(UrlPath.ALL_USERS);
    }
}
