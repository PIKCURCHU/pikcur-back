package com.pikcurchu.pikcur.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ResGoodsDetailDto {
    private List<String> imageList;
    private String categoryPath;
    private String goodsName;
    private Integer goodsId;
    private String brandName;
    private Integer brandId;
    private Long currentBidPrice;
    private Long buyoutPrice;
    private Long startPrice;
    private Long shippingPrice;
    private String quality;
    private String auctionEndDate;
    private String size;
    private boolean isLiked;
    private String statusName;
    private String goodsInfo;
    private StoreInfo storeInfo;
}
