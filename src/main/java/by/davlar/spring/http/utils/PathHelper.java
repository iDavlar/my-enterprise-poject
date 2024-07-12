package by.davlar.spring.http.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathHelper {
    public String redirect(String path) {
        return "redirect:" + ((path.startsWith("/")) ? "" : "/") + path;
    }
}
