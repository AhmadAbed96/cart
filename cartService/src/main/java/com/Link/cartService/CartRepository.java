package com.Link.cartService;

import com.Link.cartService.Model.Cart;
import com.Link.cartService.Model.Dto.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
//    Optional<Cart> findByUserNumber(Integer userNumber);


}
