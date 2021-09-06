package ru.platonov.dto;

import java.math.BigDecimal;

public record UserWhoRequestedExchangeSumResponse(long id, String username, BigDecimal sum) {
}
