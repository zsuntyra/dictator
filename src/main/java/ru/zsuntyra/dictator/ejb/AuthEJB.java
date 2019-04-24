package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.domain.User;
import ru.zsuntyra.dictator.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Getter
@Setter
@Stateless
public class AuthEJB {

    @EJB
    private TokenEJB tokenEJB;

    @Inject
    private UserRepository userRepository;

    private static final String TOKEN_ATTRIBUTE_NAME = "AuthToken";

    public boolean login(String username, String password) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        if (user.getPassword().equals(encodeHexString(password.getBytes()))) {
            HttpSession session = request.getSession(true);
            if (tokenEJB.getAuthorizedUsers().containsValue(user)) {
                String foundToken = tokenEJB.findToken(user);
                tokenEJB.getAuthorizedUsers().remove(foundToken);
            }
            String token = generateToken();
            tokenEJB.getAuthorizedUsers().put(token, user);
            session.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
            return true;
        }

        return false;
    }

    // Returns error message or null if no error happened
    public String signUp(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return Message.USERNAME_ALREADY_TAKEN;
        }

        if (username.length() < 3) {
            return Message.USERNAME_TOO_SHORT;
        }
        if (password.length() < 3) {
            return Message.PASSWORD_TOO_SHORT;
        }

        User user = new User(username, encodeHexString(password.getBytes()));
        userRepository.create(user);
        return null;
    }

    public void logout() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(TOKEN_ATTRIBUTE_NAME) != null) {
            session.removeAttribute(TOKEN_ATTRIBUTE_NAME);
        }
    }

    private String encodeHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private String generateToken() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

}
