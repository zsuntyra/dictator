package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.User;
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

    public User getCurrentUser() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return userRepository.findByUsername(context.getUserPrincipal().getName());
    }

}
