package ru.platonov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.platonov.dto.RatingCurrencyPair;
import ru.platonov.entities.ConversionCurrency;

import java.util.Set;

@Repository
public interface ExchangeRepository extends JpaRepository<ConversionCurrency, Long> {

    @Query("select new ru.platonov.dto.RatingCurrencyPair(c.CurrencyPair, count(c.CurrencyPair)) " +
           "from ConversionCurrency c " +
           "group by c.CurrencyPair " +
           "order by count(c.CurrencyPair) desc ")
    Set<RatingCurrencyPair> getRatingCurrencyPair();


}
