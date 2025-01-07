package com.Link.cartService.Config;
import feign.Response;
import feign.codec.ErrorDecoder;
import com.Link.cartService.Exception.ClientErrorException;
import com.Link.cartService.Exception.ServerErrorException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String message = response.reason();

        if (status >= 400 && status < 500) {
            return new ClientErrorException(status, message);
        } else if (status >= 500) {
            return new ServerErrorException(status, message);
        }

        return new RuntimeException("Generic error");
    }
}
