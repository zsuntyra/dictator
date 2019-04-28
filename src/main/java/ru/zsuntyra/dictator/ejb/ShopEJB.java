package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.Associate;
import ru.zsuntyra.dictator.domain.User;
import ru.zsuntyra.dictator.repository.AssociateRepository;
import ru.zsuntyra.dictator.repository.UserRepository;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Getter
@Setter
@Stateless
public class ShopEJB {

    @Inject
    private AssociateRepository associateRepository;

    @Inject
    private UserRepository userRepository;

    public List<Associate> getAllAssociates() {
        return associateRepository.findAll();
    }

    public boolean buyAssociate(long associateId) {
        User customer = getUserFromContext();
        Associate product = associateRepository.findById(associateId);

        if (customer.getAssociates().contains(product) || customer.getMoney() < product.getCost()) {
            return false;
        }

        product.getUsers().add(customer);
        customer.setMoney(customer.getMoney() - product.getCost());
        customer.getAssociates().add(product);

        userRepository.update(customer);
        // FIXME Should I update the product ?
        return true;
    }

    private User getUserFromContext() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return userRepository.findByUsername(context.getUserPrincipal().getName());
    }

}
