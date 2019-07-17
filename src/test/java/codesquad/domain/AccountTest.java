package codesquad.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void passwordMatch() {
        String password = "abcd1234";
        Account account = new Account("be@gmail.com", password, "bell", "010-0000-0000");

        assertThat(account.matchPassword(password)).isTrue();
        assertThat(account.matchPassword("11111111")).isFalse();
    }
}
