package it.example.lavoretti.mapper;

import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperComponent {

    public UserEntity toEntity(User user) {
        return new UserEntity(user.username(),
                              user.email(),
                              user.password(),
                              user.roleType());
    }

    public User toDomain(UserEntity userEntity) {
        return new User(userEntity.getId(),
                        userEntity.getRoleType(),
                        userEntity.getPassword(),
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.isAccountExpired(),
                        userEntity.isLocked(),
                        userEntity.isCredentialsExpired(),
                        userEntity.isEnabled(),
                        userEntity.getExpiredAt());
    }

}
