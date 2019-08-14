package codesquad.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AccountTest {

    @Test
    public void passwordMatch() {
        String password = "abcd1234";

        Account account = new Account()
                .setEmail("be@gmail.com")
                .setPassword(password)
                .setName("bell")
                .setPhoneNumber("010-0000-0000")
                .build();


        assertAll(
                () -> assertEquals(account.matchPassword(password), true),
                () -> assertEquals(account.matchPassword("11111111"), false)
        );
        assertThat(account.matchPassword(password)).isTrue();
        assertThat(account.matchPassword("11111111")).isFalse();
    }
}
