package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.DuplicatedAccountException;
import codesquad.exception.NotFoundAccountException;
import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnMatchedCheckingPasswordException;
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
    private static final String SESSIONED_USER = "sessionedUser";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Void> create(SignUpDTO signUpDTO) {

        if (!signUpDTO.isCheckingPassWordMatch()) {
            throw new UnMatchedCheckingPasswordException();
        }

        if (accountRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw new DuplicatedAccountException();
        }

        signUpDTO.encodePassword(passwordEncoder.encode(signUpDTO.getPassword()));
        Account account = new Account(signUpDTO);

        accountRepository.save(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(NotFoundAccountException::new);
    }

    public ResponseEntity<Account> login(HttpSession session, LoginDTO loginDTO) {
        Account account = accountRepository.findByEmail(loginDTO.getEmail()).orElseThrow(NotFoundAccountException::new);

        if (!passwordEncoder.matches(loginDTO.getPassword(), account.getPassword())) {
            throw new UnAuthenticationException();
        }

        session.setAttribute(SESSIONED_USER, account);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(account, headers, HttpStatus.FOUND);
    }
}
