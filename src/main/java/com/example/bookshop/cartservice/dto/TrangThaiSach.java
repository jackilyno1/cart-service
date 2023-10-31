package com.example.bookshop.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrangThaiSach {
    private int sachId;
    private BigDecimal gia;
    private Boolean trangThai;

    @Override
    public boolean equals(Object obj) {
        TrangThaiSach tts = (TrangThaiSach) obj;
        return tts.getSachId() == this.sachId;
    }
}
