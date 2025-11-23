package com.pikcurchu.pikcur.mapper;

import java.util.List;

public interface ImageMapper {
    void insertGoodsImage(Integer goodsId, String imageUrl, Integer sort);

    List<String> findGoodsImages(Integer goodsId);
}
