package com.senla.model.repository;

import com.senla.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Boolean findByTokenNumber(String tokenNumber);
}
