package org.among.usermodule.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.among.usermodule.user.security.CustomAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final String secretKey;
    private final Long accessTokenValidityInMilliSeconds;


    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInSeconds) {
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliSeconds = accessTokenValidityInSeconds * 1000;
    }

    /**
     * 로그인 성공 시 토큰 발급
     * @param authentication {Authentication}
     * @return token {String}
     */
    public String createAccessToken(Authentication authentication) {
        // Authentication -> UserDetails 확인...
        String email = (String) authentication.getPrincipal();
        String username = ((CustomAuthenticationToken) authentication).getUsername();
        Date createdTime = new Date();
        Date expiredTime = new Date(createdTime.getTime() + this.accessTokenValidityInMilliSeconds);
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id", email)
                .claim("username", username)
                .setIssuedAt(createdTime)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * API 요청 시 들어온 토큰을 통해 Authentication 획득
     * @param token {String}
     * @return authentication {Authentication}
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new CustomAuthenticationToken(claims.get("id").toString(), null, claims.get("username").toString(), null);
    }

    /**
     * API 요청 시 들어온 토큰 유효성 검사
     * @param token {String}
     * @return {boolean}
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 존재하지 않습니다.");
        }
        return false;
    }
}
