package ir.ronad.courierManager.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ir.ronad.courierManager.security.otp.OtpAuthenticationToken;
import ir.ronad.courierManager.security.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ir.ronad.courierManager.security.jwt.JwtConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private JwtParser jwtParser;

    @Value("${ronad.security.authentication.jwt.base64-secret}")
    private String base64Secret;

    @PostConstruct
    public void initialize() {
        byte[] keyBytes;
        String secret = new JwtProperties().getSecret();
        if (!StringUtils.isEmpty(secret)) {
            log.warn("Warning: the JWT key used is not Base64-encoded. " +
                    "We recommend using the security.authentication.jwt.base64-secret` key for optimum security.");
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(base64Secret);
        }
        Key key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token");
            log.trace("Invalid JWT token trace", e);
        }
        return false;
    }

    public Authentication extractAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String subject = claims.getSubject();
        if (subject.startsWith(PHONE_NUMBER_PREFIX)) {
            String phoneNumber = subject.substring(PHONE_NUMBER_PREFIX.length());
            return new OtpAuthenticationToken(phoneNumber, null, authorities);
        } else if (subject.startsWith(EMAIL_ADDRESS_PREFIX)) {
            String emailAddress = subject.substring(EMAIL_ADDRESS_PREFIX.length());
            return new UsernamePasswordAuthenticationToken(emailAddress, null, authorities);
        }

        return null;
    }

    public Long getWarehouseId(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.get("warehouseId", Long.class);
    }

    public List<Long> getSellerIds(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        List<Integer> sellerIds = claims.get("sellerIds", ArrayList.class);
        if (sellerIds != null) {
            List<Long> result = sellerIds.stream()
                    .mapToLong(Integer::longValue)
                    .boxed()
                    .collect(Collectors.toList());
            return result;
        } else return null;
    }

    public String getName(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.get("name", String.class);
    }

    public String getRole(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    public Long getPartnerId(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.get("partnerId", Long.class);
    }

    public Long getUserId(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.get("userId", Long.class);
    }
}
