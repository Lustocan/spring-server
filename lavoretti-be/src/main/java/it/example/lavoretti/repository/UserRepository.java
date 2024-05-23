package it.example.lavoretti.repository;

import it.example.lavoretti.dao.UserEntity;
import java.util.UUID;

import it.example.lavoretti.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    User findByUsername(String username);
    User findByEmail(String email);
}
