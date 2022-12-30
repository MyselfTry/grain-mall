package com.java.gmall.auth.feign;

import com.java.gmall.ums.api.UmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/28 15:28
 */
@FeignClient("ums-server")
public interface GmallUmsFeign extends UmsApi {
}
