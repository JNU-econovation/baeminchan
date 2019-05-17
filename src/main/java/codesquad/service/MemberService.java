package codesquad.service;

import codesquad.domain.member.Member;
import codesquad.domain.member.MemberRepository;
import codesquad.domain.member.MemberLoginDto;
import codesquad.domain.member.MemberRequestDto;
import codesquad.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Logger log = LoggerFactory.getLogger(MemberService.class);

    @Transactional
    public Member add(MemberRequestDto memberRequestDto) {
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        return memberRepository.save(memberRequestDto.toEntity());
    }

    public Member login(MemberLoginDto memberLoginDto) throws AccountNotFoundException {
        Member member = memberRepository.findByMemberId(memberLoginDto.getMemberId())
                .orElseThrow(AccountNotFoundException::new);

        if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
            throw new UnAuthorizedException("비밀번호가 일치하지 않습니다");
        }
        return member;
    }
}
