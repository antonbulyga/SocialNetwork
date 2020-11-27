package com.senla.service.impl;

import com.senla.entity.Token;
import com.senla.repository.TokenRepository;
import com.senla.service.TokenService;
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
    public Boolean findTokenByTokenNumber(String tokenNumber) {
        Boolean flag = tokenRepository.findByTokenNumber(tokenNumber);
        if(flag == null){
            return false;
        }
        log.info("Finding token by number");
        return flag;
    }

    @Override
    public void addToken(String tokenNumber) {
        Token token = new Token();
        token.setTokenNumber(tokenNumber);
        tokenRepository.save(token);
        log.info("Adding token number");
    }
}
