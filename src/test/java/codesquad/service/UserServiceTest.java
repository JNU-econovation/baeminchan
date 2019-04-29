package codesquad.service;

import codesquad.domain.LoginDTO;
import codesquad.domain.User;
import codesquad.domain.UserRequestDTO;
import codesquad.exception.BadRequestException;
import codesquad.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private LoginDTO loginDTO;


    @Before
    public void setUp() {
        userRequestDTO = new UserRequestDTO("email@email.com", "name", "01012341234", "password", "password");
        loginDTO = new LoginDTO("email@email.com", "password");
    }

    @Test
    public void 회원가입() {
        User testUser = new User(userRequestDTO);
        when(userRepository.save(testUser.encode(passwordEncoder))).thenReturn(testUser);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        User tempUser = userService.save(userRequestDTO);

        assertThat(tempUser.getPassword()).isEqualTo("password");
    }

    @Test(expected = BadRequestException.class)
    public void 회원가입_실패_이메일중복() {
        User user = new User(userRequestDTO);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        userService.save(userRequestDTO);
    }

    @Test
    public void 로그인_성공() {
        User user = new User(userRequestDTO);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        userService.login(loginDTO);
    }

}
