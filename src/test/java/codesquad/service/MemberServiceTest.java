package codesquad.service;

import codesquad.domain.member.Member;
import codesquad.domain.member.MemberLoginDto;
import codesquad.domain.member.MemberRepository;
import codesquad.exception.UnAuthorizedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    MemberService memberService;

    public Member defaultMember() {
        return Member.builder()
                .memberId("esp2ar0@gmail.com")
                .password("q12345678")
                .name("창환")
                .cell1("010")
                .cell2("1111")
                .cell3("2222")
                .build();
    }

    @Test
    public void loginTest_success() throws AccountNotFoundException {
        //given
        Member member = defaultMember();
        MemberLoginDto memberLoginDto = new MemberLoginDto(member.getMemberId(), member.getPassword());

        //when
        when(memberRepository.findByMemberId(member.getMemberId())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword()))
                .thenReturn(memberLoginDto.getPassword().equals(member.getPassword()));
        Member loginMember = memberService.login(memberLoginDto);

        //then
        assertThat(loginMember).isEqualTo(member);
    }

    @Test(expected = UnAuthorizedException.class)
    public void loginTest_password_mismatch() throws AccountNotFoundException {
        //given
        Member member = defaultMember();
        MemberLoginDto memberLoginDto = new MemberLoginDto(member.getMemberId(), "a87654321");

        //when
        when(memberRepository.findByMemberId(member.getMemberId())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword()))
                .thenReturn(memberLoginDto.getPassword().equals(member.getPassword()));
        Member loginMember = memberService.login(memberLoginDto);

        //then
        assertThat(loginMember).isEqualTo(member);
    }

    @Test(expected = AccountNotFoundException.class)
    public void loginTest_account_notFound() throws AccountNotFoundException {
        //given
        Member member = defaultMember();
        MemberLoginDto memberLoginDto = new MemberLoginDto("nowmoon94@naver.com", "abc12345");

        //when
        when(memberRepository.findByMemberId(member.getMemberId())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword()))
                .thenReturn(memberLoginDto.getPassword().equals(member.getPassword()));
        Member loginMember = memberService.login(memberLoginDto);

        //then
        assertThat(loginMember).isEqualTo(member);
    }
}