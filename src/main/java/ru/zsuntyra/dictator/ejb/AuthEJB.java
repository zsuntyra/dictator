package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@Stateless
public class AuthEJB {

    public String login() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();

        // TODO
        return null;
    }

    public String signUp() throws NoSuchAlgorithmException {
        MessageDigest passwordDigest = MessageDigest.getInstance("SHA-256");
        passwordDigest.reset();
        passwordDigest.digest();
        // TODO
    }

    public String logout() {
        // TODO
        return null;
    }

    private String hexEncode(Byte[] bytes) {
        char[] hexadecimal = {0, 1}; // FIXME

    }

}
