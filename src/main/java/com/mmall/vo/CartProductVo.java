package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by sakura on 2018/3/20.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductVo {
    //结合了产品和购物车的一个抽象对象
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Integer productChecked;

    private String limitQuantity;

}
