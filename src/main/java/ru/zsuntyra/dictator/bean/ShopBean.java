package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.ejb.ShopEJB;

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
@ManagedBean(name = "shopBean", eager = true)
@SessionScoped
public class ShopBean implements Serializable {

    @EJB
    private ShopEJB ShopEJB;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    private Associate associate;

    public List<Associate> getAllAssociates(){
        return ShopEJB.getAllAssociates();
    }

    public void buyAssociate(Associate associate){
        ShopEJB.buyAssociate(associate.getId());
    }

    public List<Avatar>  getAllAvatars(){
        return  ShopEJB.getAllAvatars();
    }
}
