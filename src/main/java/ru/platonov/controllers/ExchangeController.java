package ru.platonov.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.platonov.services.CurrencyService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Currency;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ExchangeController {

    Logger log = LoggerFactory.getLogger(ExchangeController.class);

    private final CurrencyService exchangeService;

    @GetMapping("/exchange")
    @PreAuthorize("hasAuthority('user:write')")
    public String exchange(Model model, Principal principal, BigDecimal amount, String fromCurrency,
                           String toCurrency) throws JsonProcessingException {
        fromCurrency = Objects.nonNull(fromCurrency) ? fromCurrency : "RUB";
        toCurrency = Objects.nonNull(toCurrency) ? toCurrency : "USD";
        amount = Objects.nonNull(amount) ? amount : BigDecimal.ZERO;
        BigDecimal exchange = exchangeService.getExchange(amount, fromCurrency, toCurrency);
        model.addAttribute("result", exchange);
        model.addAttribute("from", fromCurrency);
        model.addAttribute("to", toCurrency);
        model.addAttribute("email", principal.getName());
        model.addAttribute("fromCurrency", Currency.getAvailableCurrencies());
        model.addAttribute("toCurrency", Currency.getAvailableCurrencies());
        model.addAttribute("statistics", exchangeService.isModerator(principal) ? "submit" : "hidden");
        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            log.info("save new exchange, id: {}",
                    exchangeService.saveExchange(amount, fromCurrency, toCurrency, principal));
        }
        return "currency_page";
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String stats(Model model, Principal principal, BigDecimal more, BigDecimal less, BigDecimal onceMore) {
        model.addAttribute("more", more);
        model.addAttribute("less", less);
        model.addAttribute("onceMore", onceMore);
        model.addAttribute("email", principal.getName());
        model.addAttribute("userList", exchangeService.getUserListByWhoRequestedExchangeSumMore(more, less, onceMore));
        model.addAttribute("currencyPair", exchangeService.getRatingCurrencyPair());
        log.info("show statistics for user {}", principal.getName());
        return "stats_page";
    }
}
