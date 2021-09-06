package ru.platonov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.platonov.dto.RatingCurrencyPair;
import ru.platonov.dto.UserWhoRequestedExchangeSumResponse;
import ru.platonov.entities.ConversionCurrency;
import ru.platonov.entities.User;
import ru.platonov.feign.client.CoinGatePlaceHolderClient;
import ru.platonov.repositories.ExchangeRepository;
import ru.platonov.repositories.UserRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CoinGatePlaceHolderClient coinGatePlaceHolderClient;
    private final UserRepository userRepository;
    private final ExchangeRepository conversionCurrencyRepository;

    @Override
    public BigDecimal getExchange(BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal course = coinGatePlaceHolderClient.getExchangeRatio(fromCurrency, toCurrency).orElse(BigDecimal.ZERO);
        return course.multiply(amount);
    }

    @Override
    public long saveExchange(BigDecimal amount, String fromCurrency, String toCurrency, Principal principal) {

        User user = userRepository.findUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Don't save new ConversionCurrency because user not found"));

        BigDecimal course = coinGatePlaceHolderClient.getExchangeRatio(fromCurrency, "USD").orElse(BigDecimal.ZERO);
        BigDecimal amountInUsd = amount.multiply(course);
        return conversionCurrencyRepository.save(
                        ConversionCurrency.builder()
                                .userId(user.getId())
                                .CurrencyPair(fromCurrency.concat(" -> ").concat(toCurrency))
                                .value(amountInUsd)
                                .build())
                .getId();
    }

    @Override
    public boolean isModerator(Principal principal) {
        return userRepository.findUserByEmail(principal.getName()).orElseThrow(
                        () -> new UsernameNotFoundException("Don't save new ConversionCurrency because user not found"))
                .isModerator();
    }

    @Override
    public Set<UserWhoRequestedExchangeSumResponse>
    getUserListByWhoRequestedExchangeSumMore(BigDecimal more, BigDecimal less, BigDecimal onceMore) {

        if (Objects.nonNull(more)) return userRepository.findUsersWhoRequestedExchangeSumMore(more).orElse(Set.of());
        if (Objects.nonNull(less)) return userRepository.findUsersWhoRequestedExchangeSumLess(less).orElse(Set.of());
        if (Objects.nonNull(onceMore))
            return userRepository.findUsersWhoRequestedExchangeMore(onceMore).orElse(Set.of());

        return Set.of();
    }

    @Override
    public Set<RatingCurrencyPair> getRatingCurrencyPair() {
        return conversionCurrencyRepository.getRatingCurrencyPair();
    }
}
