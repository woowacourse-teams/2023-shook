package shook.shook.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shook.shook.auth.exception.TokenException;

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

    public String createAccessToken(final long memberId, final String nickname) {
        return createToken(memberId, nickname, accessTokenValidTime);
    }

    public String createRefreshToken(final long memberId) {
        final Claims claims = Jwts.claims().setSubject("user");
        claims.put("memberId", memberId);
        final Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    private String createToken(final long memberId, final String nickname, final long validTime) {
        final Claims claims = Jwts.claims().setSubject("user");
        claims.put("memberId", memberId);
        claims.put("nickname", nickname);
        final Date now = new Date();

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
        } catch (MalformedJwtException | SignatureException e) {
            throw new TokenException.NotIssuedTokenException(Map.of("Token", token));
        } catch (ExpiredJwtException e) {
            throw new TokenException.ExpiredTokenException(Map.of("Token", token));
        }
    }
}
