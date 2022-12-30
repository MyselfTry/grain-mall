package com.java.gmall.item.feign;

import com.java.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/26 15:01
 */
@FeignClient("sms-server")
public interface SmsFeign extends GmallSmsApi {
}
