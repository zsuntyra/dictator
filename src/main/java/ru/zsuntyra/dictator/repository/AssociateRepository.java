package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Associate;
import ru.zsuntyra.dictator.domain.Avatar;
import ru.zsuntyra.dictator.domain.User;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("associateRepository")
public class AssociateRepository extends JpaRepository<Associate> {

    public AssociateRepository() {
        super(Associate.class);
    }

    public List<Associate> findAllByUser(User user) {
        if (user == null) {
            return null;
        }

        String query = "select * from associate inner join usr_associate ua on associate.id = ua.associates_id " +
                "where ua.users_id = " + user.getId();
        ;
        return getEntityManager().createNativeQuery(query, Associate.class).getResultList();
    }
}
