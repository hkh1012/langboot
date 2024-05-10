package com.hkh.ai.component;

import com.hkh.ai.common.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * @author citygu
 */
@Slf4j
@Service
public class TokenService {
    @Value("${system.auth.token.secret}")
    private String secret;

    @Value("${system.auth.token.expire}")
    public Long expiredTime;

    public Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .build()
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            log.error("token解析异常", ex.getMessage(), ex);
            ex.printStackTrace();
            throw new TokenException("token解析异常");
        }
    }

    public String createJWT(String name, String userId, String role, String audience, String issuer) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Date now = new Date();

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                .claim("role", role)
                .claim("unique_name", name)
                .claim("userid", userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signingKey, signatureAlgorithm);
        //添加Token过期时间
        if (expiredTime >= 0) {
            long expMillis = now.getTime() + expiredTime;
            Date exp = new Date(expMillis);
            long beforeMillis = now.getTime() - 2000;
            Date before = new Date(beforeMillis);
            builder.setExpiration(exp).setNotBefore(before);
        }

        //生成JWT
        return builder.compact();
    }



}
