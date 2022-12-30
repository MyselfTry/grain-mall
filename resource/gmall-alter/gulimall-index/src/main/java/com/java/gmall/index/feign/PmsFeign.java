package com.java.gmall.index.feign;

import com.java.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/17 22:20
 */
@FeignClient("pms-server")
public interface PmsFeign extends GmallPmsApi{
}
