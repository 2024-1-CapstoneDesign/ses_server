package capstone.ses.service;

import capstone.ses.domain.member.Member;
import capstone.ses.auth.jwt.JwtTokenProvider;
import capstone.ses.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Member findMemberByAccessToken(String accessToken) {
        return memberRepository.findByEmail(jwtTokenProvider.getEmailFromAccessToken(accessToken.substring(7))).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
    }

}
