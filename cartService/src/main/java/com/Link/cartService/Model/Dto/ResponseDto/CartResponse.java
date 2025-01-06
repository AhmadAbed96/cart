package com.Link.cartService.Model.Dto.ResponseDto;


import com.Link.cartService.Model.Dto.RequestDto.ItemCartRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {
    private Integer totalItems;

    private Double totalCost;
    private Integer quantity;
    private List<ItemCartRequest> item;
}
