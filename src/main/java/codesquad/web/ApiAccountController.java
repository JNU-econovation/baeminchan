package codesquad.web;

import codesquad.dto.SignUpDTO;
import codesquad.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ApiAccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/join/sign-up")
    public ResponseEntity<Void> createAccount(@RequestBody SignUpDTO signUpDTO) {
        accountService.create(signUpDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
