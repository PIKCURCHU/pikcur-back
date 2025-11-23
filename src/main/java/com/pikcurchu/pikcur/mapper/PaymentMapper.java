package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.response.ResPaymentAddressDto;
import com.pikcurchu.pikcur.vo.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    void insertPayment(Payment payment);

    ResPaymentAddressDto selectPaymentAddress(Integer memberNo, Integer bidId);

    ResPaymentAddressDto selectBuyoutPaymentAddress(Integer memberNo, Integer goodsId);
}
