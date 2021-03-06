package com.mercateo.spring.security.jwt.token.extractor;

import java.util.Optional;

import com.mercateo.spring.security.jwt.token.claim.JWTClaim;

import io.vavr.collection.List;
import io.vavr.collection.Map;

class InnerClaimsWrapper {

    Map<String, JWTClaim> wrapInnerClaims(List<JWTClaim> claims) {
        return claims.groupBy(JWTClaim::name).mapValues(this::wrapGroupedClaims);
    }

    private JWTClaim wrapGroupedClaims(List<JWTClaim> claims) {
        final List<JWTClaim> reverse = claims.reverse();

        Optional<JWTClaim> innerClaim = Optional.empty();

        for (JWTClaim jwtClaim : reverse) {
            innerClaim = Optional.of(JWTClaim.builder().from(jwtClaim).innerClaim(innerClaim).build());
        }

        // noinspection ConstantConditions
        return innerClaim.get();
    }
}
