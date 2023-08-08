package shook.shook.auth.jwt.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    private final Key secretKey;

    public TokenProvider(
        @Value("${jwt.access-token-valid-time}") final long accessTokenValidTime,
        @Value("${jwt.refresh-token-valid-time}") final long refreshTokenValidTime,
        @Value("${jwt.secret-code}") final String secretCode
    ) {
        this.accessTokenValidTime = accessTokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
        this.secretKey = generateSecretKey(secretCode);
    }

    private Key generateSecretKey(final String secretCode) {
        final String encodedSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodedSecretCode.getBytes());
    }

    public String createAccessToken(final long memberId) {
        return createToken(memberId, accessTokenValidTime);
    }

    public String createRefreshToken(final long memberId) {
        return createToken(memberId, refreshTokenValidTime);
    }

    private String createToken(final long memberId, final long validTime) {
        final Claims claims = Jwts.claims().setSubject("user");
        claims.put("memberId", memberId);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + validTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public Claims parseClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (MalformedJwtException e) {
            throw new TokenException.NotIssuedTokenException();
        } catch (ExpiredJwtException e) {
            throw new TokenException.ExpiredTokenException();
        }
    }
}
