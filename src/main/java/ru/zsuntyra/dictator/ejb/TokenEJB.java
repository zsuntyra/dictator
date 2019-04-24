package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.User;

import javax.ejb.Init;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Singleton
public class TokenEJB {

    @Init
    public void init() {
        authorizedUsers = new HashMap<>();
    }

    private Map<String, User> authorizedUsers;

}
