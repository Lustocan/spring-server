package it.example.lavoretti.controller;

import static org.assertj.core.api.Assertions.assertThat;

import it.example.lavoretti.BaseTestContainer;
import it.example.lavoretti.LavorettiApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LavorettiApplication.class)
class LoginControllerTestIT extends BaseTestContainer {

    @Test
    @DisplayName("test set up")
    void test_set_up() {
        // given

        // when
        // then
        assertThat("ok").isEqualTo("ok");
    }
}