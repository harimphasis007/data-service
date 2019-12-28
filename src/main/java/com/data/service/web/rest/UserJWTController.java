package com.data.service.web.rest;


//import io.github.jhipster.sample.security.jwt.JWTConfigurer;
//import io.github.jhipster.sample.security.jwt.TokenProvider;
//import io.github.jhipster.sample.web.rest.vm.LoginVM;
//
//import com.codahale.metrics.annotation.Timed;

import com.data.service.security.jwt.TokenProvider;
import com.data.service.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;

//import org.springframework.security.authentication.AuthenticationManager;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    //private final AuthenticationManager authenticationManager;

    public UserJWTController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        System.out.println("authorize called ");
        HashSet<SimpleGrantedAuthority> authorities = new HashSet();
        if (loginVM.getRole() == null || loginVM.getRole().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority(loginVM.getRole()));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword(), authorities);

        String jwt = tokenProvider.createToken(authenticationToken, false);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        JWTToken jwtToken = new JWTToken(jwt);
        System.out.println("Token :" + jwtToken.getIdToken());
        return new ResponseEntity<>(jwtToken, httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
