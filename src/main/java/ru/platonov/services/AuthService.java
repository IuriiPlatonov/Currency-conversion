package ru.platonov.services;

import ru.platonov.dto.AuthRequest;
import ru.platonov.dto.RegisterRequest;

public interface AuthService {
    boolean authenticate(AuthRequest request);

    boolean registerNewUser(RegisterRequest request);
}
