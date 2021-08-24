package com.example.sbbank.security.jwt;

import com.example.sbbank.entity.Authority;
import com.example.sbbank.exception.InvalidTokenException;
import com.example.sbbank.payload.response.AccessTokenResponseDto;
import com.example.sbbank.payload.response.TokenResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private static final long tokenExpiration = 1000 * 60 * 5L;
    private static final long accessTokenExpiration = 1000 * 60 * 60 * 24 * 5L;

    @Value("${jwt.secret}")
    private String secretkey;

    protected String init() {
        return Base64.getEncoder().encodeToString(secretkey.getBytes(StandardCharsets.UTF_8));
    }

    public AccessTokenResponseDto createAccessToken(String userPk, Authority role) {
        Date now = new Date();

        String token = Jwts.builder()
                .setSubject(userPk)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, init())
                .compact();

        return new AccessTokenResponseDto(token);
    }

    public TokenResponseDto createToken(String userPk, Authority role) {
        Date now = new Date();

        String token = Jwts.builder()
                .setSubject(userPk)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS256, init())
                .compact();

        return new TokenResponseDto(token);
    }

    public boolean validateToken(String jwtToken) {
        try {
            return !getUserPk(jwtToken)
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims getUserPk(String token) {
        return Jwts.parser().setSigningKey(init()).parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
