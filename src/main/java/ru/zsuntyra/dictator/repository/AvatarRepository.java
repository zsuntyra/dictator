package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Avatar;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("avatarRepository")
public class AvatarRepository extends JpaRepository<Avatar> {

    public AvatarRepository() {
        super(Avatar.class);
    }

    public Avatar findByName(String name) {
        if (name == null) {
            return null;
        }
        String query = "SELECT a FROM Avatar a WHERE a.name='" + name + "'";
        return getElementOrNull(getEntityManager().createQuery(query, Avatar.class).getResultList());
    }

}
