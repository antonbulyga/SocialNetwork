package com.senla.service;


public interface TokenService {
    Boolean findTokenByTokenNumber(String tokenNumber);

    void addToken(String tokenNumber);
}
