package by.davlar.spring.http.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AttributeHelper {
    public final SessionAttributesHandler SESSION = new SessionAttributesHandler();
    public final ModelAttributesHandler MODEL = new ModelAttributesHandler();


    public class SessionAttributesHandler {
        public final String USER = "user";
        public final String ALL_USERS = "users";
    }

    public class ModelAttributesHandler {
        public final String ERRORS = "errors";
        public final String ROLES = "roles";
        public final String USER = "user";
        public final String USERS = "users";
        public final String LOGIN = "login";
        public final String PASSWORD = "password";
    }
}
