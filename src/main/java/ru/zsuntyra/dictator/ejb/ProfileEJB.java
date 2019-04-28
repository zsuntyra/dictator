package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.Associate;
import ru.zsuntyra.dictator.domain.Fraction;
import ru.zsuntyra.dictator.domain.User;
import ru.zsuntyra.dictator.repository.AssociateRepository;
import ru.zsuntyra.dictator.repository.UserRepository;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Getter
@Setter
@Stateless
public class ProfileEJB {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AssociateRepository associateRepository;

    public User getCurrentUser() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return userRepository.findByUsername(context.getUserPrincipal().getName());
    }

    public int getUserLevel() {
        return getCurrentUser().getProgress() / 10;
    }

    public int getAvailablePointsAmount() {
        return getUserLevel() / 5;
    }

    public void createAssociate(Associate associate) {
        User user = getCurrentUser();
        if (isPointDistributionAllowed(associate)) {
            if (user.getAssociate() != null) {
                associateRepository.removeById(user.getAssociate().getId());
            }

            int distributedPoints = calculateDistributedPoints(associate);
            associate.setCost((int) (distributedPoints * 15 * (1 + Math.log(distributedPoints))));
            associate.setSeller(user);
            user.setAssociate(associate);

            associateRepository.create(associate);
        }
    }

    private boolean isPointDistributionAllowed(Associate associate) {
        if (getUserLevel() < 5) {
            return false;
        }

        int distributedPoints = calculateDistributedPoints(associate);
        return distributedPoints < getAvailablePointsAmount();
    }

    private int calculateDistributedPoints(Associate associate) {
        int distributedPoints = 0;
        for (Fraction fraction : Fraction.values()) {
            distributedPoints += associate.getCoefficientByFraction(fraction);
        }
        return distributedPoints;
    }

}
