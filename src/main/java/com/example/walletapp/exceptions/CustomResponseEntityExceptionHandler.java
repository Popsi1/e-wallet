//package com.example.walletapp.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@RestController
//@ControllerAdvice
//public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(WalletException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(UserAlreadyHasWalletException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(UserDoesNotExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(WalletIdDoesNotExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(AccountNumberNotAssociatedWithWalletException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(InsufficientBalanceInWalletException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(KycAlreadyExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(KycMasterDoesNotExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(kycLevelAlreadyExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(Exception ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(EmailAlreadyExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public final ResponseEntity<?> handleWalletException(PhoneNumberAlreadyExistException ex, WebRequest request){
//        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//}
