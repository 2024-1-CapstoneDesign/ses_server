package capstone.ses.service;

import capstone.ses.auth.jwt.AuthTokensGenerator;
import capstone.ses.domain.member.Member;
import capstone.ses.dto.auth.request.GoogleApiClient;
import capstone.ses.dto.auth.request.GoogleInfoResponse;
import capstone.ses.dto.jwt.AuthTokens;
import capstone.ses.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator tokensGenerator;

    private final GoogleApiClient googleApiClient;

    public AuthTokens loginByAccessToken(String accessToken) {
        // 1. google accessToken으로 사용자 정보 받아오기
        GoogleInfoResponse googleInfoResponse = googleApiClient.googleInfo("Bearer" + accessToken);


        String email = googleInfoResponse.email();

        Member member;
        // 1-1. 사용자 정보가 db에 없을 경우
        if (!memberRepository.existsByEmail(email)) {
            member = memberRepository.save(
                    Member.builder()
                            .name(googleInfoResponse.name())
                            .email(email)
                            .picture(googleInfoResponse.picture())
                            .build()
            );
        } else {
            member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("해당 이메일로 유저가 존재하지 않습니다."));
        }

        // 2. 토큰 생성 후 리턴
        return tokensGenerator.generate(member.getEmail());
    }
}
