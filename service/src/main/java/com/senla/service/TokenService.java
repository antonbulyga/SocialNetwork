package com.senla.service;


import com.senla.entity.Token;

public interface TokenService {
    Token findTokenByTokenNumber(String tokenNumber);

    void addToken(String tokenNumber);
}
