package com.cntclst.idv5.controllers;

import com.cntclst.idv5.config.JWTTokenHelper;
import com.cntclst.idv5.models.MyUserDetails;
import com.cntclst.idv5.models.UserDetailsServiceImpl;
import com.cntclst.idv5.requests.AuthenticationRequest;
import com.cntclst.idv5.requests.LoginResponse;
import com.cntclst.idv5.requests.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


}
