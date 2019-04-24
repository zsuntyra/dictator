package ru.zsuntyra.dictator.security;

import ru.zsuntyra.dictator.config.ErrorMessage;
import ru.zsuntyra.dictator.ejb.TokenEJB;

import javax.ejb.EJB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter extends HttpFilter {

    private static final String TOKEN_NAME = "Token";

    @EJB
    private TokenEJB tokenEJB;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String token = (String) session.getAttribute(TOKEN_NAME);
            if (!isTokenValid(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.UNAUTHORIZED);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isTokenValid(String token) {
        return tokenEJB.getAuthorizedUsers().containsKey(token);
    }

}
