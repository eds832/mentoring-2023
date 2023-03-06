package com.epam.mentoring.autoconfig.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.epam.mentoring.autoconfig.configuration.AppTestConfiguration;
import com.epam.mentoring.autoconfig.domain.User;

/**
 * @author Eduard_Sardyka
 */
@SpringBootTest
@Import(AppTestConfiguration.class)
@ActiveProfiles(profiles = "test")
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