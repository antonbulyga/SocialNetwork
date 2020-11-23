package com.senla.model.service;

import com.senla.model.entity.Token;
import org.springframework.stereotype.Service;


public interface TokenService {
    Boolean findTokenByTokenNumber(String tokenNumber);
    void addToken(String tokenNumber);
}
