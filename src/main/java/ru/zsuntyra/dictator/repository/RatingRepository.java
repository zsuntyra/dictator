package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Rating;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("ratingRepository")
public class RatingRepository extends JpaRepository<Rating> {

    public RatingRepository() {
        super(Rating.class);
    }

    public Rating findByUserId(long userId) {
        String query = "SELECT r FROM Rating r WHERE r.user.id=" + userId;
        return getElementOrNull(getEntityManager().createQuery(query, Rating.class).getResultList());
    }

}
