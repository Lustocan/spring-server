package it.example.lavoretti.dao;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserId {

    private UUID id;

    public UserId(String id) {
        this.id = UUID.fromString(id);
    }

}
