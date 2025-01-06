package com.Link.cartService.Config;

import com.Link.cartService.Config.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Feign;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .errorDecoder(new CustomErrorDecoder());
        // Other Feign configurations can be chained here if needed
    }
}
