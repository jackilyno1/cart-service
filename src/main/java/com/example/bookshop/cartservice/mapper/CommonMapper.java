package com.example.bookshop.cartservice.mapper;

import com.example.bookshop.cartservice.dto.SachDto;
import com.example.bookshop.cartservice.model.Sach;

public class CommonMapper {
    public static SachDto mapToSachDto(Sach sach) {
        return SachDto.builder()
                .id(sach.getId())
                .anh(sach.getAnh())
                .ten(sach.getTen())
                .soLuong(sach.getSoLuong())
                .build();
    }
}
