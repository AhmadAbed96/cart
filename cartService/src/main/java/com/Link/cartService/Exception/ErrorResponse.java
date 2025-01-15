package com.Link.cartService.Exception;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class ErrorResponse {
    private String message;

    private String Url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm:ss")
    private Date timeStamp;

    public ErrorResponse(){
        this.timeStamp = new Date();
    }

    public ErrorResponse(String message, String url){
        this();
        this.message = message;
        this.Url = url;

    }
}
