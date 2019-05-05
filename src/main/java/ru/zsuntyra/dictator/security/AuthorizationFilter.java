package ru.zsuntyra.dictator.security;

import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.ejb.AuthEJB;
import ru.zsuntyra.dictator.ejb.TokenEJB;

import javax.ejb.EJB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "authorizationFilter")
public class AuthorizationFilter extends HttpFilter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("", "/index.xhtml", "/index")));

    @EJB
    private TokenEJB tokenEJB;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String path = request.getRequestURI()
                    .substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            if (ALLOWED_PATHS.contains(path)) {
                chain.doFilter(request, response);
                return;
            }

            String token = (String) session.getAttribute(AuthEJB.TOKEN_ATTRIBUTE_NAME);
            if (!isTokenValid(token)) {
                // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Message.UNAUTHORIZED);
                response.sendRedirect(PathConfig.REDIRECT_TO_LOGIN);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isTokenValid(String token) {
        return tokenEJB.getAuthorizedUsers().containsKey(token);
    }

}
