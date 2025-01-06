package com.Link.cartService.Model;


import com.Link.cartService.Model.Dto.RequestDto.ItemCartRequest;
import com.Link.cartService.Model.Dto.ResponseDto.ItemResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
@Builder

public class Cart {
    @Id
    private String id;

    private List<ItemCartRequest> item;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double totalPrice;

    @Min(value = 0, message = "Quantity must be at least 0")
    private int totalQuantity;
}
