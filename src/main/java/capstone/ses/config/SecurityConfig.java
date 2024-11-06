package capstone.ses.config;

import capstone.ses.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final CorsConfig corsConfig;
    private final JwtFilter jwtFilter;

    // 시큐리티 필터는 다른 어떤 필터보다 먼저 실행됨
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((manager) -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 로그인 안함
                .addFilter(corsConfig.corsFilter()) // @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증 (O)
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 안함
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/oauth/**", "/api/v1/soundeffect/**", "/api/health").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
