package com.ismhac.jspace.config.security.jwt;

import com.ismhac.jspace.model.RefreshToken;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.repository.RefreshTokenRepository;
import com.ismhac.jspace.util.HashUtils;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    @NonFinal
    @Value("${jwt.secret-key}")
    protected String SECRET_KEY;

    @Value("${jwt.token.expiration}")
    private long TOKEN_EXPIRATION;

    @Value("${jwt.refresh.token.exppiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    private final HashUtils hashUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("jspace")
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .claim("scope", user.getRole().getCode())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can not create token", e);
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("jspace")
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));

            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(hashUtils.hmacSHA512(jwsObject.serialize().trim()))
                    .build();
            refreshTokenRepository.save(refreshToken);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can not create token", e);
            throw new RuntimeException(e);
        }
    }

    public boolean introspect(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SECRET_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return signedJWT.verify(jwsVerifier) && expiryTime.after(new Date());
    }
}
