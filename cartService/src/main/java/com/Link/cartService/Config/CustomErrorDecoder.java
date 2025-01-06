package com.Link.cartService.Config;
import feign.Response;
import feign.codec.ErrorDecoder;
import com.Link.cartService.Exception.ClientErrorException;
import com.Link.cartService.Exception.ServerErrorException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            // Client error, handle it
            return new ClientErrorException(response.status(), "Client error occurred");
        } else if (response.status() >= 500 && response.status() <= 599) {
            // Server error, handle it
            return new ServerErrorException(response.status(), "Server error occurred");
        }
        // Default error handling
        return new ErrorDecoder.Default().decode(methodKey, response);
    }
}
