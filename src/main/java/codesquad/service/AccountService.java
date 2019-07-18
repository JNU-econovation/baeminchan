package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.NotFoundException;
import codesquad.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account create(SignUpDTO signUpDTO) {

        if (!signUpDTO.isCheckingPassWordMatch()) {
            throw new RuntimeException();
        }

        signUpDTO.encodePassword(passwordEncoder.encode(signUpDTO.getPassword()));

        return accountRepository.save(new Account(signUpDTO));
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    public ResponseEntity<Account> login(HttpSession session, LoginDTO loginDTO) {
        Account account = accountRepository.findByEmail(loginDTO.getEmail()).orElseThrow(NotFoundException::new);

        if (!passwordEncoder.matches(loginDTO.getPassword(), account.getPassword())) {
            throw new UnAuthenticationException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(account, headers, HttpStatus.FOUND);
    }
}
