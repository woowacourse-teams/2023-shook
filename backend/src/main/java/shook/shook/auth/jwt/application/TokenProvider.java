package shook.shook.auth.jwt.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shook.shook.auth.jwt.exception.TokenException;

@Component
public class TokenProvider {

    private final long accessTokenValidTime;
    private final long refreshTokenValidTime;
    private final String secretCode;
    private Key secretKey;

    public TokenProvider(
        final @Value("${jwt.access-token-valid-time}") long accessTokenValidTime,
        final @Value("${jwt.refresh-token-valid-time}") long refreshTokenValidTime,
        final @Value("${jwt.secret-code}") String secretCode
    ) {
        this.accessTokenValidTime = accessTokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
        this.secretCode = secretCode;
    }

    @PostConstruct
    public void generateSecretKey() {
        String encodedSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        secretKey = Keys.hmacShaKeyFor(encodedSecretCode.getBytes());
    }

    public String createAccessToken(final Long userId) {
        return createToken(userId, accessTokenValidTime);
    }

    public String createRefreshToken(final Long userId) {
        return createToken(userId, refreshTokenValidTime);
    }

    private String createToken(final Long userId, final Long validTime) {
        Claims claims = Jwts.claims().setSubject("user");
        claims.put("userId", userId);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + validTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateTokenExpiration(final String token) {
        try {
            final Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            final Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public Claims parseClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            throw new TokenException.InValidTokenException();
        }
    }
}
