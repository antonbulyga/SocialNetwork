package com.senla.repository;

import com.senla.entity.Token;
import com.senla.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = ConfigurationTest.class)
@EnableAutoConfiguration
public class TokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void findByUserName_Success() {
        Token token = new Token();
        token.setTokenNumber("12345");
        entityManager.persist(token);
        entityManager.flush();
        Token result = tokenRepository.findByTokenNumber(token.getTokenNumber());
        assertEquals(token, result);
    }

    @Test
    public void addToken_Success() {
        Token token = new Token();
        token.setTokenNumber("12345");
        entityManager.persist(token);
        entityManager.flush();
        Token result = tokenRepository.save(token);
        assertEquals(token, result);
    }
}
