package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.Rating;
import ru.zsuntyra.dictator.repository.RatingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Getter
@Setter
@Stateless
public class RatingEJB {

    @Inject
    private RatingRepository ratingRepository;

    public List<Rating> getTop50PlayersByRating() {
        return ratingRepository.findTopNByProgress(50);
    }

}
