package com.Link.cartService.Model.Dto.ResponseDto;

import com.Link.cartService.Model.Status;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public class Size {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
//    @ValidSection
    private String sectionId;
    @NotBlank
    private double price;
    private Status status;
}
