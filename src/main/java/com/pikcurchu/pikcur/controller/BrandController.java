package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="brand api", description = "브랜드 관련 api")
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "브랜드 정보 조회", description = "브랜드 아이디를 통해 정보 조회")
    @GetMapping("/{brandId}")
    public ResponseEntity<ResBrandDetailDto> selectBrandDetail(@PathVariable Integer brandId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        ResBrandDetailDto brandDetail = brandService.selectBrandDetail(brandId, memberNo);
        return new ResponseEntity<>(brandDetail, HttpStatus.OK);
    }

    @Operation(summary = "브랜드 리스트 조회", description = "등록되어있는 브랜드 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<List<ResBrandListDto>> selectBrandList() {
        List<ResBrandListDto> brandList = brandService.selectBrandList();
        return new ResponseEntity<>(brandList, HttpStatus.OK);
    }

    @Operation(summary = "브랜드 상품 조회", description = "브랜드 아이디를 통한 해당 상품 리스트 조회")
    @GetMapping("/{brandId}/goods")
    public ResponseEntity<ResGoodsPageDto> selectBrandGoodsList(@PathVariable Integer brandId, HttpServletRequest request, @RequestParam int currentPage) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        ResGoodsPageDto goodsList = brandService.selectBrandGoodsList(brandId, memberNo, currentPage);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @Operation(summary = "브랜드 찜", description = "상품 번호를 통해 해당 상품 찜")
    @PostMapping("/{brandId}/like")
    public ResponseEntity<Void> insertBrandLike(@PathVariable Integer brandId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        brandService.insertBrandLike(brandId, memberNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "브랜드 찜 취소", description = "상품 번호를 통해 해당 상품 찜 취소")
    @DeleteMapping("/{brandId}/like")
    public ResponseEntity<Void> deleteBrandLike(@PathVariable Integer brandId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        brandService.deleteBrandLike(brandId, memberNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
