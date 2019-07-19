package codesquad.web;

import codesquad.domain.Account;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class ApiAccountController {
    private Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/member/sign-up")
    public ResponseEntity createAccount(@Valid @RequestBody SignUpDTO signUpDTO) {
        log.debug("request log: {}", signUpDTO);

        return accountService.create(signUpDTO);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Account> login(HttpSession session, @Valid @RequestBody LoginDTO loginDTO) {
        return accountService.login(session, loginDTO);
    }
}
