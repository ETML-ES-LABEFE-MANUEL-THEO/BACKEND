package ch.zucchinit.zauction.Auth;

import ch.zucchinit.zauction.Auction.Auction;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private @Column(unique = true) String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private String zipCode;
    private BigDecimal balance = BigDecimal.valueOf(0);

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private List<Auction> auctions;

    public User() {}
    public User(String firstName, String lastName, String email, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = hashedPassword;
    }

    public boolean hasSufficientBalance(BigDecimal amount) {  return getAvailableBalance().compareTo(amount) >= 0; }
    public BigDecimal getAvailableBalance() { return this.balance.subtract(getReservedBalance()); }
    public BigDecimal getReservedBalance() {
        if (this.auctions == null) return BigDecimal.ZERO;
        return getAuctions().stream()
                .filter(Auction::isLast)
                .map(Auction::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override
    public String getUsername() { return email; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
