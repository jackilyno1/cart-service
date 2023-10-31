package com.example.bookshop.cartservice.model;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sach {
    private int id;
    private String ten;
    private String anh;
    private Integer soLuong;
}
