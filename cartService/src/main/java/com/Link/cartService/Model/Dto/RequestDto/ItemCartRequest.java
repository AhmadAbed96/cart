package com.Link.cartService.Model.Dto.RequestDto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCartRequest {
    @NotBlank(message = "Item ID must not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Invalid itemId format")
    private String itemId;

    @NotBlank(message = "Size ID must not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Invalid sizeId format")
    private String sizeId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

}
