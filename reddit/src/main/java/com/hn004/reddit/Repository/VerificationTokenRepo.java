package com.hn004.reddit.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hn004.reddit.Entity.VerificationToken;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long>{
    Optional<VerificationToken> findByToken(String token);
}
