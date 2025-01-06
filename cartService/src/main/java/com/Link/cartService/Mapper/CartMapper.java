package com.Link.cartService.Mapper;


import com.Link.cartService.Model.Cart;
import com.Link.cartService.Model.Dto.RequestDto.ItemCartRequest;
import com.Link.cartService.Model.Dto.ResponseDto.CartResponse;
import com.Link.cartService.Model.Dto.ResponseDto.ItemResponse;
import com.Link.cartService.Model.Dto.ResponseDto.ItemSizeResponse;
import com.Link.cartService.proxy.MenuProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
    public class CartMapper {

        @Autowired
        private MenuProxy menuProxy;

        public CartResponse mapCartToCartResponse(Cart cart) {
            List<ItemCartRequest> itemCartRequests = cart.getItem().stream()
                    .map(item -> {
                        ItemSizeResponse itemResponse = menuProxy.getItemSizeName(item.getItemId(),item.getSizeId()).getBody();
                        if (itemResponse == null) {
                            throw new RuntimeException("Item not found for id: " + item.getItemId());
                        }

                        return ItemCartRequest.builder()
                                .itemId(itemResponse.getItemName()) // Map itemId to item name
                                .sizeId(itemResponse.getSizeName()) // Map sizeId to size name
                                .quantity(item.getQuantity())
                                .build();
                    })
                    .collect(Collectors.toList());

            return CartResponse.builder()
                    .totalItems(itemCartRequests.size())
                    .totalCost(cart.getTotalPrice())
                    .quantity(cart.getTotalQuantity())
                    .item(itemCartRequests)
                    .build();
        }
    }
