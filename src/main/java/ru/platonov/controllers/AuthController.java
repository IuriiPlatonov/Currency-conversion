package ru.platonov.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.platonov.dto.AuthRequest;
import ru.platonov.dto.RegisterRequest;
import ru.platonov.services.AuthServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public String authenticate(AuthRequest request) {
        try {
            if (authService.authenticate(request)) {
                log.info("authenticate successful: {}", request);
            } else {
                log.info("authenticate failed, wrong password: {}", request);
                return "redirect:/";
            }
        } catch (UsernameNotFoundException e) {
            log.info("authenticate failed, wrong email: {}", e.getMessage());
            return "redirect:/";
        }
        return "redirect:/exchange";
    }

    @GetMapping("/register_page")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register_page";
    }

    @PostMapping("/register")
    public String register(RegisterRequest request) {
        if (authService.registerNewUser(request)) {
            log.info("save new USER: {}", request);
            return "redirect:/";
        }
        return "redirect:/register_page";
    }
}
