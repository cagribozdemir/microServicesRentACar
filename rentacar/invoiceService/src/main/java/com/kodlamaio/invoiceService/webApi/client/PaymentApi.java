package com.kodlamaio.invoiceService.webApi.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "paymentApi", url = "http://localhost:9010/payment/api/payments/")
public interface PaymentApi {

}
