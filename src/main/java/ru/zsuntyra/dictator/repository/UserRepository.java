package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.User;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("userRepository")
public class UserRepository extends JpaRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }
        String query = "SELECT u FROM User u WHERE u.username='" + username + "'";
        return getElementOrNull(getEntityManager().createQuery(query, User.class).getResultList());
    }

}
