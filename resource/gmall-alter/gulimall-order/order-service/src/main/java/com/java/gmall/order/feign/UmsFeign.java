package com.java.gmall.order.feign;

import com.java.gmall.ums.api.UmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/30 11:41
 */
@FeignClient("ums-server")
public interface UmsFeign extends UmsApi {
}
