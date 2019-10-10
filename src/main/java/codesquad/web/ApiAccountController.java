package codesquad.web;

import codesquad.dto.FindingEmailDTO;
import codesquad.dto.FindingPasswordDTO;
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

@RestController
@RequiredArgsConstructor
public class ApiAccountController {
    private Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    private final AccountService accountService;

    @PostMapping("/member/sign-up")
    public ResponseEntity<Void> createAccount(@Valid @RequestBody SignUpDTO signUpDTO) {
        log.debug("request log: {}", signUpDTO);

        accountService.create(signUpDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/member/sign-in")
    public ResponseEntity<Void> login(HttpSession session, @Valid @RequestBody LoginDTO loginDTO) {
        accountService.login(session, loginDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/member/find/request")
    public ResponseEntity<String> findId(@Valid @RequestBody FindingEmailDTO findingEmailDTO) {
        String foundId = accountService.findId(findingEmailDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(foundId, headers, HttpStatus.FOUND);
    }

    @PostMapping("/member/find-pass/request")
    public ResponseEntity<String> findPassword(@Valid @RequestBody FindingPasswordDTO findingPasswordDTO) {
        String temporaryPassword = accountService.findPassword(findingPasswordDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(temporaryPassword, headers, HttpStatus.CREATED);
    }
}
