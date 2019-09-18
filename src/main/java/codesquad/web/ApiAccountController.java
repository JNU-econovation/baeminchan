package codesquad.web;

import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ApiAccountController {
    private Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    private final AccountService accountService;

    @PostMapping("/member/sign-up")
    public ResponseEntity<Void> createAccount(@Valid @RequestBody SignUpDTO signUpDTO) {
        log.debug("request log: {}", signUpDTO);

        accountService.create(signUpDTO);

        return makeResponseEntity("/login", HttpStatus.CREATED);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Void> login(HttpSession session, @Valid @RequestBody LoginDTO loginDTO) {
        accountService.login(session, loginDTO);

        return makeResponseEntity("/", HttpStatus.FOUND);
    }

    private ResponseEntity<Void> makeResponseEntity(String path, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(path));
        return new ResponseEntity<>(headers, status);
    }
}
