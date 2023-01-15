package com.cntclst.idv5.controllers;

import com.cntclst.idv5.config.JWTTokenHelper;
import com.cntclst.idv5.models.MyUserDetails;
import com.cntclst.idv5.models.User;
import com.cntclst.idv5.models.UserDetailsServiceImpl;
import com.cntclst.idv5.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping(
            value ="/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getUserName(),
                                authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        System.out.println(authentication.getPrincipal());

        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        String jwtToken = null;
        try {
            jwtToken = jwtTokenHelper.generateToken(user.getUsername());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user){
        MyUserDetails userObj = (MyUserDetails) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setId(userObj.getID());
        userInfo.setUserName(userObj.getUsername());
        userInfo.setRole( userObj.getOnlyOneRole() );

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/user/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest userCreateRequest){

        User u = new User();
        u.setUsername( userCreateRequest.getUserName());
        u.setPassword( passwordEncoder.encode( userCreateRequest.getPassword()));
        u.setRole("ROLE_USER");
        u.setEnabled((byte)1 );

        try {
            userDetailsService.createUser(u);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/user/updatePassword")
    public ResponseEntity<?> updatePassword(Principal user, @RequestBody PasswordUpdateRequest passwordUpdateRequest){

        try {
            userDetailsService.updatePassword(
                    user.getName(),
                    passwordEncoder.encode(passwordUpdateRequest.getPassword()));
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/user/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteRequest deleteRequest){

        userDetailsService.deleteUser(deleteRequest.getId());

        return ResponseEntity.ok("Success");
    }


}
