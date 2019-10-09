package codesquad.domain;

import codesquad.dto.SignUpDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static codesquad.domain.AccountType.ADMIN;
import static codesquad.domain.AccountType.USER;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "email")
    private String email;

    @Lob
    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "user_name")
    private String name;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType = USER;

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public Account setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Account changeToAdmin() {
        this.accountType = ADMIN;
        return this;
    }

    public Account changeToUser() {
        this.accountType = USER;
        return this;
    }

    public Account build() {
        return new Account(this);
    }

    private Account(Account account) {
        this.id = account.id;
        this.email = account.email;
        this.password = account.password;
        this.name = account.name;
        this.phoneNumber = account.phoneNumber;
        this.accountType = account.accountType;
    }

    public Account(SignUpDTO signUpDTO) {
        this.email = signUpDTO.getEmail();
        this.password = signUpDTO.getPassword();
        this.name = signUpDTO.getName();
        this.phoneNumber = signUpDTO.getPhoneNumber();
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountType=" + accountType +
                '}';
    }

    public boolean isAdmin() {
        return this.accountType.equals(ADMIN);
    }

    public boolean hasSamePhoneNumber(String phoneNumber) {
        return this.phoneNumber.equals(phoneNumber);
    }
}
