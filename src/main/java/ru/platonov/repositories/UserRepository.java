package ru.platonov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.platonov.dto.UserWhoRequestedExchangeSumResponse;
import ru.platonov.entities.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("select new ru.platonov.dto.UserWhoRequestedExchangeSumResponse(u.id, u.name, sum(c.value)) " +
           "from User u " +
           "Join u.conversionCurrencies c " +
           "group by u " +
           "having sum(c.value) > :value ")
    Optional<Set<UserWhoRequestedExchangeSumResponse>> findUsersWhoRequestedExchangeSumMore(@Param("value") BigDecimal value);

    @Query("select new ru.platonov.dto.UserWhoRequestedExchangeSumResponse(u.id, u.name, sum(c.value)) " +
           "from User u " +
           "Join u.conversionCurrencies c " +
           "group by u " +
           "having sum(c.value) < :value ")
    Optional<Set<UserWhoRequestedExchangeSumResponse>> findUsersWhoRequestedExchangeSumLess(@Param("value") BigDecimal value);

    @Query("select new ru.platonov.dto.UserWhoRequestedExchangeSumResponse(u.id, u.name, max(c.value)) " +
           "from User u " +
           "Join u.conversionCurrencies c " +
           "group by u " +
           "having max(c.value) > :value ")
    Optional<Set<UserWhoRequestedExchangeSumResponse>> findUsersWhoRequestedExchangeMore(@Param("value") BigDecimal value);
}
