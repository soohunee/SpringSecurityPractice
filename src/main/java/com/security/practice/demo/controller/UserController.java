package com.security.practice.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.practice.demo.domain.Role;
import com.security.practice.demo.domain.RoleToUserForm;
import com.security.practice.demo.domain.User;
import com.security.practice.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class UserController {
    private final UserService userService;

    private final static String BEARER = "Bearer ";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok()
                .body(userService.getUsers());
    }

    @PostMapping(path = "/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/user/save")
                        .toUriString()
        );

        return ResponseEntity.created(uri)
                .body(userService.saveUser(user));
    }

    @PostMapping(path = "/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/role/save")
                        .toUriString()
        );
        return ResponseEntity.created(uri)
                .body(userService.saveRole(role));
    }

    @PostMapping(path = "/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm roleToUserForm) {
        userService.addRoleToUser(roleToUserForm.getUsername(), roleToUserForm.getRolename());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            try {
                String refreshToken = authorizationHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();

                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username); //make sure this user exists

                String accessToken = JWT.create()
                        .withSubject(username) //usernames are unique values
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",
                                user.getRoles()
                                        .stream()
                                        .map(Role::getName)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);

                log.info("Issue new access token to user {}", username);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }
            catch (Exception exception) {
                log.error("Error logging in : {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                Map<String, String> error = new HashMap<>();
                error.put("errMsg", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            log.error("Refresh token is missing");
            throw new RuntimeException("Refresh token is missing");
        }
    }
}