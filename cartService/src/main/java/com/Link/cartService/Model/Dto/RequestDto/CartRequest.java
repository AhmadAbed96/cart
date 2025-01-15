package com.Link.cartService.Model.Dto.RequestDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    @Valid
    private List<ItemCartRequest> items;
}
