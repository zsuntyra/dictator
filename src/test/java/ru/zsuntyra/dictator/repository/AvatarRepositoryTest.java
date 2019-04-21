package ru.zsuntyra.dictator.repository;

import org.junit.Test;
import ru.zsuntyra.dictator.domain.Avatar;

import static org.junit.Assert.assertEquals;

public class AvatarRepositoryTest {

    @Test
    public void saveAndFindByName() {
        AvatarRepository avatarRepository = new AvatarRepository();

        String name = "avatarTestName";
        String color = "avatarTestColor";
        String particle = "avatarTestParticle";
        int cost = 15;

        Avatar avatar = new Avatar();
        avatar.setName(name);
        avatar.setColor(color);
        avatar.setCost(cost);
        avatar.setParticle(particle);
        avatarRepository.create(avatar);

        Avatar received = avatarRepository.findByName(name);
        assertEquals(name, received.getName());
        assertEquals(color, received.getColor());
        assertEquals(particle, received.getParticle());
        assertEquals(cost, received.getCost());
    }

}
