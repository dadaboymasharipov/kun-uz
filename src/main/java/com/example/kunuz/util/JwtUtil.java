package com.example.kunuz.util;

import com.example.kunuz.dto.jwt.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "very_long_mazgiskjdh2skjdhadasdasg7SomethinsInsideThisLeyfgdfgdwiodjiwojoeweiwomieowjwdoeiwjdmioewwidewjioewmewimwdoiewjdowaThatDoesn'tMakeSenseksmdnduiwjiewododwlamssiwfd";

    public static String encode(String profileId, String username, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

//        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512"/*sa.getJcaName()*/);
        jwtBuilder.signWith(secretKeySpec);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);
        jwtBuilder.claim("username", username);

        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("KunUzTest");

        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token){
//        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512"/*sa.getJcaName()*/);
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        String id = (String) claims.get("id");
        String role = (String) claims.get("role");
        String username = (String) claims.get("username");
        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, profileRole, username);
        }
        return new JwtDTO(id);
    }



}
