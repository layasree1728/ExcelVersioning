package com.laya.ExcelVersion.security;

import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtilService {

    private final String SECRET_KEY;

    private final long expiration = 86400000;

    private final UserService userService;

    public JwtUtilService(UserService userService) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGenerator.generateKey();
        this.userService = userService;
        SECRET_KEY = Base64.getEncoder().encodeToString(sk.getEncoded());
    }

    public String generateToken(Authentication authentication) {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        UserDTO user = userService.getUser(authentication);

        return Jwts.builder()
                .subject(oidcUser.getEmail())
                .claims(getClaims(oidcUser, "ROLE_" + user.getRole().getName()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    private Map<String, Object> getClaims(OidcUser oidcUser, String role) {
        //getting claims from the oidc user and adding roles to it from repository
        Map<String, Object> claims = new HashMap<>(oidcUser.getClaims().entrySet().stream()
                .map(claimName -> Map.entry(claimName.getKey(), claimName.getValue().toString()))
                .collect(HashMap::new, (claimName, claimValue) -> claimName.put(claimValue.getKey(), claimValue.getValue()), HashMap::putAll));
        claims.put("role", role);
        return claims;
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            String roleFromToken = extractRole(token);
            String roleFromUser = "ROLE_" + userService.getUserRoleFromEmail(extractEmail(token)).getName();

            log.info("Role from token: {}", roleFromToken);
            log.info("Role from user: {}", roleFromUser);
            if (!roleFromUser.equals(roleFromToken)) {
                log.error("Invalid roles in JWT token");
                return false;
            }

            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public String extractGivenName(String token) {
        return extractClaim(token, claims -> claims.get("given_name", String.class));
    }

    public String extractFamilyName(String token) {
        return extractClaim(token, claims -> claims.get("family_name", String.class));
    }

    public CustomUserPrincipal extractUser(String token) {
        return new CustomUserPrincipal(extractGivenName(token), extractFamilyName(token), extractEmail(token), extractRole(token));
    }

}