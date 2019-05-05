package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.bean.MessageBean;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.ejb.AuthEJB;
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

    @EJB
    private AuthEJB authEJB;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    private Associate associate;
    private String name;
    private int armyPoints = 0;
    private int powerPoints = 0;
    private int peoplePoints = 0;
    private int policePoints = 0;
    private int humanRightDefendersPoints = 0;
    private int moneyPoints = 0;

    public User getInfoUser(){
       return authEJB.getAuthorizedUser();
    }

    public int getUserLVL(){
        return profileEJB.getUserLevel();
    }

    public int getAvailablePointsAmount(){
        return profileEJB.getAvailablePointsAmount();
    }

    public void createAssociate() {
        associate = new Associate();
        associate.setName(name);
        associate.setArmyCoefficient(armyPoints);
        associate.setHumanRightDefendersCoefficient(humanRightDefendersPoints);
        associate.setPeopleCoefficient(peoplePoints);
        associate.setPoliceCoefficient(policePoints);
        associate.setPowerCoefficient(powerPoints);
        associate.setMoneyCoefficient(moneyPoints);
        profileEJB.createAssociate(associate);
    }
}
