package com.Link.cartService.Controller;


import com.Link.cartService.Model.Cart;
import com.Link.cartService.Model.Dto.RequestDto.CartRequest;
import com.Link.cartService.Model.Dto.RequestDto.ItemCartRequest;
import com.Link.cartService.Model.Dto.ResponseDto.CartResponse;
import com.Link.cartService.Model.Dto.ResponseDto.ItemResponse;
import com.Link.cartService.Model.Dto.ResponseDto.ItemSizeResponse;
import com.Link.cartService.Model.Dto.ResponseDto.SizeResponse;
import com.Link.cartService.Service.CartService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@Validated
@Slf4j
@RequestMapping("/cart")
@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("getSectionCount/")
    public ResponseEntity<Long> getSectionCount(){
        return ResponseEntity.ok(cartService.getSectionCount());
    }

    @GetMapping("getItemById/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable String id){
        return ResponseEntity.ok(cartService.getItemById(id));
    }

    @PostMapping("/createCart")
    public ResponseEntity<String> createCart(@RequestBody @Valid CartRequest items)
    {
        log.info("inside controller");
        cartService.createCart(items.getItems());
        return ResponseEntity.ok("the cart created");
    }

    @PostMapping("/kkkk")
    public ResponseEntity<String> createCarthh( @Valid @RequestBody  ItemCartRequest items)
    {
        return ResponseEntity.ok("a");
    }
//    @PutMapping("/updateCart/{id}")
//    public ResponseEntity<String> updateCart(@PathVariable String id, @RequestBody List<ItemCartRequest> updatedItems){
//        cartService.updateCart(id, updatedItems);
//        return ResponseEntity.ok("the item updated");
//    }

    @GetMapping("/getStatus")
    public ResponseEntity<Boolean> checkStatus(String itemId, String sizeId){
        return ResponseEntity.ok(cartService.getStatus(itemId, sizeId));
    }

    @PutMapping("/addItem")
    public ResponseEntity<String> addItemToCart(@Valid @RequestBody List<ItemCartRequest> items, String cartId) {
        cartService.addItemsToCart(cartId, items);
        return ResponseEntity.ok("the cart updated");
    }

    @GetMapping("getCart/{id}")
    public ResponseEntity<CartResponse> getCartById(@Valid @PathVariable String id){

        return ResponseEntity.ok(cartService.getCartById(id));

    }

    @PutMapping("increaseItem/{cartId}/{itemId}/{sizeId}")
    public ResponseEntity<String> increaseCartItem(
            @Valid @PathVariable String cartId
            , @PathVariable String itemId,
            @PathVariable String sizeId) {
        cartService.increaseItem(cartId, itemId, sizeId);
        return ResponseEntity.ok("the item increased");
    }

    @PutMapping("decreaseItem/{cartId}/{itemId}/{sizeId}")
    public ResponseEntity<String> decreaseCartItem(
             @PathVariable String cartId
            ,@PathVariable String itemId
            ,@PathVariable String sizeId) {
        cartService.decreaseItem(cartId, itemId, sizeId);
        return ResponseEntity.ok("the item decreased");
    }

    @DeleteMapping("deleteCartItem/{cartId}/{itemId}/{sizeId}")
    public ResponseEntity<String> deleteCartItem(@Valid
             @PathVariable String cartId
            ,@PathVariable String itemId
            ,@PathVariable String sizeId){
        cartService.deleteCartItem(cartId, itemId, sizeId);
        return ResponseEntity.ok("the item deleted");

    }

    @GetMapping("getItemSizeName/{itemId}/{sizeId}")
    public ResponseEntity<ItemSizeResponse> getItemSizeResponse(@PathVariable String itemId, String sizeId){
        return ResponseEntity.ok((cartService.getItemSizeName(itemId, sizeId)));
    }

    @GetMapping("/getSizeById/{id}")
    public ResponseEntity<SizeResponse> getSizeById(@PathVariable String id){
        return ResponseEntity.ok(cartService.getSizeById(id));
    }
//    @GetMapping("getCartById()/{cartId}")
//    public ResponseEntity<CartResponse> getCartById(@Valid @PathVariable String cartId){
//        CartResponse cart = cartService.getCartById(cartId);
//        return ResponseEntity.ok(())
//
//    }
}
