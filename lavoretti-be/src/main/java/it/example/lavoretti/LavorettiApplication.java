package it.example.lavoretti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LavorettiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LavorettiApplication.class, args);
    }

}
