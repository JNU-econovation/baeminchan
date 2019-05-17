package codesquad.web;

import codesquad.domain.member.MemberLoginDto;
import codesquad.domain.member.MemberRequestDto;
import codesquad.service.MemberService;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/members")
public class ApiMemberController {

    @Autowired
    private MemberService memberService;

    private static final Logger log = LoggerFactory.getLogger(ApiMemberController.class);

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        memberService.add(memberRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginDto memberLoginDto, HttpSession httpSession) throws AccountNotFoundException {
        httpSession.setAttribute(HttpSessionUtils.MEMBER_SESSION_KEY, memberService.login(memberLoginDto));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
}