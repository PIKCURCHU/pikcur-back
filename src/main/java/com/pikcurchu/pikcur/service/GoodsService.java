package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.dto.request.ReqGoodsDto;
import com.pikcurchu.pikcur.dto.request.ReqGoodsReportDto;
import com.pikcurchu.pikcur.dto.request.ReqStoreReportDto;
import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.mapper.GoodsMapper;
import com.pikcurchu.pikcur.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsMapper goodsMapper;
    private final ImageMapper imageMapper;
    private final FileService fileService;
    private static final int PAGE_SIZE_21 = 21;
    private static final int PAGE_SIZE_6 = 6;

    public List<ResGoodsItemDto> selectPopularGoodsList(Integer memberNo) {
        return goodsMapper.findPopularGoodsList(memberNo);
    }
    public List<ResGoodsItemDto> selectRecentViewGoodsList(Integer memberNo) {
        return goodsMapper.findRecentViewGoodsList(memberNo);
    }
    public List<ResGoodsItemDto> selectGoodsListByEndDate(Integer memberNo) {
        return goodsMapper.findGoodsByAuctionEndAsc(memberNo);
    }
    public List<ResCategoryDto> selectCategories() {
        return goodsMapper.findCategories();
    }

    public ResGoodsPageDto selectGoodsListByCategoryId(Integer categoryId, Integer memberNo, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_21;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_21);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResGoodsItemDto> goodsList = goodsMapper.findGoodsListByCategoryId(params);
        int totalCount = goodsMapper.countCategoryGoodsById(categoryId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_21);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    public ResGoodsDetailDto selectGoodsDetailById(Integer goodsId, Integer memberNo) {
        ResGoodsDetailDto goodsDetail = goodsMapper.findGoodsDetailById(goodsId, memberNo);
        goodsDetail.setImageList(imageMapper.findGoodsImages(goodsId));

        if(memberNo != null) {
            goodsMapper.insertGoodsHistory(goodsId, memberNo);
        }
        goodsMapper.updateGoodsView(goodsId);

        return goodsDetail;
    }

    public void reportGoods(Integer goodsId, Integer memberNo) {
        goodsMapper.insertGoodsReport(goodsId, memberNo);
    }

    public void insertGoodsLike(Integer goodsId, Integer memberNo) {
        goodsMapper.insertGoodsLike(goodsId, memberNo);
    }

    public void deleteGoodsLike(Integer goodsId, Integer memberNo) {
        goodsMapper.deleteGoodsLike(goodsId, memberNo);
    }

    @Transactional
    public Integer insertGoods(ReqGoodsDto reqGoodsDto, Integer memberNo) {
        reqGoodsDto.setMemberNo(memberNo);
        goodsMapper.insertGoods(reqGoodsDto);

        return reqGoodsDto.getGoodsId();
    }

    public ResGoodsQuestionsPageDto selectGoodsQuestionsById(Integer goodsId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("goodsId", goodsId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResGoodsQuestionsDto> questionList = goodsMapper.selectGoodsQuestionsById(params);
        int totalCount = goodsMapper.countSellTransactionByStoreId(goodsId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResGoodsQuestionsPageDto(questionList, totalPages, totalCount);
    }

    @Transactional
    public void insertGoodsImages(Integer goodsId, List<MultipartFile> images) {
        for (int i = 0; i < images.size(); i++) {
            MultipartFile img = images.get(i);
            String uploadedPath = fileService.goodsUploadFile(img); // 파일 저장 후 URL 반환
            imageMapper.insertGoodsImage(goodsId, uploadedPath, i+1); // sort값부여
        }
    }
}
