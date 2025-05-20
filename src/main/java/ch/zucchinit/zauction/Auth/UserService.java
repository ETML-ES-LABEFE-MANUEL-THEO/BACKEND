package ch.zucchinit.zauction.Auth;

import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public AuthDTO.UserProfile getUserProfile(User user) {
        return new AuthDTO.UserProfile(user.getFirstName(), user.getLastName(), user.getBalance());
    }

    public AuthDTO.UserAccount getUserAccount() {
        User user = getUserFromContext();
        return new AuthDTO.UserAccount(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getCity(),
                user.getZipCode()
        );
    }

    public User findUserByEmailAndPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) throw new ResourceNotFound();

        return user.get();
    }

    public User createUser(AuthDTO.UserRegister userRegister) {
        String hashedPassword = passwordEncoder.encode(userRegister.password());

        return userRepository.save(
                new User(userRegister.firstName(), userRegister.lastName(), userRegister.email(), hashedPassword)
        );
    }

    public AuthDTO.UserAccount updateUser(AuthDTO.UserAccount userAccount) {
        User user = getUserFromContext();

        updateIfChanged(user::getFirstName, user::setFirstName, userAccount.firstName());
        updateIfChanged(user::getLastName, user::setLastName, userAccount.lastName());
        updateIfChanged(user::getPhone, user::setPhone, userAccount.phone());
        updateIfChanged(user::getAddress, user::setAddress, userAccount.address());
        updateIfChanged(user::getCity, user::setCity, userAccount.city());
        updateIfChanged(user::getZipCode, user::setZipCode, userAccount.zipCode());

        userRepository.save(user);
        return userAccount;
    }

    public void resetPassword(AuthDTO.UserResetPassword userResetPassword) {
        User user = getUserFromContext();

        if (!passwordEncoder.matches(userResetPassword.oldPassword(), user.getPassword())) throw new ResourceNotFound();
        user.setPassword(passwordEncoder.encode(userResetPassword.newPassword()));
    }
}
