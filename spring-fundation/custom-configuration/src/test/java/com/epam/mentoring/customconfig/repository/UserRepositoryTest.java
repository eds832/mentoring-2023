package com.epam.mentoring.customconfig.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.epam.mentoring.customconfig.domain.User;
import com.epam.mentoring.customconfig.configuration.AppConfig;

/**
 * @author Eduard_Sardyka
 */
@SpringBootTest
@Import(AppConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testDataSource() {
        User user = new User();
        user.setName("Dan");
        user.setSurname("Doe");
        user.setEmail("dan_doe@gmail.com");
        userRepository.save(user);
        List<User> users = (List<User>) userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getName()).isEqualTo("Dan");
        assertThat(users.get(0).getSurname()).isEqualTo("Doe");
        assertThat(users.get(0).getEmail()).isEqualTo("dan_doe@gmail.com");
    }
}