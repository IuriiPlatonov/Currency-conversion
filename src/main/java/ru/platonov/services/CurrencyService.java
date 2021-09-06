package ru.platonov.services;

import ru.platonov.dto.RatingCurrencyPair;
import ru.platonov.dto.UserWhoRequestedExchangeSumResponse;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Set;

public interface CurrencyService {
    BigDecimal getExchange(BigDecimal amount, String fromCurrency, String toCurrency);

    long saveExchange(BigDecimal amount, String fromCurrency, String toCurrency, Principal principal);

    boolean isModerator(Principal principal);

    Set<UserWhoRequestedExchangeSumResponse> getUserListByWhoRequestedExchangeSumMore(BigDecimal more, BigDecimal less
    , BigDecimal onceMore);


    Set<RatingCurrencyPair> getRatingCurrencyPair();

}

