package com.bucaudio.controller;

import com.bucaudio.common.exception.InvalidUserInformation;
import com.bucaudio.dto.AuthRequest;
import com.bucaudio.entity.UserInfo;
import com.bucaudio.security.JwtService;
import com.bucaudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authenticate.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(authRequest.getUsername()), HttpStatus.ACCEPTED);
        } else {
            throw new UsernameNotFoundException("Authentication Failure...");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody UserInfo userInfo) {
        try {
            return new ResponseEntity<>(userService.addUser(userInfo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('ROLE_AUTHOR') || hasAuthority(ROLE_ADMIN) || hasAuthority(ROLE_PUBLISHER)")
    public ResponseEntity<?> getUser(@RequestParam(name = "email") String email) {
        validateUser(email);

        try {
            return new ResponseEntity<>(userService.getUserInfo(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_AUTHOR') || hasAuthority(ROLE_ADMIN) || hasAuthority(ROLE_PUBLISHER)")
    public ResponseEntity<?> update(@RequestBody UserInfo userInfo) {
        validateUser(userInfo.getEmail());

        try {
            return new ResponseEntity<>(userService.updateUserInfo(userInfo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateUser(String email) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(email, userDetails.getUsername())) {
            throw new InvalidUserInformation("User Details Incorrect...");
        }
    }

}
