package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.bean.MessageBean;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.ejb.ProfileEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ManagedBean(name = "profileBean", eager = true)
@SessionScoped
public class ProfileBean implements Serializable {

    @EJB
    private ProfileEJB profileEJB;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    public User getInfoUser(){
       return profileEJB.getCurrentUser();
    }

    public int getUserLVL(){
        return profileEJB.getUserLevel();
    }

    public int getAvailablePointsAmount(){
        return profileEJB.getAvailablePointsAmount();
    }
}
