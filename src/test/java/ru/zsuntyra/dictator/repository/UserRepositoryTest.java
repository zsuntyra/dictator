package ru.zsuntyra.dictator.repository;

import org.junit.Test;
import ru.zsuntyra.dictator.domain.User;

import static org.junit.Assert.assertEquals;

public class UserRepositoryTest {

    @Test
    public void saveUserTest() {
        UserRepository userRepository = new UserRepository();

        User user = new User();
        user.setUsername("usernameSaveTest");
        user.setPassword("passwordSaveTest");
        user.setProgress(5);

        userRepository.create(user);
    }

    @Test
    public void saveAndFindTest() {
        UserRepository userRepository = new UserRepository();

        String username = "username2SaveTest";
        String password = "password2SaveTest";
        int progress = 5;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setProgress(progress);

        userRepository.create(user);
        User received = userRepository.findByUsername(username);

        assertEquals(password, received.getPassword());
        assertEquals(progress, received.getProgress());
    }

}
