package by.davlar.spring.http.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String LOGOUT = "/logout";
    public static final String ALL_USERS = "/users";
    public static final String USER_ID = "/user/{id}";
    public static final String USER = "/user/";
}
