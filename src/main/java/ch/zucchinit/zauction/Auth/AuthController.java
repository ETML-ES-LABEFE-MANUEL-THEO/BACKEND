package ch.zucchinit.zauction.Auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService)
    {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public AuthDTO.UserDetails register(HttpServletResponse response, @Valid @RequestBody AuthDTO.UserRegister userRegister) {
        User user = userService.createUser(userRegister);
        Token token = tokenService.registerToken(user);

        Cookie cookieToken = tokenService.getCookieToken(token.getToken());
        response.addCookie(cookieToken);

        return userService.getUserDetails(user);
    }

    @PostMapping("/login")
    public AuthDTO.UserDetails login(HttpServletResponse response, @Valid @RequestBody AuthDTO.UserLogin userLogin) {
        User user = userService.findUserByEmailAndPassword(userLogin.email(), userLogin.password());
        Token token = tokenService.registerToken(user);

        Cookie cookieToken = tokenService.getCookieToken(token.getToken());
        response.addCookie(cookieToken);

        return userService.getUserDetails(user);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public void unregister(HttpServletResponse response) {
        Token token = (Token) SecurityContextHolder.getContext().getAuthentication().getDetails();
        tokenService.unregisterToken(token);

        Cookie cookieToken = tokenService.deleteCookieToken();
        response.addCookie(cookieToken);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public AuthDTO.UserDetails me() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserDetails(user);
    }
}
