package com.java.gmall.pms.feign;

import com.java.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("sms-server")
public interface SkuSaleFeign extends GmallSmsApi {

}