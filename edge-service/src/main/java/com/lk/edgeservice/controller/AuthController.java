package com.lk.edgeservice.controller;

import com.lk.edgeservice.model.JwtAuthenticationResponse;
import com.lk.edgeservice.service.AuthUserDetailsService;
import com.lk.edgeservice.model.Account;
import com.lk.edgeservice.service.SignupMessageSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Api(value="Authentication Microservice", description = "Service for registration and login")
@RestController
public class AuthController {

    private Logger  logger  = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    SignupMessageSender signupMessageSender;

    @Autowired
    AuthUserDetailsService userDetailsService;

    @ApiOperation(value="Login", response = JwtAuthenticationResponse.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody Account account){
        logger.info("/login request -> "+account.toString());
        MultiValueMap<String, String> authorizationHeader = new LinkedMultiValueMap<>();

        String token = userDetailsService.generateToken(account);
        logger.info("/login token generated -> "+token);
        JwtAuthenticationResponse jwtAuthenticationResponse =   new JwtAuthenticationResponse(token,"Bearer");
        return new ResponseEntity<>(jwtAuthenticationResponse,HttpStatus.OK);


    }

    @ApiOperation(value="Register", response = Account.class)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Account> register(@RequestBody Account account){

        logger.info("/register request -> "+account.toString());
        Account user    =   userDetailsService.save(account);

        //Send sign up email notification
        signupMessageSender.sendSignupMessage(user);

        logger.info("/login response -> "+user.toString());

        return new ResponseEntity<Account>(user, HttpStatus.CREATED);

    }


}
