package CodeQuest.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CodeQuest.model.User;
import CodeQuest.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://code-quest-virid-five.vercel.app"
    "https://code-quest-sound17.vercel.app",
    "https://code-quest-4pwx70dza-sound17.vercel.app"
})
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (user.getName() == null ||
            user.getEmail() == null ||
            user.getPassword() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "All fields are required"));
        }

        if (user.getPassword().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Password must be at least 8 characters"));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Email already registered"));
        }

        userRepository.save(user);

        return ResponseEntity.ok(
                Map.of("message", "Registration successful")
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        Optional<User> user =
                userRepository.findByEmail(loginUser.getEmail());

        if (user.isEmpty() ||
            !user.get().getPassword()
                    .equals(loginUser.getPassword())) {

            return ResponseEntity.status(401)
                    .body(Map.of(
                            "message",
                            "Incorrect email or password"
                    ));
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "name", user.get().getName(),
                        "email", user.get().getEmail()
                )
        );
    }
}