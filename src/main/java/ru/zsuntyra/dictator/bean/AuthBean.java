package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Getter
@Setter
@ManagedBean
@SessionScoped
public class AuthBean implements Serializable {

    private String username;
    private String password;

}
