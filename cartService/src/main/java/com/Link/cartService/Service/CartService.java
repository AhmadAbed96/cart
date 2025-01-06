package com.Link.cartService.Service;

import com.Link.cartService.CartRepository;
import com.Link.cartService.Mapper.CartMapper;
import com.Link.cartService.Model.Cart;
import com.Link.cartService.Model.Dto.RequestDto.ItemCartRequest;
import com.Link.cartService.Model.Dto.ResponseDto.*;
import com.Link.cartService.proxy.MenuProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuProxy menuProxy;
    @Autowired
    private CartMapper cartMapper;

    public ItemSizeResponse getItemSizeName(String itemId, String sizeId){
        return menuProxy.getItemSizeName(itemId, sizeId).getBody();
    }

     public Long  getSectionCount (){
        RestTemplate restTemplate = new RestTemplate();
         String url = "http://localhost:8000/Sections/getSectionCount";
        Long sectionCount = restTemplate
                .getForObject(url, Long.class);
        return sectionCount;
     }

     public SizeResponse getSizeById(String id){
        return menuProxy.getSizeById(id).getBody();
     }

    public ItemResponse getItemById(String id){
         return menuProxy.getItemById(id).getBody();
    }

    public Boolean getStatus(String itemId, String sizeId){
         return menuProxy.checkStatus(itemId, sizeId).getBody();
    }

    public CartResponse getCartById(String id){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart with ID " + id + " does not exist."));
        return cartMapper.mapCartToCartResponse(cart);
    }

    public void createCart(List<ItemCartRequest> items) {
        if (items == null || items.isEmpty()) {
            log.info("The item list is null or empty");
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }

        Map<String, ItemCartRequest> uniqueItemsMap = new HashMap<>();
        double totalPrice = 0.0;
        int totalQuantity = 0;

        for (ItemCartRequest itemCart : items) {
            ResponseEntity<Boolean> response = menuProxy.checkStatus(itemCart.getItemId(), itemCart.getSizeId());
            SizeResponse size = menuProxy.getSizeById(itemCart.getSizeId()).getBody();
            if (response.getBody() == null || !response.getBody()) {
                throw new IllegalArgumentException("Item with ID " + itemCart.getItemId() + " is not available.");
            }

            String uniqueKey = itemCart.getItemId() + "_" + itemCart.getSizeId();
            if (uniqueItemsMap.containsKey(uniqueKey)) {
                ItemCartRequest existingItem = uniqueItemsMap.get(uniqueKey);
                existingItem.setQuantity(existingItem.getQuantity() + itemCart.getQuantity());
            } else {
                uniqueItemsMap.put(uniqueKey, itemCart);
            }
        }

        // Validate and calculate totals
        List<ItemCartRequest> validItems = new ArrayList<>(uniqueItemsMap.values());
        for (ItemCartRequest validItem : validItems) {

            SizeResponse sizeResponse = menuProxy.getSizeById(validItem.getSizeId()).getBody();
            if (sizeResponse == null) {
                throw new IllegalArgumentException("Failed to fetch item details for ID " + validItem.getItemId());
            }
            totalPrice += sizeResponse.getPrice() * validItem.getQuantity();
            totalQuantity += validItem.getQuantity();
        }

        // Create and save the cart
        if (!validItems.isEmpty()) {
            Cart cart = Cart.builder()
                    .item(validItems)
                    .totalPrice(totalPrice)
                    .totalQuantity(totalQuantity)
                    .build();
            cartRepository.save(cart);
        } else {
            throw new IllegalStateException("No valid items to create the cart.");
        }
    }

    public void addItemsToCart(String cartId, List<ItemCartRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with ID " + cartId + " does not exist."));

        Map<String, ItemCartRequest> existingItemsMap = new HashMap<>();
        for (ItemCartRequest existingItem : cart.getItem()) {
            String uniqueKey = existingItem.getItemId() + "_" + existingItem.getSizeId();
            existingItemsMap.put(uniqueKey, existingItem);
        }

        double updatedTotalPrice = 0;
        int updatedTotalQuantity = 0;

        for (ItemCartRequest itemCart : items) {
            ResponseEntity<Boolean> response = menuProxy.checkStatus(itemCart.getItemId(), itemCart.getSizeId());

            if (response.getBody() == null || !response.getBody()) {
                throw new IllegalArgumentException("Item with ID " + itemCart.getItemId() + " is not available.");
            }

            String uniqueKey = itemCart.getItemId() + "_" + itemCart.getSizeId();

            if (existingItemsMap.containsKey(uniqueKey)) {
                ItemCartRequest existingItem = existingItemsMap.get(uniqueKey);
                existingItem.setQuantity(existingItem.getQuantity() + itemCart.getQuantity());
            } else {
                existingItemsMap.put(uniqueKey, itemCart);
            }
        }

        List<ItemCartRequest> updatedItems = new ArrayList<>(existingItemsMap.values());

        for (ItemCartRequest updatedItem : updatedItems) {
            SizeResponse sizeResponse = menuProxy.getSizeById(updatedItem.getSizeId()).getBody();
            if (sizeResponse == null) {
                throw new IllegalArgumentException("Failed to fetch item details for ID " + updatedItem.getItemId());
            }
            updatedTotalPrice += sizeResponse.getPrice() * updatedItem.getQuantity();
            updatedTotalQuantity += updatedItem.getQuantity();
        }

        cart.setItem(updatedItems);
        cart.setTotalPrice(updatedTotalPrice);
        cart.setTotalQuantity(updatedTotalQuantity);

        cartRepository.save(cart);
    }

    public void increaseItem(String cartId, String itemId, String sizeId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with ID " + cartId + " does not exist."));
        ResponseEntity<Boolean> response = menuProxy.checkStatus(itemId, sizeId);
        if (response.getBody() == null || !response.getBody()) {
            throw new IllegalArgumentException("Item with ID " + itemId + " is not available.");
        }

        SizeResponse sizeResponse = menuProxy.getSizeById(sizeId).getBody();

        ItemCartRequest itemCart = cart.getItem().stream()
                .filter(i -> i.getItemId().equals(itemId) && i.getSizeId().equals(sizeId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("the item not found"));
        itemCart.setQuantity(itemCart.getQuantity()+1);
        cart.setTotalQuantity(cart.getTotalQuantity()+1);
        cart.setTotalPrice(cart.getTotalPrice()+(sizeResponse.getPrice()));
        cartRepository.save(cart);
    }

    public void decreaseItem(String cartId, String itemId, String sizeId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with ID " + cartId + " does not exist."));
        ResponseEntity<Boolean> response = menuProxy.checkStatus(itemId, sizeId);

        if (response.getBody() == null || !response.getBody()) {
            throw new IllegalArgumentException("Item with ID " + itemId + " is not available.");
        }

        SizeResponse sizeResponse = menuProxy.getSizeById(sizeId).getBody();

        ItemCartRequest itemCart = cart.getItem().stream()
                .filter(i -> i.getItemId().equals(itemId) && i.getSizeId().equals(sizeId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("the item not found"));
        if (itemCart.getQuantity() > 0){
            itemCart.setQuantity(itemCart.getQuantity()-1);
            cart.setTotalQuantity(cart.getTotalQuantity()-1);
           cart.setTotalPrice(cart.getTotalPrice()-(sizeResponse.getPrice()));
        }

        cartRepository.save(cart);
    }

    public void deleteCartItem(String cartId, String itemId, String sizeId){
         Cart cart = cartRepository.findById(cartId)
                 .orElseThrow(() -> new RuntimeException("the cart not found"));
        ItemCartRequest itemCart= cart.getItem()
                .stream()
                .filter(item-> item.getItemId().equals(itemId) && item.getSizeId().equals(sizeId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("the item not found"));
         boolean itemRemoved  = cart.getItem()
                 .removeIf(item -> item.getItemId().equals(itemId) && item.getSizeId().equals(sizeId));

        SizeResponse sizeResponse = menuProxy.getSizeById(sizeId).getBody();
        if (!itemRemoved){
                throw new RuntimeException("the item not found");
            }

            cart.setTotalQuantity(cart.getTotalQuantity()-itemCart.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice()-(itemCart.getQuantity()* sizeResponse.getPrice()));
            cartRepository.save(cart);
        }


    }





