package uz.pdp.transactionreports.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.service.UserService;
import uz.pdp.transactionreports.utils.enums.Role;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {
    private final UserService userService;
    public JwtProvider(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.token.secret.expiry}")
    private Integer expireDays;

    @Value("${jwt.token.secret.key}")
    private String secretKeyString;

    @Cacheable(value = "tokenGenerate", key = "#username")
    public String generateToken(String username) {
        UserCreateReadDto byUsername = userService.getByUsername(username);
        Date expire = new Date(System.currentTimeMillis() + expireDays * 24 * 60 * 60 * 1000);
        return Jwts.builder()
                .subject(username)
                .expiration(expire)
                .issuedAt(new Date())
                .signWith(getKey())
                .claim("role", byUsername.getRole())
                .compact();
    }
    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        String roleName = getClaims(token).get("role", String.class);
        Role role = Role.valueOf(roleName);
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    public Claims getClaims(String token) {
        return ((Claims)(Jwts.parser().verifyWith(getKey()).build().parse(token).getPayload()));
    }

    @Cacheable(value = "tokenSubject", key = "#token")
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }


    private SecretKey getKey() {
        byte[] decode = Base64.getDecoder().decode(secretKeyString);
        return Keys.hmacShaKeyFor(decode);
    }
}
