package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.FindingEmailDTO;
import codesquad.dto.FindingPasswordDTO;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.*;
import codesquad.sequrity.HttpSessionUtils;
import codesquad.sequrity.RandomPasswordGenerator;
import codesquad.utils.ExceptionMessages;
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

    public String findId(FindingEmailDTO findingIdDTO) {
        Account account = accountRepository.findByName(findingIdDTO.getName())
                .orElseThrow(() -> new NotFoundAccountException(ExceptionMessages.NO_ACCOUNT_WITH_SUCH_INFO));

        if (!account.hasSamePhoneNumber(findingIdDTO.getPhoneNumber())) {
            throw new NotFoundAccountException(ExceptionMessages.NO_ACCOUNT_WITH_SUCH_INFO);
        }

        return account.getEmail();
    }

    public String findPassword(FindingPasswordDTO findingPasswordDTO) {
        Account account = accountRepository.findByEmail(findingPasswordDTO.getEmail())
                .orElseThrow(() -> new NotFoundAccountException(ExceptionMessages.NO_ACCOUNT_WITH_SUCH_INFO));

        if (!account.hasSameName(findingPasswordDTO.getName()) || !account.hasSamePhoneNumber(findingPasswordDTO.getPhoneNumber())) {
            throw new NotFoundAccountException(ExceptionMessages.NO_ACCOUNT_WITH_SUCH_INFO);
        }

        String newPassword = RandomPasswordGenerator.generatePassword();

        account.setPassword(newPassword);
        accountRepository.save(account);

        return newPassword;
    }
}
