package com.lk.edgeservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDetails> handleException(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {

        ErrorDetails errorDetails   =   new ErrorDetails(new Date(), ex.getMessage(), "This is just a global exception handler for demonstration purpose. ");
        return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
