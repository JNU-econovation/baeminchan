package codesquad.web;

import codesquad.domain.Account;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
public class ApiAccountController {
    private Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/member/sign-up")
    public ResponseEntity<Void> createAccount(@RequestBody SignUpDTO signUpDTO) {
        log.debug("request log: {}", signUpDTO);

        accountService.create(signUpDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/login"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Account> login(HttpSession session, @RequestBody LoginDTO loginDTO) {
        return accountService.login(session, loginDTO);
    }
}
