package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.dto.response.ResBrandDetailDto;
import com.pikcurchu.pikcur.dto.response.ResBrandListDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsPageDto;
import com.pikcurchu.pikcur.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandMapper brandMapper;
    private static final int PAGE_SIZE_21 = 21;

    public ResBrandDetailDto selectBrandDetail(Integer brandId, Integer memberNo) {
        ResBrandDetailDto brandDetailDto = brandMapper.selectBrandDetail(brandId, memberNo);
        brandDetailDto.setGoodsCount(brandMapper.selectBrandGoodsCount(brandId));
        return brandDetailDto;
    }

    public ResGoodsPageDto selectBrandGoodsList(Integer brandId, Integer memberNo, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_21;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("brandId", brandId);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_21);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResGoodsItemDto> goodsList = brandMapper.selectBrandGoodsList(params);
        int totalCount = brandMapper.countBrandGoodsByBrandId(brandId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_21);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    public void insertBrandLike(Integer brandId, Integer memberNo) {
        brandMapper.insertBrandLike(brandId, memberNo);
    }

    public void deleteBrandLike(Integer brandId, Integer memberNo) {
        brandMapper.deleteBrandLike(brandId, memberNo);
    }

    public List<ResBrandListDto> selectBrandList() {
        return brandMapper.selectBrandList();
    }
}
