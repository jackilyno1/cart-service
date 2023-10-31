package com.example.bookshop.cartservice.service;

import com.example.bookshop.cartservice.dto.CartDto;
import com.example.bookshop.cartservice.dto.SachDto;
import com.example.bookshop.cartservice.dto.TrangThaiSach;
import com.example.bookshop.cartservice.exception.CartNotFoundException;
import com.example.bookshop.cartservice.mapper.CommonMapper;
import com.example.bookshop.cartservice.model.Cart;
import com.example.bookshop.cartservice.model.Sach;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final String KEY_CART = "CART";
    private final WebClient webClient;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Cart> hashOperations;
    public CartDto getCart(Long userId){
        Cart cart = hashOperations.get(KEY_CART, userId);
        if(cart == null) {
            throw new CartNotFoundException("Không tìm thấy giỏ hàng");
        }
        List<SachDto> sachDtos = cart.getSachList()
                .stream()
                .map(CommonMapper::mapToSachDto)
                .toList();
        List<Integer> sachIds = sachDtos.stream()
                        .map(SachDto::getId)
                        .toList();
        TrangThaiSach[] trangThaiSaches = webClient.get()
                .uri("http://sach/api/sach/trangThaiGia",
                        uriBuilder -> uriBuilder
                                .queryParam("sachId", sachIds)
                                .build())
                .retrieve()
                .bodyToMono(TrangThaiSach[].class)
                .block();
        try {
            sachDtos.forEach(sachDto -> {
                int index = Arrays.binarySearch(trangThaiSaches, sachDto.getId());
                TrangThaiSach tts = trangThaiSaches[index];
                if(!tts.getTrangThai()){
                    sachDtos.remove(sachDto);
                    return;
                }
                sachDto.setGia(tts.getGia());
                sachDto.setTrangThai(tts.getTrangThai());
            });
        } catch (Exception e) {
            throw new RuntimeException("Xảy ra lỗi, vui lòng thử lại sau.");
        }
        return new CartDto(cart.getId(), sachDtos);
    }


}
