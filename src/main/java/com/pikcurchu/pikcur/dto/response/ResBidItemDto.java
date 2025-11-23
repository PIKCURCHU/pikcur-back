package com.pikcurchu.pikcur.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResBidItemDto {
    private Integer goodsId;
    private Integer bidId;
    private String goodsName;
    private Integer bidPrice;
    private String statusName;
    private LocalDateTime createDate;
}
