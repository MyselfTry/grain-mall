package com.java.gmall.order.feign;

import com.java.gmall.cart.api.CartApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/30 11:43
 */
@FeignClient("cart-server")
public interface CartFeign extends CartApi {
}
