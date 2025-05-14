package ch.zucchinit.zauction.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    void deleteByExpireDateBefore(LocalDateTime date);
}
