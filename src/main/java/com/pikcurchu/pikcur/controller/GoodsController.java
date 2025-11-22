package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqGoodsDto;
import com.pikcurchu.pikcur.dto.request.ReqGoodsReportDto;
import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name="goods api", description = "상품 관련 api")
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @Operation(summary = "인기 상품 조회", description = "좋아요 수 기준 인기 상품 리스트 조회")
    @GetMapping("/popular")
    public ResponseEntity<List<ResGoodsItemDto>> selectPopularGoodsList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        List<ResGoodsItemDto> goodsList = goodsService.selectPopularGoodsList(memberNo);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @Operation(summary = "최근 본 상품 조회", description = "최근 본 상품 리스트 조회")
    @GetMapping("/recent")
    public ResponseEntity<List<ResGoodsItemDto>> selectRecentViewGoodsList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        List<ResGoodsItemDto> goodsList = goodsService.selectRecentViewGoodsList(memberNo);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @Operation(summary = "마감 임박 상품 조회", description = "마감 임박 상품 리스트 조회")
    @GetMapping("/closing-soon")
    public ResponseEntity<List<ResGoodsItemDto>> selectGoodsListByEndDate(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        List<ResGoodsItemDto> goodsList = goodsService.selectGoodsListByEndDate(memberNo);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @Operation(summary = "카테고리 리스트 조회", description = "카테고리 리스트 조회")
    @GetMapping("/categories")
    public ResponseEntity<List<ResCategoryDto>> selectCategories() {
        List<ResCategoryDto> categories = goodsService.selectCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "카테고리 별 상품 조회", description = "카테고리 아이디를 통한 상품 리스트 조회")
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ResGoodsPageDto> selectGoodsListByCategoryId(@PathVariable Integer categoryId, HttpServletRequest request, @RequestParam int currentPage) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        ResGoodsPageDto goodsList = goodsService.selectGoodsListByCategoryId(categoryId, memberNo, currentPage);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @Operation(summary = "상품 상세 조회", description = "상품 아이디를 통한 상품 상세 조회")
    @GetMapping("/{goodsId}")
    public ResponseEntity<ResGoodsDetailDto> selectGoodsDetailById(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        ResGoodsDetailDto goodsDetail = goodsService.selectGoodsDetailById(goodsId, memberNo);
        return new ResponseEntity<>(goodsDetail, HttpStatus.OK);
    }

    @Operation(summary = "상품 문의 리스트 조회", description = "상품 아이디를 통한 상품 문의 리스트 조회")
    @GetMapping("/{goodsId}/questions")
    public ResponseEntity<ResGoodsQuestionsPageDto> selectGoodsQuestionsById(@PathVariable Integer goodsId, @RequestParam int currentPage) {
        ResGoodsQuestionsPageDto goodsQuestions = goodsService.selectGoodsQuestionsById(goodsId, currentPage);
        return new ResponseEntity<>(goodsQuestions, HttpStatus.OK);
    }

    @Operation(summary = "상품 신고", description = "상품 번호를 통해 해당 상품 신고")
    @PostMapping("/report/{goodsId}")
    public ResponseEntity<Void> reportGoods(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        goodsService.reportGoods(goodsId, memberNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "상품 찜", description = "상품 번호를 통해 해당 상품 찜")
    @PostMapping("/like/{goodsId}")
    public ResponseEntity<Void> goodsLike(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        goodsService.insertGoodsLike(goodsId, memberNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "상품 찜 취소", description = "상품 번호를 통해 해당 상품 찜 취소")
    @DeleteMapping("/like/{goodsId}")
    public ResponseEntity<Void> deleteGoodsLike(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        goodsService.deleteGoodsLike(goodsId, memberNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "상품 등록", description = "경매 상품 등록")
    @PostMapping
    public ResponseEntity<Void> insertGoods(@RequestPart("goodsData") ReqGoodsDto reqGoodsDto,
                                            @RequestPart("image") List<MultipartFile> images, HttpServletRequest request) {

        Integer memberNo = (Integer) request.getAttribute("memberNo");
        Integer goodsId = goodsService.insertGoods(reqGoodsDto, memberNo);
        if(images.isEmpty() || images == null) {
            throw new IllegalArgumentException("이미지는 최소 1장 이상 필요합니다.");
        }
        goodsService.insertGoodsImages(goodsId, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
