package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Associate;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("associateRepository")
public class AssociateRepository extends JpaRepository<Associate> {

    public AssociateRepository() {
        super(Associate.class);
    }

}
