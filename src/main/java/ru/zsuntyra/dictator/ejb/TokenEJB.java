package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Singleton
public class TokenEJB {

    @PostConstruct
    public void init() {
        authorizedUsers = new HashMap<>();
    }

    private Map<String, User> authorizedUsers;

    public String findToken(User user) {
        for (Map.Entry<String, User> entry : authorizedUsers.entrySet()) {
            if (entry.getValue().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }

}
