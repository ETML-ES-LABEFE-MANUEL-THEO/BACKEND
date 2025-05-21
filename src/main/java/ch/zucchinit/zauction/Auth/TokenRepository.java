package ch.zucchinit.zauction.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends JpaRepository<Token, String> {
    void deleteByExpireDateBefore(LocalDateTime date);
}
