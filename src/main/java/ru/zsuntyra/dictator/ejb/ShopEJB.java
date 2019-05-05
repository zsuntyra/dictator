package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.Associate;
import ru.zsuntyra.dictator.domain.Avatar;
import ru.zsuntyra.dictator.domain.User;
import ru.zsuntyra.dictator.repository.AssociateRepository;
import ru.zsuntyra.dictator.repository.AvatarRepository;
import ru.zsuntyra.dictator.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Getter
@Setter
@Stateless
public class ShopEJB {

    @Inject
    private AssociateRepository associateRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AvatarRepository avatarRepository;

    @EJB
    private AuthEJB authEJB;

    public List<Associate> getAllAssociates() {
        return associateRepository.findAll();
    }

    public boolean buyAssociate(long associateId) {
        User customer = authEJB.getAuthorizedUser();
        Associate product = associateRepository.findById(associateId);

        if (customer.getAssociates().contains(product) || customer.getMoney() < product.getCost()) {
            return false;
        }

        product.getUsers().add(customer);
        customer.setMoney(customer.getMoney() - product.getCost());
        customer.getAssociates().add(product);

        userRepository.update(customer);

        User seller = product.getSeller();
        if (seller != null) {
            seller.setMoney((int) (seller.getMoney() + product.getCost() * 0.1));
        }
        userRepository.update(seller);
        // FIXME Should I update the product ?
        return true;
    }

    public List<Avatar> getAllAvatars() {
        return avatarRepository.findAll();
    }

    public boolean buyAvatar(long avatarId) {
        User customer = authEJB.getAuthorizedUser();
        Avatar product = avatarRepository.findById(avatarId);

        if (customer.getAvatars().contains(product) || customer.getMoney() < product.getCost()) {
            return false;
        }

        product.getUsers().add(customer);
        customer.setMoney(customer.getMoney() - product.getCost());
        customer.getAvatars().add(product);

        userRepository.update(customer);
        return true;
    }

}
