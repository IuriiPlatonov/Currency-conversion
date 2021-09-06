package ru.platonov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.platonov.dto.AuthRequest;

import java.security.Principal;
import java.util.Objects;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String login(Model model, Principal principal) {
        model.addAttribute("authRequest", new AuthRequest());
        if (Objects.nonNull(principal)) return "redirect:/exchange";
        return "login_page";
    }
}
