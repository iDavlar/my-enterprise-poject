package by.davlar.spring.http.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    private static final String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";

    public String getPath(String jspName) {
        return JSP_FORMAT.formatted(jspName);
    }
}
