package ru.platonov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.platonov.dto.AuthRequest;
import ru.platonov.dto.RegisterRequest;
import ru.platonov.entities.User;
import ru.platonov.repositories.UserRepository;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager manager;

    @Override
    public boolean authenticate(AuthRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User ".concat(email).concat(" not found")));
        if (new BCryptPasswordEncoder(12).matches(password, user.getPassword())) {
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return true;
        }
        return false;
    }

    @Override
    public boolean registerNewUser(RegisterRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isEmpty()
            && Objects.nonNull(request.getEmail())
            && request.getPassword().equals(request.getRepeatPassword())) {
            userRepository.save(
                    User.builder()
                            .email(request.getEmail())
                            .name(request.getName())
                            .password(new BCryptPasswordEncoder(12).encode(request.getPassword()))
                            .build());
            return true;
        }
        return false;
    }
}
