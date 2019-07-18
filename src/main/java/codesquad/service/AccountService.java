package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.NotFoundException;
import codesquad.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Account login(HttpSession session, LoginDTO loginDTO) {
        Account account = accountRepository.findByEmail(loginDTO.getEmail()).orElseThrow(NotFoundException::new);

        if (!passwordEncoder.matches(account.getPassword(), loginDTO.getPassword())) {
            throw new UnAuthenticationException();
        }

        return account;
    }
}
