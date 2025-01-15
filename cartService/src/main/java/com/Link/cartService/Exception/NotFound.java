package com.Link.cartService.Exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class NotFound extends ApiBaseException {
    public NotFound(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
