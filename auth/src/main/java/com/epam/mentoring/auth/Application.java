package com.epam.mentoring.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.epam.mentoring.auth.model.UserEntity;
import com.epam.mentoring.auth.repository.UserRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createUser("user", "VIEW_INFO");
        createUser("admin", "VIEW_ADMIN");
        createUser("full", "VIEW_INFO;VIEW_ADMIN" );
    }

    private void createUser(String user, String authorities) {
        String userName = user + "@epam.com";
        String password = user + "Password";
        String salt = BCrypt.gensalt();
        String hashedPass = BCrypt.hashpw(password, salt);
        UserEntity userEntity = new UserEntity(null, userName, hashedPass, authorities);
        userRepository.save(userEntity);
        System.out.println(
            "Created new user with username: " + userName + ", password: " + password + ", hashedPass: " + hashedPass);
    }
}