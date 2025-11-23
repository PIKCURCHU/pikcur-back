package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqVerifyPaymentDto;
import com.pikcurchu.pikcur.dto.response.ResPaymentAddressDto;
import com.pikcurchu.pikcur.dto.response.ResVerifyPaymentDto;
import com.pikcurchu.pikcur.service.PaymentService;
// ... (다른 import)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="payment api", description = "결제 관련 api")
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 검증 요청", description = "결제 검증 로직")
    @PostMapping("/verify")
    public ResponseEntity<ResVerifyPaymentDto> verifyPayment(@RequestBody ReqVerifyPaymentDto dto, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        try {
            boolean isValid = paymentService.verifyPayment(dto, memberNo);

            if (isValid) {
                ResVerifyPaymentDto responseDto = new ResVerifyPaymentDto("success", "결제 검증 성공");
                return ResponseEntity.ok().body(responseDto);
            } else {
                ResVerifyPaymentDto responseDto = new ResVerifyPaymentDto("fail", "결제 검증 실패 (금액 불일치)");
                return ResponseEntity.badRequest().body(responseDto);
            }

        } catch (RuntimeException e) {
            ResVerifyPaymentDto responseDto = new ResVerifyPaymentDto("error", "결제 처리 중 서버 오류 발생");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @Operation(summary = "낙찰 결제 전 정보 주소정보 조회", description = "낙찰 결제 전 정보 주소정보 조회 API")
    @GetMapping("/info")
    public ResponseEntity<ResPaymentAddressDto> selectPaymentAddress(HttpServletRequest request, @RequestParam Integer bidId) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");

        ResPaymentAddressDto response = paymentService.selectPaymentAddress(memberNo, bidId);

        return new ResponseEntity<ResPaymentAddressDto>(response, HttpStatus.OK);
    }

    @Operation(summary = "즉시 결제 전 정보 주소정보 조회", description = "즉시 결제 전 정보 주소정보조회 API")
    @GetMapping("/buyout/{goodsId}")
    public ResponseEntity<ResPaymentAddressDto> selectBuyoutPaymentAddress(HttpServletRequest request, @PathVariable Integer goodsId) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");

        ResPaymentAddressDto response = paymentService.selectBuyoutPaymentAddress(memberNo, goodsId);

        return new ResponseEntity<ResPaymentAddressDto>(response, HttpStatus.OK);
    }
}