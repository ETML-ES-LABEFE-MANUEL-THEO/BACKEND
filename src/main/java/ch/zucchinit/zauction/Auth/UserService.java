package ch.zucchinit.zauction.Auth;

import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthDTO.UserDetails getUserDetails(User user) {
        return new AuthDTO.UserDetails(user.getFirstName(), user.getLastName(), user.getBalance());
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
}
