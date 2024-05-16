package it.example.lavoretti.repository;

import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.dao.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UserId> {

}
