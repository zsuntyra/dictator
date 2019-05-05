package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.ejb.AuthEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@Getter
@Setter
@ManagedBean(name = "authBean", eager = true)
@SessionScoped
public class AuthBean implements Serializable {

    @EJB
    private AuthEJB authEJB;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    private String username;
    private String password;

    public String signIn() {
        if (username != null && password != null && authEJB.login(username, password)) {
            messageBean.setCurrentMessage(null);
            return PathConfig.REDIRECT_TO_GAME;
        } else {
            messageBean.setCurrentMessage(Message.INVALID_CREDENTIALS);
            return null;
        }
    }

    public void signUp() {
        if (username != null && password != null) {
            String errorMessage = authEJB.signUp(username, password);
            if (errorMessage == null) {
                messageBean.setCurrentMessage(Message.REGISTER_SUCCESS);
            } else {
                messageBean.setCurrentMessage(errorMessage);
            }
        }
    }

    public String logout() {
        authEJB.logout();
        return PathConfig.REDIRECT_TO_LOGIN;
    }

}
