package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.response.ResBrandDetailDto;
import com.pikcurchu.pikcur.dto.response.ResBrandListDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BrandMapper {
    ResBrandDetailDto selectBrandDetail(Integer brandId, Integer memberNo);

    List<ResGoodsItemDto> selectBrandGoodsList(Map<String, Object> params);

    void insertBrandLike(Integer brandId, Integer memberNo);

    void deleteBrandLike(Integer brandId, Integer memberNo);

    int countBrandGoodsByBrandId(Integer brandId);

    Integer selectBrandGoodsCount(Integer brandId);

    List<ResBrandListDto> selectBrandList();
}
