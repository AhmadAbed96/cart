package com.Link.cartService.Exception;

import com.Link.cartService.Exception.ErrorResponse;
import com.Link.cartService.Exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<?> handleNotFoundException(NotFound e) {
        ErrorResponse categoryNotFound = new ErrorResponse(
                e.getMessage(),
                " cart not found",
                LocalDate.now()) {};

        HttpStatusCode httpStatusCode = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(categoryNotFound, httpStatusCode);
    }


    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> handleBadRequestException(BadRequest e) {
        ErrorResponse categoryNotFound = new ErrorResponse(
                e.getMessage(),
                "Bad Request, the cart is null or empty",
                LocalDate.now()) {};

        HttpStatusCode httpStatusCode = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(categoryNotFound, httpStatusCode);
    }

}
