package com.senla.service.token;

import com.senla.entity.Token;
import com.senla.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    @Override
    public Token findTokenByTokenNumber(String tokenNumber) {
        log.info("Finding token by number");
        return tokenRepository.findByTokenNumber(tokenNumber);
    }

    @Override
    public void addToken(String tokenNumber) {
        Token token = new Token();
        token.setTokenNumber(tokenNumber);
        log.info("Adding token number");
        tokenRepository.save(token);
    }
}
