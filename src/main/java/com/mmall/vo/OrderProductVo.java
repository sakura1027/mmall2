package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sakura on 2018/4/4.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;

}
