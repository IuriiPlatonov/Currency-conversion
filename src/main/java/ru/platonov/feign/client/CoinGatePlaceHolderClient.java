package ru.platonov.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Optional;

@FeignClient(value = "CoinGate", url = "https://api.coingate.com/v2/rates/merchant")
public interface CoinGatePlaceHolderClient {
    @GetMapping("/{toCurrency}/{fromCurrency}")
    Optional<BigDecimal> getExchangeRatio(@PathVariable("toCurrency") String fromCurrency,
                                          @PathVariable("fromCurrency") String toCurrency);
}
