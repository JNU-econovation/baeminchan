package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.DuplicatedAccountException;
import codesquad.exception.NotFoundAccountException;
import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnMatchedCheckingPasswordException;
import codesquad.sequrity.HttpSessionUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account create(SignUpDTO signUpDTO) {

        if (!signUpDTO.isCheckingPassWordMatch()) {
            throw new UnMatchedCheckingPasswordException();
        }

        if (accountRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw new DuplicatedAccountException();
        }

        signUpDTO.encodePassword(passwordEncoder.encode(signUpDTO.getPassword()));

        return accountRepository.save(new Account(signUpDTO));
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(NotFoundAccountException::new);
    }

    public Account login(HttpSession session, LoginDTO loginDTO) {
        Account account = findByEmail(loginDTO.getEmail());

        matchPassword(loginDTO.getPassword(), account.getPassword());
        session.setAttribute(HttpSessionUtils.SESSIONED_USER, account);

        return account;
    }

    public Account login(LoginDTO loginDTO) {
        Account account = findByEmail(loginDTO.getEmail());

        matchPassword(loginDTO.getPassword(), account.getPassword());

        return account;
    }

    private void matchPassword(String requestPassword, String actualPassword) {
        if (!passwordEncoder.matches(requestPassword, actualPassword)) {
            throw new UnAuthenticationException();
        }
    }
}
