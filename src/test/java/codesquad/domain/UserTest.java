package codesquad.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void 비밀번호확인() {
        User user = new User(0L, "email@email.com", "name", "password", "01012341234", Role.USER);
        LoginDTO loginDTO = new LoginDTO("email@email.com", "password");
        user = user.encode(passwordEncoder);
        assertThat(user.isCorrectPassword(passwordEncoder, loginDTO)).isTrue();
    }

    @Test
    public void 비밀번호암호화() {
        User user = new User(0L, "email@email.com", "name", "password", "01012341234", Role.USER);
        User anotherUser = new User(1L, "email@email.com", "name", "password", "01012341234", Role.USER);
        user = user.encode(passwordEncoder);
        assertThat(user.getPassword().equals(anotherUser.getPassword())).isFalse();
    }
}
