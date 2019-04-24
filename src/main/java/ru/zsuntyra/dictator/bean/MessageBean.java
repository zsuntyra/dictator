package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@Getter
@Setter
@ManagedBean(name = "messageBean")
@SessionScoped
public class MessageBean implements Serializable {

    private String currentMessage;

}
