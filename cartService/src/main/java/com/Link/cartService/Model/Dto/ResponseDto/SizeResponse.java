package com.Link.cartService.Model.Dto.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeResponse {
    private String name;
    private String sectionId;
    private double price;
}
