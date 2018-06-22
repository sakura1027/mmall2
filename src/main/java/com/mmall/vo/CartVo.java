package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sakura on 2018/3/20.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartVo {
    List<CartProductVo> cartProductVoList;

    private BigDecimal cartTotalPrice;

    private boolean allChecked;

    private String imageHost;

}
