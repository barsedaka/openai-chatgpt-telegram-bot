package com.bar.repository;

import com.bar.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void itShouldCheckIfUserCanBeCreatedAndRecalled() {
        // given
        String email = "bar.sedaka@gmail.com";
        UserEntity user = new UserEntity(
                "barsed",
                "bar",
                "sed",
                email,
                1L
        );
        underTest.save(user);

        // when
    }
}
