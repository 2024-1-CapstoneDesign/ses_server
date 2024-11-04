package capstone.ses.service;

import capstone.ses.domain.member.Member;
import capstone.ses.dto.jwt.AuthTokens;
import capstone.ses.jwt.AuthTokensGenerator;
import capstone.ses.jwt.JwtTokenProvider;
import capstone.ses.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator tokensGenerator;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokens loginByAccessToken(String accessToken) {
        // 1. google accessToken으로 사용자 정보 받아오기
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(authHeaders);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, entity, Map.class);
        Map userInfo = userInfoResponse.getBody();

        String email = (String) userInfo.get("email");
        String picture = (String) userInfo.get("picture");
        String name = (String) userInfo.get("name");

        Member member;
        // 1-1. 사용자 정보가 db에 없을 경우
        if (!memberRepository.existsByEmail(email)) {
            member = memberRepository.save(
                    Member.builder()
                            .name(name)
                            .email(email)
                            .picture(picture)
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
