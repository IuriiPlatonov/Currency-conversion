package ru.platonov.entities;

import lombok.*;
import ru.platonov.enums.Role;

import javax.persistence.*;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private boolean moderator;

    @OneToMany(mappedBy = "userId")
    private List<ConversionCurrency> conversionCurrencies;

    public Role getRole() {
        return isModerator() ? Role.MODERATOR : Role.USER;
    }
}
