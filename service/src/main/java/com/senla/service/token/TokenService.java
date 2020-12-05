package com.senla.service.token;


import com.senla.entity.Token;

public interface TokenService {
    Token findTokenByTokenNumber(String tokenNumber);

    void addToken(String tokenNumber);
}
