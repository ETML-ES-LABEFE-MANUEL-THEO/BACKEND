package ch.zucchinit.zauction.Auth;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Token {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String token;
    private LocalDateTime creationDate;
    private LocalDateTime expireDate;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {}
    public Token(LocalDateTime expireDate, User user) {
        this.creationDate = LocalDateTime.now();
        this.expireDate = expireDate;
        this.user = user;
    }

    public boolean isValid() {
        return !expired && expireDate.isAfter(LocalDateTime.now());
    }
}
