package com.Link.cartService.proxy;


import com.Link.cartService.Config.FeignClientConfig;
import com.Link.cartService.Model.Dto.ResponseDto.ItemResponse;
import com.Link.cartService.Model.Dto.ResponseDto.ItemSizeResponse;
import com.Link.cartService.Model.Dto.ResponseDto.SizeResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Menu-Service", url = "http://localhost:8000", configuration = FeignClientConfig.class)
public interface MenuProxy {
    @GetMapping("/items/GetItemById/{id}")
    public ResponseEntity<ItemResponse> getItemById(@Valid @PathVariable String id);

    @GetMapping("/items/ItemStatus/{id}")
    public ResponseEntity<Boolean> checkStatus(@PathVariable String id);

    @GetMapping("/items/ItemStatus")
    public ResponseEntity<Boolean> checkStatus(
            @RequestParam String itemId,
            @RequestParam String sizeId
    ) ;

    @GetMapping("/items/getItemSizeNameById/{itemId}/{sizeId}")
    public ResponseEntity<ItemSizeResponse> getItemSizeName(@Valid @RequestParam String itemId,
                                                            @RequestParam String sizeId);

    @GetMapping("/size/getSizeById/{id}")
    public ResponseEntity<SizeResponse> getSizeById( @PathVariable String id);
}
