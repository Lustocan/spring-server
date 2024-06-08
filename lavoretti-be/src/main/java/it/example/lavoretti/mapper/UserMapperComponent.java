package it.example.lavoretti.mapper;

import static it.example.lavoretti.domain.users.RoleType.ROLE_USER;

import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.domain.users.SignUpUser;
import it.example.lavoretti.domain.users.User;
import javax.annotation.ParametersAreNonnullByDefault;
import org.springframework.stereotype.Component;

@Component
@ParametersAreNonnullByDefault
public class UserMapperComponent {

    public UserEntity toEntity(User user) {
        return new UserEntity(user.username(),
                              user.email(),
                              user.password(),
                              user.salt(),
                              user.role());
    }

    public User toDomain(UserEntity userEntity) {
        return new User(userEntity.getId(),
                        userEntity.getRole(),
                        userEntity.getPassword(),
                        userEntity.getSalt(),
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.isAccountExpired(),
                        userEntity.isLocked(),
                        userEntity.isCredentialsExpired(),
                        userEntity.isEnabled(),
                        userEntity.getExpiredAt());
    }

    public UserEntity toEntity(SignUpUser signUpUser, String salt) {
        return new UserEntity(signUpUser.username(),
                              signUpUser.email(),
                              signUpUser.password(),
                              salt,
                              ROLE_USER);
    }
}
