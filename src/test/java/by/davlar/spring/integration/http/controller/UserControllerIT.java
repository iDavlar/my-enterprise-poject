package by.davlar.spring.integration.http.controller;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.UserReadDto;
import by.davlar.spring.http.utils.TemplatePath;
import by.davlar.spring.http.utils.UrlPath;
import by.davlar.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.davlar.spring.dto.UserCreateEditDto.Fields.*;
import static by.davlar.spring.http.utils.AttributeHelper.MODEL;
import static by.davlar.spring.http.utils.AttributeHelper.SESSION;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserControllerIT {
    private final MockMvc mockMvc;
    private final UserService userService;

    private static final Integer ADMIN_ID = 1;
    private static final String ADMIN_LOGIN = "Davlar";
    private static final String WRONG_ADMIN_PASSWORD = "";
    private static final Integer USER_ID = 2;
    private static final Integer NO_DEPENDENCY_USER_ID = 6;

    private static final String USER_UPDATE_URL = "/user/" + USER_ID + "/update";

    private static final String USER_DELETE_URL = "/user/" + NO_DEPENDENCY_USER_ID + "/delete";
    private static final String USER_URL_1 = UrlPath.USER + ADMIN_ID;

    private static final String USER_URL_2 = UrlPath.USER + USER_ID;
    private static final String USER_URL_7 = UrlPath.USER + "7";

    @Test
    void getFindAll_redirectToLogin() throws Exception {
        mockMvc.perform(get(UrlPath.ALL_USERS))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(UrlPath.LOGIN));
    }

    @Test
    void getFindAll_access() throws Exception {
        var user = userService.findById(ADMIN_ID).orElseThrow();
        mockMvc.perform(get(UrlPath.ALL_USERS)
                        .sessionAttr(SESSION.USER, user))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(TemplatePath.ALL_USERS))
                .andExpect(model().attributeExists(MODEL.USERS))
                .andExpect(model().attribute(MODEL.USERS, IsCollectionWithSize.hasSize(6)));
    }

    @Test
    void getFindById_redirectToLogin() throws Exception {
        mockMvc.perform(get(USER_URL_1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(UrlPath.LOGIN));
    }

    @Test
    void getFindById_findId1_access() throws Exception {
        var user = userService.findById(ADMIN_ID).orElseThrow();
        mockMvc.perform(get(USER_URL_1)
                        .sessionAttr(SESSION.USER, user))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(TemplatePath.USER))
                .andExpect(model().attributeExists(MODEL.USER, MODEL.ROLES));
    }

    @Test
    void getFindById_findId6_failAndThrowResponseStatusException() throws Exception {
        var user = userService.findById(ADMIN_ID).orElseThrow();
        mockMvc.perform(get(USER_URL_7)
                        .sessionAttr(SESSION.USER, user))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getLogin_redirectToUserId() throws Exception {
        Optional<UserReadDto> user = userService.findById(ADMIN_ID);
        mockMvc.perform(get(UrlPath.LOGIN)
                        .sessionAttr(SESSION.USER, user.orElseThrow()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(USER_URL_1));
    }

    @Test
    void getLogin_accessToLogin() throws Exception {
        mockMvc.perform(get(UrlPath.LOGIN))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(TemplatePath.LOGIN));
    }

    @Test
    void postLogin_adminLogin_successAndRedirectToAllUsers() throws Exception {
        var user = userService.findById(ADMIN_ID).orElseThrow();
        mockMvc.perform(post(UrlPath.LOGIN)
                        .param(login, user.getLogin())
                        .param(password, user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(UrlPath.ALL_USERS))
                .andExpect(request().sessionAttribute(SESSION.USER, user));
    }

    @Test
    void postLogin_userLogin_successAndRedirectToUserId() throws Exception {
        var user = userService.findById(USER_ID).orElseThrow();
        mockMvc.perform(post(UrlPath.LOGIN)
                        .param(login, user.getLogin())
                        .param(password, user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(USER_URL_2))
                .andExpect(request().sessionAttribute(SESSION.USER, user));
    }

    @Test
    void postLogin_adminLogin_failAndRedirectToLogin() throws Exception {
        mockMvc.perform(post(UrlPath.LOGIN)
                        .param(login, ADMIN_LOGIN)
                        .param(password, WRONG_ADMIN_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(UrlPath.LOGIN))
                .andExpect(request().sessionAttributeDoesNotExist(SESSION.USER))
                .andExpect(flash().attribute(MODEL.LOGIN, ADMIN_LOGIN));
    }

    @Test
    void getRegistration_accessToRegistration() throws Exception {
        mockMvc.perform(get(UrlPath.REGISTRATION))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name(TemplatePath.REGISTRATION),
                        model().attributeExists(
                                MODEL.ROLES,
                                MODEL.USER
                        )
                );
    }

    @Test
    void getRegistration_redirectToUserId() throws Exception {
        var user = userService.findById(ADMIN_ID).orElseThrow();
        mockMvc.perform(get(UrlPath.REGISTRATION)
                        .sessionAttr(SESSION.USER, user))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(USER_URL_1)
                );
    }

    @Test
    void postRegistration_wrongData_failAndRedirectToRegistration() throws Exception {
        mockMvc.perform(post(UrlPath.REGISTRATION)
                        .params(createEditUserParams(false)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(UrlPath.REGISTRATION),
                        flash().attributeExists(
                                MODEL.USER,
                                MODEL.ERRORS
                        ),
                        flash().attribute(MODEL.ERRORS, Matchers.hasSize(3))
                );
    }

    @Test
    void postRegistration_correctData_successAndRedirectToUserId() throws Exception {
        mockMvc.perform(post(UrlPath.REGISTRATION)
                        .params(createEditUserParams(true)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern(UrlPath.USER + "{\\d+}"),
                        request().sessionAttribute(SESSION.USER, Matchers.notNullValue())
                );
    }

    @Test
    void postUpdate_changeUserFirstName_successAndRedirectToUserId() throws Exception {
        var user = userService.findById(USER_ID).orElseThrow();
        var editUserParams = createEditUserParams(user);
        editUserParams.set(firstName, "Test1234567");
        mockMvc.perform(post(USER_UPDATE_URL)
                        .params(editUserParams))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(USER_URL_2)
                );
    }

    @Test
    void postDelete_changeUserFirstName_successAndRedirectToUserId() throws Exception {
        mockMvc.perform(post(USER_DELETE_URL))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl(UrlPath.ALL_USERS)
                );
    }

    private MultiValueMap<String, String> createEditUserParams(boolean correct) {
        Map<String, List<String>> params = new HashMap<>();
        params.put(firstName, List.of("Test"));
        params.put(lastName, List.of("Test123"));
        params.put(birthday, List.of("2000-01-01"));
        params.put(login, List.of(correct ? "Test4321" : ""));
        params.put(password, List.of("1234567"));
        params.put(telephone, List.of(""));
        params.put(role, List.of("USER"));
        return new MultiValueMapAdapter<>(params);
    }

    private MultiValueMap<String, String> createEditUserParams(UserReadDto dto) {
        Map<String, List<String>> params = new HashMap<>();
        params.put(firstName, List.of(dto.getFirstName()));
        params.put(lastName, List.of(dto.getLastName()));
        params.put(birthday, List.of(dto.getBirthday().toString()));
        params.put(login, List.of(dto.getLogin()));
        params.put(password, List.of(dto.getPassword()));
        params.put(telephone, List.of(dto.getTelephone()));
        params.put(role, List.of(dto.getRole().getName()));
        return new MultiValueMapAdapter<>(params);
    }

}
