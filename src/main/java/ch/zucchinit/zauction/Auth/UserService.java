package ch.zucchinit.zauction.Auth;

import ch.zucchinit.zauction.Exceptions.ExceptionsDTO;
import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import ch.zucchinit.zauction.Exceptions.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static ch.zucchinit.zauction.Utils.FieldUpdater.updateIfChanged;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User getUserFromContext() {
        try {
            Long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean isSameUser(User user) {
        if (user == null) return false;
        return user.isSame(getUserFromContext());
    }

    public void restrictUser(User user) {
        if (!isSameUser(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public void restrictUsers(List<User> users) {
        if (users.stream().noneMatch(this::isSameUser)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) throw new ResourceNotFound();

        return user.get();
    }

    public User createUser(AuthDTO.UserRegister userRegister) {
        if (userRepository.existsByEmail(userRegister.email())) {
            ExceptionsDTO.ValidationError error = new ExceptionsDTO.ValidationError(List.of("email"), "Cet email est déjà utilisé");
            throw new ValidationError(List.of(error));
        }

        String hashedPassword = passwordEncoder.encode(userRegister.password());

        return userRepository.save(
                new User(userRegister.firstName(), userRegister.lastName(), userRegister.email(), hashedPassword)
        );
    }

    public User updateUser(AuthDTO.UserAccount userAccount) {
        User user = getUserFromContext();

        updateIfChanged(user::getFirstName, user::setFirstName, userAccount.firstName());
        updateIfChanged(user::getLastName, user::setLastName, userAccount.lastName());
        updateIfChanged(user::getPhone, user::setPhone, userAccount.phone());
        updateIfChanged(user::getAddress, user::setAddress, userAccount.address());
        updateIfChanged(user::getCity, user::setCity, userAccount.city());
        updateIfChanged(user::getZipCode, user::setZipCode, userAccount.zipCode());

        return userRepository.save(user);
    }

    public void resetPassword(AuthDTO.UserResetPassword userResetPassword) {
        User user = getUserFromContext();

        if (!passwordEncoder.matches(userResetPassword.oldPassword(), user.getPassword())) throw new ResourceNotFound();
        user.setPassword(passwordEncoder.encode(userResetPassword.newPassword()));
        userRepository.save(user);
    }

    public void setBalanceForUser(User user, BigDecimal newBalance) {
        user.setBalance(newBalance);
        userRepository.save(user);
    }
}
