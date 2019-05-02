package com.lk.accountservice.controller;


import com.lk.accountservice.model.Account;
import com.lk.accountservice.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Account Microservice", description = "User account management service")
@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private static final Logger logger  = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "Get Logged In user details", response = Account.class)
    @GetMapping("/me")
    public ResponseEntity<Account> currentLoggedInUser(@RequestHeader("userId") String userId){

        logger.info("Logged In user Id -> "+userId);
        Account account =   accountService.getAccountDetails(Long.parseLong(userId));
        return new ResponseEntity<Account>(account, HttpStatus.OK);

    }

    @ApiOperation(value="Get user details for given user Id", response = Account.class)
    @GetMapping("/users/{userId}")
    public ResponseEntity<Account> accountDetails(@PathVariable Long userId){
        logger.info("Get account details request for user Id -> "+userId);
        Account account =   accountService.getAccountDetails(userId);
        return new ResponseEntity<Account>(account, HttpStatus.OK);

    }

}
