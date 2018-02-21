package org.jboss.quickstarts.jaxrsjwt.auth;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.wildfly.security.pem.Pem;
import org.wildfly.security.util.CodePointIterator;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.security.PrivateKey;

@ApplicationScoped
public class JwtManager {
    static {
        String privateKeyString = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDLjkvMhyfNDMQx\n" +
                "sn+9sVTTzRqqr+QvNKwIkrAixjYUXmYDIfyAod/044AiaxqVXl6Ws4bLbt9Q0gmU\n" +
                "GYCOHJbjAkCGz89+u5uGa1mEElXh/gIYQlvuG15YPyH5M+vbMPPTD3k+JsKTWluq\n" +
                "eLL0BEEs382RkTl9IeYLwjIxcPAkjngHsL+u1kq1baeEvjmrvFS8zTiaGGsqED1x\n" +
                "7UMP5bSyTg6CnaY9ffKvvX7WyJg9R5tCBeZMw9TZnoLbuF7htMBvxeENRsG8VCKH\n" +
                "jFNqzhgmpulH55pP4+s8Lv6YgVzIc8ue1OModWrFN5RHjHpXFHAFc7GGHkIpM9l5\n" +
                "WvyYEjnfAgMBAAECggEAXBxQAIVIZWlZfoOfel88W3t8jbB6OUJEdryqJ5I59o2g\n" +
                "69n9QNf/V/l6ihidNuo1M0yZuoS5WQEyhGnl2mjlfmQOfvKammaawgDhyPnjExi2\n" +
                "KfQdVWndD2HCJdpw6AcGzoM6Af3YHoXM5R8pG3Kxa3T6uNxWf2FPkBKYU/cfM+rV\n" +
                "mtak8BL/IY3XoaPA+gJ79O8jIV61cumB2+1sKZJit2kUojQSXRYK5rBI9lTiuLuq\n" +
                "R1xna8TuZmFgF1TC5cOW8vEswdvbHLgqjmCc9r17Ccdpu9q2WERyL6bUipVLg3Or\n" +
                "LTWgK9WYw+ODKRF7mgq0S6myPaj52zajifE9Zd0rAQKBgQDmW3hm0hyHq3kRQhHS\n" +
                "kJKTThqM3I0eGgPN1fu8itMy0ttJsXY04BTA6AYZeJ27ZJn/PLIwWIbV5NmbOxK3\n" +
                "phoBWIDMHnnB/IQSjMtliFauJqHFzgPrMmOLeFyq+xFsNHD0pSM6wY+uqpq98Q7V\n" +
                "YczV0AKYns32VKNWLKAOOOgfTwKBgQDiNw9lt9lUF3Ktd/UZS+N4uALqlEzh3q5E\n" +
                "m5q31hcqkT3EGRtjIPsvLyN5wIZ+iHIxb/b5xJ6Okb28ZAp1oIMWdm5zT0Uv4Mtf\n" +
                "aM46i5HZHyTxucyZeWTvySFcyZ5uvd4cyTU8qHQGVD1WZuL7Y5SvoYkl0rTZgJXF\n" +
                "qPN+60MYcQKBgE6OEOAXdM07R30AJ+3DiYicUOeKasFCI9v0xfUE7oLdbxSRZPsh\n" +
                "E1K5jSLwg/Gcm/tzalycQ9b7RM3v9jEUfrfIw5tYJTNm9m8Q/mewd8bJu88lsyZo\n" +
                "t5OfRxKzrbxVodN74SivS11IJp7xbpqL0Ht1hP0g4qHHOTAw5aAT/ex3AoGAY0V2\n" +
                "ZFUB8n03vhheXvOGp3tZDEh4VMa8Ay+l/fbdw7hpOp0p/56BnHs7PjrWysBmHHve\n" +
                "Dk3Spw4eDYZ5cJU38auXXNL0scYceYostlR5dKbON6Ypu1Aik3fYRDfWZxK7XjaO\n" +
                "PkhOYlilcj1Ebr2MimaBITAOXUSodU1nTdNAZoECgYEA3glrLRCuWwuIQKCFgVTY\n" +
                "OsoqIRH5+CLuPnbTTiXSai3JR0wNOQ/co4Nx/BScclmZAFhGb7Dsu5qTwa/KViig\n" +
                "cvnzA2Pooz3jeCys/2kwL9A88yO6Cdf8jvnoSeS8T0bq3zuLdBpG5wxlTUl//qPy\n" +
                "SYtduAZ9Er7ecS7EiEl9fQg=\n" +
                "-----END PRIVATE KEY-----\n";
        privateKey = Pem.parsePemContent(CodePointIterator.ofString(privateKeyString)).next().tryCast(PrivateKey.class);
    }

    private static final PrivateKey privateKey;
    private static final int TOKEN_VALIDITY = 14400;
    private static final String CLAIM_ROLES = "groups";
    private static final String ISSUER = "quickstart-jwt-issuer";
    private static final String AUDIENCE = "jwt-audience";

    public String createJwt(final String subject, final String[] roles) throws Exception {
        JWSSigner signer = new RSASSASigner(privateKey);
        JsonArrayBuilder rolesBuilder = Json.createArrayBuilder();
        for (String role : roles) rolesBuilder.add(role);

        JsonObjectBuilder claimsBuilder = Json.createObjectBuilder()
                .add("sub", subject)
                .add("iss", ISSUER)
                .add("aud", AUDIENCE)
                .add(CLAIM_ROLES, rolesBuilder.build())
                .add("exp", ((System.currentTimeMillis() / 1000) + TOKEN_VALIDITY));

        JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(new JOSEObjectType("jwt")).build(),
                new Payload(claimsBuilder.build().toString()));

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }
}
