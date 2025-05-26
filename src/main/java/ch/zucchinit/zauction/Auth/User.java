package ch.zucchinit.zauction.Auth;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
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

    public User() {}

    public User(String firstName, String lastName, String email, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = hashedPassword;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public BigDecimal getBalance() { return balance; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getZipCode() { return zipCode; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public boolean hasSufficientBalance(BigDecimal amount) {  return this.balance.compareTo(amount) >= 0; }
    public void increasebalance(BigDecimal amount) {  this.balance = this.balance.add(amount); }
    public void decreasebalance(BigDecimal amount) {  this.balance = this.balance.subtract(amount); }

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
